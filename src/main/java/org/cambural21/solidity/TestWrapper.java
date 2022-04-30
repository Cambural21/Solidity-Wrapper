package org.cambural21.solidity;

import javafx.util.Pair;
import org.cambural21.solidity.eip712.Cast;
import org.cambural21.solidity.eip712.EIP712;
import org.cambural21.solidity.eip712.abi;
import org.cambural21.solidity.manager.contracts.TantalumToken;
import org.cambural21.solidity.utils.Sqlite;
import org.cambural21.solidity.wrapper.Blockchain;
import org.cambural21.solidity.wrapper.Feature;
import org.cambural21.solidity.wrapper.LoggingLevel;
import org.cambural21.solidity.wrapper.Wrapper;
import org.cambural21.solidity.wrapper.credentials.CredentialHelper;
import org.cambural21.solidity.wrapper.interfaces.IERC;
import org.cambural21.solidity.wrapper.interfaces.IERC20;
import org.cambural21.solidity.wrapper.interfaces.IERC2612;
import org.json.JSONObject;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.ChainId;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.Map;
import java.util.TreeMap;

public class TestWrapper {

    //------------------------------------------------------------------------------------------------------------------

    private static Pair<BigInteger, EIP712.IEIP2612Permit> permit(Wrapper wrapper, String contractAddress, Map<Feature, IERC> features,
                                                       String owner, String spender, BigInteger value, BigInteger deadline){
        deadline = deadline == null?Cast.toDateBigInteger("22-01-2030 10:15:55 AM"):deadline;
        value = value == null?BigInteger.ONE:value;
        IERC2612 ierc2612 = (IERC2612)features.get(Feature.IERC2612);
        EIP712 eip712Generator = EIP712.getEIP712("TANTALUS", "1", (byte)4, contractAddress);
        BigInteger nonce = null;
        {
            try{
                nonce = ierc2612.nonces(owner).add(BigInteger.ONE);
            }catch (Exception e){
                e.printStackTrace();
                nonce = null;
            }
        }
        EIP712.IEIP2612Permit permit = eip712Generator.getEIP2612Permit(wrapper, owner, spender, nonce, value, deadline);
        return wrapper != null && permit != null?new Pair<BigInteger, EIP712.IEIP2612Permit>(nonce, permit):null;
    }

    private static Pair<Wrapper, Contract> deploy(Blockchain blockchain, Credentials credentials, Class contractCls){
        Wrapper wrapper = Wrapper.getInstance(blockchain, credentials);
        Contract contract = wrapper != null?wrapper.deploy(contractCls, true):null;
        return wrapper != null && contract != null?new Pair<Wrapper, Contract>(wrapper, contract):null;
    }

    private static Pair<Wrapper, Map<Feature, IERC>> loadFeatures(Blockchain blockchain, Credentials credentials,
                                                              String contractAddress, Class contractCls){
        Wrapper wrapper = Wrapper.getInstance(blockchain, credentials);
        Map<Feature, IERC> features = wrapper != null?wrapper.loadFeatures(contractCls, contractAddress, Feature.IERC2612, Feature.IERC20):null;
        return wrapper != null && features != null?new Pair<Wrapper, Map<Feature, IERC>>(wrapper, features):null;
    }

    private static EIP712.IEIP2612Permit buildPermit(String privateKey, String infuraKey, String contractAddress,
                                                     Class<?> contractCls, String owner, String spender,
                                                     BigInteger value, BigInteger deadline){
        Blockchain blockchain = RINKEBY_INFURA(infuraKey);
        Credentials credentials = privateKey(privateKey);
        Pair<Wrapper, Map<Feature, IERC>> pairFeatures = loadFeatures(blockchain, credentials, contractAddress, contractCls);
        Pair<BigInteger, EIP712.IEIP2612Permit> pairPermit = permit(pairFeatures.getKey(), contractAddress, pairFeatures.getValue(), owner, spender, value, deadline);
        pairFeatures.getKey().shutdown();
        //return pairPermit != null?new Pair<Pair<Wrapper, Map<Feature, IERC>>, Pair<BigInteger, EIP712.IEIP2612Permit>>(pairFeatures, pairPermit):null;
        return pairPermit != null?pairPermit.getValue():null;
    }

    private static Boolean claimPermit(String privateKey, String infuraKey, String contractAddress,
                                       Class<?> contractCls, EIP712.IEIP2612Permit permit){
        Blockchain blockchain = RINKEBY_INFURA(infuraKey);
        Credentials credentials = privateKey(privateKey);
        Pair<Wrapper, Map<Feature, IERC>> pairFeatures = loadFeatures(blockchain, credentials, contractAddress, contractCls);
        IERC2612 ierc2612 = (IERC2612)pairFeatures.getValue().get(Feature.IERC2612);
        IERC20 ierc20 = (IERC20)pairFeatures.getValue().get(Feature.IERC20);

        TransactionReceipt transactionReceipt = null;
        try{
            if(ierc20.allowance(permit.getOwner(),permit.getSpender()) == BigInteger.valueOf(0))
                transactionReceipt = ierc2612.permit(
                        permit.getOwner(),
                        permit.getSpender(),
                        permit.getValue(),
                        permit.getDeadline(),
                        permit.getV(),
                        permit.getR(),
                        permit.getS());
        }catch (Exception e){
            e.printStackTrace();
            transactionReceipt = null;
        }

        Boolean transfer = null;
        if(transactionReceipt != null){
            try{
                transfer = ierc20.transferFrom(permit.getOwner(), permit.getSpender(), permit.getValue());
            }catch (Exception e){
                e.printStackTrace();
                transfer = null;
            }
        }

        pairFeatures.getKey().shutdown();

        return transfer;
    }

    private static File saveCredentials(Credentials credentials, String password) {
        File dir = new File("wallet");
        File wallet = null;
        {
            try{
                if(!dir.exists()) dir.mkdirs();
                wallet = CredentialHelper.generateWalletFile(password, credentials, dir.getAbsoluteFile());
            }catch (Exception e){
                e.printStackTrace();
                wallet = null;
            }
        }
        return wallet;
    }

    private static String toJson(EIP712.IEIP2612Permit permit, int indentFactor){
        String json = null;
        if(permit != null && indentFactor>=0){
            try{
                Map<String, Object> map = new TreeMap<>();
                map.put("deadline", permit.getDeadline());
                map.put("spender", permit.getSpender());
                map.put("owner", permit.getOwner());
                map.put("value", permit.getValue());
                map.put("nonce", permit.getNonce());
                map.put("r", abi.toHex(permit.getR()));
                map.put("s", abi.toHex(permit.getS()));
                map.put("v", permit.getV());
                json = new JSONObject(map).toString(indentFactor);
            }catch (Exception e){
                e.printStackTrace();
                json = null;
            }
        }
        return json;
    }

    private static Credentials loadCredentials(File wallet, String password) {
        Credentials credentials = null;
        {
            if(wallet != null && password != null){
                try{
                    credentials = CredentialHelper.loadCredentials(password, wallet);
                }catch (Exception e){
                    e.printStackTrace();
                    credentials = null;
                }
            }
        }
        return credentials;
    }

    private static EIP712.IEIP2612Permit fromJson(String json){
        EIP712.IEIP2612Permit permit = null;
        if(json != null && !json.isEmpty()){
            try{
                JSONObject map = new JSONObject(json);
                final String spender = new Address(160, map.getString("spender")).getValue();
                final String owner = new Address(160, map.getString("owner")).getValue();
                final BigInteger deadline = map.getBigInteger("deadline");
                final BigInteger nonce = map.getBigInteger("nonce");
                final BigInteger value = map.getBigInteger("value");
                final BigInteger v = map.getBigInteger("v");
                byte[] s = Numeric.hexStringToByteArray(map.getString("s"));
                byte[] r = Numeric.hexStringToByteArray(map.getString("r"));
                if(!owner.isEmpty() && !spender.isEmpty() &&deadline != null && nonce != null && value != null &&
                        v != null && s.length>0 &&  r.length>0){
                    permit = new EIP712.IEIP2612Permit(){
                        @Override
                        public BigInteger getDeadline() {
                            return deadline;
                        }
                        @Override
                        public BigInteger getValue() {
                            return value;
                        }
                        @Override
                        public BigInteger getNonce() {
                            return nonce;
                        }
                        @Override
                        public String getSpender() {
                            return spender;
                        }
                        @Override
                        public String getOwner() {
                            return owner;
                        }
                        @Override
                        public BigInteger getV() {
                            return v;
                        }
                        @Override
                        public byte[] getR() {
                            return r;
                        }
                        @Override
                        public byte[] getS() {
                            return s;
                        }
                        @Override
                        public String toString() {
                            return "IEIP2612Permit: JSON";
                        }
                    };
                }
            }catch (Exception e){
                e.printStackTrace();
                permit = null;
            }
        }
        return permit;
    }

    private static Credentials privateKey(String privateKey){
        Credentials credentials = CredentialHelper.load(privateKey);
        return credentials;
    }

    private static Blockchain RINKEBY_INFURA(String key){
        return new Blockchain() {
            @Override
            public LoggingLevel getLoggingLevel() {
                return LoggingLevel.trace;
            }

            @Override
            public boolean isInfura() {
                return true;
            }

            @Override
            public String getRPC() {
                return "https://rinkeby.infura.io/v3/" +key;
            }
            @Override
            public String getName() {
                return "Rinkeby Test Network";
            }
            @Override
            public long getChainID() {
                return ChainId.RINKEBY;
            }
        };
    }

    private static Credentials bip44(String password){
        String mnemonic = CredentialHelper.generateMnemonic();
        Credentials credentials = CredentialHelper.loadBip44(mnemonic, password);
        return credentials;
    }

    private static Credentials bip39(String password){
        String mnemonic = CredentialHelper.generateMnemonic();
        Credentials credentials = CredentialHelper.loadBip39(mnemonic, password);
        return credentials;
    }

    private static Connection sqlite(File db) {
        Sqlite sqlite = new Sqlite();
        Connection connection = sqlite.open(db);
        return connection;
    }

    //------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println();
    }

    //------------------------------------------------------------------------------------------------------------------

}


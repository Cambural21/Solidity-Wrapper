package org.cambural21.solidity;

import org.cambural21.solidity.utils.Sqlite;
import org.cambural21.solidity.wrapper.Blockchain;
import org.cambural21.solidity.wrapper.LoggingLevel;
import org.cambural21.solidity.wrapper.Wrapper;
import org.cambural21.solidity.wrapper.credentials.CredentialHelper;
import org.web3j.crypto.Credentials;
import org.web3j.tx.ChainId;
import org.web3j.tx.Contract;

import java.io.File;
import java.sql.Connection;

public class TestWrapper {

    //------------------------------------------------------------------------------------------------------------------

    private static void wrapper(Blockchain blockchain, Credentials credentials, Class contractCls, String contractAddress) {
        Wrapper wrapper = Wrapper.getInstance(blockchain, credentials);

        //TODO DEPLOY
        {
            Contract contract = wrapper.deploy(contractCls, true);
            System.out.println("contractAddress: " + contract.getContractAddress());
        }

        //TODO LOAD & TRANSFER
        /*{
            Map<Feature, IERC> features = wrapper.loadFeatures(contractCls, contractAddress, Feature.IERC2612, Feature.IERC20);
            IERC2612 ierc2612 = (IERC2612)features.get(Feature.IERC2612);
            IERC20 ierc20 = (IERC20)features.get(Feature.IERC20);

            EIP712 eip712Generator = EIP712.getEIP712("TANTALUS", "1", (byte)4, contractAddress);
            BigInteger deadline = Cast.toDateBigInteger("22-01-2030 10:15:55 AM");;
            BigInteger value = BigInteger.ONE;
            String spender = "";
            String owner = "";

            BigInteger nonce = null;
            {
                try{
                    nonce = ierc2612.nonces(owner);
                }catch (Exception e){
                    e.printStackTrace();
                    nonce = null;
                }
            }

            EIP712.IEIP2612Permit permit = eip712Generator.getEIP2612Permit(wrapper, owner, spender, nonce, value, deadline);
            BigInteger V = permit.getV();
            byte[] R = permit.getR();
            byte[] S = permit.getS();

            TransactionReceipt transactionReceiptPermit = null;
            try{
                transactionReceiptPermit = ierc2612.permit(owner, spender, value, deadline, V, R, S);
            }catch (Exception e){
                e.printStackTrace();
                transactionReceiptPermit = null;
            }

            Boolean transfer = null;
            try{
                transfer = ierc20.transferFrom(owner, spender, value);;
            }catch (Exception e){
                e.printStackTrace();
                transfer = null;
            }

            wrapper.shutdown();
        }*/

        System.out.println();
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

    public static void main(String[] args) {
        String privateKey = "", infuraKey = "", contractAddressToLoad = "";
        Class contractClsToDeploy = null;

        Blockchain blockchain = RINKEBY_INFURA(infuraKey);
        Credentials credentials = privateKey(privateKey);
        wrapper(blockchain, credentials, contractClsToDeploy, contractAddressToLoad);
    }

    //------------------------------------------------------------------------------------------------------------------

}


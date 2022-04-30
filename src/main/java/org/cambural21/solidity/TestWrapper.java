package org.cambural21.solidity;

import javafx.util.Pair;
import org.cambural21.solidity.manager.contracts.TantalumToken;
import org.cambural21.solidity.utils.Sqlite;
import org.cambural21.solidity.wrapper.Blockchain;
import org.cambural21.solidity.wrapper.Feature;
import org.cambural21.solidity.wrapper.LoggingLevel;
import org.cambural21.solidity.wrapper.Wrapper;
import org.cambural21.solidity.wrapper.credentials.CredentialHelper;
import org.cambural21.solidity.wrapper.interfaces.IERC;
import org.web3j.crypto.Credentials;
import org.web3j.tx.ChainId;
import org.web3j.tx.Contract;

import java.io.File;
import java.sql.Connection;
import java.util.Map;

public class TestWrapper {

    //------------------------------------------------------------------------------------------------------------------

    private static Pair<Wrapper, Map<Feature, IERC>> features(Blockchain blockchain, Credentials credentials,
                                                              String contractAddress, Class contractCls){
        Wrapper wrapper = Wrapper.getInstance(blockchain, credentials);
        Map<Feature, IERC> features = wrapper != null?wrapper.loadFeatures(contractCls, contractAddress, Feature.IERC2612, Feature.IERC20):null;
        return wrapper != null && features != null?new Pair<Wrapper, Map<Feature, IERC>>(wrapper, features):null;
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

    //------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        String privateKey = "", infuraKey = "", contractAddressToLoad = "";
        Class contractClsToDeploy = null;

        Blockchain blockchain = RINKEBY_INFURA(infuraKey);
        Credentials credentials = privateKey(privateKey);
        wrapper(blockchain, credentials, contractClsToDeploy, contractAddressToLoad);
    }

    //------------------------------------------------------------------------------------------------------------------

}


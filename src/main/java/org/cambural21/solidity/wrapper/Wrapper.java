package org.cambural21.solidity.wrapper;

import org.cambural21.solidity.wrapper.contracts.EIP20;
import org.cambural21.solidity.wrapper.contracts.EIPSpecial;
import org.cambural21.solidity.wrapper.contracts.EIPStandard;
import org.cambural21.solidity.wrapper.interfaces.IERC;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.slf4j.impl.SimpleLoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class Wrapper extends BaseWrapper {



    private final String blockchainName;
    private final long chainId;

    private Wrapper(Web3j web3j, String blockchainName, long chainId, Credentials credentials, LoggingLevel lvl) {
        super(web3j, credentials);
        this.blockchainName = blockchainName;
        this.chainId = chainId>=0?chainId:-1;
        setLoggingLevel(lvl);
    }

    //******************************************************************************************************************

    private static <T extends org.web3j.tx.Contract> T invokeLoad(Class<T> clazz, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider){
        T t = null;
        if(clazz != null && contractAddress != null && !contractAddress.isEmpty() && web3j != null && transactionManager != null && gasProvider != null){
            try{
                Method method = clazz.getMethod("load", String.class, Web3j.class, TransactionManager.class, ContractGasProvider.class);
                t = (T) method.invoke(null, contractAddress, web3j, transactionManager, gasProvider);
            }catch (Exception e){
                e.printStackTrace();
                t = null;
            }
        }
        return t;

    }

    private static <T extends org.web3j.tx.Contract> T invokeDeploy(Class<T> clazz, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider, boolean async){
        T t = null;
        if(clazz != null && web3j != null && transactionManager != null && gasProvider != null){
            try{
                Method method = clazz.getMethod("deploy", Web3j.class, TransactionManager.class, ContractGasProvider.class);
                RemoteCall<T> remoteCall = (RemoteCall<T>) method.invoke(null, web3j, transactionManager, gasProvider);
                t = async?remoteCall.sendAsync().get():remoteCall.send();
            }catch (Exception e){
                e.printStackTrace();
                t = null;
            }
        }
        return t;
    }

    private void fillMap(final WrapperExecutor executor, final Map<Feature, IERC> MAP, final Feature... features) {
        for (Feature feature: features) {
            switch (feature){
                //------------------------------------------------------------------------------------------------------
                case IERC20Paused:{
                    MAP.put(feature, executor.getIERC20Paused());
                } break;
                case IERC20Backed:{
                    MAP.put(feature, executor.getIERC20Backed());
                } break;
                //------------------------------------------------------------------------------------------------------
                case IERC2771:{
                    MAP.put(feature, executor.getIERC2771());
                } break;
                case IERC2612:{
                    MAP.put(feature, executor.getIERC2612());
                } break;
                case IERC1046:{
                    MAP.put(feature, executor.getIERC1046());
                } break;
                case IERC1820:{
                    MAP.put(feature, executor.getIERC1820());
                } break;
                case UNISWAP:{
                    MAP.put(feature, executor.getUNISWAP());
                } break;
                case IERC173:{
                    MAP.put(feature, executor.getIERC173());
                } break;
                case IERC165:{
                    MAP.put(feature, executor.getIERC165());
                } break;
                //------------------------------------------------------------------------------------------------------
                case IERC20:{
                    MAP.put(feature, executor.getIERC20());
                } break;
            }
        }
    }

    private <T extends Contract> EIPStandard loadEIPStandard(Class<T> classContract, String contractAddress){
        return contractAddress != null && !contractAddress.isEmpty()?EIPStandard.load(classContract, contractAddress, getWeb3j(), getTransactionManager(), getGasProvider()):null;
    }

    private <T extends Contract> EIPSpecial loadEIPSpecial(Class<T> classContract, String contractAddress){
        return contractAddress != null && !contractAddress.isEmpty()? EIPSpecial.load(classContract, contractAddress, getWeb3j(), getTransactionManager(), getGasProvider()):null;
    }

    private <T extends Contract> EIP20 loadEIP20(Class<T> classContract, String contractAddress){
        return contractAddress != null && !contractAddress.isEmpty()?EIP20.load(classContract, contractAddress, getWeb3j(), getTransactionManager(), getGasProvider()):null;
    }

    //******************************************************************************************************************

    public static Wrapper getInstance(Blockchain blockchain, Credentials credentials){
        Wrapper helper = null;
        if(blockchain != null && credentials != null){
            try{
                LoggingLevel loggingLevel = blockchain.getLoggingLevel();
                loggingLevel = loggingLevel != null?loggingLevel:LoggingLevel.debug;
                Web3j web3j = blockchain.isInfura()?createInfuraService(blockchain.getRPC()):createService(blockchain.getRPC());
                helper = new Wrapper(web3j, blockchain.getName(), blockchain.getChainID(), credentials, loggingLevel);
            }catch (Exception e){
                e.printStackTrace();
                helper = null;
            }
        }
        return helper;
    }

    public String getBlockchainName() {
        return blockchainName;
    }

    private TransactionManager getTransactionManager()  {
        return new RawTransactionManager(getWeb3j(), getCredentials(), getChainId());
    }

    private void setLoggingLevel(LoggingLevel lvl) {
        if(lvl != null){
            try{
                String logLevel = null;
                switch (lvl){
                    case trace: {
                        logLevel = "trace";
                    } break;
                    case debug: {
                        logLevel = "debug";
                    } break;
                    case info: {
                        logLevel = "info";
                    } break;
                    case warning: {
                        logLevel = "warning";
                    } break;
                    case error: {
                        logLevel = "error";
                    } break;
                }
                System.setProperty(org.slf4j.impl.SimpleLogger.SHOW_THREAD_NAME_KEY, "false");
                System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, logLevel.toLowerCase());
                Field field = SimpleLogger.class.getDeclaredField("INITIALIZED");
                field.setAccessible(true);
                field.set(null, false);
                Method method = SimpleLoggerFactory.class.getDeclaredMethod("reset");
                method.setAccessible(true);
                method.invoke(LoggerFactory.getILoggerFactory());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void shutdown()  {
        getWeb3j().shutdown();
    }

    public long getChainId() {
        return chainId;
    }

    public String getPublicAddress(){
        return "0x"+getCredentials().getEcKeyPair().getPublicKey().toString(16);
    }

    public ECKeyPair getECKeyPair(){
        return getCredentials().getEcKeyPair();
    }

    public String getAddress(){
        return getCredentials().getAddress();
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "address='" + getAddress() + '\'' +
                ", blockchain='" + blockchainName + '\'' +
                ", chainId=" + chainId +
                '}';
    }

    //******************************************************************************************************************

    public <T extends Contract> Map<Feature, IERC> loadFeatures(Class<T> classContract, String contractAddress, Feature... features){
        final Map<Feature, IERC> MAP = new HashMap<>();
        if(classContract != null && contractAddress != null && !contractAddress.isEmpty() && features.length>0){
            {
                boolean base = false, standard = false, special = false;

                for (Feature feature: features) {
                    if(feature.isBase() && !base) base = true;
                    else if(feature.isSpecial() && !special) special = true;
                    else if(feature.isStandard() && !standard) standard = true;
                }

                if(special){
                    EIPSpecial eip = loadEIPSpecial(classContract, contractAddress);
                    WrapperExecutor executor = new WrapperExecutor(eip);
                    fillMap(executor, MAP, features);
                }
                else if(standard){
                    EIPStandard eip = loadEIPStandard(classContract, contractAddress);
                    WrapperExecutor executor = new WrapperExecutor(eip);
                    fillMap(executor, MAP, features);
                }
                else if(base){
                    EIP20 eip = loadEIP20(classContract, contractAddress);
                    WrapperExecutor executor = new WrapperExecutor(eip);
                    fillMap(executor, MAP, features);
                }
            }
        }
        return MAP;
    }

    public <T extends org.web3j.tx.Contract> T load(Class<T> clazz, String contractAddress){
        T t = null;
        if(clazz != null && contractAddress != null && !contractAddress.isEmpty()) t = invokeLoad(clazz, contractAddress, getWeb3j(), getTransactionManager(), getGasProvider());
        return t;
    }

    public <T extends org.web3j.tx.Contract> T deploy(Class<T> clazz, boolean async){
        T t = null;
        if(clazz != null) t = invokeDeploy(clazz, getWeb3j(), getTransactionManager(), getGasProvider(), async);
        return t;
    }

    //******************************************************************************************************************


}

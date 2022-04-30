package org.cambural21.solidity.wrapper.contracts;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.lang.reflect.Field;
import java.math.BigInteger;

public abstract class EIPBase extends Contract {

    @Deprecated
    protected EIPBase(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EIPBase(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasProvider);
    }

    @Deprecated
    protected EIPBase(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EIPBase(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, gasProvider);
    }

    protected static <T extends Contract> String extractBINARY(Class<T> classContract){
        String BINARY = null;
        if(classContract != null){
            try{
                Field field = classContract.getDeclaredField("BINARY");
                field.setAccessible(true);
                String str = (String) field.get(null);
                if(str != null & !str.isEmpty()) BINARY = str;
                field.setAccessible(false);
            }catch (Exception e){
                BINARY = null;
            }
            if(BINARY == null || BINARY.isEmpty()) throw new NullPointerException("BINARY is NULL");
        }
        return BINARY;
    }

    //------------------------------------------------------------------------------------------------------------------

}

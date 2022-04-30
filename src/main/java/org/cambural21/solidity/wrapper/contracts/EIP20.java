package org.cambural21.solidity.wrapper.contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class EIP20 extends EIPBase {

    public static final String FUNC_DECREASEALLOWANCE = "decreaseAllowance";
    public static final String FUNC_INCREASEALLOWANCE = "increaseAllowance";
    public static final String FUNC_TRANSFERFROM = "transferFrom";
    public static final String FUNC_TOTALSUPPLY = "totalSupply";
    public static final String FUNC_ALLOWANCE = "allowance";
    public static final String FUNC_BALANCEOF = "balanceOf";
    public static final String FUNC_TRANSFER = "transfer";
    public static final String FUNC_DECIMALS = "decimals";
    public static final String FUNC_APPROVE = "approve";
    public static final String FUNC_SYMBOL = "symbol";
    public static final String FUNC_NAME = "name";

    @Deprecated
    protected <T extends Contract> EIP20(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(extractBINARY(classContract), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected <T extends Contract> EIP20(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(extractBINARY(classContract), contractAddress, web3j, transactionManager, gasProvider);
    }

    @Deprecated
    protected <T extends Contract> EIP20(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(extractBINARY(classContract), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected <T extends Contract> EIP20(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(extractBINARY(classContract), contractAddress, web3j, credentials, gasProvider);
    }

    //------------------------------------------------------------------------------------------------------------------

    public static <T extends Contract> EIP20 load(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EIP20(classContract, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static <T extends Contract> EIP20 load(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EIP20(classContract, contractAddress, web3j, credentials, contractGasProvider);
    }

    public static <T extends Contract> RemoteCall<T> deploy(Class<T> classContract, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(classContract, web3j, transactionManager, contractGasProvider, extractBINARY(classContract), "");
    }

    public static <T extends Contract> RemoteCall<T> deploy(Class<T> classContract, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(classContract, web3j, credentials, contractGasProvider, extractBINARY(classContract), "");
    }

    //TODO DEPRECATED --------------------------------------------------------------------------------------------------

    @Deprecated
    public static <T extends Contract> EIP20 load(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EIP20(classContract, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static <T extends Contract> EIP20 load(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EIP20(classContract, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @Deprecated
    public static <T extends Contract> RemoteCall<T> deploy(Class<T> classContract, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(classContract, web3j, credentials, gasPrice, gasLimit, extractBINARY(classContract), "");
    }

    @Deprecated
    public static <T extends Contract> RemoteCall<T> deploy(Class<T> classContract, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(classContract, web3j, transactionManager, gasPrice, gasLimit, extractBINARY(classContract), "");
    }

    //TODO IERC20 ------------------------------------------------------------------------------------------------------

    public RemoteFunctionCall<TransactionReceipt> decreaseAllowance(String spender, BigInteger subtractedValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DECREASEALLOWANCE,
                Arrays.<Type>asList(new Address(160, spender),
                        new Uint256(subtractedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> increaseAllowance(String spender, BigInteger addedValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCREASEALLOWANCE,
                Arrays.<Type>asList(new Address(160, spender),
                        new Uint256(addedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    //todo solidity function transferFrom(address from, address to, uint256 amount) external returns (bool);
    public RemoteFunctionCall<Boolean> transferFrom(String from, String to, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new Address(160, from),
                        new Address(160, to), new Uint256(amount)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    //todo solidity  function approve(address spender, uint256 amount) external returns (bool);
    public RemoteFunctionCall<Boolean> approve(String spender, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new Address(160, spender),new Uint256(amount)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLOWANCE,
                Arrays.<Type>asList(new Address(160, owner),
                        new Address(160, spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    //todo solidity function transfer(address to, uint256 amount) external returns (bool);
    public RemoteFunctionCall<Boolean> transfer(String to, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(160, to), new Uint256(amount)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new Address(160, account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DECIMALS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

}

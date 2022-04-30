package org.cambural21.solidity.wrapper.contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class EIPSpecial extends EIPStandard {

    protected <T extends Contract> EIPSpecial(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(classContract, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    protected <T extends Contract> EIPSpecial(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(classContract, contractAddress, web3j, credentials, contractGasProvider);
    }

    public static <T extends Contract> EIPSpecial load(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EIPSpecial(classContract, contractAddress, web3j, credentials, contractGasProvider);
    }

    public static <T extends Contract> EIPSpecial load(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EIPSpecial(classContract, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    //TODO IERC20Backed ------------------------------------------------------------------------------------------------

    public static final String FUNC_GETERC20COUNTHOLDERS = "getERC20CountHolders";

    public static final String FUNC_UPDATEERC20BALANCES = "updateERC20Balances";

    public static final String FUNC_GETERC20BALANCES = "getERC20Balances";

    public static final String FUNC_GETERC20HOLDERS = "getERC20Holders";

    public RemoteFunctionCall<BigInteger> updateERC20Balances(List<String> holders_, List<BigInteger> balances_) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEERC20BALANCES,
                Arrays.<Type>asList(new DynamicArray<Address>(
                                Address.class,
                                org.web3j.abi.Utils.typeMap(holders_, Address.class)),
                        new DynamicArray<Uint256>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(balances_, Uint256.class))),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getERC20Holders(BigInteger from, BigInteger to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETERC20HOLDERS,
                Arrays.<Type>asList(new Uint256(from),
                        new Uint256(to)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getERC20Balances(List<String> _addresses) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETERC20BALANCES,
                Arrays.<Type>asList(new DynamicArray<Address>(
                        Address.class,
                        org.web3j.abi.Utils.typeMap(_addresses, Address.class))),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getERC20CountHolders() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETERC20COUNTHOLDERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    //TODO IERC20Paused ------------------------------------------------------------------------------------------------

    public static final String FUNC_ISERC20PAUSED = "isERC20Paused";

    public static final String FUNC_SETERC20PAUSED = "setERC20Paused";

    public RemoteFunctionCall<Boolean> isERC20Paused() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISERC20PAUSED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setERC20Paused(Boolean paused) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETERC20PAUSED,
                Arrays.<Type>asList(new Bool(paused)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    //TODO IERC1046 ----------------------------------------------------------------------------------------------------

    public static final String FUNC_UPDATETOKENURI = "updateTokenURI";

    public RemoteFunctionCall<TransactionReceipt> updateTokenURI(String tokenURI_) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATETOKENURI,
                Arrays.<Type>asList(new Utf8String(tokenURI_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    //TODO Test --------------------------------------------------------------------------------------------------------

    public static final String FUNC_TESTV1 = "TESTV1";

    //fixme
    public RemoteFunctionCall<byte[]> TESTV1(BigInteger id, BigInteger typeId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TESTV1,
                Arrays.<Type>asList(new Uint256(id), new Uint256(typeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public static final String FUNC_TESTV2 = "TESTV2";

    //fixme
    public RemoteFunctionCall<byte[]> TESTV2(BigInteger id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TESTV2,
                Arrays.<Type>asList(new Uint256(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    /*public RemoteFunctionCall<byte[]> TESTV1(BigInteger id, BigInteger typeId, boolean bytes32) {
        TypeReference<?> reference = bytes32?new TypeReference<Bytes32>() {}:new TypeReference<DynamicBytes>(){};
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TESTV1,
                Arrays.<Type>asList(new Uint256(id),
                        new Uint256(typeId)),
                Arrays.<TypeReference<?>>asList(reference));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }*/

    /*public RemoteFunctionCall<byte[]> TESTV2(BigInteger id, boolean bytes32) {
        TypeReference<?> reference = bytes32?new TypeReference<Bytes32>() {}:new TypeReference<DynamicBytes>(){};
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TESTV2,
                Arrays.<Type>asList(new Uint256(id)),
                Arrays.<TypeReference<?>>asList(reference));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }*/

}

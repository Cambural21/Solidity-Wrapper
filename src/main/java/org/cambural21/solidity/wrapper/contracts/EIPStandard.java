package org.cambural21.solidity.wrapper.contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
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

public class EIPStandard extends EIP20 {

    protected <T extends Contract> EIPStandard(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(classContract, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    protected <T extends Contract> EIPStandard(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(classContract, contractAddress, web3j, credentials, contractGasProvider);
    }

    public static <T extends Contract> EIPStandard load(Class<T> classContract, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EIPStandard(classContract, contractAddress, web3j, credentials, contractGasProvider);
    }

    public static <T extends Contract> EIPStandard load(Class<T> classContract, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EIPStandard(classContract, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    //TODO IERC173 -----------------------------------------------------------------------------------------------------

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    //TODO IERC2771 ----------------------------------------------------------------------------------------------------

    public static final String FUNC_ISTRUSTEDFORWARDER = "isTrustedForwarder";

    public RemoteFunctionCall<Boolean> isTrustedForwarder(String trustedForwarder) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISTRUSTEDFORWARDER,
                Arrays.<Type>asList(new Address(160, trustedForwarder)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    //TODO IERC1046 ----------------------------------------------------------------------------------------------------

    public static final String FUNC_TOKENURI = "tokenURI";

    public RemoteFunctionCall<String> tokenURI() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENURI,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    //TODO IERC165 -----------------------------------------------------------------------------------------------------

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    //TODO IERC1820 ----------------------------------------------------------------------------------------------------

    public static final String FUNC_CANIMPLEMENTINTERFACEFORADDRESS = "canImplementInterfaceForAddress";

    public RemoteFunctionCall<byte[]> canImplementInterfaceForAddress(byte[] interfaceHash, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CANIMPLEMENTINTERFACEFORADDRESS,
                Arrays.<Type>asList(new Bytes32(interfaceHash),
                        new Address(160, account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    //TODO IERC2612 ----------------------------------------------------------------------------------------------------

    public static final String FUNC_DOMAIN_SEPARATOR = "DOMAIN_SEPARATOR";

    public static final String FUNC_NONCES = "nonces";

    public static final String FUNC_permit = "permit";

    public RemoteFunctionCall<byte[]> DOMAIN_SEPARATOR() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DOMAIN_SEPARATOR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> nonces(String owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NONCES,
                Arrays.<Type>asList(new Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> permit(String _owner, String _spender, BigInteger _value, BigInteger _deadline, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_permit,
                Arrays.<Type>asList(new Address(160, _owner),
                        new Address(160, _spender),
                        new Uint256(_value),
                        new Uint256(_deadline),
                        new Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    //TODO UNISWAP -----------------------------------------------------------------------------------------------------

    public static final String FUNC_SELFPERMIT = "selfPermit";

    public static final String FUNC_SELFPERMITALLOWED = "selfPermitAllowed";

    public static final String FUNC_SELFPERMITALLOWEDIFNECESSARY = "selfPermitAllowedIfNecessary";

    public static final String FUNC_SELFPERMITIFNECESSARY = "selfPermitIfNecessary";

    public RemoteFunctionCall<TransactionReceipt> selfPermit(String token, BigInteger value, BigInteger deadline, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SELFPERMIT,
                Arrays.<Type>asList(new Address(160, token),
                        new Uint256(value),
                        new Uint256(deadline),
                        new Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> selfPermitAllowed(String token, BigInteger nonce, BigInteger expiry, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SELFPERMITALLOWED,
                Arrays.<Type>asList(new Address(160, token),
                        new Uint256(nonce),
                        new Uint256(expiry),
                        new Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> selfPermitIfNecessary(String token, BigInteger value, BigInteger deadline, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SELFPERMITIFNECESSARY,
                Arrays.<Type>asList(new Address(160, token),
                        new Uint256(value),
                        new Uint256(deadline),
                        new Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> selfPermitAllowedIfNecessary(String token, BigInteger nonce, BigInteger expiry, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SELFPERMITALLOWEDIFNECESSARY,
                Arrays.<Type>asList(new Address(160, token),
                        new Uint256(nonce),
                        new Uint256(expiry),
                        new Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> permit(String holder, String spender, BigInteger nonce, BigInteger expiry, Boolean allowed, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_permit,
                Arrays.<Type>asList(new Address(160, holder),
                        new Address(160, spender),
                        new Uint256(nonce),
                        new Uint256(expiry),
                        new Bool(allowed),
                        new Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }
}

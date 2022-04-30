package org.cambural21.solidity.wrapper.interfaces;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public interface UNISWAP extends IERC {

    TransactionReceipt permit(String holder, String spender, BigInteger nonce, BigInteger expiry, Boolean allowed, BigInteger v, byte[] r, byte[] s) throws Exception;

    TransactionReceipt selfPermitAllowedIfNecessary(String token, BigInteger nonce, BigInteger expiry, BigInteger v, byte[] r, byte[] s) throws Exception;

    TransactionReceipt selfPermitIfNecessary(String token, BigInteger value, BigInteger deadline, BigInteger v, byte[] r, byte[] s) throws Exception;

    TransactionReceipt selfPermitAllowed(String token, BigInteger nonce, BigInteger expiry, BigInteger v, byte[] r, byte[] s) throws Exception;

    TransactionReceipt selfPermit(String token, BigInteger value, BigInteger deadline, BigInteger v, byte[] r, byte[] s) throws Exception;

}

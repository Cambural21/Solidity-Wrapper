package org.cambural21.solidity.wrapper.interfaces;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public interface IERC2612 extends IERC {

    TransactionReceipt permit(String _owner, String _spender, BigInteger _value, BigInteger _deadline, BigInteger v, byte[] r, byte[] s) throws Exception;

    BigInteger nonces(String owner) throws Exception;

    byte[] DOMAIN_SEPARATOR() throws Exception;

}

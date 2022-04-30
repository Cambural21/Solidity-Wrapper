package org.cambural21.solidity.wrapper.interfaces;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public interface IERC20 extends IERC {

    TransactionReceipt decreaseAllowance(String spender, BigInteger subtractedValue) throws Exception;

    TransactionReceipt increaseAllowance(String spender, BigInteger addedValue) throws Exception;

    Boolean transferFrom(String from, String to, BigInteger amount) throws Exception;

    Boolean transfer(String expender, BigInteger amount) throws Exception;

    Boolean approve(String expender, BigInteger amount) throws Exception;

    BigInteger allowance(String owner, String expender) throws Exception;

    BigInteger balanceOf(String account) throws Exception;

    BigInteger totalSupply() throws Exception;

    BigInteger decimals() throws Exception;

    String symbol() throws Exception;

    String name() throws Exception;

}

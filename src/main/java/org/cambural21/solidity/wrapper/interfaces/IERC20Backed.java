package org.cambural21.solidity.wrapper.interfaces;

import java.math.BigInteger;
import java.util.List;

public interface IERC20Backed extends IERC {

    BigInteger updateERC20Balances(List<String> holders_, List<BigInteger> balances) throws Exception;

    List<String>  getERC20Holders(BigInteger from, BigInteger to) throws Exception;

    List<BigInteger> getERC20Balances(List<String> addresses) throws Exception;

    BigInteger getERC20CountHolders() throws Exception;

}

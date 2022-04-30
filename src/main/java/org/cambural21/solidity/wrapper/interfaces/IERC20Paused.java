package org.cambural21.solidity.wrapper.interfaces;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface IERC20Paused extends IERC {

    TransactionReceipt setERC20Paused(Boolean paused) throws Exception;

    Boolean isERC20Paused() throws Exception;

}

package org.cambural21.solidity.wrapper.interfaces;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface IERC173 extends IERC {

    TransactionReceipt transferOwnership(String newOwner) throws Exception;

    TransactionReceipt renounceOwnership() throws Exception;

    String owner() throws Exception;

}

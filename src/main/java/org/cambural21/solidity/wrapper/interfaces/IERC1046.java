package org.cambural21.solidity.wrapper.interfaces;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface IERC1046 extends IERC {

    TransactionReceipt updateTokenURI(String tokenURI_) throws Exception;

    String tokenURI() throws Exception;
}

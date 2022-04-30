package org.cambural21.solidity.wrapper.interfaces;

public interface IERC165 extends IERC {

    Boolean supportsInterface(byte[] interfaceId) throws Exception;

}

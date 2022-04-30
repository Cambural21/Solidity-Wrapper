package org.cambural21.solidity.wrapper.interfaces;

public interface IERC1820 extends IERC {

    byte[] canImplementInterfaceForAddress(byte[] interfaceId, String account) throws Exception;

}

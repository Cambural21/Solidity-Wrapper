package org.cambural21.solidity.wrapper.interfaces;

public interface IERC2771 extends IERC {

    Boolean isTrustedForwarder(String trustedForwarder) throws Exception;

}

package org.cambural21.solidity.wrapper;

public interface Blockchain {

    LoggingLevel getLoggingLevel();

    boolean isInfura();

    long getChainID();

    String getName();

    String getRPC();

}

package org.cambural21.solidity.wrapper.credentials;

import org.cambural21.solidity.wrapper.Blockchain;
import org.web3j.crypto.Credentials;

public class CredentialManager {

    static {
        synch = new Object();
    }

    private CredentialManager() {}

    private static volatile CredentialManager manager;
    private Credentials credentials = null;
    private Blockchain blockchain = null;
    private static Object synch;

    public CredentialManager setCredentials(Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public CredentialManager setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
        return this;
    }

    public boolean hasCredentials() {
        return credentials != null;
    }

    public boolean hasBlockchain() {
        return blockchain != null;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public static CredentialManager getInstance() {
        CredentialManager localRef = manager;
        if (localRef == null) {
            synchronized (synch) {
                localRef = manager;
                if (localRef == null) manager = localRef = new CredentialManager();
            }
        }
        return localRef;
    }

}

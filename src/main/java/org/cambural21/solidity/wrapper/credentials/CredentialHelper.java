package org.cambural21.solidity.wrapper.credentials;

import org.cambural21.solidity.wrapper.Wrapper;
import org.web3j.crypto.*;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

public final class CredentialHelper {

    private CredentialHelper() {}

    //TODO -------------------------------------------------------------------------------------------------------------

    public static Credentials loadBip44(String mnemonic, String password){
        password = password != null && !password.isEmpty()?password.trim():"";
        Credentials credentials = null;
        if(mnemonic != null && !mnemonic.isEmpty()){
            try{
                final int[] path = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0, 0};
                byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
                credentials = load(seed, path);
            }catch (Exception e){
                e.printStackTrace();
                credentials = null;
            }
        }
        return credentials;
    }

    public static Credentials loadBip39(String mnemonic, String password){
        password = password != null && !password.isEmpty()?password.trim():"";
        Credentials credentials = null;
        if(mnemonic != null && !mnemonic.isEmpty()){
            try{
                credentials = WalletUtils.loadBip39Credentials(mnemonic, password);
            }catch (Exception e){
                e.printStackTrace();
                credentials = null;
            }
        }
        return credentials;
    }

    public static Credentials load(String privateKey, String publicKey){
        Credentials credentials = null;
        if(publicKey != null && !publicKey.isEmpty() && privateKey != null && !privateKey.isEmpty()){
            try{
                credentials = Credentials.create(privateKey, publicKey);
            } catch (Exception e){
                e.printStackTrace();
                credentials = null;
            }
        }
        return credentials;
    }

    public static Credentials load(byte[] seed, int[] path){
        Credentials credentials = null;
        if(seed != null && seed.length>0 && path != null && path.length>0){
            try{
                Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
                Bip32ECKeyPair childKeypair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, path);
                credentials = Credentials.create(childKeypair);
            }catch (Exception e){
                e.printStackTrace();
                credentials = null;
            }
        }
        return credentials;
    }

    public static Credentials load(ECKeyPair ecKeyPair){
        Credentials credentials = null;
        if(ecKeyPair != null){
            try{
                credentials = Credentials.create(ecKeyPair);
            }catch (Exception e){
                e.printStackTrace();
                credentials = null;
            }
        }
        return credentials;
    }

    public static Credentials load(String privateKey){
        Credentials credentials = null;
        if(privateKey != null && !privateKey.isEmpty()){
            try{
                credentials = Credentials.create(privateKey);
            }catch (Exception e){
                e.printStackTrace();
                credentials = null;
            }
        }
        return credentials;
    }

    public static String generateMnemonic() {
        String mnemonic = null;
        try{
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] buffer = new byte[32];
            for (int i = 0; i < Math.abs(random.nextInt(3000)); i++) random.nextBytes(buffer);
            mnemonic = MnemonicUtils.generateMnemonic(buffer);
        }catch (Exception e){
            e.printStackTrace();
            mnemonic = null;
        }
        return mnemonic;
    }

    //TODO -------------------------------------------------------------------------------------------------------------

    public static File generateWalletFile(String password, Credentials credentials, File destinationDirectory) throws CipherException, IOException {
        ECKeyPair ecKeyPair = credentials != null?credentials.getEcKeyPair():null;
        return generateWalletFile(password, ecKeyPair, destinationDirectory);
    }

    public static File generateWalletFile(String password, ECKeyPair ecKeyPair, File destinationDirectory) throws CipherException, IOException {
        return new File(WalletUtils.generateWalletFile(password, ecKeyPair, destinationDirectory,  false));
    }

    public static File generateWalletFile(String password, Wrapper wrapper, File destinationDirectory) throws CipherException, IOException {
        ECKeyPair ecKeyPair = wrapper != null?wrapper.getECKeyPair():null;
        return generateWalletFile(password, ecKeyPair, destinationDirectory);
    }

    public static Credentials loadCredentials(String password, File wallet) throws CipherException, IOException {
        return WalletUtils.loadCredentials(password,wallet.getAbsolutePath());
    }

    //TODO -------------------------------------------------------------------------------------------------------------

}

package org.cambural21.solidity.eip712;

import org.cambural21.solidity.wrapper.Wrapper;
import org.cambural21.solidity.wrapper.interfaces.IERC2612;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;

public final class EIP712 {

    private byte[] domainSeparator = null;
    private String addressContract = null;
    private BigInteger chainId = null;

    public EIP712() {}

    //------------------------------------------------------------------------------------------------------------------

    private static final String EIP_191_VERSION_E_HEADER = "Ethereum Signed Message:\n";
    public static final byte EIP_191_VERSION_BYTE_0x00 = 0x00;
    public static final byte EIP_191_VERSION_BYTE_0x01 = 0x01;
    public static final byte EIP_191_VERSION_BYTE_0x45 = 0x45;
    private static final byte EIP_191_PREFIX = 0x19;

    public enum TypeHash {
        DOMAIN, PERMIT, PERSON, MAIL, SWAP;
    }

    //------------------------------------------------------------------------------------------------------------------

    public static String ecrecover(byte[] message, Sign.SignatureData signatureData, boolean needToHash, boolean recoverAddress) {
        String found = recoverAddress?"0x0000000000000000000000000000000000000000":"0x0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        if(message != null && message.length>0 && signatureData != null){
            try{
                BigInteger R = Numeric.toBigInt(signatureData.getR());
                BigInteger S = Numeric.toBigInt(signatureData.getS());
                byte[] vRaw = signatureData.getV();
                final int header = vRaw[0] & 0xFF;
                // The header byte: 0x1B = first key with even y, 0x1C = first key with odd y,
                //                  0x1D = second key with even y, 0x1E = second key with odd y
                if (header < 27 || header > 34) throw new SignatureException(String.format("Header byte out of range: %d", header));
                int recId = header - 27;
                ECDSASignature sig = new ECDSASignature(R, S);
                BigInteger res = Sign.recoverFromSignature(recId, sig, needToHash?abi.keccak256(message):message);
                String addr = recoverAddress?"0x" + Keys.getAddress(res):"0x" + res.toString(16);
                found = addr != null && !addr.isEmpty()?addr:found;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return found;
    }

    private static byte[] _preSigningDataWithIntendedValidator(String address, byte[] message) throws SignatureException {
        if(message == null || message.length != 32) throw new SignatureException("message is NULL");
        if(address == null || address.isEmpty()) throw new SignatureException("address is NULL");
        byte[] result = new StringBuffer().append(EIP_191_PREFIX).append(EIP_191_VERSION_BYTE_0x00).append(address).append(message).toString().getBytes();
        return abi.keccak256(result);
    }

    private static byte[] _preSigningStructuredData(byte[] domainSeparator, byte[] message) throws SignatureException {
        if(domainSeparator == null || domainSeparator.length != 32) throw new SignatureException("domainSeparator is NULL");
        if(message == null || message.length != 32) throw new SignatureException("message is NULL");
        byte[] result = new StringBuffer().append(EIP_191_PREFIX).append(EIP_191_VERSION_BYTE_0x01).append(domainSeparator).append(message).toString().getBytes();
        return abi.keccak256(result);
    }

    private static byte[] _preSigningWithPersonalSignMessage(byte[] message) throws SignatureException {
        if(message == null || message.length != 32) throw new SignatureException("Empty message not allowed for version E");
        int digits = 0, length = 0;
        while (length != 0) {
            digits++;
            length /= 10;
        }
        byte[] lengthAsText = new byte[digits];
        length = message.length;
        int index = digits - 1;
        while (length != 0) {
            int uint8 =  48 + length % 10;
            byte bytes1 = (byte)uint8;
            lengthAsText[index--] = bytes1;
            length /= 10;
        }
        byte[] result = new StringBuffer().append(EIP_191_PREFIX).append(EIP_191_VERSION_E_HEADER).append(lengthAsText).append(message).toString().getBytes();
        return abi.keccak256(result);
    }

    public static Sign.SignatureData sign(byte[] message, ECKeyPair ecKeyPair, boolean needToHash){
        Sign.SignatureData signature = null;
        if(message != null && message.length>0 && ecKeyPair != null){
            try{
                signature = Sign.signMessage(message, ecKeyPair, needToHash);
            }catch (Exception e){
                e.printStackTrace();
                signature = null;
            }
        }
        return signature;
    }

    /** Signature byte 	Signature type 	Signature verification
     * 0x00 	Illegal 	None
     * 0x01 	EIP191  	ecrecover
     * 0x02 	EIP1271 	isValidSignature(bytes, bytes)
     * 0x03 	EIP1654 	isValidSignature(bytes32, bytes)
     */
    public byte[] preSigning(byte signatureByte, byte[] message) {
        byte[] data = null;
        try{
            switch (signatureByte){
                case EIP_191_VERSION_BYTE_0x00: {
                    if(this.addressContract == null || this.addressContract.isEmpty()) throw new NullPointerException("addressContract is null");
                    data = _preSigningDataWithIntendedValidator(this.addressContract, message);
                } break;
                case EIP_191_VERSION_BYTE_0x01: {
                    if(this.domainSeparator == null || this.domainSeparator.length == 0) throw new NullPointerException("domainSeparator is null");
                    data = _preSigningStructuredData(this.domainSeparator, message);
                } break;
                case EIP_191_VERSION_BYTE_0x45: {
                    data = _preSigningWithPersonalSignMessage(message);
                } break;
                default: throw new SignatureException("Unsupported version");
            }
        }catch (Exception e){
            e.printStackTrace();
            data = null;
        }
        return data;
    }

     //------------------------------------------------------------------------------------------------------------------

    public boolean setDomainSeparator(String name, String version, byte chainId, String verifyingContract) {
        return setDomainSeparator(new EIP712Domain(name, version, chainId, verifyingContract));
    }

    public boolean setDomainSeparator(byte[] domainSeparator, long chainId, String verifyingContract) {
        this.domainSeparator = domainSeparator != null && domainSeparator.length ==32 ?domainSeparator:null;
        this.chainId = chainId>0?BigInteger.valueOf(chainId):null;
        if(verifyingContract != null && !verifyingContract.isEmpty()) setAddressContract(verifyingContract);
        else setAddressContract(null);
        return this.domainSeparator != null && this.domainSeparator.length == 32;
    }

    public boolean setDomainSeparator(EIP712Domain domain) {
        this.domainSeparator = domain != null?domain.hash().getValue():null;
        this.chainId = domain != null?domain.getChainId():null;
        if(domain != null) setAddressContract(domain.getVerifyingContract());
        else setAddressContract(null);
        return this.domainSeparator != null && this.domainSeparator.length == 32;
    }

    //------------------------------------------------------------------------------------------------------------------

    public boolean setAddressContract(String addressContract) {
        this.addressContract = addressContract;
        return this.addressContract != null && !this.addressContract.isEmpty();
    }

    public byte[] getDomainSeparator() {
        return domainSeparator;
    }

    public String getAddressContract() {
        return addressContract;
    }

    public BigInteger getChainId() {
        return chainId;
    }

    public String getHexDomainSeparator() {
        return domainSeparator != null && domainSeparator.length>0?abi.toHex(domainSeparator):null;
    }

    //------------------------------------------------------------------------------------------------------------------

    public IEIP2612Permit getEIP2612Permit(Credentials credentials, String owner, String spender, BigInteger nonce,
                                           BigInteger value, BigInteger deadline){
        return getEIP2612Permit(credentials != null?credentials.getEcKeyPair():null, owner, spender, nonce,value, deadline);
    }

    public IEIP2612Permit getEIP2612Permit(Wrapper wrapper, String owner, String spender, BigInteger nonce,
                                           BigInteger value, BigInteger deadline){
        return getEIP2612Permit(wrapper != null?wrapper.getECKeyPair():null, owner, spender, nonce,value, deadline);
    }

    public IEIP2612Permit getEIP2612Permit(ECKeyPair keyPair, String owner, String spender, BigInteger nonce,
                                           BigInteger value, BigInteger deadline){
        IEIP2612Permit result = null;
        if(keyPair != null && owner != null && !owner.isEmpty() && spender != null && !spender.isEmpty() && nonce != null && value != null && deadline != null){
            try{
                EIP2612Permit permit = new EIP2612Permit(owner, spender, nonce, value, deadline);
                byte[] permitHash = preSigning(EIP712.EIP_191_VERSION_BYTE_0x01, permit.hash().getValue());
                if(permitHash != null && permitHash.length == 32){
                    Sign.SignatureData signatureData = Sign.signMessage(permitHash, keyPair, false);
                    EIP712.VRS vrs = EIP712.getRSV(signatureData);
                    final BigInteger V = vrs.getV();
                    final byte[] R = vrs.getR();
                    final byte[] S = vrs.getS();
                    result = new IEIP2612Permit() {
                        @Override
                        public BigInteger getDeadline() {
                            return deadline;
                        }

                        @Override
                        public BigInteger getValue() {
                            return null;
                        }

                        @Override
                        public BigInteger getNonce() {
                            return nonce;
                        }

                        @Override
                        public String getSpender() {
                            return spender;
                        }

                        @Override
                        public String getOwner() {
                            return owner;
                        }

                        @Override
                        public BigInteger getV() {
                            return V;
                        }
                        @Override
                        public byte[] getR() {
                            return R;
                        }
                        @Override
                        public byte[] getS() {
                            return S;
                        }
                        @Override
                        public String toString() {
                            return "IEIP2612Permit: " + (abi.toHex(permitHash));
                        }
                    };
                }
            }catch (Exception e){
                e.printStackTrace();
                result = null;
            }
        }
        return result;
    }

    public static EIP712 getEIP712(String name, String version, long chainID, String contractAddress){
        EIP712 eip712 = null;
        if(name != null && !name.isEmpty() && contractAddress != null && !contractAddress.isEmpty()){
            eip712 = new EIP712();
            eip712.setDomainSeparator(new EIP712Domain(name, version, chainID, contractAddress));
        }
        return eip712;
    }

    public static VRS getRSV(Sign.SignatureData signatureData){
        VRS rsv = null;
        if(signatureData != null){
            try{
                final byte[] S = signatureData.getS();
                final byte[] R = signatureData.getR();
                final BigInteger V = Numeric.toBigInt(signatureData.getV());
                final BytesBuilder buffer = new BytesBuilder().append(R).append(S).append(signatureData.getV()[0]);
                if(S != null && S.length>0 && R != null && R.length>0 && buffer.size() == 65){
                    rsv = new VRS() {
                        @Override
                        public byte[] getSignature() {
                            return buffer.toByteArray();
                        }
                        @Override
                        public byte[] getR() {
                            return R;
                        }
                        @Override
                        public byte[] getS() {
                            return S;
                        }
                        @Override
                        public BigInteger getV() {
                            return V;
                        }
                        @Override
                        public String toString() {
                            return "VRS";
                        }
                    };
                }
            }catch (Exception e){
                e.printStackTrace();
                rsv = null;
            }
        }
        return rsv;
    }

    public interface IEIP2612Permit {
        BigInteger getDeadline();
        BigInteger getValue();
        BigInteger getNonce();
        String getSpender();
        String getOwner();
        BigInteger getV();
        byte[] getR();
        byte[] getS();
    }

    public interface VRS {
        byte[] getSignature();
        BigInteger getV();
        byte[] getR();
        byte[] getS();
    }

}

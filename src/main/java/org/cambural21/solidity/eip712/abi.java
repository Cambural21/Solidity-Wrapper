package org.cambural21.solidity.eip712;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.web3j.abi.datatypes.Type.MAX_BYTE_LENGTH;

public final class abi {

    private abi(){}

    //------------------------------------------------------------------------------------------------------------------

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static byte[] reverse(byte a[], int n) {
        byte[] b = new byte[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
        return b;
    }

    public static String toHex(byte[] array){
        return array != null && array.length>0?abi.toHex(array):null;
    }

    //------------------------------------------------------------------------------------------------------------------

    public static byte[] encodePacked(Type... types){
        BytesBuilder builder = new BytesBuilder();
        for (Type type: types) builder.append(encodePacked(type));
        int size = builder.size();
        return size>0?builder.toByteArray():null;
    }

    public static byte[] encodePacked(Type type){
        byte[] out = null;
        if(type == null) throw new NullPointerException("type is null");
        if(type instanceof Address){
            String hex = ((Address)type).getValue().substring(2);
            out = hexStringToByteArray(hex);
        }
        else if (type instanceof Utf8String){
            out = ((Utf8String)type).getValue().getBytes(StandardCharsets.UTF_8);
            //out = "fixme".getBytes(StandardCharsets.UTF_8);//fixme
        }
        else if (type instanceof Bool){
            byte bool = (byte) (((Bool)type).getValue()?0x01:0x00);
            out = new byte[]{bool};
        }
        else if(type instanceof Uint8){
            byte b = ((Uint8)type).getValue().toByteArray()[0];
            out = new byte[]{b};
        }
        else if (type instanceof NumericType){
            BigInteger integer = ((NumericType)type).getValue();
            byte[] raw = integer.toByteArray();
            byte[] buffer = new byte[MAX_BYTE_LENGTH];
            System.arraycopy(raw, 0, buffer, 0, Math.min(raw.length, buffer.length));
            out = reverse(buffer, buffer.length);
        }
        else if (type instanceof BytesType){
            byte[] value = ((BytesType)type).getValue();
            int length = value.length;
            int mod = length % MAX_BYTE_LENGTH;
            byte[] dest;
            if (mod != 0) {
                int padding = MAX_BYTE_LENGTH - mod;
                dest = new byte[length + padding];
                System.arraycopy(value, 0, dest, 0, length);
            } else {
                dest = value;
            }
            out = dest;
        }
        return out;
    }

    //------------------------------------------------------------------------------------------------------------------

    public static byte[] keccak256(String message){
        return keccak256(message.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] keccak256(byte[] message){
        return Hash.sha3(message);
    }

    //------------------------------------------------------------------------------------------------------------------

    public static byte[] encode(Type... types){
        BytesBuilder builder = new BytesBuilder();
        for (Type type: types) builder.append(encode(type));
        int size = builder.size();
        return size>0?builder.toByteArray():null;
    }

    public static byte[] encode(Type type){
        byte[] out = null;
        if(type == null) throw new NullPointerException("type is null");
        if(type instanceof Address){
            String hex = ((Address)type).getValue().substring(2);
            byte[] raw = hexStringToByteArray(hex);
            byte[] buffer = new byte[MAX_BYTE_LENGTH];
            int spaces = buffer.length-raw.length;
            System.arraycopy(raw, 0, buffer, spaces, Math.min(raw.length, buffer.length));
            out = buffer;
        }
        else if (type instanceof Utf8String){
            String text = FunctionEncoder.encodeConstructor(Arrays. <Type> asList(type));
            out = hexStringToByteArray(text);
        }
        else if (type instanceof Bool){
            Boolean bool = ((Bool)type).getValue();
            byte[] buffer = new byte[32];
            buffer[0] = (byte) (bool?0x01:0x00);
            out = reverse(buffer, buffer.length);
        }
        else if (type instanceof NumericType){
            BigInteger integer = ((NumericType)type).getValue();
            byte[] raw = integer.toByteArray();
            byte[] buffer = new byte[MAX_BYTE_LENGTH];
            System.arraycopy(raw, 0, buffer, 0, Math.min(raw.length, buffer.length));
            out = reverse(buffer, buffer.length);
        }
        else if (type instanceof BytesType){
            byte[] value = ((BytesType)type).getValue();
            int length = value.length;
            int mod = length % MAX_BYTE_LENGTH;
            byte[] dest;
            if (mod != 0) {
                int padding = MAX_BYTE_LENGTH - mod;
                dest = new byte[length + padding];
                System.arraycopy(value, 0, dest, 0, length);
            } else {
                dest = value;
            }
            out = dest;
        }
        return out;
    }

    //------------------------------------------------------------------------------------------------------------------
}

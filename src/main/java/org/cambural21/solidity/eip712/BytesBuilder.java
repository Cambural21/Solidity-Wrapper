package org.cambural21.solidity.eip712;

import java.io.ByteArrayOutputStream;

public final class BytesBuilder {

    private final ByteArrayOutputStream buffer;

    public BytesBuilder(){
        buffer = new ByteArrayOutputStream();
    }

    public BytesBuilder append(byte[] bytes){
        try{
            buffer.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public BytesBuilder append(byte b){
        try{
            buffer.write(new byte[]{b});
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public BytesBuilder append(int b){
        try{
            buffer.write(new byte[]{(byte)b});
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public byte[] toByteArray(){
        return buffer.size()>0?buffer.toByteArray():null;
    }

    public int size(){
        return buffer.size();
    }
}

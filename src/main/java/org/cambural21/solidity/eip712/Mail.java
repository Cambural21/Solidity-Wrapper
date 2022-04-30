package org.cambural21.solidity.eip712;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;

public class Mail extends SolidityEncoder {

    private Person from;
    private Person to;
    private String contents;

    public Mail(Person from, Person to, String contents){
        if(from == null) throw new NullPointerException("from is null");
        else if(to == null) throw new NullPointerException("to is null");
        else if(contents == null || contents.isEmpty()) throw new NullPointerException("contents is null");
        this.contents = contents;
        this.from = from;
        this.to = to;
    }

    @Override
    public Bytes32 hash() {
        Bytes32 hash = null;
        try{//EIP_712_MAIL
            hash = keccak256(abi.encode(
                    getTypeHash(EIP712.TypeHash.MAIL),
                    from.hash(),
                    to.hash(),
                    keccak256(contents))
            );
        }catch (Exception e){
            e.printStackTrace();
            hash = null;
        }
        return hash;
    }
}
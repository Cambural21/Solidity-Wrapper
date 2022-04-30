package org.cambural21.solidity.eip712;

import org.web3j.abi.datatypes.generated.Bytes32;

import java.math.BigInteger;

public abstract class SolidityEncoder implements SolidityStruct {

    //------------------------------------------------------------------------------------------------------------------

    protected Bytes32 getTypeHash(EIP712.TypeHash typeHash){
        Bytes32 hash = null;
        if(typeHash != null){
            switch (typeHash){
                case DOMAIN:{
                    hash = keccak256("EIP712Domain(string name,string version,uint256 chainId,address verifyingContract)");
                } break;
                case PERMIT:{
                    hash = keccak256("Permit(address owner,address spender,uint256 value,uint256 nonce,uint256 deadline)");
                } break;
                case PERSON:{
                    hash = keccak256("Person(string name,address wallet)");
                } break;
                case MAIL:{
                    hash = keccak256("Mail(Person from,Person to,string contents)");
                } break;
                case SWAP:{
                    hash = keccak256("EIPxSWAP(string symbol,address buyer,address seller,uint256 expend,uint256 received,uint256 deadline)");
                } break;
            }
        }
        return hash;
    }

    protected Bytes32 keccak256(String text){
        return new Bytes32(abi.keccak256(text));
    }

    protected Bytes32 keccak256(byte[] data){
        return new Bytes32(abi.keccak256(data));
    }

    public String toHex(){
        Bytes32 hash = hash();
        return hash != null && hash.getValue() != null && hash.getValue().length>0?abi.toHex(hash.getValue()):null;
    }

    //------------------------------------------------------------------------------------------------------------------

}

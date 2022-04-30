package org.cambural21.solidity.eip712;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Bytes32;

import java.math.BigInteger;

public class EIP2612Permit extends SolidityEncoder {

    private BigInteger nonce;
    private String owner;
    private String spender;
    private BigInteger value;
    private BigInteger deadline;

    public EIP2612Permit(String owner, String spender, BigInteger nonce, BigInteger value, BigInteger deadline){
        if(owner == null) throw new NullPointerException("owner is null");
        else if(spender == null) throw new NullPointerException("spender is null");
        this.nonce = nonce;
        this.owner = owner;
        this.spender = spender;
        this.value = value;
        this.deadline = deadline;
    }

    @Override
    public Bytes32 hash() {
        Bytes32 hash = null;
        try{//EIP_712_PERMIT
            hash = keccak256(abi.encode(
                    getTypeHash(EIP712.TypeHash.PERMIT),
                    new Address(160, owner),
                    new Address(160, spender),
                    new Uint(value), new Uint(nonce), new Uint(deadline))
            );
        }catch (Exception e){
            e.printStackTrace();
            hash = null;
        }
        return hash;
    }

}
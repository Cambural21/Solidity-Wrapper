package org.cambural21.solidity.eip712;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;

public final class Person extends SolidityEncoder {

    private String wallet;
    private String name;

    public Person (String name, String wallet) {
        if(name == null && name.isEmpty()) throw new NullPointerException("name is null");
        else if(wallet == null && wallet.isEmpty()) throw new NullPointerException("wallet is null");
        this.wallet = wallet;
        this.name = name;
    }

    @Override
    public Bytes32 hash() {
        Bytes32 hash = null;
        try{//EIP_712_PERSON
            hash = keccak256(abi.encode(
                    getTypeHash(EIP712.TypeHash.PERSON),
                    keccak256(name),
                    new Utf8String(wallet))
            );
        }catch (Exception e){
            e.printStackTrace();
            hash = null;
        }
        return hash;
    }

}

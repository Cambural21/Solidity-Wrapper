package org.cambural21.solidity.eip712;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

public class EIP2612Swap extends SolidityEncoder {

    private String symbol;
    private String buyer;
    private String seller;
    private BigInteger expend;
    private BigInteger received;
    private BigInteger deadline;

    public EIP2612Swap(String symbol, String buyer, String seller, BigInteger expend, BigInteger received, BigInteger deadline){
        if(symbol == null || symbol.isEmpty()) throw new NullPointerException("symbol is null");
        else if(buyer == null || buyer.isEmpty()) throw new NullPointerException("buyer is null");
        else if(seller == null || seller.isEmpty()) throw new NullPointerException("seller is null");
        this.symbol = symbol;
        this.buyer = buyer;
        this.seller = seller;
        this.expend = expend;
        this.received = received;
        this.deadline = deadline;
    }

    @Override
    public Bytes32 hash() {
        Bytes32 hash = null;
        try{//EIP_712_PERMIT
            hash = keccak256(abi.encode(
                    getTypeHash(EIP712.TypeHash.SWAP),
                    new Utf8String(symbol),
                    new Address(160, buyer),
                    new Address(160, seller),
                    new Uint256(expend), new Uint256(received), new Uint256(deadline))
            );
        }catch (Exception e){
            e.printStackTrace();
            hash = null;
        }
        return hash;
    }
}
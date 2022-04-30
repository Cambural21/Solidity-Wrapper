package org.cambural21.solidity.eip712;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

public class EIP712Domain extends SolidityEncoder {

    private String name;
    private String version;
    private BigInteger chainId;
    private String contractAddress;

    public EIP712Domain(String name, String version, long chainId, String contractAddress){
        if(chainId <=0 ) throw new NullPointerException("chainId is null");
        else if(name == null || name.isEmpty()) throw new NullPointerException("name is null");
        else if(version == null || version.isEmpty()) throw new NullPointerException("version is null");
        else if(contractAddress == null || contractAddress.isEmpty()) throw new NullPointerException("contractAddress is null");
        this.name = name;
        this.version = version;
        this.contractAddress = contractAddress;
        this.chainId = BigInteger.valueOf(chainId);
    }

    public BigInteger getChainId() {
        return chainId;
    }

    @Override
    public Bytes32 hash() {
        Bytes32 hash = null;
        try{//DOMAIN_SEPARATOR v4

            hash = keccak256(abi.encode(
                    getTypeHash(EIP712.TypeHash.DOMAIN),
                    new Bytes32(abi.keccak256(name)),
                    new Bytes32(abi.keccak256(version)),
                    new Uint256(chainId),
                    new Address(160, contractAddress))
            );
        }catch (Exception e){
            e.printStackTrace();
            hash = null;
        }
        return hash;
    }

    public String getVerifyingContract() {
        return contractAddress;
    }
}
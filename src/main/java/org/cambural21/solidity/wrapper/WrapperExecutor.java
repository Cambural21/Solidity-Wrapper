package org.cambural21.solidity.wrapper;

import org.cambural21.solidity.wrapper.contracts.EIP20;
import org.cambural21.solidity.wrapper.contracts.EIPSpecial;
import org.cambural21.solidity.wrapper.contracts.EIPStandard;
import org.cambural21.solidity.wrapper.interfaces.*;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WrapperExecutor {

    private EIPStandard eipStandard = null;
    private EIPSpecial eipSpecial = null;
    private EIP20 eip20 = null;

    public WrapperExecutor(EIP20 eip20){
        this.eip20 = eip20;
        this.eipStandard = null;
        this.eipSpecial = null;
    }

    public WrapperExecutor(EIPStandard eipStandard){
        this.eip20 = eipStandard;
        this.eipStandard = eipStandard;
        this.eipSpecial = null;
    }

    public WrapperExecutor(EIPSpecial eipSpecial){
        this.eip20 = eipSpecial;
        this.eipStandard = eipSpecial;
        this.eipSpecial = eipSpecial;
    }

    //------------------------------------------------------------------------------------------------------------------

    private EIPStandard getEipStandard() {
        return eipStandard;
    }

    private EIPSpecial getEipSpecial() {
        return eipSpecial;
    }

    private EIP20 getEip20() {
        return eip20;
    }

    //------------------------------------------------------------------------------------------------------------------

    public final IERC20Paused getIERC20Paused(){
        return new IERC20Paused() {
            @Override
            public TransactionReceipt setERC20Paused(Boolean paused) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipSpecial().setERC20Paused(paused);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public Boolean isERC20Paused() throws Exception {
                RemoteFunctionCall<Boolean> remoteFunctionCall = getEipSpecial().isERC20Paused();
                CompletableFuture<Boolean> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String toString() {
                return "IERC165: ";
            }
        };
    }

    public final IERC20Backed getIERC20Backed(){
        return new IERC20Backed() {

            @Override
            public BigInteger updateERC20Balances(List<String> holders, List<BigInteger> balances) throws Exception {
                RemoteFunctionCall<BigInteger> remoteFunctionCall = getEipSpecial().updateERC20Balances(holders, balances);
                CompletableFuture<BigInteger> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public List<String> getERC20Holders(BigInteger from, BigInteger to) throws Exception {
                RemoteFunctionCall<List> remoteFunctionCall = getEipSpecial().getERC20Holders(from, to);
                CompletableFuture<List> send = remoteFunctionCall.sendAsync();
                List<String> holders = new ArrayList<>();
                for (Object o: send.get()) {
                    holders.add((String)o);
                }
                return holders;
            }

            @Override
            public List<BigInteger> getERC20Balances(List<String> addresses) throws Exception {
                RemoteFunctionCall<List> remoteFunctionCall = getEipSpecial().getERC20Balances(addresses);
                CompletableFuture<List> send = remoteFunctionCall.sendAsync();
                List<BigInteger> balances = new ArrayList<>();
                for (Object o: send.get()) {
                    if(o instanceof BigInteger) balances.add((BigInteger)o);
                }
                return balances;
            }

            @Override
            public BigInteger getERC20CountHolders() throws Exception {
                RemoteFunctionCall<BigInteger> remoteFunctionCall = getEipSpecial().getERC20CountHolders();
                CompletableFuture<BigInteger> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String toString() {
                return "IERC20Backed: ";
            }
        };
    }

    //------------------------------------------------------------------------------------------------------------------

    public final IERC2771 getIERC2771(){
        return new IERC2771() {
            @Override
            public Boolean isTrustedForwarder(String trustedForwarder) throws Exception {
                RemoteFunctionCall<Boolean> remoteFunctionCall = getEipStandard().isTrustedForwarder(trustedForwarder);
                CompletableFuture<Boolean> send = remoteFunctionCall.sendAsync();
                return send.get();
            }
            @Override
            public String toString() {
                return "IERC2771: ";
            }
        };
    }

    public final IERC2612 getIERC2612(){
        return new IERC2612() {
            @Override
            public TransactionReceipt permit(String _owner, String _spender, BigInteger _value, BigInteger _deadline, BigInteger v, byte[] r, byte[] s) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().permit(_owner, _spender, _value, _deadline, v, r, s);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public BigInteger nonces(String owner) throws Exception {
                RemoteFunctionCall<BigInteger> remoteFunctionCall = getEipStandard().nonces(owner);
                CompletableFuture<BigInteger> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public byte[] DOMAIN_SEPARATOR() throws Exception {
                RemoteFunctionCall<byte[]> remoteFunctionCall = getEipStandard().DOMAIN_SEPARATOR();
                CompletableFuture<byte[]> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String toString() {
                return "IERC2612: ";
            }
        };
    }

    public final IERC1046 getIERC1046(){
        return new IERC1046() {
            @Override
            public TransactionReceipt updateTokenURI(String tokenURI_) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipSpecial().updateTokenURI(tokenURI_);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String tokenURI() throws Exception {
                RemoteFunctionCall<String> remoteFunctionCall = getEipStandard().tokenURI();
                CompletableFuture<String> send = remoteFunctionCall.sendAsync();
                return send.get();
            }
            @Override
            public String toString() {
                return "IERC1046: ";
            }
        };
    }

    public final IERC1820 getIERC1820(){
        return new IERC1820() {
            @Override
            public byte[] canImplementInterfaceForAddress(byte[] interfaceId, String account) throws Exception {
                RemoteFunctionCall<byte[]> remoteFunctionCall = getEipStandard().canImplementInterfaceForAddress(interfaceId, account);
                CompletableFuture<byte[]> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String toString() {
                return "IERC1820: ";
            }
        };
    }

    public final UNISWAP getUNISWAP(){
        return new UNISWAP() {
            @Override
            public TransactionReceipt permit(String holder, String spender, BigInteger nonce, BigInteger expiry, Boolean allowed, BigInteger v, byte[] r, byte[] s) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().permit(holder, spender, nonce, expiry, allowed, v, r, s);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public TransactionReceipt selfPermitAllowedIfNecessary(String token, BigInteger nonce, BigInteger expiry, BigInteger v, byte[] r, byte[] s) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().selfPermitAllowedIfNecessary(token, nonce, expiry, v, r, s);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public TransactionReceipt selfPermitIfNecessary(String token, BigInteger value, BigInteger deadline, BigInteger v, byte[] r, byte[] s) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().selfPermitAllowed(token, value, deadline, v, r, s);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public TransactionReceipt selfPermitAllowed(String token, BigInteger nonce, BigInteger expiry, BigInteger v, byte[] r, byte[] s) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().selfPermitAllowed(token, nonce, expiry, v, r, s);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public TransactionReceipt selfPermit(String token, BigInteger value, BigInteger deadline, BigInteger v, byte[] r, byte[] s) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().selfPermit(token, value, deadline, v, r, s);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String toString() {
                return "UNISWAP: ";
            }
        };
    }

    public final IERC173 getIERC173(){
        return new IERC173() {
            @Override
            public TransactionReceipt transferOwnership(String newOwner) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().transferOwnership(newOwner);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public TransactionReceipt renounceOwnership() throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEipStandard().renounceOwnership();
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String owner() throws Exception {
                RemoteFunctionCall<String> remoteFunctionCall = getEipStandard().owner();
                CompletableFuture<String> send = remoteFunctionCall.sendAsync();
                return send.get();
            }
            @Override
            public String toString() {
                return "IERC173: ";
            }
        };
    }

    public final IERC165 getIERC165(){
        return new IERC165() {
            @Override
            public Boolean supportsInterface(byte[] interfaceId) throws Exception {
                RemoteFunctionCall<Boolean> remoteFunctionCall = getEipStandard().supportsInterface(interfaceId);
                CompletableFuture<Boolean> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String toString() {
                return "IERC165: ";
            }
        };
    }

    public final IERC20 getIERC20(){
        return new IERC20() {
            @Override
            public TransactionReceipt decreaseAllowance(String spender, BigInteger subtractedValue) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEip20().decreaseAllowance(spender, subtractedValue);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public TransactionReceipt increaseAllowance(String spender, BigInteger addedValue) throws Exception {
                RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = getEip20().increaseAllowance(spender, addedValue);
                CompletableFuture<TransactionReceipt> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public Boolean transferFrom(String from, String to, BigInteger amount) throws Exception {
                RemoteFunctionCall<Boolean> remoteFunctionCall = getEip20().transferFrom(from, to, amount);
                CompletableFuture<Boolean> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public Boolean transfer(String expender, BigInteger amount) throws Exception {
                RemoteFunctionCall<Boolean> remoteFunctionCall = getEip20().transfer(expender, amount);
                CompletableFuture<Boolean> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public Boolean approve(String expender, BigInteger amount) throws Exception {
                RemoteFunctionCall<Boolean> remoteFunctionCall = getEip20().approve(expender, amount);
                CompletableFuture<Boolean> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public BigInteger allowance(String owner, String expender) throws Exception {
                RemoteFunctionCall<BigInteger> remoteFunctionCall = getEip20().allowance(owner, expender);
                CompletableFuture<BigInteger> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public BigInteger balanceOf(String account) throws Exception {
                RemoteFunctionCall<BigInteger> remoteFunctionCall = getEip20().balanceOf(account);
                CompletableFuture<BigInteger> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public BigInteger totalSupply() throws Exception {
                RemoteFunctionCall<BigInteger> remoteFunctionCall = getEip20().totalSupply();
                CompletableFuture<BigInteger> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public BigInteger decimals() throws Exception {
                RemoteFunctionCall<BigInteger> remoteFunctionCall = getEip20().decimals();
                CompletableFuture<BigInteger> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String symbol() throws Exception {
                RemoteFunctionCall<String> remoteFunctionCall = getEip20().symbol();
                CompletableFuture<String> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String name() throws Exception {
                RemoteFunctionCall<String> remoteFunctionCall = getEip20().name();
                CompletableFuture<String> send = remoteFunctionCall.sendAsync();
                return send.get();
            }

            @Override
            public String toString() {
                return "IERC20: ";
            }
        };
    }

}

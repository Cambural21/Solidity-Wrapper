package org.cambural21.solidity.wrapper;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.infura.InfuraHttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public abstract class BaseWrapper {

    private final ContractGasProvider gasProvider;
    private final Credentials credentials;
    private final Web3j web3j;

    protected BaseWrapper(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) throws NullPointerException {

        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = gasProvider;
        if(web3j == null || credentials == null || gasProvider == null) throw new NullPointerException("Parameters are NULL");
    }

    protected BaseWrapper(Web3j web3j, Credentials credentials){
        this(web3j, credentials, new DefaultGasProvider());
    }

    //******************************************************************************************************************

    protected static Web3j createInfuraService(String rpc){
        Web3j web3j = null;
        if(rpc != null && !rpc.isEmpty()){
            try {
                web3j = Web3j.build(new InfuraHttpService(rpc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return web3j;
    }

    protected static Web3j createService(String rpc){
        Web3j web3j = null;
        if(rpc != null && !rpc.isEmpty()){
            try {
                web3j = Web3j.build(new HttpService(rpc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return web3j;
    }

    //******************************************************************************************************************

    public final ContractGasProvider getGasProvider() {
        return gasProvider;
    }

    public final Credentials getCredentials() {
        return credentials;
    }

    public final Web3j getWeb3j() {
        return web3j;
    }

    //******************************************************************************************************************



    //ERC20, ERC721

}

package org.cambural21.solidity.utils;

import org.cambural21.solidity.wrapper.Blockchain;
import org.sqlite.SQLiteConfig;
import org.web3j.abi.datatypes.Address;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sqlite {

    private Connection connection = null;

    public Sqlite(){}

    public final Connection getConnection() {
        return connection;
    }

    public final Connection open(File database){
        if(database != null){
            SQLiteConfig config = new SQLiteConfig();
            config.setSharedCache(true);
            config.enableRecursiveTriggers(true);
            try{
                connection = DriverManager.getConnection("jdbc:sqlite:"+database.getAbsolutePath(), config.toProperties());
            }catch (Exception e){
                e.printStackTrace();
                connection = null;
            }
        }
        return connection;
    }

    public final void close(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                connection = null;
            }
        }
    }

    //TODO Deploy --------------------------------------------------------------------------------

    public boolean existDeploy(Address publicAddress, Address addressContract, Blockchain blockchain)  {
        int TOTAL = 0;
        try{
            String SQL_SELECT = "SELECT COUNT(*) as TOTAL FROM contract_table WHERE publicAddress = '?' AND addressContract = '?' AND chainName = '?' AND chainRPC = '?' AND chainID = '?' LIMIT 1";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, publicAddress.getValue());
            preparedStatement.setString(2, addressContract.getValue());
            preparedStatement.setString(3, blockchain.getName());
            preparedStatement.setString(4, blockchain.getRPC());
            preparedStatement.setLong(5, blockchain.getChainID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                TOTAL = resultSet.getInt("TOTAL");
                break;
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return TOTAL >= 1;
    }

    public int addDeploy(Address publicAddress, Address addressContract, Blockchain blockchain) {
        int row = -1;
        try{
            String SQL_INSERT = "INSERT INTO contract_table (publicAddress, addressContract, chainName, chainRPC, chainID) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, publicAddress.getValue());
            preparedStatement.setString(2, addressContract.getValue());
            preparedStatement.setString(3, blockchain.getName());
            preparedStatement.setString(4, blockchain.getRPC());
            preparedStatement.setLong(5, blockchain.getChainID());
            row = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            row = -1;
        }
        return row;
    }

    public List<Address> listDeploy(Address publicAddress, Blockchain blockchain) {
        List<Address> list = new ArrayList<>();
        try{
            String SQL_SELECT = "SELECT addressContract FROM contract_table WHERE publicAddress = '?' AND chainName = '?' AND chainRPC = '?' AND chainID = '?' ORDER BY id ASC";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, publicAddress.getValue());
            preparedStatement.setString(2, blockchain.getName());
            preparedStatement.setString(3, blockchain.getRPC());
            preparedStatement.setLong(4, blockchain.getChainID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Address addressContract = new Address(160, resultSet.getString("addressContract"));
                if(!list.contains(addressContract)) list.add(addressContract);
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public int createDeploy() {
        int row = -1;
        try{
            String SQL_CREATE = "CREATE TABLE IF NOT EXISTS contract_table (id INTEGER PRIMARY KEY AUTOINCREMENT, publicAddress TEXT, addressContract TEXT)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_CREATE);
            row = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            row = -1;
        }
        return row;
    }

    //TODO Wallet ----------------------------------------------------------------------------------

    public boolean existWallet(Address addressWallet, File wallet)  {
        int TOTAL = 0;
        try{
            String SQL_SELECT = "SELECT COUNT(*) as TOTAL FROM wallet_table WHERE addressWallet = '?' AND wallet = '?' LIMIT 1";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, addressWallet.getValue());
            preparedStatement.setString(2, wallet.getAbsolutePath());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                TOTAL = resultSet.getInt("TOTAL");
                break;
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return TOTAL >= 1;
    }

    public int addWallet(Address addressWallet, File wallet) {
        int row = -1;
        try{
            String SQL_INSERT = "INSERT INTO wallet_table (addressWallet, wallet) VALUES (?,?,?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, addressWallet.getValue());
            preparedStatement.setString(2, wallet.getAbsolutePath());
            row = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            row = -1;
        }
        return row;
    }

    public Map<Address, File> listWallet() {
        Map<Address, File> map = new HashMap<>();
        try{
            String SQL_SELECT = "SELECT addressWallet, wallet FROM wallet_table ORDER BY id ASC";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Address addressWallet = new Address(160, resultSet.getString("addressWallet"));
                File wallet = new File(resultSet.getString("wallet"));
                if(!map.containsKey(addressWallet)) map.put(addressWallet, wallet);
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public int createWallet() {
        int row = -1;
        try{
            String SQL_CREATE = "CREATE TABLE IF NOT EXISTS wallet_table (id INTEGER PRIMARY KEY AUTOINCREMENT, addressWallet TEXT, wallet TEXT)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_CREATE);
            row = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            row = -1;
        }
        return row;
    }

    //TODO -----------------------------------------------------------------------------------------

}

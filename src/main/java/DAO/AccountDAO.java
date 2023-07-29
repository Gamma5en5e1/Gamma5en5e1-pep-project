package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        if(account.getUsername()!=null && account.getPassword().length()>3 && login(account).account_id == account.account_id){
        try{
            String sql="INSERT INTO account (username,password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet kResultSet = preparedStatement.getGeneratedKeys();
            if(kResultSet.next()){
                int generated_account_id =(int) kResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
        return null;
    }

    public Account login(Account account){
        Connection connection = ConnectionUtil.getConnection();
        Account loggedAccount= new Account(0,account.getUsername(), account.getPassword());
        try{
            String sql="SELECT (account_id) FROM ACCOUNT WHERE username = (?) AND password = (?)";
            PreparedStatement preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                loggedAccount.setAccount_id(resultSet.getInt("account_id"));
                return loggedAccount;
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return loggedAccount;
    }
}
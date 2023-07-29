package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        if(account.getUsername().length()>0 && account.getPassword().length()>3){
        try{
            String sql=" INSERT INTO ACCOUNT(username,password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            //ResultSet kResultSet = preparedStatement.getGeneratedKeys();
            //account.setAccount_id((int) kResultSet.getLong(1));
            account.setAccount_id(1);
            return login(account);
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
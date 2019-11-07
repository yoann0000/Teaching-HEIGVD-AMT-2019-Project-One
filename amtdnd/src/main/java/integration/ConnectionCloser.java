package integration;

import java.sql.Connection;
import java.sql.SQLException;

class ConnectionCloser {
    private ConnectionCloser(){}

    static void closeConnection(Connection connection){
        try{
            if(connection != null)connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

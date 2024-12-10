
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {

  private final String dbname;
  private final String username;
  private final String password;
  private final String port;
  private final String host;
  private final String url;
    
              
  public ConexaoPostgreSQL(String username, String password){
    dbname  = "google_keep";
    this.username = username;
    this.password = password;
    port = "5432"; // minha porta é 5233
    host = "localhost";
    url = "jdbc:postgresql://"+host+":"+port+"/"+dbname;

  }
     
  public Connection getConexao() throws SQLException{
    return DriverManager.getConnection(url, username, password);
  }
    
}

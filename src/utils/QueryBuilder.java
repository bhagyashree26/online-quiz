package utils;

import java.sql.*;
import java.util.ArrayList;

public class QueryBuilder{
  private String query = "";
  private Connection con = null;
  private PreparedStatement stmt = null;
  @SuppressWarnings("unused")
private String[] data;
  private ResultSet rs = null;
  private String table;
  private ArrayList<String> columns;

  public QueryBuilder(String tableName) throws Exception{
    this.table = tableName;
    Class.forName("com.mysql.jdbc.Driver");

    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
    columns = new ArrayList<String>();
  }

  public void setColumns() throws SQLException{
    this.query = "select column_name from information_schema.columns where table_name = '" + this.table + "' and table_schema = 'quiz'";

    stmt = con.prepareStatement(this.query);
    rs = stmt.executeQuery();

    while(rs.next()){
      columns.add( rs.getString("column_name") );
    }

  }

  public ArrayList<String> getColumns(){
    return columns;
  }

  public void createInsertQuery(String[] data){
    this.query = "INSERT INTO " + this.table + " VALUES ";
  }

  public void closeConnection() throws SQLException{
    con.close();
  }
}

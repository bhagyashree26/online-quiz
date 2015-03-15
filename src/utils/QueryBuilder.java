package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryBuilder<output>{
  private String query = "";
  private Connection con = null;
  private PreparedStatement stmt = null;
  private ResultSet rs = null;
  private String table;
  private ArrayList<String[]> columns;
  private ArrayList<String> data;
  private Map<String, String> output;
  private int colCount;

  public QueryBuilder(String tableName) throws Exception{
    this.table = tableName;
    Class.forName("com.mysql.jdbc.Driver");

    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
    columns = new ArrayList<String[]>();
    data = new ArrayList<String>();
    output = new HashMap<String, String>();
    
    setColumns();
  }

  public void setColumns() throws SQLException{
    this.query = "select column_name, data_type from information_schema.columns where table_name = '" + this.table + "' and table_schema = 'quiz'";

    stmt = con.prepareStatement(this.query);
    rs = stmt.executeQuery();

    while(rs.next()){
    	String[] data = {rs.getString("column_name"), rs.getString("data_type")};
    	columns.add( data );
    }

  }

  public ArrayList<String[]> getColumns(){
    return columns;
  }
  

  public void createInsertQuery(Map<String, String> d){
    this.query = "INSERT INTO " + this.table + "(";
    this.query += implode((String[]) columns.toArray());
    this.query += " VALUES";
    this.query += implode("?", colCount);
    
    for(String[] s : columns){
    	String value = d.get(s[0]);
    	if( value != null ){
    		data.add(value);
    	}
    }
    
  }
  
  public void getData(String condition) throws SQLException{
	  this.query = "SELECT * FROM " + this.table;
	  if( condition != null )
		  this.query += " WHERE " + condition;
  }
  
  public void executeQuery(Boolean getId) throws SQLException, NullPointerException{
	  if( data.size() == 0 && getId == true ) throw new NullPointerException("No Query Data found, please create Query Before Executing.");
	  
	  stmt = con.prepareStatement(this.query, Statement.RETURN_GENERATED_KEYS);
	  
	  for(int i = 1; i <= data.size(); i++ ){
		  String type = columns.get(i)[1];
		  if( type.equalsIgnoreCase("int") || type.equalsIgnoreCase("smallint") ){
			  stmt.setInt( i, Integer.parseInt(data.get(i-1)) );
		  }
		  else{
			  stmt.setString( i, data.get(i-1) );
		  }
	  }
	  
	  rs = stmt.executeQuery();
	  
	  if( getId ){
		  
		  ResultSet generatedKeys = stmt.getGeneratedKeys();
		  if( generatedKeys.next() ){
			  output.put("Last_Insert_Id", generatedKeys.getString(1));
			  output.put("Message", "Sucessfully Inserted Data");
		  }
		  else{
			  output.put("Message", "Failed to Insert Data");
		  }
		  
	  }
	  
	  
  }
  
  public String getQuery(){
	  return this.query;
  }
  
  public ResultSet getResultSet(){
	  return rs;
  }
  
  public ArrayList<Map<String, String>> prepareOutput() throws SQLException{
	  ArrayList<Map<String, String>> outputData = new ArrayList<Map<String, String>>();
	  while( rs.next() ){
		  Map<String, String> row = new HashMap<String, String>();
		  for(String[] s : columns){
			  row.put(s[0], rs.getString(s[0]));
		  }
		  outputData.add(row);
	  }
	  
	  return outputData;
  }

  public void closeConnection() throws SQLException{
    con.close();
  }
  
private String implode(String character, int length){
	  String output = "(";
	  for(int i =0; i < length-1; i++){
		  output += character + ", ";
	  }
	  output += character + ")";
	  
	  return output;
  }
  
  private String implode(String[] data){
	  colCount = 0;
	  String output = "(";
	  int len = data.length - 1;
	  for(int i = 0; i < len; i++){
		  if( data[i].equals("id") ) continue;
		  output += data[i] + ", ";
		  colCount++;
	  }
	  output += data[len] + ")";
	  colCount++;
	  
	  return output;
  }
}

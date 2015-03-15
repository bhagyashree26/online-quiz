package test;

import utils.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DemoQuery{
  public static void main( String[] args ){

    try{
      QueryBuilder<?> q = new QueryBuilder<Object>("admin");

      ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

      q.getData(null);
      System.out.println(q.getQuery());
      q.executeQuery(false);
      
      
      
      data = q.prepareOutput();
      
      System.out.println("Length of Data " + data.size() );
      
      for(Map<String, String> row : data ){
    	  Iterator<Entry<String, String>> entries = row.entrySet().iterator();
    	  while(entries.hasNext()){
    		  Entry<String, String> pair = (Entry<String, String>) entries.next();
    		  String key = pair.getKey();
    		  String value = pair.getValue();
    		  System.out.println( key + "\t" + value );
    	  }
      }

      q.closeConnection();
    }
    catch( SQLException e ){
      System.out.println(e.toString());
    }
    catch( ClassNotFoundException e ){
      System.out.println(e.toString());
    }
    catch( Exception e ){
      System.out.println(e.toString());
    }
  }
}

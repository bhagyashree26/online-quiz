package test;

import utils.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

public class DemoQuery{
  public static void main( String[] args ){

    try{
      QueryBuilder<?> q = new QueryBuilder<Object>("user");

      ArrayList<String[]> columns = new ArrayList<String[]>();

      columns = q.getColumns();

      for( String[] s : columns ){
        System.out.println(s[0] + "\t" + s[1]);
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

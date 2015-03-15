package test;

import utils.QueryBuilder;
import java.sql.SQLException;
import java.util.ArrayList;

public class DemoQuery{
  public static void main( String[] args ){

    try{
      QueryBuilder q = new QueryBuilder("user");
      q.setColumns();

      ArrayList<String> columns = new ArrayList<String>();

      columns = q.getColumns();

      for( String s : columns ){
        System.out.println(s);
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

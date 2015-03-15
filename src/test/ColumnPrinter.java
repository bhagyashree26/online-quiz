package test;
import utils.QueryBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ColumnPrinter
 */
@WebServlet("/servlet/ColumnPrinter")
public class ColumnPrinter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		res.setContentType("text/html");
		String table = req.getParameter("t");
		ArrayList<String[]> columns = new ArrayList<String[]>();
		
		PrintWriter out = res.getWriter();
		
		out.write("<html>");
		out.write("<head><title>" + table + "</title></head>");
		out.write("<body><table border='1' width='100%'>");
		
		try{
			QueryBuilder<Object> q = new QueryBuilder<Object>(table);
			
			columns = q.getColumns();
			
			q.closeConnection();
			
			
		}
		catch(SQLException e){
			String[] error = {e.toString(), "SQL Error"};
			columns.add( error );
		}
		catch(ClassNotFoundException e){
			String[] error = {e.toString(), "SQL Error"};
			columns.add( error );
		}
		catch(Exception e){
			String[] error = {e.toString(), "SQL Error"};
			columns.add( error );
		}
		
		for(String[] s : columns){
			out.write("<tr><td>" + s[0] + "</td><td>" + s[1] + "</td></tr>");
		}
		out.write("</table>");
		out.write("Length of Column : " + columns.size());
		out.write("</body>");
		
		out.write("</html>");
		
	}

}

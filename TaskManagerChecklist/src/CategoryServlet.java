

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String Get_Category = "SELECT name FROM mytaskdatabase.category";
	private Connection conn;
	String categoryname;
    public CategoryServlet() 
    {
        super();
        
    }
    public void init(ServletConfig config) throws ServletException 
	{
		DataSource dataSource=new MysqlDataSource();
		try {
			conn = dataSource.getConnection("root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
				pstmt = conn.prepareStatement(Get_Category);
				rs = pstmt.executeQuery();
				rs.next();
				categoryname=rs.getString(2);
				request.setAttribute("categname", categoryname);
                // req.setAttribute("bookID", bookid);
				request.getRequestDispatcher("AddTask.jsp").forward(request, response); 
			}
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	}

}

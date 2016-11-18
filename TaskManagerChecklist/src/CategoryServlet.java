

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.swing.internal.plaf.basic.resources.basic;
import com.task.dao.CategoryDao;
import com.task.dao.Imp.CategoryDaoImp;
import com.task.dto.Category;

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
    	List listOfCategory=null;
		CategoryDao catDao=new CategoryDaoImp();
    	try 
    	{
			listOfCategory=catDao.findAllCategory();
			request.setAttribute("listOfCategory", listOfCategory);
			request.getRequestDispatcher("/AddCategory.jsp").forward(request, response); 
    	} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Category category=new Category();
		category.setName(request.getParameter("catname"));
		List listOfCategory=null;
		CategoryDao catDao=new CategoryDaoImp();
		try 
		{
				boolean result=catDao.addCategory(category);
				if(result)
				{
					listOfCategory=catDao.findAllCategory();
					request.setAttribute("listOfCategory", listOfCategory);
					request.getRequestDispatcher("/AddCategory.jsp").forward(request, response); 
				}
				else
				{
					String message="Error!!!";
                	request.setAttribute("message", message);
                      request.getRequestDispatcher("/AddCategory.jsp").forward(request, response); 
				}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

}

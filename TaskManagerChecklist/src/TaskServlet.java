

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.task.dao.TaskDao;
import com.task.dao.Imp.TaskDaoImp;
import com.task.dto.Task;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String Get_CategoryId = "SELECT id FROM mytaskdatabase.category where name=?";
	private Connection conn;
    public TaskServlet() 
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
	public void destroy() 
	{
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
			response.setContentType("text/html");
			int categoryId;
			String categoryName=request.getParameter("categoryname");
			
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
					pstmt = conn.prepareStatement(Get_CategoryId);
					pstmt.setString(1, categoryName);
			
					rs = pstmt.executeQuery();
					rs.next();
					categoryId=rs.getInt(1);
					
					Task task=new Task();
					task.setCategoryId(categoryId);
					task.setName(request.getParameter("taskname"));
					task.setTime(request.getParameter("time"));
		            task.setReminder(Integer.parseInt(request.getParameter("reminder")));
		            task.setStatus(request.getParameter("status"));
		            task.setDescription(request.getParameter("description"));
		            
		            TaskDao taskDao=new TaskDaoImp();
		            try
		            {
		                boolean result=taskDao.addTask(task);
		                if(result)
		                {
		                    String message="Record Inserted Successfully!!!";
		                    request.setAttribute("message", message);
		                    request.getRequestDispatcher("AddTask.jsp").forward(request, response); 
		                }
		                else
		                {
		                    //  req.getRequestDispatcher("error.jsp").forward(req, resp); 
		                }
		                        
				} 
				catch (SQLException e1) 
				{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			
			
            }
            catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

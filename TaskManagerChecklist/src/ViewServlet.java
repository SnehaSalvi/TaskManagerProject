

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.task.dao.CategoryDao;
import com.task.dao.SubtaskDao;
import com.task.dao.TaskDao;
import com.task.dao.Imp.SubtaskDaoImp;
import com.task.dao.Imp.TaskDaoImp;
import com.task.dto.Subtask;


@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String Get_TaskId = "SELECT id FROM mytaskdatabase.task where name=?";

	private Connection conn;
    public ViewServlet() 
    {
        super();
        // TODO Auto-generated constructor stub
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
		//String button_action=request.getParameter("id");
		//System.out.println(button_action);
		
		
		//doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String taskName=request.getParameter("taskname");
		String button=request.getParameter("button");
		int taskId;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try 
		{
			pstmt = conn.prepareStatement(Get_TaskId);
			pstmt.setString(1, taskName);
			rs = pstmt.executeQuery();
			rs.next();
			taskId=rs.getInt(1);
			
			SubtaskDao subDao=new SubtaskDaoImp();
			List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
			request.setAttribute("taskName",taskName);
			request.setAttribute("listOfItem",listOfItem);
			RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
			rd.forward(request, response);
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}

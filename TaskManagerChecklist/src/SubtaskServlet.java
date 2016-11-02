

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
import com.task.dao.SubtaskDao;
import com.task.dao.Imp.SubtaskDaoImp;
import com.task.dto.Subtask;

@WebServlet("/SubtaskServlet")
public class SubtaskServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String Get_TaskId = "SELECT id FROM mytaskdatabase.task where name=?";
	private static final String Update_Task_Status="UPDATE mytaskdatabase.task SET reminder=?,status=? where id=?";
	
	//private static final String Get_SubtaskId = "SELECT id FROM mytaskdatabase.subtask where name=?";
	private Connection conn;
    public SubtaskServlet() 
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
		
	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String taskName=request.getParameter("taskname");
		
		String button=request.getParameter("button");
		String status1="Item Scheduled";
		int taskId,subtaskId;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		if(button.equalsIgnoreCase("Add More"))
		{
		try {
			pstmt = conn.prepareStatement(Get_TaskId);
			pstmt.setString(1, taskName);
			rs = pstmt.executeQuery();
			rs.next();
			taskId=rs.getInt(1);
			System.out.println("taskId"+taskId);
			Subtask subtask=new Subtask();
			subtask.setTaskId(taskId);
			subtask.setName(request.getParameter("itemname"));
			subtask.setStatus(status1);
			
			SubtaskDao subtaskDao=new SubtaskDaoImp();
			
			boolean result=subtaskDao.addSubtask(subtask);
				if(result)
				{
					int reminder=1;
					String status="Task Scheduled";
					pstmt = conn.prepareStatement(Update_Task_Status);
					pstmt.setInt(1,reminder);
					pstmt.setString(2,status); 
					pstmt.setInt(3,taskId);
			        pstmt.executeUpdate();
					String message="Record Inserted Successfully!!!";
					request.setAttribute("button", button);
					request.setAttribute("message", message);
					request.getRequestDispatcher("HomeServlet").forward(request, response); 
				}
				else
				{
					String message="Error!!!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("HomeServlet").forward(request, response); 
				}
				subtaskDao.close();
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(button.equalsIgnoreCase("Done"))
		{
			try {
				pstmt = conn.prepareStatement(Get_TaskId);
				pstmt.setString(1, taskName);
				rs = pstmt.executeQuery();
				rs.next();
				taskId=rs.getInt(1);
				System.out.println("taskId"+taskId);
				Subtask subtask=new Subtask();
				subtask.setTaskId(taskId);
				subtask.setName(request.getParameter("itemname"));
				subtask.setStatus(status1);				
				SubtaskDao subtaskDao=new SubtaskDaoImp();
				
				boolean result=subtaskDao.addSubtask(subtask);
					if(result)
					{
						int reminder=1;
						String status="Task Scheduled";
						pstmt = conn.prepareStatement(Update_Task_Status);
						pstmt.setInt(1,reminder);
						pstmt.setString(2,status); 
						pstmt.setInt(3,taskId);
				        pstmt.executeUpdate();
						SubtaskDao subDao=new SubtaskDaoImp();
						List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
						request.setAttribute("taskName",taskName);
						request.setAttribute("listOfItem",listOfItem);
						RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
						rd.forward(request, response);
					}
					else
					{
						String message="Error!!!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("HomeServlet").forward(request, response); 
					}
					subtaskDao.close();
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		else if(button.equalsIgnoreCase("Update"))
		{
			try 
			{
				String itemName=request.getParameter("itemname");
			//	String description=request.getParameter("description");
				subtaskId=Integer.parseInt(request.getParameter("subId"));
				pstmt = conn.prepareStatement(Get_TaskId);
				
				pstmt.setString(1, taskName);
				rs = pstmt.executeQuery();
				rs.next();
				taskId=rs.getInt(1);
				
				
				Subtask subtask=new Subtask();
				
				subtask.setTaskId(taskId);
				subtask.setName(itemName);
				subtask.setStatus(status1);
				subtask.setSubtaskID(subtaskId);
				
				SubtaskDao subDao=new SubtaskDaoImp();
				boolean result=subDao.updateSubtask(subtask);
				if(result)
				{
					
					List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
					String message="Record Updated Successfully!!!";
					request.setAttribute("message", message);
					request.setAttribute("taskName",taskName);
					request.setAttribute("listOfItem",listOfItem);
					RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
					rd.forward(request, response);
				}
				else
				{
					String message="Error!!!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("HomeServlet").forward(request, response); 
				}
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
		else
		{
			
		}
		
	}
	public void destroy() 
	{
		
	}
}

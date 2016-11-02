

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.task.dao.Imp.CategoryDaoImp;
import com.task.dao.Imp.SubtaskDaoImp;
import com.task.dao.Imp.TaskDaoImp;
import com.task.dto.Category;
import com.task.dto.Subtask;
import com.task.dto.Task;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static final String Select_All_Category = "SELECT * FROM mytaskdatabase.category";
	private static final String Select_Task_Name = "SELECT name FROM mytaskdatabase.task where id=?";
	private static final String Get_TaskId = "SELECT id FROM mytaskdatabase.task where name=?";
	private static final String Delete_Subtask = "DELETE FROM mytaskdatabase.subtask where id=?";
	private static final String Select_Subtask = "SELECT taskid,name,status FROM mytaskdatabase.subtask where id=?";
	private static final String Select_Subtask_Count = "select count(id) from mytaskdatabase.subtask where taskid=?";
	private static final String Update_Task_Status="UPDATE mytaskdatabase.task SET reminder=?,status=? where id=?";
	private Connection conn;  
    public HomeServlet() 
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
	public void destroy() 
	{
		// TODO Auto-generated method stub
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String taskname1=request.getParameter("taskname1");
		System.out.println("taskname1sasasias"+taskname1);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String button_action=request.getParameter("button");
		System.out.println(button_action);
		CategoryDao categoryDao=null;
		TaskDao taskDao=null;
		if(button_action.equalsIgnoreCase("1") || button_action.equalsIgnoreCase("Done"))
		{
		try {
				categoryDao=new CategoryDaoImp();
				List<Category> listOfCategory = categoryDao.findAllCategory();
				System.out.println("size"+listOfCategory.size());
				//String message="Record Inserted Successfully!!!";
				request.setAttribute("listOfCategory",listOfCategory);
				//request.setAttribute("message",message);
				RequestDispatcher rd = request.getRequestDispatcher("AddTask.jsp");
				rd.forward(request, response);
				
			} 
			catch (SQLException e) 
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			finally
			{
				categoryDao.close();
			}
		}
		else if(button_action.equalsIgnoreCase("2") || button_action.equalsIgnoreCase("Add More"))
		{
			taskDao=new TaskDaoImp();
			List<Task> listOfTask;
			try {
					listOfTask = taskDao.findAllTask();
				
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("AddItem.jsp");
					rd.forward(request, response);
			} catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				taskDao.close();
			}
			
		}
		else if(button_action.equalsIgnoreCase("3"))
		{
			 taskDao=new TaskDaoImp();
			
			List listOfTask;
			try {
				listOfTask = taskDao.findAllTask();
			
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("ViewAllTask.jsp");
					rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
		}
		else if(button_action.equalsIgnoreCase("7"))
		{
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int taskId=Integer.parseInt(request.getParameter("taskId"));
			SubtaskDao subDao=new SubtaskDaoImp();
			List<Subtask> listOfItem;
			
			
			try 
			{
				pstmt = conn.prepareStatement(Select_Task_Name);
				
				pstmt.setInt(1, taskId);
				rs = pstmt.executeQuery();
				rs.next();
				String taskName=rs.getString(1);
				listOfItem = subDao.findAllItemByID(taskId);
			
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
		}
		else if(button_action.equalsIgnoreCase("edit"))
		{
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			taskDao=new TaskDaoImp();
			String taskName,itemName,itemStatus;
			int taskId;
			List<Task> listOfTask;
			try {
				
				int subId=Integer.parseInt(request.getParameter("check1"));
				System.out.println("subID"+subId);
				pstmt = conn.prepareStatement(Select_Subtask);
				
				pstmt.setInt(1, subId);
				rs = pstmt.executeQuery();
				rs.next();
				taskId=rs.getInt(1);
				itemName=rs.getString(2);
				itemStatus=rs.getString(3);
				pstmt = conn.prepareStatement(Select_Task_Name);
				
				pstmt.setInt(1, taskId);
				rs = pstmt.executeQuery();
				rs.next();
				taskName=rs.getString(1);
				//taskName=request.getParameter("taskname");
					listOfTask = taskDao.findAllTask();
					request.setAttribute("subId", subId);
					request.setAttribute("taskName", taskName);
					request.setAttribute("itemName", itemName);
				//	request.setAttribute("itemStatus", itemStatus);
					RequestDispatcher rd = request.getRequestDispatcher("UpdateItemlist.jsp");
					rd.forward(request, response);
			} catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				taskDao.close();
			}
	
			
		}
		else if(button_action.equalsIgnoreCase("delete"))
		{
			int subtaskID=Integer.parseInt(request.getParameter("check1")); 
			System.out.println("subtaskID"+subtaskID);
			int count;
			try 
			{
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				String taskName=request.getParameter("taskname");
				System.out.println("taskname"+taskName);
				
			
				pstmt = conn.prepareStatement(Delete_Subtask);
			      
				pstmt.setInt(1,subtaskID );
		                int result=pstmt.executeUpdate();
		                SubtaskDao subDao=new SubtaskDaoImp();
		                if(result>=1)
		                {
		                	pstmt = conn.prepareStatement(Select_Subtask_Count);
		    				
		    				pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				count=rs.getInt(1);
		    				if(count<=0)
		    				{
		    					int reminder=0;
		    					String status="Task Finished";
		    					String status1="Status: Task Finished";
		    					pstmt = conn.prepareStatement(Update_Task_Status);
		    					pstmt.setInt(1,reminder);
		    					pstmt.setString(2,status); 
		    					pstmt.setInt(3,Integer.parseInt(request.getParameter("taskId")) );
		    			        pstmt.executeUpdate();
		    					String message="No Item in a list!!!";
		    					request.setAttribute("message", message);
		    					request.setAttribute("status",status1);
								request.setAttribute("taskName",taskName);
		    					RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
								rd.forward(request, response);
		    				}
		    				else
		    				{
		                	List<Subtask> listOfItem = subDao.findAllItemByID(Integer.parseInt(request.getParameter("taskId")));
							String message="Record Deleted Successfully!!!";
							request.setAttribute("message", message);
							request.setAttribute("taskName",taskName);
							request.setAttribute("listOfItem",listOfItem);
							RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
							rd.forward(request, response);
		    				}
		                    
		                }
		               
	              /*  if(result)
	                {
	                	List<Subtask> listOfItem = subDao.findAllItemByID(Integer.parseInt(request.getParameter("taskId")));
						String message="Record Deleted Successfully!!!";
						request.setAttribute("message", message);
						request.setAttribute("taskName",taskName);
						request.setAttribute("listOfItem",listOfItem);
						RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
						rd.forward(request, response);
	                }*/
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
	//doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String button_action=request.getParameter("button");
		CategoryDao categoryDao=null;
		TaskDao taskDao=null;
		if(button_action.equalsIgnoreCase("1") || button_action.equalsIgnoreCase("Done"))
		{
		try {
				categoryDao=new CategoryDaoImp();
				List<Category> listOfCategory = categoryDao.findAllCategory();
				System.out.println("size"+listOfCategory.size());
				
				String message="Record Inserted Successfully!!!";
				request.setAttribute("listOfCategory",listOfCategory);
				request.setAttribute("message", message);
				RequestDispatcher rd = request.getRequestDispatcher("AddTask.jsp");
				rd.forward(request, response);
				
			} 
			catch (SQLException e) 
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			finally
			{
				categoryDao.close();
			}
		}
		else if(button_action.equalsIgnoreCase("2") || button_action.equalsIgnoreCase("Add More"))
		{
			taskDao=new TaskDaoImp();
			List<Task> listOfTask;
			try {
					listOfTask = taskDao.findAllTask();
					String message=request.getParameter("message");
					request.setAttribute("listOfTask", listOfTask);
					request.setAttribute("message", message);
					RequestDispatcher rd = request.getRequestDispatcher("AddItem.jsp");
					rd.forward(request, response);
			} catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				taskDao.close();
			}
		}
		
		
	}

}

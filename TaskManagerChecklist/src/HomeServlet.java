

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
	private static final String Select_Cat_ID = "SELECT categoryid FROM mytaskdatabase.task where id=?";
	private static final String Select_Task_ID = "SELECT id FROM mytaskdatabase.task where categoryid=2";
	private static final String Select_Subtask_Name = "select s.name from mytaskdatabase.subtask t,mytaskdatabase.subtask s  where t.id=s.taskid and t.name=?";
	private static final String Select_Task_Count = "select count(id) from mytaskdatabase.task";
	private static final String Select_TaskId = "SELECT id FROM mytaskdatabase.task";
	
	private static final String Select_Subtask_Count = "select count(id) from mytaskdatabase.subtask where taskid=?";
	private static final String Update_Task_Status="UPDATE mytaskdatabase.task SET reminder=?,status=? where id=?";
	
	private static final String Select_Subtask_Status_Count = "select count(id) from mytaskdatabase.subtask where taskid=? and status='Item Scheduled'";
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
		
		
		if(button_action.equalsIgnoreCase("6") || button_action.equalsIgnoreCase("Done"))
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
				PreparedStatement pstmt = conn.prepareStatement(Select_Task_Count);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				int count=rs.getInt(1);
				if(count<=0)
				{
					String message="No Task Available";
					request.setAttribute("message", message);
					
					RequestDispatcher rd = request.getRequestDispatcher("ViewAllTask.jsp");
					rd.forward(request, response);
							
				}
				else
				{
					listOfTask = taskDao.findAllTask();
					
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("ViewAllTask.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
		}
		else if(button_action.equalsIgnoreCase("11"))
		{
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			List<Integer> taskId;
			List<Subtask> listOfItem=null;
			List<Integer> listOfId=null;
			try
			{
				pstmt = conn.prepareStatement(Select_TaskId);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					
				System.out.println("taskId: "+rs.getInt(1));
				listOfId=new ArrayList<Integer>();
				listOfId.add(rs.getInt(1));
				//	SubtaskDao subDao=new SubtaskDaoImp();
				//	listOfItem = subDao.findAllItemByID(rs.getInt(1));
				//	List<Subtask> listOfItem1 = subDao.findAllItemStatus(rs.getInt(1));
					
					
				}
				request.setAttribute("listOfId", listOfId);
				RequestDispatcher rd = request.getRequestDispatcher("ListView.jsp");
				rd.forward(request, response);
			}
			catch (SQLException e) 
			{
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
				
				String message="Task Created!!!";
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
		if(button_action.equalsIgnoreCase("6") || button_action.equalsIgnoreCase("Done"))
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
		else if(button_action.equalsIgnoreCase("Add") || button_action.equalsIgnoreCase("Add More"))
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
		/*else if(button_action.equalsIgnoreCase("View Item"))
		{
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int taskId=Integer.parseInt(request.getParameter("taskId"));	
			try
			{
				pstmt = conn.prepareStatement(Select_Task_ID);
				
				rs = pstmt.executeQuery();
			//	List<Integer> taskList = new ArrayList<Integer>();
				while(rs.next())
				{
					pstmt = conn.prepareStatement(Select_Task_Name);
					pstmt.setInt(1, rs.getInt(1));
					rs = pstmt.executeQuery();
					while(rs.next())
					{
						
						String taskname=rs.getString(1);
						List<String> taskName=new ArrayList<String>();
						taskName.add(rs.getString(1));
						SubtaskDao subDao=new SubtaskDaoImp();
    					List<Subtask> listOfItem = subDao.findAllItemName(taskname);
							
							request.setAttribute("listOfItem", listOfItem);
							request.setAttribute("taskName", taskName);
							RequestDispatcher rd = request.getRequestDispatcher("ViewAll.jsp");
							rd.forward(request, response);
					
							
						}
						
					}
				
				
				}
				
			
			
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				//taskDao.close();
			}
		}*/
		else if(button_action.equalsIgnoreCase("Add Item"))
		{
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int taskId=Integer.parseInt(request.getParameter("taskId"));			
			System.out.println("Task ID IS:"+taskId);
			Task task=new Task();
			taskDao=new TaskDaoImp();
			int count;
			try {
					pstmt = conn.prepareStatement(Select_Task_Name);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					String taskName=rs.getString(1);
					pstmt = conn.prepareStatement(Select_Subtask_Status_Count);
    				
    				pstmt.setInt(1, taskId);
    				rs = pstmt.executeQuery();
    				rs.next();
    				count=rs.getInt(1);
    				if(count<=0)
    				{
					SubtaskDao subDao=new SubtaskDaoImp();
					List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
					
					String message="No Item in a list!!!";
					request.setAttribute("message", message);
					request.setAttribute("listOfItem1",listOfItem1);
					request.setAttribute("taskName",taskName);
					RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
					rd.forward(request, response);
    				}
    				else
    				{
    					SubtaskDao subDao=new SubtaskDaoImp();
    					List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
    					List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
    					request.setAttribute("taskName",taskName);
    					request.setAttribute("listOfItem",listOfItem);
    					request.setAttribute("listOfItem1",listOfItem1);
    					RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
    					rd.forward(request, response);
    				}
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				taskDao.close();
			}
		}
		else if(button_action.equalsIgnoreCase("View Item"))
		{
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int taskId=Integer.parseInt(request.getParameter("taskId"));			
			System.out.println("Task ID IS:"+taskId);
			Task task=new Task();
			taskDao=new TaskDaoImp();
			int count;
			try {
					pstmt = conn.prepareStatement(Select_Task_Name);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					String taskName=rs.getString(1);
					pstmt = conn.prepareStatement(Select_Subtask_Status_Count);
    				
    				pstmt.setInt(1, taskId);
    				rs = pstmt.executeQuery();
    				rs.next();
    				count=rs.getInt(1);
    				if(count<=0)
    				{
					SubtaskDao subDao=new SubtaskDaoImp();
					List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
					
					String message="No Item in a list!!!";
					request.setAttribute("message", message);
					//request.setAttribute("listOfItem1",listOfItem1);
					request.setAttribute("taskId",taskId);
					request.setAttribute("taskName",taskName);
					RequestDispatcher rd = request.getRequestDispatcher("ViewAll.jsp");
					rd.forward(request, response);
    				}
    				else
    				{
    					SubtaskDao subDao=new SubtaskDaoImp();
    					List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
    				//	List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
    					request.setAttribute("taskId",taskId);
    					request.setAttribute("taskName",taskName);
    					request.setAttribute("listOfItem",listOfItem);
    					//request.setAttribute("listOfItem1",listOfItem1);
    					RequestDispatcher rd = request.getRequestDispatcher("ViewAll.jsp");
    					rd.forward(request, response);
    				}
			} 
			catch (SQLException e) 
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

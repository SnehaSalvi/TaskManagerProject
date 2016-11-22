

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.task.dto.Task;


@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String Get_TaskId = "SELECT id FROM mytaskdatabase.task where name=?";
	private static final String Select_Task_Count = "select count(id) from mytaskdatabase.task";
	private static final String Delete_Task = "DELETE FROM mytaskdatabase.task where id=?";
	private static final String Get_CategoryId = "SELECT id FROM mytaskdatabase.category where name=?";
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
	
			TaskDao taskDao=new TaskDaoImp();
			
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
					
					RequestDispatcher rd = request.getRequestDispatcher("/ViewAllTask.jsp");
					rd.forward(request, response);
							
				}
				else
				{
					listOfTask = taskDao.findAllTask();
					
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("/ViewAllTask.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
		
		
		//doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String button_action=request.getParameter("button");
		String status1="Item Scheduled";
		
		System.out.println("BUTTON: "+button_action);
		CategoryDao categoryDao=null;
		int categoryId;
		String status="Task Scheduled";
		String categoryName=request.getParameter("categoryname");
		if(button_action.equalsIgnoreCase("Remove"))
		{
		
		try
		{
			pstmt = conn.prepareStatement(Delete_Task);
			pstmt.setInt(1,Integer.parseInt(request.getParameter("check1")));
			int result=pstmt.executeUpdate();
			List listOfTask;
			TaskDao taskDao=new TaskDaoImp();
			if(result>=1)
			{
				pstmt = conn.prepareStatement(Select_Task_Count);
				rs = pstmt.executeQuery();
				rs.next();
				int count=rs.getInt(1);
				if(count<=0)
				{
					String message="No Item in a list";
					String status2="Task Removed Successfully!!!";
					request.setAttribute("message", message);
					request.setAttribute("status", status2);
					
					RequestDispatcher rd = request.getRequestDispatcher("/ViewAllTask.jsp");
					rd.forward(request, response);
							
				}
				else
				{
					listOfTask = taskDao.findAllTask();
					
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("/ViewAllTask.jsp");
					rd.forward(request, response);
				}
				
				
			
			}
			else
			{
				String message="Error!!!";
				request.setAttribute("status", message);
				request.getRequestDispatcher("/ViewAll.jsp").forward(request, response); 
			}
			
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		}
		else if(button_action.equalsIgnoreCase("Complete"))
		{
			System.out.println("TASKIDDDD"+Integer.parseInt(request.getParameter("taskId")));
		try
		{
			pstmt = conn.prepareStatement(Delete_Task);
			pstmt.setInt(1,Integer.parseInt(request.getParameter("taskId")));
			int result=pstmt.executeUpdate();
			List listOfTask;
			TaskDao taskDao=new TaskDaoImp();
			if(result>=1)
			{
				pstmt = conn.prepareStatement(Select_Task_Count);
				rs = pstmt.executeQuery();
				rs.next();
				int count=rs.getInt(1);
				if(count<=0)
				{
										
					
					String message="No Item in a list";
					String status2="Task Removed Successfully!!!";
					request.setAttribute("message", message);
					request.setAttribute("status", status2);
					
					RequestDispatcher rd = request.getRequestDispatcher("/TaskHome.jsp");
					rd.forward(request, response);
							
				}
				else
				{
					
					listOfTask = taskDao.findAllTask();
					String status2="Task Removed Successfully!!!";
					//request.setAttribute("message", message);
					request.setAttribute("status", status2);
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("/TaskHome.jsp");
					rd.forward(request, response);
				}
				
				
			
			}
			else
			{
				String message="Error!!!";
				request.setAttribute("status", message);
				request.getRequestDispatcher("/ViewAll.jsp").forward(request, response); 
			}
			
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		}
		else if(button_action.equalsIgnoreCase("Save"))
		{
			System.out.println("action:"+button_action);
			try {
				pstmt = conn.prepareStatement(Get_CategoryId);
				pstmt.setString(1, categoryName);
		
				rs = pstmt.executeQuery();
				rs.next();
				categoryId=rs.getInt(1);
				
				Task task=new Task();
				task.setCategoryId(categoryId);
				task.setName(request.getParameter("taskname"));
				Date date1;
				String date3 = null;
				
				
				 try 
				 {
					date1 = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("mydate"));
				
					task.setDate(date1);
				 } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
				
				task.setTime(request.getParameter("time"));
	            task.setReminder(Integer.parseInt(request.getParameter("reminder")));
	            task.setStatus(status);
	            task.setDescription(request.getParameter("description"));
	            
	            TaskDao taskDao=new TaskDaoImp();
	            try
	            {
	                boolean result=taskDao.addTask(task);
	                if(result)
	                {
	                	/*String message="Task Created!!!";
	                
	                	categoryDao=new CategoryDaoImp();
	    				List<Category> listOfCategory = categoryDao.findAllCategory();
	    				System.out.println("size"+listOfCategory.size());
	    				//String message="Record Inserted Successfully!!!";
	    				request.setAttribute("listOfCategory",listOfCategory);
	    				//request.setAttribute("message",message);
	    				request.setAttribute("message", message);
	    				RequestDispatcher rd = request.getRequestDispatcher("/AddTask.jsp");
	    				rd.forward(request, response);*/
	                	
	                	
	            		
	            		taskDao=new TaskDaoImp();
	            			
	            			List listOfTask;
	            			

	            			try 
	            			{
	            					pstmt = conn.prepareStatement(Select_Task_Count);
	            					rs = pstmt.executeQuery();
	            					rs.next();
	            					int count=rs.getInt(1);
	            					if(count<=0)
	            					{
	            						String message="No Task Available";
	            						request.setAttribute("message", message);
	            					
	            						RequestDispatcher rd = request.getRequestDispatcher("/TaskHome.jsp");
	            						rd.forward(request, response);
	            							
	            					}
	            					else
	            					{
	            						listOfTask = taskDao.findAllTask();
	            						
	            						request.setAttribute("listOfTask", listOfTask);
	            					
	            						RequestDispatcher rd = request.getRequestDispatcher("/TaskHome.jsp");
	            						rd.forward(request, response);
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
	                	String message="Error!!!";
	                	request.setAttribute("message", message);
	                      request.getRequestDispatcher("HomeServlet").forward(request, response); 
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
	
		else if(button_action.equalsIgnoreCase("Taskview"))
		{
			TaskDao taskDao=null;
			
			taskDao=new TaskDaoImp();
				
				List listOfTask=null;
				

				try {
					pstmt = conn.prepareStatement(Select_Task_Count);
					rs = pstmt.executeQuery();
					rs.next();
					int count=rs.getInt(1);
					if(count<=0)
					{
						String message="No Task Available";
						request.setAttribute("message", message);
						
						RequestDispatcher rd = request.getRequestDispatcher("/TaskHome.jsp");
						rd.forward(request, response);
								
					}
					else
					{
						listOfTask = taskDao.findAllTask();
						
						request.setAttribute("listOfTask", listOfTask);
						
						RequestDispatcher rd = request.getRequestDispatcher("/TaskHome.jsp");
						rd.forward(request, response);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else if(button_action.equalsIgnoreCase("TasksList"))
		{
			TaskDao taskDao=new TaskDaoImp();
			
			List listOfTask;
			try {
				pstmt = conn.prepareStatement(Select_Task_Count);
				rs = pstmt.executeQuery();
				rs.next();
				int count=rs.getInt(1);
				if(count<=0)
				{
					String message="No Task Available";
					request.setAttribute("message", message);
					
					RequestDispatcher rd = request.getRequestDispatcher("/ViewAllTask.jsp");
					rd.forward(request, response);
							
				}
				else
				{
					listOfTask = taskDao.findAllTask();
					
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("/ViewAllTask.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
		
		}
		else
		{
		}
	}
}

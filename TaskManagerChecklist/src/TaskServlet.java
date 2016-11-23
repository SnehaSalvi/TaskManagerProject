

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
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

@WebServlet(urlPatterns = {"/Task/*","/Tasks/*","/Item/*","/Category/*"})
public class TaskServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPDATE_Subtask_Status = "UPDATE mytaskdatabase.subtask SET status='Item Completed' where id=?";
	private static final String Update_Task_Status="UPDATE mytaskdatabase.task SET reminder=?,status=? where id=?";
	private static final String Get_TaskId = "SELECT id FROM mytaskdatabase.task where name=?";
	private static final String Get_CategoryId = "SELECT id FROM mytaskdatabase.category where name=?";
	private static final String Select_Task_Count = "select count(id) from mytaskdatabase.task";
	private static final String Select_Task_Name = "SELECT name FROM mytaskdatabase.task where id=?";
	private static final String UPDATE_Subtask_Status1 = "UPDATE mytaskdatabase.subtask SET status='Item Scheduled' where id=?";
	private static final String Select_Subtask_Status_Count = "select count(id) from mytaskdatabase.subtask where taskid=? and status='Item Scheduled'";
	private static final String Select_Subtask = "SELECT taskid,name,status FROM mytaskdatabase.subtask where id=?";
	private static final String Select_Subtask_Status_Count1 = "select count(id) from mytaskdatabase.subtask where taskid=? and status='Item Completed'";
	private static final String Delete_Task = "DELETE FROM mytaskdatabase.task where id=?";
	
	private static final String Select_Task_Date_Time = "SELECT date,time FROM mytaskdatabase.task where id=?";
	
	private Connection conn;
    public TaskServlet() 
    {
        super();
       
    }
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//String button_action=request.getParameter("button");
		// TODO Auto-generated method stub
		/*CategoryDao categoryDao=null;
		TaskDao taskDao=null;
		
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
		*/
		
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
		//doPost(req, resp);
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
			String status1="Item Scheduled";
			String button_action=request.getParameter("button");
			System.out.println("Hello"+request.getParameter("name"));
			System.out.println("BUTTON: "+button_action);
			CategoryDao categoryDao=null;
			int categoryId;
			String status="Task Scheduled";
			String categoryName=request.getParameter("categoryname");
		//	String categoryName1=request.getParameter("categoryname1");
			
			//Boolean active = Boolean.valueOf(request.getParameter("someField"));
			//to get a priimitive boolean
			//boolean action = active.booleanValue();
			//System.out.println("action"+action);
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			if(button_action.equalsIgnoreCase("Add"))
			{
				
				try {
					pstmt = conn.prepareStatement(Select_Task_Name);
					pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
					rs = pstmt.executeQuery();
					rs.next();
					String taskName=rs.getString(1);
				
					System.out.println("TASKNAME:"+request.getParameter("taskname")+"/t"+Integer.parseInt(request.getParameter("taskId")));
					request.setAttribute("taskName",taskName);
					
					request.setAttribute("taskId", Integer.parseInt(request.getParameter("taskId")));
					
					
					
					RequestDispatcher rd = request.getRequestDispatcher("/AddTaskItem.jsp");
					rd.forward(request, response);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(button_action.equalsIgnoreCase("createTask"))
			{
			try {
					categoryDao=new CategoryDaoImp();
					List<Category> listOfCategory = categoryDao.findAllCategory();
					System.out.println("size"+listOfCategory.size());
					//String message="Record Inserted Successfully!!!";
					request.setAttribute("listOfCategory",listOfCategory);
					//request.setAttribute("message",message);
					RequestDispatcher rd = request.getRequestDispatcher("/AddTask.jsp");
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
			else if(button_action.equalsIgnoreCase("Add Item"))
			{
			
				int taskId=Integer.parseInt(request.getParameter("taskId"));			
				System.out.println("Task ID IS:"+taskId);
				Task task=new Task();
				TaskDao taskDao=new TaskDaoImp();
				int count;
				try {
						pstmt = conn.prepareStatement(Select_Task_Name);
						pstmt.setInt(1, taskId);
						rs = pstmt.executeQuery();
						rs.next();
						String taskName=rs.getString(1);
						pstmt = conn.prepareStatement(Select_Task_Date_Time);
	    				
	    				pstmt.setInt(1, taskId);
	    				rs = pstmt.executeQuery();
	    				rs.next();
	    				Date date1=rs.getDate(1);
	    				String time=rs.getString(2);
	    				
	    				String date;
						
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							
							 date = dateFormat.format(date1); 

							//date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
						
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
						request.setAttribute("taskId",taskId);
						request.setAttribute("date",date);
						request.setAttribute("time",time);
						RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
						rd.forward(request, response);
	    				}
	    				else
	    				{
	    					SubtaskDao subDao=new SubtaskDaoImp();
	    					List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
	    					List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
	    					request.setAttribute("taskName",taskName);
	    					request.setAttribute("taskId",taskId);
	    					request.setAttribute("listOfItem",listOfItem);
	    					request.setAttribute("listOfItem1",listOfItem1);
	    					request.setAttribute("date",date);
							request.setAttribute("time",time);
	    					RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
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
		
			else if(button_action.equalsIgnoreCase("Add More"))
			{
			try {
				System.out.println("***TASKID");
				pstmt = conn.prepareStatement(Get_TaskId);
				pstmt.setString(1, request.getParameter("taskname"));
				rs = pstmt.executeQuery();
				rs.next();
				int taskId=rs.getInt(1);
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
						status="Task Scheduled";
						pstmt = conn.prepareStatement(Update_Task_Status);
						pstmt.setInt(1,reminder);
						pstmt.setString(2,status); 
						pstmt.setInt(3,taskId);
				        pstmt.executeUpdate();
						String message="Add New Item!!!";
						
						
								request.setAttribute("taskName", request.getParameter("taskname"));
								request.setAttribute("message", message);
								request.setAttribute("taskId", request.getParameter("taskId"));
								RequestDispatcher rd = request.getRequestDispatcher("/AddTaskItem.jsp");
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
			else if(button_action.equalsIgnoreCase("Save.") || button_action.equalsIgnoreCase("Done"))
			{
				try {
					pstmt = conn.prepareStatement(Get_TaskId);
					pstmt.setString(1,  request.getParameter("taskname"));
					rs = pstmt.executeQuery();
					rs.next();
					int taskId=rs.getInt(1);
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
							status="Task Scheduled";
							pstmt = conn.prepareStatement(Update_Task_Status);
							pstmt.setInt(1,reminder);
							pstmt.setString(2,status); 
							pstmt.setInt(3,taskId);
					        pstmt.executeUpdate();
					        
					    	pstmt = conn.prepareStatement(Select_Task_Date_Time);
					        pstmt.setInt(1, taskId);
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				Date date1=rs.getDate(1);
		    				String time=rs.getString(2);
		    				status="Item Added Successfully!!!";
		    				String date;
							
								DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								
								 date = dateFormat.format(date1); 
							SubtaskDao subDao=new SubtaskDaoImp();
							List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
							List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
							
							request.setAttribute("taskName", request.getParameter("taskname"));
							request.setAttribute("listOfItem",listOfItem);
							request.setAttribute("listOfItem1",listOfItem1);
							request.setAttribute("taskId",taskId);
							request.setAttribute("date",date);
							request.setAttribute("time",time);
							request.setAttribute("status",status);
							RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
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
			else if(button_action.equalsIgnoreCase("+"))
			{
				int subtaskID=Integer.parseInt(request.getParameter("subtaskId")); 
				 SubtaskDao subDao=new SubtaskDaoImp();
				try
				{
					
					pstmt = conn.prepareStatement(Select_Subtask);
					
					pstmt.setInt(1, subtaskID);
					rs = pstmt.executeQuery();
					rs.next();
					int taskId=rs.getInt(1);
					pstmt = conn.prepareStatement(UPDATE_Subtask_Status1);
					pstmt.setInt(1,subtaskID);
					int result=pstmt.executeUpdate();
					if(result>=1)
					{
						pstmt = conn.prepareStatement(Select_Task_Date_Time);
	    				
	    				pstmt.setInt(1, taskId);
	    				rs = pstmt.executeQuery();
	    				rs.next();
	    				Date date1=rs.getDate(1);
	    				String time=rs.getString(2);
	    				
	    				String date;
						
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							
							 date = dateFormat.format(date1); 
						List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
						List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
						request.setAttribute("taskName",request.getParameter("taskname"));
						request.setAttribute("listOfItem",listOfItem);
						request.setAttribute("listOfItem1",listOfItem1);
						request.setAttribute("taskId",taskId);
						request.setAttribute("date",date);
						request.setAttribute("time",time);
						RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
						rd.forward(request, response);
					}
					else
					{
						String message="Error!!!";
						request.setAttribute("status", message);
						request.getRequestDispatcher("ViewTask").forward(request, response);
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
				int subtaskID=Integer.parseInt(request.getParameter("check1")); 
				String taskName=request.getParameter("taskname");
			    SubtaskDao subDao=new SubtaskDaoImp();
			 try
			 {
	            
					
					pstmt = conn.prepareStatement(UPDATE_Subtask_Status);
					pstmt.setInt(1,subtaskID);
					int result=pstmt.executeUpdate();
					if(result>=1)
		            {
		            	pstmt = conn.prepareStatement(Select_Subtask_Status_Count);
						
						pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
						rs = pstmt.executeQuery();
						rs.next();
						int count=rs.getInt(1);
						if(count<=0)
						{
							pstmt = conn.prepareStatement(Select_Task_Date_Time);
		    				
		    				pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				Date date1=rs.getDate(1);
		    				String time=rs.getString(2);
		    				
		    				String date;
							
								DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								
								 date = dateFormat.format(date1); 
							List<Subtask> listOfItem1 = subDao.findAllItemStatus(Integer.parseInt(request.getParameter("taskId")));
							String message="No Item in a list!!!";
	    					request.setAttribute("message", message);
							request.setAttribute("taskName",taskName);
							request.setAttribute("listOfItem1",listOfItem1);
							request.setAttribute("taskId",Integer.parseInt(request.getParameter("taskId")));
							request.setAttribute("date",date);
							request.setAttribute("time",time);
							RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
							rd.forward(request, response);
						}
						else
						{
							pstmt = conn.prepareStatement(Select_Subtask_Status_Count1);
		    				
		    				pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				int count1=rs.getInt(1);
		    				
		    				pstmt = conn.prepareStatement(Select_Task_Date_Time);
		    				
		    				pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				Date date1=rs.getDate(1);
		    				String time=rs.getString(2);
		    				
		    				String date;
							
								DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								
								 date = dateFormat.format(date1); 
								List<Subtask> listOfItem = subDao.findAllItemByID(Integer.parseInt(request.getParameter("taskId")));
								List<Subtask> listOfItem1 = subDao.findAllItemStatus(Integer.parseInt(request.getParameter("taskId")));
								String message=count1+" Item Completed!!!";
		    					request.setAttribute("status", message);
								request.setAttribute("taskName",taskName);
								request.setAttribute("taskId",Integer.parseInt(request.getParameter("taskId")));
								request.setAttribute("listOfItem",listOfItem);
								request.setAttribute("listOfItem1",listOfItem1);
								request.setAttribute("date",date);
								request.setAttribute("time",time);
								RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
								rd.forward(request, response);
						}
		            }
					else 
					{
						String message="Error!!!";
						request.setAttribute("status", message);
						request.getRequestDispatcher("/ViewTask.jsp").forward(request, response);
					}
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			}
			else if(button_action.equalsIgnoreCase("View Item"))
			{
				
				int taskId=Integer.parseInt(request.getParameter("taskId"));			
				System.out.println("Task ID IS:"+taskId);
				Task task=new Task();
				TaskDao taskDao=new TaskDaoImp();
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
						RequestDispatcher rd = request.getRequestDispatcher("/ViewAll.jsp");
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
	    					RequestDispatcher rd = request.getRequestDispatcher("/ViewAll.jsp");
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
			else if(button_action.equalsIgnoreCase("Remove"))
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
							status="Task Removed Successfully!!!";
							request.setAttribute("message", message);
							request.setAttribute("status", status);
							
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
						request.getRequestDispatcher("ViewAll.jsp").forward(request, response); 
					}
					
				}
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(button_action.equalsIgnoreCase("Edit"))
			{
				
			
				TaskDao taskDao=new TaskDaoImp();
				String itemName,itemStatus;
				
				List<Task> listOfTask;
				try {
					
					int subId=Integer.parseInt(request.getParameter("check1"));
					System.out.println("subID"+subId);
					pstmt = conn.prepareStatement(Select_Subtask);
					
					pstmt.setInt(1, subId);
					rs = pstmt.executeQuery();
					rs.next();
					int taskId=rs.getInt(1);
					itemName=rs.getString(2);
					itemStatus=rs.getString(3);
					pstmt = conn.prepareStatement(Select_Task_Name);
					
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					String taskName=rs.getString(1);
					//taskName=request.getParameter("taskname");
						listOfTask = taskDao.findAllTask();
						request.setAttribute("subId", subId);
						request.setAttribute("taskName", taskName);
						request.setAttribute("itemName", itemName);
						request.setAttribute("taskId", taskId);
					//	request.setAttribute("itemStatus", itemStatus);
						RequestDispatcher rd = request.getRequestDispatcher("/UpdateItemlist.jsp");
						rd.forward(request, response);
				} catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{

				}
		
				
			}
			else if(button_action.equalsIgnoreCase("Update"))
			{
				try 
				{
					String itemName=request.getParameter("itemname");
				//	String description=request.getParameter("description");
					int subtaskId=Integer.parseInt(request.getParameter("subId"));
					pstmt = conn.prepareStatement(Get_TaskId);
					
					pstmt.setString(1, request.getParameter("taskname"));
					rs = pstmt.executeQuery();
					rs.next();
					int taskId=rs.getInt(1);
					
					
					Subtask subtask=new Subtask();
					
					subtask.setTaskId(taskId);
					subtask.setName(itemName);
					subtask.setStatus(status1);
					subtask.setSubtaskID(subtaskId);
					
					SubtaskDao subDao=new SubtaskDaoImp();
					boolean result=subDao.updateSubtask(subtask);
					if(result)
					{
						pstmt = conn.prepareStatement(Select_Task_Date_Time);
	    				
	    				pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
	    				rs = pstmt.executeQuery();
	    				rs.next();
	    				Date date1=rs.getDate(1);
	    				String time=rs.getString(2);
	    				
	    				String date;
						
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							
							 date = dateFormat.format(date1); 
						
						List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
						List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
						String message="Item Updated Successfully!!!";
						request.setAttribute("status", message);
						request.setAttribute("taskName",request.getParameter("taskname"));
						request.setAttribute("listOfItem",listOfItem);
						request.setAttribute("listOfItem1",listOfItem1);
						request.setAttribute("taskId",taskId);
						request.setAttribute("date",date);
						request.setAttribute("time",time);
						RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
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
	
		/*if(button_action.equalsIgnoreCase("Done"))
		{
				Category category=new Category();
				System.out.println("action:"+button_action);
				category.setName(request.getParameter("categoryname1"));
				CategoryDao catDao=new CategoryDaoImp();
				try 
				{
					//catDao.addCategory(category);
					pstmt = conn.prepareStatement(Get_CategoryId);
					pstmt.setString(1, categoryName1);
			
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
		            task.setStatus(request.getParameter("status"));
		            task.setDescription(request.getParameter("description"));
		            
		            TaskDao taskDao=new TaskDaoImp();
		            try
		            {
		                boolean result=taskDao.addTask(task);
		                if(result)
		                {
		                    
		                    request.getRequestDispatcher("HomeServlet").forward(request, response); 
		                }
		                else
		                {
		                      request.getRequestDispatcher("AddTask.jsp").forward(request, response); 
		                }
		                        
				} 
				catch (SQLException e1) 
				{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
		            finally
			        {
			        	taskDao.close();
			        }
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  finally
			        {
					  catDao.close();
			        }
		}
		else
		{
				
		}
			}*/
	else if(button_action.equalsIgnoreCase("Remove"))
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
	else if(button_action.equalsIgnoreCase("ItemNew"))
	{
		categoryDao=null;
		TaskDao taskDao=new TaskDaoImp();
	
			
			List<Task> listOfTask=null;
			try 
			{
					listOfTask = taskDao.findAllTask();
			
					request.setAttribute("listOfTask", listOfTask);
					
					RequestDispatcher rd = request.getRequestDispatcher("/AddItem.jsp");
					rd.forward(request, response);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	else if(button_action.equalsIgnoreCase("CompleteTask"))
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
	else if(button_action.equalsIgnoreCase("Add Cat"))
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
	else if(button_action.equalsIgnoreCase("submit"))
	{
		String name=request.getParameter("name");
		String name2="Hello"+" "+name;
		request.setAttribute("name2", name2);
		request.getRequestDispatcher("/Test.jsp").forward(request, response); 
	}
	else
	{
	}
	
	}
}

			
	


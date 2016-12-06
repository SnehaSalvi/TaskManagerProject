

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

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
import com.google.gson.Gson;

  
@WebServlet(urlPatterns = {"/Task/*","/Tasks/*","/Items/*","/Category/*"})

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
	
	private static final String Select_Category ="SELECT name FROM mytaskdatabase.category";
	
	private static final String Select_Reminder ="select reminder from mytaskdatabase.task where id=?";
	
	private static final String UPDATE_Task_Reminder = "UPDATE mytaskdatabase.task set date=?, time=?, reminder=? where id=?";

	
	private static final String Select_task_id ="SELECT taskid FROM mytaskdatabase.subtask where name=?";
	private Connection conn;
    public TaskServlet() 
    {
        super();
       
    }
    
	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
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
	
	public void getPath()
	{
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
			response.setContentType("text/html");
			
			
			String path = request.getRequestURI().substring(request.getContextPath().length());
			System.out.println("PATH"+path);
			String status1="Item Scheduled";
			String button_action=request.getParameter("button");
			String mode=request.getParameter("mode");
			System.out.println("BUTTON: "+button_action);
			System.out.println("MODE: "+mode);
		
			 String str=null;
			 StringTokenizer st=new StringTokenizer(mode,"CompleteTask");
			 while(st.hasMoreTokens())
			 {
				str=st.nextToken();
			 }	
			 System.out.println("TOKEN:"+str);
			 
			 String str1=null;
			 st=new StringTokenizer(path,"/Tasks/");
			 while(st.hasMoreTokens())
			 {
				str1=st.nextToken();
			 }	
			 System.out.println("TOKEN:"+str1);
			 
			CategoryDao categoryDao=null;
			int categoryId;
			String status="Task Scheduled";
			String categoryName=request.getParameter("categoryname");
			String catName=request.getParameter("catname");
			System.out.println("CATEGORY NAME: "+catName);
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date12 = new Date();
			String task_date=dateFormat1.format(date12);
			System.out.println(task_date);//12:08:43
			dateFormat1 = new SimpleDateFormat("HH:mm:ss");
			date12 = new Date();
			String task_time=dateFormat1.format(date12);
			System.out.println(task_time);//12:08:43
		//	String categoryName1=request.getParameter("categoryname1");
			
			//Boolean active = Boolean.valueOf(request.getParameter("someField"));
			//to get a priimitive boolean
			//boolean action = active.booleanValue();
			//System.out.println("action"+action);
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			if(mode.equalsIgnoreCase("Taskview"))
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
			else if(mode.equalsIgnoreCase("ItemNew"))
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
			else if(mode.equalsIgnoreCase("CategoryNew"))
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
			else if(mode.equalsIgnoreCase("AddItem"))
			{
				System.out.println("INSIDE ADD ITEM>>>>>>>>>>>>>>>>>>");
				int taskId=Integer.parseInt(str1);			
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
	    				
	    				
	    				pstmt = conn.prepareStatement(Select_Reminder);
						pstmt.setInt(1, taskId);
						rs = pstmt.executeQuery();
						rs.next();
						int reminder=rs.getInt(1);
	    				String result=null;
	    				if(reminder==0)
	    				{
	    					result="OFF";
	    				}
	    				else
	    				{
	    					result="ON";
	    				}
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
						request.setAttribute("result",result);
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
							request.setAttribute("result",result);
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
			else if(mode.equalsIgnoreCase("Remind"))
			{
				int taskId=Integer.parseInt(request.getParameter("taskId"));
				request.setAttribute("taskId", taskId);
				
				RequestDispatcher rd = request.getRequestDispatcher("/AddReminder.jsp");
				rd.forward(request, response);
				
			}
			else if(mode.equalsIgnoreCase("RemindMe"))
			{
				int taskId=Integer.parseInt(str1);
				Date date1=null;
				int count;
				try 
				{
					
					pstmt = conn.prepareStatement(Select_Task_Name);
					
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					String taskName=rs.getString(1);
					String itemName=request.getParameter("itemname");
				//	String description=request.getParameter("description");
					date1 = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("mydate"));

					pstmt = conn.prepareStatement(UPDATE_Task_Reminder);
					pstmt.setDate(1, new java.sql.Date(date1.getTime()));
					pstmt.setString(2,request.getParameter("time"));
					pstmt.setInt(3, Integer.parseInt(request.getParameter("reminder")));
					pstmt.setInt(4,taskId);
					int result=pstmt.executeUpdate();
			        
					pstmt = conn.prepareStatement(Select_Reminder);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					int reminder=rs.getInt(1);
    				String result1=null;
    				if(reminder==0)
    				{
    					result1="OFF";
    				}
    				else
    				{
    					result1="ON";
    				}
					System.out.println("REMINDER STATUS+++++"+result1);
					Subtask subtask=new Subtask();
					SubtaskDao subDao=new SubtaskDaoImp();
					if(result>=1)
					{
						pstmt = conn.prepareStatement(Select_Task_Date_Time);
	    				
	    				pstmt.setInt(1, taskId);
	    				rs = pstmt.executeQuery();
	    				rs.next();
	    				date1=rs.getDate(1);
	    				String time=rs.getString(2);
	    				
	    				String date;
						
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							
							 date = dateFormat.format(date1); 
							 /*
						
						List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
						List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
				
						request.setAttribute("taskName",taskName);
						request.setAttribute("listOfItem",listOfItem);
						request.setAttribute("listOfItem1",listOfItem1);
						request.setAttribute("taskId",taskId);
						request.setAttribute("date",date);
						request.setAttribute("time",time);
						request.setAttribute("result",result1);
						RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
						rd.forward(request, response);*/
						
						pstmt = conn.prepareStatement(Select_Subtask_Status_Count);
	    				
	    				pstmt.setInt(1, taskId);
	    				rs = pstmt.executeQuery();
	    				rs.next();
	    				count=rs.getInt(1);
	    				if(count<=0)
	    				{
						subDao=new SubtaskDaoImp();
						List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
						
						String message="No Item in a list!!!";
						request.setAttribute("message", message);
						request.setAttribute("listOfItem1",listOfItem1);
						request.setAttribute("taskName",taskName);
						request.setAttribute("taskId",taskId);
						request.setAttribute("date",date);
						request.setAttribute("time",time);
						request.setAttribute("result",result1);
						RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
						rd.forward(request, response);
	    				}
	    				else
	    				{
	    					subDao=new SubtaskDaoImp();
	    					List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
	    					List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
	    					request.setAttribute("taskName",taskName);
	    					request.setAttribute("taskId",taskId);
	    					request.setAttribute("listOfItem",listOfItem);
	    					request.setAttribute("listOfItem1",listOfItem1);
	    					request.setAttribute("date",date);
							request.setAttribute("time",time);
							request.setAttribute("result",result1);
	    					RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
	    					rd.forward(request, response);
	    				}
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
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if(mode.equalsIgnoreCase("CompleteTask"+str))
			{
				System.out.println("INSIDE COMPLETE TASK>>>>>>>>>>>>>>>>>>");
				System.out.println("TASKIDDDD"+Integer.parseInt(str));
				int taskId=Integer.parseInt(str);
			try
			{
				pstmt = conn.prepareStatement(Delete_Task);
				pstmt.setInt(1,taskId);
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
			
			else if(mode.equalsIgnoreCase("createTask"))
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
			else if(mode.equalsIgnoreCase("Save"))
			{
				System.out.println("action:"+button_action);
				System.out.println("REMINDE????????????"+Integer.parseInt(request.getParameter("reminder")));
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
			else if(mode.equalsIgnoreCase("SaveItem") || mode.equalsIgnoreCase("Done"))
			{
				int taskId=Integer.parseInt(str1);
				System.out.println("TASKID:>>>>>"+taskId);
				try {
					
					System.out.println("taskId"+taskId);
					Subtask subtask=new Subtask();
					subtask.setTaskId(taskId);
					subtask.setName(request.getParameter("itemname"));
					subtask.setStatus(status1);				
					SubtaskDao subtaskDao=new SubtaskDaoImp();
					
					boolean result=subtaskDao.addSubtask(subtask);
					pstmt = conn.prepareStatement(Select_Reminder);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					int reminder1=rs.getInt(1);
    				String result1=null;
    				if(reminder1==0)
    				{
    					result1="OFF";
    				}
    				else
    				{
    					result1="ON";
    				}
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
							pstmt = conn.prepareStatement(Select_Task_Name);
					        pstmt.setInt(1, taskId);
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				String taskname=rs.getString(1);
							
							request.setAttribute("taskName",taskname);
							request.setAttribute("listOfItem",listOfItem);
							request.setAttribute("listOfItem1",listOfItem1);
							request.setAttribute("taskId",taskId);
							request.setAttribute("date",date);
							request.setAttribute("time",time);
							request.setAttribute("status",status);
							request.setAttribute("result",result1);
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
			else if(mode.equalsIgnoreCase("Edit"))
			{
				
				TaskDao taskDao=new TaskDaoImp();
				String itemName,itemStatus;
				
				List<Task> listOfTask;
				try {
					
					int subId=Integer.parseInt(request.getParameter("subId"));
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
					
					pstmt = conn.prepareStatement(Select_Reminder);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					int reminder1=rs.getInt(1);
    				String result1=null;
    				if(reminder1==0)
    				{
    					result1="OFF";
    				}
    				else
    				{
    					result1="ON";
    				}
					//taskName=request.getParameter("taskname");
						listOfTask = taskDao.findAllTask();
						request.setAttribute("subId", subId);
						request.setAttribute("taskName", taskName);
						request.setAttribute("itemName", itemName);
						request.setAttribute("taskId", taskId);
						request.setAttribute("result", result1);
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
			else if(mode.equalsIgnoreCase("Update"))
			{
				int taskId=Integer.parseInt(str1);
				try 
				{
					
					pstmt = conn.prepareStatement(Select_Task_Name);
					
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					String taskName=rs.getString(1);
					String itemName=request.getParameter("itemname");
				//	String description=request.getParameter("description");
					int subtaskId=Integer.parseInt(request.getParameter("subId"));
					pstmt = conn.prepareStatement(Get_TaskId);
					
					
					
					
					Subtask subtask=new Subtask();
					
					subtask.setTaskId(taskId);
					subtask.setName(itemName);
					subtask.setStatus(status1);
					subtask.setSubtaskID(subtaskId);
					
					SubtaskDao subDao=new SubtaskDaoImp();
					boolean result=subDao.updateSubtask(subtask);
					
					pstmt = conn.prepareStatement(Select_Reminder);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					int reminder1=rs.getInt(1);
    				String result1=null;
    				if(reminder1==0)
    				{
    					result1="OFF";
    				}
    				else
    				{
    					result1="ON";
    				}
					if(result)
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
						String message="Item Updated Successfully!!!";
						request.setAttribute("status", message);
						request.setAttribute("taskName",taskName);
						request.setAttribute("listOfItem",listOfItem);
						request.setAttribute("listOfItem1",listOfItem1);
						request.setAttribute("taskId",taskId);
						request.setAttribute("date",date);
						request.setAttribute("time",time);
						request.setAttribute("result",result1);
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
			else if(mode.equalsIgnoreCase("Complete"))
			{
				int subtaskID=Integer.parseInt(request.getParameter("subId")); 
				String taskName=null;
				int taskId=Integer.parseInt(str1);
			    SubtaskDao subDao=new SubtaskDaoImp();
			 try
			 {
				 	pstmt = conn.prepareStatement(Select_Task_Name);
					
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					taskName=rs.getString(1);
					
					pstmt = conn.prepareStatement(UPDATE_Subtask_Status);
					pstmt.setInt(1,subtaskID);
					int result=pstmt.executeUpdate();
					pstmt = conn.prepareStatement(Select_Reminder);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					int reminder1=rs.getInt(1);
    				String result1=null;
    				if(reminder1==0)
    				{
    					result1="OFF";
    				}
    				else
    				{
    					result1="ON";
    				}
					if(result>=1)
		            {
		            	pstmt = conn.prepareStatement(Select_Subtask_Status_Count);
						
						pstmt.setInt(1, taskId);
						rs = pstmt.executeQuery();
						rs.next();
						int count=rs.getInt(1);
						if(count<=0)
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
							List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
							String message="No Item in a list!!!";
	    					request.setAttribute("message", message);
							request.setAttribute("taskName",taskName);
							request.setAttribute("listOfItem1",listOfItem1);
							request.setAttribute("taskId",taskId);
							request.setAttribute("date",date);
							request.setAttribute("time",time);
							request.setAttribute("result",result1);
							RequestDispatcher rd = request.getRequestDispatcher("/ViewTask.jsp");
							rd.forward(request, response);
						}
						else
						{
							pstmt = conn.prepareStatement(Select_Subtask_Status_Count1);
		    				
		    				pstmt.setInt(1,taskId);
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				int count1=rs.getInt(1);
		    				
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
								String message=count1+" Item Completed!!!";
		    					request.setAttribute("status", message);
								request.setAttribute("taskName",taskName);
								request.setAttribute("taskId",taskId);
								request.setAttribute("listOfItem",listOfItem);
								request.setAttribute("listOfItem1",listOfItem1);
								request.setAttribute("date",date);
								request.setAttribute("time",time);
								request.setAttribute("result",result1);
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
			else if(mode.equalsIgnoreCase("AddToItem"))
			{
				int subtaskID=Integer.parseInt(request.getParameter("subId")); 
				 SubtaskDao subDao=new SubtaskDaoImp();
				try
				{
					
					pstmt = conn.prepareStatement(Select_Subtask);
					
					pstmt.setInt(1, subtaskID);
					rs = pstmt.executeQuery();
					rs.next();
					int taskId=rs.getInt(1);
					pstmt = conn.prepareStatement(Select_Task_Name);
					
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					String taskName=rs.getString(1);
					pstmt = conn.prepareStatement(UPDATE_Subtask_Status1);
					pstmt.setInt(1,subtaskID);
					int result=pstmt.executeUpdate();
					pstmt = conn.prepareStatement(Select_Reminder);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					int reminder1=rs.getInt(1);
    				String result1=null;
    				if(reminder1==0)
    				{
    					result1="OFF";
    				}
    				else
    				{
    					result1="ON";
    				}
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
						request.setAttribute("taskName",taskName);
						request.setAttribute("listOfItem",listOfItem);
						request.setAttribute("listOfItem1",listOfItem1);
						request.setAttribute("taskId",taskId);
						request.setAttribute("date",date);
						request.setAttribute("time",time);
						request.setAttribute("result",result1);
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
			else if(mode.equalsIgnoreCase("AddNewItem"))
			{
				
				try {
					pstmt = conn.prepareStatement(Get_TaskId);
			        pstmt.setString(1, request.getParameter("taskname"));
    				rs = pstmt.executeQuery();
    				rs.next();
    				int taskId=rs.getInt(1);
					
					Subtask subtask=new Subtask();
					subtask.setTaskId(taskId);
					subtask.setName(request.getParameter("itemname"));
					subtask.setStatus(status1);				
					SubtaskDao subtaskDao=new SubtaskDaoImp();
					
					boolean result=subtaskDao.addSubtask(subtask);
					
					pstmt = conn.prepareStatement(Select_Reminder);
					pstmt.setInt(1, taskId);
					rs = pstmt.executeQuery();
					rs.next();
					int reminder1=rs.getInt(1);
    				String result1=null;
    				if(reminder1==0)
    				{
    					result1="OFF";
    				}
    				else
    				{
    					result1="ON";
    				}
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
							pstmt = conn.prepareStatement(Select_Task_Name);
					        pstmt.setInt(1, taskId);
		    				rs = pstmt.executeQuery();
		    				rs.next();
		    				String taskname=rs.getString(1);
							
							request.setAttribute("taskName",taskname);
							request.setAttribute("listOfItem",listOfItem);
							request.setAttribute("listOfItem1",listOfItem1);
							request.setAttribute("taskId",taskId);
							request.setAttribute("date",date);
							request.setAttribute("time",time);
							request.setAttribute("status",status);
							request.setAttribute("result",result1);
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
			
			else if(mode.equalsIgnoreCase("Add Cat"))
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
							
		    					//listOfCategory=catDao.findCategory();
		    					response.getWriter().print(new Gson().toJson(request.getParameter("catname")));
		    					//String taskList=new Gson().toJson(listOfCategory);
		    					//	String listCat= taskList;
							
//								request.setAttribute("listOfCategory", listOfCategory);
//								request.getRequestDispatcher("/AddCategory.jsp").forward(request, response); 
		    				
		    			
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
		
	else
	{
	}

	}
}

			
	


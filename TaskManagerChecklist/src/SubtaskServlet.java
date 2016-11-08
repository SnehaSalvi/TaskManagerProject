

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
import com.task.dao.TaskDao;
import com.task.dao.Imp.SubtaskDaoImp;
import com.task.dao.Imp.TaskDaoImp;
import com.task.dto.Subtask;
import com.task.dto.Task;

@WebServlet("/SubtaskServlet")
public class SubtaskServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String Get_TaskId = "SELECT id FROM mytaskdatabase.task where name=?";
	private static final String Update_Task_Status="UPDATE mytaskdatabase.task SET reminder=?,status=? where id=?";
	private static final String Select_Subtask = "SELECT taskid,name,status FROM mytaskdatabase.subtask where id=?";
	private static final String Select_Task_Name = "SELECT name FROM mytaskdatabase.task where id=?";
	private static final String Delete_Subtask = "DELETE FROM mytaskdatabase.subtask where id=?";
	private static final String UPDATE_Subtask_Status = "UPDATE mytaskdatabase.subtask SET status='Item Completed' where id=?";
	private static final String UPDATE_Subtask_Status1 = "UPDATE mytaskdatabase.subtask SET status='Item Scheduled' where id=?";
	private static final String Select_Subtask_Count = "select count(id) from mytaskdatabase.subtask where taskid=?";
	private static final String Select_Subtask_Status_Count = "select count(id) from mytaskdatabase.subtask where taskid=? and status='Item Scheduled'";
	private static final String Select_Subtask_Status_Count1 = "select count(id) from mytaskdatabase.subtask where taskid=? and status='Item Completed'";
	
	private static final String Delete_Task = "DELETE FROM mytaskdatabase.task where id=?";
	
	private static final String Select_Task_Count = "select count(id) from mytaskdatabase.task";
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
					String message="Add New Item!!!";
					
					
							request.setAttribute("taskName", taskName);
							request.setAttribute("message", message);
							RequestDispatcher rd = request.getRequestDispatcher("AddTaskItem.jsp");
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
		else if(button.equalsIgnoreCase("Add"))
		{
			
			System.out.println("TaskName:"+taskName);
			request.setAttribute("taskName", taskName);
		
			RequestDispatcher rd = request.getRequestDispatcher("AddTaskItem.jsp");
			rd.forward(request, response);
		}
		else if(button.equalsIgnoreCase("Edit"))
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

			}
	
			
		}
		else if(button.equalsIgnoreCase("+"))
		{
			int subtaskID=Integer.parseInt(request.getParameter("subtaskId")); 
			 SubtaskDao subDao=new SubtaskDaoImp();
			try
			{
				pstmt = conn.prepareStatement(Select_Subtask);
				
				pstmt.setInt(1, subtaskID);
				rs = pstmt.executeQuery();
				rs.next();
				taskId=rs.getInt(1);
				pstmt = conn.prepareStatement(UPDATE_Subtask_Status1);
				pstmt.setInt(1,subtaskID);
				int result=pstmt.executeUpdate();
				if(result>=1)
				{
					List<Subtask> listOfItem = subDao.findAllItemByID(taskId);
					List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
					request.setAttribute("taskName",taskName);
					request.setAttribute("listOfItem",listOfItem);
					request.setAttribute("listOfItem1",listOfItem1);
					RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
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
		else if(button.equalsIgnoreCase("Complete"))
		{
			int subtaskID=Integer.parseInt(request.getParameter("check1")); 
			taskName=request.getParameter("taskname");
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
						List<Subtask> listOfItem1 = subDao.findAllItemStatus(Integer.parseInt(request.getParameter("taskId")));
						String message="No Item in a list!!!";
    					request.setAttribute("message", message);
						request.setAttribute("taskName",taskName);
						request.setAttribute("listOfItem1",listOfItem1);
						RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
						rd.forward(request, response);
					}
					else
					{
						pstmt = conn.prepareStatement(Select_Subtask_Status_Count1);
	    				
	    				pstmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));
	    				rs = pstmt.executeQuery();
	    				rs.next();
	    				int count1=rs.getInt(1);
							List<Subtask> listOfItem = subDao.findAllItemByID(Integer.parseInt(request.getParameter("taskId")));
							List<Subtask> listOfItem1 = subDao.findAllItemStatus(Integer.parseInt(request.getParameter("taskId")));
							String message=count1+" Item Completed!!!";
	    					request.setAttribute("status", message);
							request.setAttribute("taskName",taskName);
							request.setAttribute("listOfItem",listOfItem);
							request.setAttribute("listOfItem1",listOfItem1);
							RequestDispatcher rd = request.getRequestDispatcher("ViewTask.jsp");
							rd.forward(request, response);
					}
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
		/*else if(button.equalsIgnoreCase("delete"))
		{
			int subtaskID=Integer.parseInt(request.getParameter("check1")); 
			System.out.println("subtaskID"+subtaskID);
			int count;
			try 
			{
				
				taskName=request.getParameter("taskname");
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
		    					String status2="Status: Task Finished";
		    					pstmt = conn.prepareStatement(Update_Task_Status);
		    					pstmt.setInt(1,reminder);
		    					pstmt.setString(2,status); 
		    					pstmt.setInt(3,Integer.parseInt(request.getParameter("taskId")) );
		    			        pstmt.executeUpdate();
		    					String message="No Item in a list!!!";
		    					request.setAttribute("message", message);
		    					request.setAttribute("status",status2);
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
		               
	                if(result)
	                {
	                	List<Subtask> listOfItem = subDao.findAllItemByID(Integer.parseInt(request.getParameter("taskId")));
						String message="Record Deleted Successfully!!!";
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
		}*/
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
						List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
						
						request.setAttribute("taskName",taskName);
						request.setAttribute("listOfItem",listOfItem);
						request.setAttribute("listOfItem1",listOfItem1);
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
					List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
					String message="Item Updated Successfully!!!";
					request.setAttribute("status", message);
					request.setAttribute("taskName",taskName);
					request.setAttribute("listOfItem",listOfItem);
					request.setAttribute("listOfItem1",listOfItem1);
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
		else if(button.equalsIgnoreCase("Remove"))
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
						String status="Task Removed Successfully!!!";
						request.setAttribute("message", message);
						request.setAttribute("status", status);
						
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
		else if(button.equalsIgnoreCase("view"))
		{
			taskId=Integer.parseInt(request.getParameter("taskId"));
			SubtaskDao subDao=new SubtaskDaoImp();
			List<Subtask> listOfItem;
			try 
			{
				listOfItem = subDao.findAllItemByID(taskId);
				List<Subtask> listOfItem1 = subDao.findAllItemStatus(taskId);
				request.setAttribute("taskName",taskName);
				request.setAttribute("listOfItem",listOfItem);
				request.setAttribute("listOfItem1",listOfItem1);
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
		
	}
	public void destroy() 
	{
		
	}
}



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.websocket.server.PathParam;

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

@WebServlet(urlPatterns = {"/Home"})
public class HomeServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static final String Select_All_Category = "SELECT * FROM mytaskdatabase.category";
	
	private static final String Update_Task_Reminder="UPDATE mytaskdatabase.task SET reminder=0 where id=?";

	private static final String Select_All_Notification_Count = "select count(id) from mytaskdatabase.task where date<? and reminder=1";
	
	private static final String Select_All_Notification_Count_Today = "select count(id) from mytaskdatabase.task where date=? and time<=? and reminder=1";
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
		String path = request.getRequestURI().substring(request.getContextPath().length());
		System.out.println("PATH"+path);
		
		
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date12 = new Date();
		System.out.println("OLD Date: "+date12);
		String task_date=dateFormat1.format(date12);
		
		System.out.println(task_date);//12:08:43
		dateFormat1 = new SimpleDateFormat("HH:mm:ss");
		date12 = new Date();
		String task_time=dateFormat1.format(date12);
		System.out.println(task_time);
		
		
		PreparedStatement pstmt = null;
		
		ResultSet rs=null;
		
		TaskDao taskDao=new TaskDaoImp();
		if(path.equalsIgnoreCase("/Home"))
		{
			
			  try {
				
				  DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				  Date date_temp=format.parse(task_date);
			System.out.println("NEW DATE:"+date_temp);
				System.out.println("NEW DATE:"+date_temp);
		System.out.println("NEW TIME:"+task_time);
			pstmt = conn.prepareStatement(Select_All_Notification_Count);
			pstmt.setDate(1, new java.sql.Date(date_temp.getTime()));
			
			rs = pstmt.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			System.out.println("COUNT>>>>>>"+count);
			pstmt = conn.prepareStatement(Select_All_Notification_Count_Today);
			pstmt.setDate(1, new java.sql.Date(date_temp.getTime()));
			pstmt.setString(2, task_time);
			rs = pstmt.executeQuery();
			rs.next();
			int count1=rs.getInt(1);
			String message="No Pending Task!!!";
			if(count<=0 && count1<=0)
			{
				request.setAttribute("message",message);
				RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
				rd.forward(request, response);

			}
			else
			{
			List<Task> listOfTaskReminder = taskDao.findAllTaskByReminder(date_temp);
			List<Task> listOfTaskReminderToday = taskDao.findAllTaskByReminderToday(date_temp, task_time);
			request.setAttribute("listOfTaskReminderToday",listOfTaskReminderToday);
			request.setAttribute("listOfTaskReminder",listOfTaskReminder);
			RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
			rd.forward(request, response);
			}
			
			}
			catch (ParseException e) 
			{
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			} catch (SQLException e) {
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
		String mode=request.getParameter("mode");
		System.out.println("MODE:++++++++++"+mode);
		int taskId=Integer.parseInt(request.getParameter("taskId"));
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date12 = new Date();
	
		String task_date=dateFormat1.format(date12);
		
		System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
		dateFormat1 = new SimpleDateFormat("HH:mm:ss");
		date12 = new Date();
		String task_time=dateFormat1.format(date12);
	
		TaskDao taskDao=new TaskDaoImp();
		if(mode.equalsIgnoreCase("Close") || mode.equalsIgnoreCase("CloseReminder"))
		{
			
			try 
			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date date_temp=format.parse(task_date);
				System.out.println("NEW DATE:"+date_temp);
				
				
				pstmt = conn.prepareStatement(Update_Task_Reminder);
				pstmt.setInt(1,taskId);
				pstmt.executeUpdate();
				pstmt = conn.prepareStatement(Select_All_Notification_Count);
				pstmt.setDate(1, new java.sql.Date(date_temp.getTime()));
				
				rs = pstmt.executeQuery();
				rs.next();
				int count=rs.getInt(1);
				System.out.println("COUNT>>>>>>"+count);
				pstmt = conn.prepareStatement(Select_All_Notification_Count_Today);
				pstmt.setDate(1, new java.sql.Date(date_temp.getTime()));
				pstmt.setString(2, task_time);
				rs = pstmt.executeQuery();
				rs.next();
				int count1=rs.getInt(1);
				String message="No Pending Task!!!";
				if(count<=0 && count1<=0)
				{
					request.setAttribute("message",message);
					RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
					rd.forward(request, response);

				}
				else
				{
				
				System.out.println("HELLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
				
				
				List<Task> listOfTaskReminderToday = taskDao.findAllTaskByReminderToday(date_temp, task_time);
				List<Task> listOfTaskReminder = taskDao.findAllTaskByReminder(date_temp);
				request.setAttribute("listOfTaskReminderToday",listOfTaskReminderToday);
				request.setAttribute("listOfTaskReminder",listOfTaskReminder);
				RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
				rd.forward(request, response);
				}
				
			}
			catch(ParseException e)
			{
				e.printStackTrace();

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

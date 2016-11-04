

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
import com.task.dao.TaskDao;
import com.task.dao.Imp.CategoryDaoImp;
import com.task.dao.Imp.TaskDaoImp;
import com.task.dto.Category;
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
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
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
			
			
			CategoryDao categoryDao=null;
			int categoryId;
			String status="Task Scheduled";
			String categoryName=request.getParameter("categoryname");
			String categoryName1=request.getParameter("categoryname1");
			Boolean active = Boolean.valueOf(request.getParameter("someField"));
			//to get a priimitive boolean
			boolean action = active.booleanValue();
			System.out.println("action"+action);
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			if(!action)
			{
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
		                	String message="Record Inserted Successfully!!!";
		                	request.setAttribute("message", message);
		                    request.getRequestDispatcher("HomeServlet").forward(request, response); 
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
		else if(action)
		{
				Category category=new Category();
				
				category.setName(request.getParameter("categoryname1"));
				CategoryDao catDao=new CategoryDaoImp();
				try 
				{
					catDao.addCategory(category);
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
			}
	}
			
	


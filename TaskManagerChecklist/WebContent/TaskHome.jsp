<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/home.css" type="text/css" rel="stylesheet"/>
<link href="css/item.css" type="text/css" rel="stylesheet"/>
<link href="css/view.css" type="text/css" rel="stylesheet"/>
<link href="css/taskview.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">


</script>
</head>
<body>
<table id="table1">
		<tr>
			<td id="td1">
				<img id="banner" src="images/banner.jpg"/>
			</td>
			<td id="td_blank">
			</td>
			<td id="td2">
				Task Manager
			</td>
			<td id="td4" align="center">
				<a href="Home.jsp"><img id="list1" src="images/HomePic.png"/></a>
			</td>
			<td id="td3" align="center">
				<input type="button" name="remind" id="button1" value="Remind Me"/>
			</td>
			<td id="td4" align="center">
				<img id="list" src="images/list.png"/>
			</td>
		</tr>
	</table>
	<table></table>
	<table>
		<tr>
			<td>
			
				<table id="table2" border="1">
					<tr>
						<td id="#td5" align="center">
							<a href="http://localhost:8080/TaskManagerChecklist/HomeServlet?button=1"><input type="button" name="button" id="button2" value="Task"/></a>
						</td>
					</tr>
					<tr>
						<td align="center">
							<a href="http://localhost:8080/TaskManagerChecklist/HomeServlet?button=2"><input type="button" name="button" id="button2" value="Item"/></a>
						</td>
					</tr>
					<tr>
						<td align="center">
							<a href="http://localhost:8080/TaskManagerChecklist/HomeServlet?button=3"><input type="button" name="button" id="button2" value="View"/></a>
						</td>
					</tr>
					<tr>
						<td rowspan="7" align="center">
							<img id="img1" src="images/menu.jpg"/>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table id="table3">
					<tr>
						<td id="#td6">&nbsp;&nbsp;&nbsp;&nbsp;
						<table align="center" cellspacing="4" cellpadding="10">
								<tr>
									<td colspan="2">
										<input type="label" id="font1" name="label" value="Task List" readonly/>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">
									<div style="overflow:scroll;height:200px;width:100%;overflow:auto">
									
									<table id="tableView1" cellpadding="6" width="510" height="180" cellspacing="2">
										<c:forEach items="${ listOfTask }" var="task">
										<tr>
									
											<td colspan="3" bgcolor="#abecef">
										
												
													
												${task.name }
												 </td>
											<td align="right" bgcolor="#abecef" width="70">
											<form id="myform" action="HomeServlet" method="POST">		
												
											<input type="hidden" name="taskId" value="${task.taskId}"/>
											<input type="submit" id="editBTN" width="25" name="button" value="Add Item">
											</form>
											</td>
											
										</tr>
											</c:forEach> 
											
									</table>
										</div>	
								
									</td>
								</tr>
							
							<tr>
											<td colspan="2" align="right">
												<a href="http://localhost:8080/TaskManagerChecklist/HomeServlet?button=6"><input type="button" name="button" id="createBTN1" value="Create New Task"/></a>
											</td>
											</tr>
								
						</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
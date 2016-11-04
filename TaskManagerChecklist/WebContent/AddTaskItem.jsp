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
<script type="text/javascript">
function clearContent()
{
	document.getElementById("label2").value="";   
}
function revert()
{
	var task=document.getElementById("select1").value;   
	document.getElementById("select1").value=task;
}
</script>
</head>
<body>
<form action="SubtaskServlet" action="POST">
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
							<table align="center" cellspacing="6" cellpadding="4">
								<tr>
									<td colspan="2">
										<input type="label" id="font1" name="label" value="Add Item" readonly/>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">
										<%-- <select id="select1" name="taskname" placeholder="Task" onfocus="clearContent()" required="required">
												<option value="${ taskName }">${ taskName }</option> 
											
										</select> --%>
										<input type="text" name="taskname" id="default_text" size="30" value="${ taskName }" onfocus="clearContent()" required="required" readonly/>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="text" name="itemname" size="30" placeholder="Name" onfocus="clearContent()" required="required"/>
									</td>
									<td>&nbsp;</td>
								</tr>
								
								<!-- <tr>
									<td colspan="2">
										<input type="text" name="description" placeholder="Description" size="50" required="required"/>
									</td>
									<td>&nbsp;</td>
								</tr> -->
								<tr>
									<td colspan="2">
										
									</td>
									<td>
										<input type="submit" id="button1" name="button" value="Add More" onclick="revert()"/>&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" id="button1" name="button" value="Done"/>
									</td>
								</tr>
								<tr>
								<td  colspan="3"><input type="label" id="label2" name="message" value="${message}" size="6"/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
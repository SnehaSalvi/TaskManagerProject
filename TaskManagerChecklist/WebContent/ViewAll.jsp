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
<link href="../css/home.css" type="text/css" rel="stylesheet"/>
<link href="../css/item.css" type="text/css" rel="stylesheet"/>
<link href="../css/view.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
function submitFunction(i) 
{
	   if (i==1) 
		   {
		    
		   document.myView.action="../Tasks";
		   document.getElementById('Btn').value = "Remove";
		   }
	   if (i==3)  {
		    
		   document.myView.action="../Tasks";
		   document.getElementById('Btn').value = "Taskview";
		   }
	   if (i==4)  {
		    
		   document.myView.action="../Item/new";
		   document.getElementById('Btn').value = "ItemNew";
		   }
	   if (i==5)  {
		    
		   document.myView.action="../Tasks";
		   document.getElementById('Btn').value = "TasksList";
		   }
	   document.myView.submit()
	   }

</script>
</head>
<body>
<form name="myView" action="" method="POST">
<input type="hidden" id="Btn" name="button" value=""/>
<input type="hidden" name="taskname" value="${taskName}"/>
	<table id="table1">
		<tr>
			<td id="td1">
				<img id="banner" src="../images/banner.jpg"/>
			</td>
			<td id="td_blank">
			</td>
			<td id="td2">
				Task Manager
			</td>
			<td id="td4" align="center">
				<a href="Home.jsp"><img id="list1" src="../images/HomePic.png"/></a>
			</td>
			<td id="td3" align="center">
				<input type="button" name="remind" id="button1" value="Remind Me"/>
			</td>
			<td id="td4" align="center">
				<img id="list" src="../images/list.png"/>
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
							<input type="button" name="button" id="button2" value="Task" onClick="submitFunction(3)"/>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="button" name="button" id="button2" value="Item" onClick="submitFunction(4)"/>
						</td>
					</tr>
					<!-- <tr>
						<td align="center">
							<input type="button" name="button" id="button2" value="View" onClick="submitFunction(5)"/>
						</td>
					</tr> -->
					<tr>
						<td >
							<table id="setTb">
								<tr>
									<td id="setting" >Settings</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td id="menu"><ul><li type="disc"><a href="http://localhost:8080/TaskManagerChecklist/Category/new"><font color="white">Category</font></a></li></ul></td>
									
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="7" align="center">
							<img id="img1" src="../images/menu.jpg"/>
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
									<td colspan="2" id="font1">
										<input type="label" id="font1" name="label" value="Task List" readonly/>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">
									<table><tr><td>
									<div style="overflow:scroll;height:135px;width:100%;overflow:auto">
										<table id="tableView2" align="center" cellspacing="0" cellpadding="6" width="210" bgcolor="#abecef">
											<tr>
											<td align="left" bgcolor="#099aa2">	<input type="checkbox" id="check" name="check1" value="${taskId}" onfocus="clearContent()"/>	</td>
												<td bgcolor="#099aa2">
												<h4> ${taskName}</h4>
												</td>
											</tr>
											<c:forEach items="${ listOfItem }" var="item">
											<tr>
											<td colspan="2">
											
												*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ item.name }
											</td>
													
											</tr>
													
											</c:forEach> 
											<tr>
												<td colspan="2" align="center">
													<font color="red">${message}</font>
												</td>
											
											</tr>
									</table>
										</div>
										</td>
										</tr>
										</table>
									</td>
								</tr>
								
								<tr>
									<td colspan="2" align="right">
										<input type="button" id="removeBTN" name="button" value="Remove" onfocus="clearContent()" onClick="submitFunction(1)"/>
									</td>
								</tr>
								<tr>
								<td  colspan="2" align="center">
								<br/><input type="label" id="label2" name="message" value="${status}" size="6"/>
								</td>
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
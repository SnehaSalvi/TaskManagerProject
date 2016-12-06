<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/home.css" type="text/css" rel="stylesheet"/>
<link href="css/view.css" type="text/css" rel="stylesheet"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}
function forImg()
{
	$('#list1').mouseover( function(){
	    $('#nav_menu').slideDown();
	});
}

function submitFunction(i) 
{
	  
	   if (i==3)  {
		    
		   document.myView.action="Tasks";
		   document.getElementById('mode').value = "Taskview";
		   }
	   if (i==4)  {
		    
		   document.myView.action="Items/new";
		   document.getElementById('mode').value = "ItemNew";
		   }
	   if (i==5)  {
		    
		   document.myView.action="Tasks";
		   document.getElementById('mode').value = "TasksList";
		   }
	   if (i==6)  {
		    
		   document.myView.action="Category/new";
		   document.getElementById('mode').value = "CategoryNew";
		   }
	   document.myView.submit()
	   }
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
				<a href="Home"><img id="list1" src="images/HomePic.png"/></a>
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
			<form name="myView" action="" method="POST">
			<input type="hidden" id="mode" name="mode" value=""/>
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
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td align="center"><input type="button" name="button" id="button3" value="Category" onClick="submitFunction(6)" /></td>
									
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="7" align="center">
							<img id="img1" src="images/menu.jpg"/>
						</td>
					</tr>
				</table>
				</form>
			</td>
			
			<td align="center">
			
				<table id="table3">
								<tr>
									<td id="font1" align="right">
								<img src="images/alarm.png" border="0" height="30px" width="30px"/>
									</td>
									<td><font size="4"><i><b>Notifications:</b></i></font>
										<font color="red" size="3">Reminder</font>
									</td>
									<td colspan="4">&nbsp;</td>
								</tr>
								<tr>
									<td align="center" colspan="6"><font color="Red"><b>${message}</b></font>
									</td>
								</tr>
								<tr>
										<td  align="center" colspan="6">
										
								<div style="overflow:scroll;height:320px;width:100%;overflow:auto">
								
										<table id="tableView1" align="center" cellpadding="7" cellspacing="4" width="750">
														<c:forEach items="${ listOfTaskReminderToday }" var="task">
														<tr>
															<td colspan="3" width="270">${ task.name }</td>
															<td align="center"><fmt:formatDate type="date" value="${task.date}" var="tdate"/>${tdate}</td>
															<td align="center">${ task.time }</td>	
															<td align="center">
															<form name="myHome1" action="Home" method="POST">
																<input type="hidden" id="mode" name="mode" value="CloseReminder"/>
																	<input type="hidden" id="taskId" name="taskId" value="${task.taskId}"/>
															<input type="image" src="images/Close-icon.png" border="0" alt="Submit" height="25px" width="25px"/>

															</form>
															</td>		
																		
														</tr>
														
														</c:forEach>
													
														
																<c:forEach items="${ listOfTaskReminder }" var="task">
														<tr>
															<td colspan="3" width="270">${ task.name }</td>
															<td align="center"><fmt:formatDate type="date" value="${task.date}" var="tdate"/>${tdate}</td>
															<td align="center">${ task.time }</td>	
															<td align="center">
															<form name="myHome" action="Home" method="POST">
																<input type="hidden" id="mode" name="mode" value="Close"/>
																	<input type="hidden" id="taskId" name="taskId" value="${task.taskId}"/>
															<input type="image" src="images/Close-icon.png" border="0" alt="Submit" height="25px" width="25px"/>

															</form>
															</td>		
																		
														</tr>
														
														</c:forEach>
											</table>
											</div>
										</td>
								</tr>
				</table>
			</td>
		</tr>
	</table>

<!--<form action="Hello">
Name:<input type="text" name="user"/>
<input type="submit" value="go"/>
</form>-->
</body>
</html>
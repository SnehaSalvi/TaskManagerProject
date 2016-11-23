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
<link href="../css/viewItem.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
function submitForm()
{
    document.getElementById('myView').action = 'http://localhost:8080/TaskManagerChecklist/Task/${taskId}/Item';
    document.getElementById('myView').submit();
}
function submitFunction(i) {
	  
	    if (i==2)  {
		    
		   document.myView.action="http://localhost:8080/TaskManagerChecklist/Task/${taskId}/Item/edit";
		   document.getElementById('Btn').value = "Edit";
		   }
	/*   if (i==3)  {
		    
		   document.myView.action="http://localhost:8080/TaskManagerChecklist/Task/${taskId}";
		   document.getElementById('Btn').value = "Complete";
	   }  */
	   if (i==4)  {
		    
		   document.myView.action="../Tasks";
		   document.getElementById('Btn').value = "Taskview";
		   }
	   if (i==5)  {
		    
		   document.myView.action="../Item/new";
		   document.getElementById('Btn').value = "ItemNew";
		   }
	   if (i==6)  {
		    
		   document.myView.action="../Tasks";
		   document.getElementById('Btn').value = "TasksList";
		   }
	   document.myView.submit()
	   }
	   
	 
function EditAll()
{
	 //document.getElementById('check').value =
		 
/* var data = document.forms[0].check1;
var i;
for (i=0;i<data.length;i++)
  {
  if (data[i].checked)
    {
	
			 window.open(""+data[i].value+"&button=edit");
    }
  } */
 
	document.getElementById('editBTN').submit;
}
function RemoveAll()
{

	 //document.getElementById('check').value ='SubtaskServlet?button=5&subtaskId=<c:out value="${item.subtaskID}"/>&taskId=<c:out value="${item.taskId}"/>&itemName=<c:out value="${item.name}"/>&itemDescription=<c:out value="${item.description}"/>'

 /* var data = document.forms[0].check1;

var i;
for (i=0;i<data.length;i++)
  {
  if (data[i].checked)
    {
	 	//alert("hjhjhjhjh"+data[i].value+"&action=delete");
	// window.open(""+data[i].value+"&button=delete");
	data[i].value+"&button=delete";
    }
  }  */
 
document.getElementById('removeBTN').submit;
}
function clearContent()
{
	

	document.getElementById("label2").value="";   
	document.getElementById("label3").value="";   
}
</script>
</head>
<body>
	
		
		<table id="table1">
			<tr>
				<td id="td1"><img id="banner" src="../images/banner.jpg" /></td>
				<td id="td_blank"></td>
				<td id="td2">Task Manager</td>
				<td id="td4" align="center"><a href="../Home.jsp"><img
						id="list1" src="../images/HomePic.png" /></a></td>
				<td id="td3" align="center"><input type="button" name="remind"
					id="button1" value="Remind Me" /></td>
				<td id="td4" align="center"><img id="list"
					src="../images/list.png" /></td>
			</tr>
		</table>
		<table></table>
		<table>
			<tr>
				<td>
		<form name="myView" action="" method="POST">
		<input type="hidden" name="taskname" value="${taskName}" />
		<input type="hidden" name="taskId" value="${taskId}" />
			<input type="hidden" id="Btn" name="button" value=""/>
					<table id="table2" border="1">
						<tr>
						<td id="#td5" align="center">
							<input type="button" name="button" id="button2" value="Task" onClick="submitFunction(4)"/>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="button" name="button" id="button2" value="Item" onClick="submitFunction(5)"/>
						</td>
					</tr>
					<!-- <tr>
						<td align="center">
							<input type="button" name="button" id="button2" value="View" onClick="submitFunction(6)"/>
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
					</form>
				</td>
				<td>
					<table id="table3">
						<tr>
							<td id="#td6">&nbsp;&nbsp;&nbsp;&nbsp;
								<table align="center" cellspacing="4" cellpadding="10">
									<tr>
										<td colspan="3" id="font1"><input type="label" id="font1" 
											name="label" value="Task List" readonly />
										<font size="2"><b>Completion Date:</b>${date}</font> 
									&nbsp;&nbsp;	<font size="2"><b>Time:</b>${time}</font></td>
										
									</tr>
									<tr>
										<td colspan="2" align="center">
											<table>
												<tr>
													<td>
														<div style="overflow: scroll; height: 135px; width: 100%; overflow: auto">
															<table id="tableView3" align="center" cellspacing="0"
																cellpadding="7" width="300" bgcolor="#abecef">
																<tr>
																	<th colspan="5" bgcolor="#099aa2">${taskName}</th>
																</tr>
																<c:forEach items="${ listOfItem }" var="item">
																	<tr>
																		
																		<td colspan="3">
																		
																	<%-- 	<input type="checkbox" id="check" name="check1" value="${item.subtaskID}" onfocus="clearContent()" />  --%>
																	<font size="3">	${ item.name } </font>
																	
																		
																		</td>
																		<td>
																		<form action="../Task/${item.taskId}/Item/edit" method="POST">
																		
																		<input type="hidden" name="taskId" value="${item.taskId}" />
																		<input type="hidden" name="taskname" value="${taskName}" />
																		<input type="hidden" name="check1" value="${item.subtaskID}" />
																		<input type="submit" id="addbtn" width="10" name="button" value="Edit" onClick="submitFunction(1)"/>
																		
																		
																		</form>
																		</td>
																		<td>
																		<form id="myform" name="myView1" action="../Task/${item.taskId}" method="POST">
																		
																		<input type="hidden" name="taskId" value="${item.taskId}" />
																			<input type="hidden" name="taskname" value="${taskName}" />
																		<input type="hidden" name="check1" value="${item.subtaskID}" />
																		<input type="submit" id="addbtn" name="button" value="Complete" onfocus="clearContent()"/>
																		
																		</form>
																		</td>
																	</tr>

																</c:forEach>
																<tr>
																	<td colspan="5" align="center"><font color="red">${message}</font>
																	</td>

																</tr>
															</table>
														</div>
													</td>
												</tr>
												<tr>
													<td>
														<div
															style="overflow: scroll; height: 110px; width: 100%; overflow: auto">
															<table id="tableView3" align="center" cellspacing="0"
																cellpadding="7" width="300" bgcolor="#abecef">
																<hr>
																<tr>

																	<th colspan="2" align="left">:: List of Completed
																		Items</th>
																</tr>
																<c:forEach items="${ listOfItem1 }" var="item1">
																	<tr>
																		<td><s>${ item1.name }</s></td>
																		<td>
																			<form id="myform" name="myView1" action="../Task/${item1.taskId}" method="POST">
																				<input type="hidden" name="taskId" value="${item1.taskId}" />
																				<input type="hidden" name="subtaskId"
																					value="${item1.subtaskID}" />
																						<input type="hidden" name="taskname" value="${taskName}" />
																				<%-- 	<input type="hidden" name="taskId" value="${item1.taskId}"/></td> --%>
																				<input type="submit" id="addbtn" width="2"
																					height="2" name="button" value="+">
																					
																			</form>
																		</td>
																		<td>
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

									<tr>
									<form action="../Task/${taskId}" method="POST">
									<input type="hidden" name="taskname" value="${taskName}" />
									<input type="hidden" name="taskId" value="${taskId}" />
										<input type="hidden" id="Btn3" name="button" value="Done."/>
										<td align="center" colspan="2">
									<input type="text" name="itemname" size="26" placeholder="New Item" onfocus="clearContent()" required="required"/>
									<input type="submit"
											id="removeBTN" name="button1" value="Add" 
											onfocus="clearContent()" />
											</td>
											</form>
									</tr>
									<tr>
										<td colspan="2" align="center"><br /> 
									<%-- 	<input type="label" id="label2" name="message1" value="${show}" size="6" /> --%>
										<font color="red">
										<input type="label" id="label2" name="message" value="${status}" size="6" border="none" />
										</font>
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
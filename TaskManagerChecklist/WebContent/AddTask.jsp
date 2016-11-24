<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/scripts/jquery.min.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../css/home.css" type="text/css" rel="stylesheet"/>
<link href="../css/task.css" type="text/css" rel="stylesheet"/>
<link href="../css/view.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
 <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
	  <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
	  <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/scripts/jquery.min.js" type="text/javascript"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script type="text/javascript">

	$(function() 
	{
	    $( "#datepicker1" ).datepicker();
	    $('#timepicker1').timepicker();

	});
	function submitFunction(i) 
	{
		   if (i==1) 
			   {
			    
			   document.myView.action="../Tasks";
			   document.getElementById('Btn').value = "Save";
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
/* function makeDisable()
{
    var x=document.getElementById("select1")
    x.disabled=true

	var d=document.getElementById("newCatT");
	d.innerHTML+="<p><input type='text' name='categoryname1' placeholder='Category' required='required'>";
	
	 var d=document.getElementById("newCatT1");
	 d.innerHTML+="<p><input type='button' name='addcat' value='Add' onclick='setValue()'>"; 
	 var action = true;
		document.getElementById("someFieldId").value = action;

		
	var x=document.getElementById("CatBTN")
    x.disabled=true
    
    document.form[0].submit();
   // action=true;
 
	
}
*/

function clearContent()
{
	document.getElementById("label3").value="";   
}

 </script>
 <style type="text/css">
.ui-datepicker {
   background: #333;
   border: 1px solid #555;
   color: #EEE;
	font-size: 2;

 }
</style>



</head>
<body>
<input type="hidden" >

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
				<a href="../Home.jsp"><img id="list1" src="../images/HomePic.png"/></a>
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
			<form name="myView" action="" method="POST">
			<input type="hidden" id="Btn" name="button" value=""/>
			<input type="hidden" name="someField" id="someFieldId" />
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
				</form>
			</td>
			<td>
			<form action="../Tasks" method="POST">
				<table id="table3">
				
					<tr>
						<td id="#td6">&nbsp;&nbsp;&nbsp;&nbsp;
							<table align="center" cellspacing="6" cellpadding="4">
								
								<tr>
									<td colspan="2">
										<input type="label" id="font1" name="label" value="Add Task" readonly/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="text" name="taskname" placeholder="Name" size="50" required="required" onfocus="clearContent()" />
									</td>
								</tr>
								
								<tr>
								
									<td colspan="2">
										 <select id="select1" name="categoryname" placeholder="Category" required="required">
										 <option value="">Please Select Category</option>
											<c:forEach items="${ listOfCategory }" var="cat">
												<option value="${ cat.name }">${ cat.name }</option> 
											</c:forEach>
										</select>
									<!-- 	<input type="button" id="CatBTN" name="newselect" value="Add category" onclick="makeDisable()"/> -->
									</td>
									
								</tr>
								
								<tr>
									<td id="newCatT">
										
									</td>
									<td id="newCatT1" align="left">
									<%-- <%
       									 String x = request.getParameter("addcat");
											if (x != null && x.equals("Add"))
											{
           										 //response.sendRedirect("/Initial");
          									  RequestDispatcher dispatcher = request.getRequestDispatcher("/TaskServlet");
           										 dispatcher.forward(request, response);
           									}
           									%> --%>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="label" id="label2" name="newdate" value="Date" size="6" readonly/>
										<input type="text" name="mydate" class="tid" id="datepicker1"/>
									<!-- 	<input type="date" name="mydate" class="tid" required="required"/> -->
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="label" id="label2" name="newtime" value="Time" size="6" readonly/>
										<input type="time" name="time" placeholder="Time" size="30" required="required" step="1"/>
								<!-- 	  <input id="timepicker1" type="text"  name="time" class="input-small"> -->
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input id="label2" type="label" value="Reminder" size="7" readonly/>
										Off<input type="radio" id="radio1" name="reminder" value="0" checked required="required"/>
										On<input type="radio" id="radio1" name="reminder" value="1" required="required"/>
									</td>
								</tr>
								<!-- <tr>
									<td colspan="2">
										 <select id="select2" name="status" placeholder="Status" required="required">
										 <option value="">Please Select Status</option>
											<option value="Task Finished">Task Finished</option> 
											<option value="Task Unfinished">Task Unfinished</option>
										</select>
									</td>
								</tr> -->
								<tr>
									<td>
										<input type="text" name="description" placeholder="Description" size="50" required="required"/>
									</td>
									<td>
										<input type="submit" name="button" id="button1" value="Save" placeholder="Done"/>
									</td>
								</tr>
								<tr>
								<td  colspan="2" align="center"><input type="label" id="label3" name="message" value="${message}" size="6"/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</form>
			</td>
		</tr>
	</table>

<!--<form action="Hello">
Name:<input type="text" name="user"/>
<input type="submit" value="go"/>
</form>-->
</body>
</html>
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

<link href="../../css/home.css" type="text/css" rel="stylesheet"/>
<link href="../../css/task.css" type="text/css" rel="stylesheet"/>

<link href="../../css/view.css" type="text/css" rel="stylesheet"/>
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
			 $( "#datepicker1" ).datepicker({ dateFormat: 'mm/dd/yy',  beforeShowDay: NotBeforeToday});
		    $('#timepicker1').timepicker();

		});
function NotBeforeToday(date)
{
    var now = new Date();//this gets the current date and time
    if (date.getFullYear() == now.getFullYear() && date.getMonth() == now.getMonth() && date.getDate() >= now.getDate())
        return [true];
    if (date.getFullYear() >= now.getFullYear() && date.getMonth() > now.getMonth())
       return [true];
     if (date.getFullYear() > now.getFullYear())
       return [true];
    return [false];
}
function clearContent()
{
	document.getElementById("label2").value="";   
}
function revert()
{
	var task=document.getElementById("select1").value;   
	document.getElementById("select1").value=task;
}
function submitFunction(i) 
{
	 if (i==3)  {
		    
		   document.myView.action="../../Tasks";
		   document.getElementById('mode').value = "Taskview";
		   }
	   if (i==4)  {
		    
		   document.myView.action="../../Items/new";
		   document.getElementById('mode').value = "ItemNew";
		   }
	   if (i==5)  {
		    
		   document.myView.action="../../Tasks";
		   document.getElementById('mode').value = "TasksList";
		   }
	   if (i==6)  {
		    
		   document.myView.action="../../Category/new";
		   document.getElementById('mode').value = "CategoryNew";
		   }
	   document.myView.submit()
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
<table id="table1">
		<tr>
			<td id="td1">
				<img id="banner" src="../../images/banner.jpg"/>
			</td>
			<td id="td_blank">
			</td>
			<td id="td2">
				Task Manager
			</td>
			<td id="td4" align="center">
				<a href="../../Home"><img id="list1" src="../../images/HomePic.png"/></a>
			</td>
			<td id="td3" align="center">
				<input type="button" name="remind" id="button1" value="Remind Me"/>
			</td>
			<td id="td4" align="center">
				<img id="list" src="../../images/list.png"/>
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
							<img id="img1" src="../../images/menu.jpg"/>
						</td>
					</tr>
				</table>
				</form>
			</td>
			<td>
				<table id="table3">
					<tr>
						<td id="#td6">&nbsp;&nbsp;&nbsp;&nbsp;
						<form action="../../Tasks/${taskId}" method="POST">
							<input type="hidden" id="mode" name="mode" value="RemindMe"/>
					
							<table align="center" cellspacing="6" cellpadding="4">
							<tr>
								<td  colspan="3" align="center"><input type="label" id="label2" name="message" value="${message}" size="6"/></td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="label" id="font1" name="label" value="Pick Date & Time" readonly/>
									</td>
									<td>&nbsp;</td>
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
										<input type="text" name="description" placeholder="Description" size="50" required="required"/>
									</td>
									<td>&nbsp;</td>
								</tr> -->
								<tr>
									<td colspan="2">
										
									</td>
									<td>
										<input type="submit" id="button1" name="button" value="Save"/>
									</td>
								</tr>
								
							</table>
							</form>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

</body>
</html>
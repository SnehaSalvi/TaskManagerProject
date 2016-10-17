<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="home.css" type="text/css" rel="stylesheet"/>
<link href="task.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<form>
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
							<input type="button" name="task" id="button2" value="Task" onclick="location.href='AddTask.jsp';"/>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="button" name="item" id="button2" value="Item" onclick="location.href='AddItem.jsp';"/>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="button" name="view" id="button2" value="View"/>
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
										<input type="label" id="font1" name="label" value="Add Task" required="required"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="text" name="taskname" placeholder="Name" size="50" required="required"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<select id="select1" name="categoryname" placeholder="Category" value="Category" required="required">
											<option value="" disabled selected hidden>Category</option>
											<option>Personal</option>
											<option>Professional</option>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="text" name="categoryname" size="30" required="required"/>
										<input type="label" id="label1" name="newcategory" value="* Add new Category" required="required"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="label" id="label2" name="newdate" value="Date" size="6" required="required"/>
										<input type="date" name="date" required="required"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="text" name="time" placeholder="Time" size="30" required="required"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input id="label2" type="label" value="Reminder" size="6" required="required"/>
										Off<input type="radio" name="reminder" checked/>
										On<input type="radio" name="reminder"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="text" name="status" placeholder="Status" size="50" required="required"/>
									</td>
								</tr>
								<tr>
									<td>
										<input type="text" name="description" placeholder="Description" size="50" required="required"/>
									</td>
									<td>
										<input type="submit" id="button1" value="Done"/>
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
<!--<form action="Hello">
Name:<input type="text" name="user"/>
<input type="submit" value="go"/>
</form>-->
</body>
</html>
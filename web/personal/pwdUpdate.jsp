<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/9/30
  Time: 15:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <base href="<%=basePath%>"/>
    <title>修改密码页面</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">个人信息</a></li>
        <li><a href="#">修改密码</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>修改密码</span></div>

    <form action="servlet/employeeServlet?method=updatePwd" method="post">
        <ul class="forminfo">
            <li><label>用户名</label> <label>${employee.empId}</label></li>
            <input type="hidden" name="empId" value="${employee.empId}">
            <li><label>旧密码</label><input name="oldPwd" type="password" class="dfinput"/><i></i></li>
            <li><label>新密码</label><input name="newPwd" type="password" class="dfinput"/><i></i></li>
            <li><label>新确认密码</label><input name="" type="password" class="dfinput"/></li>
            <li><label>&nbsp;</label><input name="" type="submit" class="btn" value="确认修改"/></li>
        </ul>
    </form>
    <c:if test="${error == true}">
        <span style="font-size: 18px; font-weight: bold; color: red">旧密码错误</span>
    </c:if>

</div>


</body>

</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/3
  Time: 14:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>顶部框架</title>

    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="js/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            //顶部导航切换
            $(".nav li a").click(function () {
                $(".nav li a.selected").removeClass("selected")
                $(this).addClass("selected");
            })
        })
    </script>


</head>

<body style="background:url(images/topbg.gif) repeat-x;">

<div class="topleft">
    <a href="main.jsp" target="_parent"><img src="images/logo.png" title="系统首页"/></a>
</div>

<ul class="nav">

    <c:if test="${sessionScope.currUser.empType==2}">
        <li><a href="system/deptAdd.jsp" target="rightFrame" class="selected"><img src="images/icon01.png"
                                                                                   title="添加部门"/>
            <h2>添加部门</h2></a></li>
        <li><a href="servlet/employeeServlet?method=showAddEmployee" target="rightFrame"><img src="images/icon02.png"
                                                                                              title="添加员工"/>
            <h2>添加员工</h2></a></li>
        <li><a href="servlet/employeeServlet?method=findEmployees" target="rightFrame"><img src="images/icon03.png"
                                                                                            title="员工管理"/>
            <h2>员工管理</h2></a></li>
    </c:if>

    <li><a href="expense/expenseAdd.jsp" target="rightFrame"><img src="images/icon04.png" title="添加报销"/>
        <h2>添加报销</h2></a></li>
    <li><a href="duty/dutyAdd.jsp" target="rightFrame"><img src="images/icon05.png" title="签到签退"/>
        <h2>签到签退</h2></a></li>
    <li><a href="myInfo.html" target="rightFrame"><img src="images/icon06.png" title="我的信息"/>
        <h2>我的信息</h2></a></li>
</ul>

<div class="topright">
    <ul>
        <li><span><img src="images/help.png" title="帮助" class="helpimg"/></span><a href="tech.html" target="rightFrame">帮助</a>
        </li>
        <li><a href="servlet/employeeServlet?method=logout" target="_parent">退出</a></li>
    </ul>

    <div class="user">
        <span><a href="myInfo.html" target="rightFrame">${sessionScope.currUser.empId}</a></span>
    </div>

</div>

</body>
</html>



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/10
  Time: 11:58
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
    <title>审核历史</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $(".click").click(function () {
                $(".tip").fadeIn(200);
            });
            $(".tiptop a").click(function () {
                $(".tip").fadeOut(200);
            });
            $(".sure").click(function () {
                $(".tip").fadeOut(100);
            });
            $(".cancel").click(function () {
                $(".tip").fadeOut(100);
            });
        });
    </script>

</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">报销管理</a></li>
        <li><a href="#">查看审核记录</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="formtitle1"><span>审核记录</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>审核人<i class="sort"><img src="images/px.gif"/></i></th>
            <th>审核结果</th>
            <th>审核意见</th>
            <th>审核时间</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${auditingList}" var="audit">
            <tr>
                <td>${audit.employee.realName}</td>
                <td>${audit.result}</td>
                <td>${audit.auditDesc}</td>
                <td>${audit.time}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<input name="ret" type="button" class="btn" value="返回"/>
<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');

    $("input[name=ret]").click(function () {
        history.back();
    })

</script>

</body>

</html>
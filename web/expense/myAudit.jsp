<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/10
  Time: 16:58
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
    <title>我的审核历史</title>
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
        <li><a href="#">我的审核历史</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="formtitle1"><span>我的审核</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>报销人<i class="sort"><img src="images/px.gif"/></i></th>
            <th>报销总额</th>
            <th>报销时间</th>
            <th>审核时间</th>
            <th>总备注信息</th>
            <th>查看具体报销项</th>
            <th>查看所附图片</th>
            <th>审核结果</th>
            <th>查看审核历史</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${auditingList}" var="audit">
            <tr>
                <td>${audit.expense.employee.realName}</td>
                <td>${audit.expense.totalAmount}</td>
                <td>${audit.expense.expTime}</td>
                <td>${audit.time}</td>
                <td>${audit.expense.expDesc}</td>
                <td><a href="servlet/expenseServlet?method=toExpenseDetail&expId=${audit.expId}" class="tablelink"> 查看具体报销项</a></td>
                <td><a href="servlet/expenseServlet?method=toExpenseImg&expId=${audit.expId}" class="tablelink">查看所附图片</a></td>
                <c:if test="${audit.expense.status == 1}">
                    <td> 审核中</td>
                </c:if>
                <c:if test="${audit.expense.status == 2}">
                    <td> 通过</td>
                </c:if>
                <c:if test="${audit.expense.status == 3}">
                    <td> 驳回</td>
                </c:if>
                <td><a href="servlet/auditServlet?method=toAuditHistory&expId=${audit.expId}" class="tablelink">查看审核记录</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>

</body>

</html>
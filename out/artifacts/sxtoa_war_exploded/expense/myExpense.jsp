<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/8
  Time: 11:41
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
    <title>我的报销列表</title>
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
        <li><a href="#">我的报销</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="formtitle1"><span>我的报销</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>编号</th>
            <th>报销总额</th>
            <th>报销时间</th>
            <th>总备注信息</th>
            <th>查看具体报销项</th>
            <th>查看所附图片</th>
            <th>状态</th>
            <th>下一个审核人</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${expenses}" var="expense">
            <tr>
                <td>${expense.expId}</td>
                <td>${expense.totalAmount}</td>
                <td>${expense.expTime}</td>
                <td>${expense.expDesc}</td>
                <td><a href="servlet/expenseServlet?method=toExpenseDetail&expId=${expense.expId}" class="tablelink"> 查看具体报销项</a></td>
                <td><a href="servlet/expenseServlet?method=toExpenseImg&expId=${expense.expId}" class="tablelink">查看所附图片</a></td>
                <c:choose>
                    <c:when test="${expense.status == 1}">
                        <td>正在审核</td>
                    </c:when>
                    <c:when test="${expense.status == 2}">
                        <td>审核通过</td>
                    </c:when>
                    <c:when test="${expense.status == 3}">
                        <td>审核驳回</td>
                    </c:when>
                </c:choose>
                <td>${expense.auditor.realName}</td>
                <td><a href="servlet/auditServlet?method=toAuditHistory&expId=${expense.expId}" class="tablelink">查看审核记录</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">
        <div class="message">共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页</div>
        <ul class="paginList">
            <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
            <li class="paginItem"><a href="javascript:;">1</a></li>
            <li class="paginItem current"><a href="javascript:;">2</a></li>
            <li class="paginItem"><a href="javascript:;">3</a></li>
            <li class="paginItem"><a href="javascript:;">4</a></li>
            <li class="paginItem"><a href="javascript:;">5</a></li>
            <li class="paginItem more"><a href="javascript:;">...</a></li>
            <li class="paginItem"><a href="javascript:;">10</a></li>
            <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
        </ul>
    </div>

</div>
<span style="color: red; font-weight: bold; font-size: 20px">${error}</span>


<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>

</body>

</html>
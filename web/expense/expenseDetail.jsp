<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/8
  Time: 14:00
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
    <title>报销项详情</title>
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
        <li><a href="#">查看具体报销项</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="formtitle1"><span>具体报销项</span></div>

    <table class="tablelist">
        <thead>
        <tr>
<%--            <th><input name="" type="checkbox" value="" checked="checked"/></th>--%>
            <th>报销项编号<i class="sort"><img src="images/px.gif"/></i></th>
            <th>报销项类型</th>
            <th>报销项金额</th>
            <th>报销项说明</th>
        </tr>
        </thead>
        <tbody>
        <C:forEach items="${expenseItemList}" var="item">
            <tr>
<%--                <td><input name="" type="checkbox" value=""/></td>--%>
                <td>${item.itemId}</td>
                <C:if test="${item.type==1}">
                    <td>通信费用</td>
                </C:if>
                <C:if test="${item.type==2}">
                    <td>办公室耗材</td>
                </C:if>
                <C:if test="${item.type==3}">
                    <td>住宿费用</td>
                </C:if>
                <C:if test="${item.type==4}">
                    <td>房租水电</td>
                </C:if>
                <C:if test="${item.type==5}">
                    <td>其他</td>
                </C:if>
                <td>${item.amount}</td>
                <td>${item.itemDesc}</td>
            </tr>
        </C:forEach>
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

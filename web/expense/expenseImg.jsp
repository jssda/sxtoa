<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/14
  Time: 10:35
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
    <title>查看上传的文件</title>
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
            $("input[name=ref]").click(function () {
                history.back();
            })
        });
    </script>

</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">报销管理</a></li>
        <li><a href="#">查看所附图片</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="formtitle1"><span>查看所附图片</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>图片</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${expImages}" var="image">
            <tr>
                <td><img src="upload/${image.fileName}"></td>
            </tr>
        </c:forEach>

     <%--   <tr>
            <td><img src="images/bill1.jpg"></td>
        </tr>

        <tr>
            <td><img src="images/bill2.jpg"></td>
        </tr>

        <tr>
            <td><img src="images/bill3.jpg"></td>
        </tr>--%>
        </tbody>
    </table>

</div>
<input name="ref" type="button" class="btn" value="返回"/>
<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>

</body>

</html>
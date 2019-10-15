<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/3
  Time: 17:12
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
    <script type="text/javascript" src="js/jquery.js"></script>
    <title>签到签退</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript">
        $(function () {

            // ajax请求签到
            $("#signIn").click(function () {
                $.ajax({
                    url: "servlet/dutyServlet?method=signIn",
                    type: "post",
                    dataType:"text",
                    success:function (data) {
                        $("#signInInfo").html(data);
                    }
                })
            });

            // ajax请求签退
            $("#signOut").click(function () {
                $.ajax({
                    url: "servlet/dutyServlet?method=signOut",
                    type: "post",
                    dataType:"text",
                    success:function (data) {
                        $("#signOutInfo").html(data);
                    }
                })
            })

        });
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">考勤管理</a></li>
        <li><a href="#">签到签退</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <ul class="forminfo">
        <li><label>&nbsp;</label><input name="signIn" id="signIn" type="button" class="btn" value="签到"/> 每天签到一次，不可重复签到
        </li>
        <div id="signInInfo"></div>

        <li><label>&nbsp;</label></li>
        <li><label>&nbsp;</label></li>
        <li><label>&nbsp;</label><input name="signOut" id="signOut" type="button" class="btn" value="签退"/>可重复签退，以最后一次签退为准
        </li>
        <div id="signOutInfo"></div>
    </ul>


</div>


</body>

</html>


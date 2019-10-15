<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/3
  Time: 14:13
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
    <title>欢迎登录后台管理系统</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="js/jquery.js"></script>
    <script src="js/cloud.js" type="text/javascript"></script>

    <script language="javascript">
        $(function () {
            $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            $(window).resize(function () {
                $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            })
        });
    </script>
<%--  校验页面是否在顶部, 如果在框架中, 跳转到顶端  --%>
    <script type="text/javascript">
        if (top != window)
            top.location.href = window.location.href;
    </script>
</head>

<body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">

<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>


<div class="logintop">
    <span>欢迎访问尚学堂OA系统</span>
    <ul>
        <li><a href="#">回首页</a></li>
        <li><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
    </ul>
</div>

<div class="loginbody">
    <span class="systemlogo"></span>
    <div class="loginbox loginbox2">
        <form action="servlet/employeeServlet?method=login" method="post">
            <ul>
                <span style="color: red; font-weight: bold; font-size: 11px; position: absolute; top: 66px; left: 286px">${error}</span>
                <li>
                    <input name="userName" type="text" class="loginuser" value="liukaili"
                           onclick="JavaScript:this.value=''"/>
                </li>
                <li>
                    <input name="password" type="password" class="loginpwd" value="123456" onclick="JavaScript:this.value=''" placeholder="密码"/>
                </li>
                <li class="yzm">
                    <span><input name="verifyCode" type="text" value="验证码" onclick="JavaScript:this.value=''"/></span>
                    <cite> <img src="random.jpg" alt="验证码"
                                onclick="javascript:$(this).attr('src', 'random.jpg?' + new Date().getTime())">
                    </cite>
                </li>
                <li>
                    <input name="" type="submit" class="loginbtn" value="登录"/>
                    <label><input name="" type="checkbox" value="" checked="checked"/>记住密码</label>
                    <label><a href="#">忘记密码？</a></label>
                </li>
            </ul>
        </form>
    </div>
</div>
</body>
</html>


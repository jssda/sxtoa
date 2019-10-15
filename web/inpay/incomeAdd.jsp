<%@ page import="java.util.Date" %>
<%@ page import="java.time.Instant" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/11
  Time: 11:35
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
    <title>无标题文档</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link href="css/select.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
    <script type="text/javascript" src="js/select-ui.min.js"></script>
    <script type="text/javascript" src="editor/kindeditor.js"></script>
    <script type="text/javascript">
        $(document).ready(function (e) {

            $(".select1").uedSelect({
                width: 345
            });

        });
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">收支管理</a></li>
        <li><a href="#">添加收入</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <form action="servlet/InPayServlet?method=incomeAdd" method="post">
        <ul class="forminfo">
            <li>
                <label>所属部门<b>*</b></label>
                <div class="vocation">
                    <select class="select1" name="deptNo">
                        <c:forEach items="${depts}" var="dept">
                            <option value="${dept.deptNo}">${dept.deptName}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li>
                <label>收入金额</label>
                <input name="amount" type="text" class="dfinput"/><i></i></li>
            <li>
                <label>收入类型</label>
                <cite>
                    <input name="icType" type="radio" value="1" checked="checked"/>人员外包&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="icType" type="radio" value="2"/>项目开发&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="icType" type="radio" value="3"/>报名费&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="icType" type="radio" value="4"/>学费&nbsp;&nbsp;&nbsp;&nbsp;
                </cite>
            </li>
            <li>
                <label>收入日期</label>
                <input name="icDate" type="text" class="dfinput" value="<%=new Date().toLocaleString()%>" readonly="readonly"/>
            </li>
            <li>
                <label>备注</label>
                <textarea name="icDesc" cols="" rows="" class="textinput"></textarea>
            </li>
            <li>
                <label>&nbsp;</label>
                <input name="" type="submit" class="btn" value="确认保存"/>
            </li>
        </ul>
    </form>
    <span style="color: red; font-size: 18px; font-weight: bold">${error}</span>
</div>

</body>

</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/9/26
  Time: 16:10
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
    <title>职位更新</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">人事管理</a></li>
        <li><a href="#">修改岗位信息</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <form action="servlet/positionServlet?method=updatePosition" method="post">
        <ul class="forminfo">
            <li><label>岗位编号</label><input name="posId" type="text" class="dfinput" value="${position.posId}" readonly="readonly"/></li>
            <li><label>岗位名称</label><input name="pName" type="text" class="dfinput" value="${position.pName}"/></li>
            <li><label>岗位描述</label><input name="pDesc" type="text" class="dfinput" value="${position.pDesc}"/></li>
            <li><label>&nbsp;</label><input name="" type="submit" class="btn" value="确认保存"/></li>
        </ul>
    </form>
    <c:if test="${error == true}">
        <span style="font-size: 18px; font-weight: bold; color: red">修改出错</span>
    </c:if>

</div>


</body>

</html>

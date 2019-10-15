<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/9/29
  Time: 20:13
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
    <title>用户信息页面</title>
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
        <li><a href="#">人事管理</a></li>
        <li><a href="#">员工信息详情</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <ul class="forminfo">
        <li>
            <label>用户名</label>
            <label>${employee.empId}</label>
        </li>
        <li>
        <li>
            <label>真实姓名</label>
            <label>${employee.realName}</label>
        </li>
        <li>
            <label>性别</label>
            <label>${employee.sex}</label>
        </li>
        <li>
            <label>出生日期</label>
            <label>${employee.birthDate}</label>
        </li>
        <li>
        <li>
            <label>入职时间</label>
            <label>${employee.hireDate}</label>
        </li>

        <li>
            <label>离职时间</label>
            <label>${employee.leaveDate}</label>
        </li>
        <li>
            <label>是否在职</label>
            <c:if test="${employee.onDuty == 1}">
                <label>在职</label>
            </c:if>
            <c:if test="${employee.onDuty == 0}">
                <label>已离职</label>
            </c:if>
        </li>

        <li>
            <label>员工类型</label>
            <c:if test="${employee.empType==1}">
                <label>基层员工</label>
            </c:if>
            <c:if test="${employee.empType==2}">
                <label>各级管理人员</label>
            </c:if>
        </li>

        <li>
            <label>所属部门<b>*</b></label>
            <label>${employee.dept.deptName}</label>

        </li>
        <li>
            <label>直接上级<b>*</b></label>
            <label>${employee.mgr.realName}<b>*</b></label>
        </li>
        </li>
        <li>
            <label>联系方式</label>
            <label>${employee.phone}</label>
        </li>
        <li>
            <label>QQ号</label>
            <label>${employee.qq}</label>
        </li>
        <li>
            <label>紧急联系人信息</label>
            <label>${employee.emerContactPerson}</label>
        </li>
        <li>
            <label>身份证号</label>
            <label>${employee.idCard}</label>
        </li>
        <li>
            <label>&nbsp;</label>
            <input name="" type="button" class="btn" value="返回" onclick="javascript:history.back(-1)"/>
        </li>
    </ul>

</div>

</body>

</html>
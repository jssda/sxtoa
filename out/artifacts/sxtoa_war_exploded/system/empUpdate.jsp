<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/9/29
  Time: 20:42
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
    <title>修改用户信息</title>
    <base href="<%=basePath%>"/>
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
        <li><a href="#">修改员工信息</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <form action="servlet/employeeServlet?method=updateEmployee" method="post">
        <ul class="forminfo">
            <li>
                <label>用户名</label>
                <input name="empId" type="text" class="dfinput" value="${employee.empId}" readonly="readonly"/>
                <i>必须唯一，也可以根据真实姓名自动生成</i></li>
            <li>
            <li>
                <label>真实姓名</label>
                <input name="realName" type="text" class="dfinput" value="${employee.realName}"/><i></i></li>
            <li>
                <label>性别</label><cite>
                <c:if test="${employee.sex == '男'}">
                <input name="sex" type="radio" value="男" checked="checked"/>男&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="sex" type="radio" value="女"/>女<i>也可以根据身份证号自动获取</i></cite>
                </c:if>
                <c:if test="${employee.sex == '女'}">
                    <input name="sex" type="radio" value="男"/>男&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="sex" type="radio" value="女" checked="checked"/>女<i>也可以根据身份证号自动获取</i></cite>
                </c:if>
            </li>
            <li>
                <label>出生日期</label>
                <input name="birthDate" type="text" class="dfinput" value="${employee.birthDate}"/><i>也可以根据身份证号自动获取</i>
            </li>
            <li>
            <li>
                <label>入职时间</label>
                <input name="hireDate" type="text" class="dfinput" value="${employee.hireDate}"/><i></i></li>

            <li>
                <label>离职时间</label>
                <input name="leaveDate" type="text" class="dfinput" value="${employee.leaveDate}"/><i></i></li>
            <li>
                <label>是否在职</label><cite>
                <c:if test="${employee.onDuty == 1}">
                <input name="onDuty" type="radio" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="onDuty" type="radio" value="0"/>否</cite>
                </c:if>
                <c:if test="${employee.onDuty == 0}">
                    <input name="onDuty" type="radio" value="1"/>是&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="onDuty" type="radio" value="0" checked="checked"/>否</cite>
                </c:if>
            </li>
            <li>
                <label>员工类型</label><cite>
                <c:if test="${employee.empType == 1}">
                    <input name="empType" type="radio" value="1" checked="checked"/>基层员工&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="empType" type="radio" value="2"/>各级管理人员</cite>
                </c:if>
                <c:if test="${employee.empType == 2}">
                    <input name="empType" type="radio" value="1"/>基层员工&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="empType" type="radio" value="2" checked="checked"/>各级管理人员</cite>
                </c:if>
            </li>
            <li>
                <label>所属部门<b>*</b></label>
                <div class="vocation">
                    <select class="select1" name="deptNo">
                        <c:forEach items="${depts}" var="dept">
                            <c:if test="${employee.dept.deptNo == dept.deptNo}">
                                <option value="${dept.deptNo}" selected="selected">${dept.deptName}</option>
                            </c:if>
                            <c:if test="${employee.dept.deptNo != dept.deptNo}">
                                <option value="${dept.deptNo}">${dept.deptName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li>
                <label>从事岗位<b>*</b></label>
                <div class="vocation">
                    <select class="select1" name="posId">
                        <c:forEach items="${positions}" var="position">
                            <c:if test="${employee.position.posId == position.posId}">
                                <option value="${position.posId}" selected="selected">${position.pName}</option>
                            </c:if>
                            <c:if test="${employee.position.posId != position.posId}">
                                <option value="${position.posId}">${position.pName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li>
                <label>直接上级<b>*</b></label>
                <div class="vocation">
                    <select class="select1" name="mgrId">
                        <c:forEach items="${mgrs}" var="mgr">
                            <c:if test="${employee.mgr.empId == mgr.empId}">
                                <option value="${mgr.empId}" selected="selected">${mgr.realName}</option>
                            </c:if>
                            <c:if test="${employee.mgr.empId != mgr.empId}">
                                <option value="${mgr.empId}">${mgr.realName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                &nbsp;&nbsp;<input name="" type="text" class="dfinput" placeholder="也可以在此输入首字母帮助显示"/></li>
            </li>
            <li>
                <label>联系方式</label>
                <input name="phone" type="text" class="dfinput" value="${employee.phone}"/>
            </li>
            <li>
                <label>QQ号</label>
                <input name="qq" type="text" class="dfinput" value="${employee.qq}"/>
            </li>
            <li>
                <label>紧急联系人信息</label>
                <textarea name="mcp" cols="" rows="" class="textinput">${employee.emerContactPerson}</textarea>
            </li>
            <li>
                <label>身份证号</label>
                <input name="idCard" type="text" class="dfinput" value="${employee.idCard}"/>
            </li>
            <li>
                <label>&nbsp;</label>
                <input name="" type="submit" class="btn" value="确认保存"/>
            </li>
        </ul>
    </form>

    <c:if test="${error == true}">
        <span style="font-weight: bold; font-size: 18px; color: red">更新失败了</span>
    </c:if>
</div>

</body>

</html>
<%@ page import="pers.jssd.entity.Employee" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/9/27
  Time: 16:30
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
    <title>雇员列表</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link href="css/select.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="js/laydate/laydate.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>

    <script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
    <script type="text/javascript" src="js/select-ui.min.js"></script>
    <script type="text/javascript" src="editor/kindeditor.js"></script>
    <script type="text/javascript">
        $(document).ready(function (e) {
            $(".select1").uedSelect({
                width: 200
            });

        });
    </script>
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
        <li><a href="#">人事管理</a></li>
        <li><a href="#">员工管理</a></li>
    </ul>
</div>

<div class="rightinfo">

    <form action="servlet/employeeServlet?method=findEmployeesBy" method="post">
        <ul class="prosearch">
            <li>
                <label>查询：</label><i>用户名</i>
                <a>
                    <input name="employeeName" type="text" value="${employeeName}" class="scinput"/>
                </a><i>所属部门</i>
                <a>
                    <select class="select1" name="deptNo">
                        <c:forEach items="${depts}" var="dept">
                            <c:if test="${deptNo == dept.deptNo}">
                                <option value="${dept.deptNo}" selected="selected">${dept.deptName}</option>
                            </c:if>
                            <c:if test="${deptNo != dept.deptNo}">
                                <option value="${dept.deptNo}">${dept.deptName}</option>
                            </c:if>
                        </c:forEach>
                        <c:if test="${empty deptNo}">
                            <option value="" selected="selected">--任意部门--</option>
                        </c:if>
                        <c:if test="${not empty deptNo}">
                            <option value="">--任意部门--</option>
                        </c:if>
                    </select>
                </a>
            </li>
            <li>
                <label>是否在职：</label>
                <c:if test="${onDuty == 1}">
                    <input name="onDuty" type="radio" value="1" checked="checked"/>&nbsp;是&nbsp;&nbsp;
                    <input name="onDuty" type="radio" value="0"/>&nbsp;否
                </c:if>
                <c:if test="${onDuty == 0}">
                    <input name="onDuty" type="radio" value="1"/>&nbsp;是&nbsp;&nbsp;
                    <input name="onDuty" type="radio" value="0" checked="checked"/>&nbsp;否
                </c:if>
                <c:if test="${empty onDuty}">
                    <input name="onDuty" type="radio" value="1"/>&nbsp;是&nbsp;&nbsp;
                    <input name="onDuty" type="radio" value="0"/>&nbsp;否
                </c:if>
            </li>
            <li>
                <label>入职时间：</label>
                <a>
                    <input name="hireDate" type="text" class="scinput" value="${hireDate}"/>
                </a>
            </li>
            <a>
                <input name="" type="submit" class="sure" value="查询"/>
            </a>
        </ul>
    </form>

    <div class="formtitle1"><span>员工列表</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>用户名<i class="sort"><img src="images/px.gif"/></i></th>
            <th>真实姓名</th>
            <th>所属部门</th>
            <th>职位类型</th>
            <th>所属岗位</th>
            <th>入职时间</th>
            <th>联系方式</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>${employee.empId}</td>
                <td>${employee.realName}</td>
                <td>${employee.dept.deptName}</td>
                <c:if test="${employee.empType == 1}">
                    <td>普通员工</td>
                </c:if>
                <c:if test="${employee.empType == 2}">
                    <td>管理人员</td>
                </c:if>
                <td>${employee.position.pName}</td>
                <td>${employee.hireDate}</td>
                <td>${employee.phone}</td>
                <td>
                    <a href="servlet/employeeServlet?method=viewUserInfo&empId=${employee.empId}"
                       class="tablelink">查看</a>
                    <a href="servlet/employeeServlet?method=toUpdateEmployee&empId=${employee.empId}" class="tablelink">修改</a>
                    <a href="javascript:confirmDelEmp('${employee.empId}')" class="tablelink"> 删除</a>
                    <a href="servlet/employeeServlet?method=toUpdatePwd&empId=${employee.empId}" class="tablelink"> 重置密码</a>
                </td>
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
    <c:if test="${error == true}">
        <span style="font-size: 18px; font-weight: bold; color: red">发生了错误</span>
    </c:if>

    <div class="tip">
        <div class="tiptop"><span>提示信息</span>
            <a></a>
        </div>

        <div class="tipinfo">
            <span><img src="images/ticon.png"/></span>
            <div class="tipright">
                <p>是否确认对信息的修改 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>

        <div class="tipbtn">
            <input name="" type="button" class="sure" value="确定"/>&nbsp;
            <input name="" type="button" class="cancel" value="取消"/>
        </div>
    </div>
</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');

    // 添加日期插件
    $("input[name=hireDate]").each(function () {
        laydate.render({
            elem: this,
            trigger: 'click'
        })
    });

    function confirmDelEmp(empId) {
        var flag = window.confirm("是否删除用户?");
        if (flag) {
            window.location.href = "servlet/employeeServlet?method=deleteEmployee&empId=" + empId;
        }
    }
</script>

</body>

</html>

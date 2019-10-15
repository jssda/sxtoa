<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/9/27
  Time: 9:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
    <script type="text/javascript" src="js/laydate/laydate.js"></script>
    <script type="text/javascript" src="wangeditor/wangEditor.js"></script>

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
        <li><a href="#">添加员工</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <form action="servlet/employeeServlet?method=addEmployee" method="post">
        <ul class="forminfo">
            <li>
                <label>用户名</label>
                <input name="empId" type="text" class="dfinput"/></li>
            <li>
            <li>
                <label>真实姓名</label>
                <input name="realName" type="text" class="dfinput"/><i></i></li>
            <li>
                <label>性别</label><cite>
                <input name="sex" type="radio" value="1" checked="checked"/>男&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="sex" type="radio" value="2"/>女<i>也可以根据身份证号自动获取</i></cite>

            </li>
            <li>
                <label>出生日期</label>
                <input id="birthday" name="birthDate" type="text" class="dfinput"/><i>也可以根据身份证号自动获取</i></li>
            <li>
            <li>
                <label>入职时间</label>
                <input id="hireDate" name="hireDate" type="text" class="dfinput"/><i></i></li>
            <li>
                <label>离职时间</label>
                <input id="leaveDate" name="leaveDate" type="text" class="dfinput"/><i></i></li>
            <li>
                <label>是否在职</label><cite>
                <input name="onDuty" type="radio" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="onDuty" type="radio" value="0"/>否</cite>
            </li>
            <li>
                <label>员工类型</label><cite>
                <input name="empType" type="radio" value="1" checked="checked"/>基层员工&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="empType" type="radio" value="2"/>各级管理人员</cite>
            </li>
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
                <label>从事岗位<b>*</b></label>
                <div class="vocation">
                    <select class="select1" name="posId">
                        <c:forEach items="${positions}" var="position">
                            <option value="${position.posId}">${position.pName}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>

            <li>
                <label>直接上级<b>*</b></label>
                <div class="vocation">
                    <select class="select1" name="mgrId">
                        <c:forEach items="${mgrs}" var="mgr">
                            <option value="${mgr.empId}">${mgr.realName}</option>
                        </c:forEach>
                        <option value="null">--无上级--</option>
                    </select>
                </div>
                &nbsp;&nbsp;<input name="" type="text" class="dfinput" placeholder="也可以在此输入首字母帮助显示"/>
            </li>

            <li>
                <label>联系方式</label>
                <input name="phone" type="text" class="dfinput"/>
            </li>

            <li>
                <label>QQ号</label>
                <input name="qq" type="text" class="dfinput"/>
            </li>

            <li>
                <label>紧急联系人信息</label>
                <textarea name="ecp" cols="" rows="" id="ecp" class="textinput" style="display: none"></textarea>
                <div id="test" style="width: 750px"></div>
            </li>

            <li>
                <label>身份证号</label>
                <input name="idCard" type="text" class="dfinput"/>
            </li>
            <li>
                <label>&nbsp;</label>
                <input name="" type="submit" class="btn" value="确认保存"/>
            </li>
        </ul>
    </form>

    <c:if test="${error == true}">
        <span style="font-size: 18px; font-weight: bold; color: red">添加失败</span>
    </c:if>

</div>

<script type="text/javascript">
    // 添加日期插件
    $("#birthday, #hireDate, #leaveDate").each(function () {
        laydate.render({
            elem: this,
            trigger: 'click'
        })
    });

    // 使用富文本编辑器
    var E = window.wangEditor
    var editor = new E('#test');

    var $ecp = $('#ecp');
    // 当文本改变的时候, 将数据同步到textarea中
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $ecp.val(html)
    }

    // 自定义菜单配置
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'justify',  // 对齐方式
        'undo',  // 撤销
        'redo'  // 重复
    ];

    editor.create();
    // 初始化 textarea 的值
    $ecp.val(editor.txt.html());
</script>
</body>

</html>

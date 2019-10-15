<%@ page import="javax.xml.crypto.Data" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/6
  Time: 10:00
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
    <title>考勤列表</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link href="css/select.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/laydate/laydate.js"></script>
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

    <script type="text/javascript">
        $(function () {
            // 页面加载, 部门跟随访问服务器加载
            $.ajax({
                url: "servlet/dutyServlet?method=findAllDept",
                type: "POST",
                success: function (jsonStr) {
                    var arr = JSON.parse(jsonStr);
                    var str = $("#sDeptId").html();
                    for (var i = 0; i < arr.length; i++) {
                        str += '<option value="' + arr[i].deptNo + '">' + arr[i].deptName + '</option>'
                    }

                    $("#sDeptId").html(str);
                }
            });

            searchForServler();

            // 绑定ajax查询
            $("#search").click(function () {
                searchForServler();
            });

            // 添加日期插件
            $("#sDtDate").each(function () {
                laydate.render({
                    elem: this,
                    trigger: 'click'
                })
            });

            // 导出
            $("#export").click(function () {
                var $sEmpId = $("#sEmpId").val();
                var $sDeptId = $("#sDeptId").val();
                var $sDtDate = $("#sDtDate").val();

                // 访问服务器, 服务器发送一个excel提供下载
                location.href = "servlet/dutyServlet?method=export&empId=" + $sEmpId + "&deptNo=" +
                    $sDeptId + "&dtDate=" + $sDtDate;
            });
        });

        // ajax 查询所有数据
        function searchForServler(index) {
            var $sEmpId = $("#sEmpId").val();
            var $sDeptId = $("#sDeptId").val();
            var $sDtDate = $("#sDtDate").val();

            // 使用ajax查询内容, 并操作dom显示在页面
            $.ajax({
                url: "servlet/dutyServlet?method=findDuty&index=" + index,
                type: "POST",
                data: {empId: $sEmpId, deptNo: $sDeptId, dtDate: $sDtDate},
                success: function (jsonStr) {
                    var data = JSON.parse(jsonStr);
                    var arr = data.list;

                    var str = "";
                    for (var i = 0; i < arr.length; i++) {
                        str += '     <tr>\n' +
                            '            <td>' + arr[i].empId + '</td>\n' +
                            '            <td>' + arr[i].employee.realName + '</td>\n' +
                            '            <td>' + arr[i].employee.dept.deptName + '</td>\n' +
                            '            <td>' + arr[i].dtDate + '</td>\n' +
                            '            <td>' + arr[i].signInTime + '</td>\n' +
                            '            <td>' + (arr[i].signOutTime == undefined ? "" : arr[i].signOutTime) + '</td>\n' +
                            '        </tr>';
                    }
                    $("#showDuty").html(str);

                    var pageIndexStr = '<div class="message">共&nbsp;<i class="blue">' + data.totalCount + '</i>&nbsp;条记录，' +
                        '共&nbsp;<i class="blue">' + data.totalPageCount + '</i>&nbsp;页, 当前显示第&nbsp;<i class="blue">' + data.index + '&nbsp;</i>页' +
                        '</div>' +
                        '<ul class="paginList">\n' +
                        '\t<li class="paginItem"><a href="javascript:searchForServler(' + (data.index - 1 <= 1 ? 1 : data.index - 1) + ');"><span class="pagepre"></span></a></li>\n';

                    for (i = 0; i < data.numbers.length; i++) {
                        if (data.numbers[i] != data.index) {
                            pageIndexStr += '\t<li class="paginItem"><a href="javascript:searchForServler(' + data.numbers[i] + ');">' + data.numbers[i] + '</a></li>\n';
                        } else {
                            pageIndexStr += '\t<li class="paginItem current"><a href="javascript:searchForServler(' + data.numbers[i] + ');">' + data.numbers[i] + '</a></li>\n';
                        }
                    }
                    pageIndexStr += '\t<li class="paginItem"><a href="javascript:searchForServler(' + (data.index + 1 >= data.totalPageCount ? data.totalPageCount : data.index + 1) + ');"><span class="pagenxt"></span></a></li>\n' +
                        '</ul>\n';

                    $(".pagin").html(pageIndexStr);
                },
            });
        }
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">考勤管理</a></li>
        <li><a href="#">考勤管理</a></li>
    </ul>
</div>

<div class="rightinfo">

    <ul class="prosearch">
        <li>
            <label>查询：</label>
            <i>用户名</i>
            <a>
                <input id="sEmpId" name="sEmpId" type="text" class="scinput"/>
            </a>

            <i>所属部门</i>
            <a>
                <select class="select1" id="sDeptId">
                    <option value='0' selected=selected>--全选--</option>
                </select>
            </a>

            <i>考勤时间</i>
            <a>
                <input id="sDtDate" name="sDtDate" type="text" class="scinput"/>
            </a>

            <a>
                <input id="search" name="search" type="button" class="sure" value="查询"/>
            </a>
            <a>
                <input id="export" name="export" type="button" class="scbtn2" value="导出"/>

            </a>
        </li>
    </ul>

    <div class="formtitle1"><span>考勤管理</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>用户名<i class="sort"><img src="images/px.gif"/></i></th>
            <th>真实姓名</th>
            <th>所属部门</th>
            <th>出勤日期</th>
            <th>签到时间</th>
            <th>签退时间</th>
        </tr>
        </thead>
        <tbody id="showDuty"></tbody>
    </table>

    <div class="pagin"></div>

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
</script>

</body>

</html>
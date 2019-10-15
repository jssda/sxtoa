<%@ page import="java.util.Date" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/7
  Time: 15:20
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
    <title>报销添加页面</title>
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
    <script type="text/javascript">
        function addPhoto() {
            //获取上传图片li
            var li = $("#uploadphoto");
            //console.info(li);
            //准备添加的dom对象
            var content = $("<span>" +
                "<label>&nbsp;</label>" +
                "<input name='photo' type='file' class='dfinput' />" +
                "<i></i>" +
                "<input type='button'  class='btn'  value='删除' onclick='removeCurrSpan(this)'/>" +
                "</span>");
            //加入到指定位置
            li.append(content);
        }

        function removeCurrSpan(obj) {
            $(obj).parent().remove();

        }

        // 添加报销项
        function addExpenseItem(id) {
            //获取上级标签
            var table = $(id);

            //创建当前标签
            var content = $('<tr>' +
                '<td><div class="vocation">' +
                '<select class="select1" name="expItem">' +
                '<option value="1">通信费用</option>\n' +
                '<option value="2">办公室耗材</option>\n' +
                '<option value="3">住宿费用</option>\n' +
                '<option value="4">房租水电</option>\n' +
                '<option value="5">其他</option>' +
                '</select></div></td>' +
                '<td><input name="amount" type="text" class="dfinput" /><i></i></td>' +
                '<td><input name="itemDesc" type="text" class="dfinput" /><i></i></td>' +
                '<td><input type="button"  class="btn"  value="删除" onclick="removeCurrItem(this)"/></td></tr>');

            //加入上级标签
            table.append(content);
            $(".select1").uedSelect({
                width: 345
            });
        }

        function removeCurrItem(obj) {
            $(obj).parent().parent().remove();

        }
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">报销管理</a></li>
        <li><a href="#">添加报销</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <form action="servlet/expenseServlet?method=add" method="post" enctype="multipart/form-data">
        <ul class="forminfo">
            <li>
                <label>报销人</label>
                <input name="" type="text" class="dfinput" value="${currUser.realName}" readonly="readonly"/><i></i>
            </li>
            <li>
                <label>报销总额(元)</label>
                <input name="totalAmount" type="text" class="dfinput"/><i></i>
            </li>
            <li>
                <label>报销时间</label>
                <input name="" type="text" class="dfinput" value="<%=new Date().toLocaleString()%>"
                       readonly="readonly"/><i></i>
            </li>

            <%--      报销项      --%>
            <li>
                <label>具体报销项</label>
                <input type="button" class="btn" value="添加报销项" onclick="addExpenseItem('#table1')"/>
                <table border="" cellspacing="" cellpadding="" width="70%" id="table1">
                    <tr>
                        <td>报销类型</td>
                        <td>报销费用</td>
                        <td>费用说明</td>
                    </tr>
                    <tr>
                        <td>
                            <div class="vocation">
                                <select class="select1" name="expItem">
                                    <option value="1">通信费用</option>
                                    <option value="2">办公室耗材</option>
                                    <option value="3">住宿费用</option>
                                    <option value="4">房租水电</option>
                                    <option value="5">其他</option>
                                </select>
                            </div>
                        </td>
                        <td><input name="amount" type="text" class="dfinput"/><i></i></td>
                        <td><input name="itemDesc" type="text" class="dfinput"/><i></i></td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </li>

            <li id="uploadphoto">
                <label>上传报销图片</label>
                <span>
                    <input name="photo" type="file" class="dfinput"/><i></i>
                    <input type="button" class="btn" value="添加图片" onclick="addPhoto()"/>
                </span>
            </li>

            <li>
                <label>审批人<b>*</b></label>
                <label>${currUser.mgr.realName}</label>
            </li>

            <li>
                <label>总备注信息</label>
                <textarea name="expDesc" cols="" rows="" class="textinput"></textarea>
            </li>

            <li>
                <label>&nbsp;</label>
                <input name="" type="submit" class="btn" value="确认保存"/>
            </li>
        </ul>
    </form>
    <span style="font-size: 20px; font-weight: bold; color: red">${error}</span>

</div>
</body>
</html>
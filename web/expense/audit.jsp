<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/9
  Time: 12:37
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
    <title>审核报销单</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script src="js/jquery.js"></script>
    <script type="text/javascript">

        /**
         * 当点击审核保存的时候, 提交数据, 并关闭窗口
         */
        function submitForm() {
            var resultArr = $("input[name=result]");
            var result;
            for (var i = 0; i < resultArr.length; i++) {
                if (resultArr[i].checked) {
                    result = resultArr[i].value;
                }
            }
            var opinion = $("input[name=opinion]").val();

            $.ajax({
                url: "servlet/auditServlet?method=audit&result=" + result + "&opinion=" + opinion + "&expId=" + ${param.expId},
                type: "POST",
                success: function (data) {
                    window.opener.location.reload();
                    window.close();
                }
            });
        }

    </script>
</head>

<body>

<div class="formtitle"><span>审核报销单</span></div>
<form action="#" onsubmit="submitForm(); return false;" method="post">
    <ul class="forminfo">
        <li>
            <label>报销单号</label>
            <label>${param.expId}</label>
        </li>
        <li>
            <label>审核结果</label>
            <input name="result" type="radio" value="通过" checked="checked"/>通过
            <input name="result" type="radio" value="拒绝"/>拒绝
            <input name="result" type="radio" value="打回"/>打回
        </li>
        <li>
            <label>审核意见</label>
            <input name="opinion" type="text" class="dfinput"/>
        </li>
        <li>
            <label>&nbsp;</label>
            <input name="confirm" type="submit" class="btn" value="确认保存"/>
        </li>
    </ul>
</form>
</body>

</html>

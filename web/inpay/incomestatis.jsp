<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/11
  Time: 21:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>收入统计柱状图</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                url: "servlet/InPayServlet?method=showIncomeStatic",
                type: "POST",
                success: function (data) {
                    var arr;
                    eval("arr = " + data);
                    var xArr = arr.icType;
                    var valArr = arr.amount;

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('container'));
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '公司收入统计柱状图'
                        },
                        tooltip: {},
                        xAxis: {
                            data: xArr
                        },
                        yAxis: {},
                        series: [{
                            name: "收入",
                            type: 'bar',
                            data: valArr
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        })
    </script>
</head>
<body>
<div id="container" style="height: 90%; width: 50%; margin: 50px auto auto;"></div>
</body>
</html>

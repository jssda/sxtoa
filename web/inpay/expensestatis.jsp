<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/12
  Time: 16:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height: 100%">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <base href="<%=basePath%>"/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>

    <title>支出统计</title>

    <script type="text/javascript">
        $(document).ready(function (e) {
            $("#select1").change();
        });

        function changePie(val) {
            $.ajax({
                url: "servlet/InPayServlet?method=showPaymentStatic",
                data: {val: val},
                success:function (data) {
                    var arr;
                    arr = eval(data);
                    var dom = document.getElementById("container");
                    var myChart = echarts.init(dom);
                    var option = {
                        title: {
                            text: '尚学堂支出统计',
                            subtext: '报销统计',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: ['通信费用', '办公室耗材', '住宿费用', '房租水电', '其他']
                        },
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: arr,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    if (option && typeof option === "object") {
                        myChart.setOption(option, true);
                    }
                }
            });
        }
    </script>
</head>

<body style="height: 100%; margin: 0">
<div style="height: 10%; width: 500px;  margin:0 auto;">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    请选择支出时间段：
    <select class="select1" id="select1" onchange="changePie(this.value)">
        <option value="1">不限</option>
        <option value="2">上月</option>
        <option value="3">今年上半年</option>
        <option value="4">今年下半年</option>
        <option value="5">去年</option>
    </select>
</div>
<div id="container" style="height: 85%"></div>
</body>
</html>
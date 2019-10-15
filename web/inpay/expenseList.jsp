<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/10/12
  Time: 10:44
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
    <title>支出列表</title>
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
                width: 150
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
            // 加载日历插件
            $("input[name=startTime], input[name=endTime]").each(function () {
                laydate.render({
                    elem: this,
                    trigger: 'click'
                })
            });

            // 发送ajax请求
            requestTo();

            // 绑定查询触发ajax请求函数
            $("input[name=search]").click(function () {
                requestTo();
            });
        })

        /**
         * 向服务器发送请求
         */
        function requestTo(index) {
            var startTime = $("input[name=startTime]").val();
            var endTime = $("input[name=endTime]").val();
            var payEmpId = $("input[name=payEmpId]").val();
            var paymentType = $("select[name=paymentType]").val();

            $.ajax({
                url: "servlet/InPayServlet?method=findPaymentBy&index=" + index,
                data: {startTime: startTime, endTime: endTime, payEmpId: payEmpId, paymentType: paymentType},
                success: function (jsonStr) {
                    var data = JSON.parse(jsonStr);
                    var str = "";
                    var arr = data.list;
                    for (var i = 0; i < arr.length; i++) {
                        str += '<tr>\n';
                        if (arr[i].expense.expenseItems[0].type == 1) {
                            str += '            <td>人员外包</td>\n';
                        } else if (arr[i].expense.expenseItems[0].type == 2) {
                            str += '            <td>项目开发</td>\n';
                        } else if (arr[i].expense.expenseItems[0].type == 3) {
                            str += '            <td>报名费</td>\n';
                        } else if (arr[i].expense.expenseItems[0].type == 4) {
                            str += '            <td>学费</td>\n';
                        } else if (arr[i].expense.expenseItems[0].type == 5) {
                            str += '            <td>其他</td>\n';
                        }
                        str += '            <td>' + arr[i].expense.expenseItems[0].amount + '</td>\n' +
                            '            <td>' + arr[i].expense.expenseItems[0].itemDesc + '</td>\n' +
                            '            <td>' + arr[i].payEmp.realName + '</td>\n' +
                            '            <td>' + arr[i].payTime + '</td>\n' +
                            '        </tr>';
                    }

                    $("tbody").html(str);


                    // 动态加载页码
                    var pageIndexStr = '<div class="message">共&nbsp;<i class="blue">' + data.totalCount + '</i>&nbsp;条记录，' +
                        '共&nbsp;<i class="blue">' + data.totalPageCount + '</i>&nbsp;页, 当前显示第&nbsp;<i class="blue">' + data.index + '&nbsp;</i>页' +
                        '</div>' +
                        '<ul class="paginList">\n' +
                        '\t<li class="paginItem"><a href="javascript:requestTo(' + (data.index - 1 <= 1 ? 1 : data.index - 1) + ');"><span class="pagepre"></span></a></li>\n';

                    for (i = 0; i < data.numbers.length; i++) {
                        if (data.numbers[i] != data.index) {
                            pageIndexStr += '\t<li class="paginItem"><a href="javascript:requestTo(' + data.numbers[i] + ');">' + data.numbers[i] + '</a></li>\n';
                        } else {
                            pageIndexStr += '\t<li class="paginItem current"><a href="javascript:requestTo(' + data.numbers[i] + ');">' + data.numbers[i] + '</a></li>\n';
                        }
                    }
                    pageIndexStr += '\t<li class="paginItem"><a href="javascript:requestTo(' + (data.index + 1 >= data.totalPageCount ? data.totalPageCount : data.index + 1) + ');"><span class="pagenxt"></span></a></li>\n' +
                        '</ul>\n';

                    $(".pagin").html(pageIndexStr);
                }
            })
        }
    </script>

</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">收支管理</a></li>
        <li><a href="#">查看支出</a></li>
    </ul>
</div>

<div class="rightinfo">

    <ul class="prosearch">
        <li>
            <label>查询：</label>
            <i>起始登记时间</i>
            <a><input name="startTime" type="text" class="scinput"/></a>
            <i>结束登记时间</i>
            <a><input name="endTime" type="text" class="scinput"/></a>
            <i>支出人</i>
            <a><input name="payEmpId" type="text" class="scinput"/></a>
        </li>
    </ul>
    <ul class="prosearch">
        <li>
            <i>支出类型</i>
            <a>
                <select class="select1" name="paymentType">
                    <option value="0">--全部--</option>
                    <option value="1">人员外包</option>
                    <option value="2">项目开发</option>
                    <option value="3">报名费</option>
                    <option value="4">学费</option>
                </select>
            </a>
            <a><input name="search" type="button" class="sure" value="查询"/></a>
        </li>
    </ul>
    <div class="formtitle1"><span>支出列表</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>类型<i class="sort"><img src="images/px.gif"/></i></th>
            <th>金额</th>
            <th>备注</th>
            <th>支出人</th>
            <th>支出时间</th>
        </tr>
        </thead>

        <tbody>
        <%--    支出内容列表    --%>
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

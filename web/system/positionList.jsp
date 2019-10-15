<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jssdjing@gmail.com
  Date: 2019/9/26
  Time: 15:08
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
    <title>无标题文档</title>
    <base href="<%=basePath%>"/>

    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery.js"></script>
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
        // 分页实现
        function changePage(index) {
            window.location = "servlet/positionServlet?method=findPositions&index=" + index;
        }
    </script>
</head>


<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">人事管理</a></li>
        <li><a href="#">岗位管理</a></li>
    </ul>
</div>

<div class="rightinfo">


    <div class="formtitle1"><span>岗位列表</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>编号<i class="sort"><img src="images/px.gif"/></i></th>
            <th>岗位名称</th>
            <th>岗位描述</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${pageBean.list}" var="pos">
            <tr>
                <td>${pos.posId}</td>
                <td>${pos.pName}</td>
                <td>${pos.pDesc}</td>
                <td><a href="servlet/positionServlet?method=findPositionById&posId=${pos.posId}"
                       class="tablelink">修改</a>
                    <a href="javascript:deleDept(${pos.posId})" class="tablelink"> 删除</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">
        <div class="message">共&nbsp;<i class="blue">${pageBean.totalCount}</i>&nbsp;条记录，
            共&nbsp;<i class="blue">${pageBean.totalPageCount}</i>&nbsp;页, 当前显示第&nbsp;<i class="blue">${pageBean.index}&nbsp;</i>页
        </div>
        <ul class="paginList">
            <c:if test="${pageBean.index == 1}">
                <li class="paginItem current"><a href="javascript:void(0);"><span class="pagepre"></span></a></li>
            </c:if>
            <c:if test="${pageBean.index != 1}">
                <li class="paginItem"><a href="javascript:changePage(${pageBean.index - 1});"><span
                        class="pagepre"></span></a></li>
            </c:if>

            <c:forEach items="${pageBean.numbers}" var="num">

                <c:if test="${pageBean.index == num}">
                    <li class="paginItem current"><a href="javascript:changePage(${num});">${num}</a></li>
                </c:if>
                <c:if test="${pageBean.index != num}">
                    <li class="paginItem"><a href="javascript:changePage(${num});">${num}</a></li>
                </c:if>
            </c:forEach>

            <c:if test="${pageBean.index == pageBean.totalPageCount}">
                <li class="paginItem current"><a href="javascript:void(0);"><span class="pagenxt"></span></a></li>
            </c:if>
            <c:if test="${pageBean.index != pageBean.totalPageCount}">
                <li class="paginItem"><a href="javascript:changePage(${pageBean.index + 1});"><span
                        class="pagenxt"></span></a></li>
            </c:if>
        </ul>
    </div>
    <div class="tip">
        <div class="tiptop"><span>提示信息</span><a></a></div>

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

    /**
     * 确认删除职位数据
     * @param posId 删除职位数据的id
     */
    function deleDept(posId) {
        var flag = window.confirm("您真的想删除吗?");
        if (flag) {
            location.href = "servlet/positionServlet?method=removePosition&posId=" + posId;
        }
    }

</script>

</body>

</html>


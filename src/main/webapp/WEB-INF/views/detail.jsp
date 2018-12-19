<%--
  Created by IntelliJ IDEA.
  User: 17699
  Date: 2018/12/19
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>详情</title>
</head>
<body>
<jsp:include page="comm/top.jsp"/>
<div class="container">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">${article.title}</h3>
        </div>
        <div class="panel-body">

            <span>${article.content}</span>
        </div>
    </div>
</div>
</body>
</html>

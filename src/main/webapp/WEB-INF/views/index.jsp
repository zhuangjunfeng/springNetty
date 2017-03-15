<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>设备列表</title>
  <meta name="viewport" content="initial-scale=1, maximum-scale=1">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <link rel="stylesheet" href="<%=basePath %>/css/sm.css">
  <link rel="stylesheet" href="<%=basePath %>/css/sm-extend.css">
  <link rel="stylesheet" type="text/css" href="<%=basePath %>/css/global.css?170122002">
</head>

<body>
<div class="page-group">
  <div class="page" id="device-list">
    <div class="content" id="devices">
    </div>
  </div>
</div>
<script type='text/javascript' src='<%=basePath %>/js/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='<%=basePath %>/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='<%=basePath %>/js/sm-extend.min.js' charset='utf-8'></script>
<script type="text/javascript" src="<%=basePath %>/js/index.js?170123003"></script>
</body>

</html>


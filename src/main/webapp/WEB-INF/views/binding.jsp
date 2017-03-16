<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>设备绑定</title>
    <script type="text/javascript" src="<%=basePath %>/js/lib/flexible.js"></script>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=basePath %>/css/sm-55da968180.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/css/layout-ef68b4fb4e.css">
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_30orapuilrgm0a4i.css">
</head>

<body>
<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示-->
    <div class="page">
        <div class="content">
            <form id="add-form">
                <div class="list-block">
                    <ul>
                        <!-- Text inputs -->
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">设备名称</div>
                                    <div class="item-input">
                                        <input type="text" placeholder="设备名称" name="device_name">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-email"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">设备UID</div>
                                    <div class="item-input">
                                        <input type="text" placeholder="设备UID" name="device_uid">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <div class="row">
                        <div class="col-50"><a href="#device-list" class="button button-big button-fill button-danger">取消</a></div>
                        <div class="col-50"><a href="javascript:add()" class="button button-big button-fill button-success">提交</a></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type='text/javascript' src='<%=basePath %>/js/lib/zepto.js' charset='utf-8'></script>
<script type='text/javascript' src='<%=basePath %>/js/lib/sm.js' charset='utf-8'></script>
<script type="text/javascript" src="<%=basePath %>/js/binding.js"></script>

</body>

</html>
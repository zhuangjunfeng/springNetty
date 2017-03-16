<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>解绑设备</title>
    <script type="text/javascript" src="<%=basePath %>/js/lib/flexible.js"></script>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=basePath %>/css/sm-55da968180.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/css/master-467270fac4.css">
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_30orapuilrgm0a4i.css">
</head>
<body>
    <div class="page-group">
        <!-- 单个page ,第一个.page默认被展示-->
        <div class="page">
            <div class="content remove-content">
                <div class="card remove-card">
                    <!-- 这里是设备的相关信息内容 -->
                    <div class="card-content">
                        <div class="list-block">
                            <ul>
                                <li>
                                    <i class="icon icon-f7"></i>
                                    <div class="item-inner">
                                        <div class="item-title">设备名称</div>
                                        <div class="item-after one-item-after">设备1</div>
                                    </div>
                                    </a>
                                </li>
                                <li>
                                    <i class="icon icon-f7"></i>
                                    <div class="item-inner">
                                        <div class="item-title">设备版本</div>
                                        <div class="item-after">V1.0</div>
                                    </div>
                                </li>
                                <li>
                                    <i class="icon icon-f7"></i>
                                    <div class="item-inner">
                                        <div class="item-title">设备二维码</div>
                                        <div class="item-after">
                                            <span class="icon iconfont icon-erweima"></span>
                                            <span class="icon icon-right"></span>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- 解绑设备的按钮 -->
                <div class="remove-button">
                    <a href="#" class="button  button-fill button-danger" id="removeBtn">解绑设备</a>
                </div>
            </div>
        </div>
    </div>
    <script type='text/javascript' src='<%=basePath %>/js/lib/zepto.js' charset='utf-8'></script>
    <script type='text/javascript' src='<%=basePath %>/js/lib/sm.js' charset='utf-8'></script>
</body>

</html>
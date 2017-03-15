<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>售后服务</title>
    <script type="text/javascript" src="<%=basePath %>/js/lib/flexible.js"></script>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=basePath %>/css/sm-55da968180.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/css/master-467270fac4.css">
</head>

<body>
<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示-->
    <div class="page">
        <!-- 这里是页面内容区 -->
        <div class="content service-content">
            <div class="card title-card">
                <!-- 这里是售后服务的标题内容 -->
                <div class="card-header">
                    <div class="list-block">
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">标 题:</div>
                                <div class="item-input">
                                    <input type="text" placeholder="请输入标题">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 这里是售后服务的联系人内容 -->
                <div class="card-header">
                    <div class="list-block">
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">联系人:</div>
                                <div class="item-input">
                                    <input type="text" placeholder="请输入联系人">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 这里是售后服务的联系电话内容 -->
                <div class="card-header">
                    <div class="list-block">
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">联系电话:</div>
                                <div class="item-input">
                                    <input type="text" placeholder="请输入联系电话">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 这里是售后服务的故障描速内容 -->
                <div class="card-footer">
                    <div class="list-block">
                        <div class="align-top">
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">故障描述:</div>
                                    <div class="item-input">
                                        <textarea placeholder="请输入故障描述"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 这里是保存的按钮 -->
            <div class="save-button">
                <a href="#" class="button button-fill">提交</a>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript' src='<%=basePath %>/js/lib/zepto.js' charset='utf-8'></script>
<script type='text/javascript' src='<%=basePath %>/js/lib/sm.js' charset='utf-8'></script>
</body>

</html>

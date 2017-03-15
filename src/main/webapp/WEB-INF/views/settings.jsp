<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>信息设置</title>
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
        <div class="content settings-content">
            <div class="card">
                <!-- 这里是信息设置关于开机内容 -->
                <div class="card-content">
                    <div class="list-block">
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-toggle"></i></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">开机提醒</div>
                                    <div class="item-text">开机成功或失误时&#44; 提醒我</div>
                                </div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 这里是信息设置关于关机内容 -->
            <div class="card settings-card">
                <div class="card-content">
                    <div class="list-block">
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-toggle"></i></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">关机提醒</div>
                                    <div class="item-text">关机成功或失误时&#44; 提醒我</div>
                                </div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card settings-card">
                <div class="card-content">
                    <div class="list-block">
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-toggle"></i></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">滤网过期提醒</div>
                                    <div class="item-text">滤网使用时长达到1个月&#44; 提醒我</div>
                                </div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript' src='<%=basePath %>/js/lib/zepto.js' charset='utf-8'></script>
<script type='text/javascript' src='<%=basePath %>/js/lib/sm.js' charset='utf-8'></script>
</body>

</html>

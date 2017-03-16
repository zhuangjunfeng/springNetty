<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>监控页面</title>
    <script type="text/javascript" src="<%=basePath %>/js/lib/flexible.js"></script>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=basePath %>/css/sm-55da968180.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/css/columns-89dd69d4d6.css">
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_30orapuilrgm0a4i.css">
</head>

<body>
<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示-->
    <div class="page">
        <div class="content monitoring-content">
            <div class="card state-card">
                <!-- 监控页面的圆圈的状态-->
                <div class="monitoring-state">
                    <h1>优</h1>
                    <h5 class="auto-title">手动运行</h5>
                    <h6>PM2.5：25mg/m3<br/>VOC：80mg/m3</h6>
                </div>
                <!-- 监控页面的信息部分-->
                <div class="monitoring-message">
                    <div class='message'>
                        <p>室温&nbsp;100&#8451;</p>
                    </div>
                    <div class='message'>
                        <p>风速&nbsp;低</p>
                    </div>
                    <div class='message'>
                        <p>湿度&nbsp;80%</p>
                    </div>
                    <div class='message'>
                        <p>滤网&nbsp;50%</p>
                    </div>
                </div>
            </div>
            <!-- 监控页面按钮的状态-->
            <div class="monitoring-button">
                <div class="monitoring-button1">
                    <div>
                        <span class="icon iconfont icon-kaiguan" id="kaiguan"></span>
                        <p>开关</p>
                    </div>
                    <div>
                        <span class="icon iconfont icon-autodq" id="auto"></span>
                        <p>模式</p>
                    </div>
                    <div>
                        <span class="icon iconfont icon-fengsushezhi" id="fengsu"></span>
                        <p>风速</p>
                    </div>
                    <div>
                        <span class="icon iconfont icon-shizhong" id="clock"></span>
                        <p>定时</p>
                    </div>
                </div>
                <div class="monitoring-button2">
                    <div>
                            <span class='icon iconfont icon-caozuo_denglizi' id='icon-denglizi'>
                            </span>
                        <p>等离子</p>
                    </div>
                    <div>
                        <span class='icon iconfont icon-dengpao-copy' id='icon-deng'></span>
                        <p>氛围灯</p>
                    </div>
                    <div>
                        <span class='icon iconfont icon-kongqilvwang' id='wang'></span>
                        <p>重置滤网</p>
                    </div>
                    <a href='javascript:;' id="more">
                        <div>
                            <span class='icon iconfont icon-sandian'></span>
                            <p>更多</p>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript' src='<%=basePath %>/js/lib/zepto.js' charset='utf-8'></script>
<script type='text/javascript' src='<%=basePath %>/js/lib/sm.js' charset='utf-8'></script>
<script type="text/javascript" src="<%=basePath %>/js/master.js"></script>
</body>

</html>
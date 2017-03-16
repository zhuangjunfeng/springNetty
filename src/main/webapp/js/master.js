
var socket;
var uid;
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 初始化页面
 */
function initWebSocket(){
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket("ws://"+window.location.hostname+":5888/ws");
        socket.onmessage = function(event) {
            $.toast(event.data);
        };
        socket.onopen = function(event) {
           uid=GetQueryString("uid");
            sendWS("webLoginActor",uid,"","");
        };
        socket.onclose = function(event) {
            initWebSocket();
        };
    } else {
        $.alert('您的手机暂不支持!');
    }
}
/**
 * 发送指令方法
 * @param cmd
 * @param uid
 * @param data
 * @param code
 */
function sendWS(cmd,uid,data,code){
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        var msgJson = { "cmd": cmd, "uid":uid,"data": data ,"code":code};
        socket.send(JSON.stringify(msgJson));
    } else {
        $.toast("网络不给力...");
    }
}
/**
 * URL上获取参数
 * @param name
 * @returns {null}
 * @constructor
 */
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}




initWebSocket();

//解绑设备页面的解绑按钮
$(document).on('click', '.button-danger', function() {
    $.confirm('解绑设备', function() {
        $.alert('解绑后无法控制设备');
    });
});

/*监控页面按钮部分*/
//开关按钮
$("#kaiguan").click(function() {
    if ($(".auto-title").text() == "手动运行") {
        $(".auto-title").text("已关机");
        $(".state-card").css("background-color", "#999999");
    } else {
        $(".auto-title").text("手动运行");
        $(".state-card").css("background-color", "#1ba365");
    }
});
//AUTO按钮
$("#auto").click(function() {
    if ($(".auto-title").text() == "手动运行") {
        $(".auto-title").text("自动运行");
    } else {
        $(".auto-title").text("手动运行");
    }
});
//风速按钮
$(document).on('click', '#fengsu', function() {
    var buttons1 = [{
        text: '高速',
        label: true
    }, {
        text: '中速',
        label: true
    }, {
        text: '低速',
        label: true
    }];
    var buttons2 = [{
        text: '取消'
    }];
    var groups = [buttons1, buttons2];
    $.actions(groups);
});

$("#clock").datetimePicker({
    value: ['1985', '12', '04', '9', '34']
});

//重置过滤网
$(document).on('click', '#wang', function() {
    $.alert('<div>重置滤网已经完成</div>');
});




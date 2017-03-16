
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
function cmdSet(data){
    if(data.cmd=="webLoginActor"){
        if(data.data=="active"){
            $(".auto-title").text("手动运行");
            $(".auto-title").attr("status","active");
            $(".state-card").css("background-color", "#1ba365");
        }else{
            $(".auto-title").text("不在线");
            $(".auto-title").attr("status","noActive");
            $(".state-card").css("background-color", "#999999");
        }
    }else{
        $.alert(data.cmd);
    }


}

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
            var data =JSON.parse(event.data);
            cmdSet(data)
        };
        socket.onopen = function(event) {
           uid=GetQueryString("uid");
            sendWS("webLoginActor",uid,"","");
            sendWS("sendDataActor",uid,"29","01");
            sendWS("sendDataActor",uid,"28","01");
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
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

initWebSocket();


/*监控页面按钮部分*/
//开关按钮
$("#switchBtn").click(function() {
    if ($(this).attr("status") == "open") {
        $(".auto-title").text("已关机");
        $(".state-card").css("background-color", "#999999");
        var  data= "010300";
        sendWS("sendDataActor",uid,data,"f5");
        $(this).attr("status","close")
    } else {
        $(".auto-title").text("手动运行");
        $(".state-card").css("background-color", "#1ba365");
        var  data= "013000";
        sendWS("sendDataActor",uid,data,"f5");
        $(this).attr("status","open")
    }
});
//AUTO按钮
$("#autoBtn").click(function() {
    $.alert("暂不支持该功能！");
});
$("#lampBtn").click(function() {
    $.alert("暂不支持该功能！");
});
//风速按钮
$("#speedBtn").click(function() {
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

$("#plasmaBtn").click(function() {
    if ($(this).attr("status") == "open") {
        var  data= "04";
        sendWS("sendDataActor",uid,data,"f5");
        $(this).attr("status","close")
    } else {
        var  data= "030300";
        sendWS("sendDataActor",uid,data,"f5");
        $(this).attr("status","open")
    }
});

$("#clockBtn").datetimePicker({
    value: ['2016', '12', '04', '9', '34']
});

//重置过滤网
$("#filterBtn").click(function() {
    $.alert('<div>重置滤网已经完成</div>');
});
//点击更多
$("#moreBtn").click(function(){
    window.location.href="http://air.semsplus.com/rest/wx/remove?uid="+uid;
});



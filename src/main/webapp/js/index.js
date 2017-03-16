var socket;
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
            $("#"+data.uid).children().find(".item-after").html("在线")
        }else{
            $("#"+data.uid).children().find(".item-after").html("不在线")
        }
    }
}

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
            query();
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

///**
// * 添加设备
// */
//function add() {
//    $.ajax({
//        type: "POST",
//        dataType: "json",
//        url: "/rest/device",
//        headers: {
//            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
//        },
//        data: $("#add-form").serialize(),
//        success: function(data) {
//            $.router.load("#device-list");
//        }
//    })
//}

///**
// * 开启风扇
// */
//function openFan(uid){
//    var  data= "015000";
//    sendWS("sendDataActor",uid,data,"f5");
//}
//
///**
// * 关闭风扇
// */
//function closeFan(uid){
//    var  data= "010100";
//    sendWS("sendDataActor",uid,data,"f5");
//}

/**
 * 查询全部设备
 */
function query() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/rest/device/find",
        data: { _method: "GET" },
        success: function(data) {
            var typeList = data.data.list;
            var listHtml = "";
            if(typeList.length==0){
                listHtml+="<div class='binding-content'><div class='binding-state'>"
                    +"<span class='icon iconfont icon-xiaolian'></span></div> <div class='binding-p'>"
                   +"您还没有绑定设备，请先进行绑定 </div><div class='binding-button'><a href='http://air.semsplus.com/rest/wx/binding' class='button button-success'>设备绑定</a>"
                    +"</div></div>";
                $("#device-list").html(listHtml);
            }else {
                $.each(typeList, function (i, n) {
                    listHtml += "<div class='list-block media-list'><ul><li>" +
                        "<a href='#' class='item-link item-content d-detail' id='" + n.device_uid + "'>" +
                        "<div class='item-media'>" +
                        "<img src='http://air.semsplus.com/img/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg' style='width: 4rem;'>" +
                        "</div>" +
                        "<div class='item-inner'>" +
                        " <div class='item-title-row'>" +
                        "<div class='item-title'>" +
                        n.device_name +
                        "</div>" +
                        "<div class='item-after'></div>" +
                        "</div>" +
                        "<div class='item-subtitle'></div>" +
                        "<div class='item-text'></div> " +
                        "</div> </a> </li> </ul> </div>"
                })

                $("#devices").html(listHtml);
                $.each(typeList, function (i, n) {
                    sendWS("webLoginActor", n.device_uid, "", "");
                });
                $(".d-detail").click(function () {
                    var uid = $(this).attr("id");
                    window.location.href = "http://air.semsplus.com/rest/wx/monitoring?uid=" + uid;
                });
            }
        }
    });
}

$(function() {
    $.init();
    initWebSocket();

});

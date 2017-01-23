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

        };
        socket.onclose = function(event) {
            $.toast("网络不给力...");
        };
    } else {
        $.alert('您的手机暂不支持!');
    }
}

function sendWS(cmd,uid,data,code){
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        var msgJson = { "cmd": cmd, "uid":uid,"data": data ,"code":code};
        $.toast(JSON.stringify(msgJson));
        socket.send(JSON.stringify(msgJson));
    } else {
        $.toast("网络不给力...");
    }
}

/**
 * 添加设备
 */
function add() {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/rest/device",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        data: $("#add-form").serialize(),
        success: function(data) {
            $.router.load("#device-list");
        }
    })
}

/**
 * 开启风扇
 */
function openFan(uid){
    var  data= "015000";
    sendWS("sendDataActor",uid,data,"f5");
}

/**
 * 关闭风扇
 */
function closeFan(uid){
    var  data= "010100";
    sendWS("sendDataActor",uid,data,"f5");
}

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
            $.each(typeList, function(i, n) {
                sendWS("webLoginActor",n.device_uid,"","");
                listHtml += "<div class='list-block media-list'><ul><li>" +
                    "<a href='#' class='item-link item-content d-detail' data-uid='"+ n.device_uid+"' data-title='"+ n.device_name+"'>" +
                    "<div class='item-media'>" +
                    "<img src='http://air.semsplus.com/img/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg' style='width: 4rem;'>" +
                    "</div>" +
                    "<div class='item-inner'>" +
                    " <div class='item-title-row'>" +
                    "<div class='item-title'>" +
                    n.device_name +
                    "</div>" +
                    "<div class='item-after'>不在线</div>" +
                    "</div>" +
                    "<div class='item-subtitle'></div>" +
                    "<div class='item-text'></div> " +
                    "</div> </a> </li> </ul> </div>"
            })

            $("#devices").html(listHtml);
            $(".d-detail").click(function(){
                var uid = $(this).attr("data-uid");
                var title = $(this).attr("data-title");
                $("#device-info-title").html(title);
                $("#device-info-uid").attr("data-uid",uid);
                $.router.load("#device-info");
            });
        }
    });
}

$(function() {
    initWebSocket();

    $(".bar-tab a").click(function() {
        $.router.load("#" + $(this).attr("menu-data"));
    })
    $(".button-success").click(function() {
            var rs = true;
            if ($("input[name='device_name']").val() == "") {
                $.alert('请输入设备名称!');
                rs = false;
                return;
            }
            if ($("input[name='device_uid']").val() == "") {
                $.alert('请输入设备UID!');
                rs = false;
                return;
            }
            if (rs) {
                add();
            }

        })

    $("#openFan").click(function(){
       var uid= $("#device-info-uid").attr("data-uid");
        openFan(uid);
    });
    $("#closeFan").click(function(){
        var uid= $("#device-info-uid").attr("data-uid");
        closeFan(uid);
    });
    //设备列表
    $(document).on("pageInit", "#device-list", function(e, id, page) {
        query();
    });
    $(document).on("pageInit", "#device-add", function(e, id, page) {
        $('#add-form')[0].reset();
    });


    $.init();
});

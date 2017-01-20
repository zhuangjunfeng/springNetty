var socket;

function initWebSocket(){
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket("ws://air.semsplus.com:5888/ws");
        socket.onmessage = function(event) {

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
                listHtml += "<div class='list-block media-list'><ul><li><a href='#device-info' class='item-link item-content'>" +
                    "<div class='item-media'>" +
                    "<img src='img/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg' style='width: 4rem;'></div>" + "<div class='item-inner'> <div class='item-title-row'><div class='item-title'>" + n.device_name +
                    "</div><div class='item-after'>不在线</div></div><div class='item-subtitle'>当前环境</div><div class='item-text'>PM超标甲醛超标</div> </div> </a> </li> </ul> </div>"
            })

            $("#devices").html(listHtml);
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
    //设备列表
    $(document).on("pageInit", "#device-list", function(e, id, page) {
        query();
    });
    $(document).on("pageInit", "#device-add", function(e, id, page) {
        $('#add-form')[0].reset();
    });

    $.init();
});

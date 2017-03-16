/**
 * 添加设备
 */
function add() {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "http://air.semsplus.com/rest/device",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        data: $("#add-form").serialize(),
        success: function(data) {
            $.toast("绑定成功");
        }
    })
}

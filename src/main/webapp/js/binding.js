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
            if(data.message=="success") {
                $.alert('绑定成功！');
                window.location.href = "http://air.semsplus.com/rest/wx/go";
            }
            if(data.message=="isIn") {
                $.alert('设备已存在！');
            }
            if(data.message=="onLogin") {
                window.location.href = "http://air.semsplus.com/rest/wx/go";
            }
        }
    })
}

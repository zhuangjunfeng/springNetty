/**
 * 添加设备
 */
function add() {
    if($('obj[name="device_name"]').val()==""||$('obj[name="device_uid"]').val()==""){
        $.alert('设备名称或UID不能为空！');
    }else{
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
}

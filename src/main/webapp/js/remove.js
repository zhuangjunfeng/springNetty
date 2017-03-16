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
//点击更多
$("#removeBtn").click(function(){
	var uid=GetQueryString("uid");
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "http://air.semsplus.com/rest/device/del",
        data: {uid: uid},
        success: function (data) {
            if(data.message=="success") {
                $.alert('解绑成功！');
                window.location.href = "http://air.semsplus.com/rest/wx/go";
            }
            if(data.message=="noIn") {
                $.alert('设备不存在！');
                window.location.href = "http://air.semsplus.com/rest/wx/go";
            }
        }
    })
});

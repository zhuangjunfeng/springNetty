$(function() {
    'use strict';

    $(".bar-tab a").click(function(){
        $.router.load("#"+$(this).attr("menu-data"));
    })
    $(".button-success").click(function(){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/rest/device",
            data: $("#add-form").serialize(),
            success: function (data) {

            }
        })

    })
    //设备列表
    $(document).on("pageInit", "#device-list", function(e, id, page) {
        query();
    });

    function query(){
        $.ajax({
            type:"GET",
            dataType:"json",
            url:"/rest/device/find",
            data:{_method:"GET"},
            success:function(data) {
                var typeList=data.data.list;
                var listHtml="";
                $.each(typeList,function(i,n) {
                    listHtml+="<div class='list-block media-list'><ul><li><a href='#device-info' class='item-link item-content'>" +
                        "<div class='item-media'>" +
                        "<img src='img/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg' style='width: 4rem;'></div>"
                        +"<div class='item-inner'> <div class='item-title-row'><div class='item-title'>"
                        + n.device_name+
                        "</div><div class='item-after'>在线</div></div><div class='item-subtitle'>当前环境</div><div class='item-text'>PM超标甲醛超标</div> </div> </a> </li> </ul> </div>"
                })

                $("#devices").html(listHtml);
            }
        });
    }

    $.init();
});

$(function() {
    'use strict';

    $(".bar-tab a").click(function(){
        $.router.load("#"+$(this).attr("menu-data"));
    })

    //无限滚动
    $(document).on("pageInit", "#device-list", function(e, id, page) {
        

    });

    $.init();
});

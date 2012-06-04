$(function(){
    
    var i = 0;

    $('.helloworld-widget').each(function(){

        if(i == 0) $('head').append('<style type="text/css">.helloworld-box{font-family:helvetica,arial,sans-serif;font-size:13px;line-height:18px;background:#fafafa;border:1px solid #ddd;color:#666;border-radius:3px}</style>');
        i++;

        var $container = $(this);
        var repo = $container.data('repo');

        var $widget = $(' \
            <div class="helloworld-box repo">HelloWorld </div> \
        ');

        $widget.appendTo($container);

    });

});
/*global define, $ window */

define( "HelloWorld/widgets/HelloWorldWidget", [ "jquery" ], function($) {

    function HelloWorldWidget() {
    }

    HelloWorldWidget.prototype.HelloWorldText = undefined;

    HelloWorldWidget.prototype.setMainContent = function(){

        var i = 0, 
        self = this, $container, repo, $widget;
        if(i === 0) {
            $('head').append('<style type="text/css">.helloworld-box{font-family:helvetica,arial,sans-serif;font-size:13px;line-height:18px;background:#fafafa;border:1px solid #ddd;color:#666;border-radius:3px}</style>');
            i++;
        }
        $container = $(this);
        repo = $container.data('repo');

        if(self.HelloWorldText !== undefined){
            $widget = $(' \\ <div class="helloworld-box repo">' + self.HelloWorldText + '</div> \\ ');
        }

        $widget.appendTo($container);
        $('.helloworld-widget').html($widget);

        return $widget;
    };

 return HelloWorldWidget;
});
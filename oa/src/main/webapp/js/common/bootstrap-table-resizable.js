/**
 * @author: Dennis Hernández
 * @webSite: http://djhvscf.github.io/Blog
 * @version: v1.0.0
 */

(function ($) {
    'use strict';

    var initResizable = function (that) {
        //Deletes the plugin to re-create it
        that.$el.colResizable({disable: true,postbackSafe:true});

        //Creates the plugin
        that.$el.colResizable({
            liveDrag: that.options.liveDrag,
            resizeMode: that.options.resizeMode,
            headerOnly: that.options.headerOnly,
            minWidth: that.options.minWidth,
            hoverCursor: that.options.hoverCursor,
            dragCursor: that.options.dragCursor,
            partialRefresh: that.partialRefresh,
            onResize: that.onResize,
            onDrag: that.options.onResizableDrag
        });
    };

    $.extend($.fn.bootstrapTable.defaults, {
        resizable: false,
        liveDrag: false,
        fixed: true,
        headerOnly: false,
        minWidth: 15,
        hoverCursor: 'e-resize',
        dragCursor: 'e-resize',
        onResizableResize: function (e) {
            return false;
        },
        onResizableDrag: function (e) {
            return false;
        }
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _toggleView = BootstrapTable.prototype.toggleView,
        _resetView = BootstrapTable.prototype.resetView;

    BootstrapTable.prototype.toggleView = function () {
        _toggleView.apply(this, Array.prototype.slice.apply(arguments));

        if (this.options.resizable && this.options.cardView) {
            //Deletes the plugin
            $(this.$el).colResizable({disable: true});
        }
    };

    BootstrapTable.prototype.resetView = function () {
        var that = this;

        _resetView.apply(this, Array.prototype.slice.apply(arguments));

        if (this.options.resizable) {
            // because in fitHeader function, we use setTimeout(func, 100);
            setTimeout(function () {
                initResizable(that);
            }, 100);
        }
    };

    BootstrapTable.prototype.onResize = function (e) {
        var that = $(e.currentTarget);
        that.bootstrapTable('resetView');
        if(that.data('bootstrap.table')){
            that.data('bootstrap.table').options.onResizableResize.apply(e);
        }
    };
    
    $.extend($.fn.bootstrapTable.defaults, {
        showResize: true
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initToolbar = BootstrapTable.prototype.initToolbar;

    BootstrapTable.prototype.initToolbar = function () {
        this.showToolbar = this.options.showResize;

        _initToolbar.apply(this, Array.prototype.slice.apply(arguments));

        if (this.options.showResize) {
            var that = this;
            var $btnGroup = this.$toolbar.find('>.btn-group');
            $btnGroup.append('<button class="btn btn-default change-mode" type="button" resizeMode="'+this.options.resizeMode+'" name="resize" aria-label="resize" style="padding: 9px 17px;" title="显示表格的宽度"><i class="glyphicon glyphicon-text-width icon-text-width"></i></button>');
            $btnGroup.find('[name="resize"]').click(function(){
                var resizeMode = 'overflow';
                if($(this).attr('resizeMode')){
                    var resizeMode = $(this).attr('resizeMode');
                    if(resizeMode == null || resizeMode == ''){
                        resizeMode = "overflow";
                    } else if(resizeMode == "fit"){
                        resizeMode = "overflow";
                    } else {
                        resizeMode = "fit";
                    }
                } 
                $.post(
                "/common/component/maishicommon",
                {
                    "servlettype" : 'brootstraptable',
                    "tableType" : that.options.tabType,
                    "userId" : that.options.userId,
                    "resizeMode" : resizeMode,
                    "configType" : 'resizeMode'
                },
                function(data) {
                    window.location.reload();
                });
            });
        }
    };
})(jQuery);

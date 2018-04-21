/**
 * @author zhixin wen <wenzhixin2010@gmail.com>
 * extensions: https://github.com/kayalshri/tableExport.jquery.plugin
 */

(function ($) {
    'use strict';
    var sprintf = $.fn.bootstrapTable.utils.sprintf;

    var TYPE_NAME = {
        json: 'JSON',
        xml: 'XML',
        png: 'PNG',
        csv: 'CSV',
        txt: 'TXT',
        sql: 'SQL',
        doc: 'MS-Word',
        excel: 'MS-Excel',
        xlsx: 'MS-Excel (OpenXML)',
        powerpoint: 'MS-Powerpoint',
        pdf: 'PDF'
    };

    $.extend($.fn.bootstrapTable.defaults, {
        showExport: false,
        exportDataType: 'basic', // basic, all, selected
        // 'json', 'xml', 'png', 'csv', 'txt', 'sql', 'doc', 'excel', 'powerpoint', 'pdf'
        exportTypes: ['json', 'xml', 'csv', 'txt', 'sql', 'excel'],
        exportOptions: {}
    });

    $.extend($.fn.bootstrapTable.defaults.icons, {
        'export': 'glyphicon-export icon-share'
    });

    $.extend($.fn.bootstrapTable.locales, {
        formatExport: function () {
            return 'Export data';
        }
    });
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales);

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initToolbar = BootstrapTable.prototype.initToolbar;
    
    /**
    BootstrapTable.prototype.getExportSearchDatas = function(){
    	return "";
    }*/

    BootstrapTable.prototype.initToolbar = function () {
        this.showToolbar = this.options.showExport;

        _initToolbar.apply(this, Array.prototype.slice.apply(arguments));

        if (this.options.showExport) {
            var that = this,
                $btnGroup = this.$toolbar.find('.changed-export'),
                $export = $btnGroup.find('div.export');

            if (!$export.length) {
                $export = $([
                    '<div class="export btn-group">',
                        '<button ex-type="contractInfo" class="btn' +
                            sprintf(' btn-%s', this.options.buttonsClass) +
                            sprintf(' btn-%s', this.options.iconSize) +
                            ' dropdown-toggle" aria-label="export type" ' +
                            'title="' + this.options.formatExport() + '" ' +
                            'data-toggle="dropdown" type="button">',
                            sprintf('<i class="%s %s"></i> ', this.options.iconsPrefix, this.options.icons['export']),
                            '<span class="export-dc ">导出合同信息</span><span class="caret"></span>',
                        '</button>',
                        '<ul class="dropdown-menu" role="menu">',
                        '</ul>',
                    '</div>'+
                    '<div class="export btn-group">',
                    '<button ex-type="contractBilling" class="btn' +
                        sprintf(' btn-%s', this.options.buttonsClass) +
                        sprintf(' btn-%s', this.options.iconSize) +
                        ' dropdown-toggle" aria-label="export type" ' +
                        'title="' + this.options.formatExport() + '" ' +
                        'data-toggle="dropdown" type="button">',
                        sprintf('<i class="%s %s"></i> ', this.options.iconsPrefix, this.options.icons['export']),
                        '<span class="export-dc">导出合同收款记录</span><span class="caret"></span>',
                    '</button>',
                    '<ul class="dropdown-menu" role="menu">',
                    '</ul>',
                '</div>'   
                        
                        ].join('')).appendTo($btnGroup);

                var $menu = $export.find('.dropdown-menu'),
                    exportTypes = this.options.exportTypes;

                if (typeof this.options.exportTypes === 'string') {
                    var types = this.options.exportTypes.slice(1, -1).replace(/ /g, '').split(',');

                    exportTypes = [];
                    $.each(types, function (i, value) {
                        exportTypes.push(value.slice(1, -1));
                    });
                }
                $.each(exportTypes, function (i, type) {
                    if (TYPE_NAME.hasOwnProperty(type)) {
                        $menu.append(['<li class="menuitem-'+ type +'" role="menuitem" data-type="' + type + '">',
                                '<a href="javascript:void(0)">',
                                    TYPE_NAME[type],
                                '</a>',
                            '</li>'].join(''));
                    }
                });
                var ex_contract;
                $menu.find('li').click(function () {
                	var columns = that.options.columns[0];
                	ex_contract = $(this).parent().parent().find('button').attr('ex-type');
                	var ignoreColumn = [];
                	if(columns != null && columns.length>0){
                		for(var i=0;i<columns.length;i++){
                			if(columns[i].exportflag != null && !columns[i].exportflag){
                				ignoreColumn.push(columns[i].field.toString());
                			}
                		}
                	}
                	debugger;
                	var date = new Date();
                	var month = date.getMonth()+1;
                	if(month<10){
                		month = "0"+month;
                	}
                	var day = date.getDate();
                	if(day==10){
                		day = "0"+day;
                	}
                	var tableName ="合同信息"+date.getFullYear()+month+day;
                    var type = $(this).data('type'),
                        doExport = function () {
                    	if(ex_contract=='contractBilling'){
                    		//导出开票信息
                    		window.location = "/makshi/contract/contractinfo/upload.ht";
                    		return;
                    	}
                            that.$el.tableExport($.extend({}, that.options.exportOptions, {
                                type: type,
                                escape: false,
                                ignoreColumn : ignoreColumn,
                                exportDataType: that.options.exportDataType,
                                columns: that.columns,
                                exportUrl: that.options.exportUrl,
                                exportParams: $.extend({},that.options.exportParams,{searchParams:that.options.getExportSearchDatas(),tableName:tableName})
                            } ));
                        };

                    if (that.options.exportDataType === 'all' && that.options.pagination) {
                        //that.$el.one(that.options.sidePagination === 'server' ? 'post-body.bs.table' : 'page-change.bs.table', function () {
                        //    doExport();
                        //    that.togglePagination();
                       // });
                        //that.togglePagination();
                    	
                    	doExport();
                    } else if (that.options.exportDataType === 'selected') {
                        var data = that.getData(),
                            selectedData = that.getAllSelections();

                        // Quick fix #2220
                        if (that.options.sidePagination === 'server') {
                            data = {total: that.options.totalRows};
                            data[that.options.dataField] = that.getData();

                            selectedData = {total: that.options.totalRows};
                            selectedData[that.options.dataField] = that.getAllSelections();
                        }

                        that.load(selectedData);
                        doExport();
                        that.load(data);
                    } else {
                        doExport();
                    }
                });
            }
        }
    };
})(jQuery);

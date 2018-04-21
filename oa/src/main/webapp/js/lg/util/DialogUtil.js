/**
 * 为了ligerUi的顶层dialog显示
 */
var DialogUtil = {
	open : function(opts){
		if(opts.type=="post"){
			opts.postUrl = opts.url;
			opts.url=__ctx+"/platform/form/post/dialog.ht";
		}
		var outerWindow =window.top;
		return outerWindow.$.ligerDialog.open(opts);
		
	}
}
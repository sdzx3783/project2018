var $modal =null;
  
  function closeModal(){
	  if($modal){
		  $modal.modal('close');
	  }
  }
  
var EventsList = function(element, options) {
  var aryHtml=['<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="my-modal">',
            '<div class="am-modal-dialog">',
            '<div class="am-modal-hd">没有更多记录了.</div>',
            '</div>',
            '</div>']
  
  var objBody=$("body");
  objBody.append(aryHtml.join(""));

  var $main = $('#wrapper');
  var $list = $main.find('#events-list');
  var $pullDown = $main.find('#pull-down');
  var $pullDownLabel = $main.find('#pull-down-label');
  var $pullUp = $main.find('#pull-up');
  var topOffset = -$pullDown.outerHeight();
  //var topOffset =0;
  this.compiler = Handlebars.compile($('#tpi-list-item').html());
  this.prev = this.next = this.page = options.params.page;
  this.total = null;

  this.getURL = function(params) {
	    var queries = [];
	    for (var key in  params) {
		      if (key !== 'page' && key !='callback') {
		        queries.push(key + '=' + params[key]);
		      }
	    }
	    queries.push('page=');
	    var rtn= options.url + '?' + queries.join('&');
	    
	    return rtn;
  };

  this.renderList = function(start, type) {
	    var _this = this;
	    var $el = $pullDown;
	    //var $el = $pullUp;
	
	    if (type === 'load') {
	    	$el = $pullUp;
	    }

	    $.get(this.URL + start).then(function(data) {
	      if(options.params.callback!=null) {
	    	  options.params.callback(data);
	      }
	      _this.total = data.total;
	      var html = _this.compiler(data.rowList);
	     
	      if (type === 'refresh') {
	        $list.html(html);
	      } else if (type === 'load') {
	        $list.append(html);
	      } else {
	        $list.html(html);
	      }
	
	      // refresh iScroll
	      setTimeout(function() {
	        _this.iScroll.refresh();
	      }, 100);
	    }, 
	    function() {
	    	console.log('Error...')
	    }).always(function() {
	    	_this.resetLoading($el);
		    if (type !== 'load') {
		        _this.iScroll.scrollTo(0, topOffset, 800, $.AMUI.iScroll.utils.circular);
		    }
	    });
  };

  this.setLoading = function($el) {
    $el.addClass('loading');
  };

  this.resetLoading = function($el) {
    $el.removeClass('loading');
  };

  this.init = function() {
    var myScroll = this.iScroll = new $.AMUI.iScroll('#wrapper', {
      click: true
    });
    // myScroll.scrollTo(0, topOffset);
    var _this = this;
    var pullFormTop = false;
    var pullStart;

    this.URL = this.getURL(options.params);
    this.renderList(options.params.page);

    myScroll.on('scrollStart', function() {
      if (this.y >= topOffset) {
        pullFormTop = true;
      }
      pullStart = this.y;
    });

    myScroll.on('scrollEnd', function() {
     
      if (pullFormTop && this.directionY === -1) {
        _this.handlePullDown();
      }
      pullFormTop = false;
      
      console.info("pullStart:" + pullStart +",this.y" + this.y +",directionY:" +this.directionY);

      // pull up to load more
      if (pullStart === this.y && (this.directionY === 1)) {
        _this.handlePullUp();
      }
    });
    $(".am-list").css("min-height",document.documentElement.clientHeight-50);
  };
  
  

  this.handlePullDown = function() {
    console.log('handle pull down');
//    if (this.prev > 1) {
      this.setLoading($pullDown);
      this.prev =this.next=1 ;
      
      this.renderList(this.prev, 'refresh');
//    } 
  };

  this.handlePullUp = function() {
    console.log('handle pull up');
    if (this.next < this.total) {
      this.setLoading($pullUp);
      this.next += options.params.page;
      this.renderList(this.next, 'load');
    } else {
      	$modal = $("#my-modal");
      	$modal.modal();
      	setTimeout("closeModal()",1000);
    }
  }
};


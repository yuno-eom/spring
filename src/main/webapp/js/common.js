//jquery.i18n.properties-min-1.0.9.js
$(document).ready(function(){
	loadMessages();
});

function getCookie(name){
	var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	if(arr != null) { return unescape(arr[2]);} else{  return null; }
}

function loadMessages() {
	$.i18n.properties({
		name:['message','common'],
		path:'/spring/resources/messages/',
		//path:'/spring/WEB-INF/classes/messages/',
		mode:'both',
		language:getCookie('selectLocale'),
		callback: function() {
			//i18nExamples();
			//alert( msg.a.userId );
		}
	});
}

function i18nExamples() {
	var msg = 'msg.a.join2';
	var arg1 = 'John';
	var arg2 = '3';
	alert('$.i18n.prop(\''+msg+'\')  -->  '+$.i18n.prop(msg, arg1, arg2));
}

//inactive backspace...
$(document).on('keydown',function(e){
	var $target = $(e.target || e.srcElement);
	if(e.keyCode == 8){
		if(!$target.is('input,textarea,[contenteditable="true"]')
				|| $target.is(':radio') || $target.is(':checkbox')
				|| e.target.readOnly || e.target.disabled){
			e.preventDefault();
		}
	}
});
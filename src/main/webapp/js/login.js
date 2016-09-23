//index.ftl, login.ftl

function doLogin(){
	//loadMessages(); --> common.js
	
	if($('#userId').val() == ''){
		alert( msg.a.userId ); //== alert($.i18n.prop('msg.a.userId'));
		$('#userId').focus();
		return false;
	}else if($('#pwd').val() == ''){
		alert( msg.a.pwd );
		$('#pwd').focus();
		return false;
	}else{
		$('#frmLogin').submit();
	}
}

function goJoin(){
	var pw = 400;
	var ph = 400;
	var pl = (screen.availWidth-pw)/2;
	var pt = (screen.availHeight-ph)/2;
	
	userWin = window.open('user/pop/join','userWin','width='+pw+',height='+ph+',left='+pl+',top='+pt+'');
	userWin.focus();
}

function doUserInfo(){
	goJoin();
	
	$('#frmUserInfo').attr('target','userWin');
	$('#frmUserInfo').submit();
}

function ajaxJoinCheck(){
	//loadMessages(); --> common.js
	
	if($('#email').val() == ""){
		alert( msg.a.email );
		$('#email').focus();
		return false;
	}else{
		$.ajax({
			url : 'user/ajaxJoinCheck',
			type: 'POST',
			data: $('#frmJoinCheck').serialize(),
			dataType: 'json',
			success: function(result, status) {
				/**/ //List로 받을 때.. 회원중복(동일 이메일) 허용...
				if (result.length > 0) {
					$('#rstJoin').empty();
					if(result.length > 1){
						$('#rstJoin').append( $.i18n.prop('msg.a.join2', result[0].userNm ,result.length) );
					}else{
						$('#rstJoin').append( $.i18n.prop('msg.a.join1', result[0].userNm) );
					}
				/*/ //Object로 받을 때..
				if (result != null) {
					$('#rstJoin').empty();
					$('#rstJoin').append( $.i18n.prop('msg.a.join1', result[0].userNm) );
				/**/
				}else{
					$('#rstJoin').empty();
					$('#rstJoin').append( msg.a.join0 );
				}
			},
			error:function(result, status) {
				alert(status + '\n\n' + result);
			}
		});
	}
}
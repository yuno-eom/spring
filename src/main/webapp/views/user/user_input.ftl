<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>DEV SPRING3 FRAMEWORK</title>
	<link rel="stylesheet" type="text/css" href="${base}/css/default.css" media="screen, projection" />
	<script type="text/javascript" src="${base}/js/lib/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${base}/js/lib/jquery.i18n.properties-min-1.0.9.js"></script>
	<script type="text/javascript" src="${base}/js/common.js"></script>
</head>
<body>
	<div id="">
		* messageSource test : <@spring.message "com.locale" />
		<form id="frmJoin" method="post" action="join">
			<h3><strong>[회원정보]</strong></h3>
			<ul>
				<li>
					<label>아이디</label>
					<#if userInfo?exists>
						${userInfo.userId}
						<input type="hidden" name="cmd" value="update" />
						<input type="hidden" name="userId" value="${userInfo.userId}" />
					<#else>
						<input type="hidden" name="cmd" value="insert" />
						<input type="text" id="userId" name="userId" maxlength="20" onkeyup="ajaxCheckId();" />
						<div id="rstCheckId"></div><input type="hidden" id="checkId" value="F" />
					</#if>
				</li>
				<li>
					<label>비밀번호</label>
					<input type="password" id="pwd" name="pwd" maxlength="20" />
				</li>
				<li>
					<label>비밀번호 확인</label>
					<input type="password" id="pwd_chk" name="pwd_chk" maxlength="20" onblur="doCheckPwd();" />
					<div id="rstCheckPwd"></div><input type="hidden" id="checkPwd" value="F" />
				</li>
				<li>
					<label>이름</label>
					<input type="text" id="userNm" name="userNm" maxlength="20" <#if userInfo?exists>value="${userInfo.userNm}"</#if> />
				</li>
				<li>
					<label>이메일</label>
					<input type="text" id="email" name="email" maxlength="100" <#if userInfo?exists>value="${userInfo.email}"</#if> />
				</li>
				<input type="hidden" name="grade" value="U" />
				
				<a href="#" onclick="ajaxJoin();">[정보저장]</a>
			</ul>
		</form>

		<script type='text/javascript'>
			function ajaxJoin(){
				if(validateUser()){
					$.post('../ajaxSubmit', $('#frmJoin').serialize()).done(function(data){ 
						if(data > 0) {
							alert('등록되었습니다.');window.close();
						} else {
							alert('DB Insert Failed..');
						}
					}).fail(function(data){ $('body').empty().append(data.responseText); });
				}
			}
			
			function validateUser(){
				var inputs = [{id:'userId', msg:'아이디를 입력해 주세요.'}, {id:'pwd', msg:'비밀번호를 입력해 주세요.'}
					, {id:'userNm', msg:'이름을 입력해 주세요.'}, {id:'email', msg:'이메일을 입력해 주세요.'}];
				
				if(!validateInput(inputs)){
					return false;
				}else{
					if($('#checkId').val() == 'F'){
						if(!ajaxCheckId()) { alert('아이디를 확인해주세요.'); return false; }
					}else if($('#checkPwd').val() == 'F'){
						if(!doCheckPwd()) { alert('비밀번호를 확인해주세요.'); return false; }
					}else{
						return true;
					}
				}
			}
			
			function validateInput(inputs){
				var valid = true;
				
				$.each(inputs, function (index, input) {
					if($('#'+input.id).val() == ''){
						alert(input.msg);
						$('#'+input.id).focus();
						valid = false;
						return false; //break;
					}
				});
				
				return valid;
			}
			
			function ajaxCheckId(){
				if($('#userId').val() != ''){
					$.post('../ajaxCheckId', $('#frmJoin').serialize()).done(function(data){ 
						if(data){
							$('#checkId').val('T');
							$('#rstCheckId').empty().append('사용 가능한 아이디입니다.');
							return true;
						}else{
							$('#checkId').val('F');
							$('#rstCheckId').empty().append('해당 아이디 사용은 불가능합니다.');
							return false;
						}
					}).fail(function(data){ $('body').empty().append(data.responseText); });
				}
			}
			
			function doCheckPwd(){
				if($('#pwd').val() == $('#pwd_chk').val()){
					$('#checkPwd').val('T');
					$('#rstCheckPwd').empty();
					return true;
				}else{
					$('#checkPwd').val('F');
					$('#rstCheckPwd').empty().append('비밀번호 확인이 일치하지않습니다.');
					return false;
				}
			}
		</script>
	</div>
</body>
</html>
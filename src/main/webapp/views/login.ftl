
<div id="login">
	LOGIN
</div>

<div id="join">
	<form id="frmLogin" method="post" action="${base}/login">
		<input type="hidden" name="mode" value="login" />
		<fieldset>
			<legend></legend>
			<h3><strong>[회원 로그인]</strong></h3>
			<ul>
				<li>
					<label>아이디</label>
					<input type="text" id="userId" name="userId" maxlength="12" <#if cookieId?exists>value="${cookieId}"</#if> />
					<input type="checkbox" id="saveId" name="saveId" value="Y" <#if cookieId?exists>checked</#if> /><label for="saveId"><span style="color:#888;">아이디 저장하기</span></label>
				</li>
				<li>
					<label>패스워드</label>
					<input type="password" id="pwd" name="pwd" maxlength="12" value="" onkeydown="if(event.keyCode==13) doLogin();" />
				</li>
				<a href="#" onclick="doLogin();" />[로그인]</a> <a href="#" onclick="goJoin();">[회원가입]</a>
			</ul>
		</fieldset>
	</form>
	
	<form id="frmJoinCheck" method="post">
		<fieldset>
			<legend></legend>
			<h3><strong>[가입확인]</strong></h3>
			<ul>
				<li>
					<label>이메일:</label>
					<input type="text" id="email" name="email" maxlength="50" onkeydown="if(event.keyCode==13) ajaxJoinCheck();" />
					<input type="text" style="display:none;" /><!-- enter submit방지.. -->
				</li>
				<a href="#" onclick="ajaxJoinCheck();" />[확인]</a>
				<div id="rstJoin"></div>
			</ul>
		</fieldset>
	</form>
</div>

<script>
	$(window).load(function() {
		<#if cookieId?exists>$('#pwd').focus();<#else>$('#userId').focus();</#if>
		<#if ERROR?exists>alert('${ERROR!}');</#if>
	});
</script>

<script src="${base}/js/login.js"></script>

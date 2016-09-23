<#assign cutTag=JspTaglibs["/WEB-INF/tld/cutString.tld"] />
<#setting date_format = "yyyy.MM.dd" />

<div style="margin:10px;">
	Hello!! Today is ${.now?date} , contextPath is ${base} , messageSource(locale) is ${.locale} (<@spring.message "com.locale" />)
</div>

<div id="accordion" style="float: left; width: 500px;">
	<#assign boardGrp = [["notice","공지사항"],["faq","FAQ"],["inquiry","Q&A"]]>
	<#list boardGrp as bGrp>
	<h3>${bGrp[1]}</h3>
		<div>
			<#if boardList?has_content>
				<#list boardList as cMap>
					<#if cMap.BD_GRP == bGrp[0]>
						<li><a href="board/${bGrp[0]}/${cMap.BD_SEQ}"><@cutTag.CutString cutString="${cMap.TITLE}" cutLength="44" strongString="" suffix="..." /></a>
						- ${cMap.REG_YMDT?date("yyyy-MM-dd")}</li>
					</#if>
				</#list>
			</#if>
		</div>
	</#list>
</div>

<div id="blank" style="float: left; width: 10px;">&nbsp;</div>

<div id="join" style="float: left; width: 300px;">
	<#if Session?exists && Session.userId?exists>
		<fieldset>
			<legend></legend>
			<h3><strong>[회원 로그인]</strong></h3>
			<ul>
				<li>안녕하세요...</li>
				<li>${Session.userNm!}님 반갑습니다</li>
				<a href="${base}/logout">[로그아웃]</a> <a href="#" onclick="doUserInfo();">[정보수정]</a>
			</ul>
		</fieldset>
		
		<form id="frmUserInfo" method="post" action="user/pop/${Session.userId}">
			<!--input type="hidden" name="userId" value="${Session.userId}" /-->
		</form>
	<#else>
		<form id="frmLogin" method="post" action="login">
			<input type="hidden" name="mode" value="index" />
			<fieldset>
				<legend></legend>
				<h3><strong>[회원 로그인]</strong></h3>
				<ul>
					<li>
						<label>아이디 &nbsp; </label>
						<input type="text" id="userId" name="userId" maxlength="12" <#if cookieId?exists>value="${cookieId}"</#if> style="width:100px;" />
						<input type="checkbox" id="saveId" name="saveId" value="Y" <#if cookieId?exists>checked</#if> /><label for="saveId" style="font-size:9px; color:#888;">Save</label>
					</li>
					<li>
						<label>패스워드</label>
						<input type="password" id="pwd" name="pwd" maxlength="12" value="" onkeydown="if(event.keyCode==13) doLogin();" />
					</li>
					<a href="#" onclick="doLogin();">[로그인]</a> <a href="#" onclick="goJoin();">[회원가입]</a>
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
	</#if>
</div>

<script>
	$(window).load(function() {
		<#if cookieId?exists>$('#pwd').focus();<#else>$('#userId').focus();</#if>
		<#if ERROR?exists>alert('${ERROR!}');</#if>
	});
	
	$(function() {
		$('#accordion').accordion();
	});
	
	$(function() {
		$('#saveId').button();
	});
</script>

<script src="${base}/js/login.js"></script>

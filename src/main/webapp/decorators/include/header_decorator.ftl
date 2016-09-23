<!-- header : start -->
<a href="${base}/index">Index</a>
| <a href="${base}/board/notice" <#if menu == 'board'>style="color:#f00"</#if>>Board</a>
<#if Session?exists && Session.grade! == "A">
	| <a href="${base}/user/users" <#if menu == 'user'>style="color:#f00"</#if>>Users</a>
	| <a href="${base}/report/board" <#if menu == 'report'>style="color:#f00"</#if>>Reports</a>
</#if>
| <a href="${base}/test/key" <#if menu == 'test'>style="color:#f00"</#if>>Test</a>

<div style="float:right;">
	<form id="locale" method="post" action="${base}/index">
		<select id="lang" name="lang" onchange="submit();">
			<option value="ko" <#if .locale! == "ko">selected</#if>>한국어</option>
			<option value="en" <#if .locale! == "en">selected</#if>>영어</option>
			<option value="ar" <#if .locale! == "ar">selected</#if>>이라크어</option>
		</select>
	</form>
</div>
<!-- header : end -->
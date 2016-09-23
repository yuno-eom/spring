<!-- side : start -->
<dl>
	<#switch menu>
		<#case "board">
			<dt style="padding:10px;">Board</dt>
			<dd><a href="${base}/board/notice" <#if bdGrp == 'notice'>style="color:#f00"</#if>>공지사항</a></dd>
			<dd><a href="${base}/board/faq" <#if bdGrp == 'faq'>style="color:#f00"</#if>>FAQ</a></dd>
			<dd><a href="${base}/board/inquiry" <#if bdGrp == 'inquiry'>style="color:#f00"</#if>>서비스문의</a></dd>
			<#break>
		<#case "user">
			<dt style="padding:10px;">User</dt>
			<dd><a href="${base}/user/users" style="color:#f00">GRID</a></dd>
			<dd>------------</dd>
			<dd><a href="${base}/user/users.json">JSON</a></dd>
			<dd><a href="${base}/user/users.xml">XML</a></dd>
			<dd><a href="${base}/user/users.xls">EXCEL</a></dd>
			<dd><a href="${base}/user/users.pdf">PDF</a></dd>
			<#break>
		<#case "report">
			<dt style="padding:10px;">Reports</dt>
			<dd><a href="${base}/report/board" style="color:#f00">Board</a></dd>
			<#break>
		<#default>
			<dt style="padding:10px;">ETC</dt>
			<dd><a href="#">etc</a></dd>
	</#switch>  
</dl>
<!-- side : end -->
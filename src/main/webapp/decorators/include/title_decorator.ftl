<!-- title : start -->
<div style="margin:20px 0; font-size:15px; font-weight:bold;">
	<#switch menu>
		<#case "board">
			${bdGrp!}
			<#break>
		<#case "user">
			Users
			<#break>
		<#case "report">
			Reports
			<#break>
		<#default>
			Title
	</#switch>  
</div>
<!-- title : end -->
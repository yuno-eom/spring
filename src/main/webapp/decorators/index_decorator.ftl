<#setting locale = springMacroRequestContext.getLocale() />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>DEV SPRING3 FRAMEWORK</title>
	<link rel="stylesheet" type="text/css" href="${base}/css/default.css" media="screen, projection" />
	<link rel="stylesheet" type="text/css" href="${base}/css/redmond/jquery-ui-1.10.3.custom.min.css" media="screen, projection" />
	<script type="text/javascript" src="${base}/js/lib/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${base}/js/lib/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="${base}/js/lib/jquery.i18n.properties-min-1.0.9.js"></script>
	<script type="text/javascript" src="${base}/js/common.js"></script>
</head>
<body>
	<div id="wrapper" <#if .locale == "ar">style="direction: rtl;"</#if>>
		<div id="container">
			<div id="header">
				<#include "/decorators/include/header_decorator.ftl" />
			</div>
			<div id="primaryContent">
				${body}
			</div>
			<div id="footer">
				<#include "/decorators/include/footer_decorator.ftl" />
			</div>
		</div>
	</div>
</body>
</html>
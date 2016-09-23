
<link rel="stylesheet" href="${base}/jqGrid/css/ui.jqgrid.css" type="text/css" />

<#if .locale! == "ar">
	<script src="${base}/jqGrid/js/i18n/grid.locale-ar.js" type="text/javascript"></script>
<#elseif .locale! == "ko">
	<script src="${base}/jqGrid/js/i18n/grid.locale-kr.js" type="text/javascript"></script>
<#else>
	<script src="${base}/jqGrid/js/i18n/grid.locale-en.js" type="text/javascript"></script>
</#if>

<script src="${base}/jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>

<div style="position:relative">
	<div class="grid-header" style="width:100%;">
		<form id="frmUser" method="post" action="users">
			<select name="grid" style="width: 100px;" onchange="submit();">
				<option value="jqGrid" <#if searchUser.grid! == "jqGrid">selected</#if>>jqGrid</option>
				<option value="slickGrid" <#if searchUser.grid! == "slickGrid">selected</#if>>slickGrid</option>
				<option value="dhtmlxGrid" <#if searchUser.grid! == "dhtmlxGrid">selected</#if>>dhtmlxGrid</option>
			</select>
			&nbsp;Search Name:<input type="text" id="searchName" name="searchText" value="${searchUser.searchText!}">
		</form>
	</div>
	<table id="list"></table>
	<div id="page"></div>
</div>

<script>
	var columns = [
		{name: "USER_ID", index: "USER_ID", width: 100, editable: false, editrules: {required: true}, key: true},
		{name: "USER_PW", index: "USER_PW", width: 150, editable: true, editrules: {required: true}, editoptions: {maxlength:"20"}},
		{name: "USER_NM", index: "USER_NM", width: 120, editable: true, editrules: {required: true}, editoptions: {maxlength:"20"}},
		{name: "EMAIL", index: "EMAIL", width: 150, editable: true, editoptions: {maxlength:"100"}},
		{name: "GRADE", index: "GRADE", width: 50, editable: true, editrules: {required: true}, edittype: "select", editoptions: {value: "A:A;U:U"}}
	];
	
	function jqGridRender() {
		$("#list").jqGrid({
			url: "userGrid",
			datatype: "json",
			mtype: "post",
			postData: {searchString: $("#searchName").val()},
			//jsonReader: {
			//	repeatitems: false, 
			//},
			colNames: ["<@spring.message 'lbl.userId' />", "<@spring.message 'lbl.pwd' />", "<@spring.message 'lbl.userNm' />", "<@spring.message 'lbl.email' />", "<@spring.message 'lbl.grade' />"],
			colModel: columns,
			viewrecords: true,
			gridview: true,
			height: "auto",
			rowNum: 10,
			pager: "#page",
			sortable: false,
			rownumbers: true,
			multiselect: true,
			<#if .locale! == "ar">direction: "rtl",</#if>
			cellEdit: true,
			cellurl: "cellEdit", //edit 'cell'
			editurl: "cellEdit", //add 'row', del 'row'
			afterInsertRow: function(rowid, rowdata, rowelem){
				$("#list").jqGrid("setColProp", "USER_ID", {editable: true});
			},
			beforeEditCell: function(id, name, val, iRow, iCol){
				$("#list").jqGrid("setColProp", "USER_ID", {editable: false});
			},
			afterSubmit: function(response, postdata){
				alert("submit:"+response);
				//var res = $.parseJSON(response.responseText);
				//if (res && res.insertStatus) {
				//	alert(res.insertStatus);
				//}
			}
		});
		
		$("#list").jqGrid("navGrid", "#page", {edit: false, add: false, del: true, refresh: false, search: false});
		$("#list").jqGrid("inlineNav", "#page", {add: true, edit: false, save: true, calcel: true});
	}
	
	jqGridRender();
</script>

<link rel="stylesheet" href="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.css" type="text/css" />
<link rel="stylesheet" href="${base}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_skyblue.css" type="text/css" />

<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_keymap_excel.js"></script>

<style>
	div.mygrid_light table.obj tr.rowselected td.cellselected, div.mygrid table.obj td.cellselected {
		background-color:#ACCADD;
	}
</style>

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
	<div id="mygrid" style="width:650px;height:300px;"></div>
	<button onclick="addRow()">Add Row</button>
	<button onclick="removeRow()">Remove Row</button>
</div>

<script>
	var mygrid;
	
	function doInitGrid() {
		mygrid = new dhtmlXGridObject("mygrid");
		mygrid.setImagePath("${base}/dhtmlxSuite/dhtmlxGrid/codebase/imgs/");
		mygrid.setColumnIds("userId,pwd,userNm,email,grade"); //without space...
		mygrid.setHeader("<@spring.message 'lbl.userId' />,<@spring.message 'lbl.pwd' />,<@spring.message 'lbl.userNm' />,<@spring.message 'lbl.email' />,<@spring.message 'lbl.grade' />");
		mygrid.setInitWidths("*,150,120,150,60");
		mygrid.setColAlign(",,,,center");
		mygrid.setColSorting("str,na,str,na,str");
		mygrid.init();
		mygrid.setSkin("dhx_skyblue");
		mygrid.load("userGrid2","js");
		mygrid.attachEvent("onRowSelect", doOnRowSelected);
	}
	
	doInitGrid();
	
	function addRow(){
		var newId = (new Date()).valueOf()
		mygrid.addRow(newId,"",mygrid.getRowsNum())
		mygrid.selectRow(mygrid.getRowIndex(newId),false,false,true);
	}
	
	function removeRow(){
		var selId = mygrid.getSelectedId()
		mygrid.deleteRow(selId);
	}
	
	function doOnRowSelected(rowID,celInd){
		//alert("Selected row ID is "+rowID+"\nUser clicked cell with index "+celInd);
	}
</script>
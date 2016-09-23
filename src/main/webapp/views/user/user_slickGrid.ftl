<link rel="stylesheet" href="${base}/js/slick/slick.grid.css" type="text/css" />
<link rel="stylesheet" href="${base}/js/slick/controls/slick.pager.css" type="text/css"/>
<link rel="stylesheet" href="${base}/js/slick/examples/examples.css" type="text/css"/>

<script src="${base}/js/slick/lib/jquery-1.7.min.js"></script>
<script src="${base}/js/slick/lib/jquery-ui-1.8.16.custom.min.js"></script>
<script src="${base}/js/slick/lib/jquery.event.drag-2.2.js"></script>

<script src="${base}/js/slick/slick.core.js"></script>
<script src="${base}/js/slick/slick.formatters.js"></script>
<script src="${base}/js/slick/slick.editors.js"></script>
<script src="${base}/js/slick/slick.grid.js"></script>
<script src="${base}/js/slick/slick.dataview.js"></script>
<script src="${base}/js/slick/plugins/slick.checkboxselectcolumn.js"></script>
<script src="${base}/js/slick/plugins/slick.rowselectionmodel.js"></script>
<script src="${base}/js/slick/controls/slick.pager.js"></script>

<style>
	.slick-cell-checkboxsel {background: #f0f0f0; border-right-color: silver; border-right-style: solid;}
	.slick-headerrow-column {background: #87ceeb; text-overflow: clip; -moz-box-sizing: border-box; box-sizing: border-box;}
	.slick-headerrow-column input {margin: 0; padding: 0; width: 100%; height: 100%; -moz-box-sizing: border-box; box-sizing: border-box;}
</style>

<div style="position:relative">
	<div style="width:650px;">
		<div class="grid-header" style="width:100%;">
			<form id="frmUser" method="post" action="users">
				<select name="grid" style="width: 100px;" onchange="submit();">
					<option value="jqGrid" <#if searchUser.grid! == "jqGrid">selected</#if>>jqGrid</option>
					<option value="slickGrid" <#if searchUser.grid! == "slickGrid">selected</#if>>slickGrid</option>
					<option value="dhtmlxGrid" <#if searchUser.grid! == "dhtmlxGrid">selected</#if>>dhtmlxGrid</option>
				</select>
			</form>
			<label>Users - slickgrid test</label>
			<span style="float:right;display:inline-block;">Search Name:<input type="text" id="txtSearch" value=""></span>
		</div>
		<div id="myGrid" style="width:100%;height:502px;"></div>
		<div id="pager" style="width:100%;height:20px;"></div>
	</div>
</div>

<script>
	var dataView;
	var grid;
	var pager;
	var data = [];
	var columns = [
		{id: "seq", name: "SEQ", field: "seq", width: 50, sortable: true},
		{id: "userId", name: "<@spring.message 'lbl.userId' />", field: "userId", width: 100, editor: Slick.Editors.Text, validator: requiredFieldValidator, sortable: true},
		{id: "pwd", name: "<@spring.message 'lbl.pwd' />", field: "pwd", width: 150, editor: Slick.Editors.Text, validator: requiredFieldValidator},
		{id: "userNm", name: "<@spring.message 'lbl.userNm' />", field: "userNm", width: 120, editor: Slick.Editors.Text, validator: requiredFieldValidator, sortable: true},
		{id: "email", name: "<@spring.message 'lbl.email' />", field: "email", width: 150, minWidth: 100, maxWidth: 200, editor: Slick.Editors.Text},
		{id: "grade", name: "<@spring.message 'lbl.grade' />", field: "grade", width: 50, editor: Slick.Editors.Text, validator: requiredFieldValidator, sortable: true}
	];
	var options = {
		editable: true,
		enableAddRow: true,
		enableCellNavigation: true,
		asyncEditorLoading: true,
		autoEdit: false,
		forceFitColumns: false,
		editCommandHandler: queueAndExecuteCommand
	};
		
	//var column_defs = columns; //column rearrange
	//columns = [];
	//var checkboxSelector = new Slick.CheckboxSelectColumn({cssClass: "slick-cell-checkboxsel"});
	var checkboxSelector = new Slick.CheckboxSelectColumn();
	columns.push(checkboxSelector.getColumnDefinition());
	//for (var i = 0; i < column_defs.length; i++) { columns.push(column_defs[i]); } //checkbox .. first
	
	var sortcol = "seq";
	var sortdir = 1;
	var searchString = "";
	var commandQueue = [];
	
	function queueAndExecuteCommand(item, column, editCommand) {
		commandQueue = []; //add : init .. last data
		commandQueue.push(editCommand);
		editCommand.execute();
	}
	
	function undo() {
		var command = commandQueue.pop();
		if (command && Slick.GlobalEditorLock.cancelCurrentEdit()) {
			alert("update fail.."); //add : $.post .. fail
			command.undo();
			grid.gotoCell(command.row, command.cell, false);
		}
	}
	
	function requiredFieldValidator(value) {
		if (value == null || value == undefined || !value.length) {
			return {valid: false, msg: "This is a required field"};
		} else {
			return {valid: true, msg: null};
		}
	}
	
	function validateRow(grid) {
		var rowIdx = grid.getActiveCell().row;
		
		$.each(grid.getColumns(), function(colIdx, column) {
			var item = grid.getDataItem(rowIdx);
			
			if (column.editor && column.validator) {
				var validationResults = column.validator(item[column.field]);
				//alert(rowIdx + "," + colIdx + " : " + column.field + "=" + item[column.field]);
				
				if (!validationResults.valid) {
					grid.gotoCell(rowIdx, colIdx, true);
					grid.getEditorLock().commitCurrentEdit();
					
					return false;
				}
			}
		});
	}
	
	$("#txtSearch").keyup(function (e) {
		Slick.GlobalEditorLock.cancelCurrentEdit();
		if (e.which == 27) this.value = ""; //clear on Esc
		searchString = this.value;
		updateFilter();
	});
	
	function updateFilter() {
		dataView.setFilterArgs({ searchString: searchString });
		dataView.refresh();
	}
	
	function myFilter(item, args) {
		if (args.searchString != "" && item["userNm"].indexOf(args.searchString) == -1) {
			return false;
		}
		return true;
	}
	
	$.get("users.json", function(json) {
		data = json.users;
		var i = 0
		for (; i < data.length; i++) {
			data[i].id = "id_" + i; //dataView required..
			data[i].seq = i + 1;
		}
		/*/test for add data..
		for (; i < 100000; i++) {
		    var d = (data[i] = {});
		    d["id"] = "id_" + i;
		    d["seq"] =  i + 1;
		    d["userId"] = "Task " + i;
		    d["pwd"] = "1";
		    d["userNm"] = "Name " + i;
		    d["email"] = "email@email.com";
		    d["grade"] = "U";
		}
		*/		
		dataView = new Slick.Data.DataView();
		grid = new Slick.Grid("#myGrid", dataView, columns, options);
		grid.setSelectionModel(new Slick.RowSelectionModel());
		grid.registerPlugin(checkboxSelector);
		pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));
		
		grid.onSort.subscribe(function (e, args) {
			sortdir = args.sortAsc ? 1 : -1;
			sortcol = args.sortCol.field;
			
			var comparer = function(a, b) {
				var x = a[sortcol], y = b[sortcol];
				return (x == y ? 0 : (x > y ? 1 : -1));
			}
			
			dataView.sort(comparer, args.sortAsc);
		});
		
		grid.onBeforeEditCell.subscribe(function (e, args) {
			//alert("before edit: "+args.row+", "+args.cell+", "+typeof(args.item));
			if (typeof(args.item) == 'object' && args.cell == 1) { //edit row : 'userId' edit disable
				return false;
			} else if (typeof(args.item) == 'undefined' && args.cell != 1) { //add row : only 'userId' edit enable
				return false;
			}
		});
		
		grid.onCellChange.subscribe(function (e, args) {
			if (typeof(args.item.id) == 'undefined') {
				alert("error..");
			} else {
				args.item.cmd = "update";
				$.post("ajaxSubmit", args.item).done(function(data){ 
					if (data > 0) {
						dataView.updateItem(args.item.id, args.item);
						//validateRow(args.grid);
					} else {
						undo(); //update fail.. & reset..
					}
				}).fail(function(data){ $("body").empty().append(data.responseText); });
			}
		});
		
		//grid.onSelectedRowsChanged.subscribe(function (e, args) {
		//	validateRow(args.grid);
		//	alert("row change: "+args.grid.getActiveCell().row+", "+args.grid.getActiveCell().cell);
		//});
		
		grid.onAddNewRow.subscribe(function (e, args) {
			//alert(args.item.userId);
			var item = {"id": "id_"+data.length+1, "seq": data.length+1, "pwd": "1", "userNm": "", "email": "", "grade": "U"};
			$.extend(item, args.item);
			
			item.cmd = "insert";
			$.post("ajaxSubmit", item).done(function(data){ // + 중복체크 (return: -1) 미구현..
				if (data > 0) {
					dataView.addItem(item);
					validateRow(args.grid); //validation 방법이... 건너뜀 ㅡㅡㅋ
					//editor이용 --> http://mleibman.github.io/SlickGrid/examples/example-composite-editor-item-details.html
				} else {
					alert("insert fail..");
				}
			}).fail(function(data){ $("body").empty().append(data.responseText); });
		});
		
		dataView.onRowCountChanged.subscribe(function (e, args) {
			grid.updateRowCount();
			grid.render();
		});
		
		dataView.onRowsChanged.subscribe(function (e, args) {
			grid.invalidateRows(args.rows);
			grid.render();
		});
		
		dataView.onPagingInfoChanged.subscribe(function (e, pagingInfo) {
			var isLastPage = pagingInfo.pageNum == pagingInfo.totalPages - 1;
			var enableAddRow = isLastPage || pagingInfo.pageSize == 0;
			var options = grid.getOptions();
			
			if (options.enableAddRow != enableAddRow) {
				grid.setOptions({enableAddRow: enableAddRow});
			}
		});
		
		dataView.beginUpdate();
		dataView.setItems(data);
		dataView.setFilterArgs({ searchString: searchString });
		dataView.setFilter(myFilter);
		dataView.endUpdate();
		
		dataView.syncGridSelection(grid, true);
	});
	
	$(document).on("keydown", function(e) {
		//alert("key: "+event.which);
		if (event.which == 46) { //delete on Delete
			var arrUser = [], arrGrid = [];
			var rows = grid.getSelectedRows();
			
			if ((rows.length > 0) && confirm("delete selected rows??")) {
				for (var i = 0; i < rows.length; i++) {
					var item = dataView.getItem(rows[i]);
					arrUser[i] = item.userId;
					arrGrid[i] = item.id;
				}
				
				$.post("ajaxDeletes", {args: arrUser}).done(function(data){ 
					if (data > 0) {
						dataView.beginUpdate();
						for (var i = 0; i < arrGrid.length; i++) dataView.deleteItem(arrGrid[i]);
						dataView.endUpdate();
					} else {
						alert("delete fail..");
					}
				}).fail(function(data){ $("body").empty().append(data.responseText); });
			}
		}
	});
</script>
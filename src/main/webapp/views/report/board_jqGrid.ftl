
<link rel="stylesheet" href="${base}/jqGrid/css/ui.jqgrid.css" type="text/css" />

<!--script src="${base}/jqGrid/js/i18n/grid.locale-en.js" type="text/javascript"></script-->
<script src="${base}/jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="${base}/js/lib/Chart.min.js"></script>

<style>
	.totals {font-weight: bold; color: gray; font-style: italic;}
	.chartbox {-webkit-border-radius: 5px; -moz-border-radius: 5px; -ms-border-radius: 5px; -o-border-radius: 5px; 
			border-radius: 5px; border: 1px solid skyblue;}
</style>

<div style="position:relative">
	<div class="grid-header" style="width:100%;">
		<form id="frmStat" method="post" action="board">
			<select name="grid" style="width: 100px;" onchange="submit();">
				<option value="jqGrid" <#if reportVO.grid! == "jqGrid">selected</#if>>jqGrid</option>
				<option value="slickGrid" <#if reportVO.grid! == "slickGrid">selected</#if>>slickGrid</option>
				<option value="dhtmlxGrid" <#if reportVO.grid! == "dhtmlxGrid">selected</#if>>dhtmlxGrid</option>
			</select>
			&nbsp;Select Years : 
			<select name="searchFilter" style="width: 100px;" onchange="loadGridChart();">
				<option value="2013" <#if reportVO.searchFilter! == "2013">selected</#if>>2013년</option>
				<option value="2014" <#if reportVO.searchFilter! == "2014">selected</#if>>2014년</option>
			</select>
			<input type="hidden" id="mode" name="mode" value="screen" />
			<input type="hidden" id="format" name="format" />
		</form>
	</div>
	<table id="list"></table> 
</div>

<div style="margin-top: 10px;"></div>

<div style="width:650px;" class="chartbox">
	<div style="margin: 10px;">
		<canvas id="canvas" height="300" width="630"></canvas>
	</div>
</div>

<div style="margin-top: 10px;">
	<a href="#" onclick="doReports('pdf');">[Report to PDF]</a> <a href="#" onclick="doReports('xls');">[Report to Excel]</a>
</div>

<script>
	var columns = [];
	columns.push({name: "BD_GRP", index: "BD_GRP", width: 60, align: "center"});
	for (var i = 1; i < 13; i++) {
		var col = "M"+(i<10?"0":"")+i;
		columns.push({name: col, index: col, width: 40, align: "right", formmater: "number", sorttype: "number"});
	}
	columns.push({name: "TOT", index: "TOT", width: 40, align: "right", formmater: "number", sorttype: "number"});

	function jqGridRender(data) {
		$("#list").jqGrid({
			datatype: "jsonstring",
			datastr: data,
			height: "auto",
			//rowNum: 10,
			colNames: ["Board", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월", "Sum"],
			colModel: columns,
			//viewrecords: true,
			gridview: true,
			//autoencode: true,
			<#if .locale! == "ar">direction : "rtl",</#if>
			//caption: "My first grid"
			footerrow: true,
			userDataOnFooter: true,
			gridComplete: function () {
				calculateTotal();
			}
		});
		
		$("#list").jqGrid('setGridParam', { datatype: 'jsonstring', datastr: data}).trigger("reloadGrid");
	}
	
	function calculateTotal() {
		var grid = $("#list");
		var sumMonth = [];
		for (var i = 1; i < 13; i++) {
			var col = "M"+(i<10?"0":"")+i;
			sumMonth[i] = grid.jqGrid('getCol', col, false, 'sum');
		}
		var sumTotal = grid.jqGrid('getCol', 'TOT', false, 'sum');
		
		grid.jqGrid("footerData", "set", {BD_GRP: 'total', M01: sumMonth[1], M02: sumMonth[2], M03: sumMonth[3]
				, M04: sumMonth[4], M05: sumMonth[5], M06: sumMonth[6], M07: sumMonth[7], M08: sumMonth[8]
				, M09: sumMonth[9], M10: sumMonth[10], M11: sumMonth[11], M12: sumMonth[12], TOT: sumTotal});
	}
	
	var chartCtx = document.getElementById("canvas").getContext("2d");
	var chartOptions = {
		scaleOverride: true,
		scaleSteps: 15,
		scaleStepWidth: 2
	};

	function ChartData(data) {
		var colors = new Array();
		colors[0] = "205,151,187";
		colors[1] = "151,187,205";
		colors[2] = "187,205,151";
		
		var label = new Array();
		for(var i = 0; i < 12; i++) label[i] = (i+1)+"월";
		
		var dataset = new Array();
		for(var i = 0; i < data.length; i++) {
			var cnts = new Array();
			for(var j = 0; j < 12; j++) {
				var id = "M"+(j<10?"0":"")+(j+1);
				cnts[j] = data[i][id];
			}
			
			var csets = new Object();
			csets["fillColor"] = "rgba("+colors[i]+",0.5)";
			csets["strokeColor"] = "rgba("+colors[i]+",1)";
			csets["data"] = cnts;
			dataset[i] = csets;
		}
		
		var jsonObject = {labels:label, datasets:dataset};
		
		return jsonObject;
	}
	
	loadGridChart();
	
	function loadGridChart() {
		//$.get("board.json", function(json) {
		$.post("board.json", $('#frmStat').serialize(), function(json) { 
			var data = json.statList;
			jqGridRender(data);
			
			var chartData = new ChartData(data);
			var myLine = new Chart(chartCtx).Bar(chartData, chartOptions);
		})
	}
	
	function doReports(format) {
		$("#mode").val("report");
		$("#format").val(format);
		$('#frmStat').submit();
	}
</script>
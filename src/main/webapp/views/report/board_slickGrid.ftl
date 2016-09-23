<link rel="stylesheet" href="${base}/js/slick/slick.grid.css" type="text/css" />
<link rel="stylesheet" href="${base}/js/slick/examples/examples.css" type="text/css"/>

<script src="${base}/js/slick/lib/jquery.event.drag-2.2.js"></script>

<script src="${base}/js/slick/slick.core.js"></script>
<script src="${base}/js/slick/slick.grid.js"></script>

<script src="${base}/js/lib/Chart.min.js"></script>

<style>
	.totals {font-weight: bold; color: gray; font-style: italic;}
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
	<div style="width:650px;">
		<div id="myGrid" style="width:100%;height:200px;"></div>
	</div>
</div>

<div style="width:650px; border: 1px solid gray;">
	<canvas id="canvas" height="300" width="650" style="margin: 5px;"></canvas>
</div>

<div style="margin-top: 10px;">
	<a href="#" onclick="doReports('pdf');">[Report to PDF]</a> <a href="#" onclick="doReports('xls');">[Report to Excel]</a>
</div>

<script type="text/javascript" src="${base}/js/test.js"></script>
<script>
	var grid;
	var data = [];
	var options = {
		enableCellNavigation: true,
		headerRowHeight: 30
	};
	var columns = [];
	columns.push({id: "item", name: "Board", field: "BD_GRP", width: 86});
	for (var i = 1; i < 13; i++) columns.push({id: i, name: i+"월", field: "M"+(i<10?"0":"")+i, width: 42});
	columns.push({id: "sum", name: "SUM", field: "TOT", width: 60, cssClass: "totals"});
	
	function TotalsDataProvider(data, columns) {
		var totals = {};
		var totalsMetadata = {
			cssClasses: "totals",
			columns: {}
		};
		
		this.getLength = function() {
			return data.length + 1;
		};
		
		this.getItem = function(index) {
			return (index < data.length) ? data[index] : totals;
		};
		
		this.getItemMetadata = function(index) {
			return (index != data.length) ? null : totalsMetadata;
		};
		
		this.updateTotals = function() {
			var columnIdx = columns.length;
			while (columnIdx--) {
				var columnId = columns[columnIdx].field;
				if (columnId == "BD_GRP") {
					var total = "SUM";
				} else {
					var total = 0;
					var i = data.length;
					while (i--) {
						total += (parseInt(data[i][columnId], 10) || 0);
					}
				}
				totals[columnId] = total;
			}
		};
		
		this.updateTotals();
	}
	
	var chartCtx = document.getElementById("canvas").getContext("2d");
	var chartOptions = {
		scaleOverride: true,
		scaleSteps: 15,
		scaleStepWidth: 2
	};

	function ChartData(data, columns) {
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
			data = json.statList;
			
			var dataProvider = new TotalsDataProvider(data, columns);
			grid = new Slick.Grid("#myGrid", dataProvider, columns, options);
			
			var chartData = new ChartData(data, columns);
			//alert(JSONtoString(chartData));
			var myLine = new Chart(chartCtx).Bar(chartData, chartOptions);
		})
	}
	
	function doReports(format) {
		$("#mode").val("report");
		$("#format").val(format);
		$('#frmStat').submit();
	}
</script>
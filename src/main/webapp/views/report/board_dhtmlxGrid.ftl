
<link rel="stylesheet" href="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.css" type="text/css" />
<link rel="stylesheet" href="${base}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_skyblue.css" type="text/css" />

<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>

<script src="${base}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js" type="text/javascript"></script>
<script src="${base}/dhtmlxSuite/dhtmlxChart/codebase/dhtmlxchart.js" type="text/javascript"></script>
<link rel="stylesheet" href="${base}/dhtmlxSuite/dhtmlxChart/codebase/dhtmlxchart.css" type="text/css" />

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
			<select id="searchFilter" name="searchFilter" style="width: 100px;" onchange="loadGridAndChart()">
				<option value="2013" <#if reportVO.searchFilter! == "2013">selected</#if>>2013년</option>
				<option value="2014" <#if reportVO.searchFilter! == "2014">selected</#if>>2014년</option>
			</select>
			<input type="hidden" id="mode" name="mode" value="screen" />
			<input type="hidden" id="format" name="format" />
		</form>
	</div>
	<div id="mygrid" style="width:650px;height:150px;"></div>
</div>

<div style="margin-top: 10px;"></div>

<div style="width:650px;" class="chartbox">
	<div id="mychart" style="width:650px;height:300px;"></div>
</div>

<div style="margin-top: 10px;">
	<a href="#" onclick="doReports('pdf');">[Report to PDF]</a> <a href="#" onclick="doReports('xls');">[Report to Excel]</a>
</div>

<script type="text/javascript" src="${base}/js/test.js"></script>
<script>
	var mygrid;
	var mychart;
	
	function initGrid() {
		mygrid = new dhtmlXGridObject("mygrid");
		mygrid.setImagePath("${base}/dhtmlxSuite/dhtmlxGrid/codebase/imgs/");
		mygrid.setColumnIds("BD_GRP,M01,M02,M03,M04,M05,M06,M07,M08,M09,M10,M11,M12,TOT"); //without space...
		mygrid.setHeader("Board,1월,2월,3월,4월,5월,6월,7월,8월,9월,10월,11월,12월,Sum");
		mygrid.setInitWidths("*,40,40,40,40,40,40,40,40,40,40,40,40,40");
		mygrid.setColAlign("center,,,,,,,,,,,,,");
		mygrid.setColSorting("str,int,int,int,int,int,int,int,int,int,int,int,int,int");
		mygrid.init();
		mygrid.setSkin("dhx_skyblue");
		mygrid.setEditable(false);
	}
	
	function initChart() {
		mychart = new dhtmlXChart({
			view: "bar",
			color: "#58dccd",
			gradient: "3d",
			container: "mychart",
			value: "#data0#",
			xAxis:{
				template:"#month#"
			},
			yAxis:{
				start:0,
			    step:10,
			    end:50
			},
			legend:{
				values:[{text:"faq",color:"#58dccd"},{text:"inqury",color:"#a7ee70"},{text:"notice",color:"#36abee"}],
				valign:"bottom",
				align:"center",
				width:100,
				layout:"x"
			}
		});
		
		mychart.addSeries({
			value:"#data1#",
			color:"#a7ee70"
		});
		
		mychart.addSeries({
			value:"#data2#",
			color:"#36abee"
		});
	}
	
	function ChartData(data) {
		var dataset = new Array();
		for(var i = 0; i < 12; i++) {
			var id = "M"+(i<10?"0":"")+(i+1);
			
			var csets = new Object();
			csets["month"] = (i+1)+"월";
			for(var j = 0; j < data.length; j++) {
				var cset = data[j][id];// > 0 ? data[j][id] : "0";
				
				if(j==0) csets["data0"] = cset;
				else if(j==1) csets["data1"] = cset;
				else if(j==2) csets["data2"] = cset;
			}
			dataset[i] = csets;
		}
		
		return dataset;
	}
	
	function loadGridAndChart() {
		$.post("board.json", $('#frmStat').serialize(), function(json) { 
			var data = json.statList;
			mygrid.clearAll();
			//mygrid.attachFooter("total,1,2,3,4,5,6,7,8,9,10,11,12,sum"); //--> available only in PRO version
			mygrid.parse(data,"js");
			
			mychart.clearAll();
			//mychart.parse(mygrid,"dhtmlxgrid");
			var chartData = new ChartData(data);
			alert(JSONtoString(chartData));
			mychart.parse(chartData,"json");
		})
	}
	
	initGrid();
	initChart();
	loadGridAndChart();
	
	function doReports(format) {
		$("#mode").val("report");
		$("#format").val(format);
		$('#frmStat').submit();
	}
</script>
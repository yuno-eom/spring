<#setting datetime_format = "yyyy.MM.dd hh:mm:ss" />

<table summary="" class="table_01" style="width:650px">
	<caption></caption>
	<colgroup>
		<col width="20%" />
		<col width="*" />
		<col width="20%" />
		<col width="*" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col">제목</th>
			<td scope="col" colspan="3" class="left">${resultView.title}</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">작성자</th>
			<td class="left">${resultView.regNm}</td>
			<th scope="row">작성일</th>
			<td class="left">${resultView.regYmdt?datetime("yyyy-MM-dd hh:mm:ss")}</td>
		</tr>
		<tr>
			<th scope="row" style="height:60px;">내용</th>
			<td colspan="3" class="left">${resultView.content?replace("\n", "<br>")}</td>
		</tr>
		<tr>
			<th scope="row">파일첨부</th>
			<td colspan="3" class="left">
				<#if resultView.bdFiles?has_content>
					<#list resultView.bdFiles as bdFile>
						<a href="${base}/download/${bdFile.fileSeq!}">${bdFile.realNm!}</a><#if bdFile_has_next>, </#if>
					</#list>
				<#else>
					첨부파일 없음
				</#if>
			</td>
		</tr>
	</tbody>
</table>

<div style="margin-top:10px;">
	<button style="font-size:9px; height:22px;" type="button" onclick="goList();">List</button>
	<div style="float:right;">
		<#if bdGrp == "inquiry" && resultView.prSeq == 0>
			<button style="font-size:9px; height:22px;" type="button" onclick="goReply();">Reply</button>
		</#if>
		<#if Session?exists>
			<#if (Session.grade == "A") || (Session.userId == resultView.userId)>
				<button style="font-size:9px; height:22px;" type="button" onclick="goModify();">Modify</button>
				<button style="font-size:9px; height:22px;" type="button" onclick="ajaxDelete();">Delete</button>
			</#if>
		</#if>
	</div>
</div>

<form id='frmBoard' method='post' action='list'>
	<!-- searchBoard -->
	<input type='hidden' name='searchFilter' value='${searchBoard.searchFilter!}' />
	<input type='hidden' name='searchText' value='${searchBoard.searchText!}' />
	<#if searchBoard.page?exists>
		<input type='hidden' name='page.pageCount' value='${searchBoard.page.pageCount}' />
		<input type='hidden' name='page.rowCount' value='${searchBoard.page.rowCount}' />
		<input type='hidden' name='page.rowMax' value='${searchBoard.page.rowMax}' />
		<input type='hidden' name='page.nowPage' value='${searchBoard.page.nowPage}' />
	</#if>
	<!-- commonBoard -->
	<input type='hidden' name='bdSeq' id='bdSeq' value='${resultView.bdSeq}' />
	<input type='hidden' name='cmd' id='cmd' />
	<input type='hidden' name='title' value='${resultView.title}' /><#-- reply -->
	<!-- restful -->
	<input type='hidden' name='_method' id='_method' />
</form>

<script type='text/javascript'>
	$(window).load(function() {
		<#if ERROR?exists>alert('${ERROR!}');</#if>
	});

	function goList(){
		$('#frmBoard').attr('action','../${bdGrp}');
		$('#frmBoard').submit();
	}

	function goReply(){
		$('#cmd').val('reply');
		$('#frmBoard').attr('action','input');
		$('#frmBoard').submit();
	}

	function goModify(){
		//if(confirm("수정하시겠습니까?")){
			$('#cmd').val('modify');
			$('#frmBoard').attr('action','input');
			$('#frmBoard').submit();
		//}
	}

	function doDelete(){
		if(confirm("삭제하시겠습니까?")){
			//$('#_method').val('delete');
			$('#cmd').val('delete');
			$('#frmBoard').attr('action','submit');
			$('#frmBoard').submit();
		}
	}

	function ajaxDelete(){
		if(confirm("삭제하시겠습니까?")){
			$('#cmd').val('delete');
			$.post('ajaxSubmit', $('#frmBoard').serialize())
			.done (function(data){ if(data > 0) goList(); else alert('DB Delete Failed..'); })
			.fail (function(data){ alert(data.responseText) });
		}
	}
</script>
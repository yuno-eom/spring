<#assign pageTag = JspTaglibs["/WEB-INF/tld/paging.tld"] />
<#assign cutTag = JspTaglibs["/WEB-INF/tld/cutString.tld"] />
<#setting date_format = "yyyy.MM.dd" />

<#assign searchFilter = searchBoard.searchFilter! />
<#assign searchText = searchBoard.searchText! />
<#assign pageCount = searchBoard.page.pageCount! />
<#assign rowCount = searchBoard.page.rowCount! />
<#assign rowMax = searchBoard.page.rowMax! />
<#assign nowPage = searchBoard.page.nowPage! />

<form id='frmSearch' method="post" action="${bdGrp}">
	<div style="float:left; margin-bottom:10px;">
		<select name="searchFilter" style="width: 100px;">
			<option value="title" <#if searchFilter! == "title">selected</#if>>제 목</option>
			<option value="content" <#if searchFilter! == "content">selected</#if>>내 용</option>
			<option value="writer" <#if searchFilter! == "writer">selected</#if>>작성자</option>
		</select>
		<input type="text" name="searchText" value="${searchText!}" style="width: 120px;" />
		<input type="submit" value="Search" style="font-size:9px;" />					
	</div>
</form>

<#if (Session?exists && Session.grade == "A" ) || bdGrp == "inquiry">
	<div style="float:right;">
		<button style="font-size:9px; height:22px;" type="button" onclick="goInput()">Write</button>
	</div>
</#if>

<table summary="" class="table_01" style="width:650px">
	<caption></caption>
	<colgroup>
		<col width="8%" />
		<col width="auto" />
		<col width="12%" />
		<col width="16%" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col">순서</th>
			<th scope="col">제목</th>
			<th scope="col">작성자</th>
			<th scope="col">등록일</th>
		</tr>
	</thead>
	<tbody>
		<#if resultList?has_content>
			<#assign nNum = rowMax - searchBoard.page.scopeRow!0 />
			<#list resultList as rMap>
				<tr>
					<td>${nNum}</td>
					<td class="left"><#if rMap.prSeq != 0>&nbsp; --> </#if><a href="#" onclick="goView(${rMap.bdSeq})"><@cutTag.CutString cutString="${rMap.title}" cutLength="54" strongString="${searchText!}" suffix="..." /></a></td>
					<td>${rMap.regNm}</td>
					<td>${rMap.regYmdt?date("yyyy-MM-dd")}</td>
				</tr>
				<#assign nNum = nNum - 1>
			</#list>
		<#else>
			<tr>
				<td align="center" colspan="4">게시글이 없습니다.</td>
			</tr>
		</#if>
	</tbody>
</table>

<@pageTag.Paging url="${bdGrp}" pageCount="${pageCount}" rowCount="${rowCount}" rowMax="${rowMax}" nowPage="${nowPage}" 
	params="searchFilter=${searchFilter!}&searchText=${searchText!}" />

<form id='frmBoard' method='post' action='${bdGrp}/input'>
	<!-- searchBoard -->
	<input type='hidden' name='searchFilter' value='${searchFilter!}'>
	<input type='hidden' name='searchText' value='${searchText!}'>
	<input type='hidden' name='page.pageCount' value='${pageCount}'>
	<input type='hidden' name='page.rowCount' value='${rowCount}'>
	<input type='hidden' name='page.rowMax' value='${rowMax}'>
	<input type='hidden' name='page.nowPage' value='${nowPage}'>
	<!-- request parameter -->
	<input type='hidden' name='bdSeq' id='bdSeq' value='0'>
</form>

<script type="text/javascript">
	function goInput(){
		//$('#bdSeq').val(0);
		$('#frmBoard').submit();
	}
	
	function goView(bdSeq){
		$('#bdSeq').val(bdSeq);
		$('#frmBoard').attr('action', '${bdGrp}/'+bdSeq);		
		$('#frmBoard').submit();
	}
</script>
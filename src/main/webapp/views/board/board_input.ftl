
<form id='frmBoard' name='frmBoard' method='post' action='submit' enctype='multipart/form-data'>
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
	<input type='hidden' name='bdSeq' id='bdSeq' value='${resultView.bdSeq!0}' />
	<input type='hidden' name='prSeq' value='${resultView.prSeq!0}' />
	<input type='hidden' name='userId' value='${Session.userId}' />
	<input type='hidden' name='cmd' id='cmd' value='insert' />
	<!-- restful -->
	<input type='hidden' name='_method' id='_method' />
	
	<table summary="" class="table_01" style="width:650px">
		<caption></caption>
		<colgroup>
			<col width="20%" />
			<col width="*" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">제목</th>
				<td class="left">
					<input type="text" name="title" id="title" <#if resultView.title?exists>value="${resultView.title}"</#if> size="60" maxlength="200" />				
				</td>
			</tr>	
			<tr>
				<th scope="row">작성자</th>
				<td class="left">
					<input type="text" name="regNm" id="regNm" <#if resultView.regNm?exists>value="${resultView.regNm}"<#elseif Session?exists> value="${Session.userNm!}"</#if> />
				</td>
			</tr>
			<tr>
				<th scope="row">내용</th>
				<td class="left">
					<textarea name="content" id="content" rows="20" cols="59"><#if resultView.content?exists>${resultView.content}</#if></textarea>
				</td>
			</tr>
			<tr>
				<th scope="row">파일첨부</th>
				<td class="left">
					<input type="file" name="uploads[0]" id="uploads_0" /><#-- uploads[배열] : @InitBinder -->
					<input type="file" name="uploads[1]" id="uploads_1" />
					<#if resultView.bdFiles?has_content>
						<div>
							현재 저장된 파일 : 
							<#list resultView.bdFiles as bdFile>
								${bdFile.realNm!}<#if bdFile_has_next>, </#if>
							</#list>
						</div>
					</#if>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div style="margin-top:10px;">
		<button style="font-size:9px; height:22px;" type="button" onclick="goList();">List</button>
		<div style="float:right;">
			<button style="font-size:9px; height:22px;" type="button" onclick="ajaxCommit();">Commit</button>
		</div>
	</div>
	
</form>

<script type='text/javascript'>
	function goList(){
		$('#frmBoard').attr('action','../${bdGrp}');
		$('#frmBoard').removeAttr('enctype');
		$('#frmBoard').submit();
	}
	
	function goView(){
		$('#frmBoard').attr('action','${resultView.bdSeq!}');
		$('#frmBoard').removeAttr('enctype');
		$('#frmBoard').submit();
	}
	
	function doCommit(){
		if(validateBoard()){
			if(confirm("저장 하시겠습니까?")) {
				if($('#bdSeq').val() > 0){ //update
					/* restful "method = 'put'" : multipart/form-data 지원안함.. -> post로 처리됨(cmd 구분)
					$('#frmBoard').removeAttr('enctype');
					$('#_method').val('put');
					*/
					$('#cmd').val('update');
				}
				
				$('#frmBoard').submit();
			}
		}
	}
	
	function ajaxCommit(){
		if(validateBoard()){
			if(confirm("저장 하시겠습니까?")) {
				if($('#bdSeq').val() > 0) $('#cmd').val('update');
				
				var formData = new FormData(document.forms.namedItem("frmBoard")); //multipart..
				$.ajax({
					url : 'ajaxSubmit',
					type: 'post',
					data: formData,
					contentType: false,
					processData: false,
					success: function(data) {
						if(data > 0){
							if($('#cmd').val() == "update") goView();
							else goList();
						}else{
							alert('DB Insert/Update Failed..');
						}
					},
					error: function(data) {
						alert(data.responseText);
					}
				});
			}
		}
	}
	
	function ajaxCommit2(){
		if(validateBoard()){
			if(confirm("저장 하시겠습니까?")) {
				$('#frmBoard').ajaxSubmit({
					beforeSerialize: function() {
						if($('#bdSeq').val() > 0) $('#cmd').val('update');
					},
					/*
					beforeSend: function() {
						status.empty();
						var percentVal = '0%';
						bar.width(percentVal)
						percent.html(percentVal);
					},
					uploadProgress: function(event, position, total, percentComplete) {
						var percentVal = percentComplete + '%';
						bar.width(percentVal)
						percent.html(percentVal);
					},
					success: function() {
						var percentVal = '100%';
						bar.width(percentVal)
						percent.html(percentVal);
					}
					*/
					success: function(responseText, statusText, xhr, $form){
						alert('status: ' + statusText + '\n\nresponseText: \n' + responseText + 
								'\n\nThe output div should have already been updated with the responseText.'); 
					}
				});
			}
		}
	}
	
	function validateBoard(){
		if($('#title').val() == ""){
			alert("제목을 입력해 주세요.");
			$('#title').focus();
			return false;
		}else if($('#content').val() == ""){
			alert("내용을 입력해 주세요.");
			$('#content').focus();
			return false;
		}else{
			return true;
		}
	}
</script> 
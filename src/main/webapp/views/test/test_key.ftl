
<form id="test" name="test" method="post" action="key">
	<input type="hidden" name="cmd" value="insert" />
	TEXT1 : <input type="text" name="text1"  size="60" maxlength="200" value="" />
	<button style="font-size:9px; height:22px;" type="button" onclick="submit();">Submit</button>
</form>

<table summary="" class="table_01" style="width:650px">
	<caption></caption>
	<colgroup>
		<col width="10%" />
		<col width="10%" />
		<col width="auto" />
		<col width="20%" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col">KEY1</th>
			<th scope="col">KEY2</th>
			<th scope="col">TEXT1</th>
			<th scope="col">DATE</th>
		</tr>
	</thead>
	<tbody>
		<#if testList?has_content>
			<#list testList as tMap>
				<tr>
					<td>${tMap.KEY1}</td>
					<td>${tMap.KEY2}</td>
					<td>${tMap.TEXT1!}</td>
					<td>${tMap.DATE1?date("yyyy-MM-dd")}</td>
				</tr>
			</#list>
		</#if>
	</tbody>
</table>
<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title"  value="login"/>
<tiles:put name="content" type="string">
<html:errors/>
<s:form>
<table>
<tr>
<td>ユーザ名:</td>
<td><html:text property="userId"/></td>
</tr>
<tr>
<td>パスワード:</td>
<td><html:password property="password"/></td>
</tr>
</table>
<input type="submit" name="submit" value="ログイン"/>
</s:form>
</tiles:put>
</tiles:insert>
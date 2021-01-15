<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="value" required="true" rtexprvalue="true" type="domain.Person"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

${value.surname}&nbsp;${fn:substring(value.name, 0, 1)}.&nbsp;${fn:substring(value.patronymic, 0, 1)}.
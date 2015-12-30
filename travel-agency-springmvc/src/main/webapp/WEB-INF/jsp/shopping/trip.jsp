<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Trip info">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/shopping/reservate" modelAttribute="createReservation">
        <!--<input type="hidden" name="tripId" value="${trip.id}"/>-->
        <form:hidden path="tripId"/>
    <div class="row">
        <div class="col-xs-6">
            <h3><c:out value="${trip.name}"/> (<c:out value="${trip.destination}"/>)</h3>
            <h4>
                <fmt:formatDate value="${trip.dateFrom}" type="date" dateStyle="medium"/>
                -
                <fmt:formatDate value="${trip.dateTo}" type="date" dateStyle="medium"/>
            </h4>
            <p>
                <c:out value="${trip.description}"/>
            </p>
            Price: <span style="color: red; font-weight: bold;"><c:out value="${trip.price}"/>&nbsp;EUR</span>
        </div>
        <div class="col-xs-6">
            <table class="table">
                <caption>Excursions</caption>
                <tbody>
                <c:forEach items="${trip.excursions}" var="ex">
                    <tr>
                        <td><form:checkbox path="excursionsId" value="${ex.id}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/shopping/excursion/view/${ex.id}"><c:out value="${ex.name}"/></a>
                        </td>
                        <td>
                            <span style="color: red; font-weight: bold;"><c:out value="${ex.price}"/>&nbsp;EUR</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <button class="btn btn-primary" type="submit">Create reservation</button>
    </div>
    </form:form>
</jsp:attribute>
</my:pagetemplate>

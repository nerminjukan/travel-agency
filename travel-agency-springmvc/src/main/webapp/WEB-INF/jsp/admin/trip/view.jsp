<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="Trip detail">
<jsp:attribute name="body">

    <div class="row">
        <div class="col-xs-6">
            <table>
                <tr>
                    <td><h3>Name:</h3></td>
                    <td><h3><c:out value="${trip.name}"/></h3></td>
                </tr>
                <tr>
                    <td><h4>Description:</h4></td>
                    <td><h4><c:out value="${trip.description}"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Date from:</h4></td>
                    <td><h4><fmt:formatDate value="${trip.dateFrom}" type="date" dateStyle="medium"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Date to:</h4></td>
                    <td><h4><fmt:formatDate value="${trip.dateTo}" type="date" dateStyle="medium"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Destination:</h4></td>
                    <td><h4><c:out value="${trip.destination}"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Price:</h4></td>
                    <td><h4><c:out value="${trip.price}"/> EUR</h4></td>
                </tr>
                <tr>
                    <td><h4>Available:</h4></td>
                    <td><h4><c:out value="${trip.availableTrips}"/></h4></td>
                </tr>
            </table>
        </div>
        <div class="col-xs-6">
            <table class="table">
                <caption>Excursions</caption>
                <tbody>
                    <c:forEach items="${trip.excursions}" var="excursion" varStatus="i">
                    <tr>
                        <td><c:out value="${i.index + 1}."/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/excursion/view/${excursion.id}">
                                <c:out value="${excursion.name}"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>
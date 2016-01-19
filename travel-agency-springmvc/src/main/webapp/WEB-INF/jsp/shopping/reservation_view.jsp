<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Reservation info">
<jsp:attribute name="body">

    <div class="row">
        <div class="col-xs-6">
            <h3><a href="${pageContext.request.contextPath}/shopping/trip/${reservation.trip.id}"><c:out value="${reservation.trip.name}"/></a> (<c:out value="${reservation.trip.destination}"/>)</h3>
            <h4>
                <fmt:formatDate value="${reservation.trip.dateFrom}" type="date" dateStyle="medium"/>
                -
                <fmt:formatDate value="${reservation.trip.dateTo}" type="date" dateStyle="medium"/>
            </h4>
            <p>
                <c:out value="${reservation.trip.description}"/>
            </p>
            Price: <span style="color: red; font-weight: bold;"><c:out value="${reservation.trip.price}"/>&nbsp;EUR</span>
        </div>
        <div class="col-xs-6">
            <table class="table">
                <caption>Excursions</caption>
                <tbody>
                <c:forEach items="${reservation.excursions}" var="ex">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/shopping/excursion/${ex.id}">
                                <c:out value="${ex.name}"/>
                            </a>
                        </td>
                        <td>
                            <span style="color: red; font-weight: bold;"><c:out value="${ex.price}"/>&nbsp;EUR</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        Total price: <span style="color: red; font-weight: bold;"><c:out value="${reservation.totalPrice}"/>&nbsp;EUR</span>
    </div>
</jsp:attribute>
</my:pagetemplate>

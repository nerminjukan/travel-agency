<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="Excursion detail">
<jsp:attribute name="body">
    
    <div class="row">
        <div class="col-xs-6">
            <table>
                <tr>
                    <td><h3>Name:</h3></td>
                    <td><h3><c:out value="${excursion.name}"/></h3></td>
                </tr>
                <tr>
                    <td><h4>Description:</h4></td>
                    <td><h4><c:out value="${excursion.description}"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Date from:</h4></td>
                    <td><h4><c:out value="${excursion.dateFrom}"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Date to:</h4></td>
                    <td><h4><c:out value="${excursion.dateTo}"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Destination:</h4></td>
                    <td><h4><c:out value="${excursion.destination}"/></h4></td>
                </tr>
                <tr>
                    <td><h4>Price:</h4></td>
                    <td><h4><c:out value="${excursion.price}"/> EUR</h4></td>
                </tr>
            </table>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>
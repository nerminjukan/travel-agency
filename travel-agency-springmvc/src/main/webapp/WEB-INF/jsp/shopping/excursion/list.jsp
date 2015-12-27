<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="Excursions">
<jsp:attribute name="body">

    <a href="${pageContext.request.contextPath}/shopping/excursion/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New excursion
    </a>
    
    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>destination</th>
            <th>price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${excursions}" var="excursion">
            <tr>
                <td>${excursion.id}</td>
                <td><c:out value="${excursion.name}"/></td>
                <td><c:out value="${excursion.destination}"/></td>
                <td><c:out value="${excursion.price}"/> EUR</td>
                <td>
                    <a href="/pa165/shopping/excursion/view/${excursion.id}" class="btn btn-primary">Detail</a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/shopping/excursion/delete/${excursion.id}">
                        <button type="submit" class="btn btn-primary">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Shopping trips">
<jsp:attribute name="body">

    <div class="row">
        <c:forEach items="${trips}" var="trip">
            <div class="col-xs-12 col-sm-4 col-md-3 col-lg-2"><!-- bootstrap responsive grid -->
                <a href="${pageContext.request.contextPath}/shopping/trip/${trip.id}">
                <div class="thumbnail">
                <div class="caption">
                    <h3><c:out value="${trip.name}"/></h3>
                    <span style="color: red; font-weight: bold;"><c:out value="${trip.price}"/> EUR</span>
                </div>
                </div>
                </a>
            </div>
        </c:forEach>
    </div>
</jsp:attribute>
</my:pagetemplate>

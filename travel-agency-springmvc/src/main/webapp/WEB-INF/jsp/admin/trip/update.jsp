<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Update trip">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/admin/trip/updating"
               modelAttribute="tripUpdate" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="id" cssClass="col-sm-2 control-label">Id</form:label>
            <div class="col-sm-10">
                <form:input path="id" readonly="true" cssClass="form-control"/>
            </div>
        </div>
        <div class="form-group ${destination_error?'has-error':''}">
            <form:label path="destination" cssClass="col-sm-2 control-label">Destination</form:label>
            <div class="col-sm-10">
                <form:input path="destination" cssClass="form-control"/>
                <form:errors path="destination" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
            <div class="col-sm-10">
                <form:textarea cols="80" rows="20" path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${dateFrom_error?'has-error':''}">
            <form:label path="dateFrom" cssClass="col-sm-2 control-label">Date from (dd.mm.yyyy)</form:label>
            <div class="col-sm-10">
                <form:input path="dateFrom" cssClass="form-control"/>
                <form:errors path="dateFrom" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${dateTo_error?'has-error':''}">
            <form:label path="dateTo" cssClass="col-sm-2 control-label">Date to (dd.mm.yyyy)</form:label>
            <div class="col-sm-10">
                <form:input path="dateTo" cssClass="form-control"/>
                <form:errors path="dateTo" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${price_error?'has-error':''}" >
            <form:label path="price" cssClass="col-sm-2 control-label">Price</form:label>
            <div class="col-sm-10">
                <form:input path="price" cssClass="form-control"/>
                <form:errors path="price" cssClass="help-block"/>
            </div>
        </div> 
        <div class="form-group ${availableTrips_error?'has-error':''}" >
            <form:label path="availableTrips" cssClass="col-sm-2 control-label">Number of available trips</form:label>
            <div class="col-sm-10">
                <form:input path="availableTrips" cssClass="form-control"/>
                <form:errors path="availableTrips" cssClass="help-block"/>
            </div>
        </div>
            
        <button class="btn btn-primary" type="submit">Update trip</button>
    </form:form>
</jsp:attribute>
</my:pagetemplate>
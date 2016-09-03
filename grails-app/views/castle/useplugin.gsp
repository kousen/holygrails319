<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'castle.label', default: 'Castle')}"/>
    <gvisualization:apiImport />
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
    <a href="#list-castle" class="skip" tabindex="-1">
        <g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
        </ul>
    </div>

    <div id="list-castle" class="content scaffold-list" role="main">
        <h1><g:message code="default.list.label" args="[entityName]"/></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
     
        <gvisualization:map elementId="map_div" columns="${mapColumns}" data="${mapData}"
            showTip="${true}" />
        <div id="map_div"></div>

        <f:table collection="${castleList}"/>

        <div class="pagination">
            <g:paginate total="${castleCount ?: 0}"/>
        </div>
    </div>
</body>
</html>
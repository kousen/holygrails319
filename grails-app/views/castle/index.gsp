<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCfjf9Il5PFGctk1DUwXK83Vdvng5M71cg"></script>
    <g:set var="entityName" value="${message(code: 'castle.label', default: 'Castle')}"/>
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
     
        <script type="text/javascript">
            google.charts.load('45', { 'packages': ['map'] });
            google.charts.setOnLoadCallback(drawVisualization);

            function drawVisualization() {
                var map = new google.visualization.Map(document.getElementById('map_div'));
                var table = new google.visualization.DataTable();
                table.addColumn('number', 'Lat');
                table.addColumn('number', 'Lng');
                table.addColumn('string', 'Name');
                $.getJSON('/castle/index.json').done(function (data) {
                    $.each(data, function (i, castle) {
                        table.addRow([castle.latitude, castle.longitude, castle.name]);
                    });
                    map.draw(table, {showTip: true});
                });
            }
        </script>
        <div id="map_div"></div>

        <f:table collection="${castleList}"/>

        <div class="pagination">
            <g:paginate total="${castleCount ?: 0}"/>
        </div>
    </div>
</body>
</html>
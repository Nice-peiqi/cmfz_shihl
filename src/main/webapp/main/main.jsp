<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>持明法州后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <%--引入Echarts js 文件--%>
    <script src="${path}/js/echarts.js"></script>
    <script src="${path}/js/china.js"></script>

    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>

    <%--引入Echarts js 文件--%>
    <script src="${path}/js/echarts.js"></script>
    <script src="${path}/js/china.js"></script>
</head>
<body>
<!--顶部导航-->
<nav class="navbar navbar-default">
    <!--导航条将充满整个屏幕-->
    <div class="container-fluid">
        <!--导航条的标题-->
        <div class="navbar-header">
            <a class="navbar-brand" href="${path}/main/main.jsp">持明法州管理系统</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">欢迎： <span style="color: dodgerblue">${admin.username}</span></a>
                </li>
                <li>
                    <a href="${path}/admin/exit"> 退出登录 <span class="glyphicon glyphicon-log-out"></span></a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!--栅格系统-->
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <!--左边手风琴部分-->
            <!--手风琴的样式 面板-->
            <div class="panel-group text-center " id="accordion">
                <!--默认的面版样式-->
                <div class=" panel panel-info">
                    <!--面板头-->
                    <div class="panel-heading">
                        <!--面板标题-->
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <!--面板的主体内容-->
                        <div class="panel-body">
                            <button type="button" class="btn btn-warning"><a href="javascript:$('#main').load('${path}/user/user.jsp');">有户信息</a></button><br/><br/>
                            <button type="button" class="btn btn-warning"><a href="javascript:$('#main').load('${path}/user/statistics.jsp');">用户统计</a></button><br/><br/>
                            <button type="button" class="btn btn-warning"><a href="javascript:$('#main').load('${path}/user/distribution.jsp');">用户分布</a></button><br/><br/>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <!--面板的主体内容-->
                        <div class="panel-body">
                            <button type="button" class="btn btn-success"><a href="javascript:$('#main').load('${path}/banner/banner.jsp');">轮播图信息</a></button>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <!--面板的主体内容-->
                        <div class="panel-body">
                            <div class="panel-body">
                                <button type="button" class="btn btn-info"><a href="javascript:$('#main').load('${path}/album/album.jsp');">专辑信息</a></button>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="panel panel-danger">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <!--面板的主体内容-->
                        <div class="panel-body">
                            <div class="panel-body">
                                <button type="button" class="btn btn-danger"><a href="javascript:$('#main').load('${path}/article/article.jsp');">文章信息</a></button>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <!--面板的主体内容-->
                        <div class="panel-body">

                        </div>
                    </div>
                </div>
                <hr/>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseSix">
                                管理员管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseSix" class="panel-collapse collapse">
                        <!--面板的主体内容-->
                        <div class="panel-body">

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-10" id="main">
            <div class="jumbotron text-left">
                <h2>欢迎来到持明法洲后台管理系统</h2>
            </div>
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel"  align="center">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="4"></li>
                </ol>

                <!--幻灯片的图片-->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="${path}/bootstrap/img/shouye.jpg">
                        <div class="carousel-caption">
                            <%--描述--%>

                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/1.png">
                        <div class="carousel-caption">
                            <%--描述--%>
                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/2.png">
                        <div class="carousel-caption">
                            <%--描述--%>
                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/3.png">
                        <div class="carousel-caption">
                            <%--描述--%>
                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/4.png">
                        <div class="carousel-caption">
                            <%--描述--%>
                        </div>
                    </div>
                </div>
                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic"  data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic"  data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                </a>
            </div>
        </div>
    </div>
</div>

<!--页脚-->
<div class="panel panel-footer " align="center">
    <div>@百知教育 zhangcn@zparkhr.com</div>
</div>
<!--巨幕开始-->
<!--右边轮播图部分-->
<!--页脚-->
<!--栅格系统-->

</body>
</html>

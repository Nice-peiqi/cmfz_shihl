<%@  page pageEncoding="UTF-8" contentType="text/html;UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="application/javascript">
        $(function () {
            $("#list").jqGrid({
                url: "${path}/user/showPage",
                datatype: "json",
                rowNum: 5,
                autowidth: true,
                height: "auto",
                styleUI: "Bootstrap",
                rowList: [5, 10, 15, 20],
                pager: '#pager',
                viewrecords: true,  //是否展示总条数
                colNames: ['Id', '头像', '名字', '昵称', '密码', '性别', '状态', '手机号', '注册时间'],
                colModel: [
                    {name: 'id', width: 55, hidden: true},
                    {
                        name: 'pic_img', id: "img_path", width: 100, edittype: "file", align: "center",
                        formatter: function (cellvalue) {
                            return "<img src='${path}/upload/photo/" + cellvalue + "' style='width:150px;height:130px' />";
                        },// 返回图片。
                    },
                    {name: 'name', width: 80, align: "center"},
                    {name: 'ahama', width: 90, align: "center"},
                    {name: 'password', width: 80, align: "center"},
                    {name: 'sex', width: 80, align: "center"},
                    {
                        name: 'status', width: 80, align: "center",
                        formatter: function (cellvalue, option, row) {
                            if (cellvalue == "冻结") {
                                //展示状态
                                return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'  >解除冻结</button>";
                            } else {
                                //不展示状态
                                return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'  >冻结</button>";
                            }
                        },
                    },
                    {name: 'phone', width: 80, align: "center"},
                    {
                        name: 'reg_date',
                        width: 80,
                        align: "center",
                        formatter: 'date',
                        formatoptions: {newformat: 'Y-m-d'}
                    }
                ]

            });

            /*增删改查操作*/
            $("#list").jqGrid('navGrid', '#pager',
                {edit: false, add: false, del: false, search: false}
            );

        });

        //点击修改
        function change(id, value) {

            if (value == "冻结") {

                $.ajax({
                    url: "${path}/user/modifierStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "解除冻结"},
                    success: function (data) {
                        //页面的刷新
                        $("#list").trigger("reloadGrid");
                        //提示框添加信息
                        $("#showMsg").html(data.error);
                        //展示错误信息
                        $("#show").show();

                        //设置提示框时间
                        setTimeout(function () {
                            //关闭提示框
                            $("#show").hide();
                        }, 3000);
                    }
                });
            } else {
                $.ajax({
                    url: "${path}/user/modifierStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "冻结"},
                    success: function (data) {

                        //页面的刷新
                        $("#list").trigger("reloadGrid");
                        //提示框添加信息
                        $("#showMsg").html(data.error);
                        //展示错误信息
                        $("#show").show();
                        //设置提示框时间
                        setTimeout(function () {
                            //关闭提示框
                            $("#show").hide();
                        }, 3000);
                    }
                });
            }
        }

        //导出用户信息
        $("#out").click(function () {
            $.ajax({
                url: "${path}/user/outPoi",
                type: "post",
                dataType: "Json",
                success: function (data) {
                    //提示框添加信息
                    $("#showMsg").html(data.error);
                    //展示错误信息
                    $("#show").show();
                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show").hide();
                    }, 3000);
                }
            })
        });

        //测试GoEasy
        $("#test").click(function () {

            $.post("${path}/user/add", function (data) {
                //页面的刷新
                $("#list").trigger("reloadGrid");
            }, "json");

        });

        //测试手机验证码
        $("#button").click(function () {
           var d = $("#input").val();
           $.post("${path}/user/testPhone","phone="+d,function () {

           },"json");
        });

    </script>
</head>
<body>
<div class="panel panel-warning">
    <div class="panel panel-heading">
        <h3>用户信息</h3>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">用户信息</a></li>
        </ul>
        <br/>

        <div class="row">
            <div class="col-lg-6">
                <%--按钮--%>
                <button id="out" type="button" class="btn btn-success">导出用户信息</button>
                <button id="int" type="button" class="btn btn-info">导入用户</button>
                <button id="test" type="button" class="btn btn-warning">测试按钮</button>
            </div><!-- /.col-lg-6 -->
            <div class="col-lg-4 col-md-offset-2">
                <div class="input-group">
                    <input id="input" type="text" class="form-control" name="phone" placeholder="请输入一个手机号码">
                    <span class="input-group-btn">
                        <button id="button" class="btn btn-default" type="button">测试短信</button>
                    </span>
                </div><!-- /input-group -col-md-offset-8->
            </div><!-- /.col-lg-6 -->
        </div><!-- /.row -->

        <br/><br/>

        <%--警告框--%>
        <div id="show" class="alert alert-warning alert-dismissible" role="alert" style="width:200px;display: none">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            <strong id="showMsg"></strong>
        </div>

        <%--表格--%>
        <table class="table" id="list"></table>
        <%--分页--%>
        <div id="pager"></div>
    </div>
</div>
</body>
</html>
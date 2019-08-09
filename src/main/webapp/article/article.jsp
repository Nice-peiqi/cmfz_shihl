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
    <script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.create('#editor_id', {
            uploadJson: "${path}/editor/upload",//文件上传
            filePostName: "photo",
            allowFileManager: true,
            fileManagerJson: "${path}/editor/queryAllPhoto",//内容回显
            afterBlur: function () {  //编辑器失去焦点(blur)时执行的回调函数。
                this.sync();  //将编辑器中的内容同步到表单
            }
        });
    </script>
    <script type="application/javascript">
        $(function () {
            $("#list").jqGrid({
                url: "${path}/article/showPage",
                editurl: "${path}/article/edit",
                datatype: "json",
                rowNum: 5,
                autowidth: true,
                height: "auto",
                styleUI: "Bootstrap",
                rowList: [5, 10, 20, 40],
                pager: '#pager',
                viewrecords: true,  //是否展示总条数
                colNames: ['Id', '封面', '标题', '内容', '上传时间', '状态', '上师Id', "操作"],
                colModel: [
                    {name: 'id', width: 55, hidden: true},
                    {
                        name: 'insert_img', width: 100, editable: true, edittype: "file", align: "center",
                        formatter: function (cellvalue) {
                            return "<img src='${path}/upload/photo/" + cellvalue + "' style='width:150px;height:130px' />";
                        },// 返回图片。
                    },
                    {name: 'title', editable: true, width: 80, align: "center"},
                    {name: 'content', editable: true, width: 100, align: "center"},
                    {
                        name: 'up_date',
                        width: 80,
                        align: "center",
                        formatter: 'date',
                        formatoptions: {newformat: 'Y-m-d'}
                    },
                    {
                        name: 'status', width: 80, align: "center",
                        formatter: function (cellvalue, option, row) {
                            if (cellvalue == "展示") {
                                //展示状态
                                return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'  >不展示</button>";
                            } else {
                                //不展示状态
                                return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'  >展示</button>";
                            }
                        }
                    },
                    {name: 'guru_id', width: 80, align: "center"},
                    {
                        name: "url", width: 80, align: "center",
                        formatter: function () {
                            return "<a href='#' ><span class='glyphicon glyphicon-list' /></a>";
                        }
                    }
                ]

            });

            /*增删改查操作*/
            $("#list").jqGrid('navGrid', '#pager',
                {edit: false, add: false, del: true, search: false}
            );

            //删除按钮
            $("#del").click(function () {
                var gr = jQuery("#list").jqGrid('getGridParam', 'selrow');
                if (gr != null)
                    jQuery("#list").jqGrid('delGridRow', gr, {
                        reloadAfterSubmit: true
                    });
                else
                    alert("请选择一行！");
            });
        });

        //点击修改
        function change(id, value) {

            if (value == "展示") {

                $.ajax({
                    url: "${path}/article/modifierStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "不展示"},
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
                        }, 5000);
                    }
                });
            } else {
                $.ajax({
                    url: "${path}/article/modifierStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "展示"},
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
                        }, 5000);
                    }
                });
            }
        }

        /*点击展示详情*/
        $("#edit").click(function () {
            //选中一行 获取行Id
            var rowId = $("#list").jqGrid("getGridParam", "selrow");
            //判断是否选中一行
            if (rowId != null) {
                //根据行Id获取行数据
                var row = $("#list").jqGrid("getRowData", rowId);

                //展示模态框
                $("#myModal").modal("show");

                //给inout框设置内容
                $("#title").val(row.title);

                //给KindEditor框设置内容
                KindEditor.html("#editor_id", row.content);

                //添加按钮
                $("#modalFooter").html("<button type='button' onclick='updateArticle(\"" + rowId + "\")' class='btn btn-default'>提交</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
            } else {
                alert("请选中一行数据");
            }

        });

        /*点击添加文章*/
        $("#add").click(function () {

            //给表单清空
            $("#articleFrom")[0].reset();
            //清空
            KindEditor.html("#editor_id", "");

            //展示模态框
            $("#myModal").modal("show");
            //添加按钮
            $("#modalFooter").html("<button type='button' onclick='addArticle()' class='btn btn-default'>提交" +
                "</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
        });

        /*点击添加按钮操作*/
        function addArticle() {
            //向后台提交
            $.ajax({
                url: "${path}/article/add",
                type: "post",
                dateType: "JSON",
                data: $("#articleFrom").serialize(),
                success: function () {
                    $("#myModal").modal('hide');//隐藏模态框
                    $("#list").trigger("reloadGrid");//刷新jqGrid

                }
            });

        }

        /*修改的提交按钮*/
        /*修改的提交按钮*/
        function updateArticle(rowId) {

            //向后台提交
            $.ajax({
                url: "${path}/article/update?articleId=" + rowId,
                type: "post",
                dateType: "JSON",
                data: $("#articleFrom").serialize(),
                success: function () {
                    $("#myModal").modal('hide');//隐藏模态框
                    $("#list").trigger("reloadGrid");//刷新jqGrid
                }
            });


        }
    </script>
</head>
<body>
<div class="panel panel-danger">
    <div class="panel panel-heading">
        <h3>文章信息</h3>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">文章信息</a></li>
        </ul>
        <br/>
        <%--按钮--%>
        <button id="edit" type="button" class="btn btn-success">查看文章</button>
        <button id="add" type="button" class="btn btn-info">增加文章</button>
        <button id="del" type="button" class="btn btn-warning">删除文章</button>
        <br/> <br/>

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
<%--初始化模态框--%>

<div id="myModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document" style="width: 730px">
        <div class="modal-content">
            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="gridSystemModalLabel">文章详情</h4>
            </div>
            <%--模态框内容--%>
            <div class="modal-body">
                <form class="form-horizontal" id="articleFrom" enctype="multipart/form-data">

                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">标题</span>
                        <input id="title" name="title" type="text" class="form-control" aria-describedby="basic-addon1">
                    </div>
                    <br/>
                    <%--封面：<input type="file" name="upload">
                    <br/1>--%>
                    <div class="input-group">
                        <%--初始化富文本编辑器--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                        </textarea>
                    </div>
                </form>
            </div>
            <%--  模态框按钮  --%>
            <div class="modal-footer" id="modalFooter">
                <%--<button type="button" class="btn btn-default">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal --
</body>
</html>
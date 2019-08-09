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
    <script>
        $(function () {

            $("#list").jqGrid({
                url: "${path}/album/showPage",
                editurl: "${path}/album/edit",
                datatype: "json",
                autowidth: true,
                height: "auto",
                styleUI: "Bootstrap",
                rowNum: 4,
                rowList: [4, 8, 16, 32],
                pager: '#pager',
                viewrecords: true,
                sortorder: "desc",
                multiselect: false,
                colNames: ['id', '名字', '封面', '评分', '作者', '播音员', '集数', '简要', '时间'],
                colModel: [
                    {name: 'id', width: 55, hidden: true},
                    {name: 'title', width: 90, align: "center", editable: true},
                    {
                        name: 'cover_img',
                        width: 100,
                        align: "center",
                        edittype: "file",
                        editable: true,
                        editoptions: {enctype: "multipart/from-data"},
                        formatter: function (cellvalue) {
                            return "<img src='${path}/upload/img/" + cellvalue + "' style='width:150px;height:100px'/>";
                        }
                    },
                    {name: 'score', width: 100, align: "center", editable: true},
                    {name: 'author', width: 80, align: "center", editable: true},
                    {name: 'broadcast', width: 80, align: "center", editable: true},
                    {name: 'count', width: 80, align: "center"},
                    {name: 'centent', width: 80, align: "center", editable: true},
                    {
                        name: 'pub_date',
                        index: 'total',
                        width: 80,
                        align: "center",
                        formatter: 'date', formatoptions: {newformat: 'Y-m-d'}
                    }
                ],
                subGrid: true, //开启子表格支持
                //subgrid_id  子表格的Id,当开启子表格式,会在主表格中创建一个子表格，subgrid_id就是这个子表格的Id
                subGridRowExpanded: function (subgridId, rowId) {
                    addSubGrid(subgridId, rowId);
                },
                /*subGridRowColapsed : function(subgrid_id, row_id) {
                    // this function is called before removing the data
                    //var subgrid_table_id;
                    //subgrid_table_id = subgrid_id+"_t";
                    //jQuery("#"+subgrid_table_id).remove();
                }*/
            });
            /*增删改查操作*/
            $("#list").jqGrid('navGrid', '#pager',
                {add: true, edit: true, del: true, search: false},
                {
                    afterSubmit: function (data) {
                        //文件上传
                        $.ajaxFileUpload({
                            url: "${path}/album/uploadAlbum",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "cover_img",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#list").trigger("reloadGrid");
                            }
                        })
                        //一定要有返回值，返回什么都行
                        return "hello";
                    },
                    closeAfterEdit: true
                },
                {
                    afterSubmit: function (data) {
                        //文件上传
                        $.ajaxFileUpload({
                            url: "${path}/album/uploadAlbum",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "cover_img",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#list").trigger("reloadGrid");
                            }
                        })
                        //一定要有返回值，返回什么都行
                        return "hello";
                    },
                    closeAfterAdd: true
                },
                {}
            );
            $("#add").click(function () {
                $("#list").jqGrid("editGridRow", "new", {
                    height: 300,
                    reloadAfterSubmit: true,

                    afterSubmit: function (data) {
                        //文件上传
                        $.ajaxFileUpload({
                            url: "${path}/album/uploadAlbum",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "cover_img",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#list").trigger("reloadGrid");
                            }
                        })
                        //一定要有返回值，返回什么都行
                        return "hello";
                    },
                    closeAfterAdd: true
                })
            });
            $("#edit").click(function () {
                var gr = jQuery("#list").jqGrid('getGridParam', 'selrow');
                if (gr != null)
                    jQuery("#list").jqGrid('editGridRow', gr, {
                        height: 300,
                        reloadAfterSubmit: true,

                        afterSubmit: function (data) {

                            //文件上传
                            $.ajaxFileUpload({
                                url: "${path}/album/uploadAlbum",
                                type: "post",
                                dataType: "JSON",
                                fileElementId: "cover_img",
                                data: {id: data.responseText},
                                success: function () {
                                    //刷新表单
                                    $("#list").trigger("reloadGrid");
                                }
                            })
                            //一定要有返回值，返回什么都行
                            return "hello";
                        },
                        closeAfterEdit: true
                    });
                else
                    alert("请选择一行！");
            })
            $("#del").click(function () {
                var gr = jQuery("#list").jqGrid('getGridParam', 'selrow');
                if (gr != null)
                    jQuery("#list").jqGrid('delGridRow', gr, {
                        reloadAfterSubmit: true
                    });
                else
                    alert("请选择一行！");
            })

        });

        //创建子表单
        function addSubGrid(subgridId, rowId) {

            var subgridTableId = subgridId + "table";
            var pagerId = subgridId + "pager";

            //初始化表单,分页工具栏
            $("#" + subgridId).html("<table id='" + subgridTableId + "'/><div id='" + pagerId + "'/>");
            //创建表单
            $("#" + subgridTableId).jqGrid({
                //url:"/chapter/queryByPage?albumId="+rowId,
                url: "${path}/chapter/showPage?albumId=" + rowId,
                editurl: "${path}/chapter/edit?albumId=" + rowId,
                datatype: "json",
                rowNum: 2,
                rowList: [2, 4, 6, 8, 10],
                pager: "#" + pagerId,
                sortname: 'num',
                sortorder: "asc",
                autowidth: true,
                height: "auto",
                styleUI: "Bootstrap",
                colNames: ['编号', '标题', '音频', '大小', '时常', '上传时间', "操作"],
                colModel: [
                    {name: "id", width: 130, key: true},
                    {name: "headline", width: 80, editable: true,align: "center"},
                    {name: "url", width: 120, editable: true, edittype: "file",align: "center"},
                    {name: "size", width: 70, align: "center"},
                    {name: "duration", width: 70, align: "center"},
                    {name: "up_date", width: 70, align: "center",formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
                    {name: "url", width: 70, align: "center",
                        formatter: function (cellVale) {
                            return "<a href='#' onclick='playerChapter(\"" + cellVale + "\")' ><span class='glyphicon glyphicon-play-circle' /></a>&nbsp;&emsp;&emsp;" +
                                   "<a href='#' onclick='downloadChapter(\"" + cellVale + "\")'><span class='glyphicon glyphicon-download' /></a>";
                        }
                    }
                ]
            });

            /*子表格增删改查*/
            $("#" + subgridTableId).jqGrid('navGrid', "#" + pagerId,
                {edit: false, add: true, del: true, search: false},
                {},
                {
                    afterSubmit: function (data) {
                        //文件上传
                        $.ajaxFileUpload({
                            url: "${path}/chapter/uploadChapter",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "url",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#" + subgridTableId).trigger("reloadGrid");
                                $("#list").trigger("reloadGrid");
                            }
                        })
                        //一定要有返回值，返回什么都行
                        return "hello";
                    },
                    closeAfterAdd: true
                },
                {}
            );
        }

        //下载
        function downloadChapter(filename) {
            location.href="${path}/chapter/dawnload?filename="+filename;
        };

        //在线播放
        function playerChapter(filename) {
            //展示模态框
            $("#myModal").modal("show");
            //播放
            $("#playAudio").attr("src","${path}/upload/music/"+filename);
        };

    </script>
</head>
<body>
<div class="panel panel-warning">
    <div class="panel panel-heading">
        <h3>轮播图信息</h3>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">专辑信息</a></li>
        </ul>
        <br/>
        <%--按钮--%>
        <button id="add" type="button" class="btn btn-success">增加专辑</button>
        <button id="edit" type="button" class="btn btn-info">修改专辑</button>
        <button id="del" type="button" class="btn btn-danger">删除专辑</button>
        <br/> <br/>

        <%--表格--%>
        <table class="table" id="list"></table>
        <%--分页--%>
        <div id="pager"></div>


        <!--modal:模态框的样式  fade渐变动画效果  tabindex  利用键盘操作模态框 -->
        <div id="myModal" class="modal fade" tabindex="-1">
            <!--模态框的面板-->
            <div class="modal-dialog">
                <!--模态框的主体-->
                <div class="modal-content">
                    <!--模态框的头部-->
                    <div class="modal-header">
                        <!--关闭模态框的X号-->
                        <button type="button" class="close" data-dismiss="modal" ><span>&times;</span></button>
                        <!--模态框的标题信息-->
                        <center><h4 class="modal-title"><strong>在 线 播 放</strong></h4></center>
                    </div>
                    <!--模态框的身体-->
                    <div class="modal-body" id="document">
                        <!--可以书写任意的内容-- controls-->
                        <center><audio id="playAudio" src="" controls/></center>
                    </div>
                    <!--模态框的尾部-->
                    <div class="modal-footer">                        <!--关闭模态框-->
                        <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
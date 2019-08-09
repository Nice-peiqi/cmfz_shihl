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
                url: "${path}/banner/showPage",
                editurl: "${path}/banner/edit",
                datatype: "json",
                colNames: ["id", "名称", "图片", "状态", "上传时间", "描述"],
                autowidth: true,
                height:"auto",
                sortorder: "desc",
                styleUI: "Bootstrap",
                pager: "#pager",  //生成分页的工具栏
                rowNum: 2,       //后台每页显示的条数
                rowList: [2, 4, 6, 8, 10],// 展示的记录数
                viewrecords: true, //展示总条数
                colModel: [
                    {name: "id",align:"center",width:20,hidden:true},
                    {name: "title",align:"center", editable: true,width:90},
                    {name: "img_path", align:"center",editable: true,width:100,edittype:"file",
                        formatter:function (cellvalue) {
                            return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:150px;height:100px'/>";
                        }
                    },
                    {name: "status",align:"center",width:80,formatter:function (cellvalue, options, rowObject) {
                            if(cellvalue=="1"){
                                return  "<button type='button' class='btn btn-success' onclick='change(\""+ rowObject.id+"\")'>冻结</button>";
                            }else{
                                return  "<button type='button' class='btn btn-danger' onclick='change(\""+ rowObject.id+"\")'>解除冻结</button>";
                            }
                        }},
                    {name: "up_date",align:"center", width:80,formatter:'date',formatoptions:{newformat:'Y-m-d'}},
                    {name: "description", align:"center",editable: true,width:80}
                ]
            });

            /*增删改查操作*/
            $("#list").jqGrid('navGrid', '#pager',
                {edit : true,add : true,del : true,search:false,addtext:"添加",edittext:"编辑",deltext:"删除"},
                {
                    afterSubmit:function(data){

                        //文件上传
                        $.ajaxFileUpload({
                            url:"${path}/banner/uploadBanner",
                            type:"post",
                            dataType:"JSON",
                            fileElementId:"img_path",
                            data:{id:data.responseText},
                            success:function(){
                                //刷新表单
                                $("#list").trigger("reloadGrid");
                            }
                        })
                        //一定要有返回值，返回什么都行
                        return "hello";
                    },
                    closeAfterEdit:true
                },
                {
                    afterSubmit:function(data){
                        //文件上传
                        $.ajaxFileUpload({
                            url:"${path}/banner/uploadBanner",
                            type:"post",
                            dataType:"JSON",
                            fileElementId:"img_path",
                            data:{id:data.responseText},
                            success:function(){
                                //刷新表单
                                $("#list").trigger("reloadGrid");
                            }
                        })
                        //一定要有返回值，返回什么都行
                        return "hello";
                    },
                    closeAfterAdd:true
                },
                {}
            );

            $("#add").click(function(){
                $("#list").jqGrid("editGridRow","new",{
                    height:300,
                    reloadAfterSubmit:true,

                    afterSubmit:function(data){
                        //文件上传
                        $.ajaxFileUpload({
                            url:"${path}/banner/uploadBanner",
                            type:"post",
                            dataType:"JSON",
                            fileElementId:"img_path",
                            data:{id:data.responseText},
                            success:function(){
                                //刷新表单
                                $("#list").trigger("reloadGrid");
                            }
                        })
                        //一定要有返回值，返回什么都行
                        return "hello";
                    },
                    closeAfterAdd:true
                })
            });
            $("#edit").click(function () {
                var gr = jQuery("#list").jqGrid('getGridParam', 'selrow');
                if (gr != null)
                    jQuery("#list").jqGrid('editGridRow', gr, {
                        height : 300,
                        reloadAfterSubmit : true,

                        afterSubmit:function(data){

                            //文件上传
                            $.ajaxFileUpload({
                                url:"${path}/banner/uploadBanner",
                                type:"post",
                                dataType:"JSON",
                                fileElementId:"img_path",
                                data:{id:data.responseText},
                                success:function(){
                                    //刷新表单
                                    $("#list").trigger("reloadGrid");
                                }
                            })
                            //一定要有返回值，返回什么都行
                            return "hello";
                        },
                        closeAfterEdit:true
                    });
                else
                    alert("请选择一行！");
            })
            $("#del").click(function () {
                var gr = jQuery("#list").jqGrid('getGridParam', 'selrow');
                if (gr != null)
                    jQuery("#list").jqGrid('delGridRow', gr, {
                        reloadAfterSubmit : true
                    });
                else
                    alert("请选择一行！");
            })
        });
        //修改状态
        function change(id){
            $.ajax({
                url:"${path}/banner/updateStatus",
                type:"post",
                dataType:"JSON",
                data:{"id":id},
                success:function (data) {

                    //刷新页面
                    $("#list").trigger("reloadGrid");
                    //提示框增加信息
                    $("#showMsg").html(data.message);
                    //展示错误信息
                    $("#show").show();

                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show").hide();
                    }, 6000);
                }
            })
        }
    </script>
</head>
<body>
<div class="panel panel-success">
    <div class="panel panel-heading">
        <h3>轮播图信息</h3>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">轮播图信息</a></li>
        </ul>
        <br/>
        <%--按钮--%>
        <button id="add" type="button" class="btn btn-success">增加轮播图</button>
        <button id="edit" type="button" class="btn btn-info">修改轮播图</button>
        <button id="del" type="button" class="btn btn-danger">删除轮播图</button>
        <br/> <br/>

        <%--警告框--%>
        <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
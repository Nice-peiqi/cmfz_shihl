<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<%--引入Echarts js 文件--%>

<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script src="${path}/js/echarts.js"></script>
<script src="${path}/js/china.js"></script>

<script type="text/javascript">
    $(function () {

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('m'));

        $.post("${path}/user/queryByCity", function (data) {

            var series = [];

            for (var i = 0; i < data.length; i++) {
                var d = data[i];

                series.push({
                    name: d.title,
                    type: 'map',
                    mapType: 'china',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: d.citys
                })
            }

            // 指定图表的配置项和数据
            option = {
                title: {
                    text: '用户分布图',
                    subtext: '纯属虚构',
                    link: "${path}/user/distribution.jsp",
                    target: "self",
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['小姐姐', '小哥哥']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: series
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }, "json");

    });
</script>

<br/><br/><br/>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<center>
    <div id="m" style="width:800px;height:500px;"></div>
</center>
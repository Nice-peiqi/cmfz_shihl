<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>

<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script src="${path}/js/echarts.js"></script>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('m'));

        var goEasy = new GoEasy({
            appkey: "BC-41e63be8af434834b75e776b897845a9" //你自己的appkeys
        });
        goEasy.subscribe({
            channel: "nice", //管道标识
            onMessage: function (message) {

                //将json字符串转为json对象
                var data = JSON.parse(message.content);
                console.log(data);

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户注册量展示',//标题
                        link: "${path}/user/statistics.jsp",
                        target: "self",
                        subtext: "用户信息"
                    },
                    tooltip: {},  //鼠标的提示
                    legend: {
                        // show:false,  是否展示 选项
                        data: ['男孩', "女孩"]  //选项
                    },
                    xAxis: {
                        data: data.month  //横坐标
                    },
                    yAxis: {},  //纵坐标   自适应
                    series: [{
                        name: '男孩',
                        type: 'bar',
                        data: data.boys
                    }, {
                        name: '女孩',
                        type: 'bar',//line
                        data: data.girls
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('m'));

        $.post("${path}/user/queryByMonth", function (data) {

            console.log(data);

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户注册量展示',//标题
                    link: "${path}/user/statistics.jsp",
                    target: "self",
                    subtext: "用户信息"
                },
                tooltip: {},  //鼠标的提示
                legend: {
                    // show:false,  是否展示 选项
                    data: ['男孩', "女孩"]  //选项
                },
                xAxis: {
                    data: data.month  //横坐标
                },
                yAxis: {},  //纵坐标   自适应
                series: [{
                    name: '男孩',
                    type: 'bar',
                    data: data.boys
                }, {
                    name: '女孩',
                    type: 'bar',//line
                    data: data.girls
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }, "json")

    });
</script>

<br/><br/><br/>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<center>
    <div id="m" style="width:800px;height:600px;"></div>
</center>
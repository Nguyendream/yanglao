    // 1	12.43	34.53	3.4	0	2018-11-15 10:57:42
    // 2	12.43	54.53	3.4	0	2018-11-15 10:58:05
    // 3	12.43	34.53	8.4	0	2018-11-15 10:58:56
    // 4	17.43	35.53	9.4	1	2018-11-28 15:30:03
    // 5	17.43	35.53	9.4	1	2019-04-23 16:58:09
    // 6	18.43	35.53	9.4	1	2019-04-23 17:58:07
    // 7	18.43	35.53	9.4	0	2019-04-23 17:58:47
    // 8	18.43	35.53	9.4	0	2019-04-23 18:26:30
    // 9	20	90	2000	1	2019-04-23 18:55:55
    // 14	20	90	2000	1	2019-04-23 19:24:19

var origin = "";

var ctx1 = $('#chart1');
var ctx2 = $('#chart2');

var selectDevie = $('#select-device');
var selectDate = $('#datepicker');

var color = Chart.helpers.color;

$('.select2').select2();

selectDate.val(moment(new Date()).format('YYYY-MM-DD'));
selectDate.datepicker({
    language: 'zh-CN',
    autoclose: true,
    todayHighlight: true,
    format: 'yyyy-mm-dd',
    endDate: new Date(),
});

$.chart_cfg = {
    ctx1: function(chartData) {
        var config = {
            type: 'scatter',
            data: chartData,
            options: {
                responsive: true,
                hoverMode: 'nearest',
                intersect: true,
                title: {
                    display: true,
                    fontSize: 14,
                    text: selectDate.val() + '的温度湿度散点图'
                },
                tooltips: {
                    callbacks: {
                        label: function (tooltipItem, data) {
                            var label = '时间: ' + moment(tooltipItem.xLabel).format('H:mm:ss');
                            label += ', ' + data.datasets[tooltipItem.datasetIndex].label + ': ';
                            label += tooltipItem.yLabel;
                            return label;
                        }
                    }
                },
                scales: {
                    xAxes: [{
                        ticks: {
                            // source: 'data',
                            // autoSkip: true
                            userCallback: function (label, index, labels) {
                                return moment(label).format('H:mm');
                            }
                        }
                    }],
                    yAxes: [{
                        type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                        display: true,
                        position: 'left',
                        id: 'y-axis-1',
                    }, {
                        type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                        display: true,
                        position: 'right',
                        reverse: true,
                        id: 'y-axis-2',

                        // grid line settings
                        gridLines: {
                            drawOnChartArea: false, // only want the grid lines for one axis to show up
                        },
                    }]
                }
            }
        }
        return config;
    },
    ctx2: function (chartData) {
        var config = {
            type: 'scatter',
            data: chartData,
            options: {
                responsive: true,
                hoverMode: 'nearest',
                intersect: true,
                title: {
                    display: true,
                    fontSize: 14,
                    text: selectDate.val() + '的可燃气体指数散点图'
                },
                tooltips: {
                    callbacks: {
                        label: function (tooltipItem, data) {
                            var label = '时间: ' + moment(tooltipItem.xLabel).format('H:mm:ss');
                            label += ', ' + data.datasets[tooltipItem.datasetIndex].label + ': ';
                            label += tooltipItem.yLabel;
                            return label;
                        }
                    }
                },
                scales: {
                    xAxes: [{
                        ticks: {
                            // source: 'data',
                            // autoSkip: true
                            userCallback: function (label, index, labels) {
                                return moment(label).format('H:mm');
                            }
                        }
                    }]
                }
            }
        }
        return config;
    }
}

$.fchart = {
    assembleAxis: function (x, y) {
        var axis = [];
        for (var i in x) {
            axis.push({
                x: moment(x[i]),
                y: y[i]
            })
        }
        return axis;
    },
    initData: function (sourceData) {
        if (sourceData == null) {
            return null;
        }

        var temp = [];//温度
        var humi = [];//湿度
        var gus = [];//可燃气浓度
        var inf = [];//红外
        var time = [];//时间

        for (var i in sourceData) {
            temp.push(sourceData[i].temp);
            humi.push(sourceData[i].humi);
            gus.push(sourceData[i].gus);
            inf.push(sourceData[i].inf);
            time.push(sourceData[i].createTime);
        }

        var data =  {
            temp: temp,
            humi: humi,
            gus: gus,
            inf: inf,
            time: time
        }
        return data;
    },
    newChart1: function (ctx, sourceData) {
        var initData = this.initData(sourceData);
        var axis1 = [];
        var axis2 = [];
        if (initData != null) {
            axis1 = this.assembleAxis(initData.time, initData.temp);
            axis2 = this.assembleAxis(initData.time, initData.humi);
        }
        // console.log(initData);
        // console.log(axis1);
        // console.log(axis2);

        var chartData = {
            datasets: [{
                label: '温度',
                xAxisID: 'x-axis-1',
                yAxisID: 'y-axis-1',
                borderColor: window.chartColors.red,
                backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
                data: axis1
            }, {
                label: '湿度',
                xAxisID: 'x-axis-1',
                yAxisID: 'y-axis-2',
                borderColor: window.chartColors.blue,
                backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
                data: axis2
            }]
        };
        // console.log(chartData);

        return new Chart(ctx, $.chart_cfg.ctx1(chartData));
    },
    newChart2: function (ctx, sourceData) {
     var initData = this.initData(sourceData);
        var axis1 = [];
        if (initData != null) {
            axis1 = this.assembleAxis(initData.time, initData.gus);
        }

        var chartData = {
            datasets: [{
                label: '可燃气体指数',
                borderColor: window.chartColors.red,
                backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
                data: axis1
            }]
        };
        // console.log(chartData);

        return new Chart(ctx, $.chart_cfg.ctx2(chartData));
    }
}

//ajax
$.func = {
    getUserInfo: function () {
        //get user info
        $.ajax({
            type: "post",
            url: origin + "user/get_user_info.do",
            timeout: "5000",
            async: true,
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                if (data.status == 0) {
                    var username = data.data.username;
                    var createTime = data.data.createTime.substr(0,10);
                    $("#usernameId").html(username);
                    $("#helloId").html(username + '，你好啊！'
                        + '<small>注册时间：' + createTime + '</small>');
                } else if (data.status == 10) {
                    location.href="./login";
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert('网络连接超时');
            }
        });
    },
    logout: function() {
        $.ajax({
            type: "post",
            url: "user/logout.do",
            timeout: "5000",
            async: true,
            error: function(textStatus, data) {
                alert("\ntextStatus: " + textStatus +
                    "\nsuccess: " + data.success);
            },
            success: function(data) {
                if (data.status == 0) {
                    location.href="./login";
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    listDevice: function () {
        var deviceList = null;
        $.ajax({
            type: "GET",
            url: origin + "manage/device/list_device.do",
            cache: false,  //禁用缓存
            data: 'pageSize=100',  //传入组装的参数
            async: false,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                // console.log(result);
                //setTimeout仅为测试延迟效果
                if (result.status == 0) {
                    //设备列表
                    deviceList = result.data.list;
                } else {
                    alert(result.msg);
                }
            }
        });
        return deviceList;
    },
    getLastDay: function () {
        var lastDay = '';
        $.ajax({
            type: "GET",
            url: origin + "manage/device/get_lastday.do",
            cache: false,  //禁用缓存
            data: 'deviceId=' + selectDevie.val(),  //传入组装的参数
            async: false,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                if (result.status == 0) {
                    lastDay = result.data;
                } else {
                    alert(result.msg);
                }
            }
        });
        return lastDay;
    },
    listLogs: function () {
        var logs = null;
        $.ajax({
            type: "POST",
            url: origin + "manage/device/list_logs_day.do",
            cache: false,  //禁用缓存
            data: 'deviceId=' + selectDevie.val() + '&date=' + selectDate.val(),  //传入组装的参数
            async: false,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                if (result.status == 0) {
                    logs = result.data;
                } else {
                    if (result.msg != '当天没有数据') {
                        alert(result.msg);
                    }
                }
            }
        });
        return logs;
    }
}

var setSelect2 = function(deviceList) {
    var select = $('#select-device');
    select.empty();
    for (var i in deviceList) {
        var option = "<option value="+ deviceList[i].deviceId +
            ">" + deviceList[i].deviceId +
            " { " + deviceList[i].deviceName +
            " }</option>";
        select.append(option);
    }

}

$(function () {
    //获取用户登陆信息
    $.func.getUserInfo();
    $('#logoutId').click(function () {
        $.func.logout();
    });

    //获取设备列表
    var deviceList = $.func.listDevice();
    setSelect2(deviceList);

    var sourceData = $.func.listLogs();
    console.log(sourceData);
    var myChart1 = $.fchart.newChart1(ctx1, sourceData);
    var myChart2 = $.fchart.newChart2(ctx2, sourceData);



    var updateChart = function () {
        var sourceData = $.func.listLogs();
        console.log(sourceData);

        myChart1.destroy();
        myChart2.destroy();
        myChart1 = $.fchart.newChart1(ctx1, sourceData);
        myChart2 = $.fchart.newChart2(ctx2, sourceData);
        // myChart1.update();
        // myChart2.update();
    }

    selectDevie.on('select2:close', function () {updateChart()});
    selectDate.change(function () {updateChart()});

    //最近数据按钮
    $('#lastDataId').click(function () {
        var lastDay = $.func.getLastDay();
        if (lastDay != '') {
            selectDate.val(lastDay);
            selectDate.datepicker('update');
            updateChart();
        }
    });
})

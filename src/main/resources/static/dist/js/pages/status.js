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

var deviceId = 0;
var origin = "";

var ctx1 = $('#chart1');
var ctx2 = $('#chart2');

var xx = [
    '2019-04-23 10:57:42',
    '2019-04-23 10:58:05',
    '2019-04-23 10:58:56',
    '2019-04-23 15:30:03',
    '2019-04-23 16:58:09',
    '2019-04-23 17:58:07'
];
var y1 = [12.43, 12.43, 12.43, 17.43, 17.43, 18.43];
var y2 = [34.53, 54.53, 34.53, 35.53, 35.53, 35.53];

var assembleData = function (x, y) {
    return {x: x, y: y}
}


var axis1 = [];
var axis2 = [];

for (var i = 0; i < xx.length; i++) {
    var date = new Date(xx[i]);
    // axis1[i] = assembleData(date.getHours() + date.getMinutes()/60 + date.getSeconds()/3600, y1[i]);
    // axis2[i] = assembleData(date.getHours() + date.getMinutes()/60 + date.getSeconds()/3600, y2[i]);
    axis1[i] = assembleData(moment(date), y1[i]);
    axis2[i] = assembleData(moment(date), y2[i]);
}

console.log(axis1);
console.log(axis2);

var color = Chart.helpers.color;
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

console.log(chartData);

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
                    location.href="/login";
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
                    location.href="/login";
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
                console.log(result);
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

    $.func.getUserInfo();
    $('#logoutId').click(function () {
        $.func.logout();
    });

    var deviceList = $.func.listDevice();
    console.log(deviceList);
    setSelect2(deviceList);

    $('#lastDataId').click(function () {
        $('#datepicker').val(moment(new Date()).format('YYYY-MM-DD'));
        $('#datepicker').datepicker('update');
    });


    var ctx1_config = {
        type: 'scatter',
        data: chartData,
        options: {
            responsive: true,
            hoverMode: 'nearest',
            intersect: true,
            title: {
                display: true,
                fontSize: 14,
                text: moment(xx[0]).format('YYYY-MM-DD') + '的温度湿度散点图'
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
    };

    var ctx2_config = {
        type: 'scatter',
        data: chartData,
        options: {
            responsive: true,
            hoverMode: 'nearest',
            intersect: true,
            title: {
                display: true,
                fontSize: 14,
                text: moment(xx[0]).format('YYYY-MM-DD') + '的温度湿度散点图'
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
    };

    var myChart1 = new Chart(ctx1, ctx1_config);
    var myChart2 = new Chart(ctx2, ctx1_config);



    $('.select2').select2();

    $('#datepicker').val(moment(new Date()).format('YYYY-MM-DD'));
    $('#datepicker').datepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true,
        format: 'yyyy-mm-dd',
        endDate: new Date()
    });

})

var deviceId = 0;
var origin = "http://strend.iok.la/";

//table的dom元素
var table1 = $('#table1Id');
var table2 = $('#table2Id');
//dataTable对象
var tableData1 = null;
var tableData2 = null;
//提示信息
var lang = {
    "sProcessing": "处理中...",
    "sLengthMenu": "每页 _MENU_ 项",
    "sZeroRecords": "没有匹配结果",
    "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
    "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
    "sInfoPostFix": "",
    "sSearch": "搜索:",
    "sUrl": "",
    "sEmptyTable": "表中数据为空",
    "sLoadingRecords": "载入中...",
    "sInfoThousands": ",",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页",
        "sJump": "跳转"
    },
    "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
    }
};
$.initTable = {
    table1: function () {
        //表格初始化
        var table = $('#devieListId').DataTable({
            language:lang,  //提示信息
            autoWidth: false,  //禁用自动调整列宽
            stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true,  //隐藏加载提示,自行处理
            serverSide: true,  //启用服务器端分页
            searching: false,  //禁用原生搜索
            lengthChange: false,//是否允许用户改变表格每页显示的记录数
            orderMulti: false,  //启用多列排序
            order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
            columnDefs: [{
                "targets": 'nosort',  //列的样式名
                "orderable": false    //包含上样式名‘nosort’的禁止排序
            }],
            ajax: function (data, callback, settings) {
                // 封装请求参数
                var param = {};
                param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                // param.pageNum = data.start;//开始的记录序号
                param.pageNum = (data.start / data.length)+1;//当前页码

                // console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "GET",
                    url: origin + "manage/device/list_device.do",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    xhrFields: {
                        withCredentials: true
                    },
                    //dataType: "json",
                    success: function (result) {
                        console.log(result);
                        //setTimeout仅为测试延迟效果
                        setTimeout(function () {
                            if (result.status == 0) {
                                //封装返回数据
                                var returnData = {};
                                returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                                returnData.recordsTotal = result.data.total;//返回数据全部记录
                                returnData.recordsFiltered = result.data.total;//后台不实现过滤功能，每次查询均视作全部结果
                                returnData.data = result.data.list;//返回的数据列表
                                // console.log(returnData);
                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                callback(returnData);
                            } else {
                                alert(result.msg);
                            }
                        }, 200);
                    }
                });
            },
            //列表表头字段
            columns: [
                { "data": "deviceId" },
                { "data": "deviceName" },
                { "data": "createTime" },
                { "data": "updateTime" },
                { "data": null }
            ],
            columnDefs: [
                {
                    targets: 4,
                    render: function (data, type, row, meta) {
                        var row = meta.row;
                        var deviceId = data.deviceId;
                        var html = '<div class="">\n';
                        html += '<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal-update-device"'+
                                'onclick="getDevieInfo('+ row +')"><i class="fa fa-edit"> 编辑</i></button>\n';
                        html += '<button type="button" class="btn btn-primary btn-sm"><i class="fa fa-database"> 查看数据</i></button>\n';
                        html += '<button type="button" class="btn btn-warning btn-sm"><i class="fa fa-file"' +
                                'onclick="listDeviceFile('+ deviceId +')"> 查看文件</i></button>\n';
                        html += '<button type="button" class="btn btn-danger btn-sm"><i class="fa fa-trash"> 删除</i></button>\n';
                        html += '</div>';
                        return html;
                    }
                }
            ]

        });
        return table;
    },
    table2: function () {
        //表格初始化
        var table = $('#devieFileListId').DataTable({
            language:lang,  //提示信息
            autoWidth: false,  //禁用自动调整列宽
            stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true,  //隐藏加载提示,自行处理
            serverSide: true,  //启用服务器端分页
            searching: false,  //禁用原生搜索
            orderMulti: false,  //启用多列排序
            order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
            columnDefs: [{
                "targets": 'nosort',  //列的样式名
                "orderable": false    //包含上样式名‘nosort’的禁止排序
            }],
            ajax: function (data, callback, settings) {
                // 封装请求参数
                var param = {};
                param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                //param.pageNum = data.start;//开始的记录序号
                param.deviceId = deviceId;
                param.pageNum = (data.start / data.length)+1;//当前页码

                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "GET",
                    url: origin + "manage/device/list_files.do",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    xhrFields: {
                        withCredentials: true
                    },
                    //dataType: "json",
                    success: function (result) {
                        console.log(result);
                        //setTimeout仅为测试延迟效果
                        setTimeout(function () {
                            if (result.status == 0) {
                                //封装返回数据
                                var returnData = {};
                                returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                                returnData.recordsTotal = result.data.total;//返回数据全部记录
                                returnData.recordsFiltered = result.data.total;//后台不实现过滤功能，每次查询均视作全部结果
                                returnData.data = result.data.list;//返回的数据列表
                                // console.log(returnData);
                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                callback(returnData);
                            } else {
                                alert(result.msg);
                            }
                        }, 200);
                    }
                });
            },
            //列表表头字段
            columns: [
                { "data": "id" },
                { "data": "fileName" },
                { "data": "fileType" },
                { "data": "createTime" },
                { "data": null }
            ],
            columnDefs: [
                {
                    targets: 4,
                    render: function (data, type, row, meta) {
                        var id = data.id;
                        var html = '<a type="button" class="btn btn-success btn-block btn-sm" ' +
                            'href="'+ origin + 'manage/device/download/' +
                            id +'">下载</a>';
                        return html;
                    }
                }
            ]

        });
        return table;
    }
};
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
                var username = data.data.username;
                var createTime = data.data.createTime.substr(0,10);
                $("#usernameId").html(username);
                $("#helloId").html(username + '，你好啊！'
                    + '<small>注册时间：' + createTime + '</small>');
            },
            error: function () {
                alert('网络连接超时');
            }
        });
    },
    addDevice: function () {
        var result = -1;
        $.ajax({
            type: "post",
            url: origin + "manage/device/add_device.do",
            timeout: "5000",
            async: false,
            xhrFields: {
                withCredentials: true
            },
            data: $('#addDeviceFormId').serialize(),
            success: function(data) {
                if (data.status == 0) {
                    alert(data.msg);
                    $('#modal-add-device').modal('hide');
                    result = 0;
                } else {
                    alert(data.msg);
                    result = 1;
                }
            },
            error: function () {
                alert('网络连接超时');
            }
        });
        return result;
    },
    updateDevice: function () {
        var result = -1;
        $.ajax({
            type: "post",
            url: origin + "manage/device/update_device.do",
            timeout: "5000",
            async: false,
            xhrFields: {
                withCredentials: true
            },
            data: $('#updateDeviceFormId').serialize(),
            success: function(data) {
                if (data.status == 0) {
                    alert(data.msg);
                    $('#modal-update-device').modal('hide');
                    result = 0;
                } else {
                    alert(data.msg);
                    result = 1;
                }
            },
            error: function () {
                alert('网络连接超时');
            }
        });
        return result;
    }

};

//编辑按钮 获取当前行信息
var getDevieInfo = function(row) {
    var data = tableData1.row(row).data();
    // console.log(data);
    var form = $('#updateDeviceFormId');
    form.find('input[name="deviceId"]').val(data.deviceId);
    form.find('input[name="deviceName"]').val(data.deviceName);
};
//查看文件按钮
var listDeviceFile = function(id) {
    deviceId = id;
    // console.log(id);
    table1.hide();
    table2.show();
    if (tableData2 == null) {
        tableData2 = $.initTable.table2();
    } else {
        tableData2.draw();
    }
};

$(function () {

    //获取用户信息
    $.func.getUserInfo();

    //初始化table1的数据
    tableData1 = $.initTable.table1();
    
    // deviceId = 1;
    // var tableData2 = $.initTable.table2();
    // table2.show();

    //添加设备
    $('#addDeviceButtonId').click(function () {
        if ($.func.addDevice() == 0) {
            tableData1.draw();
        }
    });
    //更新设备信息
    $('#updateDeviceButtonId').click(function () {
        if ($.func.updateDevice() == 0) {
            tableData1.draw();
        }
    });

})
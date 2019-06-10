$(function () {

    var usernamePattern = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){3,20}$/;
    var phonePattern = /^\d{11}$/;
    var passwordPattern = /^([a-zA-Z0-9]|[._&*#@]){6,20}$/;

    var usernameId = $('#usernameId');
    var phoneId = $('#phoneId');
    var passwordId = $('#passwordId');
    var passwordId1 = $('#passwordId1');
    var checkBoxId = $('#checkboxId');

    $.checkFromServer = function(str, type) {
        var result = false;
        $.ajax({
            type: "post",
            url: "user/check_valid.do",
            timeout: "5000",
            data: 'str=' + str + '&type=' + type,
            async: false,
            error: function () {
                alert("与服务器连接错误");
            },
            success: function (data) {
                if (data.status == 0) {
                    result = true;
                } else {
                    result = false;
                }
            }
        });
        return result;
    };

    $.checkValid = {
        username: function () {
            if (!usernamePattern.exec(usernameId.val())) {
                // alert("用户名为5到20个字符，以字母开头，仅限大小写字母和数字！")
                usernameId.parent().attr("class", "form-group has-feedback has-error");
                usernameId.siblings('span[class="help-block"]').html(
                    '<i class="fa fa-times-circle-o">' +
                    '格式为5到20个字符，以字母开头，仅限大小写字母和数字！</i>');
                return false;
            } else {
                if ($.checkFromServer(usernameId.val(), 'username')) {
                    usernameId.parent().attr("class", "form-group has-feedback");
                    usernameId.siblings('span[class="help-block"]').html('');
                    return true;
                } else {
                    usernameId.parent().attr("class", "form-group has-feedback has-error");
                    usernameId.siblings('span[class="help-block"]').html(
                        '<i class="fa fa-times-circle-o">' +
                        '用户名已存在!</i>');
                    return false;
                }
            }

        },
        phone: function () {
            if (!phonePattern.exec(phoneId.val())) {
                // alert("手机号码格式有误！");
                phoneId.parent().attr("class", "form-group has-feedback has-error");
                phoneId.siblings('span[class="help-block"]').html(
                    '<i class="fa fa-times-circle-o">' +
                    '手机号为11位的数字！</i>');
                return false;
            } else {
                if ($.checkFromServer(phoneId.val(), 'phone')) {
                    phoneId.parent().attr("class", "form-group has-feedback");
                    phoneId.siblings('span[class="help-block"]').html('');
                    return true;
                } else {
                    phoneId.parent().attr("class", "form-group has-feedback has-error");
                    phoneId.siblings('span[class="help-block"]').html(
                        '<i class="fa fa-times-circle-o">' +
                        '该手机号已注册！</i>');
                    return false;
                }

            }

        },
        password: function () {
            if (!passwordPattern.exec(passwordId.val())) {
                // alert("密码为6到20个字符，仅限大小写字母和数字和._&*#@");
                passwordId.parent().attr("class", "form-group has-feedback has-error");
                passwordId.siblings('span[class="help-block"]').html(
                    '<i class="fa fa-times-circle-o">' +
                    '密码为6到20个字符，仅限大小写字母和数字和._&*#@</i>');
                return false;
            } else {

                passwordId.parent().attr("class", "form-group has-feedback");
                passwordId.siblings('span[class="help-block"]').html('');
                return true;
            }

        },
        password1: function () {
            if (passwordId1.val() != passwordId.val()) {
                // alert("两次密码不一致！");
                passwordId1.parent().attr("class", "form-group has-feedback has-error");
                passwordId1.siblings('span[class="help-block"]').html(
                    '<i class="fa fa-times-circle-o">' +
                    '两次密码不一致！</i>');
                return false;
            } else {

                passwordId1.parent().attr("class", "form-group has-feedback");
                passwordId1.siblings('span[class="help-block"]').html('');
                return true;
            }

        },
        checkBox: function () {
            if (!checkBoxId.is(":checked")) {
                alert("请同意用户条款");
                return false;
            }
            return true;
        }
    };

    //监听事件
    usernameId.blur(function (){$.checkValid.username()});
    phoneId.blur(function (){$.checkValid.phone()});
    passwordId.blur(function (){$.checkValid.password()});
    passwordId1.blur(function (){$.checkValid.password1()});


    $("#registerButtonId").click(function () {
        if ($.checkValid.username() &&
            $.checkValid.phone() &&
            $.checkValid.password() &&
            $.checkValid.password1() &&
            $.checkValid.checkBox()) {

            //提交表单
            $.ajax({
                type: "post",
                url: "user/register.do",
                timeout: "5000",
                data: $("#registerFormId").serialize(),
                async: true,
                error: function (textStatus, data) {
                    alert("注册失败，服务器错误");
                },
                success: function (data) {
                    if (data.status == 0) {
                        alert("注册成功");
                        location.href = "./login";
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    });
})
define(function(require, exports, module) {
    var $ = require('jquery');
    require('datepicker');
    require('bootstrap');

    var ajax = require('./ajax');
    var list=require("./list");
    var option=require("./option");
    var loading = require("./loading");
    var form = require("./form");
    $(document).ready(function() {
        //导航条初始化
        $('#nav_case_create')[0].classList.add("active");
        $('#nav_case_query')[0].classList.remove("active");
        //init date picker
        $('#occurDate').datepicker({format: 'yyyy-mm-dd'});
        $('#finishDate').datepicker({format: 'yyyy-mm-dd'});

        //init option content
        option.ajax("caseType", "/login/loadOption", "", "option", 0);
        option.ajax("status", "/login/loadOption", "", "option", 0);

        //提交按钮的点击操作
        $('#submit').bind('click', function () {
            //提交表单
            form.defaultSubmit("case_create","","",null,function (data) {
                //callback 显示创建成功的消息
                form.success("created!");
                //提交后清理下表单
                form.clean("case_create");
            });
        });
    });






});
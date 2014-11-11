define(function(require, exports, module) {
    var $ = require('jquery');
    require('datepicker');


    var ajax = require('./ajax');
    var list=require("./list");
    var option=require("./option");
    var loading = require("./loading");
    var dialog = require("./dialog");
    var form = require("./form");
    var bootstrap = require('bootstrap');
    $(document).ready(function() {

        //导航条初始化
        $('#nav_case_create')[0].classList.remove("active");
        $('#nav_case_query')[0].classList.add("active");

        //init empty table
        list.empty("financecase_list", "NoRowsTemplate");
        //init date picker
        $('#occurDate').datepicker({format: 'yyyy-mm-dd'});
        $('#finishDate').datepicker({format: 'yyyy-mm-dd'});

        //init option content
        option.ajax("caseType", "loadOption.do", "", "option", 0);
        option.ajax("status", "loadOption.do", "", "option", 0);

        //search payplan list
        $('#search').bind('click', function () {
        	//list_model,financecase_list这些参数去ftl文件里面找
            list.init("list_model", "financecase_list", "pagination_bar", "", "", "NoRowsTemplate",
                      "case_search", null, 1,  function(data){

            });

        });

        //将初始状态变为处理中
        $('.proc-action').live('click', function () {
            var caseId = $(this).attr("case-id");
            dialog.init("Case处理中", "确认处理该Case吗？", "确认", function () {
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "/case/ajax/procFinanceCase",
                    data: {
                        "fcId": caseId
                    },
                    success: function (data) {
                        if (data.code != 200) {
                            form.fail("系统异常，请稍后再试！");
                            return;
                        }
                        if (data.msg['actionResult'] == "success") {
                            form.success(data.msg["message"]);
                            $('#search').trigger("click");
                        } else {
                            form.fail(data.msg["message"]);
                        }

                    }
                });
                dialog.hide();
            });
        });

        //完成对case的处理，这个会弹窗，需要把所有信息补全，先按照这个思路做吧
        //这次不用live绑定，据说不推荐，需要被逐步淘汰了。
        //看来还是要用live，因为要用到this,用on方法我不会获取this
        $( document ).on( "click", ".done-action", function() {
            var caseId = $(this).attr("case-id");
            buildDoneCase(caseId);
            $('#doneDialog').modal("show");
        } );
        /*$('.done-action').live('click', function () {
            var caseId = $(this).attr("case-id");
            buildDoneCase(caseId);
            $('#doneDialog').modal("show");
        });*/

        //接着上面写，对话框已经创建好，下面写对话框的提交按钮的触发事件
        $('#done_submit').live('click',function(){
            form.defaultSubmit("case_done","","",null,function(data){
                //callback 显示更新成功的消息
                form.success("Case已处理");
                $('#doneDialog').modal("hide");
                $('#search').trigger("click");
            });
        });
    });

    function buildDoneCase(caseId) {
        //给完成日期空间添加datepicker
        $('#done_finishDate').datepicker({format: 'yyyy-mm-dd'});
        var fcId = caseId+"id";
        var desc = caseId+"desc";
        var reso = caseId+"reso";
        var type = caseId+"type";
        var impa = caseId+"impa";
        var occur = caseId+"occur";
        var mail = caseId+"mail";
        /*$('#done_description').val("hello");
        var descobj = $('#'+desc);
        var descvalue = descobj.text();*/

        $('#done_fcId').val($('#'+fcId).text());
        $('#done_description').val($('#'+desc).text());
        $('#done_resolution').val($('#'+reso).text());
        $('#done_caseType').val($('#'+type).text());
        $('#done_occurDate').val($('#'+occur).text());
        $('#done_impact').val($('#'+impa).text());
        $('#done_mailTitle').val($('#'+mail).text());
        $('#done_status').val("已处理");
    };
});
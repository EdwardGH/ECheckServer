define(function(require, exports, module) {
    var $ = require('jquery');
    require('datepicker');
    require('bootstrap');
    require('upload');
    var ajax = require('./ajax');
    var list=require("./list");
    var option=require("./option");
    var loading = require("./loading");
    var dialog = require("./dialog");
    $(document).ready(function() {

        $('body').on('click', function (e) {
            $('[data-toggle="popover"]').each(function() {
                if (!$(this).is(e.target)
                    && $(this).has(e.target).length === 0
                    && $('.popover').has(e.target).length === 0) {
                    $(this).popover('hide');
                }
            });
        });

        //init empty table
        list.empty("payorder_list", "NoRowsTemplate");


        $(".paid-time").hide();
        $(".sendback-time").hide();
        //init option content
        option.ajax("status", "/exchange/ajax/loadPOStatusOption", "", "option", 0);
        option.ajax("businesstype", "/exchange/ajax/loadBusinessTypeOption", "", "option", 0);
        //init date picker
        $('#addbegindate').datepicker({format: 'yyyy-mm-dd'});
        $('#addenddate').datepicker({format: 'yyyy-mm-dd'});

        //search payplan list
        $('#search').bind('click', function () {
            $(".paid-time").hide();
            $(".sendback-time").hide();
            var status = $("#status").val();
            if(status == 0){
                $(".paid-time").show();
                $(".sendback-time").show();
            } else if(status == 3){
                $(".paid-time").show();
            } else if(status == 4){
                $(".sendback-time").show();
            }

            list.init("list_model", "payorder_list", "pagination_bar", "", "", "NoRowsTemplate", "payorder_search", null, 1,  function(data){
                $(".recordCount").html(data.payOrderModel.recordCount);
                $(".totalAmount").html(data.totalAmount);
                if (data.status == 3) {
                    $("#btn-import").show();
                } else {
                    $("#btn-import").hide();
                }
                if (data.status == 1 || data.status == 2) {
                    $('#order-export').show();
                } else {
                    $('#order-export').hide();
                }
                if (data.status == 2) {
                    $('#pay-success').show();
                } else {
                    $('#pay-success').hide();
                }

               if (status == 1 || status == 0 || status == 3 || status == 4) {
                    $('.selected-payorder').hide();
                    $('#select-all').hide();
                }else{
                    $('.selected-payorder').show();
                    $('#select-all').show();
                };

                ENV.data._allRecords = false;
                $('#select-all').attr('checked', false);
                $('#select-all-pageinfo').hide();
                $('#select-cur-pageinfo').hide();
                $(".bank-show").popover({trigger:'click',animation:true,html:true,title:"银行帐号信息",content:function(){return "<tr><th style='text-align:left; min-width:60px;'>开户名：</th><td style='text-align:left'>"+$(this).attr('bankAccountName')+"</td></tr><tr><th style='text-align:left; min-width:60px;'>开户行：</th><td style='text-align:left'>"+$(this).attr('bankName')+"</td></tr><tr><th style='text-align:left; min-width:60px;'>帐号：</th><td style='text-align:left'>"+$(this).attr('bankAccountNo')+"</td></tr>"}});
                
                if (data.payOrderModel.recordCount == 0) {
                    $('#order-export').hide();
                    $('#pay-success').hide();
                    $('.selected-payorder').hide();
                    $('#select-all').hide();
                    $("#btn-import").hide();
                };
            });
        });

        //导出支付
        $('#order-export').click(function(){
            var url = 'orderexport?'
                +'businessType=' + $('#businesstype').val()
                + '&addBeginDate=' + $('#datebegin').val()
                + '&addEndDate=' + $('#addenddate').val()
                + '&status=' + $('#status').val()
                + '&payCode=' + $('#paycode').val();

            window.open(url,'_blank');

            setTimeout(function(){
                $('#status').val(2)
                $('#search').trigger("click");
                                }, 3000);           
        });

        //确认支付
        $('#pay-success').click(function() {
            loading.show();
            var param = {};
            param.allRecords = ENV.data._allRecords;

            var poIds = [];
            $.each($('.selected-payorder'), function (index, el) {
                var recJqObj = $(el)
                if (recJqObj[0].checked) {
                    poIds.push(recJqObj.attr("po-id"));
                }
            });
            if (poIds.length === 0) {
                loading.hide();
                dialog.init("确认支付", "请勾选需要确认支付成功的记录！");
                return false;
            }

            if (ENV.data._allRecords) {
                param.businessType = $('#businesstype').val();
                param.payCode = $('#paycode').val();
                param.status = $('#status').val();
                param.addBeginDate = $('#addbegindate').val();
                param.addEndDate = $('#addenddate').val();
            } else {
                param.poIds = poIds.join(',');
            }

            $.ajax({
                type: "POST",
                url: "/exchange/ajax/payconfirm",
                data: param
            }).done(function (data) {
                loading.hide();
                if (data.code == 200) {
                    dialog.init("确认支付", "确认成功" + data.msg.count + "条");
                    $('#status').val(2);
                    $('#search').trigger("click");
                } else {
                    dialog.init("确认支付", "提交失败");
                }
            });
        });


        //勾选全部
        $('#select-all').bind('click', function () {
            var checked = $('#select-all')[0].checked;
            $.each($('.selected-payorder'), function (index, el) {
                $(el).attr('checked', checked);
            });

            if (checked) {
                $('#select-all-pageinfo').show();
            } else {
                $('#select-all-pageinfo').hide();
            }
        });

        //勾选全部
        $('#select-all-page').bind('click', function () {
            ENV.data._allPage = true;
            $('#select-all-pageinfo').hide();
            $('#select-cur-pageinfo').show();
        });

        //取消勾选
        $('#select-cur-page').bind('click', function () {
            ENV.data._allPage = false;
            $('#select-cur-pageinfo').hide();
            $('#select-all-pageinfo').hide();
            $('#select-all').attr('checked', false);
            $.each($('.selected-payorder'), function (index, el) {
                $(el).attr('checked', false);
            });
        });

        $('#select-add-date').bind('change', function () {
            changeAddDateSelect();
        });

        $("#chooseFile").on("click", function(){
            $("#import-file").trigger("click")
        });

        $("#import-file").on('change', function (event, files, label) {
            $('input[name="upload-file"]').val(this.value);
        });

        $('#import-file').fileupload({
            url: "../ajax/importRefund",
            dataType: 'json',
            add: function (e, data) {
                $('#confirm-import').click(function () {
                    $('#import-refund').modal('hide');
                    loading.show();
                    data.submit();
                });
            },
            done: function (e, data) {
                loading.hide();
                $('#refund-result').modal('show');
                var failedResultElem = $("#refund-result .failed-result");
                failedResultElem.empty();
                var successResultElem = $("#refund-result .success-result");
                successResultElem.empty();
                if (data.result.code != 200) {
                    failedResultElem.html("<span class=\"fail-msg\">系统异常，请稍后再试</span>");
                    return;
                }
                if (data.result.excelInvalidMsg) {
                    failedResultElem.html("<span class=\"fail-msg\">" + data.result.excelInvalidMsg + "</span>");
                    return;
                }

                if (data.result.msg.successCount > 0) {
                    successResultElem.html(
                            "退票成功<span class=\"number-char\">" +
                            data.result.msg.successCount +
                            "</span>条，总金额<span class=\"number-char\">" +
                                data.result.msg.refundTotalAmount +
                            "</span>元。");

                }

                var errorMsg = "<span class=\"fail-msg\">退票失败记录</span>" +
                    "<table class=\"table result-table\"><thead><tr><th>失败原因</th><th>失败的付款单号</th></tr></thead>";
                var hasError = false;
                if (data.result.invalidRefundMap.invalidIds) {
                    hasError = true;
                    errorMsg += "<tr><td>付款单号格式不对！正确格式为：P+数字。</td><td>"
                        + data.result.invalidRefundMap.invalidIds + "</td></tr>";
                }
                if (data.result.invalidRefundMap.duplicateIds) {
                    hasError = true;
                    errorMsg += "<tr><td>付款单号有重复！</td><td>" +
                        data.result.invalidRefundMap.duplicateIds + "</td></tr>";
                }
                if (data.result.invalidRefundMap.notFoundRefundIds) {
                    hasError = true;
                    errorMsg += "<tr><td>付款单号不存在！</td><td>" +
                        data.result.invalidRefundMap.notFoundRefundIds + "</td></tr>";
                }
                if (data.result.invalidRefundMap.statusErrorIds) {
                    hasError = true;
                    errorMsg += "<tr><td>付款单号状态错误！</td><td>" +
                        data.result.invalidRefundMap.statusErrorIds + "</td></tr>";
                }

                if (hasError) {
                    errorMsg += "</table>";
                    $("#refund-result .failed-result").html(errorMsg);
                }
                $('#search').trigger("click");
            }
        });
    });


    function changeAddDateSelect(){
        var dateMap = initDateMap();
        var v = $('#select-add-date').val();
        var s = dateMap[v || '0']['s'];
        var e = dateMap[v || '0']['e'];
        $('#addbegindate').val(s);
        $('#addenddate').val(e);
    };

    function initDateMap(){
        var now = new Date();
        var dateMap = {
            '99': {
                s: '',
                e: ''
            },
            '0': {
                s: '',
                e: now.defaultFormat()
            },
            '1': {
                s: now.defaultFormat(),
                e: now.defaultFormat()
            },
            '2': {
                s: now.pre(1).defaultFormat(),
                e: now.pre(1).defaultFormat()
            },
            '3': {
                s: now.pre(2).defaultFormat(),
                e: now.pre(2).defaultFormat()
            }
        };
        return dateMap;
    };

});
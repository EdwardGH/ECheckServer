define(function(require, exports, module) {
    var $ = require('jquery');
    require('datepicker');
    require('bootstrap');
    require('upload');
    var list = require("./list");
    var form = require("./form");
    var option = require("./option");
    var loading = require("./loading");
    var dialog = require("./dialog");
    var date = require("./date");
    var autoValidator = require("./autoValidator");

    $(document).ready(function () {
        list.empty("invoice_list", "NoRowsTemplate");

        option.ajax("q-businessType", "/settle/ajax/loadBusinessTypeOption", "", "option", 0);
        option.ajax("q-status", "/settle/ajax/loadInvoiceStatusOption", "", "option", 0);
        option.ajax("q-operationType", "/settle/ajax/loadOperationTypeOption", "", "option", 0);
        option.ajax("q-invoiceType", "/settle/ajax/loadInvoiceTypeOption", "", "option", 0);

        $('#q-invoice-begin-time').datepicker({format: 'yyyy-mm-dd'});
        $('#q-invoice-end-time').datepicker({format: 'yyyy-mm-dd'});
        $('#q-release-begin-time').datepicker({format: 'yyyy-mm-dd'});
        $('#q-release-end-time').datepicker({format: 'yyyy-mm-dd'});


        $('#q-businessType').on('change', function () {
            var businessTypeVal = $(this).val();
            if (businessTypeVal == 1){
                $('#q-companyId').html("<option value='0'>请选择开票公司</option>");
                $('#q-companyId').append("<option value='2'>汉海上海</option>");
            } else if (businessTypeVal == 5) {
                $('#q-companyId').html("<option value='0'>请选择开票公司</option>");
                $('#q-companyId').append("<option value='1'>汉涛</option>");
                $('#q-companyId').append("<option value='2'>汉海上海</option>");
                $('#q-companyId').append("<option value='3'>汉海北京</option>");
                $('#q-companyId').append("<option value='4'>汉海广州</option>");
            } else {
                $('#q-companyId').html("<option value='0'>请选择开票公司</option>");
            }
        });

        $('#search').on('click', function () {
            list.init("list_model", "invoice_list", "pagination_bar", "", "", "NoRowsTemplate", "ro-search-form", null, 1,  function(data) {
                $(".recordCount").html(data.invoiceModel.recordCount);
                $(".totalAmount").html(data.totalAmount);
                if (data.status == 1) {
                    $('#invoice-export').show();
                    $('#btn-invoice-import').hide();
                } else if (data.status == 2) {
                    $('#invoice-export').show();
                    $('#btn-invoice-import').show();
                } else {
                    $('#invoice-export').hide();
                    $('#btn-invoice-import').hide();
                }
                ENV.data._allRecords = false;
                $('#select-all-in-page').attr('checked', false);
                $('#select-all-pageinfo').hide();
                $('#select-cur-pageinfo').hide();
            });
        });

        $('#select-all-in-page').bind('click', function () {
            var checked = $('#select-all-in-page')[0].checked;
            $.each($('.selected-invoice'), function (index, el) {
                $(el).attr('checked', checked);
            });

            if (checked) {
                $('#select-all-pageinfo').show();
            } else {
                $('#select-all-pageinfo').hide();
            }
            ENV.data._allRecords = false;
        });

        //选择所有
        $('#select-all-page').bind('click', function () {
            ENV.data._allRecords = true;
            $('#select-all-pageinfo').hide();
            $('#select-cur-pageinfo').show();
        });

        //取消勾选
        $('#select-cur-page').bind('click', function () {
            ENV.data._allRecords = false;
            $('#select-cur-pageinfo').hide();
            $('#select-all-pageinfo').hide();
            $('#select-all-in-page').attr('checked', false);
            $.each($('.selected-invoice'), function (index, el) {
                $(el).attr('checked', false);
            });
        });

        $('#invoice-export').click(function() {
            loading.show();
            var param = {};
            param.allRecords = ENV.data._allRecords;

            var ids = [];
            $.each($('.selected-invoice'), function (index, el) {
                var recJqObj = $(el)
                if (recJqObj[0].checked) {
                    ids.push(recJqObj.attr("invoiceId"));
                }
            });
            if (ids.length === 0) {
                loading.hide();
                dialog.init("开票导出", "请勾选需要导出的记录！");
                return false;
            }

            var url="/settle/ajax/exportAllInvoice?"
                +"businessType="+$('#q-businessType').val()
                +"&cityID="+$('#q-cityId').val()
                +"&companyId="+$('#q-companyId').val()
                +"&status="+$('#q-status').val()
                +"&operationType="+$('#q-operationType').val()
                +"&invoiceType="+$('#q-invoiceType').val()
                +"&invoiceDateBegin="+$('#q-invoice-begin-time').val()
                +"&invoiceDateEnd="+$('#q-invoice-end-time').val()
                +"&invoiceTaxNos="+$('#q-invoiceTaxNos').val()
                +"&releaseDateBegin="+$('#q-release-begin-time').val()
                +"&releaseDateEnd="+$('#q-release-end-time').val();
            if (!ENV.data._allRecords) {
                url="/settle/ajax/exportInvoice?invoiceIds="+ids.join(',');
            }

            window.open(url,'_blank');
            loading.hide();
            setTimeout(function(){
                $('#status').val(1)
                $('#search').trigger("click");
            }, 3000);

        });

        $("#chooseFile").on("click", function(){
            $("#import-file").trigger("click")
        });

        $("#import-file").on('change', function (event, files, label) {
            $('input[name="upload-file"]').val(this.value);
        });

        $('#import-file').fileupload({
            url: "../ajax/importInvoice",
            dataType: 'json',
            add: function (e, data) {
                // 删除之前注册的事件处理，防止重复提交
                $('#confirm-import').unbind("click");
                $('#confirm-import').click(function () {
                    var uploadFileField = $("input[name='upload-file']");
                    if (!autoValidator.validate(uploadFileField[0])) {
                        errorForUpload(autoValidator.errorMsg());
                        uploadFileField.addClass("error-input");
                        return;
                    }
                    uploadFileField.removeClass("error-input");
                    $('#import-invoice').modal('hide');
                    loading.show();
                    data.submit();
                });
            },
            done: function (e, data) {
                $("input[name='upload-file']").val("");
                loading.hide();
                var failedResultElem = $("#import-invoice-result .failed-result");
                failedResultElem.empty();
                var successResultElem = $("#import-invoice-result .success-result");
                successResultElem.empty();
                if (data.result.code != 200) {
                    failedResultElem.html("<span class=\"fail-msg\">系统异常，请稍后再试</span>");
                    $('#import-invoice-result').modal('show');
                    return;
                }
                if (data.result.invalidFileMsg) {
                    failedResultElem.html("<span class=\"fail-msg\">" + data.result.invalidFileMsg + "</span>");
                    $('#import-invoice-result').modal('show');
                    return;
                }

                if (data.result.msg.totalCount > 0) {
                    successResultElem.html(
                            "导入成功<span class=\"number-char\">" +
                            data.result.msg.totalCount +
                            "</span>条，总金额<span class=\"number-char\">" +
                            data.result.msg.totalAmount +
                            "</span>元。");

                }

                var checkErrorFields = {
                    "emptyFieldRows": "字段为空",
                    "invalidAmountRows": "金额格式错误",
                    "invalidInvoiceIdRows": "开票ID错误",
                    "invalidTaxNoRows": "金税发票错误",
                    "invalidReverseTaxNoRows": "冲销发票错误",
                    "invalidDateRows": "开票日期错误",
                    "duplicateRows": "字段重复",
                    "updateFailedRows": "导入失败"
                };
                var errHtml = "";
                for (errField in checkErrorFields) {
                    if (data.result.msg[errField]) {
                        errHtml += generateErrHtml(data.result.msg[errField], checkErrorFields[errField]);
                    }
                }
                if (errHtml.length > 0) {
                    failedResultElem.html("<div class='fail-msg' style='font-weight:bold'>导入失败记录</div><br>" + errHtml);
                }
                $('#import-invoice-result').modal('show');
            }
        });


        $('#q-select-invoice-time').on('change', function () {
            changeInvoiceTimeSelect();
        });

        $('#q-select-release-time').on('change', function () {
            changeReleaseTimeSelect();
        });

        $('.cancel-action').live('click', function () {
            var invoiceId = $(this).attr("invoice-id");
            if (!invoiceId) {
                errorForUpload("请选择要作废的发票！");
                return;
            }

            dialog.init("作废发票", "确认要作废此发票吗？", "确认", function () {
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "../ajax/procFinanceCase",
                    data: {
                        "invoiceId": invoiceId
                    },
                    success: function (data) {
                        if (data.code != 200) {
                            errorForUpload("系统异常，请稍后再试！");
                            return;
                        }
                        if (data.msg['actionResult'] == "success") {
                            showTips(data.msg["message"]);
                            $('#search').trigger("click");
                        } else {
                            errorForUpload(data.msg["message"]);
                        }

                    }
                });
                dialog.hide();
            });
        });
    });

    function generateErrHtml(errItems, title) {
        var errorContent = "<span class='fail-msg'>" + title + "</span>";
        errorContent += "<table class='table result-table'><tbody>";
        for (var i = 0; i < errItems.length; ++i) {
            errorContent += "<tr><td>" + errItems[i] + "</td></tr>";
        }
        errorContent += "</tbody></table>";
        return errorContent;
    }

    function changeInvoiceTimeSelect(){
        var dateMap = initDateMap();
        var v = $('#q-select-invoice-time').val();
        var s = dateMap[v || '0']['s'];
        var e = dateMap[v || '0']['e'];
        $('#q-invoice-begin-time').val(s);
        $('#q-invoice-end-time').val(e);
    }

    function changeReleaseTimeSelect(){
        var dateMap = initDateMap();
        var v = $('#q-select-release-time').val();
        var s = dateMap[v || '0']['s'];
        var e = dateMap[v || '0']['e'];
        $('#q-release-begin-time').val(s);
        $('#q-release-end-time').val(e);
    }

    function errorForUpload(error) {
        var errorDiv = $(".tips-container .error:first")[0];
        errorDiv.innerHTML = error;
        errorDiv.style.display = 'block';
        setTimeout(hideErrorForUpload, 10000);
    }

    function hideErrorForUpload() {
        var errorDiv = $(".tips-container .error:first")[0];
        errorDiv.innerHTML = "";
        errorDiv.style.display = 'none';
    }

    function showTips(message) {
        var tipsDiv = $(".tips-container .tips:first")[0];
        tipsDiv.innerHTML = message;
        tipsDiv.style.display = 'block';
        setTimeout(hideTips, 10000);
    }

    function hideTips() {
        var tipsDiv = $(".tips-container .tips:first")[0];
        tipsDiv.innerHTML = "";
        tipsDiv.style.display = 'none';
    }

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
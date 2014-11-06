//form submit function
define(function(require, exports, module) {

    var ajax=require("./ajax");
    var $=require("jquery");
    var autoValidator=require("./autoValidator");
    module.exports= {
        //Used to be called form_submit, generate the params and do the ajax, callback the function.
        submit: function (formId, context, callback) {
            var formDiv = document.getElementById(formId);
            var values = formDiv.getElementsByClassName("form_value");
            var i = 0;
            var params = "";
            for (i = 0; i < values.length; i++) {
                if (values[i].type == "radio" && !values[i].checked) {
                    continue;
                }
                if (values[i].tagName == "span" || values[i].tagName == "SPAN") {
                    params += "&" + values[i].name + "=" + values[i].getAttribute("value");
                } else {
                    params += "&" + values[i].name + "=" + values[i].value;
                }
            }
            ajax.post(formDiv.getAttribute("form_url"), params, function (data) {
                callback(data);
            });
        },

        //Used to be called the same name!
        defaultSubmit: function (formId, context, params , validator, callback, redirect) {
            if (!this.validate(formId,validator)) {
                return;
            }
            this.submit(formId, context, function (data) {
                if (callback != null) {
                    callback(data);
                }
                if (redirect != null) {
                    window.location.href = url;
                }
            });
        },

        validate: function (formId,validator) {
            var fields = $("#"+formId+" .form_value");
            var j = 0;
            for (j = 0; j < fields.length; j++) {
                var field = fields[j];
                if(field.classList.contains("error-input")) {
                    field.classList.remove("error-input");
                }
            }
            for (j = 0; j < fields.length; j++) {
                var field = fields[j];
                if (!autoValidator.validate(field)) {
                    error(autoValidator.errorMsg());
                    if(!field.classList.contains("error-input")) {
                        field.classList.add("error-input");
                    }
                    return false;
                }
                if (field.getAttribute("vType") == "custom") {
                    var s = "validator." + field.name + "(field.value)";
                    if (!eval(s)) {
                        error(validator.errorMsg());
                        if(!field.classList.contains("error-input")) {
                            field.classList.add("error-input");
                        }
                        return false;
                    }
                }
            }
            return true;
        },

        success:function(content){
            var successDiv = $(".tips-container .tips")[0];
            successDiv.innerHTML = content;
            successDiv.style.display = 'block';
            setTimeout(hideSuccess, 3000);
        },

        fail:function(content){
            error(content);
        },

        //Used to be called default_clean_form, clean the "form_clean" input in the form, and set to default if necessary.
        clean: function (formId) {
            var fields = $("#"+formId+" .form_value");;
            var i;
            for (i = 0; i < fields.length; i++) {
                /*if (fields[i].type == "radio") {
                    if (fields[i].className.indexOf("form_default") != -1) {
                        fields[i].checked = true;
                        break;
                    }
                } else if (fields[i].tagName == "select" || fields[i].tagName == "SELECT") {
                    //TODO
                    var options = fields[i].options;
                    var j;
                    for (j = 0; j < options.length; j++) {
                        if (options[j].className.indexOf("form_default") != -1) {
                            options[j].selected = true;
                            break;
                        }
                    }
                } else {
                    fields[i].value = "";
                }*/
                //上面注释掉的是以前的代码，不符合我的需求，重新写了下面的代码
                if(fields[i].tagName == "select" || fields[i].tagName == "SELECT"){
                    var options = fields[i].options;
                    options[0].selected = true;
                }
                else if(fields[i].tagName == "input" || fields[i].tagName == "INPUT"){
                    fields[i].value = "";
                }
                else if(fields[i].tagName == "textarea" || fields[i].tagName == "TEXTAREA"){
                    fields[i].value = "";
                }
            }
        }
    };

    function error (error) {
        var errorDiv = $(".tips-container .error:first")[0];
        errorDiv.innerHTML = error;
        errorDiv.style.display = 'block';
        setTimeout(hideError, 3000);
    }

    function hideError () {
        var errorDiv = $(".tips-container .error:first")[0];
        errorDiv.innerHTML = "";
        errorDiv.style.display = 'none';
    }

    function hideSuccess () {
        var successDiv = $(".tips-container .tips")[0];
        successDiv.innerHTML = "";
        successDiv.style.display = 'none';
    }
});

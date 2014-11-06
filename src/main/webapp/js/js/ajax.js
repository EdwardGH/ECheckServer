//ajax request common function, for post request.
define(function(require, exports, module) {
    var $=require("jquery");

    module.exports={
        //Used to be call ajax_pure, send http post request to any url
        post:function(url,params,callback,errorCallback){
            $.ajax({
                type: "post",
                dataType: "json",
                url : url,
                data: params,
                success: function(data){
                    if(callback){
                        callback(data);
                    }
                },
                error: function(jqXHR, textStatus, errorThrown){
                    if(errorCallback){
                        errorCallback();
                    }
                    alert("error@ajax_post:"+errorThrown);
                }
            });
        },

        get:function(url,callback){
            $.ajax({
                type: "get",
                url : url,
                success: function(data){
                    if(callback){
                        callback(data);
                    }
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("error@ajax_get:"+errorThrown);
                }
            });
        }
    };
});


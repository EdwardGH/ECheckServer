(function() {
    var getBase = function() {
        return '../';
    };

    if (typeof ENV === 'undefined') {
        ENV = {};
    }

    seajs.config({
        debug: ENV.debug
    });

    seajs.config(
        {
            base: getBase(),

            alias: {
                "jquery": "js/lib/jquery/src/jquery.js",
                "$": "js/lib/jquery/src/jquery.js",
                /*"jquery": "js/bootstrap/jquery-1.11.1.js",
                "$": "js/bootstrap/jquery-1.11.1.js",*/
                /*"upload": "js/lib/jquery-upload/jquery.fileupload.js",*/
                "datepicker": "js/lib/date-picker/js/bootstrap-datepicker.js",
                "timepicker": "js/lib/timepicker/js/bootstrap-timepicker.js",
                /*"datetimepicker":"js/bootstrap/bootstrap-datetimepicker.js",
                "datetimepickerZh":"js/bootstrap/bootstrap-datetimepicker.zh-CN.js",*/
                "bootstrap": "js/bootstrap/bootstrap.js",
                "nav": "js/lib/theme/nav.js",
                "alert": "js/lib/alert/alert.js"
            }
        }
    );

})()
<#include "/WEB-INF/pages/util/pageNavigation.ftl" />
<#setting number_format="#.##"/>
<#assign basePath=request.contextPath>
<head>
    <title>电力巡检</title>
    <script charset="utf-8" src="${basePath}/js/seajs/dist/sea.js"></script>
    <script charset="utf-8" src="${basePath}/js/sea-config.js"></script>
    <!-- Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="${basePath}/js/bootstrap/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="${basePath}/js/lib/date-picker/css/datepicker.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/css/caserecord.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/css/common.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/css/base.css" type="text/css">
</head>
<body>
<#include "/WEB-INF/pages/common/tips.ftl">
<div class="main_container">
    <div class="nav_container">
	<#include "/WEB-INF/pages/common/nav.ftl">
    </div>
    <div class="panel_container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">Cases</h4>
            </div>
            <div class="panel-body">
                <div class="form-horizontal" id="case_search">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">类型</label>
                        <div class="col-sm-4">
                            <select id="caseType" name="caseType" class="form-control form_value"
                                    <#--validate="ne[0]" error_msg="ne[0]:请选择类型"-->>

							</select>
                        </div>
                        <label  class="col-sm-1 control-label">状态</label>
                        <div class="col-sm-4">
                            <select id="status" name="status" class="form-control form_value"
                                    <#--validate="ne[0]" error_msg="ne[0]:请选择状态"-->>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-2 control-label">提出时间</label>
                        <div class="col-sm-3">
                            <div class="input-group date form_date " data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                <input class="form-control form_value" id="occurDate"
									   name="occurDate" size="16" type="text" value="" >
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar">
                                </span></span>
                            </div>
                            <input type="hidden" id="dtp_input" value="" /><br/>
                        </div>

                        <label  class="col-sm-2 control-label">完成时间</label>
                        <div class="col-sm-3">
                            <div class="input-group  " data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                <input class="form-control form_value" id="finishDate"
									   name="finishDate" size="16" type="text" value=""
                                       validate="ge[occurDate]" error_msg="ge[occurDate]:起始日期大于结束日期">

                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar">
                                </span></span>
                            </div>
                            <input type="hidden" id="dtp_input2" value="" /><br/>
                        </div>



                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button  id="search" class="btn btn-info">查询</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<#include "/WEB-INF/pages/calist.ftl">
    </div>
</div>


<script>
    // 加载入口模块

    seajs.use("../js/js/caselist");

</script>

</body>
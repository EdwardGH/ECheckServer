<#setting number_format="#.##"/>
<#assign basePath=request.contextPath>
<head>
    <title>大众点评网</title>
    <script charset="utf-8" src="${basePath}/js/seajs/dist/sea.js"></script>
    <script charset="utf-8" src="${basePath}/js/sea-config.js"></script>
    <!-- Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="${basePath}/js/bootstrap/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="${basePath}/js/bootstrap/bootstrap-theme.css" type="text/css">
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
                <h3 class="panel-title">Cases</h3>
            </div>
            <div class="panel-body">
                <div class="form-horizontal" id="case_create"
                     form_url="/case/ajax/financeCaseCreate">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">问题描述</label>
                        <div class="col-sm-10">
                            <input  class="form-control form_value" name="description" id="description" placeholder="问题描述">
                        </div>

                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">原因及解决方案</label>
                        <div class="col-sm-10">
                            <#--<input  class="form-control form_value" name="resolution"
									id="resolution" placeholder="原因及解决方案">-->
							<textarea class="form-control form_value" name="resolution"
									  id="resolution" rows="2"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">类型</label>
                        <div class="col-sm-4">
                            <select id="caseType" name="caseType" class="form-control form_value"
                                    validate="ne[0]" error_msg="ne[0]:请选择类型">

                            </select>
                        </div>
                        <label  class="col-sm-2 control-label">提出时间</label>
                        <div class="col-sm-3">
                            <div class="input-group date form_date " data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                <input class="form-control form_value" id="occurDate" name="occurDate" size="16" type="text" value=""
                                       datatype="date" dateinput="create">

                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar">
                                </span></span>
                            </div>
                            <input type="hidden" id="dtp_input" value="" /><br/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label  class="col-sm-2 control-label">影响范围和数据</label>
                        <div class="col-sm-4">
                            <input  class="form-control form_value" id="impact" name="impact" placeholder="">
                        </div>
                        <label  class="col-sm-2 control-label">完成时间</label>
                        <div class="col-sm-3">
                            <div class="input-group date form_date " data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                <input class="form-control form_value" id="finishDate" validate="ge[occurDate]"
                                       size="16" type="text" value=""  name="finishDate" datatype="date"
                                       error_msg="ge[occurDate]:起始日期大于结束日期">

                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar">
                                </span></span>
                            </div>
                            <input type="hidden" id="dtp_input" value="" /><br/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-4">
                            <select id="status" name="status" class="form-control form_value"
                                    validate="ne[0]" error_msg="ne[0]:请选择状态">
                            </select>
                        </div>
                        <label  class="col-sm-2 control-label">邮件标题</label>
                        <div class="col-sm-4">
                            <input  class="form-control form_value"  placeholder=""
                                    name="mailTitle" id="mailTitle">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button  id="submit" class="btn btn-info">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    // 加载入口模块

    seajs.use("../js/js/financecase");

</script>

</body>
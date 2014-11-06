<#assign ava=JspTaglibs["/WEB-INF/tld/avatar-tags.tld"]>
<#include "/WEB-INF/pages/util/pageNavigation.ftl" />
<#setting number_format="#.##"/>

<head>
    <title>付款结算单查询</title>
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/base-css.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/billingpayable-css.min.css'/>" type="text/css">
    <script type="text/javascript" src="${commonStaticServer}/build/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${commonStaticServer}/build/relation.js"></script>
</head>

<body>
<form>
<!-- @ main -->
<!--内容-->
<div class="content">
<div class="padding">

<div>
<div class="panel">
    <div class="header section-title">
        <h4>结算单查询</h4>
    </div>
    <div class="body">
        <div class="form-horizontal">
            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">应付日期</label>

                    <div class="controls">
                        <select id="select-date" class="select-small">
                            <option value="99">自定义</option>
                            <option value="0">截至今天</option>
                            <option value="1">今天</option>
                            <option value="2">昨天</option>
                            <option value="3">前天</option>
                        </select>
                        <div class="input-append">
                            <input type="text" id="billdatebegin" value="${billDateBegin}"
                                   class="input-small">
                            <span class="add-on">
                                <i class="icon-calendar">
                                </i>
                            </span>
                        </div>
                        至
                        <div class="input-append">
                            <input type="text" id="billdateend" value="${billDateEnd}"
                                   class="input-small">
                            <span class="add-on">
                                <i class="icon-calendar">
                                </i>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">合同流水号</label>
                    <div class="controls">
                        <input type="text" id="contractserialno" value="${contractSerialNo}"></div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">团购号</label>
                    <div class="controls">
                    <#if dealGroupId == 0 >
                        <input type="text" id="dealgroupid"">
                    <#else>
                        <input type="text" id="dealgroupid" value="${dealGroupIds}">
                    </#if>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">套餐号</label>
                    <div class="controls">
                    <#if dealId == 0 >
                        <input type="text" id="dealid"">
                    <#else>
                        <input type="text" id="dealid" value="${dealIds}">
                    </#if>
                    </div>
                </div>

            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">状态</label>

                    <div class="controls">
                        <select id="status">
						<#if status == 0>
                            <option value="0" selected="true">全部</option>
						<#else>
                            <option value="0">全部</option>
						</#if>
						<#if status == 1>
                            <option value="1" selected="true">初始</option>
						<#else>
                            <option value="1">初始</option>
						</#if>
						<#if status == 5>
                            <option value="5" selected="true">待生成付款计划</option>
						<#else>
                            <option value="5">待生成付款计划</option>
						</#if>
                        <#if status == 7>
                            <option value="7" selected="true">校验不通过</option>
                        <#else>
                            <option value="7">校验不通过</option>
                        </#if>
                        <#if status == 8>
                            <option value="8" selected="true">已生成付款计划</option>
                        <#else>
                            <option value="8">已生成付款计划</option>
                        </#if>
                        <#if status == 9>
                            <option value="9" selected="true">支付成功</option>
                        <#else>
                            <option value="9">支付成功</option>
                        </#if>
                        </select>
                    </div>
                </div>
                <div class="control-group span6" id="not-passed-reason-selector">
                    <label class="control-label">不通过原因</label>

                    <div class="controls">
                        <select id="audittype">
                            <option value="0">全部</option>
                        <#if auditType == 11>
                            <option value="11" selected="true">银行帐号异常</option>
                        <#else>
                            <option value="11">银行帐号异常</option>
                        </#if>
                        <#if auditType == 21>
                            <option value="21" selected="true">合同变更中</option>
                        <#else>
                            <option value="21">合同变更中</option>
                        </#if>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row-fluid label-colon">
                <div class="control-group span6">
                <label class="control-label">结算单ID</label>
                <div class="controls">
                <#if bpid == 0 >
                    <input type="text" id="bpid"">
                <#else>
                    <input type="text" id="bpid" value="${bpIds}">
                </#if>
                </div>
            </div>
            </div>
            <div class="row-fluid">
                <div class="control-group span6">
                    <label class="control-label"></label>

                    <div class="controls">
                        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="search"><span class="glyphicon glyphicon-search"></span>查询</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel">
    <div class="header section-title">
        <h4>结算单列表</h4>
    </div>
    <div class="body strip">
        <div>
            <div class="alert alert-warning" id="select-all-pageinfo">
                已勾选本页全部数据， <a href="#"
                              id="select-all-page">点击这里勾选全部</a>&nbsp;<i>${billingPayableModel.recordCount}</i> 条
            </div>
            <div class="alert alert-warning" id="select-cur-pageinfo">
                已勾选全部 <i>${billingPayableModel.recordCount}</i> 条， <a href="#" id="select-cur-page">取消勾选</a>
            </div>
            <div class="alert alert-info" id="result-info">
                    <span class="number-char">${billingPayableModel.recordCount}</span> 条符合条件的结果，总金额
                    <span class="number-char">${planTotalAmount?string(",##0.00")}</span> 元，已支付（含抵扣）
                    <span class="number-char">${deductedTotalAmount?string(",##0.00")}</span> 元，未支付
                    <span class="number-char">${notDeductedTotalAmount?string(",##0.00")}</span> 元
                &nbsp;&nbsp;
                <#if status == 1 || status == 7>
                        <a class="btn btn-primary btn-fs-normal btn-fs-lg ajaxdisabledbutton" href="#" id="batch-bill">
                        <span class="glyphicon glyphicon-ok"></span>提交付款计划</a>
                        <span class="warn-msg submit-msg"><p>请选择需要提交的结算单!</p></span>
                </#if>

            </div>
        </div>
        <div class="tb-wrapper">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th width="2%"><input type="checkbox" id="select-all"></th>
                    <th width="8%" class ="fs tb-header id" >结算单ID</th>
                    <th width="8%" class="fs tb-header plan-date">应付日期</th>
                    <th width="15%" class="fs tb-header customer-name">客户名</th>
                    <th width="8%" class="fs tb-header contract-serial-no">合同流水号</th>
                    <th width="8%" class="fs tb-header amount">结算单金额</th>
                    <th width="8%" class="fs tb-header dealgroup-id">团购号</th>
                    <th width="8%" class="fs tb-header deal-id">套餐号</th>
                    <th width="7%" class="fs tb-header relation-link">关系图</th>
                    <th width="10%" class="fs tb-header status">状态</th>
                    <#if status == 0 || status == 7>
                        <th class="fs tb-header not-passed-reason">不通过原因</th>
                    </#if>
                </tr>
                </thead>

                <tbody>
                <#if billingPayableModel.recordCount == 0>
                <tr>
                    <td colspan="10" class="no-records">没有查询到任何记录</td>
                </tr>

                <#else>
                    <#assign even = false>
                    <#list billingPayableModel.records as item>
                        <#if even == true>

                        <tr class="even">
                            <#assign even = false>
                        <#else>
                        <tr>
                            <#assign even = true>
                        </#if>
                        <td>
                            <input type="checkbox" id="${item.bpId}" class="selected-bill">
                        </td>
                        <td class ="fs tb-item id number-char" >${item.bpId}</td>
                        <td class="fs tb-item plan-date number-char">${item.planDate?string("yyyy-MM-dd")}</td>
                        <td class="fs tb-item customer-name">${item.customerName}</td>
                        <td class="fs tb-item contract-serial-no number-char">${item.contractSerialNo}</td>
                        <td class="fs tb-item amount number-char">${item.planAmount?string(",##0.00")}</td>
                        <td class="fs tb-item dealgroup-id number-char">${item.dealGroupId}</td>
                        <td class="fs tb-item deal-id number-char">${item.dealId}</td>
                        <td class="fs tb-item relation-link"><a href="#relation-result" data-toggle="modal" class="query-relation" bpid="${item.bpId}">查看</a></td>
                        <td class="fs tb-item status">${item.status}</td>
                        <#if status == 0 || status == 7>
                            <td class="fs tb-item not-passed-reason">${item.auditType}</td>
                        </#if>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    <@pageNavigation billingPayableModel />
    </div>
</div>
</div>
<!--内容 end-->
<@ava.content holderid="PlaceHolderScripts">
<script>
    // 加载入口模块
    if (ENV.debug) {
        seajs.use("<@ava.extStaticResource resource='/js/bill-payable-list'/>");
        seajs.use("<@ava.extStaticResource resource='/js/bill-common'/>");
    } else {
        seajs.use("<@ava.extStaticResource resource='/build/bill-payable-list.min'/>");
        seajs.use("<@ava.extStaticResource resource='/build/bill-common.min'/>");
    }
    ENV.data.query = {
        "billDateBegin": "${billDateBegin}",
        "billDateEnd": "${billDateEnd}",
        "contractSerialNo": "${contractSerialNo}",
        "dealGroupIds": "${dealGroupIds}",
        "dealIds": "${dealIds}",
        "status": "${status}",
        "auditType": "${auditType}",
        "bpIds": "${bpIds}"
    }
</script>
<script>
    if (location.href == 'http://fs.sys.www.dianping.com/prepaidcard/bill/bplist' || location.href == 'http://fs.dper.com/prepaidcard/bill/bplist'){
		pageTracker._trackPageview('dp_fs_prepaidcard_bplist_page');
	}
</script>
</@ava.content>
</form>
</body>
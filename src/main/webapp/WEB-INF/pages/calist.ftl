<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">Case列表</h4>
    </div>
    <div class="">
        <div class="alert alert-success noradius" role="alert">查询结果</div>
        <div class="tb-wrapper">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th width="1%" class="fs tb-header">case ID</th>

                    <th width="5%" class="fs tb-header">问题描述</th>
                    <th width="8%" class="fs tb-header">原因及解决方案</th>
                    <th width="2%" class="fs tb-header">类型</th>
                    <th width="4%" class="fs tb-header">影响范围和数据</th>
                    <th width="3%" class="fs tb-header">提出时间</th>
                    <th width="3%" class="fs tb-header">完成时间</th>
                    <th width="2%" class="fs tb-header">状态</th>
                    <th width="6%" class="fs tb-header">邮件标题</th>
                    <th width="4%" class="fs tb-header">操作</th>
                </tr>
                </thead>

                <tbody id="financecase_list" namespace="financeCaseModel" page_size="20"
                       table_url="/case/ajax/financecaselist">
                </tbody>

            </table>
        </div>
	<#include "/WEB-INF/pages/common/paging.ftl">
	</div>
</div>

<script id="NoRowsTemplate" type="text/x-jquery-tmpl">
<tr>
<td class="norecord" colspan="13">没有查询到任何记录</td>
</tr>
</script>

<script id="list_model" type="text/x-jquery-tmpl">
{{each(i,record) records}}
{{if i%2==1}}
    <tr class="even">
    {{else}}
    <tr >
    {{/if}}

        <td id="{{= record.fcId}}id" class="fs tb-item auto-break">{{= record.fcId}}</td>
        <td id="{{= record.fcId}}desc" class="fs tb-item auto-break">{{= record.description}}</td>
        <td id="{{= record.fcId}}reso" class="fs tb-item auto-break">{{= record.resolution}}</td>
        <td id="{{= record.fcId}}type" class="fs tb-item auto-break">{{= record.caseType}}</td>
        <td id="{{= record.fcId}}impa" class="fs tb-item auto-break">{{= record.impact}}</td>
        <td id="{{= record.fcId}}occur" class="fs tb-item auto-break">{{= record.occurDate}}</td>
        <td class="fs tb-item auto-break">{{= record.finishDate}}</td>
        <td class="fs tb-item auto-break">{{= record.status}}</td>
        <td id="{{= record.fcId}}mail" class="fs tb-item auto-break">{{= record.mailTitle}}</td>
        <td class="fs tb-item auto-break">
           {{if record.status=="初始"}}
           <a href="javascript:void(0)" case-id="{{= record.fcId}}" class="proc-action">处理中</a><br/>
           <a href="javascript:void(0)" case-id="{{= record.fcId}}" class="done-action">已处理</a>
           {{else record.status=="处理中"}}
           <a href="javascript:void(0)" case-id="{{= record.fcId}}" class="done-action">已处理</a>
           {{/if}}
        </td>
    </tr>
    {{/each}}

</script>
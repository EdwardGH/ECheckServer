<div class="tips-container ">
    <div class="tips">
    </div>
    <div class="error">
    </div>
</div>


<div class="modal fade" id="modalDialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
                <h4 class="modal-title" id="modalDialog_title"></h4>
            </div>
            <div class="modal-body" id="modalDialog_body">
            </div>
            <div class="modal-footer" id="modalDialog_footer">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<#--这个div是用来做完成case的对话框的-->
<div class="modal fade " id="doneDialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="modalDialog_title">完成Case</h4>
            </div>
            <div class="modal-body" id="modalDialog_body">
                <div class="form-horizontal" id="case_done"
                     form_url="/case/ajax/doneFinanceCase">
                    <div class="form-group">
						<input class="form_value" name="done_fcId" id="done_fcId" hidden="true">
                        <label  class="col-sm-2 control-label">问题描述</label>
                        <div class="col-sm-10">
                            <input  class="form-control form_value" name="done_description"
									id="done_description" placeholder="问题描述">
                        </div>

                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">原因及解决方案</label>
                        <div class="col-sm-10">
                            <#--<input  class="form-control form_value" name="done_resolution"
									id="done_resolution" placeholder="原因及解决方案">-->
                            <textarea class="form-control form_value" name="done_resolution" id="done_resolution" rows="2"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">类型</label>
                        <div class="col-sm-4">
                            <input  class="form-control form_value" name="done_caseType"
                                    id="done_caseType" placeholder="" disabled>
                        </div>
                        <label  class="col-sm-2 control-label">提出时间</label>
                        <div class="col-sm-3">
                            <input  class="form-control form_value" name="done_occurDate"
                                    id="done_occurDate" placeholder="" disabled>
                        </div>

                    </div>

                    <div class="form-group">
                        <label  class="col-sm-2 control-label">影响范围和数据</label>
                        <div class="col-sm-4">
                            <input  class="form-control form_value" id="done_impact"
									name="done_impact" placeholder="">
                        </div>
                        <label  class="col-sm-2 control-label">完成时间</label>
                        <div class="col-sm-3">
                            <div class="input-group date form_date " data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                <input class="form-control form_value" id="done_finishDate" validate="ge[done_occurDate]"
                                       dateinput="create" size="16" type="text" value=""  name="done_finishDate" datatype="date"
                                       error_msg="ge[done_occurDate]:起始日期大于结束日期">

                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar">
                                </span></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-4">
                            <input  class="form-control form_value" name="done_status"
                                    id="done_status" placeholder="" disabled>
                        </div>
                        <label  class="col-sm-2 control-label">邮件标题</label>
                        <div class="col-sm-4">
                            <input  class="form-control form_value"  placeholder=""
                                    name="done_mailTitle" id="done_mailTitle">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button  id="done_submit" class="btn btn-info">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
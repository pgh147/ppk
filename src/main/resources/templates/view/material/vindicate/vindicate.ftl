<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>销售列表</title>
    <meta name="keyword" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="Author" content="zifan">
    <meta name="copyright" content="All Rights Reserved">
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
    
</head>

<body class="fixed-sidebar">
    <div id="wrapper">
        <!----左侧导航开始----->
        <nav class="navbar-default navbar-static-side" role="navigation" id="leftnav"></nav>
        <!----左侧导航结束----->
        <!---右侧内容区开始---->
        <div id="page-wrapper" class="gray-bg">
            <!---顶部状态栏 star-->
            <div class="row ">
            		<nav class="navbar navbar-fixed-top" role="navigation" id="topnav"></nav>
            </div>
            <!---顶部状态栏 end-->

            <!--------当前位置
            <div class="row  border-bottom white-bg">
                <div class="col-sm-4">
                    <h2 style="margin-top:10px;">销售列表</h2>
                    
                </div>
            </div>
----->
            <!-----内容区域---->
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content m-b-sm border-bottom">
                    <div class="row">
                        <div class="col-lg-8 col-md-6 col-sm-4 ">
				<@shiro.hasRole name="admin_role">
                            <a id="editable-sample_new" class="btn btn-primary" data-toggle="modal" data-typemodel="add" data="ff"  href="#modal-form">
                                上传sku<i class="fa fa-plus"></i>
                            </a> 
                            <a id="editable-sample_new" class="btn btn-primary" data-toggle="modal" data-typemodel="add" data="ffd"  href="#modal-form2">
                                上传库存 <i class="fa fa-plus"></i>
                            </a> 
                        </@shiro.hasRole>    
                        </div>
                        <div class=" col-lg-4 col-md-6 col-sm-8 " >
                                <div class="tablesearch pull-right m-t-xs">
                                   <div class="table-td">
                                        <div class="input-group" >
                                                <input type="text" class="input-sm form-control" name="productNo" id="productNo" value="" placeholder="产品编码">
                                                <span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary " id="button-simple"> 查询</button></span>
                                        </div>
                                   </div>
                                   <div class="table-td m-l-sm pull-right">
                                       <a  class="btn btn-sm btn-primary dropdown-toggle" role="button" data-toggle="collapse" href="#collapseExample" aria-expanded="false" aria-controls="collapseExample"> 高级搜索 <span class="caret"></span></a>

                                   </div>
                                </div>
                        </div>
                    </div>
                    <!----高级搜索内容区---->
                    <div class="collapse" id="collapseExample">
                        <div class="border-top m-t-md m-b-none sidedown-box" >
                            <div class="row">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label class="control-label" for="order_id">开发员编号</label>
                                        <input type="text" id="userNo" name="userNo" value="" placeholder="开发员编号" class="form-control">
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label class="control-label" >产品名称</label>
                                        <input type="text" id="productName" name="productName" value="" placeholder="产品名称" class="form-control">

                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label class="control-label" >上传账号</label>
                                        <input type="text" id="uploadAccount" name="uploadAccount" value="" placeholder="上传账号" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="row">

                                <div class="col-sm-4 col-sm-push-8 text-right">
                                    <button type="button" class="btn btn-primary" id="search-button"><i class="fa fa-search"></i> 立即搜索</button>
                                </div>
                            </div>

                        </div>
                    </div>
                    <!---高级搜索结束---->
                </div>


                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox">
                            <div class="ibox-content" style="padding-top:0px;">
                                <div class="table-responsive ">
                                    <table class="table table-centerbody table-striped table-condensed text-nowrap" id="editable-sample">
                                        <thead>
                                            <tr>
                                                <th>产品图片</th>
                                            	<th>产品编号 </th>
                                                <th>产品名称 </th>
                                                <th>开发员编号</th>
                                                <th>上传账号 </th>
                                                <th style="padding:0;"><div style="font-size: 11px;-webkit-transform: scale(0.7,0.7);height: 0px;"><p  class="canSort" name="threeQty">产品3天销量 </p><p class="canSort" name="weekQty"> 产品7天销量 </p><p class="canSort" name="monthQty">产品30天销量</p></div></th>
                                                <th class="canSort" name="stkQty">亚马逊库存</th>
                                                <th class="canSort" name="ingQty">采购在途库存</th>
                                                <th class="canSort" name="inQty">本地仓库存</th>
                                                <th class="canSort" name="outCanSaleDays">海外仓可售天数</th>
                                                <th class="canSort" name="canSaleDays">可售天数</th>
                                              <!--  <th>采购数量</th>-->
                                            </tr>
                                        </thead>
                                        <tbody id="table-body">
                                            
                                           

                                        </tbody>
                                    </table>
                                </div>
                                
                                    
                                        <div id="zxf_pagediv" class="pagination"></div>
                                    
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <!-----内容结束----->

            <!----版权信息----->
            <div class="footer">
                <div class="pull-right">
                    版权所有，<strong>违者必究.</strong> 
                </div>
                <div>
                    <strong>Copyright</strong> Example Company &copy; 2019-2020
                </div>
            </div>

        </div>
        <!---右侧内容区结束----->

    </div>

    <div class="modal fade" id="modal-form" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">上传SKU</h4>
                </div>
                <div class="modal-body">

                       <form name="entity" id="input_form" class="form-horizontal" enctype="multipart/form-data">
                       
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 请输入要上传SKU的列，必须对应excel中的列 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
								
									<textarea  name="excelImportCls" id="excelImportCls" style="width: 100%;"  ></textarea>
								</div>
							</div>		
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 请上传SKU的Excel </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="file" class="file-loading" name="productExcelData" id="productImgData"  accept=".xls,.xlsx" >
									
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-12 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">
										<i class="fa fa-check"></i> 填写完成，提交！
									</button>
									<button class="btn btn-white" type="reset">重置</button>
								</div>
							</div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="modal-form2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">上传库存</h4>
                </div>
                <div class="modal-body">

                       <form name="entity" id="input_form2" class="form-horizontal" enctype="multipart/form-data">
                       
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 请输入要上传的列，必须对应excel中的列 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
								
									<textarea  name="excelImportCls" id="excelImportCls2" style="width: 100%;"  ></textarea>
								</div>
							</div>		
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 请上传SKU的Excel </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="file" class="file-loading" name="productStkData" id="productStkData"  accept=".xls,.xlsx" >
									
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-12 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">
										<i class="fa fa-check"></i> 填写完成，提交！
									</button>
									<button class="btn btn-white" type="reset">重置</button>
								</div>
							</div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- 全局 scripts -->
    <script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/wuling.js"></script>
    
    <script src="${ctx}/static/js/plugins/page/jq-paginator.min.js"></script>


    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <!-- 插件 scripts -->
    <script src="${ctx}/static/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js" async></script><!---顶部弹出提示--->
    <script src="${ctx}/static/js/plugins/sweetalert/sweetalert.min.js" async></script><!---对话框 alert--->
    <script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js"></script>  <!---表单验证--->
    <script src="${ctx}/static/js/plugins/validate/validate-cn.js" ></script> <!---validate 自定义方法--->
	<!---文件上传--->
    <script src="${ctx}/static/js/plugins/fileinput/fileinput.js"></script>
	<!---文件上传中文配置--->
	<script src="${ctx}/static/js/plugins/fileinput/fileinput_locale_zh.js"></script>
    <script src="${ctx}/static/page/vindicate.js"> </script>
</body>
</html>

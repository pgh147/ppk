<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>产品明细</title>
    <meta name="keyword" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
        <link href="${ctx}/static/js/plugins/pictureCut/cropper.min.css" rel="stylesheet">
    <link href="${ctx}/static/js/plugins/pictureCut/main.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
</head>

<body class="fixed-sidebar">
    <div id="wrapper" > 
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

            <!-----内容区域---->
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content m-b-sm border-bottom">
                   
                       <form name="entity" id="input_form" class="form-horizontal" enctype="multipart/form-data">
                       
									<input type="text" style="display:none"  id="produc_id_h" name="id" >
									<input type="text" style="display:none"  id="isUpdateImg_id_h" name="isUpdateImg" >
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="name"><span class="text-danger">* </span>产品编号 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text"  name="productNo" value="" placeholder="请输入产品编号" class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="name"><span class="text-danger">* </span>产品名称 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text"  name="productName" value="" placeholder="请输入产品名称" class="form-control" required>
								</div>
							</div>							                       
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="shortName"><span class="text-danger">*</span> 开发员编号 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text"  name="userNo" value="" placeholder="请输入开发员编号" class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="shortName"><span class="text-danger">*</span> 上传账号 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text"  name="uploadAccount" value="" placeholder="请输入上传账号" class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 产品图片 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<div id="show-img-data"></div>
									<!--<div class="container" id="crop-avatar">
										<div class="avatar-view" >上传图片</div>
									</div>-->
									<input type="file" class="file-loading"  id="productImgData"  accept="image/*">
									<input type="text" style="display:none"  id="productImgData_h" name="productImgData" >
								</div>
							</div>
							
							

							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label"> 竞争对手连接 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
										<textarea rows="5" cols="20" id="rivalLink" name="rivalLink" value="" placeholder="请输入竞争对手连接,多个换行" class="form-control" ></textarea>
										 <!--
										 <input type="text" id="rivalLink" name="rivalLink" value="" placeholder="请输入竞争对手连接" class="form-control" >
										 -->
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label"> 供应商链接 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
								<textarea rows="5" cols="20" id="supplierLink" name="supplierLink" value="" placeholder="请输入供应商连接,多个换行" class="form-control" ></textarea>
									<!--<input type="text"  name="supplierLink" value="" placeholder="请输入供应商链接." class="form-control" >-->
								</div>
							</div>
							

							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="callSupplier"> 产品开发分析 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<div style="color:red">分析内容包含：产品主关键词，市场容量，top5的利润率，竞争饱和度，生命周期，产品包装，性比价，等创新优势</div>
									<textarea rows="5" cols="20" id="productAnys" name="productAnys" value="" placeholder="产品开发分析 " class="form-control" ></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="callSupplier"> 产品安全性 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
								<div style="color:red">安全性内容包含：（有无功能专利、外观专利、版权风险、相关认证）</div>
									<input type="text"  name="productSafe" value=""  class="form-control" >
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 产品成本价 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text"  name="productPrice" value="" placeholder="产品成本价" class="form-control" >
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 产品尺寸(cm) </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="productSize" name="productSize" value="" placeholder="产品尺寸(cm)" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 外包装尺寸(cm) </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="pSize" name="pSize" value="" placeholder="外包装尺寸(cm)" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 净重 (g)</label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="productVol" name="productVol" value="" placeholder="净重(g)" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 毛重(g) </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="pVol" name="pVol" value="" placeholder="毛重(g)" class="form-control" >
								</div>
							</div>							
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 首批发货数量US UK </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="firstSendQty" name="firstSendQty" value="" placeholder="首批发货数量US UK" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 预计月销量 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="productOrderQty" name="productOrderQty" value="" placeholder="预计月销量" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 产品起订量 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="predictSalesQty" name="predictSalesQty" value="" placeholder="产品起订量" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 质检注意事项 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="qcNotice" name="qcNotice" value="" placeholder="质检注意事项" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 售价 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="salePrice" name="salePrice" value="" placeholder="售价" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 英文标题 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<div style="color:red">不能超过200个字符,还可以输入<span id="enTitleSpan">200</span>字符</div>
									<input type="text" id="enTitle" name="enTitle" value="" placeholder="英文标题" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 卖点 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="productSelling1" name="productSelling1" value="" placeholder="卖点1" class="form-control" >
									<input type="text" id="productSelling2" name="productSelling2" value="" placeholder="卖点2" class="form-control" >
									<input type="text" id="productSelling3" name="productSelling3" value="" placeholder="卖点3" class="form-control" >
									<input type="text" id="productSelling4" name="productSelling4" value="" placeholder="卖点4" class="form-control" >
									<input type="text" id="productSelling5" name="productSelling5" value="" placeholder="卖点5" class="form-control" >
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 描述 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
								<div style="color:red">不能超过2000个字符,还可以输入<span id="descriptSpan">2000</span>字符</div>
									<textarea rows="5" cols="20" id="descript" name="descript" value="" placeholder="描述" class="form-control" ></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 广告词 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<textarea rows="5" cols="20" id="message" name="message" value="" placeholder="广告词 " class="form-control" ></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> Search term </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<div style="color:red">不能超过250个字符,还可以输入<span id="searchTermSpan">250</span>字符</div>
									<input type="text" id="searchTerm" name="searchTerm" value="" placeholder="Search term" class="form-control" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="kuaiQianId"> 分类 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="classify" name="classify" value="" placeholder="分类" class="form-control" >
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



    <!-- Cropping modal -->
    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form class="avatar-form" action="crop.php" enctype="multipart/form-data" method="post">
            <div class="modal-header">
              <button class="close" data-dismiss="modal" type="button">&times;</button>
              <h4 class="modal-title" id="avatar-modal-label">Change Avatar</h4>
            </div>
            <div class="modal-body">
              <div class="avatar-body">

                <!-- Upload image and data -->
                <div class="avatar-upload">
                  <input class="avatar-src" name="avatar_src" type="hidden">
                  <input class="avatar-data" name="avatar_data" type="hidden">
                  <label for="avatarInput">Local upload</label>
                  <input class="avatar-input" id="avatarInput" name="avatar_file" type="file">
                </div>

                <!-- Crop and preview -->
                <div class="row">
                  <div class="col-md-9">
                    <div class="avatar-wrapper"></div>
                  </div>
                  <div class="col-md-3">
                    <div class="avatar-preview preview-lg"></div>
                    <div class="avatar-preview preview-md"></div>
                    <div class="avatar-preview preview-sm"></div>
                  </div>
                </div>

                <div class="row avatar-btns">
                  <div class="col-md-9">
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="Rotate -90°">Rotate Left</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="-15" type="button">-15°</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="Rotate 90 °">Rotate Right</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="15" type="button">15°</button>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <button class="btn btn-primary btn-block avatar-save" type="submit">Done</button>
                  </div>
                </div>
              </div>
            </div>
          </form>
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
    <!--
      <script src="${ctx}/static/js/plugins/pictureCut/cropper.min.js"></script>
  <script src="${ctx}/static/js/plugins/pictureCut/main.js"></script>
  -->
    <script src="${ctx}/static/page/prodDetail.js"> </script>
</body>
</html>

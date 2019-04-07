<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>销售汇总</title>
    <meta name="keyword" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="Author" content="zifan">
    <meta name="copyright" content="All Rights Reserved">
    <link href="${ctx}/static/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <!--date style-->
    <link href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
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
                    <h2 style="margin-top:10px;">产品开发销量</h2>
                </div>
            </div>
----->
            <!-----内容区域---->
            <div class="wrapper wrapper-content ">
                <div class="row">
                        <div class="ibox ">
                            <div class="ibox-content">
                                <div class="row">
                                    
                                    <div class="form-group col-sm-4 col-lg-5 m-t-xs m-b-none" id="data_5">
                                        <div class="input-daterange input-group" id="datepicker">
                                            <input type="text" class="input-sm form-control" name="start" id="beginDate" >
                                            <span class="input-group-addon">至</span>
                                            <input type="text" class="input-sm form-control" name="end" id="endDate" >
                                            <span class="input-group-btn"><button type="button" id="searchButton" class="btn btn-sm btn-primary no-margins"> 查询</button> </span>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 m-t-xs m-b-none">
                                        <div data-toggle="buttons" class="btn-group" id="customRadio">
                                            <label class="btn btn-sm btn-white" data-num="1"> <input type="radio" id="option1" name="options"> 按天 </label>
                                            <label class="btn btn-sm btn-white" data-num="2"> <input type="radio" id="option2" name="options"> 按周 </label>
                                            <label class="btn btn-sm btn-white btn-primary active" data-num="3"> <input type="radio" id="option3" name="options"> 按月 </label>
                                            <label class="btn btn-sm btn-white" data-num="4"> <input type="radio" id="option4" name="options"> 按年 </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        
                        
                        <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>开发员销售汇总</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                
                            </div>
                        </div>
                        <div class="ibox-content">
                            <div class="table-responsive">
                                <div class="row" id="echartsUserSale" style="height: 250px; width: 100%">

                                </div>
                            </div>

                        </div>
                    </div>
                        
                        
                        
                        <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>top10SKU</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                
                            </div>
                        </div>
                        <div class="ibox-content">
                            <div class="table-responsive">
                                <div class="row" id="echartstop10" style="height: 250px; width: 100%">

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
                    <strong>Copyright</strong> Wuling Company &copy; 2014-2015
                </div>
            </div>
        </div>
        <!---右侧内容区结束----->
    </div>
    </div>
    <!-- Mainly scripts -->
    <script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/wuling.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js"></script>  <!---表单验证--->
    <script src="${ctx}/static/js/plugins/validate/validate-cn.js" ></script> 
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${ctx}/static/js/plugins/echarts/echarts.common.min.js"></script>
   <!-- <script src="${ctx}/static/js/divresize.js"> </script> -->
    <script src="${ctx}/static/page/salesvolume.js"> </script>
    

</body>
</html>

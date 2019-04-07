$(document).ready(function () {
	

    var date = new Date();
    //结束时间： 
	$('#endDate').datepicker({ 
		todayBtn : "linked", 
		autoclose : true, 
		format:"yyyy-mm-dd", 
		defaultDate : new Date() 
	}).on('changeDate',function(e){ 
		var endTime = e.date; 
		$('#beginDate').datepicker('setEndDate',endTime); 
	});
	$("#endDate").datepicker("update", date);
	date.setDate(date.getDate()-30);
    $('#beginDate').datepicker({ 
		todayBtn : "linked", 
		autoclose : true, 
		format:"yyyy-mm-dd",
		initialDate : date.getDate() 
	}).on('changeDate',function(e){ 
		var startTime = e.date; 
		$('#endDate').datepicker('setStartDate',startTime); 
	}); 
	$("#beginDate").datepicker("update", date);
$("#searchButton").click(function(e){
	loadTop10Data();
    loadSaleQtyData();
});
    $('#customRadio label').click(function(e){
    	$('#customRadio label').removeClass('btn-primary active');
    	$(e.target).addClass('btn-primary active');
    	//触发查询
    	var date = new Date();
    	$("#endDate").datepicker("update", date);
    	if($(e.target).attr("data-num") == 1){
    	//date.setDate(date.getDate()-30);
    	}else if($(e.target).attr("data-num") == 2){
    	date.setDate(date.getDate()-7);
    	}else if($(e.target).attr("data-num") == 3){
    	date.setDate(date.getDate()-30);
    	}else{
    	date.setDate(date.getDate()-365);
    	}
    	$("#beginDate").datepicker("update", date);
    	
    	loadTop10Data();
        loadSaleQtyData();
    })
	
    function loadTop10Data(){
  	  var param ={
  			endDate:$("#endDate").val(),
  			startDate:$("#beginDate").val()
  	  }
    	$.ajax({
          type: "GET",
          contentType: "application/json; charset=utf-8", 
          url: "/product_vindicate/findTop10SKU.json",
          data: param,
          dataType: "json",
          success: function(data) {
          	if(data.list.length > 0){
          		var xData = [],yData = [];
          		for(var i = 0;i<data.list.length;i++){
          			var item = data.list[i];
          			xData.push(item.productNo);
          			yData.push(item.saleQty);
          		}
          		myChartTop10.setOption(commonChartsOption({xData:xData,yData:yData,barWidth:20}));
          	}else{
          		myChartTop10.clear();
          	}
          	
          },
          error: function(e) {
          	
          }
    	});
    }
    function loadSaleQtyData(){
    	  var param ={
    			endDate:$("#endDate").val(),
    			startDate:$("#beginDate").val()
    	  }
      	$.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8", 
            url: "/product_vindicate/findUserSaleQty.json",
            data: param,
            dataType: "json",
            success: function(data) {
            	if(data.list.length > 0){
            		var xData = [],yData = [];
            		for(var i = 0;i<data.list.length;i++){
            			var item = data.list[i];
            			xData.push(item.userNo);
            			yData.push(item.saleQty);
            		}
            		myChartUserSale.setOption(commonChartsOption({xData:xData,yData:yData,barWidth:20}));
            	}else{
            		myChartUserSale.clear();
            	}
            	
            },
            error: function(e) {
            	
            }
      	});
      }
    loadTop10Data();
    loadSaleQtyData();
    function commonChartsOption(data){
    	return  {
    	        color: ['#3398DB'],
    	        tooltip : {
    	            trigger: 'axis',
    	            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    	                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    	            }
    	        },
    	        grid: {
    	            left: '3%',
    	            right: '4%',
    	            bottom: '3%',
    	            containLabel: true
    	        },
    	        xAxis : [
    	            {
    	                type : 'category',
    	                data : data.xData,
    	                axisTick: {
    	                    alignWithLabel: true
    	                },
    	            	axisLabel:{
    	            		interval :0
    	            	}
    	            }
    	        ],
    	        yAxis : [
    	            {
    	                type : 'value',
    	                name: '单位（个）',
    	            }
    	        ],
    	        series : [
    	            {
    	                name:'卖出数量',
    	                type:'bar',
    	                barWidth: data.barWidth?data.barWidth:'60%',
    	                data:data.yData
    	            }
    	        ]
    	    };
    }
    
//    // 基于准备好的dom，初始化echarts图表
     myChartTop10 = echarts.init(document.getElementById('echartstop10'));
     myChartUserSale = echarts.init(document.getElementById('echartsUserSale'));
    
//    $("div.wrapper-content").resize(function() {
//    	myChartTop10.resize();
//    	myChartUserSale.resize();
//    });
    resizeChart = function(){
    	console.log(6);
    	myChartTop10.resize();
    	myChartUserSale.resize();
    }
    $(window).resize(function() {
    	myChartTop10.resize();
    	myChartUserSale.resize();
    });

});
var resizeChart,myChartTop10,myChartUserSale;
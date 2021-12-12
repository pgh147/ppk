$(document).ready(function () {
	
	var id = "" ;
    if(window.location.href.indexOf("?") >= 0){
    	var params = window.location.href.substring(window.location.href.indexOf("?")+1).split("&");
    	$.each(params,function(index,item){
    		if(item.indexOf("id=") == 0){
    			 id = item.split("=")[1];
    		}
    	});
    }	

	loadTop10Data();
    

	
    function loadTop10Data(){
  	  var param ={
  			id:id
  	  }
    	$.ajax({
          type: "GET",
          contentType: "application/json; charset=utf-8", 
          url: "/product_vindicate/findSKUMonthList.json",
          data: param,
          dataType: "json",
          success: function(data) {
          	if(data.list.length > 0){
          		var xData = [],yData = [];  
          		 var date = new Date();    
          		 //获取到当前月份,设置月份   
          		 for (var i = 0; i < 365; i++) {      
          			 date.setDate(date.getDate() - 1);
          			 //每次循环一次 月份值减1        
          			 var m = date.getMonth() + 1;        
          			 m = m < 10 ? "0" + m : m;     
          			var d = date.getDate()< 10 ? "0" + date.getDate() : date.getDate();        
          			xData.push(date.getFullYear() + "-" + (m)+ "-" + (d))  
          		 }
          		 xData.reverse();
          		for(var i = 0;i<365;i++){
          			yData.push(null);
          			for(var j = 0;j<data.list.length;j++){
          				var item = data.list[j];
          				if(item.remark == xData[i]){
          					yData.splice(i,1,item.saleQty);
          				}
          			}
          		}
          		myChartTop10.setOption(commonChartsOption({xData:xData,yData:yData}));
          	}else{
          		myChartTop10.clear();
          	}
          	
          },
          error: function(e) {
          	
          }
    	});
    }
    
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
    	            bottom: '10%',
    	            containLabel: true
    	        },
    	        xAxis : [
    	            {
    	                type : 'category',
    	                data : data.xData,
//    	                axisTick: {
//    	                    alignWithLabel: false
//    	                },
//    	                splitNumber:10,
    	            	axisLabel:{
    	            		interval :function(index, value) {console.log(value);if(value&&value.split("-")[2] == '01'){return true}else{ return false}},
//    	            		rotate:45
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
    	                type:'line',
//    	                barWidth: data.barWidth?data.barWidth:'60%',
    	                data:data.yData,
    	                smooth: true,
//    	                itemStyle: {
    	                	normal: {
    	                        label: {
    	                            show: true, //开启显示
    	                            position: 'top', //在上方显示
    	                            formatter: function (val) {
    	                                // console.log(val);
    	                                if ([2, 7, 12, 17].includes(val.dataIndex)) {
    	                                    return val.value + '%'
    	                                } else {
    	                                    return ' '
    	                                }
    	                            },
    	                            color: 'black'
    	                        },
    	                    },
    	                    // 单独改变小原点样式
    	                    emphasis:{
    	                        selectorLabel:{
    	                            showSymbol:false,
    	                        },
    	                        color:"transparent",
    	                        borderColor:"transparent",
    	                        borderWidth:0,
    	                        transitionDuration:0,
    	                    }

    	                }
//    	            }
    	        ]
    	    };
    }
    
//    // 基于准备好的dom，初始化echarts图表
     myChartTop10 = echarts.init(document.getElementById('echartstop10'));
    
    resizeChart = function(){
    	myChartTop10.resize();
    }
    $(window).resize(function() {
    	myChartTop10.resize();
    });

});
var resizeChart,myChartTop10;
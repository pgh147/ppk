
    //下拉框组件
    $(".chosen-select").chosen({no_results_text:'未找到此项',width:"100%",disable_search_threshold:10});
    function appendSelect(){
        var json=[
            {name:'下拉菜单一',val:"1"},
            {name:'下拉菜单二',val:"1"},
            {name:'下拉菜单三',val:"1"},
            {name:'下拉菜单四',val:"1"},
            {name:'下拉菜单五',val:"1"},
            {name:'下拉菜单六',val:"1"},
            {name:'下拉菜单七',val:"1"}
        ];

        var select=$("#city2");
        for(var i=0; i<json.length; i++){
            var op=document.createElement('option');
            op.value=json[i].val;
            op.text=json[i].name;
            select.append(op);
        }

        $(".chosen-select").trigger("chosen:updated");

    }
    
	
        $(document).ready(function () {
        	$("#excelImportCls").val("amazonOrderId,merchantOrderId,purchaseDate,lastUpdatedDate,orderStatus,fulfillmentChannel,salesChannel,orderChannel,url,shipServiceLevel,productName,sku,asin,itemStatus,quantity,currency,itemPrice,itemTax,shippingPrice,shippingTax,giftWrapPrice,giftWrapTax,itemPromotionDiscount,shipPromotionDiscount,shipCity,shipState,shipPostalCode,shipCountry,promotionIds");
        	$("#excelImportCls2").val("sellerSku,fulfillmentChannelSku,asin,conditionType,warehouseConditionCode,quantityAvailable");
        	$("#excelImportCls3").val("fnsku,yourPrice,salesPrice,longestSide,medianSide,shortestSide,estimatedFeeTotal,fba");

        	$(".canSort").click(function(e){
        		if($(e.target).attr("class").indexOf('desc') > -1){
        			$(e.target).removeClass('desc').addClass("asc");
        		}else if($(e.target).attr("class").indexOf('asc') > -1){
        			$(e.target).removeClass('asc');
        		}else{
        			$(e.target).addClass("desc");
        		}
        		loadData();
        		
        	});
        	
        	
            $("#input_form").validate({
            	rules: {
            		productExcelData: {
                        required: true
                    }
                },
                messages: {
                	productExcelData: {
                        required: "请上传Excel",
                    }
                },
            	debug: true,
		        submitHandler: function(form) {
		        	addSkuform(form);
		        }
            });
              

            $("#input_form2").validate({
            	rules: {
            		productStkData: {
                        required: true
                    }
                },
                messages: {
                	productStkData: {
                        required: "请上传Excel",
                    }
                },
            	debug: true,
		        submitHandler: function(form) {
		        	addStkform(form);
		        }
            });
            $("#input_form3").validate({
            	rules: {
            		productStkData: {
                        required: true
                    }
                },
                messages: {
                	productStkData: {
                        required: "请上传Excel",
                    }
                },
            	debug: true,
		        submitHandler: function(form) {
		        	addMoneyform(form);
		        }
            });            
            $('#outCellList').on('show.bs.modal', function (e) {
              	var button = $(event.target) // Button that triggered the modal
          		  var id = button.data('idmodel');
              		$("#outcell-table-body").empty();
          		  $.ajax({
          	          url: "/outCell/selectByNo.json?billNo="+id,
          	          type: "get",
          	          dataType: "json",
          	          data: null,
          	          success: function(data) {
          	        	  if(data.list){
          	        		  var $h="";
          	        		  $.each(data.list,function(index,item){
          	        			$h += "<tr><td>"+item.outQty+"</td><td>"+item.usQty+"</td><td>"+item.ukQty+"</td><td>"+item.caQty+"</td><td>"+item.createTime+"</td><td>"+item.remark+"</td></tr>"
          	        		  })
          	        		  $("#outcell-table-body").html($h);
          	        	  }
          	          },
          	          error:function(e){
          	        	  toastr.error("获取明细错误");
          	          }
          	        });
              	})	
      //t添加
      function addSkuform(form) {
    	  var file = document.getElementById('productImgData').files[0];
          var formData = new FormData($('#input_form')[0]);
          formData.append('importFile', file);
          formData.append('colNames', $("#excelImportCls").val());
          formData.append('mustArray', $("#excelImportCls").val());
          $.ajax({
              url: '/product_vindicate/import.json',
              type: 'POST',
              cache: false,
              data: formData,
              processData: false,
              contentType: false,
          }).done(function(res) {
        	  if(res.code == "0"){
        		  toastr.success('', '导入成功！');
                  $('#modal-form').modal('hide');
                  loadData();
        	  }else{
        		  toastr.error('', '导入出错！'+res.detail);
                  $('#modal-form').modal('hide');
        	  }
          }).fail(function(res) {
        	  toastr.error("出现错误，请更改");
          });
      }
    //t添加
      function addStkform(form) {
    	  var file2 = document.getElementById('productStkData').files[0];
          var formData = new FormData($('#input_form')[0]);
          formData.append('importFile', file2);
          formData.append('colNames', $("#excelImportCls2").val());
          formData.append('mustArray', $("#excelImportCls2").val());
          $.ajax({
              url: '/product_vindicate/importStk.json',
              type: 'POST',
              cache: false,
              data: formData,
              processData: false,
              contentType: false,
          }).done(function(res) {
        	  if(res.code == "0"){
        		  toastr.success('', '导入成功！');
                  $('#modal-form2').modal('hide');
                  loadData();
        	  }else{
        		  toastr.error('', '导入出错！'+res.detail);
                  $('#modal-form2').modal('hide');
        	  }
          }).fail(function(res) {
        	  toastr.error("出现错误，请更改");
          });
      }
      
      function addMoneyform(form) {
    	  var file3 = document.getElementById('productMoneyData').files[0];
          var formData = new FormData($('#input_form3')[0]);
          formData.append('importFile', file3);
          formData.append('colNames', $("#excelImportCls3").val());
          formData.append('mustArray', $("#excelImportCls3").val());
          $.ajax({
              url: '/product_vindicate/importProfitRate.json',
              type: 'POST',
              cache: false,
              data: formData,
              processData: false,
              contentType: false,
          }).done(function(res) {
        	  if(res.code == "0"){
        		  toastr.success('', '导入成功！');
                  $('#modal-form3').modal('hide');
                  loadData();
        	  }else{
        		  toastr.error('', '导入出错！'+res.detail);
                  $('#modal-form3').modal('hide');
        	  }
          }).fail(function(res) {
        	  toastr.error("出现错误，请更改");
          });
      }
      //加载数据
      function loadData(num){
    	  var param ={
    			  productNo:$("#productNo").val(),
    			  userNo:$("#userNo").val(),
    			  productName:$("#productName").val(),
    			  uploadAccount:$("#uploadAccount").val()
    	  }
    	  var orderBy = "";
    	  $.each($('#editable-sample .desc'),function(index,item){
    		  if(orderBy){
    			  orderBy += ",";
    		  }
    		  var name = $(item).attr("name");
    		  if(name == 'outCanSaleDays'){
    			  orderBy += "stkQty/monthQty  desc";
    		  }else if(name == 'canSaleDays'){
    			  orderBy += "(stkQty+ingQty+inQty)/monthQty desc";
    		  }else if(name == 'profitRate'){
    			  orderBy += "profit_rate  desc";
    		  }else{
    			  orderBy += name+" desc";
    		  }
    	  });
    	  $.each($('#editable-sample .asc'),function(index,item){
    		  if(orderBy){
    			  orderBy += ",";
    		  }
    		  var name = $(item).attr("name");
    		  if(name == 'outCanSaleDays'){
    			  orderBy += "stkQty/monthQty  asc";
    		  }else if(name == 'canSaleDays'){
    			  orderBy += "(stkQty+ingQty+inQty)/monthQty asc";
    		  }else if(name == 'profitRate'){
    			  orderBy += "profit_rate  asc";
    		  }else{
    			  orderBy += name+" asc";
    		  }
    	  });
    	  if(orderBy){
    		  param['orderBy'] = orderBy;
    	  }
      	$.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8", 
            url: "/product_vindicate/list.json?pageNum="+(num?num:1),
            data: JSON.stringify(param),
            dataType: "json",
            success: function(data) {
            	if($button){
            		$($button).attr('disabled',false);
            	}
            	$("#table-body").empty();
            	if(data.page.total > 0){
            		$('#zxf_pagediv').jqPaginator('option', {
            			totalCounts: data.page.total
            		});
            		if(data.page.list.length > 0){
            			var $tr = "";
                		$.each(data.page.list,function(index,item){
                			 $tr += '<tr><td class="imgbox"><img width="100" height="100" src="/product/getImg/'+item.productNo+'.json" class="smallimg"></td><td><a href="/product/vindicate/saleQty/page?id='+item.id+'" >'+item.productNo+' </a></td> <td> <span style="width:200px" title="'+(item.productName?item.productName:"")+'"  class="long-break-word">'+item.productName+'</span></td><td>'+item.userNo+'</td><td>'+item.uploadAccount+'</td> '+
                            '<td>'+(item.threeQty?item.threeQty:'0')+'<br />'+(item.weekQty?item.weekQty:'0')+'<br />'+(item.monthQty?item.monthQty:'0')+' </td><td>'+(item.profitRate?item.profitRate*100+'%':'-')+'</td><td>'+(item.stkQty?item.stkQty:'0')+'</td><td>'+(item.ingQty?item.ingQty:'0')+'</td><td>'+(item.inQty?item.inQty:'0')+'</td><td>'+(item.monthQty?Math.round((parseInt(item.stkQty)/parseInt(item.monthQty))*30):'无销量')+'</td><td>'+(item.monthQty?Math.round(((parseInt(item.stkQty)+item.ingQty+item.inQty)/parseInt(item.monthQty))*30):'无销量')+'</td><td><a style="text-decoration: underline;" data-userid="1" data-toggle="modal" data-typemodel="search" data-idmodel="'+item.billNo+'" data-target="#outCellList">'+(item.outQty?item.outQty:"")+'</a></td><td>'+(item.createTime?item.createTime:'')+'</td>'+     //<td><input value="'+(item.okQty?item.okQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="okQty" class="canEdit"></td>'+
                         '</tr>';
                			
                		});
                		
                		$("#table-body").html($tr);
                		 var obj = new zoom('mask', 'bigimg','smallimg');
             			obj.init();
            		}
            		
            	}
            	
            },
            error: function(e) {
            	if($button){
            		$($button).attr('disabled',false);
            	}
            }
      	});
      }

      $("#zxf_pagediv").jqPaginator({
    	  totalCounts: 1,
    	  pageSize: 10,
    	    currentPage: 1,
            first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">末页<\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
    	    onPageChange: function (num, type) {
    	    	if(type != 'init'){
    	    		loadData(num);	    		
    	    	}
    	    }
    	});
      
      loadData();
      var $button = null;
      //查询
      $("#search-button,#button-simple").click(function(e){
    	  $button = e.target;
    	  $(e.target).attr('disabled',true);
    	  loadData();
      });

    
    });



    var loadData;
        $(document).ready(function () {
            //关闭模态框清空表单值
            $("#myModa-reset").on('hidden.bs.modal', function (event) {
                $(this).find("input").val("");
            });
            //删除数据
            $(document).on('click','#editable-sample button.delete', function () {
                var row=$(this).parents("tr")[0];
                var id=$(this).data("idmodel");
                    var swa = swal({
                        title: "您确定要删除吗?",
                        text: "产品删除后将不可恢复!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#1ab394",
                        confirmButtonText: "确定删除！",
                        closeOnConfirm: false
                    }, function (isConfirm,b) {
                    	if (isConfirm) {
                    		
                    		$.ajax({
                    			url: "/product/delete.json?id="+id,
                    			type: "get",
                    			dataType: "json",
                    			data: null,
                    			success: function(data) {
                    				swal.close();
//                    				toastr.success("删除成功");
                    				row.className="animated bounceOut";
                    				loadData();
                    			},
                    			error:function(e){
                    				swal.close();
                    				toastr.error("删除失败");
                    			}
                    		});
                    		
                    	}
                    });
            });

      //加载数据
      loadData = function loadData(num){
    	  var param ={
    			  productNo:$("#productNo").val(),
    			  userNo:$("#userNo").val(),
    			  productName:$("#productName").val(),
    			  productStatus:$("#productStatus").val(),
    			  uploadAccount:$("#uploadAccount").val()
    	  }
      	$.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8", 
            url: "/product/list.json?pageNum="+(num?num:1),
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
                			 $tr += '<tr><td>'+ findInArr([{itemValue:1,itemName:"初始"},{itemValue:15,itemName:"确认"},{itemValue:20,itemName:"审核"}],item.productStatus)+'</td>';
//                			 if(item.productStatus <= 15){
                				 $tr +='<td><img width="100" height="70" src="'+item.productImgData+'"></td><td> <a href="/product/detail/page?id='+item.id+'" >'+item.productNo+' </a></td>';
//                				 $tr +='<td><img width="100" height="70" src="'+item.productImgData+'"></td><td> <a data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form">'+item.productNo+' </a></td>';
//                			 }else{
//                				 $tr +='<td><img width="100" height="70" src="'+item.productImgData+'"></td><td>'+item.productNo+'</td>';
//                			 }
                			 $tr +='<td>'+item.userNo+'</td><td>'+item.uploadAccount+'</td> <td><span style="width:200px" title="'+(item.productName?item.productName:"")+'"  class="long-break-word">'+item.productName+'</span></td>'; 
                			 
                			 $tr +='<td>';
                				 if(item.rivalLink){
                					 $.each(item.rivalLink.split("\n"),function(index,item){
                						 $tr +='<p  class="p-cls"><a href="'+item+'" target="_blank" style="width:100px" class="long-break-word">'+item+'</a></p>';
                					 });
                				 }else{
                					 $tr += ' '; 
                				 }
                			$tr +='</td><td>'	; 
                			 if(item.supplierLink){
            					 $.each(item.supplierLink.split("\n"),function(index,item){
            						 $tr +='<p class="p-cls"><a href="'+item+'" target="_blank" style="width:100px" class="long-break-word">'+item+'</a></p>';
            					 });
            				 }else{
            					 $tr += ' '; 
            				 } 
//                            '<td><a style="width:100px" target="_blank"  href="'+(item.supplierLink?item.supplierLink:"")+'"  class="long-break-word">'+(item.supplierLink?item.supplierLink:' ')+'</a></td><td>'+(item.firstSendQty?item.firstSendQty:' ')+'</td><td>'+(item.productOrderQty?item.productOrderQty:' ')+'</td><td>'+(item.predictSalesQty?item.predictSalesQty:' ')+'</td>'+
                			 $tr +='</td><td>'+(item.firstSendQty?item.firstSendQty:' ')+'</td><td>'+(item.productOrderQty?item.productOrderQty:' ')+'</td><td>'+(item.predictSalesQty?item.predictSalesQty:' ')+'</td><td class="text-right text-nowrap">'+
                                 '<div class="btn-group ">';
                                 if(item.productStatus <= 15){
//                                	 $tr += '<button class="btn btn-white btn-sm edit" data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form"><i class="fa fa-pencil"></i> <a href="/product/detail/page?id='+item.id+'"> 编辑</a></button>';
                                	 $tr += '<button class="btn btn-white btn-sm edit"  ><i class="fa fa-pencil"></i> <a href="/product/detail/page?id='+item.id+'"> 编辑</a></button>';
                                 }
                                     if(data.flag){
                                    	 if(item.productStatus == 15){
                                    		 $tr += '<button class="btn-white  btn btn-sm rset" onclick="audit('+"'"+item.id+"'"+','+"'20'"+')" ><i class="fa fa-eye"></i>审核</button>';
                                    	 }else if(item.productStatus < 15){
                                    		 $tr += '<button class="btn-white  btn btn-sm rset" onclick="audit('+"'"+item.id+"'"+','+"'15'"+')"><i class="fa fa-eye"></i>确认</button>';
                                    	 }
                                    	 if(item.productStatus > 15){
                                    		 $tr += '<button class="btn-white  btn btn-sm delete" data-userid="1" data-idmodel="'+item.id+'"><i class="fa fa-trash"></i>  删除</button>';
                                    	 }
                                     }
                                     if(item.productStatus <= 15){
                                    	 $tr += '<button class="btn-white  btn btn-sm delete" data-userid="1" data-idmodel="'+item.id+'"><i class="fa fa-trash"></i>  删除</button>';
                                     }
                                     $tr += '</div>'+
                             '</td>'+
                         '</tr>';
                			
                		});
                		
                		$("#table-body").html($tr);
            		}
            		
            	}
            	
            },
            error: function(e) {
            	if($button){
            		$($button).attr('disabled',false);
            	}
            }
      	});
      };
      

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
//    	        $('#text').html('当前第' + num + '页');
    	        loadData(num);
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
      //导出
      $("#button-export").click(function(e){
    	  var param ={
    			  productNo:$("#productNo").val(),
    			  userNo:$("#userNo").val(),
    			  productName:$("#productName").val(),
    			  productStatus:$("#productStatus").val(),
    			  uploadAccount:$("#uploadAccount").val()
    	  }
    	  window.open("/product/big_simpleExport.json?"+$.param(param), "_blank");
      });
    });
        
        function audit(id,status){
          	$.ajax({
                  url: "/product/updateStatus.json",
                  type: "post",
                  contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                  dataType: "json",
                  data: {id:id,productStatus:status},
                  success: function(data) {
                    toastr.success('', '修改成功！');
                    $('#modal-form').modal('hide');
                    loadData();
                  },
                  error:function(e){
                	  toastr.error("出现错误，请更改");
                  }
                });
          }        

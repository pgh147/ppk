//采购


    var loadData;
        $(document).ready(function () {
        	$("#excelImportCls").val("productNo,userNo,qcIncellNo,purchaseQty,productPrice,supplier,discount,remark");
        	//导入
        	 $("#input_import").validate({
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
 		        	importSkuform(form);
 		        }
             });
        	//t添加
             function importSkuform(form) {
           	  var file = document.getElementById('productImgData').files[0];
                 var formData = new FormData($('#input_form')[0]);
                 formData.append('importFile', file);
                 formData.append('colNames', $("#excelImportCls").val());
                 formData.append('mustArray', $("#excelImportCls").val());
                 $.ajax({
                     url: '/purchase/import.json',
                     type: 'POST',
                     cache: false,
                     data: formData,
                     processData: false,
                     contentType: false,
                 }).done(function(res) {
	               	  if(res.code == "0"){
	               		  toastr.success('', '导入成功！');
	                         $('#modal-import').modal('hide');
	                         loadData();
	               	  }else{
	               		  toastr.error('', '导入出错！'+res.detail);
	                         $('#modal-import').modal('hide');
	               	  }
                 }).fail(function(res) {
               	  toastr.error("出现错误，请更改");
                 });
             }
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
                        text: "记录删除后将不可恢复!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#1ab394",
                        confirmButtonText: "确定删除！",
                        closeOnConfirm: false
                    }, function (isConfirm,b) {
                    	if (isConfirm) {
                    		
                    		$.ajax({
                    			url: "/purchase/delete.json?id="+id,
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
            $("#input_form").validate({
            	rules: {
            		userNo: {
                        required: true,
                        rangelength: [1, 12],
                    },
                    uploadAccount: {
                        required: true,
                        rangelength: [1, 12],
                    },
                    productNo: {
                        required: true,
                        rangelength: [1, 12],
                    },
                    productName: {
                        required: true,
                    },
                    productPrice: {
                    	number: true
                    }
                },
                messages: {
                	userNo: {
                        required: "请输入开发员编号",
                        rangelength: jQuery.validator.format("开发员编号应为1-12位的英文字母")
                    },
                    uploadAccount: {
                        required: "请填写上传账号",
                        rangelength: jQuery.validator.format("上传账号应为1-12位的英文字母")
                    },
                    productNo: {
                        required: "请输入开发员编号",
                        rangelength: jQuery.validator.format("开发员编号应为1-12位的英文字母")
                    },
                    productName: {
                        required: "请输入产品名称",
                    },
                    productPrice: "请输入数字"
                },
            	debug: true,
		        submitHandler: function(form) {
		        	if($("#produc_id_h").val()){
		        		editform(form)
		        	}else{
		        		addform(form);
		        	}
		        }
            });
            function $_(id) {
                    return document.getElementById(id);
            }         
            $('#modal-form').on('show.bs.modal', function (e) {
            	$("#show-img-data").empty();
//            	$(item).attr("disabled",true);
            	var button = $(event.target) // Button that triggered the modal
            	  var type = button.data('typemodel') // Extract info from data-* attributes
            	  if(type =='modlify'){
                	  $(this).find('.modal-title').text('修改采购记录')
            		  var id = button.data('idmodel');
            		  $.ajax({
            	          url: "/purchase/detail.json?id="+id,
            	          type: "get",
            	          dataType: "json",
            	          data: null,
            	          success: function(data) {
            	        	  if(data.page){
            	        		  $("#show-img-data").append("<img src='"+data.page.productImgData+"' >");
            	        		  $.each($(':input','#input_form'),function(index,item){
            	        			  $(item).val(data.page[item.name]);
            	        			  if(item.name == 'billNo' && data.page[item.name]){
            	        				  $(item).attr("readonly",true);
            	        			  }
            	        		  })
            	        	  }
            	          },
            	          error:function(e){
            	        	  toastr.error("获取明细错误");
            	          }
            	        });
            		  
            	  }else{
                	  $(this).find('.modal-title').text('添加采购记录')
            		  $.each($(':input','#input_form'),function(index,item){
            			  
            			  $(item).val('');//.attr("readonly",false);;
            		  })
            	  }
            	})
            	
            	//修改
      function editform(form) {
          	  var data = {};
      	    var t = $(form).serializeArray();
      	    $.each(t, function() {
      	      data[this.name] = this.value;
      	    });
        $.ajax({
          url: "/purchase/edit.json",
          type: "post",
//          contentType:"application/x-www-form-urlencoded; charset=UTF-8",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
//          data: $(form).serialize(),
          data:JSON.stringify(data),
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
            //t添加
      function addform(form) {
    	  var data = {};
    	    var t = $(form).serializeArray();
    	    $.each(t, function() {
    	      data[this.name] = this.value;
    	    });
        $.ajax({
          url: "/purchase/add.json",
          type: "post",
//          contentType:"application/x-www-form-urlencoded; charset=UTF-8",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
//          data: $(form).serialize(),
          data:JSON.stringify(data),
          success: function(data) {
            toastr.success('', '添加成功！');
            $('#modal-form').modal('hide');
            loadData();
          },
          error:function(e){
        	  toastr.error("出现错误，请更改");
          }
        });
      }
      //加载数据
      loadData = function loadData(num){
    	  var param ={
    			  productNo:$("#productNo").val(),
    			  userNo:$("#userNo").val(),
    			  productStatus:$("#productStatus").val(),
    			  supplier:$("#supplier").val()
    	  }
      	$.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8", 
            url: "/purchase/list.json?pageNum="+(num?num:1),
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
                			 if(item.productStatus <= 15){
                				 $tr += '<td><a data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form">'+(item.billNo?item.billNo:' ')+'</a></td>';                				 
                			 }else{
                				 $tr += '<td>'+(item.billNo?item.billNo:' ')+'</td>';
                			 }
                			 $tr += '<td><img width="100" height="70" src="'+item.imgData+'"></td><td>'+item.productNo+'</td> <td><span style="width:200px" title="'+(item.productName?item.productName:"")+'"  class="long-break-word">'+item.productName+'</span></td> <td>'+item.userNo+'</td><td>'+(item.qcIncellNo?item.qcIncellNo:' ')+'</td><td>'+item.purchaseQty+'</td><td>'+(item.productPrice?item.productPrice:' ')+'</td>'+
                            '<td>'+(item.discount?item.discount:' ')+'</td><td>'+(item.supplier?item.supplier:' ')+'</td> <td>'+(item.createTime?item.createTime:' ')+'</td><td>'+(item.remark?item.remark:' ')+' </td>'+
                             '<td class="text-right text-nowrap">'+
                                 '<div class="btn-group ">';
//                                 if(item.productStatus <= 15){
                                	 $tr += '<button class="btn btn-white btn-sm edit" data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form"><i class="fa fa-pencil"></i>  编辑</button>';
//                                 }
//                                     if(data.flag){
////                                    	 if(item.productStatus == 15){
////                                    		 $tr += '<button class="btn-white  btn btn-sm rset" onclick="audit('+"'"+item.id+"'"+','+"'20'"+')" ><i class="fa fa-eye"></i>审核</button>';
////                                    	 }else 
//                                    		 if(item.productStatus < 15){
//                                    		 $tr += '<button class="btn-white  btn btn-sm rset" onclick="audit('+"'"+item.id+"'"+','+"'20'"+')"><i class="fa fa-eye"></i>审核</button>';
//                                    	 }
//                                    	 if(item.productStatus > 15){
//                                    		 $tr += '<button class="btn-white  btn btn-sm delete" data-userid="1" data-idmodel="'+item.id+'"><i class="fa fa-trash"></i>  删除</button>';
//                                    	 }
//                                     }
//                                     if(item.productStatus <= 15){
                                    	 $tr += '<button class="btn-white  btn btn-sm delete" data-userid="1" data-idmodel="'+item.id+'"><i class="fa fa-trash"></i>  删除</button>';
//                                     }
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
      
    
    });
        
        function audit(id,status){
          	$.ajax({
                  url: "/purchase/updateStatus.json",
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

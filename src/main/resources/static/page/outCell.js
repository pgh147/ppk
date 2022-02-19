//出库


    var loadData,pageNum;
        $(document).ready(function () {
        	$("#excelImportCls").val("productNo,userNo,outQty,usQty,ukQty,caQty,remark");
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
		        	addSkuform(form);
		        }
            });
       	//t添加
            function addSkuform(form) {
          	  var file = document.getElementById('productImgData').files[0];
                var formData = new FormData($('#input_form')[0]);
                formData.append('importFile', file);
                formData.append('colNames', $("#excelImportCls").val());
                formData.append('mustArray', $("#excelImportCls").val());
                $.ajax({
                    url: '/outCell/import.json',
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
                        text: "产品删除后将不可恢复!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#1ab394",
                        confirmButtonText: "确定删除！",
                        closeOnConfirm: false
                    }, function (isConfirm,b) {
                    	if (isConfirm) {
                    		
                    		$.ajax({
                    			url: "/outCell/delete.json?id="+id,
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
//            		userNo: {
//                        required: true,
//                        rangelength: [1, 12],
//                    },
                    productNo: {
                        required: true,
                        rangelength: [1,12],
                    },
                    outQty: {
                    	required: false,
                    	number: true
                    },
                    usQty: {
                    	required: false,
                    	number: true
                    },
                    ukQty: {
                    	required: false,
                    	number: true
                    },
                    caQty: {
                    	required: false,
                    	number: true
                    }
                },
                messages: {
//                	userNo: {
//                        required: "请输入开发员编号",
//                        rangelength: jQuery.validator.format("开发员编号应为1-12位的英文字母")
//                    },
                    productNo: {
                        required: "请输入产品编号",
                        rangelength: jQuery.validator.format("产品编号应为1-12位的英文字母")
                    },
                    outQty: {
                        required: "请输入出库数量",
                        number: "请输入数字"
                    },
                    usQty: {
                        number: "请输入数字"
                    },
                    ukQty: {
                        number: "请输入数字"
                    },
                    caQty: {
                        number: "请输入数字"
                    }
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
            	var button = $(event.target) // Button that triggered the modal
            	  var type = button.data('typemodel') // Extract info from data-* attributes
            	  if(type =='modlify'){
                	  $(this).find('.modal-title').text('修改出库记录')
            		  var id = button.data('idmodel');
            		  $.ajax({
            	          url: "/outCell/detail.json?id="+id,
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
                	  $(this).find('.modal-title').text('添加出库记录')
            		  $.each($(':input','#input_form'),function(index,item){
            			  if(item.name == 'productNo'){
            				  $(item).val(button.data('idmodel'));
            			  }else{
            				  $(item).val('');
            			  }
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
          url: "/outCell/edit.json",
          type: "post",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          data:JSON.stringify(data),
          success: function(data) {
            if(data.status ==1){
            	toastr.success('', '修改成功！');
                $('#modal-form').modal('hide');
                loadData(pageNum);               		
        	}else{
        		toastr.error("出现错误，请更改");
        	}
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
          url: "/outCell/add.json",
          type: "post",
//          contentType:"application/x-www-form-urlencoded; charset=UTF-8",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
//          data: $(form).serialize(),
          data:JSON.stringify(data),
          success: function(data) {
            toastr.success('', '添加成功！');
            $('#modal-form').modal('hide');
            loadData(pageNum);
          },
          error:function(e){
        	  toastr.error("出现错误，请更改");
          }
        });
      }
      
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
      //加载数据
      loadData = function loadData(num){
    	  pageNum = num?num:1;
    	  var param ={
    			  productNo:$("#productNo").val(),
    			  userNo:$("#userNo").val(),
//    			  billNo:$("#billNo").val(),
    			  productStatus:$("#productStatus").val(),
    			  sort:Sort?Sort:''
    	  }
      	$.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8", 
            url: "/outCell/list.json?pageNum="+(num?num:1),
            data: JSON.stringify(param),
            dataType: "json",
            success: function(data) {
            	if($button){
            		$($button).attr('disabled',false);
            	}
            	$("#table-body").empty();
            	if(data.page.total > 0){
            		$('#zxf_pagediv').jqPaginator('option', {
            			totalCounts: data.page.total,
            			currentPage:num?parseInt(num):1
            		});
            		if(data.page.list.length > 0){
            			var $tr = "";
                		$.each(data.page.list,function(index,item){
//                			if(item.productStatus <= 15){
//                				$tr += '<tr><td><a data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form">'+ findInArr([{itemValue:1,itemName:"初始"},{itemValue:15,itemName:"确认"},{itemValue:20,itemName:"审核"}],item.productStatus)+'</a></td>';//<a data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form">'+item.billNo+'</a></td>';
//               			 	}else{
//               			 		$tr += '<tr><td>'+ findInArr([{itemValue:1,itemName:"初始"},{itemValue:15,itemName:"确认"},{itemValue:20,itemName:"审核"}],item.productStatus)+'</td>';
//               			 	}
                				$tr +=  ' <tr><td class="imgbox"><img width="100" height="100" src="/product/getImg/'+item.productNo+'.json" class="smallimg"><td>'+item.productNo+'</td> <td><span style="width:200px" title="'+(item.productName?item.productName:"")+'"  class="long-break-word">'+item.productName+'</span></td> <td>'+(item.surplusQty?item.surplusQty:'0')+'</td>'+
                				'<td>'+(item.usQty?item.usQty:' ')+'</td><td>'+(item.ukQty?item.ukQty:' ')+'</td><td>'+(item.caQty?item.caQty:' ')+'</td><td>'+(item.remark?item.remark:' ')+'</td>'+
                				'<td class="text-right text-nowrap">'+
                                 '<div class="btn-group ">';
                				$tr += '<button class="btn btn-white btn-sm edit" data-userid="1" data-toggle="modal" data-typemodel="search" data-idmodel="'+item.billNo+'" data-target="#outCellList"><i class="fa fa-search"></i>  查看出库记录</button>';
                                 if(data.flag){ 
                                		 $tr += '<button class="btn btn-white btn-sm edit" data-userid="1" data-toggle="modal" data-typemodel="add" data-idmodel="'+item.productNo+'" data-idmodel="'+item.billNo+'" data-target="#modal-form"><i class="fa fa-pencil"></i>  添加出库记录</button>';
                                 }
                                 $tr += '</div>'+
                             '</td>'+
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
    	    	if(type != 'init'){
    	    		loadData(num);	    		
    	    	}
    	    }
    	});
      
      loadData();
      var $button = null,Sort="";
      //查询
      $("#search-button,#button-simple").click(function(e){
    	  $button = e.target;
    	  $(e.target).attr('disabled',true);
    	  loadData();
      });
      
      $("#sort-store").click(function(e){
    	  var $i = $("#sort-store i");
    	 if($i.attr('class') == 'fa fa-sort' ){
    		 $i.attr('class','fa fa-sort-desc') 
    		 Sort = " order by a.surplus_qty DESC"
    	 }else if($i.attr('class') == 'fa fa-sort-desc'){
    		 $i.attr('class','fa fa-sort-asc') 
    		 Sort = " order by a.surplus_qty asc"
    	 }else{
    		 $i.attr('class','fa fa-sort') 
    		 Sort = ""
    	 }
    	  loadData();
      });
    
    });
        
        function audit(id,status){
          	$.ajax({
                  url: "/outCell/updateStatus.json",
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

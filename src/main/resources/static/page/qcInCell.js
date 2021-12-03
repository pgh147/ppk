//质检入库


    var loadData,pageNum;
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
                    			url: "/qcIncell/delete.json?id="+id,
                    			type: "get",
                    			dataType: "json",
                    			data: null,
                    			success: function(data) {
                    				swal.close();
//                    				toastr.success("删除成功");
                    				row.className="animated bounceOut";
                    				loadData(pageNum);
                    			},
                    			error:function(e){
                    				swal.close();
                    				toastr.error("删除失败");
                    			}
                    		});
                    		
                    	}
                    });
            });
          //批量删除数据
            $(document).on('click','#batch_delete', function () {
                var row=$(this).parents("tr")[0];
                var textCont = "该"+checkids.length+"条数据删除后将不可恢复!";
                    var swa = swal({
                        title: "您确定要删除这些数据吗?",
                        text: textCont,//"数据删除后将不可恢复!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#1ab394",
                        confirmButtonText: "确定删除！",
                        closeOnConfirm: false
                    }, function (isConfirm,b) {
                    	if (isConfirm) {
                    		
                    		$.ajax({
                    			url: "/qcIncell/batchDelete.json",
                    			type: "POST",
                    			dataType: "json",
                    			contentType:"application/json",
                    			data: JSON.stringify(checkids),
                    			success: function(data) {
                    				swal.close();
                    				toastr.success("批量删除成功");
                    				loadData(pageNum);
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
                    productNo: {
                        required: true,
                        rangelength: [1,12],
                    },
                    noOkQty: {
                    	number: true
                    },
                    okQty: {
                    	required: true,
                    	number: true
                    },
                    inCellQty: {
                    	required: true,
                    	number: true
                    },
                    returnQty: {
                    	number: true
                    },
                    usQty: {
                    	number: true
                    },
                    ukQty: {
                    	number: true
                    },
                    caQty: {
                    	number: true
                    }
                },
                messages: {
                	userNo: {
                        required: "请输入开发员编号",
                        rangelength: jQuery.validator.format("开发员编号应为1-12位的英文字母")
                    },
                    productNo: {
                        required: "请输入开发员编号",
                        rangelength: jQuery.validator.format("开发员编号应为1-12位的英文字母")
                    },
                    productName: {
                        rangelength: "请输入数字"
                    },
                    okQty: {
                    	number:"请输入数字",
                    	required: "请输入合格数量",
                    },
                    inCellQty: {
                    	required: "请输入入库数量",
                        rangelength: "请输入数字"
                    },
                    returnQty: "请输入数字",
                    usQty: "请输入数字",
                    ukQty: "请输入数字",
                    caQty: "请输入数字"
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
            	var button = $(event.target) // Button that triggered the modal
            	  var type = button.data('typemodel') // Extract info from data-* attributes
            	  if(type =='modlify'){
                	  $(this).find('.modal-title').text('修改质检入库记录')
            		  var id = button.data('idmodel');
            		  $.ajax({
            	          url: "/qcIncell/detail.json?id="+id,
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
            	        			  }else if(!data.flag && (item.name == 'usQty' || item.name == 'ukQty' || item.name == 'caQty')){
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
                	  $(this).find('.modal-title').text('添加质检入库记录')
            		  $.each($(':input','#input_form'),function(index,item){
            			  $(item).val('');
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
          url: "/qcIncell/edit.json",
          type: "post",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
//          data: $(form).serialize(),
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
          url: "/qcIncell/add.json",
          type: "post",
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
      //加载数据
      loadData = function loadData(num){
    	  checkids = [];
    	  pageNum = num?num:1;
    	  var param ={
    			  productNo:$("#productNo").val(),
    			  billNo:$("#billNo").val(),
    			  userNo:$("#userNo").val(),
    			  productStatus:$("#productStatus").val(),
    			  orderSql:Sort?Sort:''
    	  }
      	$.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8", 
            url: "/qcIncell/list.json?pageNum="+(num?num:1),
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
                			 $tr += '<tr><td><input onclick="checkClick(this)" type="checkbox" name="checkd" value="'+item.id+'"></td><td>'+ findInArr([{itemValue:1,itemName:"初始"},{itemValue:15,itemName:"确认"},{itemValue:20,itemName:"审核"}],item.productStatus)+'</td>';
                			 if(item.productStatus <= 15){	
                				 $tr += ' <td><a>'+item.purchaseNo+'</a></td>';
                				 $tr += '<td class="imgbox"><img width="100" height="100" src="/product/getImg/'+item.productNo+'.json" class="smallimg"><td>'+item.productNo+'</td><td><span style="width:200px" title="'+(item.productName?item.productName:"")+'"  class="long-break-word">'+item.productName+'</span></td><td><input value="'+item.userNo+'" name="userNo" onchange="onchangeInput('+"'"+item.id+"'"+',this)" class="canEdit" /></td><td>'+item.pUserNo+'</td><td>'+(item.purchaserNo?item.purchaserNo:' ')+' </td><td>'+(item.purchaseQty?item.purchaseQty:' ')+' </td><td><input value="'+(item.okQty?item.okQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="okQty"  class="canEdit" /></td>  <td><input value="'+(item.noOkQty?item.noOkQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="noOkQty"  class="canEdit" /></td>'+
                				 '<td><input value="'+(item.incellQty?item.incellQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="incellQty"  class="canEdit" /></td><td><input value="'+(item.returnQty?item.returnQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="returnQty"  class="canEdit" /></td><td>'+(item.causRemark?item.causRemark:"")+'</td><td><input value="'+(item.usQty?item.usQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="usQty"  class="canEdit" /></td><td><input value="'+(item.usQty?item.ukQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="ukQty"  class="canEdit" /></td><td><input value="'+(item.usQty?item.caQty:'0')+'" onchange="onchangeInput('+"'"+item.id+"'"+',this)" name="caQty"  class="canEdit" /></td>';
                			 }else{
                				 $tr += ' <td>'+item.purchaseNo+'</td>';
                				 $tr += '<td class="imgbox"><img width="100" height="100" src="/product/getImg/'+item.productNo+'.json" class="smallimg"><td>'+item.productNo+'</td><td><span style="width:200px" title="'+(item.productName?item.productName:"")+'"  class="long-break-word">'+item.productName+'</span></td><td>'+item.userNo+'</td><td>'+item.pUserNo+'</td><td>'+(item.purchaserNo?item.purchaserNo:' ')+' </td><td>'+(item.purchaseQty?item.purchaseQty:' ')+' </td><td>'+(item.okQty?item.okQty:'0')+'</td>  <td>'+(item.noOkQty?item.noOkQty:'0')+'</td>'+
                				 '<td>'+(item.incellQty?item.incellQty:'0')+'</td><td>'+(item.returnQty?item.returnQty:'0')+'</td><td>'+(item.causRemark?item.causRemark:'')+'</td><td>'+item.usQty+'</td><td>'+item.ukQty+'</td><td>'+item.caQty+'</td>';
                			 }
                            $tr += '<td>'+(item.qcNotice?item.qcNotice:' ')+'</td><td class="text-right text-nowrap">'+
                                 '<div class="btn-group ">';
                             if(item.productStatus <= 15){
                            	 $tr += '<button class="btn btn-white btn-sm edit" data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form"><i class="fa fa-pencil"></i>  编辑</button>';
                             }
                             //有权限
                             if(data.flag){
                            	if(item.productStatus < 15){
                            		 $tr += '<button class="btn-white  btn btn-sm rset" onclick="audit('+"'"+item.id+"'"+','+"'20'"+')"><i class="fa fa-eye"></i>审核</button>';
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
      
      $("#selectAll").click(function(e){
    	  if(this.checked){
    		  $("#table-body input[type=checkbox]").prop("checked",true)
    		  checkids = [];
    		  $.each($("#table-body input[type=checkbox]"),function(index,item){
    			  checkids.push($(item).val());
    		  })
//    		  $("#table-body input[type=checkbox]").attr("checked",true)
    	  }else{
    		  checkids = [];
    		  $("#table-body input[type=checkbox]").removeAttr("checked")
    	  }
      })
		$("#batch_delete").click(function(){
			
		})

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
      var $button = null,Sort="",sortMap={};
      //查询
      $("#search-button,#button-simple").click(function(e){
    	  $button = e.target;
    	  $(e.target).attr('disabled',true);
    	  loadData();
      });
      
      //排序
      $(".sort-return").click(function(e){
    	  var $i = $(this).find("i");
    	  var name = $(this).attr('name')
    	 if($i.attr('class') == 'fa fa-sort' ){
    		 $i.attr('class','fa fa-sort-desc') 
    		 sortMap[name] = 'desc'
    	 }else if($i.attr('class') == 'fa fa-sort-desc'){
    		 $i.attr('class','fa fa-sort-asc') 
    		 sortMap[name] = 'asc'
    	 }else{
    		 $i.attr('class','fa fa-sort') 
    		 delete sortMap[name] 
    	 }
    	  
    		  if(JSON.stringify(sortMap) != "{}")Sort = ' order by '
    		  else Sort = ""
    	  for(var item in sortMap){
    		  Sort += item + " "+sortMap[item] + " ,"
    	  }
    	  Sort.length > 1?Sort = Sort.substring(0,Sort.length -1):''
    	  loadData();
      });
      
      
    
    });
        
        function audit(id,status){
          	$.ajax({
                  url: "/qcIncell/updateStatus.json",
                  type: "post",
                  contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                  dataType: "json",
                  data: {id:id,productStatus:status},
                  success: function(data) {
                    toastr.success('', '修改成功！');
                    $('#modal-form').modal('hide');
                    loadData(pageNum);
                  },
                  error:function(e){
                	  toastr.error("出现错误，请更改");
                  }
                });
          }        
        
      function  onchangeInput(id,self){
    	  	var param = {"id":id};
    	  	param[$(self).attr('name')]=$(self).val();
    	  	if($(self).attr('name') != "userNo"){
    	  		param['userNo']=$(self).parent().parent().find('input[name=userNo]').val();
    	  	}
    	  	var me = self;
          	$.ajax({
//                url: "/qcIncell/updateStatus.json",
//                type: "post",
//                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
//                dataType: "json",
//                data: param,
                url: "/qcIncell/edit.json",
                type: "post",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data:JSON.stringify(param),
                success: function(data) {
//                  toastr.success('', '修改成功！');
//                  $('#modal-form').modal('hide');
//                  loadData();
                	if(data.status ==1){
                		$(me).parent().removeClass("no-ok").addClass("ok");                		
                	}else{
                		 $(me).parent().removeClass("ok").addClass("no-ok");
                	}
                },
                error:function(e){
//              	  toastr.error("修改出现错误，请更改");
              	  $(me).parent().addClass("no-ok");
                }
              });
        }  
      var checkids = [];
      function checkClick(e){
    	  if(e.checked ){
    		 if( $("#table-body input[type=checkbox]:checked").length == $("#table-body input[type=checkbox]").length){
    			 $("#selectAll").prop("checked",true)
    		 }
    		  if(checkids.indexOf($(e).val())<0){
    			  checkids.push($(e).val());
    		  }
    	  }else{
    		  $("#selectAll").removeAttr("checked");
    		  if(checkids.indexOf($(e).val()) > -1){
    			  checkids.splice(checkids.indexOf($(e).val()),1);
    		  }
    	  }
      }
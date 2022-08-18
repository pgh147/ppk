


    var loadData,pageNum=0;
        $(document).ready(function () {
            //关闭模态框清空表单值
            $("#myModa-reset").on('hidden.bs.modal', function (event) {
                $(this).find("input").val("");
            });
            $("#input_form").validate({
             	rules: {
             		issues: {
                         required: true
                     },
                     productNo: {
                         required: true
                     },
                 },
                 messages: {
                	 issues: {
                         required: "请输入待解决事项",
                     },
                     productNo: {
                         required: "请输入产品编号",
                     }
                 },
             	debug: true,
 		        submitHandler: function(form) {
 		        	if ($("#produc_id_h").val()) {
						editform(form)
					} else {
						addform(form);
					}
 		        }
             });
            
         // t添加
			function addform(form) {
				var data = {};
				var t = $(form).serializeArray();
				$.each(t, function() {
					data[this.name] = this.value;
				});
				$.ajax({
					url : "/issues/add.json",
					type : "post",
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					data : JSON.stringify(data),
					success : function(data) {
						if(data.status == 0){
							toastr.error("出现错误，请更改");
						}else{							
							toastr.success('', '添加成功！');
							$('#modal-form').modal('hide');
							loadData(pageNum);
						}
					},
					error : function(e) {
						toastr.error("出现错误，请更改");
					}
				});
			}
			$('#modal-form')
					.on(
							'show.bs.modal',
							function(e) {
//								debugger;
								var button = ''
								if(event.target.nodeName == 'BUTTON'){
									button = $(event.target) // Button
								}else{
									button = $(event.path[1])
								}
																// that
																// triggered
																// the
																// modal
//								debugger
								var type = button.data('typemodel') // Extract
																	// info
																	// from
																	// data-*
																	// attributes
								if (type == 'modlify') {
									$(this).find('.modal-title').text(
											'修改记录')
									var id = button.data('idmodel');
									$
											.ajax({
												url : "/issues/detail.json?id="
														+ id,
												type : "get",
												dataType : "json",
												data : null,
												success : function(data) {
													if (data.page) {
													
														$.each(
																		$(
																				':input',
																				'#input_form'),
																		function(index,item) {
																			console.log(item.name,data.page[item.name])
																			if(item.name != 'isOk'){																				
																				$(item).val(data.page[item.name]);
																			}
																				
																		})
															$('input[name=isOk][value=1]','#input_form').prop('checked',true)			
																		
													}
												},
												error : function(e) {
													toastr
															.error("获取明细错误");
												}
											});

								} else {
									$(this).find('.modal-title').text(
											'添加记录')
									$.each($(':input', '#input_form'),
											function(index, item) {
												$(item).val('');
											})
								}
							})
			// 删除数据
			$(document)
					.on(
							'click',
							'#editable-sample button.delete',
							function() {
								var row = $(this).parents("tr")[0];
								var id = $(this).data("idmodel");
								var swa = swal(
										{
											title : "您确定要删除吗?",
											text : "删除后将不可恢复!",
											type : "warning",
											showCancelButton : true,
											confirmButtonColor : "#1ab394",
											confirmButtonText : "确定删除！",
											closeOnConfirm : false
										},
										function(isConfirm, b) {
											if (isConfirm) {

												$
														.ajax({
															url : "/issues/delete.json?id="
																	+ id,
															type : "get",
															dataType : "json",
															data : null,
															success : function(
																	data) {
																swal
																		.close();
																// toastr.success("删除成功");
																row.className = "animated bounceOut";
																loadData(pageNum);
															},
															error : function(
																	e) {
																swal
																		.close();
																toastr
																		.error("删除失败");
															}
														});

											}
										});
							});

      //加载数据
      loadData = function loadData(num){
    	  var param ={
    			  productName:$("#productName").val(),
    			  productNo:$("#productNo").val(),
    			  isOk:$("#isOk").val()
    	  }
      	$.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8", 
            url: "/issues/list.json?pageNum="+(num?num:1),
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
                			 $tr += '<tr><td class="imgbox"><img width="100" height="100" src="/product/getImg/'+item.productNo+'.json" class="smallimg"></td><td> <a href="/product/detail/page?id='+item.id+'&pageNum='+(num?num:1)+'" >'+item.productNo+' </a></td>';

                			 $tr +='<td>'+item.userNo+'</td><td><pre>'+item.issues+'</pre></td> <td>'+item.createTime+'</td>'; 
                			 
                			 $tr +='<td>'+(item.isOk==1?'未解决':'已解决')+'</td><td class="text-right text-nowrap">'+
                                 '<div class="btn-group " >';
                                 if(item.isOk == 1 ){
                                	 $tr += '<button class="btn btn-white btn-sm edit" data-userid="1" data-toggle="modal" data-typemodel="modlify" data-idmodel="'+item.id+'" data-target="#modal-form"><i class="fa fa-pencil"></i>  编辑</button>';
                                 }
                                     if(data.flag){
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
	    		pageNum = num;
	    		loadData(pageNum);	    		
	    	}
	    }
	});

      var params = window.location.href.substring(window.location.href.indexOf("?")+1).split("&");
  	$.each(params,function(index,item){
  		if(item.indexOf("pageNum=") == 0 ){
  			pageNum = item.split("=")[1];
  			return true;
  		}
  	});
      loadData(pageNum);

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
    	  window.open("/issues/big_simpleExport.json?"+$.param(param), "_blank");
      });
    });
        function editform(form) {
        	  var data = {};
    	    var t = $(form).serializeArray();
    	    $.each(t, function() {
    	      data[this.name] = this.value;
    	    });
      $.ajax({
        url: "/issues/edit.json",
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
//        function audit(id,status){
//        	
//          	$.ajax({
//                  url: "/issues/updateStatus.json",
//                  type: "post",
//                  contentType:"application/x-www-form-urlencoded; charset=UTF-8",
//                  dataType: "json",
//                  data: {id:id,productStatus:status},
//                  success: function(data) {
//                    toastr.success('', '修改成功！');
//                    $('#modal-form').modal('hide');
//                    loadData(pageNum);
//                  },
//                  error:function(e){
//                	  toastr.error("出现错误，请更改");
//                  }
//                });
//          	
//          }        

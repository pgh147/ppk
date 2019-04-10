
window.onbeforeunload = function(e)
{ 
	e.returnValue="确定离开当前页面吗？";
    return e;
}

    var loadData;
        $(document).ready(function () {
        	$('#descript').on('input', function (event) {
			 	if ($(this).prop('comStart')) return;    // 中文输入过程中不截断
			 	var val = $(this).val();
			 	if(val){
	        			var hasChinessLength= chineseLeng(val);
	        			var enLength = val.length - hasChinessLength;
	        			$("#descriptSpan").text(2000-enLength-hasChinessLength*3);
	        		}
	        }).on('compositionstart', function (event) {
	            $(this).prop('comStart', true);
	        }).on('compositionend', function (event) { //在确定中文输入完成后触发获取输入框内容
	        	 var val = $(this).val();
	        	 if(val){
	        			var hasChinessLength= chineseLeng(val);
	        			var enLength = val.length - hasChinessLength;
	        			$("#descriptSpan").text(2000-enLength-hasChinessLength*3);
	        		}
		        $(this).prop('comStart', false);
	        });
        	$('#searchTerm').on('input', function (event) {
			 	if ($(this).prop('comStart')) return;    // 中文输入过程中不截断
			 	var val = $(this).val();
			 	if(val){
	        			var hasChinessLength= chineseLeng(val);
	        			var enLength = val.length - hasChinessLength;
	        			$("#searchTermSpan").text(250-enLength-hasChinessLength*3);
	        		}
	        }).on('compositionstart', function (event) {
	            $(this).prop('comStart', true);
	        }).on('compositionend', function (event) { //在确定中文输入完成后触发获取输入框内容
	        	 var val = $(this).val();
	        	 if(val){
	        			var hasChinessLength= chineseLeng(val);
	        			var enLength = val.length - hasChinessLength;
	        			$("#searchTermSpan").text(250-enLength-hasChinessLength*3);
	        		}
		        $(this).prop('comStart', false);
	        });
        	$('#enTitle').on('input', function (event) {
        				 	if ($(this).prop('comStart')) return;    // 中文输入过程中不截断
        				 	var val = $(this).val();
        				 	if(val){
       		        			var hasChinessLength= chineseLeng(val);
       		        			var enLength = val.length - hasChinessLength;
       		        			$("#enTitleSpan").text(200-enLength-hasChinessLength*3);
       		        		}
        		        }).on('compositionstart', function (event) {
        		            $(this).prop('comStart', true);
        		        }).on('compositionend', function (event) { //在确定中文输入完成后触发获取输入框内容
        		        	 var val = $(this).val();
        		        	 if(val){
        		        			var hasChinessLength= chineseLeng(val);
        		        			var enLength = val.length - hasChinessLength;
        		        			$("#enTitleSpan").text(200-enLength-hasChinessLength*3);
        		        		}
        			        $(this).prop('comStart', false);
        		        });


            function chineseLeng(temp){
                var reg = /[\u4e00-\u9fa5]/g;
                var mathChar = temp.match(reg);
                if(mathChar){
                	return mathChar.length;
                }
                return 0;
            }
            
            function claCounts(str,char){
            	var count = 0,pos = str.indexOf(char);
            	while(pos > -1){
            		count ++;
            		pos = str.indexOf(char,pos+1);
            	}
            	return count;
            }

            
            //关闭模态框清空表单值
            $("#myModa-reset").on('hidden.bs.modal', function (event) {
                $(this).find("input").val("");
            });

            $("#input_form").validate({
            	rules: {
            		userNo: {
                        required: true,
                        rangelength: [1, 12],
                    },
                    uploadAccount: {
                        required: true,
                        rangelength: [1, 50],
                    },
                    productNo: {
                        required: true,
                        rangelength: [1,12],
                    },
                    productName: {
                        required: true,
                    },
                    productPrice: {
                    	number: true
                    },
//                    supplierLink: "url"
                },
                messages: {
                	userNo: {
                        required: "请输入开发员编号",
                        rangelength: jQuery.validator.format("开发员编号应为1-12位的英文字母")
                    },
                    uploadAccount: {
                        required: "请填写上传账号",
                        rangelength: jQuery.validator.format("上传账号应为1-50位的英文字母")
                    },
                    productNo: {
                        required: "请输入开发员编号",
                        rangelength: jQuery.validator.format("开发员编号应为1-12位的英文字母")
                    },
                    productName: {
                        required: "请输入产品名称"
                    },
                    productPrice: "请输入数字",
//                    supplierLink: "请输入合法链接"
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
            $("#productImgData").change(function(){
            	gen_base64();
            });
            function $_(id) {
                    return document.getElementById(id);
            }
            function gen_base64() {
                var file = document.getElementById('productImgData').files[0];
                if(file.size > 51535){
                	toastr.error('图片过大,不能大于51K');
                	return false;
                }
                r = new FileReader();  //本地预览
                r.onload = function(){
                	$("#show-img-data").empty().append("<img src='"+r.result+"' >");
                	$('#isUpdateImg_id_h').val(true);
                    $('#productImgData_h').val(r.result);
                }
                r.readAsDataURL(file);    //Base64
            }    
            
            if(window.location.href.indexOf("?") >= 0){
            	var params = window.location.href.substring(window.location.href.indexOf("?")+1).split("&");
            	var id ;
            	$.each(params,function(index,item){
            		if(item.indexOf("id=") == 0){
            			id = item.split("=")[1];
            		}
            	});
            	if(id){
            		//编辑
            		$.ajax({
          	          url: "/product/detail.json?id="+id,
          	          type: "get",
          	          dataType: "json",
          	          data: null,
          	          success: function(data) {
          	        	  if(data.page){
          	        		  $("#show-img-data").append("<img src='"+data.page.productImgData+"' >");
          	        		  $.each($(':input','#input_form'),function(index,item){
          	        			  $(item).val(data.page[item.name]);
          	        			  if(item.name == 'productNo' && data.page[item.name]){
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
            		//新增
            		
            	}
            }
//            $('#modal-form').on('show.bs.modal', function (e) {
//            	$("#show-img-data").empty();
////            	$(item).attr("disabled",true);
//            	var button = $(event.target) // Button that triggered the modal
//            	  var type = button.data('typemodel') // Extract info from data-* attributes
//            	  if(type =='modlify'){
//                	  $(this).find('.modal-title').text('修改产品')
//            		  var id = button.data('idmodel');
//            		  $.ajax({
//            	          url: "/product/detail.json?id="+id,
//            	          type: "get",
//            	          dataType: "json",
//            	          data: null,
//            	          success: function(data) {
//            	        	  if(data.page){
//            	        		  $("#show-img-data").append("<img src='"+data.page.productImgData+"' >");
//            	        		  $.each($(':input','#input_form'),function(index,item){
//            	        			  $(item).val(data.page[item.name]);
//            	        			  if(item.name == 'productNo' && data.page[item.name]){
//            	        				  $(item).attr("readonly",true);
//            	        			  }
//            	        		  })
//            	        	  }
//            	          },
//            	          error:function(e){
//            	        	  toastr.error("获取明细错误");
//            	          }
//            	        });
//            		  
//            	  }else{
//                	  $(this).find('.modal-title').text('添加产品')
//            		  $.each($(':input','#input_form'),function(index,item){
//            			  $(item).val('').attr("readonly",false);;
//            		  })
//            	  }
//            	})
            	
            	//修改
      function editform(form) {
          	  var data = {};
      	    var t = $(form).serializeArray();
      	    $.each(t, function() {
      	      data[this.name] = this.value;
      	    });
        $.ajax({
          url: "/product/edit.json",
          type: "post",
//          contentType:"application/x-www-form-urlencoded; charset=UTF-8",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
//          data: $(form).serialize(),
          data:JSON.stringify(data),
          success: function(data) {
            toastr.success('', '添加成功！');
            $('#modal-form').modal('hide');
//            loadData();
            window.location.href = '/product/upload/page';
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
          url: "/product/add.json",
          type: "post",
//          contentType:"application/x-www-form-urlencoded; charset=UTF-8",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
//          data: $(form).serialize(),
          data:JSON.stringify(data),
          success: function(data) {
            toastr.success('', '添加成功！');
            $('#modal-form').modal('hide');
//            loadData();
            window.location.href = '/product/upload/page';
          },
          error:function(e){
        	  toastr.error("出现错误，请更改");
          }
        });
      }
  
    
    });
            

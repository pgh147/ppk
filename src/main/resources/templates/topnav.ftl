<div class="navbar-header">
	<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
</div>
<ul class="nav navbar-top-links navbar-right notification-menu">

	<li class="dropdown"><a class="dropdown-toggle count-info" data-toggle="dropdown" href="#"> <i class="fa fa-envelope"></i> <span class="label label-warning">16</span>
	</a>
		<div class="dropdown-menu dropdown-menu-head pull-right">
			<h5 class="title">You have 5 Mails</h5>
			<ul class="dropdown-list normal-list">
				<li class="new"><a href=""> <span class="thumb"><img src="${ctx}/static/images/photos/user1.png" alt=""></span> <span class="desc"> <span class="name">陈小林 <span class="badge badge-success">new</span></span> <span class="msg">小帅哥晚上有空吗？一起出来玩吧！...</span>
					</span>
				</a></li>
				<li><a href=""> <span class="thumb"><img src="${ctx}/static/images/photos/user2.png" alt=""></span> <span class="desc"> <span class="name">Jonathan Smith</span> <span class="msg">Lorem ipsum dolor sit amet...</span>
					</span>
				</a></li>
				<li><a href=""> <span class="thumb"><img src="${ctx}/static/images/photos/user3.png" alt=""></span> <span class="desc"> <span class="name">Jane Doe</span> <span class="msg">Lorem ipsum dolor sit amet...</span>
					</span>
				</a></li>
				<li><a href=""> <span class="thumb"><img src="${ctx}/static/images/photos/user4.png" alt=""></span> <span class="desc"> <span class="name">Mark Henry</span> <span class="msg">Lorem ipsum dolor sit amet...</span>
					</span>
				</a></li>
				<li><a href=""> <span class="thumb"><img src="${ctx}/static/images/photos/user5.png" alt=""></span> <span class="desc"> <span class="name">Jim Doe</span> <span class="msg">Lorem ipsum dolor sit amet...</span>
					</span>
				</a></li>
				<li class="new"><a href="">Read All Mails</a></li>
			</ul>
		</div></li>
	<li class="dropdown"><a class="dropdown-toggle count-info" data-toggle="dropdown" href="#"> <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
	</a>
		<div class="dropdown-menu dropdown-menu-head pull-right">
			<h5 class="title">Notifications</h5>
			<ul class="dropdown-list normal-list">
				<li class="new"><a href=""> <span class="label label-danger"><i class="fa fa-bolt"></i></span> <span class="name">Server #1 overloaded. </span> <em class="small">34 mins</em>
				</a></li>
				<li class="new"><a href=""> <span class="label label-danger"><i class="fa fa-bolt"></i></span> <span class="name">Server #3 overloaded. </span> <em class="small">1 hrs</em>
				</a></li>
				<li class="new"><a href=""> <span class="label label-danger"><i class="fa fa-bolt"></i></span> <span class="name">Server #5 overloaded. </span> <em class="small">4 hrs</em>
				</a></li>
				<li class="new"><a href=""> <span class="label label-danger"><i class="fa fa-bolt"></i></span> <span class="name">Server #31 overloaded. </span> <em class="small">4 hrs</em>
				</a></li>
				<li class="new"><a href="">See All Notifications</a></li>
			</ul>
		</div></li>

	<li class="user-dropdown">
	<a href="#" class="btn  dropdown-toggle" data-toggle="dropdown" style="padding: 10px 0 0 0;"> 
	
	<!--
	<img src="${ctx}/static/images/photos/user-avatar.png" alt="" width="20"> 
	-->
	<div style="width: 90px;display: inline-block;">
		<div>${role!'暂无'}</div>
		<div><@shiro.principal /></div>
	</div>

              
	
	<span class="caret"></span>
	</a>
		<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
			<li><a href="#modal-updateUserInfo" data-toggle="modal" data-backdrop="false"><i class="fa fa-user"></i> 用户信息</a></li>
			<li><a href="#modal-updatePassword" data-toggle="modal" data-backdrop="false"><i class="fa fa-cog"></i> 修改密码</a></li>
			<li><a href="${ctx}/logout"><i class="fa fa-sign-out"></i> 退出</a></li>
		</ul></li>
</ul>



 <div class="modal fade" modal-backdrop="false" id="modal-updatePassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel5">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">修改密码</h4>
                </div>
                <div class="modal-body">

                       <form name="entity" id="input_updatePassword" class="form-horizontal" enctype="multipart/form-data">
                       
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 原密码 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" class="file-loading" name="oldPassword" id="oldPassword"  >
								</div>
							</div>		
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 新密码 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" class="file-loading" name="newPassword" id="newPassword"  >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 确认新密码 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" class="file-loading" name="newPassword2" id="newPassword2"  >
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">
										<i class="fa fa-check"></i> 填写完成，提交！
									</button>
									<button class="btn btn-white" type="reset">取消</button>
								</div>
							</div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade" modal-backdrop="false" id="modal-updateUserInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel5">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">用户信息</h4>
                </div>
                <div class="modal-body">

                       <form name="entity" id="input_updateUserInfo" class="form-horizontal" enctype="multipart/form-data">
                       
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 用户编号 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" class="file-loading" name="username"  readonly="true" >
								</div>
							</div>		
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> 用户名称 </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" class="file-loading" name="trueName"   >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-3 control-label" for="logoFile"> email </label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" class="file-loading" name="email"   >
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">
										<i class="fa fa-check"></i> 填写完成，提交！
									</button>
									<button class="btn btn-white" type="reset">取消</button>
								</div>
							</div>
                    </form>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
$(document).ready(function () {


	$("#input_updatePassword").validate({
    	rules: {
    		oldPassword: {
                required: true
            },
            newPassword: {
                required: true
            },
            newPassword2: {
                required: true
            }
        },
        messages: {
        	oldPassword: {
                required: "请输入密码",
            },
            newPassword: {
                required: "请输入需要更改的密码",
            },
            newPassword2: {
                required: "请确认输入需要更改的密码",
            }
        },
    	debug: true,
        submitHandler: function(form) {
        	
        	$.ajax({
          url: "/user/updatePassword.json",
          type: "post",
          contentType:"application/x-www-form-urlencoded; charset=UTF-8",
          dataType: "json",
          data: $(form).serialize(),
          success: function(data) {
            toastr.success('', '修改成功，请退出重新登录！');
            $('#modal-updatePassword').modal('hide');
          },
          error:function(e){
        	  toastr.error("出现错误，请更改");
          }
        });
        	
        	
        }
    });


$("#input_updateUserInfo").validate({
    	rules: {
    		username: {
                required: true
            },
            trueName: {
                required: true
            },
            email: {
                required: true
            }
        },
        messages: {
        	username: {
                required: "请输入账号",
            },
            trueName: {
                required: "请输入姓名",
            },
            email: {
                required: "请输入email",
            }
        },
    	debug: true,
        submitHandler: function(form) {
        	debugger;
        	$.ajax({
          url: "/user/updateUserInfo.json",
          type: "post",
          contentType:"application/x-www-form-urlencoded; charset=UTF-8",
          dataType: "json",
          data: $(form).serialize(),
          success: function(data) {
            if(data.retCode == '0'){
	            toastr.success('', '修改成功，请退出重新登录！');
	            $('#modal-updateUserInfo').modal('hide');
            }else{
            	toastr.error(data.message);
            }
          },
          error:function(e){
        	  toastr.error("出现错误，请更改");
          }
        });
        	
        	
        }
    });

		$('#modal-updateUserInfo').on('show.bs.modal', function (e) {
            		  $.ajax({
            	          url: "/user/userInfo.json",
            	          type: "get",
            	          dataType: "json",
            	          data: null,
            	          success: function(data) {
            	        	  if(data.data){
            	        		  $.each($(':input','#input_updateUserInfo'),function(index,item){
            	        			  $(item).val(data.data[item.name]);
            	        		  })
            	        	  }
            	          },
            	          error:function(e){
            	        	  toastr.error("获取明细错误");
            	          }
            	        });
            	})
    
    });
</script>
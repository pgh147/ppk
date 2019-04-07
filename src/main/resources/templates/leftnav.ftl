<div class="sidebar-scroll">
	<div class="logo">
		<a href="index.html">&nbsp;&nbsp;<img src="${ctx }/static/images/logo_icon.png" alt="" /></a>
	</div>
	<div class="sidebar-collapse">
		<div class="nav-header" id="side-head">
			<div class="dropdown profile-element text-center">
			<!--
				<span><img alt="image" class="img-circle " src="${ctx }/static/img/profile_small.jpg" /></span> 
				-->
				<a data-toggle="dropdown" class="dropdown-toggle" href="#"><span class="clear"> <span class="block m-t-xs"> <strong class="font-bold"><@shiro.principal /></strong></span>
						<span class="text-muted text-xs block">用户设置<b class="caret"></b></span>
				</span> </a>
				<ul class="dropdown-menu animated fadeInRight m-t-xs">
					<li><a href="#modal-updateUserInfo" data-toggle="modal" data-backdrop="false">用户信息</a></li>
					<li><a href="#modal-updatePassword" data-toggle="modal" data-backdrop="false">修改密码</a></li>
					<li class="divider"></li>
					<li><a href="${ctx}/logout">退出</a></li>
				</ul>
			</div>
		</div>
		<ul class="nav metismenu" id="side-menu">
			<#if permission_session?? && (permission_session?size > 0) >
				<#list permission_session as one>
					<#if one.children?? && (one.children?size > 0) >
					<li class="active">
					<#else>
					<li >
					</#if>
						<#if one.url??>
							<a href="${ctx}/${one.url!''}">
						<#else>
							<a href="#">
						</#if>
						<#if one.children?? && (one.children?size > 0) >
							<i class="fa ${one.cssClass!''}"></i> <span class="nav-label">${one.name!''}</span><span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
								<#list one.children as two>
									<li>
										<#if two?? && two.url??>
											<a href="${ctx}/${two.url!''}">
										<#else>
											<a href="#">
										</#if>
										<i class="fa ${two.cssClass!''}"></i>${two.name!''}</a>
										<#if two.children?? && (two.children?size > 0) >
											<ul class="nav nav-third-level">
												<#list two.children as three>
													<li><a href="${ctx }/${three.url!''}"><i class="fa ${three.cssClass!''}"></i>${three.name!''}</a></li>
												</#list>
											</ul>
										</#if>
									</li>
								</#list>
							</ul>
						<#else>
							<i class="fa ${one.cssClass!''}"></i> <span class="nav-label">${one.name!''}</span></a>
						</#if>
					</li>
				</#list>
			</#if>
<!-- 
			<li class="landing_link"><a target="_blank" href="landing.html"><i class="fa fa-star"></i> <span class="nav-label">系统更新</span> <span class="label label-warning pull-right">NEW</span></a></li>
			<li class="special_link"><a href="package.html"><i class="fa fa-database"></i> <span class="nav-label">购买服务</span></a></li>
		-->
		</ul>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	$.each($("#side-menu a"),function(index,item){
		if($(item).attr('href') == window.location.pathname){
			$(item).addClass("active");
		}else{
			$(item).removeClass("active");
		}
	});

 });
</script>
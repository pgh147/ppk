<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理系统登录</title>
    <meta name="keyword" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="Author" content="zifan">
    <meta name="copyright" content="All Rights Reserved">
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
html,body {
	height:100%;
	background-color: #f5f5f5;
	    background: url(/static/images/reg-bg.png) no-repeat;
    background-size: 100%;
}
.container{
	    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.form-signin {
    max-width: 300px;
    padding: 19px 29px 29px;
    margin: 0 auto 20px;
    background-color: #00BCD4;
    opacity: 0.7;
    /**
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;

	**/
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}

.login-error {
	color: #C7254E;
	font-size: 90%;
	display: block;
	white-space: nowrap;
}
</style>
<!--[if lt IE 9]>
    <script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
    <script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">
		<form action="${ctx }/login" class="form-signin" id="userForm" method="POST">
			<h3 class="form-signin-heading">请登录</h3>
			<input type="text" class="form-control input-block-level" id="username" name="username" placeholder="用户" >
			<input type="password" class="form-control input-block-level" id="password" name="password" placeholder="密码" >
			<button class="btn btn-large btn-primary" id="loginbutton" type="submit">登录</button>
			</br>
			</br>
		</form>
	</div>
	    <script src="${ctx}/static/js/jquery-2.1.1.js"></script>
	<script type="text/javascript">
		document.onkeydown = function (e) { // 回车提交表单
// 兼容FF和IE和Opera
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        $("#loginbutton").click();
    }
}
	</script>
</body>
</html>

var vue = new Vue({
	el: "#app",
	data: {
		phone: "",
		password: "",
		loginInfoFlag: false,
		msg: "",
		loginErr: 0
	},
	methods: {
		login: function() {
		
			// 判断用户信息是否为空，为空则在页面提示，并停止代码继续向下执行
			if (vue.phone == "" || vue.password == "") {
				vue.msg = "用户名或密码不能为空";
				vue.loginInfoFlag = true;
				return;
			}

			// 判断如果错误记录小于三次，则直接判断用户信息不进入拼图验证
			if (vue.loginErr < 3) {
				vue.loginAuthentication();
				return;
			}
			// 打开拼图验证码
			vue.openDiv();
		},
		// 拼图验证码
		openDiv: function() {
			// 判断如果错误记录小于三次则直接退出函数
			// if (vue.loginErr < 3) {
			// 	return;
			// }
			document.getElementById('captcha').innerHTML = '';
			jigsaw.init({
				el: document.getElementById('captcha'),
				// 拼图验证成功后执行
				onSuccess: function() {
					// 关闭拼图窗口
					$("#divSCA").CloseDiv();
					// 调用登录信息验证
					vue.loginAuthentication();
				}
			})
			// 如果拼图验证失败则打开新的拼图验证窗口
			$("#divSCA").OpenDiv();
		},
		// 用户登录信息验证
		loginAuthentication: function() {
			// 通过 Ajax 向后端发送请求
			axios({
				method: "post",
				url: "http://user.lxsc.com:8081/login",
				params: { // 发送请求携带的参数
					phone: vue.phone,
					password: vue.password
				},
			}).then(function(res) { // res 为后端响应的信息
				// 获取 data 变量
				var data = res.data;
				console.log(data);
				 // 判断登录状态码，等于 555 表示用户已锁定
				if (data.result != null && data.result == 555) {
					vue.msg = data.msg;
					vue.loginInfoFlag = true;
					return;
				} else if (data.code != 10000) { // 判断登录状态码，不等于 10000 表示登录失败，在页面提示失败信息，并停止代码继续向下执行
					// 每次登录错误都会增加一次记录
					vue.loginErr = vue.loginErr + 1;
					vue.msg = "用户名或密码错误，请检查大小写";
					vue.loginInfoFlag = true;
					return;
				}

				vue.loginErr = 0;
				// 登录成功后将提示信息改为空，不显示，并跳转首页
				vue.msg = "";
				vue.loginInfoFlag = false;
				// 将后端返回的用户信息保存到 session 会话中
				sessionStorage.setItem("token", data.result.token)
				sessionStorage.setItem("id", data.result.id)
				sessionStorage.setItem("phone", data.result.phone)
				sessionStorage.setItem("nickName", data.result.nickName)
				// 登录成功跳转地址，登录后跳转到登录前的页面中。如果登录前没有任何页面则跳转首页
				if (sessionStorage.getItem("loginPagePath") != null) {
					window.location.href = sessionStorage.getItem("loginPagePath");
				} else {
					window.location.href = "index.html";
				}
			});
		}
	}
});
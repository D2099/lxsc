// const {
// 	axios
// } = require("../axios.min");

var vue = new Vue({
	el: "#app",
	data: {
		phone: "",
		nickName: "",
		password: "",
		confirmPassword: "",
		readInfoFlag: "",
		regInfoFlag: true,
		phoneInfo: "",
		confirmPWInfo: "",
		readInfo: "",
	},
	methods: {
		reg: function() {
			// 信息获取测试
			// console.log(
			// 	"phone:" + vue.phone + 
			// 	"\nnickName:" + vue.nickName + 
			// 	"\npassword:" + vue.password + 
			// 	"\nconfirmPassword:" + vue.confirmPassword + 
			// 	"\nreadInfoFlag:" + vue.readInfoFlag
			// );

			// 进行注册信息判断
			if (vue.phone == "") {
				vue.phoneInfo = "手机号码不能为空";
				return;
			} else {
				vue.phoneInfo = "";
			}
			if (vue.password != vue.confirmPassword) {
				vue.confirmPWInfo = "输入的密码不一致";
				return;
			} else if (vue.password == "" || vue.confirmPassword == "") {
				vue.confirmPWInfo = "密码不能为空";
				return;
			} else {
				vue.confirmPWInfo = "";
			}
			if (!vue.readInfoFlag) {
				vue.readInfo = "请阅读并勾选协议";
				return;
			} else {
				vue.readInfo = "";
			}

			axios({
				method: "post",
				url: "http://user.lxsc.com:8081/registry",
				params: {
					phone: vue.phone,
					nickName: vue.nickName,
					password: vue.password
				}
			}).then(function(res) {
				console.log(res);
				var data = res.data;
				
				if (data.code == "10002") {
					vue.phoneInfo = data.msg;
					return;
				} else {
					vue.phoneInfo = "";
				}
				
				sessionStorage.setItem("token", data.result.token);
				sessionStorage.setItem("phone", data.result.phone);
				sessionStorage.setItem("nickName", data.result.nickName);
				window.location.href="registry_login.html";
			});
		}

	}
});
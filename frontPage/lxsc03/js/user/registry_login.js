var vue = new Vue({
	el: "#app",
	data: {
		registrationSuccessfulJumpCountdown: 4,
		time: null
	},
	methods: {
		
	}
});

// 注册成功自动跳转首页
window.onload = function onload() {
	vue.registrationSuccessfulJumpCountdown = vue.registrationSuccessfulJumpCountdown - 1;
	vue.time = setTimeout("onload()", 1000);
	if (!(vue.registrationSuccessfulJumpCountdown > 0)) {
		clearTimeout(vue.time);
		vue.time = null;
		window.location.href = "index.html";
	}
}
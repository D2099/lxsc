var vue = new Vue({
	el: "#app",
	data: {
		token: sessionStorage.getItem("token"),
		id: sessionStorage.getItem("id"),
		phone: sessionStorage.getItem("phone"),
		nickName: sessionStorage.getItem("nickName"),
	},
	methods: {
		loginPage: function() {
			sessionStorage.setItem("loginPagePath", window.location.href)
		}
	}
});
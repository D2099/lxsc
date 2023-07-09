var vue = new Vue({
	el: "#app",
	data: {
		token: sessionStorage.getItem("token"),
		confirmUserAddressList: "",
		confirmOrdersInfoList: "",
		ordersMoney: "",
		addressId: ""
	},
	methods: {
		getConfirmUserAddressList: function() {
			axios({
				method: "get",
				url: "http://user.lxsc.com:8081/getConfirmUserAddressList",
				params: {
					token: vue.token
				}
			}).then(function(res) {
				var data = res.data;
				console.log(data);
				if (data.code != 10000) {
					alert(data.msg);
					return;
				}
				vue.confirmUserAddressList = data.result;
				vue.addressId = data.result[0].id;
				console.log(data.result[0].name);
			});
		},
		getConfirmOrdersInfoList: function() {
			axios({
				method: "get",
				url: "http://orders.lxsc.com:8083/getConfirmOrdersInfoList",
				params: {
					ordersId: sessionStorage.getItem("ordersId")
				}
			}).then(function(res) {
				var data = res.data;
				console.log(data);
				vue.confirmOrdersInfoList = data.result;
				vue.ordersMoney = sessionStorage.getItem("ordersMoney");
			});
		},
		toPay: function() {
			// alert("跳转支付~");
			// alert("addressId:" + vue.addressId);
			sessionStorage.setItem("addressId", vue.addressId);
			window.location.href = "payply.html";
		}
	}
});

vue.getConfirmUserAddressList();
vue.getConfirmOrdersInfoList();
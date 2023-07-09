var vue = new Vue({
	el: "#app",
	data: {
		token: sessionStorage.getItem("token"),
		ordersId: sessionStorage.getItem("ordersId"),
		orderNo: sessionStorage.getItem("orderNo"),
		ordersMoney: sessionStorage.getItem("ordersMoney"),
		addressId: sessionStorage.getItem("addressId"),
		point: 0
	},
	methods: {
		
	}
});
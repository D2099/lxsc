var vue=new Vue({
	el:"#app",
	data:{
		goodsId: ""
	},methods:{
		// num: function(){},
		// confimOrders: function(){},
		totalNum: function(){},
		A: function(){},
		B: function(){},
		C: function(){},
		haveImg: function(){},
		evaluateList: function(){},
		pageNo: function(){},
		pageCount: function(){},
		secKill: function() {
			axios({
				method: "get",
				url: "http://goods.seckill.lxsc.com:8085/secKill",
				params: {
					goodsId: vue.goodsId
				}
			}).then(function(res) {
				var data = res.data;
				// console.log(data);
				if (data.code != 10000) {
					alert(data.msg);
					return;
				}
				vue.getOrdersInfo();
			});
		},
		getOrdersInfo: function() {
			axios({
				method: "get",
				url: "http://orders.seckill.lxsc.com:8086/getOrdersInfo",
				params: {
					goodsId: vue.goodsId
				}
			}).then(function(res) {
				var data = res.data;
				console.log(data);
				if (data.code != 10000) {
					window.setTimeout("vue.getOrdersInfo()", 3000);
				}
				alert(
					"订单ID:" + data.result.id + "\n" + 
					"商品ID:" + data.result.goodsId + "\n" +
					"用户ID:" + data.result.uid + "\n" +
					"价格:" + data.result.buyPrice + "\n" + 
					"跳转支付页面~"
				)
			});
		}
	}
})

// 获取商品 ID 并赋值
var path = window.location.href;
var lastIndex1 = path.lastIndexOf("/");
var lastIndex2 = path.lastIndexOf(".");
vue.goodsId = path.substring(lastIndex1 + 1, lastIndex2);
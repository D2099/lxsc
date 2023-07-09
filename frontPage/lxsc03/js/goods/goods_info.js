var vue = new Vue({
	el: "#app",
	data: {
		token: sessionStorage.getItem("token"),
		nickName: sessionStorage.getItem("nickName"),
		goodsId: "",
		num: 1,
		confimOrders: "",
		totalNum: "",
		ANum: "",
		BNum: "",
		CNum: "",
		imgNum: "",
		// evaluateList: "",
		pageNo: "1",
		pageSize: "5",
		pageCount: "",
		evaluateLevel: "",
		evaluateResult: "",
		evaluateOther: "",
		userDefaultheadPortrait: "http://img.alicdn.com/tps/TB1l6dkOXXXXXXEXVXXXXXXXXXX-210-210.png_70x70.jpg",
		inventory: 888
	},
	methods: {
		evaluateListM: function() {

			axios({
				method: "get",
				url: "http://evaluate.lxsc.com:8082/getEvaluateList",
				params: {
					goodsId: vue.goodsId,
					evaluateLevel: vue.evaluateLevel,
					pageNo: vue.pageNo,
					pageSize: vue.pageSize
				}
			}).then(function(res) {
				var data = res.data;
				// console.log(data);
				console.log(data.result);
				vue.totalNum = data.result.totalNum;
				vue.pageNo = data.result.pageNo;
				vue.pageCount = data.result.pageCount;
				vue.evaluateResult = data.result;
				// alert(data.result.pageNo);
			});
		},
		nextPage: function(num) {
			
			if (num == -1 && vue.pageNo > 1) {				
				vue.pageNo--;
			} else if (num == -1 && vue.pageNo == 1) {
				return;
			} else if (num == 1 && vue.pageNo < vue.pageCount) {
				vue.pageNo++;
				// vue.pageNo = vue.pageNo + 1;
			} else {
				return;
			}
			vue.evaluateListM();
		},
		// 根据评价等级查询数据
		evaluateLevelF: function(level) {
			// alert(level);
			vue.evaluateLevel = level;
			vue.pageNum = "1";
			vue.evaluateListM();
		},
		countEvaluateNum: function() {
			axios({
				method: "get",
				url: "http://evaluate.lxsc.com:8082/countEvaluateNum",
				params: {
					goodsId: vue.goodsId
				}
			}).then(function(res) {
				var data = res.data;
				console.log("count:" + data);
				// 将获取到的各个等级评价数量赋值
				vue.ANum = data.result.A;
				vue.BNum = data.result.B;
				vue.CNum = data.result.C;
				vue.imgNum = data.result.image;
			});
		},
		// 更改购买数量
		changeBuyNum: function(num) {
			// 如果是手动输入的值则是字符串，将变量转换为 number 类型
			vue.num = vue.num * 1;
			// 进行条件控制
			if (num == -1 && vue.num - 1 < 1) {
				vue.num = 1;
				return;
			} else if (num == 1 && (vue.num + num) > vue.inventory) {
				vue.num = vue.inventory;
				return;
			}
			// 对数量增加或者减少
			vue.num = vue.num + num;
		},
		loginPage: function() {
			sessionStorage.setItem("loginPagePath", window.location.href)
		},
		buyGoods: function() {
			if (vue.num > vue.inventory || vue.num < 1) {
				alert("购买数量非法");
				return;
			}
			// alert("购买商品");
			axios({
				method: "get",
				url: "http://orders.lxsc.com:8083/addOrder",
				params: {
					goodsId: vue.goodsId,
					token: vue.token,
					buyNum: vue.num
				}
			}).then(function(res) {
				var data = res.data;
				console.log(data);
				if (data.code != 10000) {
					alert(data.msg);
					return;
				}
				sessionStorage.setItem("ordersId", data.result.ordersId);
				sessionStorage.setItem("orderNo", data.result.orderNo);
				sessionStorage.setItem("ordersMoney", data.result.ordersMoney);
				window.location.href = "confirm_order.html";
			});
		}
	}
})

// 获取当前商品的路径
var path = window.location.href;
// 获取最后一个 / 字符的下标
var startIndex = path.lastIndexOf("/");
// 获取最后一个 . 字符的下标 
var endIndex = path.lastIndexOf(".");
// 通过下标截取商品具体 ID 值
vue.goodsId = path.substring(startIndex + 1, endIndex);
vue.evaluateListM();
vue.countEvaluateNum();
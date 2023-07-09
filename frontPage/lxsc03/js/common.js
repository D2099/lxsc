$(function(){
	
	//子页搜索
	$(".index .top_ad #close").click(function(){
		$(".index .top_ad").slideUp(500);
	})
	
	$(".pro_cont .item dl dd img").click(function(){
		$(".pro_cont .item dl dd img").removeClass("on");
		$(this).addClass("on")
		$(this).parent().siblings("dt").children().attr('src',$(this)[0].src);
	})
	

	$(".menu #nav").hover(function(){
		$(".hover").hide()
		$(this).children(".nav_son").fadeIn(200);
	},function(){
		$(".hover").hide()
		$(this).children(".nav_son").fadeOut(0);
		
	})
	
	$(".xlc").hover(function(){						
		$(this).find("ul").show();								
	},function(){							
		$(this).find("ul").hide();								
	})
	$(".xlc ul li").hover(function(){						
	},function(){						
	}).click(function(){
		var a = $(this).html();
		$(this).parent().parent().find("input").val(a);
		$(this).parent().hide();
		var cl=$(this).attr("class");
		$(this).parent().parent().find("input").attr("class",cl);
	})
	
	$(".float2 li a").click(function(){
		$(".float2 li a").removeClass("on");
		$(this).addClass("on")
	})
	
	 //首页子菜单
	//  $(".nav_son li").hover(function(){
	// 	num=$(this).index()
	// 	$(".nav_son .hover").fadeIn(300);
	// 	// $(".nav_son .hover #son:eq("+num+")").fadeIn(300);		
	// 	// alert($("#son"+num).html())
	// 	$("#son"+num).fadeIn(300);
	// 	flag=true
	// },function(){
	// 	// $(".nav_son .hover #son").hide();	
	// 	// $(".nav_son .hover").hide();	
	// 	// if(!flag){
	// 	// 	alert(1)
	// 	// 	$(".nav_son .hover #son"+num).hide();
	// 	// 	$(".nav_son .hover").hide();
	// 	// }
	// })
	
	$(".nav_son li").mouseover(function(){
		var num=$(this).index()
		console.log(num)
		$(".menuSon").hide()
		$(".nav_son .hover").fadeIn(300);
		$("#son"+num).fadeIn(300);
	})
	// $(".nav_son").mouseout(function(){
	// 	$(".menuSon").hide()
	// })
})




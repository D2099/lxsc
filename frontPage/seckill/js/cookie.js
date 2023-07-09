
function removeCookie(name,path,domain)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null)
		document.cookie =name+"="+ cval+";path="+path+";domain="+domain+";expires="+exp.toGMTString()
}
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
	return unescape(arr[2]);
	else
	return null;
}
function setCookie(name,value,path,domain)
{
	document.cookie =name+"="+ value+";path="+path+";domain="+domain
}


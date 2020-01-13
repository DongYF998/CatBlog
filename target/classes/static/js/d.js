$(function(){
	var height = $(window).height();
})

var alldata = new Array(['钟静涛','0001'],['于怀琴','0002'],['邓兵','0003'],['彭俊逸','0004'],['方勇','0005'],['董一凡','0006']);
var img = new Array('./img/man.jpg','./img/woman.jpg');

var timer
function change(){
	var num = alldata.length;
	var index = Math.floor((Math.random()*num));
	$("#headImg").attr("src",img[index%2]);
	$("#name").html(alldata[index][0]);
	$("#code").html(alldata[index][1]);
}
function start(){
	clearInterval(timer);
	timer = setInterval('change()',100);
}
function ok(){
	clearInterval(timer);
}

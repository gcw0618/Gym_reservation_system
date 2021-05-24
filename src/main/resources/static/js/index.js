/*选项卡右键关闭刷新*/
layui.config({
	base: '/plugins/layui/js/',
}).use(['element', 'tabrightmenu'], function() {
	var $ = layui.jquery;
	let element = layui.element;
	let rightmenu_ = layui.tabrightmenu;
	//隐藏tab主页关闭标签
	$(".layui-tab-title li:first-child i:last-child").css("display", "none");

	// 默认方式渲染全部：关闭当前（closethis）、关闭所有（closeall）、关闭其它（closeothers）、关闭左侧所有（closeleft）、关闭右侧所有（closeright）、刷新当前页（refresh）
	rightmenu_.render({
		container: '#tabs_div',
		filter: 'tabs',
		navArr: [{
				eventName: 'refresh',
				title: '刷新当前页'
			},
			{
				eventName: 'closethis',
				title: '关闭当前页'
			},
			{
				eventName: 'closeall',
				title: '关闭所有页'
			},
			{
				eventName: 'closeothers',
				title: '关闭其它页'
			}
		],
		pintabIDs: ["index"]
	});

	//触发事件
	var active = {
		//在这里给active绑定几项事件，后面可通过active调用这些事件
		tabAdd: function(url, id, name) {
			//新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
			//关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
			element.tabAdd('tabs', {
				title: name,
				content: '<iframe class="layui-admin-iframe" data-frameid="' + id +
					'" scrolling="auto" frameborder="0" src="' + url +
					'" style="width:100%;height:99%;"></iframe>',
				id: id //规定好的id
			})
			element.tabChange('tabs', id); //根据传入的id传入到指定的tab项
			FrameWH(); //计算ifram层的大小
		},
		tabChange: function(id) {
			//切换到指定Tab项
			element.tabChange('tabs', id); //根据传入的id传入到指定的tab项
		},
		tabDelete: function(id) {
			element.tabDelete("tabs", id); //删除
		},
		tabDeleteAll: function(ids) { //删除所有
			$.each(ids, function(i, item) {
				element.tabDelete("tabs", item); //ids是一个数组，里面存放了多个id，调用tabDelete方法分别删除
			})
		}
	};


	//当点击有site-demo-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
	$('.site-demo-active').on('click', function() {
		var dataid = $(this);

		//这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
		if ($(".layui-tab-title li[lay-id]").length <= 0) {
			//如果比零小，则直接打开新的tab项
			active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
		} else {
			//否则判断该tab项是否以及存在

			var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
			$.each($(".layui-tab-title li[lay-id]"), function() {
				//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
				if ($(this).attr("lay-id") == dataid.attr("data-id")) {
					isData = true;
				}
			})
			if (isData == false) {
				//标志为false 新增一个tab项
				active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
			}
		}
		//最后不管是否新增tab，最后都转到要打开的选项页面上
		active.tabChange(dataid.attr("data-id"));
	});

	element.on('tab(tabs)', function(data) {
		//tab切换时，左侧菜单对应选中
		var url = $(this).attr("lay-id");
		$(".layui-nav-tree li dd").each(function(i, e) {
			if ($(this).find("a").attr("data-id") == url) {
				$(this).attr("class", "layui-this");
			} else {
				$(this).attr("class", "");
			}
		})
	});

	function FrameWH() {
		var h = $(window).height() - 120;
		$("iframe").css("height", h + "px");
	}

	$(window).resize(function() {
		FrameWH();
	});
	FrameWH();
});

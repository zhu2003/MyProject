  layui.use('layer',function () {
        var layer = layui.layer;
    });
    function tip() {
        layer.tips('点击修改头像', $("#tip"),{
            tips:[4,'#009E94'],
            time:1500
        });
    }
//tab跳转
layui.use(['element','layer','upload'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var upload = layui.upload;
    // var layid = location.hash.replace(/^#demo=/, '');
    // element.tabChange('current', layid);
    //修改头像

    upload.render({
        elem: '#test1'
        , url: '/userImg'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $("#photo").attr('src', result); //图片链接（base64）
                $("#picName").val(file.name);
            });
        },
        done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            //上传成功
            layer.msg("上传成功");
        },
        error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });
    $("#userImg").click(function () {
        layer.open({
            type:1,
            title:'修改头像',
            content: $("#uploadUserImg"),
            area:['300px','250px'],
        })
    });
    $("#btn-upload").click(function () {
        $.ajax({
            type:'post',
            url:'/updateImg',
            data:{
                photo:$("#picName").val(),
                userId:$("#userId").val()
            },
            success:function (data) {
                layer.msg(data);
                setTimeout(function () {
                    layer.closeAll();
                },500);
                location.reload();
            },
            error:function () {
                layer.msg('执行失败');
            }

        })
    });
    var active = {
        //在这里给active绑定几项事件，后面可通过active调用这些事件
        tabAdd: function(url,id,name) {
            //新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
            element.tabAdd('demo', {
                title: name,
                content: '<iframe data-frameid="'+id+'" scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:99%;"></iframe>',
                id: id //规定好的id
            });
            FrameWH();  //计算ifram层的大小
        },
        tabChange: function(id) {
            //切换到指定Tab项
            element.tabChange('demo', id); //根据传入的id传入到指定的tab项
        },
        tabDelete: function (id) {
            element.tabDelete("demo", id);//删除
        }

    };
$(".site-demo-active").click(function () {
    var dataid = $(this);

    if ($(".layui-tab-title li[lay-id]").length<=0){
        active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"),dataid.attr("data-title"));
    }else {
        var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
        var arr = new Array();
        arr = $(".layui-tab-title li[lay-id]");

        $.each(arr,function () {
            if ($(this).attr("lay-id") == dataid.attr("data-id")) {
                isData = true;
                active.tabChange(dataid.attr("data-id"));
            }
        });
        if (isData == false){
            active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"),dataid.attr("data-title"));
        }

    }
    active.tabChange(dataid.attr("data-id"));
});
    function FrameWH() {
        var h = $(window).height() -41- 10 - 60 -10-44 -10;
        $("iframe").css("height",h+"px");
    }

    $(window).resize(function () {
        FrameWH();
    });

//修改密码
});
//公告轮播
var isStoped = false;
var oScroll = document.getElementById("scrollWrap");
with (oScroll) {
    noWrap = true;
}

oScroll.onmouseover = new Function('isStoped = true');
oScroll.onmouseout = new Function('isStoped = false');

var preTop = 0;
var curTop = 0;
var stopTime = 0;
var oScrollMsg = document.getElementById("scrollMsg");

oScroll.appendChild(oScrollMsg.cloneNode(true));
init_srolltext();


function init_srolltext() {
    oScroll.scrollTop = 0;
    setInterval('scrollUp()', 10);
}

function scrollUp() {
    if (isStoped)
        return;
    curTop += 1;
    if (curTop == 19) {
        stopTime += 1;
        curTop -= 1;
        if (stopTime == 180) {
            curTop = 0;
            stopTime = 0;
        }
    } else {
        preTop = oScroll.scrollTop;
        oScroll.scrollTop += 1;
        if (preTop == oScroll.scrollTop) {
            oScroll.scrollTop = 0;
            oScroll.scrollTop += 1;
        }
    }
}
//清除cookie的两个函数
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function clearCookie() {
    setCookie("userName", "", -1);
    setCookie("pwd", "", -1);
}
var nowTime;
//显示时钟特效
function play() {
    var time = new Date();
    nowTime = time.getFullYear() + "年" + (time.getMonth()+1) + "月" + time.getDate()+ "日 "+ time.getHours() + "时" + time.getMinutes() + "分" + time.getSeconds() + "秒";
    document.getElementById("time").innerHTML = nowTime;
};
setInterval(play, 1000);
$("#btn-updatePwd").click(function () {
    location.href="/";
});

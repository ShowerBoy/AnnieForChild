package com.annie.annieforchild.Utils;

/**
 * 字段名称
 * Created by WangLei on 2018/1/30
 */
public class MethodCode {
    final public static String VERSION = "v1/";
    final public static String SYSTEMAPI = "System/";
    final public static String SYSTEMTEST = "SystemTest/";
    final public static String HOMEPAGEAPI = "Homepage/";
    final public static String PERSONAPI = "Person/";
    final public static String PERSONAPI2 = "PersonApi/";
    final public static String TASKAPI = "Task/";
    final public static String TASKAPI2 = "TaskApi/";
    final public static String SEARCHAPI = "Search/";
    final public static String ADDRESSAPI = "Address/";
    final public static String SHAREAPI = "Share/";
    final public static String CLASSSCHEDULE = "ClassSchedule/";
    final public static String SQUAREAPI = "Square/";

    final public static String HOMEWORKAPI = "homeworkApi/";
    final public static String NETCLASSAPI = "NetclassApi/";
    final public static String SIGNINAPI = "Signin/";

    final public static String DEVICETYPE = "deviceType";
    final public static String DEVICEID = "deviceID";
    final public static String APPVERSION = "appVersion";

    final public static String STATUS = "status";
    final public static String MSG = "msg";
    final public static String ERRTYPE = "errType";
    final public static String ERRINFO = "errInfo";
    final public static String DATA = "data";
    final public static String SERIALNUMBER = "serialNumber"; //流水号
    final public static String TOKEN = "token";
    final public static String AVATARUEL = "avatarUrl";

    /**
     * EventBus
     */
    final public static int EVENT_RELOGIN = -2000; //重新登陆
    final public static int EVENT_WEBRECORDWITHGRADE = -240; //网页录音智聆
    final public static int EVENT_WEBVIDEO = -230; //网页播放视频
    final public static int EVENT_WEBSHARE = -220; //网页分享
    final public static int EVENT_UNPLAYING = -210; //没在播放
    final public static int EVENT_ISPLAYING = -200; //在播放
    final public static int EVENT_WEBRECORD = -190;
    final public static int EVENT_REFRESH = -180;
    final public static int EVENT_CONFIRMBUYSUC = -170;
    final public static int EVENT_LESSON = -160;
    final public static int EVENT_ISDROP = -150;
    final public static int EVENT_MUSICSTOP = -140;
    final public static int EVENT_TASKIMAGE = -130;
    final public static int EVENT_TASKDATA = -120;
    final public static int EVENT_DATA = -110;
    final public static int EVENT_STOPPLAY = -100;
    final public static int EVENT_LOADING = -90;
    final public static int EVENT_VISIBILITY = -60;
    final public static int EVENT_NECTAR = -70;
    final public static int EVENT_GONE = -80;
    final public static int EVENT_ERROR = -10;
    final public static int EVENT_PAY = -50;
    final public static int EVENT_PRACTICE = -40;
    final public static int EVENT_PAGEPLAY = -30;
    final public static int EVENT_MUSICPLAY = -20;
    final public static int EVENT_ADDRESS = -5;
    final public static int EVENT_PLAYING = -4;
    final public static int EVENT_MUSIC = -3;
    final public static int EVENT_MAIN = 0;
    final public static int EVENT_LOGIN = 1; //登陆
    final public static int EVENT_RGISTER = 2; //注册
    final public static int EVENT_VERIFICATION_CODE = 3; //获取验证码
    final public static int EVENT_USERINFO = 4; //获取用户信息
    final public static int EVENT_MYSCHEDULE = 5; //课表
    final public static int EVENT_ADDCHILD = 6; //注册学员
    final public static int EVENT_ADDCHILD2 = 7; //注册学员2
    final public static int EVENT_USERLIST = 8; //获取学员列表
    final public static int EVENT_UPDATEUSER = 9; //修改个人信息
    final public static int EVENT_RESETPASSWORD = 10; //修改密码
    final public static int EVENT_MYCOLLECTIONS1 = 11; //我的收藏——磨耳朵
    final public static int EVENT_MYCOLLECTIONS2 = 12; //我的收藏——阅读
    final public static int EVENT_MYCOLLECTIONS3 = 13; //我的收藏——口语
    final public static int EVENT_CHANGEPHONE = 14; //修改手机号
    final public static int EVENT_GETNECTAR = 15; //我的花蜜
    final public static int EVENT_CHECKUPDATE = 16; //检测更新


    final public static int EVENT_UPLOADAVATAR = 17; //上传头像
    final public static int EVENT_SETDEFAULEUSER = 18; //设置默认学员
    final public static int EVENT_GETMYMESSAGES = 19; //获取通知和群消息
    final public static int EVENT_FEEDBACK = 20; //意见反馈
    final public static int EVENT_GETHELP = 21; //帮助文档
    final public static int EVENT_CANCELCOLLECTION1 = 22; //取消收藏——磨耳朵
    final public static int EVENT_CANCELCOLLECTION2 = 23; //取消收藏——阅读
    final public static int EVENT_CANCELCOLLECTION3 = 24; //取消收藏——口语
    final public static int EVENT_MYRECORDINGS = 25; //我的录音
    final public static int EVENT_DELETERECORDING = 26; //删除录音
    final public static int EVENT_EXCHANGEGOLD = 27; //花蜜兑换
    final public static int EVENT_GETHOMEDATA = 28; //获取首页信息
    final public static int EVENT_GETLISTENING = 29; //获取磨耳朵信息
    final public static int EVENT_GETMUSICCLASSES1 = 30; //获取儿歌分类
    final public static int EVENT_GETMUSICCLASSES2 = 31; //获取诗歌分类
    final public static int EVENT_GETMUSICCLASSES3 = 32; //获取动画分类
    final public static int EVENT_GETMUSICCLASSES4 = 33; //获取绘本分类
    final public static int EVENT_GETMUSICCLASSES5 = 34; //获取我要唱歌分类
    final public static int EVENT_GETMUSICLIST = 35; //获取儿歌列表
    final public static int EVENT_DELETEUSERNAME = 36; //删除学员
    final public static int EVENT_GETMYLISTENING = 37; //获取我的磨耳朵
    final public static int EVENT_COMMITDURATION = 38; //时长录入
    final public static int EVENT_GETBOOKSCORE = 39; //获取书籍信息
    final public static int EVENT_GETBOOKAUDIODATA = 40; //练习，挑战，PK信息
    final public static int EVENT_UPLOADAUDIO = 41; //上传音频接口——磨耳朵
    final public static int EVENT_COLLECTCOURSE = 42; //收藏
    final public static int EVENT_PK_EXERCISE = 43; //pk——练习
    final public static int EVENT_PK_CHALLENGE = 44; //pk——挑战
    final public static int EVENT_PK_PK = 45; //pk——pk
    final public static int EVENT_GETPKUSERS = 46; //获取pk对象
    final public static int EVENT_GETALLMATERIALLIST1 = 47; //选择磨耳朵分类
    final public static int EVENT_GETALLMATERIALLIST2 = 48; //选择阅读分类
    final public static int EVENT_GETALLMATERIALLIST3 = 49; //选择口语分类
    final public static int EVENT_ADDSCHEDULE = 50; //添加课表
    final public static int EVENT_TOTALSCHEDULE = 51; //总课表
    final public static int EVENT_EDITSCHEDULE = 52; //编辑课表
    final public static int EVENT_DELETESCHEDULE = 53; //删除课表
    final public static int EVENT_GETMUSICCLASSES6 = 54; //获取阅读分类
    final public static int EVENT_GETMUSICCLASSES7 = 55; //获取阅读分类
    final public static int EVENT_GETMUSICCLASSES8 = 56; //获取阅读分类
    final public static int EVENT_GETMUSICCLASSES9 = 57; //获取阅读分类
    final public static int EVENT_GETMUSICCLASSES10 = 58; //获取阅读分类
    final public static int EVENT_GLOBALSEARCH = 59; //全局搜索
    final public static int EVENT_GETPKRESULT = 60; //获取PK结果
    final public static int EVENT_MYCOURSESONLINE = 61; //获取线上课程
    final public static int EVENT_MYCOURSESOFFLINE = 62; //获取线下课程
    final public static int EVENT_MYTEACHINGMATERIALS = 63; //我的教材
    final public static int EVENT_JOINMATERIAL = 64; //加入教材
    final public static int EVENT_CANCELMATERIAL = 65; //取消加入教材
    final public static int EVENT_GETRANK = 66; //榜单
    final public static int EVENT_GETSQUARERANK = 67; //广场首页排行榜
    final public static int EVENT_GETSQUARERANKLIST = 68; //广场排行榜列表
    final public static int EVENT_LIKESTUDENT = 69; //点赞
    final public static int EVENT_CANCELLIKESTUDENT = 70; //取消点赞
    final public static int EVENT_GETMYREADING = 71; //阅读存折
    final public static int EVENT_COMMITREADING = 72; //阅读时长录入
    final public static int EVENT_GETREADING = 73; //获取阅读
    final public static int EVENT_SHARETO = 74; //分享
    final public static int EVENT_GETDURATIONSTATISTICS = 75; //统计
    final public static int EVENT_GETREADLIST = 76; //获取阅读列表
    final public static int EVENT_GETQRCODE = 77; //获取二维码
    final public static int EVENT_GETANIMATIONLIST = 78; //获取看动画
    final public static int EVENT_GETSPOKENCLASSES = 79; //获取口语分类
    final public static int EVENT_GETSPOKENLIST = 80; //获取口语列表
    final public static int EVENT_COMMITBOOK = 81; //提交书名
    final public static int EVENT_DAILYPUNCH = 82; //在线得花蜜
    final public static int EVENT_IWANTLISTEN = 83; //我要
    final public static int EVENT_ACCESSBOOK = 84; //访问书籍
    final public static int EVENT_UPLOADAUDIOTIME = 85; //上传音频时长
    final public static int EVENT_MYCALENDAR = 86; //
    final public static int EVENT_MONTHCALENDAR = 87; //当月课程
    final public static int EVENT_MYPERIOD = 88; //课时核对
    final public static int EVENT_SUGGESTPERIOD = 89; //课时提异
    final public static int EVENT_MYTASK = 90; //我的作业
    final public static int EVENT_TASKDETAILS = 91; //作业详情
    final public static int EVENT_COMPLETETASK = 92; //完成作业
    final public static int EVENT_UPLOADTASKIMAGE = 93; //提交作业图片
    final public static int EVENT_SELECT = 94; //选择图片
    final public static int EVENT_SUBMITTASK = 95; //提交作业
    final public static int EVENT_CLOCKINSHARE = 96; //打卡分享
    final public static int EVENT_GETCARDDETAIL = 97; //打卡详情
    final public static int EVENT_SHARESUCCESS = 98; //打卡分享成功
    final public static int EVENT_GETMYSPEAKING = 99; //口语存折
    final public static int EVENT_GETTAGS = 100; //
    final public static int EVENT_GETTAGBOOK = 101; //
    final public static int EVENT_COMMITSPEAKING = 102; //口语录入
    final public static int EVENT_UNLOCKBOOK = 103; //花蜜解锁
    final public static int EVENT_GETNETHOMEDATA = 104; //网课首页
    final public static int EVENT_GETNETSUGGEST = 105; //网课购买页
    final public static int EVENT_GETMYNETCLASS = 106; //我的网课
    final public static int EVENT_CONFIRMORDER = 107; //订单确认
    final public static int EVENT_GETMYADDRESS = 108; //我的收货地址
    final public static int EVENT_ADDADDRESS = 109; //添加收货地址
    final public static int EVENT_EDITADDRESS = 110; //修改收货地址
    final public static int EVENT_DELETEADDRESS = 111; //删除收货地址
    final public static int EVENT_BUYNETWORK = 112; //购买网课
    final public static int EVENT_MYCOLLECTIONS0 = 113; //我的收藏——其他
    final public static int EVENT_CANCELCOLLECTION0 = 114; //取消收藏——其他
    final public static int EVENT_GETNETDETAILS = 115; //网课详情
    final public static int EVENT_GETLESSON = 116; //网课列表
    final public static int EVENT_GETMUSICCLASSES11 = 117; //获取听对话分类
    final public static int EVENT_GETSPOKENCLASSES1 = 118; //
    final public static int EVENT_GETSPOKENCLASSES2 = 119; //
    final public static int EVENT_GETSPOKENCLASSES3 = 120; //
    final public static int EVENT_GETSPOKENCLASSES4 = 121; //
    final public static int EVENT_GETSPOKENCLASSES5 = 122; //
    final public static int EVENT_GETSPEAKING = 123; //
    final public static int EVENT_GETRELEASE = 124; //
    final public static int EVENT_RELEASEBOOK = 125; //
    final public static int EVENT_RELEASESUCCESS = 126; //
    final public static int EVENT_PLAYTIMES = 127; //
    final public static int EVENT_ADDLIKES = 128; //
    final public static int EVENT_CANCELLIKES = 129; //
    final public static int EVENT_ADDLIKES_PRODUCTION = 169; //
    final public static int EVENT_CANCELLIKES_PRODUCTION = 170; //
    final public static int EVENT_SHARECOIN = 130; //
    final public static int EVENT_GETRADIO = 131; //
    final public static int EVENT_GETLYRIC = 132; //
    final public static int EVENT_LUCKDRAW = 133; //
    final public static int EVENT_GETHOMEPAGE = 138; //
    final public static int EVENT_GETPRODUCTIONLIST = 139; //
    final public static int EVENT_CANCELRELEASE = 140; //


    final public static int EVENT_BUYSUCCESS = 137; //网课购买成功
    final public static int EVENT_GETNETEXPDETAILS = 134; //体验课具体内容详情
    final public static int EVENT_GETPREHEATCONSULT = 135; //预热课
    final public static int EVENT_GETLISTENANDREAD = 136; //泛听泛读
    final public static int EVENT_BUYNUM = 141; //购买之前验证下，netsuggest验证
    final public static int EVENT_BUYNUM1 = 142; //购买之前验证，confirmorder验证
    final public static int EVENT_ORDERQUERY = 143; //购买之后查询订单状态
    final public static int EVENT_GETWEICLASS = 144; //微课堂视频
    final public static int EVENT_GETNETEXPDETAILS_NEW = 145; //体验课具体内容详情新版本
    final public static int EVENT_SPECIALCLASS = 146; //体验课具体内容详情新版本
    final public static int EVENT_SPECIALPREHEATING = 147; //综合课预热课
    final public static int EVENT_BINDWEIXIN = 148; //
    final public static int EVENT_GETBINDVERIFICATIONCODE = 149; //
    final public static int EVENT_BINDSTUDENT = 150; //
    final public static int EVENT_GETMYORDERLIST = 151; //
    final public static int EVENT_GETMYORDERDETAIL = 152; //
    final public static int EVENT_CONTINUEPAY = 153; //
    final public static int EVENT_CANCELORDER = 154; //
    final public static int EVENT_EXPERIENCEDETAILSV2 = 155; //
    final public static int EVENT_VIDEOPAYRECORD = 156; //
    final public static int EVENT_VIDEOLIST = 157; //
    final public static int EVENT_UPLOADIMGH5 = 158; //
    final public static int EVENT_SHOWGIFTS = 159; //
    final public static int EVENT_CHOOSEGIFT = 160; //
    final public static int EVENT_GETMESSAGESLIST = 161; //
    final public static int EVENT_SPECIALCLASSV2 = 162; //
    final public static int EVENT_TASKLIST = 163; //
    final public static int EVENT_TASKDETAIL = 164; //
    final public static int EVENT_DISCOUNTRECORD = 165; //优惠券
    final public static int EVENT_EXPERIENCEDETAILSV3 = 166; //体验课V3
    final public static int EVENT_UPLOADING = 167; //上传测试录音
    final public static int EVENT_INSERTEQUIPMENTDATA = 168; //上传测试录音


    final public static String A0102="A0102";//点击我的课表
    final public static String A0103 ="A0103";//点击网课
    final public static String A0104="A0104";//点击存折
    final public static String A0105="A0105";//点击广场
    final public static String A0106="A0106";//点击磨耳朵
    final public static String A0107="A0107";//点击流利读
    final public static String A0108="A0108";//点击地道说
    final public static String A0109="A0109";//点击磨耳朵列表
    final public static String A0110="A0110";//点击磨耳朵更多
    final public static String A0111="A0111";//点击流利读
    final public static String A0112="A0112";//点击流利读更多
    final public static String A0113="A0113";//点击地道说
    final public static String A0114="A0114";//点击地道说更多
    final public static String A0115="A0115";//点击每日一歌
    final public static String A0116="A0116";//点击每日一诗
    final public static String A0117="A0117";//点击每日一读
    final public static String A0118="A0118";//点击首页网课图片介绍

    final public static String A0203="A0203";//点击我的课表
    final public static String A0204="A0204";//点击我的网课
    final public static String A0205="A0205";//点击我的作业
    final public static String A0206="A0206";//点击我的教材

    final public static String A1403 ="A1403";//打卡页面点击去磨耳朵
    final public static String A1404 ="A1404";//打卡页面点击去阅读
    final public static String A140101="A140101";//打卡页面点击去口语
    final public static String A140102="A140102";//打卡页面点击打卡

    final public static String A0402="A0402";//点击个人中心
    final public static String A0403="A0403";//点击等级
    final public static String A0404="A0404";//点击设置
    final public static String A0405="A0405";//点击花蜜
    final public static String A0406="A0406";//点击添加学员
    final public static String A0407="A0407";//点击课时核对
    final public static String A0408="A0408";//点击我的作品
    final public static String A0409="A0409";//点击我的订单
    final public static String A0410="A0410";//点击我的优惠券
    final public static String A0411="A0411";//点击我的收藏
    final public static String A0412="A0412";//点击推荐好友
    final public static String A040401="A040401";//点击推荐好友
    final public static String A040402="A040402";//点击推荐好友
    final public static String A040403="A040403";//点击推荐好友

    final public static String A010201="A010201";//点击添加课表
    final public static String A010202="A010202";//点击加入课表

    final public static String A0502="A0502";//点击听儿歌
    final public static String A0503="A0503";//点击听诗歌
    final public static String A0504="A0504";//点击听故事
    final public static String A0505="A0505";//点击分级读物
    final public static String A0506="A0506";//点击桥梁书
    final public static String A0507="A0507";//点击章节书
    final public static String A0508="A0508";//点击听对话
    final public static String A0509="A0509";//点击看动画
    final public static String A0510="A0510";//点击伴奏
    final public static String A0511="A0511";//点击最近播放
    final public static String A0512="A0512";//点击我的收藏
    final public static String A0513="A0513";//点击磨耳朵页面的安娃电台的各个类型
    final public static String A0514="A0514";//点击磨耳朵页面的安娃电台的各个类型
    final public static String A0515="A0515";//点击磨耳朵页面的安娃电台的各个类型
    final public static String A050201="A050201";//点击磨耳朵听儿歌等列表页的收藏
    final public static String A050202="A050202";//点击磨耳朵听儿歌等列表页的加入教材
    final public static String A050203="A050203";//点击磨耳朵听儿歌等列表页的加入课表

    final public static String A060102="A060102";//点击磨耳朵播放页面的收藏
    final public static String A060103="A060103";//点击磨耳朵播放页面的分享
    final public static String A060104="A060104";//点击磨耳朵播放页面的安娃电台
    final public static String A0602="A0602";//点击磨耳朵资源页的练习
    final public static String A0603="A0603";//点击磨耳朵资源页的挑战
    final public static String A0604="A0604";//点击磨耳朵资源页的pk
    final public static String A060501="A060501";//点击磨耳朵资源列表页的收藏
    final public static String A060502="A060502";//点击磨耳朵资源列表页的加入教材
    final public static String A060503="A060503";//点击磨耳朵资源列表页的加入课表

    final public static String A1101="A1101";//点击录制列表用户播放按钮
    final public static String A1102="A1102";//点击录制列表用户的点赞
    final public static String A1103="A1103";//点击录制列表用户的用户头像

    final public static String A0902="A0902";//点击地道说页面的绘本口语
    final public static String A0903="A0903";//点击地道说页面的主题口语
    final public static String A0904="A0904";//点击地道说页面的交际口语
    final public static String A0905="A0905";//点击地道说页面的动画口语
    final public static String A0906="A0906";//点击地道说页面的项目演讲
    final public static String A0907="A0907";//点击地道说页面的口语推荐列表
    final public static String A090201="A090201";//点击地道说页面的收藏
    final public static String A090202="A090202";//点击地道说页面的加入教材
    final public static String A090203="A090203";//点击地道说页面的加入课表

    final public static String A0702="A0702";//点击流利度页面绘本
    final public static String A0703="A0703";//点击流利度页面分级读物
    final public static String A0704="A0704";//点击流利度页面桥梁书
    final public static String A0705="A0705";//点击流利度页面章节书
    final public static String A0706="A0706";//点击流利度页面页面推荐列表

    final public static String A080102="A080102";//点击流利度资源页回放
    final public static String A080103="A080103";//点击流利度资源页重录
    final public static String A080104="A080104";//点击流利度资源页发布
    final public static String A080105="A080105";//点击流利度资源页关闭







}

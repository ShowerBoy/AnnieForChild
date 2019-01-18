package com.annie.annieforchild.Utils;

/**
 * 字段名称
 * Created by WangLei on 2018/1/30 0030
 */

public class MethodCode {
    final public static String SYSTEMAPI = "systemApi/";
    final public static String PERSONAPI = "personApi/";
    final public static String HOMEPAGEAPI = "HomepageApi/";
    final public static String HOMEWORKAPI = "homeworkApi/";
    final public static String SEARCHAPI = "SearchApi/";
    final public static String SQUAREAPI = "SquareApi/";
    final public static String NETCLASSAPI = "NetclassApi/";
    final public static String SIGNINAPI = "Signin/";
    final public static String TASKAPI = "TaskApi/";
    final public static String SHAREAPI = "ShareApi/";
    final public static String ERRTYPE = "errType";
    final public static String ERRINFO = "errInfo";
    final public static String DATA = "data";
    final public static String SERIALNUMBER = "serialNumber"; //流水号
    final public static String TOKEN = "token";
    final public static String AVATARUEL = "avatarUrl";

    /**
     * EventBus
     */
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
    final public static int EVENT_SHARECOIN = 130; //
    final public static int EVENT_GETRADIO = 131; //
    final public static int EVENT_GETLYRIC = 132; //
    final public static int EVENT_LUCKDRAW = 133; //
    final public static int EVENT_GETHOMEPAGE = 134; //
    final public static int EVENT_GETPRODUCTIONLIST = 135; //
    final public static int EVENT_CANCELRELEASE = 136; //
}

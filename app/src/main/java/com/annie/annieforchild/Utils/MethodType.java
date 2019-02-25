package com.annie.annieforchild.Utils;

/**
 * 接口名称
 * Created by WangLei on 2018/2/2 0002
 */

public class MethodType {
    final public static String GET_VERIFICATION_CODE = "getVerificationCode"; //获取验证码 1
    final public static String REGISTER = "register"; //注册 2
    final public static String LOGIN = "login"; //登陆 3
    final public static String GETUSERINFO = "getUserInfo"; //获取个人信息 4
    final public static String REGISTERUSER = "registerUser"; //注册学员 5
    final public static String GETUSERLIST = "getUserList"; //获取学员列表 6
    final public static String UPDATEUSERINFO = "updateUserInfo"; //修改个人信息 7
    final public static String MYCOLLECTIONS = "myCollections"; //我的收藏
    final public static String COLLECTCOURSE = "collectCourse"; //收藏
    final public static String CHANGEPHONE = "changePhone"; //修改手机号
    final public static String RESETPASSWORD = "resetPassword"; //修改密码
    final public static String CHECKUPDATE = "checkUpdate"; //检测更新
    final public static String GETNECTAR = "getNectar"; //我的花蜜
    final public static String UPLOADACATAR = "uploadAvatar"; //上传头像
    final public static String SETDEFAULTUSER = "setDefaultUser"; //设置默认学员
    final public static String DELETEUSERNAME = "deleteUsername"; //删除学员
    final public static String GETMYMESSAGES = "getMyMessages"; //获取通知和群消息
    final public static String FEEDBACK = "feedback"; //意见反馈
    final public static String GETHELP = "getDocumentations"; //帮助文档
    final public static String CANCELCOLLECTION = "cancelCollection"; //取消收藏
    final public static String MYRECORDINGS = "myRecordings"; //我的录音
    final public static String MYRECORDINGSTEST = "myRecordingstest"; //我的录音
    final public static String DELETERECORDING = "deleteRecording"; //删除录音
    final public static String DELETERECORDINGTEST = "deleteRecordingtest"; //删除录音
    final public static String EXCHANGERECORDING = "nectarExchangeRecording"; //花蜜兑换记录
    final public static String GETHOMEDATA = "getHomeData"; //获取首页信息
    final public static String GETCARDDETAIL = "getCardDetail"; //今日打卡详情
    final public static String GETLISTENING = "getListening"; //获取磨耳朵信息
    final public static String GETMYLISTENING = "getMyListening"; //获取我的磨耳朵
    final public static String GETMUSICCLASSES = "getMusicClasses"; //获取儿歌分类
    final public static String GETMUSICLIST = "getMusicList"; //获取儿歌列表
    final public static String GETMUSICLISTTEST = "getMusicListtest"; //获取儿歌列表
    final public static String MYSCHEDULE = "mySchedule"; //我的课表
    final public static String ADDSCHEDULE = "addSchedule"; //添加课表
    final public static String EDITSCHEDULE = "editSchedule"; //编辑课表
    final public static String DELETESCHEDULE = "deleteSchedule"; //删除课表
    final public static String TOTALSCHEDULE = "totalSchedule"; //总课表
    final public static String COMMITDURATION = "commitDuration"; //时长录入
    final public static String GETBOOKSCORE = "getBookScore"; //获取书籍接口
    final public static String GETBOOKAUDIODATA = "getBookAudioData"; //练习，挑战，PK接口
    final public static String GETBOOKAUDIODATATEST = "getBookAudioDatatest"; //练习，挑战，PK接口
    final public static String UPLOADAUDIO = "uploadAudioResource"; //上传音频接口——磨耳朵
    final public static String UPLOADAUDIOTEST = "uploadAudioResourcetest"; //上传音频接口——磨耳朵
    final public static String GETPKUSERS = "getPkUsers"; //获取pk对象
    final public static String GETALLMATERIALLIST = "getAllMaterialList"; //获取所有教材
    final public static String GLOBALSEARCH = "globalSearch"; //全局搜索
    final public static String GETPKRESULT = "getPkResult"; //获取PK结果
    final public static String GETPKRESULTTEST = "getPkResulttest"; //获取PK结果
    final public static String MYCOURSESONLINE = "myCoursesOnline"; //获取我的线上课程
    final public static String MYCOURSESOFFLINE = "myCoursesOffline"; //获取我的线下课程
    final public static String MYTEACHINGMATERIALS = "myTeachingMaterials"; //我的教材
    final public static String JOINMATERIAL = "joinMaterial"; //加入教材
    final public static String CANCELMATERIAL = "cancelMaterial"; //取消加入教材
    final public static String GETRANK = "getRank"; //榜单
    final public static String GETSQUARERANK = "getSquareRank"; //广场排行榜首页
    final public static String GETSQUARERANKLIST = "getSquareRankList"; //广场排行榜列表
    final public static String LIKESTUDENT = "likeStudent"; //点赞
    final public static String CANCELLIKESTUDENT = "cancelLikeStudent"; //取消点赞
    final public static String GETMYREADING = "getMyReading"; //阅读存折
    final public static String COMMITREADING = "commitReading"; //阅读时长录入
    final public static String GETREADING = "getReading"; //获取阅读接口
    final public static String EXCHANGEGOLD = "exchangeGold"; //兑换金币
    final public static String SHARETO = "shareTo"; //分享
    final public static String GETDURATIONSTATISICS = "getDurationStatistics"; //统计
    final public static String GETREADLIST = "getReadList"; //获取阅读列表
    final public static String GETQRCODE = "getQrCode"; //获取二维码
    final public static String GETANIMATIONLIST = "getAnimationList"; //获取看动画
    final public static String GETSPOKENLSIST = "getSpokenList"; //获取口语列表
    final public static String COMMITBOOK = "commitBook"; //上传书名
    final public static String DAILYPUNCH = "DailyPunch"; //在线送花蜜
    final public static String IWANTLISTEN = "iWantListen"; //我要
    final public static String ACCESSBOOK = "accessBook"; //访问书籍
    final public static String UPLOADAUDIOTIME = "uploadAudioTime"; //上传音频时长
    final public static String UPLOADAUDIOTIMETEST = "uploadAudioTimetest"; //上传音频时长
    final public static String MYCALENDAR = "myCalendar"; //
    final public static String MONTHCALENDAR = "monthCalendar"; //当月课程
    final public static String MYPERIOD = "myPeriod"; //课时核对
    final public static String SUGGESTPERIOD = "suggestPeriod"; //课时提异
    final public static String MYTASK = "myTask"; //我的作业
    final public static String TASKDETAILS = "taskDetails"; //作业详情
    final public static String COMPLETETASK = "completeTask"; //完成作业
    final public static String UPLOADTASKIMAGE = "uploadTaskImage"; //提交作业图片
    final public static String SUBMITTASK = "submitTask"; //提交作业
    final public static String CLOCKINSHARE = "clockinShare"; //打卡分享
    final public static String SHARESUCCESS = "ShareSuccess"; //打卡分享成功回调
    final public static String GETMYSPEAKING = "getMySpeaking"; //口语存折
    final public static String GETTAGS = "GetTags"; //
    final public static String GETTAGBOOK = "GetTagBook"; //
    final public static String COMMITSPEAKING = "commitSpeaking"; //口语录入
    final public static String UNLOCKBOOK = "unlockBook"; //花蜜解锁
    final public static String GETNETHOMEDATA = "getNetHomeData"; //网课首页
    final public static String GETNETSUGGEST = "getNetSuggest"; //网课购买页
    final public static String GETMYNETCLASS = "getMyNetClass"; //我的网课
    final public static String CONFIRMORDER = "confirmOrder"; //订单确认
    final public static String GETMYADDRESS = "getMyAddress"; //我的收货地址
    final public static String ADDADDRESS = "addAddress"; //添加收货地址
    final public static String EDITADDRESS = "editAddress"; //修改收货地址
    final public static String DELETEADDRESS = "deleteAddress"; //删除收货地址
    final public static String BUYNETWORK = "buyNetWork"; //购买网课
    final public static String GETNETDETAILS = "getNetDetails"; //网课详情
//    final public static String GETLESSON = "getLesson"; //网课列表
    final public static String GETSPEAKING = "getSpeaking"; //口语首页
    final public static String GETRELEASE = "getRelease"; //发布页面
    final public static String RELEASEBOOK = "releaseBook"; //发布
    final public static String RELEASESUCCESS = "releaseSuccess"; //发布成功
    final public static String PLAYTIMES = "playtimes"; //
    final public static String ADDLIKES = "addlikes"; //
    final public static String CANCELLIKES = "Cancellikes"; //
    final public static String SHARECOIN = "shareCoin"; //
    final public static String GETRADIO = "getRadio"; //
    final public static String GETLYRIC = "getLyric"; //
    final public static String LUCKDRAW = "luckDraw"; //
    final public static String LUCKDRAWTEST = "luckDrawtest"; //
    final public static String GETHOMEPAGE = "getHomepage"; //
    final public static String GETPRODUCTIONLIST = "getProductionList"; //
    final public static String CANCELRELEASE = "cancelRelease"; //
    final public static String BUYNUM = "buynum"; //

    final public static String GETLESSON = "ParentChildlessons"; //亲子课
    final public static String BUYSUCCESS = "buySuccess"; //购买网课成功获取推荐课程
    final public static String GETNETEXPDETAILS = "experienceDetails"; //购买的网课具体内容
    final public static String GETPREHEATCONSULT = "PreheatingClass"; //预热课
    final public static String GETLISTENANDREAD = "listeningandreading"; //泛听泛读
    final public static String ORDERQUERY = "OrderQuery"; //泛听泛读
}

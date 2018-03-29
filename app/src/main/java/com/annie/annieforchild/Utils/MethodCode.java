package com.annie.annieforchild.Utils;

/**
 * 字段名称
 * Created by WangLei on 2018/1/30 0030
 */

public class MethodCode {
    final public static String ERRTYPE = "errType";
    final public static String ERRINFO = "errInfo";
    final public static String DATA = "data";
    final public static String SERIALNUMBER = "serialNumber"; //流水号
    final public static String TOKEN = "token";
    final public static String AVATARUEL = "avatarUrl";

    /**
     * EventBus
     */
    final public static int EVENT_MAIN = 0;
    final public static int EVENT_LOGIN = 1; //登陆
    final public static int EVENT_RGISTER = 2; //注册
    final public static int EVENT_VERIFICATION_CODE = 3; //获取验证码
    final public static int EVENT_USERINFO = 4; //获取用户信息
    final public static int EVENT_SCHEDULE = 5; //课表
    final public static int EVENT_ADDCHILD = 6; //注册学员
    final public static int EVENT_ADDCHILD2 = 7; //注册学员2
    final public static int EVENT_USERLIST = 8; //获取学员列表
    final public static int EVENT_UPDATEUSER = 9; //修改个人信息
    final public static int EVENT_RESETPASSWORD = 10; //修改密码
    final public static int EVENT_MYCOLLECTIONS1 = 11; //我的收藏——磨耳朵
    final public static int EVENT_MYCOLLECTIONS2 = 12; //我的收藏——阅读
    final public static int EVENT_MYCOLLECTIONS3 = 13; //我的收藏——口语
    final public static int EVENT_CHANGEPHONE = 14; //修改手机号
    final public static int EVENT_GETNECTAR1 = 15; //我的花蜜——收入
    final public static int EVENT_GETNECTAR2 = 16; //我的花蜜——支出
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
    final public static int EVENT_EXCHANGERECORDING = 27; //花蜜兑换记录
    final public static int EVENT_GETHOMEDATA = 28; //获取首页信息
    final public static int EVENT_GETLISTENING = 29; //获取磨耳朵信息
    final public static int EVENT_GETMUSICCLASSES1 = 30; //获取儿歌分类
    final public static int EVENT_GETMUSICCLASSES2 = 31; //获取诗歌分类
    final public static int EVENT_GETMUSICCLASSES3 = 32; //获取对话分类
    final public static int EVENT_GETMUSICCLASSES4 = 33; //获取绘本分类
    final public static int EVENT_GETMUSICLIST = 34; //获取儿歌列表
    final public static int EVENT_DELETEUSERNAME = 35; //删除学员
}

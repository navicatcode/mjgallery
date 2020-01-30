package com.mjgallery.mjgallery.app;

import android.os.Environment;

import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;

import java.util.ArrayList;
import java.util.List;


public final class AppConstants {


    //文件的存储根路径
    public final static String SAVE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mj/";
    //图片的存储路径
    public static final String IMAGE_PATH = SAVE_FILE_PATH + "image/";


    public static final String VIDEO_PATH = SAVE_FILE_PATH + "video/";
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    public static final String APP_ID = "wxe3bb94b2a02e502c";
    public final static int STATUS_COLSE = 0;//开关状态：关
    public final static int STATUS_OPEN = 1;//开关状态：开
    //-----------消息通知 -----
    public final static String musicOpen = "musicOpen"; //音效开关key,valuse:0为关，1为开
    public final static String messageOpen = "messageOpen"; //新消息通知开关key,valuse:0为关，1为开
    public final static String bannerOpen = "bannerOpen"; //横幅开关key,valuse:0为关，1为开
    public final static String soundOpen = "soundOpen"; //铃声开关key,valuse:0为关，1为开
    public final static String shakeOpen = "shakeOpen"; //震动开关key,valuse:0为关，1为开
    public final static String nightOpen = "nightOpen"; //夜间模式key,valuse:0为关，1为开
    //-----------隐私 -----
    public final static String shouZhiOpen = "shouZhiOpen"; //收支显示key,valuse:0为关，1为开
    public final static String jieSuanOpen = "jieSuanOpen"; //结算方式显示key,valuse:0为关，1为开
    public final static String qRCodeOpen = "qRCodeOpen"; //二维码显示key,valuse:0为关，1为开
    public static boolean isOneStart = false;//判断是否是第一次登陆
    public static String TOKEN = "token";//token信息
    public static String DEVICE_TOKEN = "deviceToken";//设置唯一表示别
    public static String APP_START_IMG = "app_start_img";//app启动图标
    public static String USERID = "userId";//token信息
    public static String HOST = "192.168.0.109";//socket地址
    public static int PORT = 8081;//socket端口
    public static int DISCOVERY_TYPE = 3;//用来保存发现的类型,下标会变化
    public static boolean DISCOVERY_DATA_ISLIST = false;//用来保存资料里面的布局，当前是网格还是list列表
    public static boolean cIsSwitchLanguage = false;//语言
    //
    public static List<String> APP_STRINGS = new ArrayList<>();
    public static List<HomeBean> APP_HOMES = new ArrayList<>();
    public static String KEYWORD;
    //开奖记录多布局的头部
    public final static int TYPE_HEAD = 1;
    //开奖记录多布局内容
    public final static int TYPE_CONTENT = 2;

    //发现资料
    public static final int FOUND_DATA = 1;
    public static final int HOME = 4;
    //发现资料
    public static final int FOUND_VIDEO = 2;
    //发现
    public static final int FOUND = 3;
    public static boolean isShare = false;


//    public static String APP_DOMAIN = "http://192.168.1.212:8100/App/api/";

    public static String APP_DOMAIN = "https://www.xgmj.com/App/api/";
 // public static String APP_DOMAIN = "http://18.163.54.123/App/api/";

}


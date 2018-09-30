package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

public class Constant {	
	public static final String topic = "quake";
	public static final int qos = 2;
	public static String broker = "tcp://127.0.0.1:61713";
	public static String clientNo = "test2";
	public static String audioServer = "127.0.0.1";
	
	public static String typeId;
	public static String orgId;
	public static String version;
	public static String appName;
	public static String updateUrl;
	public static String adUpdateUrl;
	public static int countDown;
	public static int alertLight;
	public static int alertSound;
	public static int emergencyLamp;
	public static int relay;
	public static float delayTime;
	public static String ntpServer;
	public static String openTime;
	public static String closeTime;
	public static boolean cloud;
	public static double longitude;
	public static double latitude;
	public static boolean lock;
	public static String centerName;
	public static int intensity;
	public static int adVolume;
	public static int alertVolume;
	public static int intensityReturn;
	
	public static final String onlineTopic  = "online";
	/*msg -1 未知 ，1在线， 0离线*/
	public static String onlineMsg;
	public static String offlineMsg;
	public static final int onlineQos = 2;
	
	public static final String actionTopic  = "action";
	public static final int actionQos = 2;
	
	public static String voiceServerTopic = "voiceServer";
	public static int voiceServerQos = 2;
	public static String voiceClientTopic = "voiceClient";
	public static int voiceClientQos = 2;
	/**
	 * 
	 */
//	public static void readConfig() {
//        //本地版本文件  
//        File configFile = new File("config.properties");
//        File infoFile = new File("info.properties");
//        
//        FileReader is = null;
//        BufferedReader br = null;
//        
//        //读取本地版本  
//        try {
//            is = new FileReader(configFile);
//            br = new BufferedReader(is);
//            String cfg = br.readLine();
//            
//            config = JSONObject.parseObject(cfg);
//            Constant.broker = config.getString("broker");
//            
//            is = new FileReader(infoFile);
//            br = new BufferedReader(is);
//            String inf = br.readLine();
//            
//            info = JSONObject.parseObject(inf);
//            Constant.appName = config.getString("appName");
//            Constant.clientNo = info.getString("clientNo");
//            Constant.typeId = info.getString("typeId");
//            Constant.orgId = info.getString("orgId");
//            Constant.version = config.getString("version");
//            Constant.countDown = config.getIntValue("countDown");
//            Constant.alertLight = config.getIntValue("alertLight");
//            Constant.alertSound = config.getIntValue("alertSound");
//            Constant.emergencyLamp = config.getIntValue("emergencyLamp");
//            Constant.relay = config.getIntValue("relay");
//            Constant.delayTime = config.getFloatValue("delayTime");
//            Constant.ntpServer = config.getString("ntpServer");
//            Constant.openTime = config.getString("openTime");
//            Constant.closeTime = config.getString("closeTime");
//            Constant.cloud = config.getBooleanValue("cloud");
//            Constant.longitude = config.getDoubleValue("longitude");
//            Constant.latitude = config.getDoubleValue("latitude");
//            Constant.lock = config.getBooleanValue("lock");
//            Constant.intensity = config.getIntValue("intensity");
//            Constant.centerName = config.getString("centerName");
//            Constant.adVolume = config.getIntValue("adVolume");
//            Constant.alertVolume = config.getIntValue("alertVolume");
//            Constant.updateUrl = config.getString("updateUrl");
//            Constant.adUpdateUrl = config.getString("adUpdateUrl");
//            Constant.intensityReturn = config.getIntValue("intensityReturn");
//            
//            Constant.onlineMsg = "{clientNo:'"+Constant.clientNo+"',orgId:'"+Constant.orgId+"',type:"+Constant.typeId+",version:'"+Constant.version+"',msg:'1'}";
//            Constant.offlineMsg = "{clientNo:'"+Constant.clientNo+"',msg:'0'}";
//            Constant.cityKey = info.getString("city");
//            Constant.city = CityKeyUtil.getCityName(info.getString("city"));
//            
//        } catch (FileNotFoundException ex) { 
//        	ex.printStackTrace();
//        } catch (IOException ex) {
//        	ex.printStackTrace();
//        } finally {  
//            //释放资源  
//            try {  
//                br.close();  
//                is.close();  
//            } catch (IOException ex1) {  
//            }  
//        }
//    }
//	
//	public static JSONObject getConfigJsonObject() {
//		return config;
//	}
//	
//	public static JSONObject getInfoJsonObject() {
//		return info;
//	}
//	
//	public static void updateConfigToFile(JSONObject config, JSONObject info) {  
//        //把本地版本文件更新为网络同步  
//		FileOutputStream verOS1 = null;  
//        FileOutputStream verOS2 = null;  
//        try {  
//            verOS1 = new FileOutputStream(configFileName);
//            verOS1.write(config.toJSONString().getBytes());
//            
//            verOS2 = new FileOutputStream(infoFileName);  
//            verOS2.write(info.toJSONString().getBytes());
//            
//        } catch (IOException ex) {  
//        } finally {  
//            try {  
//                verOS1.close();
//                verOS2.close(); 
//            } catch (IOException ex1) {  
//            }  
//        }  
//    }
}

package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import audio.AudioClient;

public class MqttClientUtil {
	private static MqttClient sampleClient;
	private static int i = 1;
	public static MqttClient getInstance() {
		if(sampleClient==null) {
			try {
				sampleClient = new MqttClient(Constant.broker, Constant.clientNo, new MemoryPersistence());
				MqttConnectOptions connOpts = new MqttConnectOptions();
		        connOpts.setUserName("admin");
		        connOpts.setPassword("keson-123".toCharArray());
		        connOpts.setCleanSession(true);
		        connOpts.setAutomaticReconnect(true);
		        //connOpts.setWill(Constant.onlineTopic, AESUtil.encrypt(Constant.offlineMsg).getBytes(), Constant.onlineQos, false);
		        LogUtil.info("MQTT", "正在连接"+Constant.broker);
		        sampleClient.setCallback(new MqttCallback() {
					
					@Override
					public void messageArrived(String topic, MqttMessage message) throws Exception {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						LogUtil.info("接收消息", "接收消息主题 : " + topic);
						LogUtil.info("接收消息", "接收消息Qos : " + message.getQos());
						LogUtil.info("接收消息", "接收消息内容 : " + new String(message.getPayload(),"UTF-8"));
						String msg = AESUtil.decrypt(message.getPayload());
						LogUtil.info("接收消息", "解密消息内容 : " + msg);
						JSONObject info = null;
						try {
							 info  = JSONObject.parseObject(msg);
						} catch (Exception e) {
							e.printStackTrace();
						}
//						操作
						if(topic.equals(Constant.voiceClientTopic)) {
							String todo = info.getString("todo");
							if(todo.equals("status")) {
								MqttClientUtil.publishTopic(Constant.onlineTopic, Constant.onlineQos, Constant.onlineMsg,false);
								return;
							}
							JSONArray clients = info.getJSONArray("clients");
							if(clients.contains(Constant.clientNo)) {
								switch (todo) {
								case "voiceConnect":
									int port = info.getIntValue("port");
									LogUtil.info("语音端口", port+"");
									AudioClient audioClient = new AudioClient(Constant.audioServer, port);
									new Thread(audioClient).start();
									Cache.normalUI.addMonitor("test1", "rmtp:", audioClient);
									break;
								default:
									break;
								}
							}
						}
					}
					
					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void connectionLost(Throwable cause) {
						// TODO Auto-generated method stub
//						StatusUI.setStatus("离线");
//						LogUtil.info("MQTT", "已离线");
//						while (true){
//					        try {//如果没有发生异常说明连接成功，如果发生异常，则死循环
//					            Thread.sleep(10000);
//					            LogUtil.info("MQTT", "客户端连接中断"+sampleClient.isConnected());
//					            if(!sampleClient.isConnected()) {
//					            	sampleClient.connect(connOpts);
//					            }else {
//					            	MqttClientUtil.publishTopic(Constant.onlineTopic, Constant.onlineQos, Constant.onlineMsg,false);
//					            	LogUtil.info("MQTT", "重连成功");
//					            	break;
//					            }
//					        }catch (Exception e){
//					        	LogUtil.info("MQTT", "断线重连中");
//					            continue;
//					        }
//					    }
					}
				});
		        sampleClient.connect(connOpts);
		        LogUtil.info("MQTT", "已连接");
		        //StatusUI.setStatus("在线");
			} catch (MqttException me) {
		        LogUtil.info("MQTT", "reason "+me.getReasonCode());
		        LogUtil.info("MQTT", "msg "+me.getMessage());
		        LogUtil.info("MQTT", "loc "+me.getLocalizedMessage());
		        LogUtil.info("MQTT", "cause "+me.getCause());
		        LogUtil.info("MQTT", "excep "+me);
		        LogUtil.info("MQTT", "网络无法连接");
		        
		        try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	        	if(i<=5) {
	        		LogUtil.info("MQTT", "尝试重连第"+(i)+"次");
		        	i++;
		        	try {
		        		sampleClient = null;
		        		MqttClientUtil.getInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
	        	}
		        
		        //StatusUI.setStatus("离线");
			}
		}
		return sampleClient;
	}
	
	public static void publishTopic(String topic, int qos, String content, boolean retained) {
		MqttClient sampleClient = MqttClientUtil.getInstance();
        LogUtil.info("MQTT", "正在发布消息"+content);
        content = AESUtil.encrypt(content);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        message.setRetained(retained);
        try {
			sampleClient.publish(topic, message);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//StatusUI.setStatus("离线");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//StatusUI.setStatus("离线");
		}
        LogUtil.info("MQTT", "消息发布成功");
	}
	
	public static void subscribeTopic(String topic, int qos) {
		MqttClient sampleClient = MqttClientUtil.getInstance();
        LogUtil.info("MQTT", "订阅主题"+topic);
        try {
        	sampleClient.subscribe(topic,qos);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//StatusUI.setStatus("离线");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//StatusUI.setStatus("离线");
		}
	}
	
	//复制文件  
    private static void CopyFile(File oldFile, File newFile) {
        FileInputStream in = null;  
        FileOutputStream out = null;  
          
        try {
            if(oldFile.exists()){  
                oldFile.delete();  
            }  
            in = new FileInputStream(newFile);  
            out = new FileOutputStream(oldFile);  

            byte[] buffer = new byte[1024 * 5];  
            int size;  
            while ((size = in.read(buffer)) != -1) {  
                out.write(buffer, 0, size);  
                out.flush();  
            }
            
        } catch (FileNotFoundException ex) {  
        	ex.printStackTrace();
        } catch (IOException ex) {  
        	ex.printStackTrace();
        } finally {  
            try {  
                out.close();
                in.close();
            } catch (IOException ex) {
            	ex.printStackTrace();
            }  
        }
    }
}

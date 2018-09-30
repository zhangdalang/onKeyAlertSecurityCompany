package gui;

import util.Cache;
import util.Constant;
import util.MqttClientUtil;

public class Main {
	public static void main(String[] args) {
		MqttClientUtil.subscribeTopic(Constant.voiceClientTopic, Constant.voiceClientQos);
		Cache.normalUI = new NormalUI();
		
	}
}

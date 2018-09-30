package util;

import java.util.HashMap;

import audio.AudioClient;
import gui.NormalUI;
import gui.NormalUI.MonitorFrame;

public class Cache {
	public static HashMap<String, MonitorFrame> monitors = new HashMap<>();
	public static NormalUI normalUI;
}

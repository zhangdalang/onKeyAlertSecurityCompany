package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import audio.AudioClient;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import util.Cache;


public class NormalUI {
	private JFrame jFrame = new JFrame();

	private JLabel panel = new JLabel();
	public NormalUI() {
		jFrame.setSize(1366, 768);
		
		panel.setIcon(new ImageIcon("img/bg.jpg"));
		panel.setLayout(null);
		
		jFrame.add(panel);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(jFrame.isUndecorated()==false) {
			jFrame.setUndecorated(true);
		}
		jFrame.setVisible(true);
		
		new NativeDiscovery().discover();
	}
	
	public void addMonitor(String clientNo, String rtmp, AudioClient audioClient) {
		MonitorFrame monitor = new MonitorFrame("rtmp://rtmp.open.ys7.com/openlive/99b8f4b547aa494aad8adeaf83a73958", 0, audioClient);
		Cache.monitors.put(clientNo, monitor);
		
	}
	
	public void closeMonitor(String clientNo) {
		Cache.monitors.get(clientNo).close();
	}
	
	
	
	public class MonitorFrame{
		private EmbeddedMediaPlayerComponent mediaPlayerComponent;
		private JFrame frame;
		private AudioClient audioClient;
		public MonitorFrame(String rtmp, int location, AudioClient audioClient){
			this.audioClient = audioClient;
			
			frame = new JFrame();
			mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
			
			int width = jFrame.getWidth()/3;
			int height = (jFrame.getHeight()-57)/2;
			int x = location>=3?(location-3)*width:location*width;
			int y = 57+(location<3?0:height);
			
			frame.add(mediaPlayerComponent);
			frame.setBounds(x-5, y, width, height);
			frame.setVisible(true);
			frame.setAlwaysOnTop(true);
			frame.addWindowListener(new WindowListener() {
				public void windowOpened(WindowEvent arg0) {}
				public void windowIconified(WindowEvent arg0) {}
				public void windowDeiconified(WindowEvent arg0) {}
				public void windowDeactivated(WindowEvent arg0) {}
				public void windowClosing(WindowEvent arg0) {
					mediaPlayerComponent.release();
					audioClient.close();
				}
				public void windowClosed(WindowEvent arg0) {}
				public void windowActivated(WindowEvent arg0) {}
			});
			mediaPlayerComponent.getMediaPlayer().setVolume(0);
			mediaPlayerComponent.getMediaPlayer().playMedia(rtmp);
		}
		
		public void close() {
			frame.setVisible(false);
			mediaPlayerComponent.getMediaPlayer().release();
			audioClient.close();
		}
	}
}

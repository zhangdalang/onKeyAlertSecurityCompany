package audio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import util.AudioUtils;
import util.Cache;

/**
 *
 * @author Administrator
 */
public class AudioClient implements Runnable{
    private OutputStream out;
    private InputStream in;
    private Socket socket;
    private byte[] bos=new byte[2024];
	private TargetDataLine targetDataLine;
	private SourceDataLine sourceDataLine;
    //private static ByteArrayOutputStream baos;
    private static byte[] bis=new byte[2024];

    public AudioClient(String host, int port) {
    	try {
            //这里需要根据自己的ip修改
            socket = new Socket(host, port);

            out = socket.getOutputStream();
            System.out.println("客户端:连接成功");
            // 保持通讯
            in = socket.getInputStream();

            targetDataLine = AudioUtils.getTargetDataLine();
            sourceDataLine = AudioUtils.getSourceDataLine();
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void close() {
    	try {
//    		AudioUtils.close();
//    		targetDataLine = null;
//    		sourceDataLine = null;
    		out.close();
			in.close();
	    	socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void run() {
		try {
			while (true) {
	
	            //获取音频流
	            int writeLen = targetDataLine.read(bos,0,bos.length);
	            //发
	            if (bos != null) {
	                //向对方发送拾音器获取到的音频
	                System.out.println("Client 发");
	                out.write(bos,0,writeLen);
	            }
	            //收
	            int readLen = in.read(bis);
	            if (bis != null) {
	                //播放对方发送来的音频
	                System.out.println("Client 收");
	                sourceDataLine.write(bis, 0, readLen);
	            }
	
	        }
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("被动挂断");
			Cache.normalUI.closeMonitor("test1");
			this.close();
		}
			
	}
}
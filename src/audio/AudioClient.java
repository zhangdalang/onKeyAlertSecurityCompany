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
            //������Ҫ�����Լ���ip�޸�
            socket = new Socket(host, port);

            out = socket.getOutputStream();
            System.out.println("�ͻ���:���ӳɹ�");
            // ����ͨѶ
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
	
	            //��ȡ��Ƶ��
	            int writeLen = targetDataLine.read(bos,0,bos.length);
	            //��
	            if (bos != null) {
	                //��Է�����ʰ������ȡ������Ƶ
	                System.out.println("Client ��");
	                out.write(bos,0,writeLen);
	            }
	            //��
	            int readLen = in.read(bis);
	            if (bis != null) {
	                //���ŶԷ�����������Ƶ
	                System.out.println("Client ��");
	                sourceDataLine.write(bis, 0, readLen);
	            }
	
	        }
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("�����Ҷ�");
			Cache.normalUI.closeMonitor("test1");
			this.close();
		}
			
	}
}
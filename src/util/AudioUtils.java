package util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioUtils {

    private static AudioFormat af;
    private static Info info;
    private static TargetDataLine td;
    private static Info dataLineInfo;
    private static SourceDataLine sd;

    /**
     * ��ȡ��Ƶ������(��ʰ����)
     * 
     * @return TargetDataLine
     * @throws LineUnavailableException
     */
    public static TargetDataLine getTargetDataLine() throws LineUnavailableException {
        if (td != null) {
            return td;
        } else {
                // 1.��ȡ��Ƶ������
                // afΪAudioFormatҲ������Ƶ��ʽ
                af = getAudioFormat();
                info = new DataLine.Info(TargetDataLine.class, af);
                // �����tdʵ������
                td = (TargetDataLine) (AudioSystem.getLine(info));
                // �򿪾���ָ����ʽ���У�������ʹ�л�����������ϵͳ��Դ����ÿɲ�����
                td.open(af);
                // ����ĳһ������ִ������ I/O
                td.start();
            return td;
        }

    }
    /**
     * ��ȡ����� д�����ݻ��Զ�����
     * 
     * @return SourceDataLine
     * @throws LineUnavailableException
     */
    public static SourceDataLine getSourceDataLine() throws LineUnavailableException {
        if (sd != null) {
            return sd;
        } else {
                // 2.����Ƶ����ȡ����
                dataLineInfo = new DataLine.Info(SourceDataLine.class, af);
                sd = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                // �򿪾���ָ����ʽ���У�������ʹ�л�����������ϵͳ��Դ����ÿɲ�����
                sd.open(af);
                // ����ĳһ������ִ������ I/O
                sd.start();

            return sd;
        }
    }

    /**
     * ����AudioFormat�Ĳ���
     * 
     * @return AudioFormat
     */
    public static AudioFormat getAudioFormat() {
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 8000f;
        int sampleSize = 16;
        String signedString = "signed";
        boolean bigEndian = true;
        int channels = 1;
        return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
    }

    /**
     * �ر���Դ
     */
    public static void close() {
        if (td != null) {
            td.stop();
        }
        if (sd != null) {
            sd.stop();
        }

    }
}
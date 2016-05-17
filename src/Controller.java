import sun.jvm.hotspot.tools.SysPropsDumper;

/**
 * Created by gustavbodestad on 2016-05-17.
 */
public class Controller {

    private Thread readerTh;
    private Thread writerTh;

    private GUIMutex mutexGUI;

    private Reader reader;
    private Writer writer;

    private String currentString;

    /**
     * Constructor.
     * @param inReader
     * @param inWriter
     */

    public Controller(Reader inReader, Writer inWriter) {
        reader = inReader;
        writer = inWriter;
        mutexGUI = new GUIMutex(this);
        mutexGUI.Start();
        reader.setController(this);
        writer.setController(this);

    }

    /**
     * Starting the threads, and provides reader/writer with information about
     * the input.
     * @param inStr
     * @param inSync
     */
    public void startThreads(String inStr, Boolean inSync) {
        currentString = inStr;
        writer.setCharArray(inStr.toCharArray(), inSync);
        reader.setLenghtOfString(inStr.length(), inSync);
        readerTh = new Thread(reader);
        writerTh = new Thread(writer);
        readerTh.start();
        writerTh.start();
    }

    /**
     * Stopping the threads, and comparing the strings.
     * @param in
     */
    public void stopThreads(String in) {
        readerTh = null;
        writerTh = null;
        System.out.print(in);
        if (in.equals(currentString)) {
            mutexGUI.setStatus(true, in);
        } else {
            mutexGUI.setStatus(false, in);
        }
    }

    /**
     * Sending real time status updates to GUI.
     * @param s
     */
    public void writeReaderStatus(String s) {
        mutexGUI.setTextR(s);
    }

    public void writeWriterStatus(String s) {
        mutexGUI.setTextW(s);
    }

}

import java.util.Observable;
import java.util.Random;

/**
 * Created by gustavbodestad on 2016-05-17.
 */
public class Writer extends Observable implements Runnable{

    private CharacterBuffer buffer;
    private Random rand;
    private char[] chars;
    private Controller cont;
    private Boolean sync;

    /**
     * Constructor.
     * @param inBuffer
     */
    public Writer(CharacterBuffer inBuffer) {
        buffer = inBuffer;
        rand = new Random();
    }

    /**
     * Providing a reference to the controller.
     * @param inCont
     */
    public void setController(Controller inCont) {
        cont = inCont;
    }

    /**
     * Providing the writer with the array of chars.
     * @param inChars
     * @param inSync
     */
    public void setCharArray(char[] inChars, Boolean inSync) {
        chars = inChars;
        sync = inSync;
    }

    /**
     * The method is writing the chararray to the buffer. One synchronized and one not
     * synchronized method.
     */
    @Override
    public void run() {
        int nbr;
        int i = 0;
        if (sync == true) {                                     //Synchronized
            synchronized (buffer) {
                try {
                    while (i < chars.length) {
                        if (buffer.getCharacter() == null) {
                            nbr = rand.nextInt(3000);
                            Thread.sleep(nbr);
                            buffer.writeSync(chars[i]);
                            cont.writeWriterStatus("Writing " + chars[i]);
                            buffer.notify();
                            i++;
                        } else {
                            buffer.wait();
                            cont.writeWriterStatus("Waiting.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        } else try {                                            //NOT Synchronized.
            for (i = 0; i < chars.length; i++) {
                    nbr = rand.nextInt(3000);
                    Thread.sleep(nbr);
                    buffer.writeSync(chars[i]);
                    cont.writeWriterStatus("Writing " + chars[i]);
                }

        } catch (Exception e) {
            e.printStackTrace();

        }
        }
    }



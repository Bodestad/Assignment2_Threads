import java.util.Observable;
import java.util.Random;

/**
 * Created by gustavbodestad on 2016-05-17.
 */
public class Reader implements Runnable {

    private CharacterBuffer buffer;
    private int lenghtOfString;
    private StringBuilder builder;
    private Character temp;
    private Controller cont;
    private Random rand;
    private Boolean sync;

    /**
     * Constructor.
     * @param inBuffer
     */
    public Reader(CharacterBuffer inBuffer) {
        rand = new Random();
        buffer = inBuffer;
        builder = new StringBuilder();
    }

    /**
     * Sets lenght of the String.
     * @param inLength
     * @param inSync
     */
    public void setLenghtOfString(int inLength, Boolean inSync) {
        lenghtOfString = inLength;
        sync = inSync;
    }

    /**
     * Provides reference to controller.
     * @param inCont
     */
    public void setController(Controller inCont) {
        cont = inCont;
    }

    /**
     * Method is reading chars from buffer. One synchronized method and one not synchronized.
     */
    @Override
    public void run() {
        int i = 0;
        int nbr;

        if(sync == true) {                                      //Synchronized
            synchronized (buffer) {
                try {
                    while ((i != lenghtOfString)) {
                        if (buffer.getCharacter() != null) {
                            nbr = rand.nextInt(3000);
                            Thread.sleep(nbr);
                            temp = buffer.readSync();
                            builder.append(temp);
                            cont.writeReaderStatus("Reading " + temp);
                            i++;
                            buffer.notify();
                        } else {
                            buffer.wait();
                            cont.writeReaderStatus("Waiting, no data.");
                        }
                    }

                    cont.stopThreads(builder.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {                                                //NOT Synchronized
            try {
                while ((i != lenghtOfString)) {
                        nbr = rand.nextInt(3000);
                        Thread.sleep(nbr);
                        temp = buffer.readSync();
                        builder.append(temp);
                        cont.writeReaderStatus("Reading " + temp);
                        i++;
                }

                cont.stopThreads(builder.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    }


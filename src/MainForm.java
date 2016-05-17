/**
 * Created by gustavbodestad on 2016-05-17.
 */
public class MainForm {

    public static void main(String[] args) {
        CharacterBuffer buffer = new CharacterBuffer();
        Writer writer = new Writer(buffer);
        Reader reader = new Reader(buffer);
        Controller controller = new Controller(reader, writer);
    }
}

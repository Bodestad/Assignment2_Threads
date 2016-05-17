import java.util.Observable;

/**
 * Created by gustavbodestad on 2016-05-17.
 */
public class CharacterBuffer extends Observable{

    private Character character;

    /**
     * Returning the character.
     * @return
     */
    public Character getCharacter(){
        return character;
    }

    /**
     * Reading the character.
     * @return
     */
    public Character readSync() {
        Character tempChar = character;
        character = null;
        return tempChar;

    }

    /**
     * Writing the character.
     * @param inChar
     */
    public void writeSync(Character inChar ) {
        character = inChar;
    }
}

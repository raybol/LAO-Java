/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

/**
 * Keywords of the lao language
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class KeyWord extends Token {

    private final char type;

    public KeyWord(String identifier) {
        super(identifier);
        type = 'k';
    }

    public char getType() {

        return type;
    }

}

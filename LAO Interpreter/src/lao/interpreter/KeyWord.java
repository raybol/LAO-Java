/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *       CECS3210 Advanced Programming  
 */
package lao.interpreter;

/**
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class KeyWord extends Token {
    private final char type;
    public KeyWord(String identifier) {
        super(identifier);
        type='k';
    }

    public char getType() {
        return type;
    }

}

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
public abstract class Literal extends Token{
    private char type;
    public Literal(String identifier) {
        super(identifier);
    }

    public char getType() {
        return type;
    }
    

}

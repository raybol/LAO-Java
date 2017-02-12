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
public class StringLiteral extends Literal{
    private String Value;
    private final char type;
    public StringLiteral(String identifier) {
        super(identifier);
        type='s';
    }

    @Override
    public char getType() {
        return type;
    }
    

}

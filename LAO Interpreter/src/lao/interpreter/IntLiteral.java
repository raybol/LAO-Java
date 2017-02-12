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
public class IntLiteral extends Literal{
    private char type;

    public IntLiteral(String identifier) {
        super(identifier);
        type='i';
    }

    @Override
    public char getType() {
        return type;
    }
    
    

}

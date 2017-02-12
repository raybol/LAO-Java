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
public class RealLiteral extends Literal{
   private final char type;

    public RealLiteral(String identifier) {
        super(identifier);
        type='r';
    }

    @Override
    public char getType() {
        return type;
    }
    
}

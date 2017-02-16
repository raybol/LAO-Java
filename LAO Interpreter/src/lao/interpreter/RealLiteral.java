/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

/**
 * Represent real values
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class RealLiteral extends Literal {

    private final char type;

    public RealLiteral(String identifier) {
        super(identifier);
        type = 'r';
    }

    @Override
    public char getType() {
        return type;
    }

}

/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

/**
 * Holds boolean values
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class BoolLiteral extends Literal {

    private boolean value;
    private final char type;

    public BoolLiteral(String identifier) {
        super(identifier);
        this.type = 'b';
    }

    public boolean isTrue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public char getType() {
        return type;
    }

}

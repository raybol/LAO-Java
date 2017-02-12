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
public class RealVariable extends Variable {
    private double value;
    private final char type;

    public RealVariable(String identifier) {
        super(identifier);
        type='r';
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public char getType() {
        return type;
    }

    @Override
    public String toString() {
         return Double.toString(value) ;
    }
    

}

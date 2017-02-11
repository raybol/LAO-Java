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
public class IntVariable extends Variable {
    private int value;
    private char type;

    public IntVariable(String identifier) {
        super(identifier);
        type = 'i';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public char getType() {
         return type;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}


/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

/**
 * Holds a string value can be changed later
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class StringVariable extends Variable {

    private String value;
    private final char type;

    public StringVariable(String identifier) {
        super(identifier);
        type = 's';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public char getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }

}

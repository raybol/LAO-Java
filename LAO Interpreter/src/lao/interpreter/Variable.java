/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

/**
 * Abstract class is used to differentiate identifiers
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public abstract class Variable extends Token {

    public Variable(String identifier) {
        super(identifier);
    }

    @Override
    public abstract char getType();

    @Override
    public abstract String toString();

}

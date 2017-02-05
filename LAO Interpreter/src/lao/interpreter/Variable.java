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
public  class Variable extends Token {
    private String type;

    public Variable(String identifier, String type) {
        super(identifier, type);
    }

    public Variable(String identifier) {
        super(identifier);
    }

//    public abstract String getType();

}

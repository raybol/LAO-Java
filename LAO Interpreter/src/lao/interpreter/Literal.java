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
public class Literal extends Token{

    public Literal(String identifier, String type) {
        super(identifier, type);
    }

    public Literal(String identifier) {
        super(identifier);
    }
    

}

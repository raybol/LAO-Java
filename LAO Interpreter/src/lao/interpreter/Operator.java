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
public class Operator extends Token{
    private int  prescedence;

    public Operator(String identifier) {
        super(identifier);

    }

    public int getPrescedence() {
        return prescedence;
    }
    

}

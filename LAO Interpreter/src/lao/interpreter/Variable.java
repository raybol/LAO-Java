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
public abstract class Variable extends Token {

    public Variable(String identifier) {
        super(identifier);
    }

    @Override
    public abstract String toString(); 
//    {
//        return super.toString(); //To change body of generated methods, choose Tools | Templates.
//    }
//    

}

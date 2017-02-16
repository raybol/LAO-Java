/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

/**
 * Abstract class used to utilize polymorphism
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public abstract class Token {

    private String Identifier;

    public Token(String Identifier) {
        this.Identifier = Identifier;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }

    public abstract char getType();

}

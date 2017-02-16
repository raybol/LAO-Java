/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

import java.util.Arrays;

/**
 * Represents operators of the lao language
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class Operator extends Token {

    private final char type;
    private int prescedence;

    public Operator(String identifier) {
        super(identifier);
        type = 'o';
        String or = ".or.";
        String and = ".and.";
        String not = ".not.";
        String[] comp = {".eq.", ".ne."};
        String[] rel = {".gt.", ".lt.", ".ge.", ".le."};
        String[] addsub = {".add.", ".sub.",};
        String[] muldiv = {".mul.", ".div."};

        if (identifier.equals(or)) {
            prescedence = 1;
        } else if (identifier.equals(and)) {
            prescedence = 2;
        } else if (identifier.equals(not)) {
            prescedence = 3;
        } else if (Arrays.asList(comp).contains(identifier.toLowerCase())) {
            prescedence = 4;
        } else if (Arrays.asList(rel).contains(identifier.toLowerCase())) {
            prescedence = 5;
        } else if (Arrays.asList(addsub).contains(identifier.toLowerCase())) {
            prescedence = 6;
        } else if (Arrays.asList(muldiv).contains(identifier.toLowerCase())) {
            prescedence = 7;
        }

    }

    public int getPrescedence() {
        return prescedence;
    }

    public char getType() {
        return type;
    }

}

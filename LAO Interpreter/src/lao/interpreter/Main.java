/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

/**
 * Program: <b>Lao Interpreter</b><br>
 * Date :2/4/2017<br>
 * Description:This program interprets statement of the LAO computer language
 * and executes them<br>
 * History<br>
 * :2/4/2017 ported from c++ to java
 * <br>
 * <br>
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LaoInterpreter interpreter = new LaoInterpreter();
        interpreter.run();

    }

}

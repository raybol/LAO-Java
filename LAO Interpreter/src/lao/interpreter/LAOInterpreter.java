/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *       CECS3210 Advanced Programming  
 */
package lao.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Program: <b></b><br>
 * Date : <br>
 * Description:<br>
 * <br>
 * <br>
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class LAOInterpreter {

    private Vector<Variable> SymbolTable;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        String str = new String();
//        str = ".5E2";
//        String pattern = new String("(-|\\+)?\\d+(\\.\\d+)?((e|E)(-|\\+)?\\d+)?");
//        String pattern2 = new String("(-|\\+)?(\\.\\d+)((e|E)(-|\\+)?\\d+)?");
//        if (str.matches(pattern2)) {
//            System.out.println("true");
//            System.out.println(Double.parseDouble(".5e3"));
//        } else {
//            System.out.println("false");
//        }
        try {
            File file = new File("code.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            ArrayList<String> v = new ArrayList<String>();

            while ((line = bufferedReader.readLine()) != null) {
                v.add(line);

            fileReader.close();
            for (String statement : v) {
                Statement s =new Statement(statement);
               

              
                System.out.println(statement);
                    
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

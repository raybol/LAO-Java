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
import java.util.List;

/**
 * Program: <b></b><br>
 * Date : <br>
 * Description:<br>
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
        LaoInterpreter interp = new LaoInterpreter();

        try {
            File file = new File("code.txt");
            FileReader fileReader;
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //  StringBuilder stringBuffer = new StringBuilder();
            String line;
            //List<String> v = new ArrayList<>();
            List<Statement> code = new ArrayList<>();
            StatementParser parser = new StatementParser();
            int i = 1;
            boolean ok = true;

            while ((line = bufferedReader.readLine()) != null) {
              //  v.add(line);
                Statement s = new Statement(line, i);
//                if(s.getType()=='e')
//                    break;
                parser.setStatement(s);
                interp.execute(s);
                code.add(s);
                i++;
                // System.out.println(line);
            }

            fileReader.close();

//            //execute statements
////            for (String statement : v) {
////                if (statement != null && !statement.isEmpty()) {
////                   
////                    s.setLine(i);
////                   ok= interp.execute(s);
////                }
////                if(!ok){ 
////                    s.printError();
////                    break;
////                 
////                    
////                }
////                
////                   
////                i++;
////                //   System.out.println(s.getStatement().get(0).getIdentifier());
////            }
        } catch (IOException e) {
            System.out.println("file not found");
        }
    }

}

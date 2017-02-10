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
        LaoInterpreter interp =new LaoInterpreter();
        
           int i = 1;
        try {
            File file = new File("code.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            ArrayList<String> v = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                v.add(line);
               // System.out.println(line);
            }

            fileReader.close();

            for (String statement : v) {
//                System.out.println(statement);
//                System.out.println(i);
//                i++;
                if (statement != null && !statement.isEmpty()) {
                   // System.out.println("statement:");
                    Statement s = new Statement(statement);
                    interp.execute(s);
                  
                  //  s.print();
                    //execute(s);
                }

                //   System.out.println(s.getStatement().get(0).getIdentifier());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
       //   interp.printST();

//        String s= new String("f");
//     ArrayList<Variable> SymbolTable;
//       ArrayList<Token> tokenTable=new ArrayList<>();
//        Variable v =new IntVariable("a");
//        Operator op1=new Operator(".and.");
//        tokenTable.add(v);
//        tokenTable.add(op1);
//        Operator op2=op1;
//          int i=0;
//           i=  tokenTable.indexOf(op2);
//          for(Token t :tokenTable){
//            if (t instanceof Variable ) {
//           
//             System.out.println("in");
//            }
//        }
  
//        if(v instanceof Variable)
//            System.out.println("ok");
//        else
//            System.out.println("nope");
    }

}

/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *       CECS3210 Advanced Programming  
 */
package lao.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class Statement {

    private List<Token> statement; 
    private int line;
    private int size;
    private char type;
    private String errorMsg;
    private int errorID;
    private boolean validStatement;
   

    public Statement(ArrayList<Token> statement) {
        this.statement = statement;
    }

    public Statement(String aSstatement, int num) {
        validStatement = true;
        line=num;
        statement = new ArrayList<Token>();
        String[] operators = {".add.", ".sub.", ".mul.", ".div.", ".or.", ".and.", ".not.", ".gt.", ".lt.", ".eq.", ".ge.", ".le.", ".ne."};
        String[] keywords = {"if", "then", "read", "print", "end.", "rem","="};
        String intPattern = new String("(-|\\+)?\\d+");
        String realPattern1 = new String("(-|\\+)?\\d+(\\.\\d+)?((e|E)(-|\\+)?\\d+)?");
        String realPattern2 = new String("(-|\\+)?(\\.\\d+)((e|E)(-|\\+)?\\d+)?");
        String[] result = aSstatement.split("\\s");
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(aSstatement);
        int qcount = countOccurrences(aSstatement, '"');

//        System.out.println(qcount);
//        System.out.println(aSstatement.lastIndexOf('"'));
        if (qcount % 2 == 0) {
            while (m.find()) {
                // statement.add(m.group(1));

                // for (String token : result) {
                // System.out.println(m.group(1));
                if (m.groupCount() > 1) {
                    // System.out.println(m.group());
                }
                if (Arrays.asList(operators).contains(m.group(1).toLowerCase())) {
                    Operator t = new Operator(m.group(1));
                    statement.add(t);
                } else if (Arrays.asList(keywords).contains(m.group(1).toLowerCase())) {
                    KeyWord t = new KeyWord(m.group(1));
                    statement.add(t);
                } else if (m.group(1).matches(intPattern)) {
                    IntLiteral t = new IntLiteral(m.group(1));
                    statement.add(t);
                } else if (m.group(1).matches(realPattern1) || m.group(1).matches(realPattern2)) {
                    RealLiteral t = new RealLiteral(m.group(1));
                    statement.add(t);
                } else if (m.group(1).charAt(0) == '"') {
                    StringLiteral t = new StringLiteral(m.group(1).replace("\"", ""));
                    statement.add(t);
                } else if (isIntVariable(m.group(1))) {
                    IntVariable t = new IntVariable(m.group(1));
                    statement.add(t);
                } else if (isRealVariable(m.group(1))) {
                    RealVariable t = new RealVariable(m.group(1));
                    statement.add(t);
                } else if (isStringVariable(m.group(1))) {
                    StringVariable t = new StringVariable(m.group(1));
                    statement.add(t);
                } else {
                    validStatement = false;
                    errorMsg = "unkown identifier";
                }
            }
        } else {
            validStatement = false;
            errorMsg = "unbalanced statement missing quote sign";
            
        }

        if (validStatement) {
            if (statement.get(0).getIdentifier().equals("rem")) {
                type = 'c';
            } else if (statement.get(0).getIdentifier().equals("print")) {
                type = 'p';
            } else if (statement.get(0).getIdentifier().equals("read")) {
                type = 'r';
            } else if (statement.get(0).getIdentifier().equals("if")) {
                type = 'i';
            } else if (statement.get(0).getIdentifier().equals("end.")) {
                type = 'e';
            } else if (statement.get(0) instanceof Variable) {
                type = 'a';
            } else {
                type = 'u';
            }
        }

    }

    public List<Token> getStatement() {
        return statement;
    }

    public void setStatement(ArrayList<Token> statement) {
        this.statement = statement;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public boolean isValidStatement() {
        return validStatement;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorID() {
        return errorID;
    }

    public void setErrorID(int errorID) {
        this.errorID = errorID;
    }
    
      public void printError() {

        System.out.println("ERROR ON LINE " + getLine());
        for (int i = 0; i <= errorID; i++) {
            System.out.print(statement.get(i).getIdentifier() + " ");
        }
        System.out.println(" ");
          System.out.println(errorMsg);

    }


    public void print() {
        for (Token t : statement) {

            System.out.println(t.getIdentifier());
        }
    }

    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

//    public static  ArrayList<String> tokenizer(String aStatement){
//        
//    } 
    public boolean isIntVariable(String identifier) {

        boolean valid = false;
        if (((identifier.charAt(0) >= 'A' && identifier.charAt(0) <= 'F')
                || (identifier.charAt(0) >= 'a' && identifier.charAt(0) <= 'f'))) {
            valid = true;

        }
        if (identifier.length() > 1) {

            for (int i = 1; identifier.length() < i && valid; i++) {
                if (((identifier.charAt(0) < 'A' && identifier.charAt(0) > 'F')
                        || (identifier.charAt(0) < 'a' && identifier.charAt(0) > 'f'))) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    public boolean isRealVariable(String identifier) {

        boolean valid = false;
        if (((identifier.charAt(0) >= 'G' && identifier.charAt(0) <= 'N')
                || (identifier.charAt(0) >= 'g' && identifier.charAt(0) <= 'n'))) {
            valid = true;
        }
        if (identifier.length() > 1) {
            for (int i = 1; identifier.length() < i && valid; i++) {
                if (((identifier.charAt(0) < 'A' && identifier.charAt(0) > 'F')
                        || (identifier.charAt(0) < 'a' && identifier.charAt(0) > 'f'))) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    public boolean isStringVariable(String identifier) {

        boolean valid = false;
        if (((identifier.charAt(0) >= 'O' && identifier.charAt(0) <= 'Z')
                || (identifier.charAt(0) >= 'o' && identifier.charAt(0) <= 'z'))) {
            valid = true;
        }
        if (identifier.length() > 1) {
            for (int i = 1; identifier.length() < i && valid; i++) {
                if (((identifier.charAt(0) < 'A' && identifier.charAt(0) > 'F')
                        || (identifier.charAt(0) < 'a' && identifier.charAt(0) > 'f'))) {
                    valid = false;
                }
            }
        }
        return valid;
    }

}

/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *      CECS 4200 Programming Languages  
 */
package lao.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a statement of the lao language
 *
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class Statement {

    private List<Token> statement;
    private String stringStatement;
    private int line;
    private int size;
    private char type;
    private String errorMsg;
    private int errorID;
    private boolean validStatement;

    public Statement(ArrayList<Token> statement) {
        this.statement = statement;
    }

    /**
     * Creates a statement objects and sets it's line number in the text code
     *
     * @param aSstatement string line of code
     * @param num line position in the code
     */
    public Statement(String aSstatement, int num) {
        stringStatement = aSstatement;
        validStatement = true;
        line = num;
        statement = new ArrayList<>();
        String[] operators = {".add.", ".sub.", ".mul.", ".div.", ".or.", ".and.", ".not.", ".gt.", ".lt.", ".eq.", ".ge.", ".le.", ".ne."};
        String[] keywords = {"if", "then", "read", "print", "end.", "rem", "="};
        String intPattern = "(-|\\+)?\\d+";
        String realPattern1 = "(-|\\+)?\\d+(\\.\\d+)?((e|E)(-|\\+)?\\d+)?";
        String realPattern2 = "(-|\\+)?(\\.\\d+)((e|E)(-|\\+)?\\d+)?";
        //String[] result = aSstatement.split("\\s");
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(aSstatement);
        int qcount = countOccurrences(aSstatement, '"');
        boolean balanced = (qcount % 2 == 0);
        int i = 0;

        while (m.find()) {

            if (m.groupCount() > 1) {

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
                errorMsg = "unkown identifier: " + m.group(1).toLowerCase();
                errorID = i - 1;
            }
            i++;
        }

        if (statement.get(0).getIdentifier().toLowerCase().equals("rem")) {
            type = 'c';
        } else if (statement.get(0).getIdentifier().toLowerCase().equals("print")) {
            type = 'p';
        } else if (statement.get(0).getIdentifier().toLowerCase().equals("read")) {
            type = 'r';
        } else if (statement.get(0).getIdentifier().toLowerCase().equals("if")) {
            type = 'i';
        } else if (statement.get(0).getIdentifier().toLowerCase().equals("end.")) {
            type = 'e';
        } else if (statement.get(0) instanceof Variable) {
            type = 'a';
        } else {
            type = 'u';
        }

        if (!balanced) {
            validStatement = false;
            errorMsg = "unbalanced statement missing quote sign";
            errorID = i - 1;
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

    /**
     * Sets the error message and position where error was detected
     *
     * @param ID position of error on the statement
     * @param errorMsg error message
     */
    public void setError(int ID, String errorMsg) {
        this.errorID = ID;
        this.errorMsg = errorMsg;
    }

    /**
     * Prints error message
     */
    public void printError() {

        System.out.println("ERROR ON LINE " + getLine() + ": " + stringStatement);
        //System.out.println(stringStatement);
        printStatementType();
        if (!statement.isEmpty()) {
            for (int i = 0; i <= errorID; i++) {
                System.out.print(statement.get(i).getIdentifier() + " ");
            }
        }

        System.out.println(" ");
        System.out.println(errorMsg);

    }

    /**
     * Prints statement type
     */
    public void printStatementType() {
        switch (type) {
            case 'c':
                break;
            case 'p':
                System.out.println("print statement");
                break;
            case 'r':
                System.out.println("read statement");
                break;
            case 'i':
                System.out.println("if/then statement");
                break;
            case 'e':
                System.out.println("end statement");
                break;
            case 'a':
                System.out.println("assignment statement");
                break;
            case 'u':
                System.out.println("unknown statement");
                break;

        }
    }

    /**
     * for testing purposes
     */
    public void print() {
        for (Token t : statement) {

            System.out.println(t.getIdentifier());
        }
    }

    /**
     * Counts number times a character appears in a string
     *
     * @param haystack string to be searched
     * @param needle character that is counted
     * @return number of times a character appears
     */
    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    /**
     * Verifies if the sting is a valid integer variable
     *
     * @param identifier a sting
     * @return returns true is identifier is a integer variable
     */
    public static boolean isIntVariable(String identifier) {

        boolean valid = false;
        if (((identifier.charAt(0) >= 'A' && identifier.charAt(0) <= 'F')
                || (identifier.charAt(0) >= 'a' && identifier.charAt(0) <= 'f'))) {
            valid = true;

        }

        if (identifier.length() > 1) {

            for (int i = 1; i < identifier.length() && valid; i++) {
                if (((identifier.charAt(i) < 'A' || identifier.charAt(i) > 'Z')
                        && (identifier.charAt(i) < 'a' || identifier.charAt(i) > 'z'))) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Verifies if the sting is a valid real variable
     *
     * @param identifier a sting
     * @return returns true is identifier is a real variable
     */
    public static boolean isRealVariable(String identifier) {

        boolean valid = false;
        if (((identifier.charAt(0) >= 'G' && identifier.charAt(0) <= 'N')
                || (identifier.charAt(0) >= 'g' && identifier.charAt(0) <= 'n'))) {
            valid = true;
        }
        if (identifier.length() > 1) {
            for (int i = 1; identifier.length() < i && valid; i++) {
                if (((identifier.charAt(i) < 'A' || identifier.charAt(i) > 'Z')
                        && (identifier.charAt(i) < 'a' || identifier.charAt(i) > 'z'))) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Verifies if the sting is a valid string variable
     *
     * @param identifier a sting
     * @return returns true is identifier is a string variable
     */
    public static boolean isStringVariable(String identifier) {

        boolean valid = false;
        if (((identifier.charAt(0) >= 'O' && identifier.charAt(0) <= 'Z')
                || (identifier.charAt(0) >= 'o' && identifier.charAt(0) <= 'z'))) {
            valid = true;
        }
        if (identifier.length() > 1) {
            for (int i = 1; identifier.length() < i && valid; i++) {
                if (((identifier.charAt(i) < 'A' || identifier.charAt(i) > 'Z')
                        && (identifier.charAt(i) < 'a' || identifier.charAt(i) > 'z'))) {
                    valid = false;
                }
            }
        }
        return valid;
    }

}

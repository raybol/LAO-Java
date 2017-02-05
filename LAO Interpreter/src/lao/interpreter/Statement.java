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

/**
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class Statement {

    private ArrayList<Token> statement;
    private int size;
    private String type;
    private String errorMsg;
    private int errorID;

    public Statement(ArrayList<Token> statement) {
        this.statement = statement;
    }

    public Statement(String aSstatement) {
        String[] operators = {".add.", ".sub.", ".mul.", ".div.", ".or.", ".and.", ".not.", ".gt.", ".lt.", ".eq.", ".ge.", ".le.", ".ne."};
        String[] keywords = { "if", "then", "read", "print",  "end.","rem" };
        String[] result = aSstatement.split("\\s");
        for (String token : result) {
            if (Arrays.asList(operators).contains(token.toLowerCase())) {
                Operator t = new Operator(token);
                statement.add(t);
            } else if (Arrays.asList(keywords).contains(token.toLowerCase())) {
                KeyWord t = new KeyWord(token);
                statement.add(t);
            } 

            //  this->tokenizer(aStatement);
            if (statement.get(0).getIdentifier().equals("rem")) {
                type = "comment";
            } else if (statement.get(0).getIdentifier().equals("print")) {
                type = "print";
            } else if (statement.get(0).getIdentifier().equals("read")) {
                type = "read";
            } else if (statement.get(0).getIdentifier().equals("if")) {
                type = "if";
            } else if (statement.get(0).getIdentifier().equals("end.")) {
                type = "end";
            } else if (statement.get(0) instanceof Variable) {
                type = "assingment";
            } else {
                type = "unknown";
            }
        }

    }

    public ArrayList<Token> getStatement() {
        return statement;
    }

    public void setStatement(ArrayList<Token> statement) {
        this.statement = statement;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isComment() {

        if (statement.get(0).getIdentifier().equals("rem")) {
            return true;
        }
        return false;
    }

    public boolean isAssigment(int start, int end) {
        {
            if (start == end) {
                //print(end);
                errorMsg = "expected assingment";
                return false;
            }
//            if (!((statement[start].isVariable))) {
//                return false;
//            }
            if (!(statement.get(start + 1).getIdentifier().equals("="))) {
                return false;
            }
            if (end > start) {
                return isArExpression(start + 2, end);
            }
            // return true;
        }
        return false;
    }

    public boolean isArExpression(int start, int end) {
        int i;
        boolean hasOP = false;
        i = statement.indexOf(".add.");
        for (Token t : statement) {
            if (t.getIdentifier().equals(".sub.") || t.getIdentifier().equals(".sub.")) {
                hasOP = true;
                break;
            }
        }
//        for (i = start; i < end; i++) {
//            if (statement.elementAt(i).getIdentifier().toLowerCase().equals(".add.")
//                    || statement.elementAt(i).getIdentifier().toLowerCase().equals(".sub.")) {
//                hasOP = true;
//                break;
//            }
//        }

        if (hasOP) {
            if (i == start || i == end) {
                errorMsg = "expected an expression un +/- de mas";
                return false;
            }
            return isArExpression(start, i - 1) && isArFactor(i + 1, end);
        } else {
            /*	if (isOperator(statement[i])) {
		cout << "error 2ops de corrido" << endl;
		return false;
		}

		else*/
            return isArFactor(start, end);
        }
        //return true;
    }

    public boolean isArFactor(int start, int end) {

        boolean hasOP = false;
        int i = start;
        for (i = start; i < end; i++) {
            if (statement.get(i).getIdentifier().toLowerCase().equals(".mul.")
                    || statement.get(i).getIdentifier().toLowerCase().equals(".div.")) {
                hasOP = true;
                break;
            }
        }

        if (hasOP) {
//    ESTO VA
//            if (isStringVariable((statement[i + 1]))
//                    || isStringVariable((statement[i - 1]))
//                    || isString((statement[i + 1]))
//                    || isString((statement[i - 1]))) {
//                //  printStatement(statement, i);  "opertator: " << statement[i] << 
//                errorMsg = " wrong operand data type";
//                errorID = i;
//                return false;
//            }


            /*if (isIntVariable((statement[i + 1])) || isIntVariable((statement[i - 1]))) {

			printStatement(statement, i);
			cout << "opertator: " << statement[i] << " wrong operand data type" << endl;
			return false;


		}*/

 /*if (i == start || i == end) {
			cout << "missing operation" << endl;
			return false;
		}*/
            return isArFactor(start, i - 1) && isArTerm(i + 1, end);
        } else {
            /*	if (isOperator(statement[i + 1])) {
		cout << "error 2ops de corrido" << endl;
		return false;
		}
		else*/
            return isArTerm(start, end);
        }
        //return true;
    }

    boolean isArTerm(int start, int end) {
        /*if (isOperator(statement[start]) || isOperator(statement[end]))
		return false;*/

        String str = new String();
        str = ".5E2";
        String pattern = new String("(-|\\+)?\\d+(\\.\\d+)?((e|E)(-|\\+)?\\d+)?");
        String pattern2 = new String("(-|\\+)?(\\.\\d+)((e|E)(-|\\+)?\\d+)?");

        if (start < end) {
            if ((statement.get(start + 1) instanceof Operator) //                    || isArithmeticOperator(statement[start + 1])
                    //                    || isRelationalOperator(statement[start + 1])
                    //                    || statement[start + 1].toLower() == ".or."
                    //                    || statement[start + 1].toLower() == ".and."
                    ) {
                return isArExpression(start, end);
            } else {
                // printStatement(statement, end);
                errorMsg = "missing operand";
                return false;
            }
        } else if (str.matches(pattern2) //                isRealwExp(statement[start])
                //                || isIntVariable(statement[start])
                //                || isRealVariable(statement[start])
                //                || isStringVariable(statement[start])
                //                || isString(statement[start])
                ) {
            return true;
        } else {
            // printStatement(statement, end);
            errorMsg = "expected variable, string or number";
            return false;
        }
    }

}

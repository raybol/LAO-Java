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
public class StatementParser {

    private Statement statement;

    public StatementParser(Statement statement) {
        this.statement = statement;
    }

    public StatementParser() {
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public boolean isComment() {

        if (statement.getStatement().get(0).getIdentifier().equals("rem")) {
            return true;
        }
        return false;
    }

    public boolean isPrint( int start, int end) {

        if ((end - start) == 1) {//isVariable(statement[start + 1]) ||
            if (statement.getStatement().get(start + 1) instanceof Variable
                    || statement.getStatement().get(start + 1) instanceof Literal) {
                //  isVariable(statement[start + 1]) || isRealwExp(statement[start + 1]) || isString(statement[start + 1])) {
                return true;
            }

        } else if (end == start) {
            return true;
        }
       // printStatement(statement, start);
        System.out.println("expected variable, number or string");
    //    cout << "expected variable, number or string" << endl;
        return false;
    }

    public boolean isAssigment(int start, int end) {
        // System.out.println("arexp");
        if (start == end) {
            //print(end);
            statement.setErrorMsg("expected assingment");
            return false;
        }
//            if (!((statement[start].isVariable))) {
//                return false;
//            }
        if (!(statement.getStatement().get(start + 1).getIdentifier().equals("="))) {

            return false;
        }
        if (end > start) {

            return isArExpression(start + 2, end);
        }
        return true;

        //  return false;
    }

    public boolean isArExpression(int start, int end) {
        int i;
        boolean hasOP = false;
      
             for (i = start; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".add.")
                    || statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".sub.")) {
                hasOP = true;
                break;
            }
        }
        
        
//        for (Token t : statement.getStatement()) {
//            if (t.getIdentifier().equals(".add.") || t.getIdentifier().equals(".sub.")) {
//                  i = statement.getStatement().indexOf(t);
//                hasOP = true;
//                break;
//            }
//        }
//        for (i = start; i < end; i++) {
//            if (statement.elementAt(i).getIdentifier().toLowerCase().equals(".add.")
//                    || statement.elementAt(i).getIdentifier().toLowerCase().equals(".sub.")) {
//                hasOP = true;
//                break;
//            }
//        }

        if (hasOP) {
            if (i == start || i == end) {
                statement.setErrorMsg("expected an expression un +/- de mas");
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

//        for(Token t:statement.getStatement()){
//            if(t instaceof instanceof )
//        }
        for (i = start; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".mul.")
                    || statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".div.")) {
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
        String pattern = new String("(-|\\+)?\\d+(\\.\\d+)?((e|E)(-|\\+)?\\d+)?");
        String pattern2 = new String("(-|\\+)?(\\.\\d+)((e|E)(-|\\+)?\\d+)?");

        if (start < end) {
            if ((statement.getStatement().get(start + 1) instanceof Operator) //                    || isArithmeticOperator(statement[start + 1])
                    //                    || isRelationalOperator(statement[start + 1])
                    //                    || statement[start + 1].toLower() == ".or."
                    //                    || statement[start + 1].toLower() == ".and."
                    ) {
                return isArExpression(start, end);
            } else {
                // printStatement(statement, end);
                statement.setErrorID(end);
                statement.setErrorMsg("missing operand");
                return false;
            }
        } else if ((statement.getStatement().get(start) instanceof Variable)//                isRealwExp(statement[start])
                || statement.getStatement().get(start) instanceof Literal //                || statement.getStatement().get(start + 1) instanceof Literal
                //                || isRealVariable(statement[start])
                //                || isStringVariable(statement[start])
                //                || isString(statement[start])
                ) {
            return true;
        } else {
            // printStatement(statement, end);
            statement.setErrorID(end);
            statement.setErrorMsg("expected variable, string or number");
            return false;
        }
    }

}

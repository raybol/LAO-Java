/*
 *   Polytechnic University of Puerto Rico                       
 *   Electrical & Computer Engineering and                       
 *        Computer Science Department                            
 *                                                                
 *       CECS3210 Advanced Programming  
 */
package lao.interpreter;

import java.util.Arrays;

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

//    public boolean isComment() {
//
//        if (statement.getStatement().get(0).getIdentifier().equals("rem")) {
//            return true;
//        }
//        return false;
//    }
    
    public boolean parse(){
             boolean ok = true;
      
        switch (statement.getType()) {
            case 'c':
                break;
            case 'p':
                isPrint(0, statement.getStatement().size() - 1);
                break;
            case 'r':
                break;
            case 'i':
                break;
            case 'e':
               
                break;
            case 'a':
                ok = isAssigment(0, statement.getStatement().size() - 1);
                break;

            case 'u':
                ok=false;
                break;

        }
        return ok;
    }
    
    public boolean isPrint(int start, int end) {

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
        statement.setError(end, "expected variable, number or string");
        //System.out.println("expected variable, number or string");
        //    cout << "expected variable, number or string" << endl;
        return false;
    }

    boolean isConditionalExpression(int start, int end) {
        if (statement.getStatement().get(end) instanceof Operator) {
            statement.setError(end,"missing operand");

            return false;
        }
//	if (isOperator(statement[end])) {
//		printStatement(statement, end);
//		cout << "missing operand";
//		return false;
//	}
        boolean hasOp = false;
        String[] operators = {".or.", ".and.", ".not.", ".gt.", ".lt.", ".eq.", ".ge.", ".le.", ".ne.", "="};
        for (int i = start; i <= end && !hasOp; i++) {
            if (Arrays.asList(operators).contains(statement.getStatement().get(i).getIdentifier().toLowerCase())) {
                hasOp = true;
            }
        }

        if (!hasOp) {
            statement.setError(end,"missing logical or relation operator");
         
//		printStatement(statement, end);
//		cout << "missing logical or relation operator";
            return false;
        }
        return isLogicalExpression(start, end);
    }

    boolean isLogicalExpression(int start, int end) {

        boolean hasOP = false;
        int i;
        //for (it; it != statement.end(); ++it, i++) 
        for (i = start; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".and.")
                    || statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".or.")) {

                hasOP = true;
                break;
            }
        }

        if (hasOP) {

            return isLogicalExpression(start, i - 1) && isNeg(i + 1, end);
        } else {
            return isNeg(start, end);
        }

    }

    boolean isNeg(int start, int end) {

        boolean hasOP = false;
        int i;
        //for (it; it != statement.end(); ++it, i++) 
        for (i = start; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".not.")) {
                hasOP = true;
                break;
            }
        }

        if (hasOP) {
            if(i==start){
            }else{
            }
            return isNeg(i + 1, end);
        } else {
            return isEQExpression(start, end);
        }
        //return true;
    }

    boolean isEQExpression(int start, int end) {

        boolean hasOP = false;
        int i = start;

        for (i = start; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".eq.")
                    || statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".ne.")) {

                hasOP = true;
                break;
            }
        }

        if (hasOP) {
            return isEQExpression(start, i - 1) && isRelationalexpression(i + 1, end);
        } else {
            return isRelationalexpression(start, end);
        }

    }

    boolean isRelationalexpression(int start, int end) {

        boolean hasOP = false;
        int i = start;

        String[] operators = {".gt.", ".lt.", ".ge.", ".le."};
        for (i = start; i < end; i++) {
            if (Arrays.asList(operators).contains(statement.getStatement().get(i).getIdentifier().toLowerCase())) {

                hasOP = true;
                break;
            }
        }

        if (hasOP) {
//		if (isStringVariable((statement[i + 1])) || isStringVariable((statement[i - 1])) || isString((statement[i + 1])) || isString((statement[i - 1]))) {
//			if (
//				!((isStringVariable((statement[i - 1])) || isString((statement[i - 1]))) && (!(isStringVariable((statement[i + 1]))) || (!isString((statement[i + 1])))))
//				)
//			{
//				printStatement(statement, i);
//				cout << "opertator: " << statement[i] << " wrong operand data type" << endl;
//				return false;
//			}
//
//		}
            return isRelationalexpression(start, i - 1) && isExpression(i + 1, end);
        } else {
            return isExpression(start, end);
        }
        //return true;
    }

    boolean isExpression(int start, int end) {

        int i = start;
        boolean hasOP = false;
        for (i = start; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".add.")
                    || statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".sub.")) {
                hasOP = true;
                break;
            }
        }

        if (hasOP) {
            if (i == start || i == end) {
                statement.setError(start,"expected an expression un +/- de mas");
       

                return false;
            }
            return isExpression(start, i - 1) && isFactor(i + 1, end);
        } else {
            /*	if (isOperator(statement[i])) {
				cout << "error 2ops de corrido" << endl;
				return false;
			}

			else*/
            return isFactor(start, end);
        }
        //return true;
    }

    boolean isFactor(int start, int end) {

        boolean hasOP = false;
        int i;
        for (i = start; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".mul.")
                    || statement.getStatement().get(i).getIdentifier().toLowerCase().equals(".div.")) {
                hasOP = true;
                break;
            }
        }

        if (hasOP) {

//		if (isStringVariable((statement[i + 1])) || isStringVariable((statement[i - 1])) || isString((statement[i + 1])) || isString((statement[i - 1]))) {
//			printStatement(statement, i);
//			cout << "opertator: " << statement[i] << " wrong operand data type" << endl;
//			return false;
//		}
            return isFactor(start, i - 1) && isTerm(i + 1, end);
        } else {
            /*	if (isOperator(statement[i + 1])) {
		cout << "error 2ops de corrido" << endl;
		return false;
		}
		else*/
            return isTerm(start, end);
        }
        //return true;
    }

    boolean isTerm(int start, int end) {

        if (start < end) {
            if ((statement.getStatement().get(start + 1) instanceof Operator)) {
                return isLogicalExpression(start, end);
            } else {

                statement.setError(end,"missing operand");
                return false;

            }
        } else if ((statement.getStatement().get(start) instanceof Variable)
                || statement.getStatement().get(start) instanceof Literal
                ) {
            return true;

        } else {
          
            //statement.printError();
          //  System.out.println("expected variable, string or number");
            statement.setError(end,"expected variable, string or number");
            return false;

        }
    }

    public boolean isAssigment(int start, int end) {
        // System.out.println("arexp");
        if (start == end) {
            //print(end);
            statement.setError(start,"expected assingment");
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
        if (hasOP) {
            if (i == start || i == end) {
                statement.setError(i,"expected an expression");
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

        if (start < end) {
            if ((statement.getStatement().get(start + 1) instanceof Operator) //                    || isArithmeticOperator(statement[start + 1])
                    //                    || isRelationalOperator(statement[start + 1])
                    //                    || statement[start + 1].toLower() == ".or."
                    //                    || statement[start + 1].toLower() == ".and."
                    ) {
                return isArExpression(start, end);
            } else {
                // printStatement(statement, end);
          
                statement.setError(end,"missing operand");
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
           // statement.setErrorID(end);
          //  statement.printError();
   //         System.out.println("expected variable, string or number");
            statement.setError(end,"expected variable, string or number");
            return false;
        }
    }

}

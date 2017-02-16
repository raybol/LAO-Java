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
 * Verifies that a lao language statement is syntactically correct
 *
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

    /**
     * Selects the proper method to execute depending on the statement
     *
     * @return retursn true if statement is valid
     */
    public boolean parse() {
        boolean ok = true;

        switch (statement.getType()) {
            case 'c':
                break;
            case 'p':
                ok = isPrintStatement(0, statement.getStatement().size() - 1);
                break;
            case 'r':
                ok = isReadStatement(0, statement.getStatement().size() - 1);
                break;
            case 'i':
                ok = isIfStatement();
                break;
            case 'e':
                break;
            case 'a':
                ok = isAssigment(0, statement.getStatement().size() - 1);
                break;
            case 'u':
                ok = false;
                break;

        }
        return ok;
    }

    /**
     * Verifies a print statement
     *
     * @param start starting position of the print part on the line of code
     * @param end ending position of the print part on the line of code
     * @return returns true if the print statement doesn't have any errors
     */
    boolean isPrintStatement(int start, int end) {

        if ((end - start) == 1) {
            if (statement.getStatement().get(start + 1) instanceof Variable
                    || statement.getStatement().get(start + 1) instanceof Literal) {
                return true;
            }

        } else if (end == start) {
            return true;
        }
        statement.setError(start, "expected variable, number or string");
        return false;
    }

    /**
     * Verifies a read statement
     *
     * @param start starting position of the read part on the line of code
     * @param end ending position of the read part on the line of code
     * @return returns true if the read statement doesn't have any errors
     */
    boolean isReadStatement(int start, int end) {
//	if (statement[start].toLower() == "read") {
        if ((end - start) == 1) {
            if (statement.getStatement().get(start + 1) instanceof Variable) {
                return true;
            }
        }
//	}
        statement.setError(start, "print can be followed by only 1 variable");
        return false;
    }

    /**
     * Verifies a if/then statement
     *
     * @return returns if the statement doesn't have any errors
     */
    boolean isIfStatement() {

        int i;
        int start = 0;
        int end = statement.getStatement().size() - 1;
        boolean hasThen = false;
        for (i = 1; i < end; i++) {
            if (statement.getStatement().get(i).getIdentifier().toLowerCase().equals("then")) {
                hasThen = true;
                break;
            }
        }
        if (!hasThen) {
            statement.setError(i, "missing then part");
            return false;
        }
        if (i == end) {
            statement.setError(i, "missing condition");
            return false;
        }

        //condition
        if (isConditionalExpression(start + 1, i - 1)) {
            if ("read".equals(statement.getStatement().get(i + 1).getIdentifier().toLowerCase())) {
                return isReadStatement(i + 1, end);
            } else if ("print".equals(statement.getStatement().get(i + 1).getIdentifier().toLowerCase())) {
                return isPrintStatement(i + 1, end);
            } else if (statement.getStatement().get(i + 1) instanceof Variable) {
                return isAssigment(i + 1, end);
            }
        }

        return false;
    }

    /**
     * Verifies a the condition part of an if/then statement
     *
     * @param start starting position of the condition part on the line of code
     * @param end ending position of the condition part on the line of code
     * @return returns true if the condition statement doesn't have any errors
     */
    boolean isConditionalExpression(int start, int end) {
        if (statement.getStatement().get(end) instanceof Operator) {
            statement.setError(end, "missing operand");

            return false;
        }
        boolean hasOp = false;
        String[] operators = {".or.", ".and.", ".not.", ".gt.", ".lt.", ".eq.", ".ge.", ".le.", ".ne.", "="};
        for (int i = start; i <= end && !hasOp; i++) {
            if (Arrays.asList(operators).contains(statement.getStatement().get(i).getIdentifier().toLowerCase())) {
                hasOp = true;
            }
        }

        if (!hasOp) {
            statement.setError(end, "missing logical or relation operator");

            return false;
        }
        return isLogicalExpression(start, end);
    }

    /**
     * Verifies a conditional expression
     *
     * @param start starting position of the conditional expression on the line
     * of code
     * @param end ending position of the conditional expression on the line of
     * code
     * @return returns true if the condition conditional expression doesn't have
     * any errors
     */
    boolean isLogicalExpression(int start, int end) {

        boolean hasOP = false;
        int i;

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

    /**
     * Verifies a logical negation
     *
     * @param start starting position of the logical negation on the line of
     * code
     * @param end ending position of the logical negation on the line of code
     * @return returns true if the logical negation doesn't have any errors
     */
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
            if (i == start) {
                return isNeg(i + 1, end);
            } else {
                return isNeg(start, i - 1) && isEQExpression(i + 1, end);
            }//isNeg(i + 1, end)

        } else {
            return isEQExpression(start, end);
        }
        //return true;
    }

    /**
     * Verifies an equality
     *
     * @param start starting position of the equality on the line of code
     * @param end ending position of the equality on the line of code
     * @return returns true if the equality doesn't have any errors
     */
    boolean isEQExpression(int start, int end) {

        boolean hasOP = false;
        int i;

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

    /**
     * Verifies a relational expression
     *
     * @param start starting position of the relational expression on the line
     * of code
     * @param end ending position of the relational expression on the line of
     * code
     * @return returns true if the relational expression doesn't have any errors
     */
    boolean isRelationalexpression(int start, int end) {

        boolean hasOP = false;
        int i;

        String[] operators = {".gt.", ".lt.", ".ge.", ".le."};
        for (i = start; i < end; i++) {
            if (Arrays.asList(operators).contains(statement.getStatement().get(i).getIdentifier().toLowerCase())) {

                hasOP = true;
                break;
            }
        }

        if (hasOP) {
            return isRelationalexpression(start, i - 1) && isExpression(i + 1, end);
        } else {
            return isExpression(start, end);
        }

    }

    /**
     * Verifies a addition/subraction expression
     *
     * @param start starting position of the addition/subraction expression on
     * the line of code
     * @param end ending position of the addition/subraction expression on the
     * line of code
     * @return returns true if the addition/subraction expression doesn't have
     * any errors
     */
    boolean isExpression(int start, int end) {

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
                statement.setError(start, "expected an expression un +/- de mas");

                return false;
            }
            return isExpression(start, i - 1) && isFactor(i + 1, end);
        } else {

            return isFactor(start, end);
        }

    }

    /**
     * Verifies a multiplication/division expression
     *
     * @param start starting position of the multiplication/division expression
     * on the line of code
     * @param end ending position of the multiplication/division expression on
     * the line of code
     * @return returns true if the multiplication/division expression doesn't
     * have any errors
     */
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

            return isFactor(start, i - 1) && isTerm(i + 1, end);
        } else {

            return isTerm(start, end);
        }

    }

    /**
     * Verifies if the term in the expression is valid
     *
     * @param start starting position of the term on the line of code
     * @param end ending position of the term on the line of code
     * @return returns true if the term doesn't have any errors
     */
    boolean isTerm(int start, int end) {

        if (start < end) {
            if ((statement.getStatement().get(start + 1) instanceof Operator)) {
                return isLogicalExpression(start, end);
            } else {

                statement.setError(end, "missing operand");
                return false;

            }
        } else if ((statement.getStatement().get(start) instanceof Variable)
                || statement.getStatement().get(start) instanceof Literal) {
            return true;

        } else {

            statement.setError(end, "expected variable, string or number");
            return false;

        }
    }

    /**
     * Verifies if the assignment part of a statement is valid
     *
     * @param start starting position of the assignment part on the line of code
     * @param end ending position of the assignment part on the line of code
     * @return returns true if the assignment part doesn't have any errors
     */

    public boolean isAssigment(int start, int end) {

        if (start == end) {

            statement.setError(start, "expected assingment");
            return false;
        }

        if (!(statement.getStatement().get(start + 1).getIdentifier().equals("="))) {

            return false;
        }
        if (end > start) {

            return isArExpression(start + 2, end);
        }
        return true;

    }

    /**
     * Verifies a addition/subraction expression
     *
     * @param start starting position of the addition/subraction expression on
     * the line of code
     * @param end ending position of the addition/subraction expression on the
     * line of code
     * @return returns true if the addition/subraction expression doesn't have
     * any errors
     */
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
                statement.setError(i, "expected an expression");
                return false;
            }
            return isArExpression(start, i - 1) && isArFactor(i + 1, end);
        } else {

            return isArFactor(start, end);
        }

    }

    /**
     * Verifies a multiplication/division expression
     *
     * @param start starting position of the multiplication/division expression
     * on the line of code
     * @param end ending position of the multiplication/division expression on
     * the line of code
     * @return returns true if the multiplication/division expression doesn't
     * have any errors
     */
    public boolean isArFactor(int start, int end) {

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

            return isArFactor(start, i - 1) && isArTerm(i + 1, end);
        } else {

            return isArTerm(start, end);
        }

    }

    /**
     * Verifies if the term in the expression is valid
     *
     * @param start starting position of the term on the line of code
     * @param end ending position of the term on the line of code
     * @return returns true if the term doesn't have any errors
     */
    boolean isArTerm(int start, int end) {

        if (start < end) {
            if ((statement.getStatement().get(start + 1).getIdentifier().toLowerCase().equals(".add."))
                    || (statement.getStatement().get(start + 1).getIdentifier().toLowerCase().equals(".sub."))
                    || (statement.getStatement().get(start + 1).getIdentifier().toLowerCase().equals(".mul."))
                    || (statement.getStatement().get(start + 1).getIdentifier().toLowerCase().equals(".div."))) {
                return isArExpression(start, end);
            } else {

                statement.setError(end, "missing or wrong operand");
                return false;
            }
        } else if ((statement.getStatement().get(start) instanceof Variable)//              
                || statement.getStatement().get(start) instanceof Literal //              
                ) {
            return true;
        } else {

            statement.setError(end, "expected variable, string or number");
            return false;
        }
    }

}

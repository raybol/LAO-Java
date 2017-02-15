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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Raul Feliciano &lt;felicianoraul@gmail.com&gt;
 */
public class LaoInterpreter {

    private Statement currentStatement;
    private List<Variable> SymbolTable;
    private StatementParser sParser;
    private Token result;

    public LaoInterpreter() {
        SymbolTable = new ArrayList<>();
        sParser = new StatementParser();
    }

    /**
     * Startup method read from file and calls execute function
     */
    public void run() {
        try {
            File file = new File("code.txt");
            FileReader fileReader;
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            List<Statement> code = new ArrayList<>();
            StatementParser parser = new StatementParser();
            int i = 1;
            boolean ok;

            while ((line = bufferedReader.readLine()) != null) {
                //  v.add(line);
                if (line != null && !line.isEmpty()) {
                    Statement s = new Statement(line, i);
//                if(s.getType()=='e')
//                    break;
                    if (s.isValidStatement()) {
                        parser.setStatement(s);
                        ok = parser.parse();
                        if (ok) {

                            ok = execute(s);
                            if (!ok) {
                                   
                                s.printError();
                                System.exit(0);
                                //break;
                            }
                        } else {
                            System.out.println("bad parse");
                            s.printError();
                         System.exit(0);
                        }

                        code.add(s);

                    } else {
                        System.out.println("error exit");
                        s.printError();
                     //   System.exit(0);
                       System.exit(0);
                    }
                }
                i++;

            }

            fileReader.close();
            System.out.println("ended before end.");
        } catch (IOException e) {
            System.out.println("file not found");
        }

    }

    /**
     * Decides which type of statement will be executed
     *
     * @param aStatement statement that will be executed
     * @return returns true if statement was properly executed
     */
    public boolean execute(Statement aStatement) {
        boolean ok = true;
        currentStatement = aStatement;
        switch (aStatement.getType()) {
            case 'c':
                break;
            case 'p':
                ok = executePrint(0, aStatement.getStatement().size() - 1);
                break;
            case 'r':
                ok = executeRead(0, aStatement.getStatement().size() - 1);
                break;
            case 'i':
                ok = executeIF();
                break;
            case 'e':
                System.exit(0);
                break;
            case 'a':
                ok = executeAssignment(0);
                break;
            case 'u':
                break;

        }
        return ok;
    }

//    public void printST() {
//        for (Variable v : SymbolTable) {
//            System.out.println(v.getIdentifier());
//            StringVariable sv = (StringVariable) v;
//            System.out.println(sv.getValue());
//
//        }
//    }
    /**
     * Executes an if/then statement
     *
     * @return Returns true if the statement was properly executed
     */
    public boolean executeIF() {

        int end = currentStatement.getStatement().size();
        currentStatement.getStatement().subList(2, end);
        //evaluate expression
        // found=false;
        int i = 0;
        //  int index=0;
        for (Token t : currentStatement.getStatement()) {
            if (t.getIdentifier().toLowerCase().equals("then")) {
                //found = true;
                //  index=i;
                break;
            }
            i++;
        }

        if (evaluateExpression(currentStatement.getStatement().subList(1, i))) {

            // currentStatement.getStatement().subList(2, end);
            //evaluate expression
            if (((BoolLiteral) result).isTrue()) {
                if (currentStatement.getStatement().get(i + 1) instanceof Variable) {
                    return executeAssignment(i + 1);
                } else if (currentStatement.getStatement().get(i + 1) instanceof KeyWord) {
                    if (currentStatement.getStatement().get(i + 1).getIdentifier().toLowerCase().equals("print")) {
                        return executePrint(i + 1, end - 1);
                    } else if (currentStatement.getStatement().get(i + 1).getIdentifier().toLowerCase().equals("read")) {
                        return executeRead(i + 1, end - 1);
                    }
                }
            }

            //  return true;
        } else {
            return false;
        }
        return true;
    }

    /**
     * Executes and assignment statement
     *
     * @param start Starting position of the assign on the statement
     * @return returns true if statement was successfully executed
     */
    public boolean executeAssignment(int start) {

        sParser.setStatement(currentStatement);
        // System.out.println(aStatement.getSize() );
        if (sParser.isAssigment(start, currentStatement.getStatement().size() - 1)) {

            int end = currentStatement.getStatement().size();
            // currentStatement.getStatement().subList(2, end);
            //evaluate expression

            evaluateExpression(currentStatement.getStatement().subList(start + 2, end));

            //assignment
            Variable var = (Variable) currentStatement.getStatement().get(start);
            if (var.getType() == result.getType()) {
                if (var instanceof IntVariable) {
                    IntVariable v = (IntVariable) (var);
                    if (result instanceof IntLiteral) {
                        v.setValue(Integer.parseInt(result.getIdentifier()));
                        SymbolTable.add(v);
                    } else {
                        return false;
                    }
                } else if (var instanceof RealVariable) {
                    RealVariable v = (RealVariable) (var);
                    v.setValue(Double.parseDouble(result.getIdentifier()));
                    SymbolTable.add(v);
                } else if (var instanceof StringVariable) {
                    StringVariable v = (StringVariable) (var);
                    v.setValue(result.getIdentifier());
                    SymbolTable.add(v);
                }

            } else {
                // currentStatement.printError();
                //System.out.println("expression returned wrong type");

                currentStatement.setError(start + 1, "expression returned wrong type");
                return false;
            }

            return true;
            //Variable v = (Variable) (aStatement.getStatement().get(2));
            //SymbolTable.add(aStatement.getStatement().get(2));
        } else {
            return false;
        }
    }
/**
 * Executes a print statement
 * @param start stating position of the print
 * @param end ending position of the print
 * @return returns true if statement was successfully executed
 */
    public boolean executePrint(int start, int end) {
        sParser.setStatement(currentStatement);
        if (sParser.isPrint(start, currentStatement.getStatement().size() - 1)) {
            if (currentStatement.getStatement().size() == 1) {
                System.out.println("");
            } else if (end - start == 1) {//currentStatement.getStatement().size()

                if (currentStatement.getStatement().get(end) instanceof Variable) {
                    boolean found = false;
                    Variable var = (Variable) currentStatement.getStatement().get(end);
                    for (Variable v : SymbolTable) {
                        if (v.getIdentifier().toLowerCase().equals(var.getIdentifier().toLowerCase())) {
                            found = true;
                            var = v;
                            break;
                        }
                    }
                    if (found) {  //print variable
                        System.out.println(var.toString());
                    } else {//variable not found
                        currentStatement.setError(start, "undeclared variable: " + var.getIdentifier());

                        //System.out.println("undeclared variable " + var.getIdentifier());
                        return false;
                    }

                } else {
                    System.out.println(currentStatement.getStatement().get(1).getIdentifier());//print literal
                }
            }
            // System.out.println("");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Executes a read statement
     * @param start Starting position of the read in the statement
     * @param end Ending position of the read in the statement
     * @return returns true if statement was successfully executed
     */
    public boolean executeRead(int start, int end) {
        Scanner input = new Scanner(System.in);
        Variable var = (Variable) currentStatement.getStatement().get(1);
        if (var instanceof IntVariable) {
            IntVariable v = (IntVariable) (var);
            try {
                v.setValue(Integer.parseInt(input.nextLine()));
                SymbolTable.add(v);
            } catch (Exception e) {
                currentStatement.setError(end, "must enter integer");

                //  System.out.println("must enter integer");
                return false;
            }

        } else if (var instanceof RealVariable) {

            RealVariable v = (RealVariable) (var);
            try {
                v.setValue(Double.parseDouble(input.nextLine()));
                SymbolTable.add(v);
            } catch (Exception e) {
                currentStatement.setError(end, "must enter real numeber");

                //  System.out.println("must enter integer");
                return false;
            }

        } else if (var instanceof StringVariable) {
            StringVariable v = (StringVariable) (var);

            v.setValue((input.nextLine()));
            SymbolTable.add(v);
        }
        return true;
    }
/**
 * Evaluates logical/conditional/arithmetic expressions
 * @param expression Expression to be evaluated
 * @return returns true if expression successfully evaluated
 */
    public boolean evaluateExpression(List<Token> expression) {
        //switch variables for literals
        for (Token t : expression) {
            if (t instanceof Variable) {
                int i = expression.indexOf(t);

                boolean found = false;
                Variable var = (Variable) t;
                for (Variable v : SymbolTable) {
                    if (v.getIdentifier().equals(var.getIdentifier())) {
                        found = true;
                        var = v;
                        break;
                    }
                }

                if (found) {
                    if (var instanceof IntVariable) {
                        Integer val = ((IntVariable) var).getValue();
                        IntLiteral literal = new IntLiteral(val.toString());
                        expression.set(i, literal);
                    } else if (var instanceof RealVariable) {
                        Double val = ((RealVariable) var).getValue();
                        RealLiteral literal = new RealLiteral(val.toString());
                        expression.set(i, literal);
                    } else if (var instanceof StringVariable) {
                        String val = ((StringVariable) var).getValue();
                        StringLiteral literal = new StringLiteral(val);
                        expression.set(i, literal);
                    }
                } else {
                    System.out.println("missing variable " + t.getIdentifier() + " declaration");
                    return false;
                }

            }
        }
        //evaluate expression
        //ArrayList<Token> postfix = new ArrayList<>();
        Queue<Token> postfix = new ArrayDeque();
        Stack<Operator> opStack = new Stack();
        Stack<Token> expStack = new Stack();
        for (Token t : expression) {
            // System.out.println(t);
            if (t instanceof Variable || t instanceof Literal) {
                postfix.add(t);
            } else if (t instanceof Operator) {
                while (!opStack.empty() && ((Operator) t).getPrescedence() <= opStack.peek().getPrescedence()) {
                    postfix.add(opStack.peek());
                    opStack.pop();

                }
                opStack.push((Operator) t);
            }

        }
        while (!opStack.isEmpty()) {
            postfix.add(opStack.peek());
            opStack.pop();
        }
        boolean ok = true;
        while (!postfix.isEmpty()) {

            if (postfix.peek() instanceof Operator) {
                // System.out.println(((Operator) postfix.peek()).getPrescedence());
                Literal v2;
                Literal v1;
                switch (((Operator) postfix.peek()).getPrescedence()) {
                    case 1:
                        v2 = (Literal) expStack.peek();
                        expStack.pop();
                        v1 = (Literal) expStack.peek();
                        expStack.pop();
                        ok = or( v1, v2);
                        expStack.push(result);
                        break;
                    case 2:
                        v2 = (Literal) expStack.peek();
                        expStack.pop();
                        v1 = (Literal) expStack.peek();
                        expStack.pop();
                        ok = and( v1, v2);
                        expStack.push(result);
                        break;
                    case 3:
                        v1 = (Literal) expStack.peek();
                        expStack.pop();
                        ok = not( v1);
                        expStack.push(result);
                        break;
                    case 4:
                        v2 = (Literal) expStack.peek();
                        expStack.pop();
                        v1 = (Literal) expStack.peek();
                        expStack.pop();
                        ok = equality((Operator) postfix.peek(), v1, v2);
                        expStack.push(result);
                        break;
                    case 5:
                        v2 = (Literal) expStack.peek();
                        expStack.pop();
                        v1 = (Literal) expStack.peek();
                        expStack.pop();
                        ok = relataion((Operator) postfix.peek(), v1, v2);
                        expStack.push(result);
                        break;
                    case 6:
                        v2 = (Literal) expStack.peek();
                        expStack.pop();
                        v1 = (Literal) expStack.peek();
                        expStack.pop();
                        ok = addsub((Operator) postfix.peek(), v1, v2);
                        //   postfix.poll();
                        expStack.push(result);
                        break;
                    case 7:
                        v2 = (Literal) expStack.peek();
                        expStack.pop();
                        v1 = (Literal) expStack.peek();
                        expStack.pop();
                        ok = muldiv((Operator) postfix.peek(), v1, v2);
                        expStack.push(result);
                        break;
                }

                postfix.poll();
            } else {

                expStack.push(postfix.peek());
                postfix.poll();
            }
            if (!ok) {
                return false;
            }
        }
        result = expStack.peek();
        return true;
    }
    /**
     * Evaluates a logical or
     * @param v1 First parameter
     * @param v2 Second parameter
     * @return 
     */
    public boolean or( Literal v1, Literal v2) {
        BoolLiteral l = new BoolLiteral("bool");

        l.setValue(((BoolLiteral) v1).isTrue() || ((BoolLiteral) v2).isTrue());
        result = l;

        return true;
    }

    public boolean and( Literal v1, Literal v2) {
        BoolLiteral l = new BoolLiteral("bool");

        l.setValue(((BoolLiteral) v1).isTrue() && ((BoolLiteral) v2).isTrue());
        result = l;

        return true;
    }

    public boolean not(Literal v1) {
        BoolLiteral l = new BoolLiteral("bool");

        l.setValue(!(((BoolLiteral) v1).isTrue()));
        result = l;

        return true;
    }

    public boolean equality(Operator op, Literal v1, Literal v2) {
        BoolLiteral l = new BoolLiteral("bool");
        int r;
        Double d1 = Double.parseDouble(v1.getIdentifier());
        Double d2 = Double.parseDouble(v2.getIdentifier());
        if (v1 instanceof StringLiteral || v2 instanceof StringLiteral) {
            if (v1 instanceof StringLiteral && v2 instanceof StringLiteral) {
                r = v1.getIdentifier().compareTo(v2.getIdentifier());
            } else {
                return false;
            }
        } else {
            r = d1.compareTo(d2);
        }

        switch (op.getIdentifier()) {
            case ".eq.":
                if (r == 0) {
                    l.setValue(true);
                } else {
                    l.setValue(false);
                }
                result = l;
                break;
            case ".nq.":
                if (r != 0) {
                    l.setValue(true);
                } else {
                    l.setValue(false);
                }
                result = l;
                break;

        }

        return true;
    }

    public boolean relataion(Operator op, Literal v1, Literal v2) {
        BoolLiteral l = new BoolLiteral("bool");
        int r;
        Double d1 = Double.parseDouble(v1.getIdentifier());
        Double d2 = Double.parseDouble(v2.getIdentifier());
        if (v1 instanceof StringLiteral || v2 instanceof StringLiteral) {
            if (v1 instanceof StringLiteral && v2 instanceof StringLiteral) {
                r = v1.getIdentifier().compareTo(v2.getIdentifier());
            } else {
                return false;
            }
        } else {
            r = d1.compareTo(d2);
        }

        switch (op.getIdentifier()) {
            case ".gt.":
                if (r > 0) {
                    l.setValue(true);
                } else {
                    l.setValue(false);
                }
                result = l;
                break;
            case ".lt.":
                if (r < 0) {
                    l.setValue(true);
                } else {
                    l.setValue(false);
                }
                result = l;
                break;
            case ".ge.":
                if (r >= 0) {
                    l.setValue(true);
                } else {
                    l.setValue(false);
                }
                result = l;
                break;
            case ".le.":
                if (r <= 0) {
                    l.setValue(true);
                } else {
                    l.setValue(false);
                }
                result = l;
                break;
        }

        return true;
    }

    public boolean addsub(Operator op, Literal v1, Literal v2) {
        int ir;
        double dr;
        //addition
        if (op.getIdentifier().equals(".add.")) {
            if (v1 instanceof StringLiteral || v2 instanceof StringLiteral) {
                result = new StringLiteral(v1.getIdentifier() + v2.getIdentifier());
            } else if (v1 instanceof RealLiteral || v2 instanceof RealLiteral) {
                dr = Double.parseDouble(v1.getIdentifier()) + Double.parseDouble(v2.getIdentifier());
                result = new RealLiteral(Double.toString(dr));
            } else {
                ir = Integer.parseInt(v1.getIdentifier()) + Integer.parseInt(v2.getIdentifier());
                result = new IntLiteral(Integer.toString(ir));
            }
            //substraction
        } else if (op.getIdentifier().equals(".sub.")) {
            //cant substract string
            if (v1 instanceof StringLiteral || v2 instanceof StringLiteral) {
                return false;
            } else if (v1 instanceof RealLiteral || v2 instanceof RealLiteral) {
                dr = Double.parseDouble(v1.getIdentifier()) - Double.parseDouble(v2.getIdentifier());
                result = new RealLiteral(Double.toString(dr));
            } else {
                ir = Integer.parseInt(v1.getIdentifier()) - Integer.parseInt(v2.getIdentifier());
                result = new IntLiteral(Integer.toString(ir));
            }
        }
        return true;
    }

    public boolean muldiv(Operator op, Literal v1, Literal v2) {
        int ir;
        double dr;
        //addition
        if (op.getIdentifier().equals(".mul.")) {
            if (v1 instanceof StringLiteral || v2 instanceof StringLiteral) {
                return false;
            } else if (v1 instanceof RealLiteral || v2 instanceof RealLiteral) {
                dr = Double.parseDouble(v1.getIdentifier()) * Double.parseDouble(v2.getIdentifier());
                result = new RealLiteral(Double.toString(dr));
            } else {
                ir = Integer.parseInt(v1.getIdentifier()) * Integer.parseInt(v2.getIdentifier());
                result = new IntLiteral(Integer.toString(ir));
            }
            //substraction
        } else if (op.getIdentifier().equals(".div.")) {
            //cant substract string
            if (v1 instanceof StringLiteral || v2 instanceof StringLiteral) {
                return false;
            } else if (v1 instanceof RealLiteral || v2 instanceof RealLiteral) {
                dr = Double.parseDouble(v1.getIdentifier()) / Double.parseDouble(v2.getIdentifier());
                result = new RealLiteral(Double.toString(dr));
            } else {
                ir = Integer.parseInt(v1.getIdentifier()) / Integer.parseInt(v2.getIdentifier());
                result = new IntLiteral(Integer.toString(ir));
            }
        }
        return true;
    }

}

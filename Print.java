import java.util.ArrayList;

/**
 * Derived class that represents an output statement in the SILLY language.
 * 
 * @author Dave Reed
 * @version 12/22/23
 */
public class Print extends Statement {
    // private Expression expr;
    private ArrayList<Expression> exprs;

    // Modify the Print class so that it can print multiple expressions, separated
    // by commas. When printed, the values should all appear on the same line,
    // separated by a single space. This will require adding field(s) and modifying
    // the constructor (to be able to read and store an arbitrary number of
    // expressions), the execute method (to display all the expression values on a
    // single line), and the toString method (to display the general format).

    /**
     * Reads in a print statement from the specified TokenStream.
     * 
     * @param input the stream to be read from
     */
    public Print(TokenStream input) throws Exception {
        this.exprs = new ArrayList<Expression>();

        if (!input.next().toString().equals("print")) {
            throw new Exception("SYNTAX ERROR: Malformed print statement");
        }
        if (!input.next().toString().equals("(")) {
            throw new Exception("SYNTAX ERROR: Missing '(' in print statement");
        }
        this.exprs.add(new Expression(input));

        while (input.lookAhead().toString().equals(",")) {
            input.next();
            this.exprs.add(new Expression(input));
        }
        if (!input.next().toString().equals(")")) {
            throw new Exception("SYNTAX ERROR: Missing ')' in print statement or missing ','");
        }
    }

    /**
     * Executes the current output statement.
     */
    public void execute() throws Exception {
        String tester = "";
        for (int i = 0; i < this.exprs.size(); i++) {
            String str = this.exprs.get(i).evaluate().toString();
            if (str.charAt(0) == '"') {
                tester += str.substring(1, str.length() - 1) + " ";
            } else {
                tester += str+ " ";
            }
        }
        System.err.println(tester);
    }

    /**
     * Converts the current output statement into a String.
     * 
     * @return the String representation of this statement
     */
    public String toString() {
        // return "print(" + this.expr + ")";

        String str = "print( ";
        for (int i = 0; i < this.exprs.size(); i++) {
            str = str + this.exprs.get(i);
            if (i < this.exprs.size() - 1) {
                str += ", ";
            } else {
                str += " ";
            }
        }
        str = str + ")";
        return str;
    }
}

/**
 * Derived class that represents an output statement in the SILLY language.
 *   @author Dave Reed
 *   @version 12/22/23
 */
public class Print extends Statement {
	private Expression expr;

    /**
     * Reads in a print statement from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Print(TokenStream input) throws Exception {
    	if (!input.next().toString().equals("print")) {
            throw new Exception("SYNTAX ERROR: Malformed print statement");
        } 
    	if (!input.next().toString().equals("(")) {
    		throw new Exception("SYNTAX ERROR: Missing '(' in print statement");
    	}
    	this.expr = new Expression(input);
    	if (!input.next().toString().equals(")")) {
            throw new Exception("SYNTAX ERROR: Missing ')' in print statement");               
    	}
    }

    /**
     * Executes the current output statement.
     */
    public void execute() throws Exception {
    	String str = this.expr.evaluate().toString();
    	if (str.charAt(0) == '"') {
    		System.out.println(str.substring(1, str.length()-1));
    	}
    	else {
    		System.out.println(str);
    	}
    }
    
    /**
     * Converts the current output statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
    	return "print(" + this.expr + ")";
    }
}



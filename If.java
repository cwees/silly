/**
 * Derived class that represents an if statement in the SILLY language.
 * 
 * @author Dave Reed
 * @version 12/22/23
 */
public class If extends Statement {
    private Expression test;
    private Compound ifBody;
    private Compound elseBody;

    /**
     * Reads in an if statement from the specified stream
     * 
     * @param input the stream to be read from
     */
    public If(TokenStream input) throws Exception {
        if (!input.next().toString().equals("if")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        this.test = new Expression(input);
        this.ifBody = new Compound(input);
        if (input.lookAhead().toString().equals("else")) {
            input.next();
            this.elseBody = new Compound(input);
        } else if (input.next().toString().equals("noelse")) {
            // if(input.hasNext()){
            //     //TODO if theres a compound after noelse bracket?
            // }
        } else {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
    }

    /**
     * Executes the current if statement.
     */
    public void execute() throws Exception {
        DataValue test = this.test.evaluate();
        if (test.getType() != DataValue.Type.BOOLEAN_VALUE) {
            throw new Exception("RUNTIME ERROR: If statement requires Boolean test.");
        } else if (((Boolean) test.getValue())) {
            this.ifBody.execute();
        } else if (this.elseBody != null) {
            this.elseBody.execute();
        }
    }

    /**
     * Converts the current if statement into a String.
     * 
     * @return the String representation of this statement
     */
    public String toString() {
        if (this.elseBody == null) {
            return "if " + this.test + " " + this.ifBody + " noelse";
        } else {
            return "if " + this.test + " " + this.ifBody + " else" + this.elseBody;
        }

    }
}

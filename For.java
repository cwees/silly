/**
 * Derived class that represents a for statment in SILLY
 * 
 * @author Chris He
 * @version 1/30/2024
 * 
 */
public class For extends Statement {
    private Integer initialValue;
    private Integer finalValue;
    private Integer iterator;
    private Token identifier;
    private Compound body;

    public For(TokenStream input) throws Exception {

        if (!input.next().toString().equals("for")) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        }
        this.identifier = input.next();
        if (this.identifier.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("SYNTAX ERROR: Illegal assignment in for statement (" + this.identifier + ")");
        }
        if (!Interpreter.MEMORY.isDeclared(this.identifier)) {
            Interpreter.MEMORY.declareVariable(this.identifier);
        } else {
            throw new Exception("SYNTAX ERROR: already existing id in for loop");
        }
        if (!input.next().toString().equals("from")) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        }
        this.initialValue = getInteger(input.next());
        this.iterator = this.initialValue;
        Interpreter.MEMORY.storeValue(this.identifier, new IntegerValue(this.initialValue));
        if (!input.next().toString().equals("to")) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        }

        this.finalValue = getInteger(input.next());
        this.body = new Compound(input);
    }

    public void execute() throws Exception {

        for (int i = Integer.valueOf(Interpreter.MEMORY.lookupValue(this.identifier).toString()); i < this.finalValue
                + 1; i++) {
            this.body.execute();
            int newval = Integer.valueOf(Interpreter.MEMORY.lookupValue(this.identifier).toString()) + 1;
            Interpreter.MEMORY.storeValue(this.identifier, new IntegerValue(newval));
        }
        this.iterator++;
        Interpreter.MEMORY.storeValue(this.identifier, new IntegerValue(this.iterator));

    }

    public String toString() {
        return "for " + this.identifier.toString() + " from " + this.initialValue + " to " + this.finalValue
                + this.body.toString();
    }

    /**
     * gets integer from token
     * 
     * @param variable token to be tested
     * @return integer value from token or memory
     * @throws Exception if variable is not an int
     */
    public int getInteger(Token variable) throws Exception {
        if (variable.getType() == Token.Type.INTEGER_LITERAL) {
            return Integer.valueOf(variable.toString());
        } else if (Interpreter.MEMORY.lookupValue(variable).getType() == DataValue.Type.INTEGER_VALUE) {
            return Integer.valueOf(Interpreter.MEMORY.lookupValue(variable).toString());
        } else {
            throw new Exception("SYNTAX ERROR: improper integer in for loop");
        }
    }
}

/**
 * Derived class that represents a for statment in SILLY
 * 
 * @author Chris He
 * @version 1/30/2024
 * 
 */
public class For extends Statement {
    private Integer initial;
    private Integer finalcount;
    private Token identifier;
    private Compound body;

    // todo test
    public For(TokenStream input) throws Exception {
        if (!input.next().toString().equals("for")) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        }
        this.identifier = input.next();
        if (this.identifier.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("SYNTAX ERROR: Illegal assignment in for statement (" + this.identifier + ")");
        }

        if (!input.next().toString().equals("from")) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        }
        if (!(input.lookAhead().getType() == Token.Type.INTEGER_LITERAL)) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        } else {
            this.initial = Integer.valueOf(new Expression(input).toString());
        }
        if (!input.next().toString().equals("to")) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        }
        if (!(input.lookAhead().getType() == Token.Type.INTEGER_LITERAL)) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        } else {
            this.finalcount = Integer.valueOf(new Expression(input).toString());
        }
        this.body = new Compound(input);
    }

    public void execute() throws Exception {
        Interpreter.MEMORY.beginNewScope();
        if (!Interpreter.MEMORY.isDeclared(this.identifier)) {
            Interpreter.MEMORY.declareVariable(this.identifier);
        } else {
            throw new Exception("SYNTAX ERROR: already existing id in for loop");
        }
        Interpreter.MEMORY.storeValue(this.identifier, new IntegerValue(this.initial));
        for (int i = this.initial; i < this.finalcount + 1; i++) {
            this.body.execute();
            int newval = Integer.valueOf(Interpreter.MEMORY.lookupValue(this.identifier).toString()) + 1;
            Interpreter.MEMORY.storeValue(this.identifier, new IntegerValue(newval));
            ;
        }
        Interpreter.MEMORY.endCurrentScope();
    }

    public String toString() {
        return "for " + this.identifier.toString() + " from " + this.initial + " to " + this.finalcount
                + this.body.toString();
    }
    // public Boolean isInteger(Token variable){
        
    // }
}

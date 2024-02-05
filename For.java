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
    private Token tok;
    private Compound body;

    // todo test
    public For(TokenStream input) throws Exception {
        if (!input.next().toString().equals("for")) {
            throw new Exception("SYNTAX ERROR: Malformed for statement");
        }
        this.tok = new Token(new Expression(input).toString());

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
        if (!Interpreter.MEMORY.isDeclared(this.tok)) {
            Interpreter.MEMORY.declareVariable(this.tok);
        } else{
            throw new Exception ("SYNTAX ERROR: preexisting id in for loop");
        }
        //todo make it so that it can read the token in the compound
        // Interpreter.MEMORY.storeValue(this.tok,this.body.evaluate());
        for (int i = this.initial; i < this.finalcount+1; i++) {
            this.body.execute();
        }
        Interpreter.MEMORY.endCurrentScope();
    }

    public String toString() {
        return "for " + this.tok.toString() + " from " + this.initial + " to " + this.finalcount + this.body.toString();
    }

}

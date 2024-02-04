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
    private String iterator;
    private Compound body;

    // todo test
    public For(TokenStream input) throws Exception {
        if (!input.next().toString().equals("for")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        if (!(input.next().getType() == Token.Type.STRING_LITERAL)) {
            throw new Exception("SYNTAX ERROR: MALFORMED if,");
        }
        this.iterator = input.toString();
        if (!input.next().toString().equals("from")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        if (!(input.next().getType() == Token.Type.INTEGER_LITERAL)) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        } else {
            this.initial = Integer.valueOf(input.toString());
        }
        if (!input.next().toString().equals("to")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        if (!(input.next().getType() == Token.Type.INTEGER_LITERAL)) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        } else {
            this.finalcount = Integer.valueOf(input.toString());
        }
        this.body = new Compound(input);
    }

    public void execute() throws Exception {
        for (int i = this.initial; i < this.finalcount; i++) {
            this.body.execute();
        }
    }

    public String toString() {
        return "for " + this.iterator + " from " + this.initial + " to " + this.finalcount + this.body;
    }

}

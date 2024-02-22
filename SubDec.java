import java.util.ArrayList;

public class SubDec extends Statement {
    private Token functionName;
    private ArrayList<Token> parameters;
    private Compound body;

    public SubDec(TokenStream input) throws Exception {
        if (!input.next().toString().equals("sub")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        this.functionName = input.next();
        if (this.functionName.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("SYNTAX ERROR: Malformed identifier in subroutine statement");
        }
        if (!input.next().toString().equals("(")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        this.parameters = new ArrayList<Token>();
        while (input.lookAhead().getType() == Token.Type.IDENTIFIER) {
            this.parameters.add(input.next());
            if (!input.lookAhead().toString().equals(",")) {
                input.next();
                continue;
            } else if (!input.lookAhead().toString().equals(")")) {
                break;
            } else {
                System.out.println(input.toString());
                throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
            }

        }
        if (!input.next().toString().equals(")")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }

        this.body = new Compound(input);
        // ()
        // (item)
        // (item, item)

    }
    // <subdecl> --> 'sub' <id> '(' [ <id> { ',' <id> } ] ')' <compound>

    public String toString() {
        String str = "sub " + this.functionName + "( ";
        for (int i = 0; i < this.parameters.size(); i++) {
            str += this.parameters.get(i).toString() + ", ";
        }
        str = str.substring(0, str.length() - 2);
        str += ") " + this.body.toString();
        return str;
    }

    public void execute() throws Exception {
        // TODO Auto-generated method stub
        // Executing a subroutine declaration when there already exists a variable or
        // subroutine with that same name should result in a run-time error. Note: when
        // a subroutine is declared, it is assumed to be in the global scope, regardless
        // of where that declaration occurs.

        // global scope

        // iterate through this.parameters, error if they have been declared beforehand,
        // else initialize
        // store body somehow
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}

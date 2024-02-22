import java.util.ArrayList;

public class SubDec extends Statement {
    private Token identifier;
    private ArrayList<Token> identifiers;
    private Compound body;

    @Override
    public While(TokenStream input) throws Exception:
        this.identifiers = new ArrayList<Token>();

        if (!input.next().toString().equals("sub")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        this.identifier = input.next();
        if (this.identifier.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        if (!input.next().toString().equals("(")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        if(!input.next().toString().equals(")")){
        //if there are multiple parameters
        //add to arraylist
        //check for syntax ","
        }
        this.body = new Compound(input);


    }
    // <subdecl> --> 'sub' <id> '(' [ <id> { ',' <id> } ] ')' <compound>

    // <subcall> --> 'call' <id> '(' [ <expr> { ',' <expr> } ] ')'
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    @Override
    public void execute() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}

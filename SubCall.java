import java.util.ArrayList;

public class SubCall extends Statement {
    private Token functionName;
    private ArrayList<TokenStream> parameters;

    //TODO convert tokenstream parameters to expressions
    
    public SubCall(TokenStream input) throws Exception {
        if (!input.next().toString().equals("call")) {
            throw new Exception("SYNTAX ERROR: Malformed subcall statement");
        }
        this.functionName = input.next();
        if (this.functionName.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("SYNTAX ERROR: Malformed identifier in subcall statement");
        }
        if (!input.next().toString().equals("(")) {
            throw new Exception("SYNTAX ERROR: Malformed subcall statement");
        }
        this.parameters = new ArrayList<TokenStream>();
        this.parameterText = new ArrayList<Token>();
        while (!input.lookAhead().toString().equals(")")) {
            this.parameters.add(input);
            input.next();
            if (input.lookAhead().toString().equals(",")) {
                continue;
            }
        }
        
        if (!input.next().toString().equals(")")) {
            throw new Exception("SYNTAX ERROR: Malformed subcall statement");
        }
        // <subcall> --> 'call' <id> '(' [ <expr> { ',' <expr> } ] ')'
    }

    @Override
    public void execute() throws Exception {
        Interpreter.MEMORY.beginNewScope();
        SubRoutine item = Interpreter.MEMORY.getSubroutine(this.functionName.toString());
        if (this.parameters.size() != item.getTokens().size()) {
            throw new Exception(
                    "Error, number of ids and expressions in declaring & calling a subroutine do not match");
        }
        for (int i = 0; i < item.getTokens().size(); i++) {

            Interpreter.MEMORY.declareVariable(item.getTokens().get(i).lookAhead());
            Expression test = new Expression(this.parameters.get(i));
            Interpreter.MEMORY.storeValue(item.getTokens().get(i).lookAhead(), test.evaluate());
        }
        item.getCompound().execute();

        Interpreter.MEMORY.endCurrentScope();
        // declare new scope
        // function

        // check if subdec and subcall parameter counts line up
        // TODO only when called is when you check for variables/etc in new scope
        // cannot reference

        // cannot get variables from outside of scope/parental scopes
        // end scope
    }

    public String toString() {
        String str = "call " + this.functionName + "( ";
        for (int i = 0; i < this.parameters.size(); i++) {
            str += this.parameters.get(i).toString() + ", ";
        }
        // str = str.substring(0, str.length() - 2);
        str += ")";
        return str;
    }


}

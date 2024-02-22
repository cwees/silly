import java.util.ArrayList;

public class SubCall extends Statement {
    private Token functionName;
    private ArrayList<Expression> parameters;

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
        this.parameters = new ArrayList<Expression>();
        while (!input.lookAhead().toString().equals(")")) {
            input.next();
            this.parameters.add(new Expression(input));
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
        if (!Interpreter.MEMORY.isDeclared(this.identifier)) {
            Interpreter.MEMORY.declareVariable(this.identifier);
        } else {
            throw new Exception("SYNTAX ERROR: already existing id in for loop");
        }
        // declare new scope
        // function

        // check if subdec and subcall parameter counts line up
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");

        // end scope
    }

    @Override
    public String toString() {
        String str = "call " + this.functionName + " (";
        for (int i = 0; i < this.parameters.size(); i++) {
            str += this.parameters.get(i).toString() + ", ";
        }
        str = str.substring(0, str.length() - 2);
        str += ")";
        return str;
    }

}

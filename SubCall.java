import java.util.ArrayList;

public class SubCall extends Statement {
    private Token functionName;
    private ArrayList<Token> parameters;

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
        this.parameters = new ArrayList<Token>();
        while (!input.lookAhead().toString().equals(")")) {
            this.parameters.add(input.next());
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

        Interpreter.MEMORY.endCurrentScope();
        // declare new scope
        // function

        // check if subdec and subcall parameter counts line up
        // TODO Auto-generated method stub
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

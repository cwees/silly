import java.util.ArrayList;

public class SubCall extends Statement {
    private Token functionName;
    private ArrayList<Expression> expressions;

    // TODO convert tokenstream parameters to expressions

    public SubCall(TokenStream input) throws Exception {
        if (!input.next().toString().equals("call")) {
            throw new Exception("SYNTAX ERROR: Malformed subcall statement");
        }
        this.functionName = input.next();
        if (this.functionName.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("SYNTAX ERROR: Malformed identifier in subcall statement");
        }
        if (!input.next().toString().equals("(")) {
            throw new Exception("SYNTAX ERROR: Missing '(' in SubCall statement");
        }
        this.expressions = new ArrayList<Expression>();
        if (!input.next().toString().equals(")")) {
            this.expressions.add(new Expression(input));
            
            while (input.lookAhead().toString().equals(",")) {
                input.next();
                this.expressions.add(new Expression(input));
            }
        }
        // if (!input.next().toString().equals(")")) {
        //     throw new Exception("SYNTAX ERROR: Malformed subcall statement");
        // }
        // <subcall> --> 'call' <id> '(' [ <expr> { ',' <expr> } ] ')'
    }

    @Override
    public void execute() throws Exception {
        Interpreter.MEMORY.beginNewScope();

        if (!Interpreter.MEMORY.isSubroutine(this.functionName.toString())) {
            throw new Exception("RUNTIME ERROR, subroutine not found");
        }

        SubRoutine item = Interpreter.MEMORY.getSubroutine(this.functionName.toString());
        if (this.expressions.size() != item.getTokens().size()) {
            throw new Exception(
                    "RUNTIME ERROR, number of ids and expressions in declaring & calling a subroutine do not match");
        }
        for (int i = 0; i < item.getTokens().size(); i++) {
            Interpreter.MEMORY.declareVariable(item.getTokens().get(i));
            Interpreter.MEMORY.storeValue(item.getTokens().get(i), this.expressions.get(i).evaluate());
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
        for (int i = 0; i < this.expressions.size(); i++) {
            str += this.expressions.get(i).toString() + ", ";
        }
        // str = str.substring(0, str.length() - 2);
        str += ")";
        return str;
    }

}

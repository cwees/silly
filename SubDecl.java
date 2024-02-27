import java.util.ArrayList;

public class SubDecl extends Statement {
    private Token functionName;
    private ArrayList<TokenStream> parameters;
    private Compound body;

    public SubDecl(TokenStream input) throws Exception {
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
        this.parameters = new ArrayList<TokenStream>();
        while (input.lookAhead().getType() == Token.Type.IDENTIFIER) {
            this.parameters.add(input);
            input.next();
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
        if (Interpreter.MEMORY.isSubroutine(functionName.toString())) {
            throw new Error("Subroutine is already declared");
        } else if (Interpreter.MEMORY.isDeclared(functionName)) {
            throw new Error("Subroutine Name is already declared as a variable!");

        }

        // System.out.println(this.body);
        // System.out.println(functionName.toString());
        // System.out.println(this.parameters);
        Interpreter.MEMORY.addSubroutine(this.body, functionName.toString(), this.parameters);

        // TODO just store stuff

        // if (this.functionName.getType() == Token.Type.INTEGER_LITERAL) {
        // Integer.valueOf(this.functionName.toString());
        // } else if (Interpreter.MEMORY.lookupValue(this.functionName).getType() ==
        // DataValue.Type.INTEGER_VALUE) {
        // Integer.valueOf(Interpreter.MEMORY.lookupValue(this.functionName).toString());
        // } else {
        // throw new Exception("SYNTAX ERROR: improper integer in for loop");
        // }
        // TODO Auto-generated method stub
        // Executing a subroutine declaration when there already exists a variable or
        // subroutine with that same name should result in a run-time error. Note: when
        // a subroutine is declared, it is assumed to be in the global scope, regardless
        // of where that declaration occurs.
    }

}

import java.util.ArrayList;

/**
 * Derived class that represents a subroutine declaration statement in the SILLY
 * language.
 * 
 * @author Chris He
 * @version working
 */
public class SubDecl extends Statement {
    private Token functionName;
    private ArrayList<Token> parameters;
    private Compound body;

    public SubDecl(TokenStream input) throws Exception {
        if (!input.next().toString().equals("sub")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        this.functionName = input.next();
        if (this.functionName.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("SYNTAX ERROR: Malformed identifier in subroutine statement");
        }
        if (!(input.next().toString().equals("("))) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        this.parameters = new ArrayList<Token>();
        if (!input.lookAhead().toString().equals(")")) {
            this.parameters.add(input.lookAhead());
            input.next();
            while (input.lookAhead().toString().equals(",")) {
                input.next();
                if (input.lookAhead().getType() == Token.Type.IDENTIFIER) {
                    this.parameters.add(input.next());
                } else {
                    throw new Exception("SYNTAX ERROR: Malformed identifier in subroutine statement");
                }
            }
        }
        if (!input.next().toString().equals(")")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine statement");
        }
        this.body = new Compound(input);
    }

    public String toString() {
        String str = "sub " + this.functionName + "(";
        for (int i = 0; i < this.parameters.size(); i++) {
            str += this.parameters.get(i).toString() + ", ";
        }
        if (this.parameters.size() > 0) {
            str = str.substring(0, str.length() - 2);
        }
        str += ") " + this.body.toString();
        return str;
    }

    public void execute() throws Exception {
        if (Interpreter.MEMORY.isSubroutine(functionName.toString())) {
            throw new Error("Subroutine is already declared");
        } else if (Interpreter.MEMORY.isDeclared(functionName)) {
            throw new Error("Subroutine Name is already declared as a variable!");
        }
        Interpreter.MEMORY.addSubroutine(this.body, functionName.toString(), this.parameters);
    }
}

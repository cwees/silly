/**
 * Derived class that represents a for statment in SILLY
 * 
 * @author Chris He
 * @version 1/30/2024
 * 
 */
public class For extends Statement {
    private Integer count;
    private Integer finalcount;

    public For(TokenStream input) throws Exception {
        if (!input.next().toString().equals("for")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
    }

    public void execute() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

}

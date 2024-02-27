import java.util.ArrayList;

public class SubRoutine {
    private Compound comp;
    private String id;
    private ArrayList<Token> tokens;

    public SubRoutine(Compound comp, String id, ArrayList<Token> tokens) {
        this.id = id;
        this.comp = comp;
        this.tokens = tokens;

    }

    /**
     * @return String id of item
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return Compound
     */
    public Compound getCompound() {
        return this.comp;
    }

    /**
     * @return ArrayList<TokenStream>
     */
    public ArrayList<Token> getTokens() {
        return this.tokens;
    }
}

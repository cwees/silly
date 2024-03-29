import java.util.Arrays;

/**
 * Class that represents a token in the SILLY language.
 * 
 * @author Dave Reed
 * @version 12/24/23
 */
public class Token {
    private String strVal;

    public static String[] keywords = { ",", "{", "}", "(", ")", "=", "print", "if", "while" };
    public static String[] unaryOps = { "not", "#" };
    public static String[] binaryOps = { "+", "~", "*", "/", "%", "^", "and", "or",
            "==", "!=", ">", ">=", "<", "<=", "@" };

    public static enum Type {
        KEYWORD, UNARY_OP, BINARY_OP, IDENTIFIER, UNKNOWN,
        INTEGER_LITERAL, STRING_LITERAL, BOOLEAN_LITERAL
    }

    /**
     * Constructs a token out of the given string.
     * 
     * @param str the string value of the token
     */
    public Token(String str) {
        this.strVal = str;
    }

    /**
     * Identifies what type of token it is.
     * 
     * @return the token type (e.g., Token.Type.IDENTIFIER)
     */
    public Token.Type getType() {
        if (Arrays.asList(Token.keywords).contains(this.strVal)) {
            return Token.Type.KEYWORD;
        } else if (Arrays.asList(Token.unaryOps).contains(this.strVal)) {
            return Token.Type.UNARY_OP;
        } else if (Arrays.asList(Token.binaryOps).contains(this.strVal)) {
            return Token.Type.BINARY_OP;
        } else if (this.strVal.equals("true") || this.strVal.equals("false")) {
            return Token.Type.BOOLEAN_LITERAL;
        } else if (Character.isDigit(this.strVal.charAt(0)) || this.strVal.charAt(0) == '-') {
            if (this.strVal.equals("-")) {
                return Token.Type.UNKNOWN;
            }
            for (int i = 1; i < this.strVal.length(); i++) {
                if (!Character.isDigit(this.strVal.charAt(i))) {
                    return Token.Type.UNKNOWN;
                }
            }
            return Token.Type.INTEGER_LITERAL;
        } else if (Character.isLetter(this.strVal.charAt(0))) {
            for (int i = 1; i < this.strVal.length(); i++) {
                if (!Character.isLetterOrDigit(this.strVal.charAt(i))) {
                    return Token.Type.UNKNOWN;
                }
            }
            return Token.Type.IDENTIFIER;
        } else if (this.strVal.charAt(0) == '"') {
            if (this.strVal.length() == 1 || this.strVal.charAt(this.strVal.length() - 1) != '"') {
                return Token.Type.UNKNOWN;
            }
            return Token.Type.STRING_LITERAL;
        } else {
            return Token.Type.UNKNOWN;
        }
    }

    /**
     * Determines when two tokens are identical.
     * 
     * @param other the other token being compared
     * @return whether the two tokens represent the same string value
     */
    public boolean equals(Object other) {
        return this.strVal.equals(((Token) other).strVal);
    }

    /**
     * Converts the token to its string representation.
     * 
     * @return the string representation
     */
    public String toString() {
        return this.strVal;
    }

    /**
     * Generates a hash code for a Token (based on its String hash code).
     * 
     * @return a hash code for the Token
     */
    public int hashCode() {
        return this.strVal.hashCode();
    }
}

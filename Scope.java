import java.util.HashMap;

/**
 * Class that represents a scope in the program execution.
 *   @author Dave Reed
 *   @version 12/22/23
 */
public class Scope {
    private HashMap<Token, Integer> map;
    private Scope parentScope;
    
    /**
     * Constructs an empty scope.
     * @param parent a reference to the parent scope (null if no parent)
     */
    public Scope(Scope parent) {
    	this.map = new HashMap<Token, Integer>();
    	this.parentScope = parent;
    }
    
    /**
     * Determines the bindings associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the bindings associated with that variable
     */      
    public Integer lookupInScope(Token variable) {
        return this.map.get(variable);
    }
    
    /**
     * Stores a variable/value in the stack segment.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void storeInScope(Token variable, int addr)  {
        this.map.put(variable, addr);
    }
    
    /**
     * Accesses the parent scope.
     * @return the parent scope (or null if no parent)
     */
    public Scope getParentScope() {
    	return this.parentScope;
    }
}

import java.util.Stack;
import java.util.ArrayList;

/**
 * Class that defines the memory space for the SILLY interpreter.
 *   @author Dave Reed
 *   @version 12/22/23
 */
public class MemorySpace {
    private Stack<Scope> stackSegment;
    private ArrayList<DataValue> heapSegment;

    /**
     * Constructs an empty memory space.
     */
    public MemorySpace() {
        this.stackSegment = new Stack<Scope>();
        this.stackSegment.push(new Scope(null));
        this.heapSegment = new ArrayList<DataValue>();
    }
    
    /**
     * Adds a new scope to the top of the runtime stack (linked to previous top).
     */
    public void beginNewScope() {
    	this.stackSegment.push(new Scope(this.stackSegment.peek()));
    }
    
    /**
     * Removes the current scope from the top of the runtime stack.
     */
    public void endCurrentScope() {
    	this.stackSegment.pop();
    }   	

    /**
     * Declares a variable (without storing an actual value).
     *   @param variable the variable to be declared
     */
    public void declareVariable(Token variable) {
        this.stackSegment.peek().storeInScope(variable, -1);
    }
    
    /** 
     * Determines if a variable is already declared.
     * @param variable the variable to be found
     * @return true if it is declared and/or assigned
     */
    public boolean isDeclared(Token variable) {
    	return (this.findScopeinStack(variable) != null);
    }
    
    /**
     * Stores a variable/value in the stack segment.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void storeValue(Token variable, DataValue val)  {
    	Scope found = this.findScopeinStack(variable);
    	int addr = this.findValueInHeap(val);
    	found.storeInScope(variable, addr);
    }
    
    /**
     * Determines the value associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the value associated with that variable
     */      
    public DataValue lookupValue(Token variable) {
    	Scope found = this.findScopeinStack(variable);
    	return this.heapSegment.get(found.lookupInScope(variable));
    }

    /////////////////////////////////////////////////////////////////////////////
    
    /**
     * Returns the index of val in the heap, adding if not already there.
     * @param val the value being searched for
     * @return the index of that value in the heap
     */
    private int findValueInHeap(DataValue val) {
    	int addr = this.heapSegment.indexOf(val);
    	if (addr == -1) {
    		addr = this.heapSegment.size();
    		this.heapSegment.add(val);
    	}
    	return addr;
    }
    
    /**
     * Locates the Scope in the stackSegment that contains the specified variable.
     * @param variable the variable being searched for
     * @return the Scope containing that variable
     */
    private Scope findScopeinStack(Token variable) {
    	Scope stepper = this.stackSegment.peek();
    	while (stepper != null && stepper.lookupInScope(variable) == null) {
    		stepper = stepper.getParentScope();
    	}
    	return stepper;
    }
}

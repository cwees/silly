import java.util.Stack;
import java.util.ArrayList;

/**
 * Class that defines the memory space for the SILLY interpreter.
 * 
 * @author Dave Reed
 * @version 12/22/23
 */
public class MemorySpace {
    private Stack<Scope> stackSegment;
    private ArrayList<DataValue> heapSegment;
    private ArrayList<SubRoutine> subSegment;
    // third structure for storing subroutines
    // store code segmentthing somewhere

    // when you call subroutine, call foo on 1,2
    // go into memory, initailize a to be 1, b to 2

    // create new scope before calling it

    /**
     * Constructs an empty memory space.
     */
    public MemorySpace() {
        this.stackSegment = new Stack<Scope>();
        this.stackSegment.push(new Scope(null));
        this.heapSegment = new ArrayList<DataValue>();
        this.subSegment = new ArrayList<SubRoutine>();

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
     * add new tokenstream to subSegment
     * 
     * @param stream TokenStream to add
     */
    public void addSubroutine(Compound comp, String id, ArrayList<TokenStream> tokens) {
        this.subSegment.add(new SubRoutine(comp, id, tokens));
    }
    /**
     * 
     * @param id of subroutine
     * @return true if ID exists, false if does not exist
     */
    public boolean isSubroutine(String id) {
        for (SubRoutine sub : this.subSegment) {
            if (sub.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param string id to be found in subSegment
     * @throws Exception if id is not found
     */
    public SubRoutine getSubroutine(String id) throws Exception {
        for (SubRoutine sub : this.subSegment) {
            if (sub.getId().equals(id)) {
                return sub;
            }
        }
        throw new Exception("Error, subRoutine not found. Are you sure it is declared?");
    }

    /**
     * Declares a variable (without storing an actual value).
     * 
     * @param variable the variable to be declared
     */
    public void declareVariable(Token variable) {
        this.stackSegment.peek().storeInScope(variable, -1);
    }

    /**
     * Determines if a variable is already declared.
     * 
     * @param variable the variable to be found
     * @return true if it is declared and/or assigned
     */
    public boolean isDeclared(Token variable) {
        return (this.findScopeinStack(variable) != null);
    }

    /**
     * Stores a variable/value in the stack segment.
     * 
     * @param variable the variable name
     * @param val      the value to be stored under that name
     */
    public void storeValue(Token variable, DataValue val) {
        Scope found = this.findScopeinStack(variable);
        int addr = this.findValueInHeap(val);
        found.storeInScope(variable, addr);
    }

    /**
     * Determines the value associated with a variable in memory.
     * 
     * @param variable the variable to look up
     * @return the value associated with that variable
     */
    public DataValue lookupValue(Token variable) {
        Scope found = this.findScopeinStack(variable);
        return this.heapSegment.get(found.lookupInScope(variable));
    }

    /////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the index of val in the heap, adding if not already there.
     * 
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
     * 
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

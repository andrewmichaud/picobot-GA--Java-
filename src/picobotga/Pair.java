/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package picobotga;

import java.util.Objects;

/**
 *
 * @author Andrew
 */
// generic pair class.  Stores two objects and returns them if asked.
public class Pair<A extends Object, B extends Object> {   
    private final A object1;
    private final B object2;

     // creates a new object with the given state and surroundings.
    public Pair(A a, B b) {
        object1 = a;
        object2 = b;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<A, B> other = (Pair<A, B>) obj;
        if (!Objects.equals(this.object1, other.object1)) {
            return false;
        }
        if (!Objects.equals(this.object2, other.object2)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.object1);
        hash = 67 * hash + Objects.hashCode(this.object2);
        return hash;
    }
    
    // getters for the two fields.
    public A getFirst() {
        return object1;
    }
    
    public B getSecond() {
        return object2;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(object1);
        sb.append(" ");
        sb.append(object2);
        return sb.toString();
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package picobotga;

import java.util.*;

/**
 *
 * @author Andrew
 */
public class Program {
    // all possible surroundings for the picobot. sorted by number of walls, 
    // so we can quickly strip out unneeded combinations depending on the
    // room type.
    public static final String[] ALL_SURROUNDINGS = {"xxxx", 
        "Nxxx", "xExx", "xxWx", "xxxS", 
        "NExx", "NxWx", "NxxS", "xEWx", "xExS", "xxWS", 
        "NEWx", "NExS", "xEWS", "NxWS",
        "NEWS"};
    private final static Character[] DIRECTIONS = {'N', 'E', 'W', 'S', 'X'};
    private ArrayList<Character> directions = new ArrayList<>();
    private String ROOM;
    private int STATES;
    private HashMap<Pair, Pair> rules;
    private ArrayList<Pair> keys;
    private Random r;
    
    // Creates a new Program with empty rule list.
    public Program() {
        rules = new HashMap<>();
        keys = new ArrayList<>(STATES * ALL_SURROUNDINGS.length);
        directions = new ArrayList<>(Arrays.asList(DIRECTIONS));
        r = new Random();
        //ROOM = room;
        STATES = PicobotGA.STATES;
    }

    public HashMap<Pair, Pair> getRules() {
        return rules;
    }

    public void setRules(HashMap<Pair, Pair> rules) {
        this.rules = rules;
    }
    
    public Program(String code) {
        String[] codeArray = code.split("\n");
        rules = new HashMap<>();
        keys = new ArrayList<>(codeArray.length);
        directions = new ArrayList<>(Arrays.asList(DIRECTIONS));
        String[] lines = code.split("\n");
        int state;
        String surroundings;
        String newDirection;
        int newState;
        Pair key;
        Pair value;
        for (String line : lines) {
            String[] tokens = line.split(" ");
            state = Integer.parseInt(tokens[0]);
            surroundings = tokens[1];
            key = new Pair(state, surroundings);
            keys.add(key);
            newDirection = tokens[3];
            newState = Integer.parseInt(tokens[4]);
            value = new Pair(newDirection, newState);
            rules.put(key, value);
        }
        r = new Random();
        STATES = PicobotGA.STATES;
    }
    
    private ArrayList<String> getPossibleSurroundings(String ROOM) {
        ArrayList surroundings = new ArrayList<>();
            for (int e = 0; e < 11; e++) {
                surroundings.add(ALL_SURROUNDINGS[e]);
            }
            return surroundings;    
    }
    
    // randomizes the rule list of a Program.
    public void randomize() {              
        ArrayList<String> surroundings = getPossibleSurroundings("Empty");
        for (int s = 0; s < STATES; s++) {
            for (String sur : surroundings ) {
                Pair cur = new Pair(s, sur);
                keys.add(cur);
                for (int c = 0; c < sur.length(); c++) {
                    char curChar = sur.charAt(c);
                    if (curChar != 'x') {
                        directions.remove(new Character(curChar));
                    }
                }
                int randIndex = r.nextInt(directions.size()); 
                Character dir = directions.get(randIndex);
                int newState = r.nextInt(STATES);
                Pair next = new Pair<>(dir, newState);
                rules.put(cur, next);
            }
        }     
    }
    
    // gets the next move of a Program given the Picobot's current state
    public Pair getMove(Pair cur) {
        return rules.get(cur);
    }
    
    // mutates a rule at random (new next direction and state)
    public void mutate() {
        // choosing a random key from the key arrayList
        int next = r.nextInt(keys.size());
        Pair victim = keys.get(next);
        String oldSur = (String)victim.getSecond();
        for (int c = 0; c < oldSur.length(); c++) {
            char curChar = oldSur.charAt(c);
            if (curChar != 'x') {
                directions.remove(new Character(curChar));
            }
        }
        // creating a "mutated" new value (e.g. one that has a random new
        // state, new direction, both, or neither)
        int newState = r.nextInt(STATES);
        int randInt = r.nextInt(directions.size());
        Character newDirection = directions.get(randInt);
        Pair newValue = new Pair(newDirection, newState);
        rules.remove(victim);
        rules.put(victim, newValue);
    }
    
    // creates a new offspring program with some of the rules from both of
    // its parents.
    public Program crossover(Program mate, double mutProp) {
        // creating a new Program. two mating programs will (should) always
        // have the same number of states and the same room type, so we take
        // that information from this Program and set the offspring's fields
        // accordingly.
        Program offspring = new Program();
        offspring.STATES = STATES;
        offspring.ROOM = ROOM;
        int crossPoint = r.nextInt(keys.size());
        //System.out.println("thisMap is: " + this.rules);
       // System.out.println("mateMap is: " + mate.rules);
        for (int i = 0; i < keys.size(); i++) {
            Pair key;
            Pair value;
          //  System.out.println("i is: " + i);
           // System.out.println("crosspoint is: " + crossPoint);
            if (i <= crossPoint) {
               // System.out.println("less than or equal");
                key = keys.get(i);
                value = rules.get(key);
                offspring.rules.put(key, value);
                offspring.keys.add(key);
            }
            else if (i > crossPoint) {
              //  System.out.println("greater than");
                key = mate.keys.get(i);
                value = mate.rules.get(key);
                offspring.rules.put(key, value);
                offspring.keys.add(key);
            }
        }
        double rand = r.nextDouble();
        // if the random double chosen is less than the mutate rate, 
        // mutate the offspring before returning it.
        // TODO add back in mutate code.
        if (rand < PicobotGA.MUTATIONRATE) {
            offspring.mutate();
        }
       // System.out.println("offsMap is: " + offspring.rules);
        return offspring;
    }
    
    // returns the rules map of the Program, broken into standard Picobot
    // format.
    @Override
   public String toString() {
       StringBuilder sb = new StringBuilder("");
       // TODO clean
       Pair[] keyArray = rules.keySet().toArray(new Pair[rules.keySet().size()]);
       for (Pair key : keyArray) {
           Pair value = rules.get(key);
           sb.append(key);
           sb.append(" -> ");
           sb.append(value);
           sb.append(" \n");
       }
       return sb.toString();
   } 
}

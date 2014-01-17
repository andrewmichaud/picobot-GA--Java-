/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package picobotga;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class Picobot {
    // some constants, for convenience (used in the surroundings array)
    final static int NORTH = 0;
    final static int EAST = 1;
    final static int WEST = 2;
    final static int SOUTH = 3;
    
    final static int EMPTY_ROOM = 0;
    // fields a picobot object needs
    // position and state
    int row;
    int col;
    int state;
    // a two-dimensional array holding the maze
    char[][] maze;
    // rules for the picobot
    Program program;
    // monitoring visited squares
    ArrayList<Pair> visited;
    int numvisited;
    
    public Picobot(int r, int c, Program p) {
        // Initializing row, column, and program.
        row = r + 1;
        col = c + 1;
        program = p;
        maze = gridMaker();
        // initializing the maze

        // initializing the picobot at state zero with no visited squares
        state = 0;
        visited = new ArrayList<Pair>();
        numvisited = 0;       
    }
    
    // steps the picobot one step through its program.
    public void step() throws ArrayIndexOutOfBoundsException, ClassCastException {
        Pair cur = new Pair(row, col);
        if (!visited.contains(cur)) {
            visited.add(cur);
            numvisited++;
        }
        // building up the surroundings so the Picobot can find the applicable
        // rule.
        StringBuilder surroundings = new StringBuilder("");
        // identifying the neighbors, so the picobot can see its surroundings.
        char north = maze[row - 1][col];
        char east  = maze[row][col + 1];
        char west  = maze[row][col - 1];
        char south = maze[row + 1][col];
        char[] neighbors = {north, east, west, south};
        // looping over those neighbors to create a "surroundings" string that
        // will be used as part of a key in a moment.
        for (int n = 0; n < 4; n++) {
            if (neighbors[n] == 'W') {
                switch (n) {
                    case NORTH:
                        surroundings.append("N");
                        break;
                    case EAST:
                        surroundings.append("E");
                        break;
                    case WEST:
                        surroundings.append("W");
                        break;
                    case SOUTH:
                        surroundings.append("S");
                        break;
                }
            }
            else {
                surroundings.append("x");
                }
            
        }
        // getting the surroundings in string form
        String s = surroundings.toString();
        // getting the new state and direction based on the current state and
        // surroundings.
        Pair key = new Pair(state, s);
        Pair value;  
        
        char direction;
        try {
            value = program.getMove(key);
            direction = (Character)value.getFirst();
        } catch (Exception e) {
            throw e; 
        }
        state = (int)value.getSecond();
        // deciding where to move based on that direction.
        // because rows increase going downward and columns rightward, 
        // north and south do the opposite of what you'd expect (up is one
        // less row, down one more row).  No direction means nothing is done.
        try {
            switch(direction) {
                case 'N':
                    row -= 1;
                    break;
                case 'E':
                    col += 1;
                    break;
                case 'W':
                    col -= 1;
                    break;
                case 'S':
                    row += 1;
                    break;
                default:
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } 
    }
    
    // calls the step method as many times as are requested
    public void run(int steps) throws ArrayIndexOutOfBoundsException, 
            NullPointerException {
        for (int s = 0; s < steps; s++) {
            try {
                step();
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                throw e;
            }
        }
    }
    
    // clears the squares of the maze, the Picobot's array of visited squares, 
    // and the Picobot's number of visited squares so it can have a clean run.
    public void clear() {
        for (Pair pair : visited) {
            int r = (int)pair.getFirst();
            int c = (int)pair.getSecond();
            maze[r][c] = ' ';
        }
        visited.clear();
        numvisited = 0;
    }
    
    // chooses the visualization method for the best Picobot solving the empty
    // room
    public void visualize(String visualizationType) {
        if ("ASCII".equals(visualizationType)) {
            int steps = 500;
            asciiVisuals(steps);
        }
    }
    
    // ASCII visualization of the best Picobot. Uses System.out.println().
    // for debugging only unless I think of some clever way to show it in a 
    // gui
    public void asciiVisuals(int steps){
        // clearing Picobot for run.
        clear();
        // prints for given number of steps.
        for (int s = 0; s < steps; s++) {
            System.out.println(this);
            // The exception that is thrown from step() exists to find programs
            // that are so bad they try to move into walls. Since asciiVisuals
            // (or any of the other visuals methods) are only called on
            // the best Program (in theory), the exception should only make it 
            // here if something has gone terribly wrong.  In which case the 
            // error is ignored entirely and the program goes down in flames.
            try {
                step();
            } catch (ArrayIndexOutOfBoundsException e) {
                // ignore
            }
        }
    }
    // returns a two-dimensional array representing the maze to the Picobot
    // that requested it. 
    private char[][] gridMaker() {
        // The grid is a two-dimensional array of chars. 
        // The numbers specified by ROWS and COLUMNS indicate the number of
        // EMPTY rows and columns (the walls aren't counted). So, to
        // get the number of rows and columns INCLUDING the walls, we add two.
        char[][] grid = new char[PicobotGA.ROWS + 2][PicobotGA.COLS + 2];
        for (int r = 0; r < PicobotGA.ROWS + 2; r++) {
            // Looping over every row and column to fill in the grid.
            for (int c = 0; c < PicobotGA.COLS + 2; c++) {
                // The top and bottom rows are entirely walls.
                if (r == 0 || r == PicobotGA.ROWS + 1) {
                    grid[r][c] = 'W';
                }
                // Every other row has a wall at the first and last squares.
                else if (c == 0 || c == PicobotGA.COLS + 1) {
                    grid[r][c] = 'W';
                }
                // All other squares are empty until visited.
                else{
                    grid[r][c] = ' ';
                }
            }
        }
        return grid;
    }
    
    // displays the maze, with the Picobot's position marked with a 'P' and
    // visited squares marked by '.'
    @Override
    public String toString() {
        // the stringbuilder that's going to hold everything.
        StringBuilder room = new StringBuilder("");
        // marking visited squares.
        for (Pair pair : visited) {
            // TODO cleanup
            maze[(int)pair.getFirst()][(int)pair.getSecond()] = '.';
        }
        // marking the Picobot's square.
        maze[row][col] = 'P';
        for (int r = 0; r < PicobotGA.ROWS + 2; r++) {
            for (int c = 0; c < PicobotGA.COLS + 2; c++) {
                char symbol = maze[r][c];
                room.append(symbol).append(" ");
            }
            room.append("\n");
        }
        return room.toString();
    }
}

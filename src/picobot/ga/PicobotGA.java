/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package picobot.ga;

/**
 *
 * @author Andrew
 */
public class PicobotGA {

    /**
     * @param args the command line arguments
     */
    // These are settings that govern Picobot and Program objects
    private int ROWS;
    private int COLS;
    private int STATES;
    private String[] allowedPatterns;
    // These are the allowed patterns for various room types.  
    private String[] emptyPatterns = {"xxxx","Nxxx","NExx","NxWx","xxxS",
                                       "xExS","xxWS","xExx","xxWx"}; 
    
    // These settings are used when we evaluate fitness and create 
    // new generations.
    private int TRIALS;
    private int STEPS;
    private int MUTATIONRATE;
    private int TOPFRACTION;
    
    // This tells the program what type of maze we have.  The default type is
    // an empty room, because that is the easiest to generate for.  Other
    // types are available, but do not work nearly as well.
    private String ROOMTYPE;
    
    // Our main constructor. Sets the default values and creates our initial
    // population of Picobot programs.
    void PicobotGA()
    {
       this.ROWS = 23;
       this.COLS = 23;
       this.STATES = 5;
       this.TRIALS = 0;
       this.STEPS = 0;
       this.
       System.out.println("Grid size is " + this.ROWS + " rows by " + this.COLS
                          + " columns.");
       System.err.println("Fitness is measured using " + this.TRIALS + )
               
       
    }
    // We have a series of functions that can retrieve and set values for us.
    // They are used in the GUI so that the user can set values and so that 
    // the program can draw our Picobot for us.
    public int getRows()
    {
        int rows = this.ROWS;
        return rows;
    }
    
    public int getCols()
    {
        int cols = this.COLS;
        return cols;
    }
    
    
    public int getRows()
    {
        int rows = this.ROWS;
        return rows;
    }
    
    public int getRows()
    {
        int rows = this.ROWS;
        return rows;
    }
    public int getRows()
    {
        int rows = this.ROWS;
        return rows;
    }
    
    public int getRows()
    {
        int rows = this.ROWS;
        return rows;
    }
    public int getRows()
    {
        int rows = this.ROWS;
        return rows;
    }
    public int getRows()
    {
        int rows = this.ROWS;
        return rows;
    }
    
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
}

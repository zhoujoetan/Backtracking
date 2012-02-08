package backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * A simple path-finding puzzle implemented by backtracking
 * @author Zhou Tan
 */
public class Backtracking {
    private static final char[] move = {'L', 'R'};
    private static ArrayList<Integer> visited;
    
    /**
     * Takes an array of integers such as in the above examples, 
     * and tries to find a path from the first location in the array (puzzle[0]) 
     * the last location in the array (puzzle[puzzle.length - 1]). 
     * location in the array may be entered more than once. 
     * move to the left will be represented by the capital letter 'L'; 
     * move to the right will be represented by the capital letter 'R'.
     * The top value on the stack is the first move.
     * @param The array of puzzle numbers
     * @return The stack of all moves, null indicates that no solution is found
     */
    public static Stack<Character> solve(int[] puzzle) {
        init();
        Stack<Character> solution = new Stack<Character>(); 
        /* Try to find a path from leftmost node to the right most node */
        if (makeMoves(puzzle, 0, solution)) {
            return solution;
        }
        else {
            return null;
        }
    }
    
    /**
     * Input and solution requirements are the same as for solve,
     * but this method finds and returns all such solutions.
     * If there are no solutions, an empty set is returned.
     * @param The array of puzzle numbers
     * @return A set of stacks of all solution
     */
    public static Set<Stack<Character>> findAllSolutions(int[] puzzle) {
        init();
        Stack<Character> solution = new Stack<Character>();
        Set<Stack<Character>> solutionSet = new HashSet<Stack<Character>>();
        
        //Consider the case when puzzle has length 1
        if (puzzle.length == 1) {
            solutionSet.add(solution);
            return solutionSet;
        }
        //Mark the first index as visited
        visited.add(0);
        makeMoves(puzzle, 0, solution, solutionSet);
        return (solutionSet.size() == 0) ? null : solutionSet;
    }
    
    /**
     * Determine if a move is valid according its current
     * position and next move
     * @param Current puzzle numbers
     * @param Current position
     * @param Direction of current move
     * @return true if the move is valid, false if not
     */
    private static boolean isValid(int[] puzzle, int position, char direction) {
        int newPosition;
        
        /* Calculate new position according to current position and direction */
        if (direction == 'L') {
            newPosition = position - puzzle[position];
        }
        else {
            newPosition = position + puzzle[position];
        }
        
        /* New position cannot cross puzzle's boundary */
        if (newPosition < 0 || newPosition > puzzle.length - 1) {
            return false;
        }
        
        /* New position must not have been visited */
        for (int i = 0; i < visited.size(); i++) {
            if (newPosition == visited.get(i).intValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Recursively make moves to either left or right directions, return true once a solution is found
     * @param The array of puzzle integers
     * @param Current position of the gamer
     * @param Solution stack of the most recent moves.
     *        Elements could be removed if they are not part of the solution
     * @return return true if current move leads to a possible solution, false otherwise
     */
    private static boolean makeMoves(int[] puzzle, int position, Stack<Character> solution) {
        int nextPosition = 0;
        
        //Consider the case which puzzle has only one element
        if (position == puzzle.length - 1) {
            return true;
        }
        /* Start looping(making moves) from index 0 */
        for (int i = 0; i < 2; i++) {
            /* Check if current iteration is a valid move (not crossing the boundary and not previously visited) */
            if (isValid(puzzle, position, move[i])) {
                
                /* For a valid move, push the moving direction to stack */
                visited.add(new Integer(position));
                /* Determine the new position */
                if (move[i] == 'L') {
                    nextPosition = position - puzzle[position];
                }
                else {
                    nextPosition = position + puzzle[position];
                }
                
                /* Immediately return true if reaches the end */
                if (nextPosition == puzzle.length - 1) {
                    /* Push the current move and return true */
                    solution.push(new Character(move[i]));
                    return true;
                }
                
                /* Recursively calls makeMoves() for next moves for SOLVE */
                if (makeMoves(puzzle, nextPosition, solution)) {
                    solution.push(new Character(move[i]));
                    return true;
                }
            }
        }
        
        //Delete current position from visited array before backtracking
        if (position != 0) {
            visited.remove(visited.size() - 1);
        }
        return false;
    }
    
    /**
     * Recursively make moves to either left or right directions,
     * adding the solution to the solution set once it has been found.
     * @param The array of puzzle integers
     * @param Current position of the gamer
     * @param Solution stack of the most recent moves.
     *        Elements could be removed if they are not part of the solution
     * @param Solution set that keeps track of all solutions
     */
    private static void makeMoves(int[] puzzle, int position, Stack<Character> solution,
                                     Set<Stack<Character>> solutionSet) {
        int nextPosition = 0;
        
        /* Start looping(making moves) from index 0 */
        for (int i = 0; i < 2; i++) {
            /* Check if current iteration is a valid move (not crossing the boundary and not previously visited) */
            if (isValid(puzzle, position, move[i])) {
                /* Determine the new position */
                if (move[i] == 'L') {
                    nextPosition = position - puzzle[position];
                }
                else {
                    nextPosition = position + puzzle[position];
                }
                /* Add the stack to solution set once a solution is found */
                if (nextPosition == puzzle.length - 1) {
                    /* Copy to the solution set */
                    solution.push(new Character(move[i]));
                    solutionSet.add(revertStack(solution));
                    solution.pop();
                }
                
                /* For a valid move, push the moving direction to stack */
                visited.add(new Integer(nextPosition));
                solution.push(new Character(move[i]));
                /* Recursively calls makeMoves() for next moves for SOLVE_ALL */
                makeMoves(puzzle, nextPosition, solution, solutionSet);
            }
        }
        
        /* Clear the visiting history(except called by the first time) */
        if (!solution.isEmpty()) {
            solution.pop();
            visited.remove(visited.size() - 1);
        }
        return;
    }
    
    /**
     * Build a reverse stack up on the given stack
     * @param The stack that is copied from
     * @return The reverse stack
     */
    private static Stack<Character> revertStack(Stack<Character> stack) {
        Stack<Character> helperStack = new Stack<Character>();
        Stack<Character> returnStack = new Stack<Character>();
        
        //Iterate through the first stack and make a copy to the helper stack
        for (Character c : stack) {
            helperStack.push(c);
        }
        //Pop the helper function to the return stack to revert it
        while (!helperStack.isEmpty()) {
            returnStack.push(helperStack.pop());
        }
        return returnStack;
    }
    
    /**
     * The initialization method called every time
     * at beginning of solve() and findAllSolutions()
     */
    private static void init() {
        visited = new ArrayList<Integer>();
    }
}

package backtracking;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.Test;

public class BacktrackingTest {

    private boolean isSolved(int[] puzzle, Stack<Character> solution) {
        int position = 0;
        ArrayList<Integer> visited = new ArrayList<Integer>();
        char move = 0;
        while (!solution.isEmpty()) {
            visited.add(position);
            move = solution.pop().charValue();
            position = (move == 'L') ? (position - puzzle[position]) : (position + puzzle[position]);
            if (visited.contains(position)) {
                return false;
            }
        }
        return (position == puzzle.length - 1);
    }
    
    @Test
    public void testSolve() {
        int[] puzzle = {3, 6, 4, 1, 3, 4, 2, 5, 3, 0};
        int[] puzzle2 = {2, 3, 1, 2, 2};
        int[] puzzle3 = {1};
        int[] puzzleFail1 = {3, 1, 2, 3, 0};
        int[] puzzleFail2 = {1, 4, 5, 20, 0};
        
        assertFalse(Backtracking.solve(puzzle) == null);
        assertFalse(Backtracking.solve(puzzle2) == null);
        assertFalse(Backtracking.solve(puzzle3) == null);
        assertTrue(isSolved(puzzle, Backtracking.solve(puzzle)));
        assertTrue(isSolved(puzzle2, Backtracking.solve(puzzle2)));
        assertTrue(isSolved(puzzle3, Backtracking.solve(puzzle3)));
        assertEquals(Backtracking.solve(puzzleFail1), null);
        assertEquals(Backtracking.solve(puzzleFail2), null);
    }

    @Test
    public void testFindAllSolutions() {
        int[] puzzle = {3, 6, 4, 1, 3, 4, 2, 5, 3, 0};
        int[] puzzle2 = {2, 3, 1, 2, 2};
        int[] puzzle3 = {1};
        int[] puzzleFail1 = {3, 1, 2, 3, 0};
        int[] puzzleFail2 = {1, 4, 5, 20, 0};
        
        assertFalse(Backtracking.findAllSolutions(puzzle) == null);
        assertFalse(Backtracking.findAllSolutions(puzzle2) == null);
        assertFalse(Backtracking.findAllSolutions(puzzle3) == null);
        assertTrue(Backtracking.findAllSolutions(puzzleFail1) == null);
        assertTrue(Backtracking.findAllSolutions(puzzleFail2) == null);
        
        for (Stack<Character> solution : Backtracking.findAllSolutions(puzzle)) {
            assertTrue(isSolved(puzzle, solution));
        }
        for (Stack<Character> solution : Backtracking.findAllSolutions(puzzle2)) {
            assertTrue(isSolved(puzzle2, solution));
        }
        for (Stack<Character> solution : Backtracking.findAllSolutions(puzzle3)) {
            assertTrue(isSolved(puzzle3, solution));
        }
        assertEquals(Backtracking.findAllSolutions(puzzle).size(), 3);
        assertEquals(Backtracking.findAllSolutions(puzzle2).size(), 2);
        assertEquals(Backtracking.findAllSolutions(puzzle3).size(), 1);
    }

}

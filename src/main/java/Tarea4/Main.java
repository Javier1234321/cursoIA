package Tarea4;

public class Main {
    static void main() {
        int[][] maze=MazeGenerator.generateMaze(10,10);
        MazeGenerator.printMaze(maze,10,10);
        LeftHand.leftHandSolve(maze,new Position(9,9));
        TwoHands twoHands=new TwoHands();
        twoHands.solve(maze,new Position(9,9));
    }
}

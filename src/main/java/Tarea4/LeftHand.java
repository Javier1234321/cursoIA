package Tarea4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LeftHand {
    static final int WIDTH = 10;
    static final int HEIGHT = 10;
    static final int VISITED = 0x20;
    static final int WEST = 0;
    static final int NORTH = 1;
    static final int SOUTH = 2;
    static final int EAST = 3;

    int[][] maze = MazeGenerator.generateMaze(WIDTH, HEIGHT);

    public static void leftHandSolve(int[][] maze, Position goalPosition) {
        Deque<Position> positionStack = new ArrayDeque<>();
        positionStack.push(new Position(0, 0));
        maze[0][0] |= VISITED;
        int direction = EAST;

        while (!(goalPosition.x == positionStack.peek().x
                && goalPosition.y == positionStack.peek().y)) {

            Position current = positionStack.peek();
            int x = current.x;
            int y = current.y;

            List<Integer> neighbours = new ArrayList<>();

            if (direction == NORTH) {
                neighbours.add(WEST);
                neighbours.add(NORTH);
                neighbours.add(EAST);
                neighbours.add(SOUTH);
            } else if (direction == EAST) {
                neighbours.add(NORTH);
                neighbours.add(EAST);
                neighbours.add(SOUTH);
                neighbours.add(WEST);
            } else if (direction == SOUTH) {
                neighbours.add(EAST);
                neighbours.add(SOUTH);
                neighbours.add(WEST);
                neighbours.add(NORTH);
            } else if (direction == WEST) {
                neighbours.add(SOUTH);
                neighbours.add(WEST);
                neighbours.add(NORTH);
                neighbours.add(EAST);
            }

            boolean moved = false;

            for (int nextDirection : neighbours) {
                boolean hasPath = false;

                if (nextDirection == WEST) {
                    hasPath = (maze[y][x] & MazeGenerator.CELL_PATH_W) != 0;
                } else if (nextDirection == NORTH) {
                    hasPath = (maze[y][x] & MazeGenerator.CELL_PATH_N) != 0;
                } else if (nextDirection == SOUTH) {
                    hasPath = (maze[y][x] & MazeGenerator.CELL_PATH_S) != 0;
                } else if (nextDirection == EAST) {
                    hasPath = (maze[y][x] & MazeGenerator.CELL_PATH_E) != 0;
                }

                if (!hasPath) {
                    continue;
                }

                int newX = x;
                int newY = y;

                if (nextDirection == WEST) {
                    newX--;
                } else if (nextDirection == NORTH) {
                    newY--;
                } else if (nextDirection == SOUTH) {
                    newY++;
                } else if (nextDirection == EAST) {
                    newX++;
                }

                if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT) {
                    continue;
                }

                if ((maze[newY][newX] & VISITED) != 0) {
                    continue;
                }

                direction = nextDirection;
                maze[newY][newX] |= VISITED;
                positionStack.push(new Position(newX, newY));
                moved = true;
                break;
            }

            if (!moved) {
                positionStack.pop();

                if (positionStack.isEmpty()) {
                    System.out.println("No se encontró el objetivo.");
                    return;
                }
            }
        }

        boolean[][] solution = new boolean[HEIGHT][WIDTH];

        for (Position position : positionStack) {
            solution[position.y][position.x] = true;
        }

        printSolution(maze, solution, goalPosition);
    }

    private static void printSolution(int[][] maze, boolean[][] solution, Position goalPosition) {
        for (int x = 0; x < WIDTH; x++) {
            System.out.print("+---");
        }
        System.out.println("+");

        for (int y = 0; y < HEIGHT; y++) {
            StringBuilder middle = new StringBuilder();
            StringBuilder bottom = new StringBuilder();

            for (int x = 0; x < WIDTH; x++) {
                if ((maze[y][x] & MazeGenerator.CELL_PATH_W) != 0) {
                    middle.append(" ");
                } else {
                    middle.append("|");
                }

                if (x == 0 && y == 0) {
                    middle.append(" S ");
                } else if (x == goalPosition.x && y == goalPosition.y) {
                    middle.append(" G ");
                } else if (solution[y][x]) {
                    middle.append(" * ");
                } else {
                    middle.append("   ");
                }

                if ((maze[y][x] & MazeGenerator.CELL_PATH_S) != 0) {
                    bottom.append("+   ");
                } else {
                    bottom.append("+---");
                }
            }

            middle.append("|");
            bottom.append("+");

            System.out.println(middle);
            System.out.println(bottom);
        }
    }
}


package Tarea4;

import java.util.ArrayDeque;
import java.util.Deque;

public class TwoHands {

    static final int WIDTH = 10;
    static final int HEIGHT = 10;

    static final int LEFT_VISITED = 0x20;
    static final int RIGHT_VISITED = 0x40;

    static final int WEST = 0;
    static final int NORTH = 1;
    static final int SOUTH = 2;
    static final int EAST = 3;

    static String publicAnnouncement = "";

    public void solve(int[][] maze, Position goalPosition) {

        Deque<Position> leftStack = new ArrayDeque<>();
        Deque<Position> rightStack = new ArrayDeque<>();

        Position leftPosition = new Position(0, 0);
        Position rightPosition = new Position(0, 0);

        int leftDirection = EAST;
        int rightDirection = EAST;

        leftStack.push(new Position(0, 0));
        rightStack.push(new Position(0, 0));

        maze[0][0] |= LEFT_VISITED;
        maze[0][0] |= RIGHT_VISITED;

        boolean leftActive = true;
        boolean rightActive = true;

        while (leftActive || rightActive) {

            if (leftActive) {

                if (isGoal(leftPosition, goalPosition)) {
                    printSolution(maze, leftStack, goalPosition);
                    return;
                }

                int[] priorities = getLeftPriorities(leftDirection);
                boolean moved = false;

                for (int nextDirection : priorities) {

                    int newX = leftPosition.x;
                    int newY = leftPosition.y;

                    if (!canMove(maze, leftPosition, nextDirection)) {
                        continue;
                    }

                    if (nextDirection == WEST) newX--;
                    else if (nextDirection == NORTH) newY--;
                    else if (nextDirection == SOUTH) newY++;
                    else if (nextDirection == EAST) newX++;

                    if (newX < 0 || newX >= WIDTH ||
                            newY < 0 || newY >= HEIGHT) {
                        continue;
                    }

                    if ((maze[newY][newX] & LEFT_VISITED) != 0) {
                        continue;
                    }

                    leftDirection = nextDirection;
                    leftPosition = new Position(newX, newY);

                    leftStack.push(new Position(newX, newY));

                    maze[newY][newX] |= LEFT_VISITED;

                    moved = true;
                    break;
                }

                if (!moved) {

                    publicAnnouncement =
                            "La mano izquierda está bloqueada.";

                    if (isGoal(leftPosition, goalPosition)) {
                        printSolution(maze, leftStack, goalPosition);
                        return;
                    }

                    if (leftStack.size() > 1) {

                        leftStack.pop();
                        leftPosition = leftStack.peek();

                        leftDirection = getDirection(
                                leftPosition,
                                leftStack.peek()
                        );

                    } else {

                        leftActive = false;

                        if (rightActive) {

                            publicAnnouncement =
                                    "La mano izquierda está bloqueada. " +
                                            "Se anuncia públicamente su posición.";

                            leftPosition =
                                    new Position(
                                            rightPosition.x,
                                            rightPosition.y
                                    );

                            leftStack.clear();
                            leftStack.push(
                                    new Position(
                                            leftPosition.x,
                                            leftPosition.y
                                    )
                            );

                            leftDirection = rightDirection;

                            maze[leftPosition.y][leftPosition.x]
                                    |= LEFT_VISITED;

                            leftActive = true;
                        }
                    }
                }

                if (isGoal(leftPosition, goalPosition)) {
                    printSolution(maze, leftStack, goalPosition);
                    return;
                }
            }

            if (rightActive) {

                if (isGoal(rightPosition, goalPosition)) {
                    printSolution(maze, rightStack, goalPosition);
                    return;
                }

                int[] priorities = getRightPriorities(rightDirection);
                boolean moved = false;

                for (int nextDirection : priorities) {

                    int newX = rightPosition.x;
                    int newY = rightPosition.y;

                    if (!canMove(maze, rightPosition, nextDirection)) {
                        continue;
                    }

                    if (nextDirection == WEST) newX--;
                    else if (nextDirection == NORTH) newY--;
                    else if (nextDirection == SOUTH) newY++;
                    else if (nextDirection == EAST) newX++;

                    if (newX < 0 || newX >= WIDTH ||
                            newY < 0 || newY >= HEIGHT) {
                        continue;
                    }

                    if ((maze[newY][newX] & RIGHT_VISITED) != 0) {
                        continue;
                    }

                    rightDirection = nextDirection;
                    rightPosition = new Position(newX, newY);

                    rightStack.push(new Position(newX, newY));

                    maze[newY][newX] |= RIGHT_VISITED;

                    moved = true;
                    break;
                }

                if (!moved) {

                    publicAnnouncement =
                            "La mano derecha está bloqueada.";

                    if (isGoal(rightPosition, goalPosition)) {
                        printSolution(maze, rightStack, goalPosition);
                        return;
                    }

                    if (rightStack.size() > 1) {

                        rightStack.pop();
                        rightPosition = rightStack.peek();

                        rightDirection = getDirection(
                                rightPosition,
                                rightStack.peek()
                        );

                    } else {

                        rightActive = false;

                        if (leftActive) {

                            publicAnnouncement =
                                    "La mano derecha está bloqueada. " +
                                            "Se anuncia públicamente su posición.";

                            rightPosition =
                                    new Position(
                                            leftPosition.x,
                                            leftPosition.y
                                    );

                            rightStack.clear();
                            rightStack.push(
                                    new Position(
                                            rightPosition.x,
                                            rightPosition.y
                                    )
                            );

                            rightDirection = leftDirection;

                            maze[rightPosition.y][rightPosition.x]
                                    |= RIGHT_VISITED;

                            rightActive = true;
                        }
                    }
                }

                if (isGoal(rightPosition, goalPosition)) {
                    printSolution(maze, rightStack, goalPosition);
                    return;
                }
            }
        }

        System.out.println("Ninguna mano encontró el objetivo.");
    }

    private boolean canMove(
            int[][] maze,
            Position position,
            int direction) {

        if (direction == WEST) {
            return (maze[position.y][position.x]
                    & MazeGenerator.CELL_PATH_W) != 0;
        }

        if (direction == NORTH) {
            return (maze[position.y][position.x]
                    & MazeGenerator.CELL_PATH_N) != 0;
        }

        if (direction == SOUTH) {
            return (maze[position.y][position.x]
                    & MazeGenerator.CELL_PATH_S) != 0;
        }

        return (maze[position.y][position.x]
                & MazeGenerator.CELL_PATH_E) != 0;
    }

    private int[] getLeftPriorities(int direction) {

        if (direction == NORTH) {
            return new int[]{
                    WEST, NORTH, EAST, SOUTH
            };
        }

        if (direction == EAST) {
            return new int[]{
                    NORTH, EAST, SOUTH, WEST
            };
        }

        if (direction == SOUTH) {
            return new int[]{
                    EAST, SOUTH, WEST, NORTH
            };
        }

        return new int[]{
                SOUTH, WEST, NORTH, EAST
        };
    }

    private int[] getRightPriorities(int direction) {

        if (direction == NORTH) {
            return new int[]{
                    EAST, NORTH, WEST, SOUTH
            };
        }

        if (direction == EAST) {
            return new int[]{
                    SOUTH, EAST, NORTH, WEST
            };
        }

        if (direction == SOUTH) {
            return new int[]{
                    WEST, SOUTH, EAST, NORTH
            };
        }

        return new int[]{
                NORTH, WEST, SOUTH, EAST
        };
    }

    private int getDirection(
            Position from,
            Position to) {

        if (to == null) {
            return EAST;
        }
        if (to.x > from.x) {
            return EAST;
        }
        if (to.x < from.x) {
            return WEST;
        }
        if (to.y > from.y) {
            return SOUTH;
        }
        return NORTH;
    }

    private boolean isGoal(
            Position position,
            Position goalPosition) {
        return position.x == goalPosition.x
                && position.y == goalPosition.y;
    }

    private void printSolution(
            int[][] maze,
            Deque<Position> path,
            Position goalPosition) {

        boolean[][] solution =
                new boolean[HEIGHT][WIDTH];

        for (Position position : path) {
            solution[position.y][position.x] = true;
        }

        for (int x = 0; x < WIDTH; x++) {
            System.out.print("+---");
        }

        System.out.println("+");

        for (int y = 0; y < HEIGHT; y++) {

            StringBuilder middle =
                    new StringBuilder();

            StringBuilder bottom =
                    new StringBuilder();

            for (int x = 0; x < WIDTH; x++) {

                if ((maze[y][x]
                        & MazeGenerator.CELL_PATH_W) != 0) {
                    middle.append(" ");
                } else {
                    middle.append("|");
                }

                if (x == 0 && y == 0) {
                    middle.append(" S ");
                } else if (x == goalPosition.x
                        && y == goalPosition.y) {
                    middle.append(" G ");
                } else if (solution[y][x]) {
                    middle.append(" * ");
                } else {
                    middle.append("   ");
                }

                if ((maze[y][x]
                        & MazeGenerator.CELL_PATH_S) != 0) {
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
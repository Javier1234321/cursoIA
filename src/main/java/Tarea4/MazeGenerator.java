package Tarea4;

import java.util.*;

public class MazeGenerator {
    static final int CELL_PATH_N = 0x01;
    static final int CELL_PATH_E = 0x02;
    static final int CELL_PATH_S = 0x04;
    static final int CELL_PATH_W = 0x08;
    static final int CELL_VISITED = 0x10;
    public int WIDTH = 20;
    public final int HEIGHT = 10;
    static Random random = new Random();

    static int[][] generateMaze(int WIDTH,int HEIGHT) {
        int[][] maze = new int[HEIGHT][WIDTH];
        Deque<Position> stack = new ArrayDeque<>();
        int startX = random.nextInt(WIDTH);
        int startY = random.nextInt(HEIGHT);
        stack.push(new Position(startX, startY));
        maze[startY][startX] |= CELL_VISITED;
        int visitedCells = 1;
        while (visitedCells < WIDTH * HEIGHT) {
            Position current = stack.peek();
            int x = current.x;
            int y = current.y;
            List<Integer> neighbours = new ArrayList<>();
            if (y > 0 && (maze[y - 1][x] & CELL_VISITED) == 0) {
                neighbours.add(0);
            }
            if (x < WIDTH - 1 && (maze[y][x + 1] & CELL_VISITED) == 0) {
                neighbours.add(1);
            }
            if (y < HEIGHT - 1 && (maze[y + 1][x] & CELL_VISITED) == 0) {
                neighbours.add(2);
            }
            if (x > 0 && (maze[y][x - 1] & CELL_VISITED) == 0) {
                neighbours.add(3);
            }
            if (!neighbours.isEmpty()) {
                int direction =
                        neighbours.get(random.nextInt(neighbours.size()));
                switch (direction) {
                    case 0 -> {
                        maze[y - 1][x] |=
                                CELL_VISITED | CELL_PATH_S;
                        maze[y][x] |= CELL_PATH_N;
                        stack.push(new Position(x, y - 1));
                    }
                    case 1 -> {
                        maze[y][x + 1] |=
                                CELL_VISITED | CELL_PATH_W;
                        maze[y][x] |= CELL_PATH_E;
                        stack.push(new Position(x + 1, y));
                    }
                    case 2 -> {
                        maze[y + 1][x] |=
                                CELL_VISITED | CELL_PATH_N;
                        maze[y][x] |= CELL_PATH_S;
                        stack.push(new Position(x, y + 1));
                    }
                    case 3 -> {
                        maze[y][x - 1] |=
                                CELL_VISITED | CELL_PATH_E;
                        maze[y][x] |= CELL_PATH_W;
                        stack.push(new Position(x - 1, y));
                    }
                }
                visitedCells++;
            } else {
                stack.pop();
            }
        }
        return maze;
    }

    static void printMaze(int[][] maze,int WIDTH,int HEIGHT) {
        for (int x = 0; x < WIDTH; x++) {
            System.out.print("+---");
        }
        System.out.println("+");
        for (int y = 0; y < HEIGHT; y++) {
            StringBuilder middle = new StringBuilder();
            StringBuilder bottom = new StringBuilder();
            for (int x = 0; x < WIDTH; x++) {
                if ((maze[y][x] & CELL_PATH_W) != 0) {
                    middle.append(" ");
                } else {
                    middle.append("|");
                }
                middle.append("   ");
                if ((maze[y][x] & CELL_PATH_S) != 0) {
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

package complete;

import java.util.Scanner;

public class Main {
    public static String[][] maze;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter only odd numbers");
        System.out.print("Row count: ");
        int rows = sc.nextInt();
        System.out.print("Column count: ");
        int columns = sc.nextInt();

        System.out.print("Would you like to fill maze automatically (y/n)? ");
        String ans = sc.next();

        switch (ans) {
            case "y":
                System.out.print("Maze options:\n1. DFS;\n2. Prim;\n(1-2)? ");
                int opt = sc.nextInt();

                switch (opt) {
                    case 1:
                        MazeGenDFS mgs = new MazeGenDFS(rows, columns);
                        maze = mgs.generateMaze();
                        break;
                    case 2:
                        MazeGenPrim mgp = new MazeGenPrim(rows, columns);
                        maze = mgp.generateMaze();
                        break;
                }
                break;
            case "n":
                System.out.println("\n| X for wall | \n| SPACE for empty cell | \n");
                System.out.println("There is an example: ");
                MazeGenPrim mgp = new MazeGenPrim(rows, columns);
                mgp.generateMaze();

                System.out.println("Enter your maze: ");
                maze = new String[rows][columns];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        maze[i][j] = sc.next();
                    }
                }
                break;
        }

        System.out.print("How to solve maze:\n1. Width search;\n2. Depth search;\n3. Death end method;\n(1-3)? ");
        int opt = sc.nextInt();
        sc.close();
        System.out.println("\nResult: \n");

        switch (opt) {
            case 1:
                SolveWidthSearch sws = new SolveWidthSearch(maze);
                sws.solve();
                break;
            case 2:
                SolveDepthSearch sds = new SolveDepthSearch(maze);
                sds.solve();
                break;
            case 3:
                SolveDeathEnd sde = new SolveDeathEnd(maze);
                sde.solve();
                break;
        }
    }
}

import java.util.Random;

public class GameOfLife {
    public boolean[][] grid;


    public static void main(String[] args) {
        GameOfLife grid1 = new GameOfLife(100);
        StdDraw.enableDoubleBuffering();
        while(true){
            drawGrid(grid1.getGrid());
            StdDraw.pause(200);
            grid1.nextCycle();
        }
    }

    //Initializing the field with random boolean values for each cell (True = alive; False = dead)
    public GameOfLife(int size){
        grid = new boolean[size][size];
        for(int i = 0; i<grid.length; i++){
            for(int g = 0; g<grid[i].length; g++){
                Random r = new Random();
                grid[i][g] = r.nextBoolean();
            }
        }
    }

    public boolean[][] getGrid() {
        return grid;
    }

    //Determine the state of each cell for the next cycle
    public boolean nextState(int x, int y){
        int neighbours = 0;
        int cCol = x-1;
        int cRow = y-1;
        for(int c = 0; c<=2; c++){
            if(cCol+c < 0 || cCol+c >= grid.length) continue;
            for(int r = 0; r<=2; r++){
                if(cRow+r < 0 || cRow+r >= grid.length) continue;
                if(cCol+c == x && cRow+r == y) continue;
                if(grid[cCol+c][cRow+r]){
                    neighbours++;
                }
            }
        }

        //Cell survives when it has 2 or 3 neighbours
        //Dead cell gets revived when it has exactly 3 neighbours
        //All other cells die/stay dead
        boolean alive = grid[x][y];
        if(alive && (neighbours == 2 || neighbours == 3)) {
            return true;
        }else return !alive && (neighbours == 3);
    }

    public void nextCycle(){
        //write new values to temporary 2D array
        boolean[][] temp = new boolean[grid.length][grid.length];
        for(int c = 0; c < grid.length; c++){
            for(int r = 0; r < grid.length; r++){
                temp[c][r] = nextState(c, r);
            }
        }
        //replacing the old generation with the contents of the temporary array
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[r].length; c++){
                grid[r][c] = temp[r][c];
            }
        }
    }

    public static void drawGrid(boolean[][] f){
        int size = f.length;
        StdDraw.clear();
        StdDraw.setXscale(0,size);
        StdDraw.setYscale(0,size);
        for(int c = 0; c < size; c++){
            for(int r = 0; r < size; r++){
                if(f[c][r]){
                    StdDraw.filledSquare(r+0.5, c+0.5, 0.47);
                }
            }
        }
        StdDraw.show();
    }

}
import java.util.Arrays;

class GameofLife {
    int xDim = 50;
    int yDim = 50;
    boolean[][] cellMatrix = new boolean[xDim][yDim];
    boolean[][] oldCellMatrix = new boolean[xDim][yDim];
    int xPx = xDim * 10;
    int yPx = yDim * 10;
    java.awt.Color deadColor = StdDraw.WHITE;
    java.awt.Color aliveColor = StdDraw.GREEN;
    boolean playing = false;
    int clickDelay = 200;

    GameofLife() {
        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.setXscale(0, xPx);
        StdDraw.setYscale(0, yPx);
        StdDraw.enableDoubleBuffering();
        // this.makeTestMatix();
        this.play();
    }

    void play() {
        this.drawGrid();
        while (true) {
            if (StdDraw.isMousePressed()) {
                changeClickedCell(StdDraw.mouseX(), StdDraw.mouseY());
                try {
                    Thread.sleep(clickDelay);
                } catch (InterruptedException ex) {
                }
            }
            while (StdDraw.hasNextKeyTyped() && StdDraw.nextKeyTyped() == ' ') {
                this.draw();
            }
        }
    }

    void changeClickedCell(Double mouseX, Double mouseY) {
        int x = mouseX.intValue() / 10;
        int y = mouseY.intValue() / 10;
        cellMatrix[x][y] = !cellMatrix[x][y];
        this.drawAllCells();
    }

    void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.002);
        this.doAllCellLogic();
        this.drawAllCells();
    }

    void drawGrid() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.002);
        for (int x = 0; x <= xDim; x++) {
            for (int y = 0; y < yDim; y++) {
                StdDraw.rectangle(x * 10 + 5, y * 10 + 5, 5, 5);
            }
        }
        StdDraw.show();
    }

    void drawCell(int x, int y, boolean state) {
        // System.out.print(state);
        if (state) {
            StdDraw.setPenColor(aliveColor);
        } else {
            StdDraw.setPenColor(deadColor);
        }
        StdDraw.filledRectangle(x * 10 + 5, y * 10 + 5, 4.5, 4.5);
        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.text(x * xDim + xDim / 2, y * yDim + yDim / 2,
        // Integer.toString(getSurroundingCellsSum(x, y)));
    }

    void drawAllCells() {
        for (int x = 0; x < xDim; x++) {
            for (int y = 0; y < yDim; y++) {
                drawCell(x, y, cellMatrix[x][y]);
            }
        }
        StdDraw.show();
    }

    boolean[][] copyBooleanMatrix(boolean[][] matrix) {
        boolean[][] ret = new boolean[xDim][yDim];
        for (int i = 0; i < xDim; i++) {
            for (int j = 0; j < yDim; j++) {
                ret[i][j] = matrix[i][j];
            }
        }
        return ret;
    }

    void doAllCellLogic() {
        oldCellMatrix = copyBooleanMatrix(cellMatrix);
        // printBooleanMatrix(oldCellMatrix);
        for (int x = 0; x < xDim; x++) {
            for (int y = 0; y < yDim; y++) {
                doCellLogic(x, y);
            }
        }
    }

    void printBooleanMatrix(boolean[][] matrix) {
        String print = "";
        for (boolean[] array : matrix) {
            for (boolean bool : array) {
                if (bool) {
                    print += "1";
                } else {
                    print += "0";
                }
            }
            print += "\n";
        }
        System.out.print(print);
    }

    void doCellLogic(int x, int y) {
        int surroundingCells = getSurroundingCellsSum(x, y);
        if (oldCellMatrix[x][y] == true && surroundingCells < 2) {
            cellMatrix[x][y] = false;
        } else if (oldCellMatrix[x][y] == true && (surroundingCells == 2 || surroundingCells == 3)) {
            cellMatrix[x][y] = true;
        } else if (oldCellMatrix[x][y] == true && surroundingCells > 3) {
            cellMatrix[x][y] = false;
        } else if (oldCellMatrix[x][y] == false && surroundingCells == 3) {
            cellMatrix[x][y] = true;
        }
    }

    int getSurroundingCellsSum(int x, int y) {
        int sum = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    try {
                        if (oldCellMatrix[x + i][y + j] == true) {
                            sum++;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return sum;
    }

    void makeTestMatix() {
        // for (int i = 0; i < xDim; i++) {
        // for (int j = 0; j < yDim; j++) {
        // cellMatrix[i][j] = Math.random() < 0.5;
        // }
        // // }
        cellMatrix[4][4] = true;
        cellMatrix[5][4] = true;
        cellMatrix[6][4] = true;
        oldCellMatrix = cellMatrix;
    }
}
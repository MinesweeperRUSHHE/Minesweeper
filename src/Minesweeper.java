public class Minesweeper {
    public static void main(String[] args) {

        MinesweeperWindows mw = new MinesweeperWindows();

        mw.executeDifficultChoice();
        boolean[][] test = {{true, true, false}, {false, false, true}, {false, true, false}};
        mw.executeMinesweeper(test, 4);
//        mw.executeMinesweeper(new boolean[mw.getHeight()][mw.getWidth()], mw.getMines());
    }
}

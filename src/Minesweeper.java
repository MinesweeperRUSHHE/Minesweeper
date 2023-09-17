public class Minesweeper {
    public static void main(String[] args) {

        MinesweeperWindows mw = new MinesweeperWindows();

        mw.executeDifficultChoice();
        mw.executeMinesweeper(new boolean[mw.getHeight()][mw.getWidth()], mw.getMines());
    }
}

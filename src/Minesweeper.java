public class Minesweeper {
    private static MinesweeperWindows mw;
    public static void main(String[] args) {
        mw = new MinesweeperWindows();
        mw.executeDifficultChoice();
        mw.executeMinesweeper(new boolean[mw.getHeight()][mw.getWidth()], mw.getMines());
    }

    public static MinesweeperWindows getMinesweeperWindows() {
        return mw;
    }
}

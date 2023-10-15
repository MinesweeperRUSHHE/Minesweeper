import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MinesweeperWindows extends JFrame {
    public static int difficulty;
    //定义三种难度的历史记录1为低级，3为高级
    static MinesweeperButton[][] minesweeperButton;
    static int columns;
    static int rows;
    static int minesNumber; // 此为设置的地雷数
    boolean[][] mines;

    public MinesweeperWindows(int rows, int columns, int difficulty, int minesNumber) {
        MinesweeperWindows.rows = rows;
        MinesweeperWindows.columns = columns;
        MinesweeperWindows.difficulty = difficulty;
        MinesweeperWindows.minesNumber = minesNumber;

        mines = new boolean[rows][columns];
        setJMenuBar(new MinesweeperMenuBar());
        setLayout(new BorderLayout()); // 设置边框布局管理器

        MinesweeperStatusPanel statusPanel = new MinesweeperStatusPanel(); // 显示状态的面板
        JPanel minesPanel = new JPanel(); // 放置地雷的面板

        minesPanel.setLayout(new GridLayout(rows, columns, 0, 0)); // 使用网格布局管理器管理地雷按钮

        placeMines(mines, minesNumber);//在此处使用随机布雷方法
        minesweeperButton = new MinesweeperButton[rows][columns];

        //将地雷按钮添加到一个网格panel里
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                minesweeperButton[i][j] = new MinesweeperButton(i, j, mines);
                minesPanel.add(minesweeperButton[i][j]);
            }
        }

        add(statusPanel, BorderLayout.NORTH); // 将状态面板添加到上部区域
        add(minesPanel, BorderLayout.CENTER); // 将地雷面板添加到中心区域

        pack(); // 设置自动窗口大小
        setLocationRelativeTo(null); // 设置窗口居中
        setVisible(true); // 设置窗口可见
        setResizable(false); // 设置窗口不能调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
    }

    //引爆所有地雷的方法
    public static void detonateAllMines() {
        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
        MinesweeperStatusPanel.faceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_cross-out eyes.png"));
        for (int i = 0; i < minesweeperButton.length; i++) {
            for (int j = 0; j < minesweeperButton[0].length; j++) {
                minesweeperButton[i][j].setLeftClickable(false);
                minesweeperButton[i][j].setRightClickable(false);
                if (minesweeperButton[i][j].getStatus() == -1) {
                    minesweeperButton[i][j].setMinesVisible(true);
                    minesweeperButton[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button_Mine.png"));
                }
            }
        }
    }

    public static void openAllCell(int xLocation, int yLocation) {
        minesweeperButton[yLocation][xLocation].setMinesVisible(true);
        minesweeperButton[yLocation][xLocation].setButtonIcon();
        //防止在空格子上插旗子
        if (minesweeperButton[yLocation][xLocation].getStatus() == 0 && minesweeperButton[yLocation][xLocation].isBelongToFlag()) {
            minesweeperButton[yLocation][xLocation].setBelongToFlag(false);
        }

        //显示附近没有地雷的九宫格的带数字的格子
        for (int i = Math.max(0, yLocation - 1); i <= Math.min(minesweeperButton.length - 1, yLocation + 1); i++) {
            for (int j = Math.max(0, xLocation - 1); j <= Math.min(minesweeperButton[0].length - 1, xLocation + 1); j++) {
                if (minesweeperButton[i][j].getStatus() == 0 && !minesweeperButton[i][j].isMinesVisible() && !(i == yLocation && j == xLocation)) {
                    openAllCell(j, i);
                } else if (minesweeperButton[i][j].getStatus() > 0) {
                    minesweeperButton[i][j].setButtonIcon();
                    minesweeperButton[i][j].setMinesVisible(true);
                }
            }
        }
    }

    public static void successOrNot() throws IOException {
        int number = minesweeperButton.length * minesweeperButton[0].length;
        for (int i = 0; i < minesweeperButton.length; i++) {
            for (int j = 0; j < minesweeperButton[0].length; j++) {
                if (!(minesweeperButton[i][j].getStatus() == -1) && minesweeperButton[i][j].isMinesVisible()) {//遍历格子，如果格子打开不为炸弹，并且已经可见
                    number -= 1;
                }
            }
        }
        if (number == minesNumber) {//判断雷数和剩余格子数是否相等
            for (int i = 0; i < minesweeperButton.length; i++) {
                for (int j = 0; j < minesweeperButton[0].length; j++) {
                    minesweeperButton[i][j].setButtonIcon();
                    if (minesweeperButton[i][j].getStatus() == -1) {
                        minesweeperButton[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button_flag.png"));
                        minesweeperButton[i][j].setLeftClickable(false);
                        minesweeperButton[i][j].setRightClickable(false);
                    }
                }
            }
            MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
            if (difficulty != 3) {
                try {
                    MinesweeperMenuBar.MinesweeperMenuGames.BestTimes.writeBestTime();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        MinesweeperStatusPanel.RemainingMinesPanel.setMinesNumber();
        MinesweeperStatusPanel.remainingMinesPanel.setLabelIcon();
    }

    public static void executeDoubleClick(int xLocation, int yLocation) {
        ArrayList<MinesweeperButton> flagButton = new ArrayList<>();
        ArrayList<MinesweeperButton> button = new ArrayList<>();

        for (int i = Math.max(0, yLocation - 1); i <= Math.min(minesweeperButton.length - 1, yLocation + 1); i++) {
            for (int j = Math.max(0, xLocation - 1); j <= Math.min(minesweeperButton[0].length - 1, xLocation + 1); j++) {
                if (!minesweeperButton[i][j].isMinesVisible()) {
                    if (minesweeperButton[i][j].isBelongToFlag()) {
                        flagButton.add(minesweeperButton[i][j]);
                    } else {
                        button.add(minesweeperButton[i][j]);
                    }
                }
            }
        }
        if (minesweeperButton[yLocation][xLocation].getStatus() > 0 && minesweeperButton[yLocation][xLocation].getStatus() <= flagButton.size()) {
            for (MinesweeperButton btn : button) {
                btn.clickButton();
            }
        }
    }

    private void placeMines(boolean[][] mines, int numbers) {
        Random random = new Random();
        while (numbers > 0) {
            //随机产生雷所在行、所在列
            int rows = random.nextInt(mines.length);
            int columns = random.nextInt(mines[0].length);
            //判断当前雷是否有重复，没有就安雷
            if (!mines[rows][columns]) {
                mines[rows][columns] = true;
                numbers -= 1;
            }
        }
    }
}
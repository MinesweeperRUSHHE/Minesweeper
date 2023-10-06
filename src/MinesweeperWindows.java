import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class MinesweeperWindows {
    static JFrame minesweeper;
    //定义三种难度的历史记录1为低级，3为高级
    private int difficulty;
    private MinesweeperButton[][] minesweeperButton;
    private MinesweeperMenuBar minesweeperMenuBar;
    private MinesweeperStatusPanel statusPanel;
    private int height;
    private int width;
    private int mines; // 此为设置的地雷数

    public int getDifficulty() {
        return difficulty;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public MinesweeperButton[][] getMinesweeperButton() {
        return minesweeperButton;
    }
    public MinesweeperStatusPanel getStatusPanel() {
        return statusPanel;
    }

    //引爆所有地雷的方法
    public void detonateAllMines(int xLocation, int yLocation) {
        MinesweeperStatusPanel.faceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_cross-out eyes.png"));
        for (int i = 0; i < minesweeperButton.length; i++) {
            for (int j = 0; j < minesweeperButton[0].length; j++) {
                minesweeperButton[i][j].setLeftClickable(false);
                minesweeperButton[i][j].setRightClickable(false);
                if (minesweeperButton[i][j].getStatus() == -1 && !(i == yLocation && j == xLocation)) {
                    minesweeperButton[i][j].setMinesVisible(true);
                    minesweeperButton[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button_Mine.png"));
                }
            }
        }
    }

    public void openAllCell(int xLocation, int yLocation) {
        minesweeperButton[yLocation][xLocation].setButtonIcon();

        if (yLocation - 1 >= 0 && minesweeperButton[yLocation - 1][xLocation].getStatus() == 0 && !minesweeperButton[yLocation - 1][xLocation].isMinesVisible()) {
            openAllCell(xLocation, yLocation - 1);
        }
        if (yLocation + 1 < minesweeperButton.length && minesweeperButton[yLocation + 1][xLocation].getStatus() == 0 && !minesweeperButton[yLocation + 1][xLocation].isMinesVisible()) {
            openAllCell(xLocation, yLocation + 1);
        }
        if (xLocation - 1 >= 0 && minesweeperButton[yLocation][xLocation - 1].getStatus() == 0 && !minesweeperButton[yLocation][xLocation - 1].isMinesVisible()) {
            openAllCell(xLocation - 1, yLocation);
        }
        if (xLocation + 1 < minesweeperButton[0].length && minesweeperButton[yLocation][xLocation + 1].getStatus() == 0 && !minesweeperButton[yLocation][xLocation + 1].isMinesVisible()) {
            openAllCell(xLocation + 1, yLocation);
        }
        //显示附近没有地雷的九宫格的带数字的格子
        for (int i = Math.max(0, yLocation - 1); i <= Math.min(minesweeperButton.length - 1, yLocation + 1); i++) {
            for (int j = Math.max(0, xLocation - 1); j <= Math.min(minesweeperButton[0].length - 1, xLocation + 1); j++) {
                if (minesweeperButton[i][j].getStatus() > 0) {
                    minesweeperButton[i][j].setButtonIcon();
                }
            }
        }
    }

    public void successOrNot() {
        int number = minesweeperButton.length * minesweeperButton[0].length;
        for (int i = 0; i < minesweeperButton.length; i++) {
            for (int j = 0; j < minesweeperButton[0].length; j++) {
                if (!(minesweeperButton[i][j].getStatus() == -1) && minesweeperButton[i][j].isMinesVisible()) {//遍历格子，如果格子打开不为炸弹，并且已经可见
                    number -= 1;
                }
            }
        }
        if (number == mines) {//判断雷数和剩余格子数是否相等
            UIManager.put("OptionPane.buttonFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
            UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
            MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
            try {
                minesweeperMenuBar.recordBestTime();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public void executeDifficultChoice() {
        /*
        choice [0,1,2,3]分别对应"低级", "中级", "高级", "自定义"
        初级为10个，中级为40个，高级为99个
         */
        switch (new DifficultyChoice().getDifficulty()) {
            case 0 -> {
                difficulty = 0;
                height = 9;
                width = 9;
                mines = 10;
            }
            case 1 -> {
                difficulty = 1;
                height = 16;
                width = 16;
                mines = 40;
            }
            case 2 -> {
                difficulty = 2;
                height = 16;
                width = 30;
                mines = 99;
            }
            case 3 -> {
                difficulty = 3;
                CustomDifficulty customDifficulty = new CustomDifficulty();
                height = customDifficulty.getHeight();
                width = customDifficulty.getWidth();
                mines = customDifficulty.getMines();
            }
            default -> System.exit(0);
        }
    }

    public void executeMinesweeper(boolean[][] mines, int numbers) {
        placeMines(mines, numbers);//在此处使用随机布雷方法
        new MainWindows(mines).executeMinesweeper();
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

    static class CustomDifficulty {
        private final int height;
        private final int width;
        private final int mines;

        public CustomDifficulty() {
            //调用showInputDialog方法获取用户输入的数据
            String High = JOptionPane.showInputDialog(null, "请输入高度");
            while (checkCustomNumbers(High)) {
                High = reEnterNumbers();
            }//调用Integer方法把数据转换为整数
            height = Integer.parseInt(High);

            String Wide = JOptionPane.showInputDialog(null, "请输入宽度");
            while (checkCustomNumbers(Wide)) {
                Wide = reEnterNumbers();
            }
            width = Integer.parseInt(Wide);

            String Mine = JOptionPane.showInputDialog(null, "请输入雷数");
            while (checkCustomNumbers(Mine)) {
                Mine = reEnterNumbers();
            }
            mines = Integer.parseInt(Mine);
        }

        private boolean checkCustomNumbers(String number) {
            //检查用户操作和判断输入数据
            if (number == null) {
                //用户关闭或取消了对话框
                JOptionPane.showMessageDialog(null, "你取消了自定义");
                //TODO:重新回到难度选择
                System.exit(0);
                return false;
            } else {
                try {
                    //判断输入的是否为整数
                    Integer.parseInt(number);
                    //判断整数的大小是否符合要求
                    return 8 > Integer.parseInt(number) || Integer.parseInt(number) > 30;
                } catch (Exception e) {
                    //不是整数
                    return true;
                }
            }
        }

        private String reEnterNumbers() {
            //如果输入的不是符合要求的整数，发出警告并要求重新输入
            JOptionPane.showMessageDialog(null, "请输入9~30的整数", "WARNING", JOptionPane.WARNING_MESSAGE);
            return JOptionPane.showInputDialog(null, "请重新输入");
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public int getMines() {
            return mines;
        }
    }

    class DifficultyChoice {
        //存储对话框的选项按钮
        private final Object[] options = {"低级", "中级", "高级", "自定义"};

        public int getDifficulty() {
            //调用showOptionDialog方法，创建一个选项对话框
            //存储用户的选择
            int choice = JOptionPane.showOptionDialog(null, "请选择游戏难度", "选项对话框", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            //根据返回值判断用户的选择
            if (choice == -1) {
                //用户关闭了对话框
                JOptionPane.showMessageDialog(null, "您没有选择难度，程序将自动关闭");
                return -1;
            } else {
                //choice [0,1,2,3]分别对应"自定义", "高级", "中级", "低级"
                JOptionPane.showMessageDialog(null, "您选择了" + options[choice]);//暂时是给你一个窗口提示难度
                difficulty = choice;
                return choice;//对应的上面的选项
            }
        }
    }

    class MainWindows {
        private int rows;
        private int columns;

        public MainWindows(boolean[][] mines) {
            //扫雷主界面，需要传入一个布尔数组表示地雷排布
            setMatrix(mines);
            minesweeperButton = new MinesweeperButton[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    minesweeperButton[i][j] = new MinesweeperButton(j, i, mines);
                }
            }
        }

        public void executeMinesweeper() {
            minesweeper = new JFrame("扫雷");
            minesweeperMenuBar = new MinesweeperMenuBar();
            minesweeper.setJMenuBar(minesweeperMenuBar);
            minesweeper.setLayout(new BorderLayout()); // 设置边框布局管理器

            statusPanel = new MinesweeperStatusPanel(); // 显示状态的面板
            JPanel minesPanel = new JPanel(); // 放置地雷的面板

            minesPanel.setLayout(new GridLayout(rows, columns, 0, 0)); // 使用网格布局管理器管理地雷按钮

            //将地雷按钮添加到一个网格panel里
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    minesPanel.add(minesweeperButton[i][j]);
                }
            }
            minesweeper.add(statusPanel, BorderLayout.NORTH); // 将状态面板添加到上部区域
            minesweeper.add(minesPanel, BorderLayout.CENTER); // 将地雷面板添加到中心区域

            minesweeper.pack(); // 设置自动窗口大小
            minesweeper.setLocationRelativeTo(null); // 设置窗口居中
            minesweeper.setVisible(true); // 设置窗口可见
            minesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
        }

        public void setMatrix(boolean[][] matrix) {
            //一个记录地雷矩阵长度的方法
            rows = matrix.length;
            columns = matrix[0].length;
        }
    }
}
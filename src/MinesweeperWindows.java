import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MinesweeperWindows {

    private int height;
    private int width;
    private int mines;

    public void executeDifficultChoice() {
        /*
        choice [0,1,2,3]分别对应"低级", "中级", "高级", "自定义"
        初级为10个，中级为40个，高级为99个
         */
        switch (new DifficultyChoice().getDifficulty()) {
            case 0 -> {
                height = 9;
                width = 9;
                mines = 10;
            }
            case 1 -> {
                height = 16;
                width = 16;
                mines = 40;
            }
            case 2 -> {
                height = 16;
                width = 30;
                mines = 99;
            }
            case 3 -> {
                CustomDifficulty customDifficulty = new CustomDifficulty();
                height = customDifficulty.getHeight();
                width = customDifficulty.getWidth();
                mines = customDifficulty.getMines();
            }
            //TODO:确认这里直接结束程序是否合适
            default -> System.exit(0);
        }
    }

    public void executeMinesweeper(boolean[][] mines, int numbers) {
        placeMines(mines, numbers);//在此处使用随机布雷方法
        new MainWindows(mines).executeMinesweeper();
    }

    private void placeMines(boolean[][] mines, int numbers) {
        //TODO:写一个布雷的算法，就是把布尔数组的false变成true，numbers表示布雷的数量
        Random r = new Random();
        while (numbers>0){
            //随机产生雷所在行、所在列
            int rr = r.nextInt(mines.length);
            int cc = r.nextInt(mines[0].length);
            //判断当前雷是否有重复，没有就安雷
            if(mines[rr][cc] != true){
                mines[rr][cc] = true;
                numbers = numbers - 1;
            }
        }
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[i].length; j++) {
                System.out.print(mines[i][j]+" ");
            }
            System.out.println();
        }
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

    /*
    showInputDialog获取用户输入的数据
    Integer转换为整数
     */
    static class CustomDifficulty {

        private final int height;
        private final int width;
        private final int mines;

        public CustomDifficulty() {
            String High = JOptionPane.showInputDialog(null, "请输入高度");
            while (checkCustomNumbers(High)) {
                High = reEnterNumbers();
            }
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
                //重新回到难度选择
                MinesweeperWindows mw = new MinesweeperWindows();
                mw.executeDifficultChoice();
                return false;
            } else {
                try {
                    //判断输入的是否为整数
                    Integer.parseInt(number);
                    //判断整数的大小是否符合要求
                    return 8 >= Integer.parseInt(number) || Integer.parseInt(number) >= 30;
                } catch (Exception e) {
                    //不是整数
                    return true;
                }
            }
        }

        private String reEnterNumbers() {
            //如果输入的不是符合要求的整数，发出警告并要求重新输入
            JOptionPane.showMessageDialog(null, "请输入9~30的整数", "Title", JOptionPane.WARNING_MESSAGE);
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

    static class DifficultyChoice {

        //存储对话框的选项按钮
        private final Object[] options = {"低级", "中级", "高级", "自定义"};
        //存储用户的选择
        private int choice;

        public int getDifficulty() {
            //调用showOptionDialog方法，创建一个选项对话框
            choice = JOptionPane.showOptionDialog(null, "请选择游戏难度", "选项对话框", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            //根据返回值判断用户的选择
            if (choice == -1) {
                //用户关闭了对话框
                JOptionPane.showMessageDialog(null, "您没有选择难度，程序将自动关闭");
                return -1;
            } else {
                //choice [0,1,2,3]分别对应"自定义", "高级", "中级", "低级"
                JOptionPane.showMessageDialog(null, "您选择了" + options[choice]);//暂时是给你一个窗口提示难度
                return choice;//对应的上面的选项
            }
        }
    }

    /*
    TODO:主窗口需要添加菜单栏（暂定是以下内容）
    游戏        帮助
    开局        关于

    初级
    中级
    高级
    自定义

    扫雷英雄榜（基本完成了再写）
    退出
     */
    static class MainWindows {
        private final boolean[][] mines;
        private final MinesweeperButton[][] minesweeperButton;
        private int rows;
        private int columns;

        public MainWindows(boolean[][] mines) {
            //TODO:扫雷主界面，需要传入一个布尔数组
            this.mines = mines;
            setMatrix(mines);
            minesweeperButton = new MinesweeperButton[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    minesweeperButton[i][j] = new MinesweeperButton(i, j, mines);
                }
            }
        }

        public void executeMinesweeper() {
            JFrame minesWeeper = new JFrame("扫雷");
            minesWeeper.setLayout(new BorderLayout()); // 设置边框布局管理器

            JPanel statusPanel = new JPanel(); // 显示状态的面板
            JPanel minesPanel = new JPanel(); // 放置地雷的面板

            JButton restartButton = new JButton("重新开始");
            MinesTimer timer = new MinesTimer();

            statusPanel.setLayout(new BorderLayout());
            minesPanel.setLayout(new GridLayout(rows, columns)); // 使用网格布局管理器管理地雷按钮

            statusPanel.add(timer, BorderLayout.EAST);
            statusPanel.add(restartButton, BorderLayout.CENTER);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    minesPanel.add(minesweeperButton[i][j]);
                }
            }

            minesWeeper.add(statusPanel, BorderLayout.NORTH); // 将状态面板添加到上部区域
            minesWeeper.add(minesPanel, BorderLayout.CENTER); // 将地雷面板添加到中心区域

            minesWeeper.pack(); // 设置自动窗口大小
            minesWeeper.setVisible(true); // 设置窗口可见
            minesWeeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
        }

        public void setMatrix(boolean[][] matrix) {
            //一个记录地雷矩阵长度的方法
            rows = matrix.length;
            columns = matrix[0].length;
        }
    }
}

class MinesTimer extends JLabel {
    public MinesTimer() {
        super("000", JLabel.CENTER); // 创建一个居中显示0的标签
        super.setFont(new Font("Arial", Font.BOLD, 20)); // 设置标签的字体为Arial，粗体，20号
        super.setForeground(Color.RED); // 设置标签的前景色为红色

        int seconds = 0; // 定义一个变量用于存储秒数
    }

    public void startMinesTimer() {
        //未完成，请勿使用
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class MinesweeperWindows {
    public static JLabel timer_1;
    public static JLabel timer_2;
    public static JLabel timer_3;
    public static MinesTimer minesTimer;
    static JButton FaceButton;
    private static JFrame minesweeper;
    private static MinesweeperButton[][] minesweeperButton;
    private static int height;
    private static int width;
    private static int mines;

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
                //重新回到难度选择(有bug，有时间再改)
                System.exit(0);
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

    static class DifficultyChoice {

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
                return choice;//对应的上面的选项
            }
        }
    }

    static class Menu {
        JMenuBar menuBar;

        public Menu() {
            menuBar = new JMenuBar();// 创建菜单栏对象

            JMenu menuGames = new JMenu("游戏");// 创建菜单对象
            JMenu menuHelps = new JMenu("帮助");

            JMenuItem menuItem1_5 = new JMenuItem("开局");// 创建子菜单的菜单项对象
            JMenuItem menuItem1_1 = new JMenuItem("初级");
            JMenuItem menuItem1_2 = new JMenuItem("中级");
            JMenuItem menuItem1_3 = new JMenuItem("高级");
            JMenuItem menuItem1_4 = new JMenuItem("自定义");
            JMenuItem menuItem1_6 = new JMenuItem("重新开始这一局");
            JMenuItem menuItem2_1 = new JMenuItem("关于");

            menuGames.add(menuItem1_5);
            menuGames.addSeparator();
            menuGames.add(menuItem1_1);
            menuGames.add(menuItem1_2);
            menuGames.add(menuItem1_3);
            menuGames.add(menuItem1_4);
            menuGames.addSeparator();
            menuGames.add(menuItem1_6);
            menuHelps.add(menuItem2_1);

            menuBar.add(menuGames);
            menuBar.add(menuHelps);

            //为菜单项添加事件监听器
            class Item1_5Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(MinesweeperWindows.minesTimer != null) {
                        minesTimer.stop();
                    }
                    minesweeper.dispose();
                    new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
                }
            }
            menuItem1_5.addActionListener(new Item1_5Listener());
            class Item1_1Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //关闭计时器
                    if(MinesweeperWindows.minesTimer != null) {
                        minesTimer.stop();
                    }
                    //关闭原窗口
                    minesweeper.dispose();
                    //修改数据
                    height = 9;
                    width = 9;
                    mines = 10;
                    //打开新窗口
                    new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
                }
            }
            menuItem1_1.addActionListener(new Item1_1Listener());
            class Item1_2Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(MinesweeperWindows.minesTimer != null) {
                        minesTimer.stop();
                    }
                    minesweeper.dispose();
                    height = 16;
                    width = 16;
                    mines = 40;
                    new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
                }
            }
            menuItem1_2.addActionListener(new Item1_2Listener());
            class Item1_3Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(MinesweeperWindows.minesTimer != null) {
                        minesTimer.stop();
                    }
                    minesweeper.dispose();
                    height = 16;
                    width = 30;
                    mines = 99;
                    new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
                }
            }
            menuItem1_3.addActionListener(new Item1_3Listener());
            class Item1_4Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(MinesweeperWindows.minesTimer != null) {
                        minesTimer.stop();
                    }
                    minesweeper.dispose();
                    CustomDifficulty customDifficulty = new CustomDifficulty();
                    new MinesweeperWindows().executeMinesweeper(new boolean[customDifficulty.getHeight()][customDifficulty.getWidth()], customDifficulty.getMines());
                }
            }
            menuItem1_4.addActionListener(new Item1_4Listener());
            class Item1_6Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //关闭计时器
                    if(MinesweeperWindows.minesTimer != null) {
                        minesTimer.stop();
                    }
                    //遍历所有格子，将他们设置为初始状态
                    int i, j;
                    for (i = 0; i < MinesweeperWindows.height; i++) {
                        for (j = 0; j < MinesweeperWindows.width; j++) {
                            minesweeperButton[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
                        }
                    }//计时器初始化
                    MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                    MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                    MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                    MinesTimer.firstClick = true;
                    /*TODO:
                    将格子设置成未打开状态
                    将剩余雷数归零
                    */
                }

            }
            menuItem1_6.addActionListener(new Item1_6Listener());
            class Item2_1Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //TODO:完善"关于"
                    JOptionPane.showMessageDialog(null, "看到这条信息的人奖励2h原神");

                }
            }
            menuItem2_1.addActionListener(new Item2_1Listener());
        }

        public JMenuBar getJMenuBar() {
            return menuBar;
        }
    }

    /*
    TODO:
    扫雷英雄榜（基本完成了再写）
    退出
     */
    static class MainWindows {
        private int rows;
        private int columns;

        public MainWindows(boolean[][] mines) {
            //TODO:扫雷主界面，需要传入一个布尔数组
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
            minesweeper.setJMenuBar(new Menu().getJMenuBar());
            minesweeper.setLayout(new BorderLayout()); // 设置边框布局管理器
            JPanel statusPanel = new JPanel(); // 显示状态的面板
            JPanel minesPanel = new JPanel(); // 放置地雷的面板
            JPanel timePanel = new JPanel(); // 计时器面板

            FaceButton = new JButton(); // 添加笑脸按钮
            FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile.png")); // 初始为微笑
            FaceButton.addMouseListener(new FaceButtonListener()); // 为笑脸按钮添加鼠标监视器
            //创建三个计时器面板
            timer_1 = new JLabel();
            timer_2 = new JLabel();
            timer_3 = new JLabel();

            statusPanel.setLayout(new BorderLayout()); // 设置边框布局管理器
            minesPanel.setLayout(new GridLayout(rows, columns)); // 使用网格布局管理器管理地雷按钮
            statusPanel.add(timePanel, BorderLayout.EAST); // 计时器面板添加到状态面板右侧
            statusPanel.add(FaceButton); // 笑脸添加到状态面板中

            //将地雷按钮添加到一个网格panel里
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    minesPanel.add(minesweeperButton[i][j]);
                }
            }
            minesweeper.add(statusPanel, BorderLayout.NORTH); // 将状态面板添加到上部区域
            minesweeper.add(minesPanel, BorderLayout.CENTER); // 将地雷面板添加到中心区域

            //计时器区域添加到计时器面板
            timePanel.add(timer_1);
            timePanel.add(timer_2);
            timePanel.add(timer_3);

            //计时器初始图片为0
            timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
            timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
            timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));

            minesweeper.pack(); // 设置自动窗口大小
            minesweeper.setLocationRelativeTo(null); // 设置窗口居中
            minesweeper.setVisible(true); // 设置窗口可见
            minesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
            MinesTimer.firstClick = true; //初始化点击状态
        }

        public void setMatrix(boolean[][] matrix) {
            //一个记录地雷矩阵长度的方法
            rows = matrix.length;
            columns = matrix[0].length;
        }

        //笑脸按钮鼠标监听器
        static class FaceButtonListener implements MouseListener {
            //点击时重新开始游戏
            @Override
            public void mouseClicked(MouseEvent e) {
                if(MinesweeperWindows.minesTimer != null) {
                    minesTimer.stop();
                }
                minesweeper.dispose();
                new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile_clicked.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
    }
/*TODO:
添加一个显示剩余雷数的区域
 */


}
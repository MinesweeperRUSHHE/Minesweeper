import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class MinesweeperWindows {
    public static int difficult;
    //定义三种难度的历史记录1为低级，3为高级
    public static int[] times = new int[3];
    public static String[] names = new String[3];
    static JFrame minesweeper;
    static String name;
    static int time;
    static File timefile;
    static File namefile;
    private static MinesweeperButton[][] minesweeperButton;
    private static int height;
    private static int width;
    private static int mines; // 此为设置的地雷数

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    public static int getMines() {
        return mines;
    }

    //引爆所有地雷的方法
    public static void detonateAllMines(int xLocation, int yLocation) {
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

    public static void openAllCell(int xLocation, int yLocation) {
        minesweeperButton[yLocation][xLocation].setMinesVisible(true);
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
                    minesweeperButton[i][j].setMinesVisible(true);
                }
            }
        }
    }
    public static void successOrNot(){
        int number = minesweeperButton.length * minesweeperButton[0].length;
        for (int i = 0;i<minesweeperButton.length;i++){
            for (int j = 0;j<minesweeperButton[0].length;j++){
                if(!(minesweeperButton[i][j].getStatus() == -1) &&  minesweeperButton[i][j].isMinesVisible()){//遍历格子，如果格子打开不为炸弹，并且已经可见
                    number-= 1;
                }
            }
        }
        if(number == mines){//判断雷数和剩余格子数是否相等
            UIManager.put("OptionPane.buttonFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
            UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
            Component mainFrame = null;
            MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
            MinesweeperWindows.Recording();
            try {
                MinesweeperWindows.Read();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                MinesweeperWindows.Write();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void Recording() {
        //记录玩家昵称和时间
        name = JOptionPane.showInputDialog(null, "原神60级玩家太有实力了\n旅行者你的名字是？", "胜利", JOptionPane.INFORMATION_MESSAGE);
        time = MinesweeperStatusPanel.MinesTimerPanel.MinesTimer.seconds;
    }

    //查看历史数据
    public static void Read() throws IOException {
        //读取历史记录
        timefile = new File("./src/Themes/Classic/time.txt");
        namefile = new File("./src/Themes/Classic/name.txt");
        FileReader time_fileReader = new FileReader(timefile);
        FileReader name_fileReader = new FileReader(namefile);
        BufferedReader time_bufferedReader = new BufferedReader(time_fileReader);
        BufferedReader name_bufferedReader = new BufferedReader(name_fileReader);
        for (int i = 0; i < 3; i++) {
            // 读取一行内容，并将其转换为整数或字符串，存储到对应的数组中
            times[i] = Integer.parseInt(time_bufferedReader.readLine());
            names[i] = name_bufferedReader.readLine();
        }
        // 关闭BufferedReader对象和FileReader对象，释放资源
        time_bufferedReader.close();
        time_fileReader.close();
        name_bufferedReader.close();
        name_fileReader.close();
    }

    //写入新数据
    public static void Write() throws IOException {
        //将time与历史记录比较，0为初级，1为中级，2为高级
        //如果小于历史记录，则更新
        if (time < times[difficult]) {
            times[difficult] = time;
            names[difficult] = name;
        }
        //写入更改后的数据
        FileWriter time_fileWriter = new FileWriter(timefile);
        FileWriter name_fileWriter = new FileWriter(namefile);
        // 使用 for 循环遍历数组中的每一个元素
        for (int i = 0; i < 3; i++) {
            // 写入每一个元素，并在末尾添加换行符
            time_fileWriter.write(times[i] + "\n");
            name_fileWriter.write(names[i] + "\n");
        }
        // 关闭 FileWriter 对象，释放资源
        time_fileWriter.close();
        name_fileWriter.close();
    }

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
                difficult = choice;
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
            JMenuItem menuItem1_7 = new JMenuItem("扫雷英雄榜");
            JMenuItem menuItem2_1 = new JMenuItem("关于");

            menuGames.add(menuItem1_5);
            menuGames.addSeparator();
            menuGames.add(menuItem1_1);
            menuGames.add(menuItem1_2);
            menuGames.add(menuItem1_3);
            menuGames.add(menuItem1_4);
            menuGames.addSeparator();
            menuGames.add(menuItem1_6);
            menuGames.add(menuItem1_7);
            menuHelps.add(menuItem2_1);

            menuBar.add(menuGames);
            menuBar.add(menuHelps);

            //为菜单项添加事件监听器
            //开局
            class Item1_5Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //关闭计时器
                    if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                    }
                    //关闭原窗口
                    minesweeper.dispose();
                    //打开新窗口
                    new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
                }
            }
            menuItem1_5.addActionListener(new Item1_5Listener());
            //初级
            class Item1_1Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //关闭计时器
                    if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
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
            //中级
            class Item1_2Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //关闭计时器
                    if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                    }
                    //关闭原窗口
                    minesweeper.dispose();
                    //修改数据
                    height = 16;
                    width = 16;
                    mines = 40;
                    //打开新窗口
                    new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
                }
            }
            menuItem1_2.addActionListener(new Item1_2Listener());
            //高级
            class Item1_3Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //关闭计时器
                    if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                    }
                    //关闭原窗口
                    minesweeper.dispose();
                    //修改数据
                    height = 16;
                    width = 30;
                    mines = 99;
                    //打开新窗口
                    new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.height][MinesweeperWindows.width], MinesweeperWindows.mines);
                }
            }
            menuItem1_3.addActionListener(new Item1_3Listener());
            //自定义
            class Item1_4Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                    }
                    //关闭原窗口
                    minesweeper.dispose();
                    //打开新窗口
                    CustomDifficulty customDifficulty = new CustomDifficulty();
                    new MinesweeperWindows().executeMinesweeper(new boolean[customDifficulty.getHeight()][customDifficulty.getWidth()], customDifficulty.getMines());
                }
            }
            menuItem1_4.addActionListener(new Item1_4Listener());
            //重新开始这一局
            class Item1_6Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //关闭计时器
                    if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                    }
                    //遍历所有格子，将他们设置为初始状态
                    int i, j;
                    for (i = 0; i < MinesweeperWindows.height; i++) {
                        for (j = 0; j < MinesweeperWindows.width; j++) {
                            minesweeperButton[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
                            minesweeperButton[i][j].leftClickable = true;
                            minesweeperButton[i][j].rightClickable = true;
                            minesweeperButton[i][j].setCanFlag(true);
                            minesweeperButton[i][j].setMinesVisible(false);
                        }
                    }//计时器初始化
                    MinesweeperStatusPanel.MinesTimerPanel.minesTimer = new MinesweeperStatusPanel.MinesTimerPanel.MinesTimer();
                }

            }
            menuItem1_6.addActionListener(new Item1_6Listener());
            class Item1_7Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        Read();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    JOptionPane.showMessageDialog(null, "\n初级：" + times[0] + "秒                   " + names[0] + "\n中级：" + times[1] + "秒                   " + names[1] + "\n高级：" + times[2] + "秒                   " + names[2],"扫雷英雄榜",JOptionPane.PLAIN_MESSAGE);
                }

            }
            menuItem1_7.addActionListener(new Item1_7Listener());
            class Item2_1Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //TODO:完善"关于"
                    JOptionPane.showMessageDialog(null, "看到这条信息的人奖励2h原神", "关于", JOptionPane.PLAIN_MESSAGE);

                }
            }
            menuItem2_1.addActionListener(new Item2_1Listener());
        }

        public JMenuBar getJMenuBar() {
            return menuBar;
        }
    }

    static class MainWindows {
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
            minesweeper.setJMenuBar(new Menu().getJMenuBar());
            minesweeper.setLayout(new BorderLayout()); // 设置边框布局管理器

            MinesweeperStatusPanel statusPanel = new MinesweeperStatusPanel(); // 显示状态的面板
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
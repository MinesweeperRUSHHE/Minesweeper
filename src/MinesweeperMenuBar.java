import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class MinesweeperMenuBar extends JMenuBar {
    static MinesweeperMenuGames menuGames;
    MinesweeperMenuHelp menuHelps;

    public MinesweeperMenuBar() {
        menuGames = new MinesweeperMenuGames("游戏");// 创建菜单对象
        menuHelps = new MinesweeperMenuHelp("帮助");

        add(menuGames);
        add(menuHelps);
    }

    static class MinesweeperMenuGames extends JMenu {
        public MinesweeperMenuGames(String name) {
            super(name);

            JMenuItem menuItem1_5 = new JMenuItem("开局");// 创建子菜单的菜单项对象
            JMenuItem menuItem1_1 = new JMenuItem("初级");
            JMenuItem menuItem1_2 = new JMenuItem("中级");
            JMenuItem menuItem1_3 = new JMenuItem("高级");
            JMenuItem menuItem1_4 = new JMenuItem("自定义");
            JMenuItem menuItem1_6 = new JMenuItem("重新开始这一局");
            JMenuItem menuItem1_7 = new JMenuItem("扫雷英雄榜");

            add(menuItem1_5);
            addSeparator();
            add(menuItem1_1);
            add(menuItem1_2);
            add(menuItem1_3);
            add(menuItem1_4);
            addSeparator();
            add(menuItem1_6);
            add(menuItem1_7);

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
                    Minesweeper.mw.dispose();
                    //打开新窗口
                    new MinesweeperWindows(Minesweeper.rows, Minesweeper.columns, Minesweeper.difficulty, Minesweeper.minesNumber);
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
                    Minesweeper.mw.dispose();
                    //修改数据
                    Minesweeper.rows = 9;
                    Minesweeper.columns = 9;
                    Minesweeper.difficulty = 0;
                    Minesweeper.minesNumber = 10;
                    //打开新窗口
                    new MinesweeperWindows(9, 9, 0, 10);
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
                    Minesweeper.mw.dispose();
                    //修改数据
                    Minesweeper.rows = 16;
                    Minesweeper.columns = 16;
                    Minesweeper.difficulty = 1;
                    Minesweeper.minesNumber = 40;
                    //打开新窗口
                    new MinesweeperWindows(16, 16, 1, 40);
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
                    Minesweeper.mw.dispose();
                    //修改数据
                    Minesweeper.rows = 16;
                    Minesweeper.columns = 30;
                    Minesweeper.difficulty = 2;
                    Minesweeper.minesNumber = 99;
                    //打开新窗口
                    new MinesweeperWindows(16, 30, 2, 99);
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
                    Minesweeper.mw.dispose();
                    //打开新窗口
                    new Minesweeper.DifficultyChoice.CustomDifficulty();
                    Minesweeper.difficulty = 3;
                    new MinesweeperWindows(Minesweeper.rows, Minesweeper.columns, Minesweeper.difficulty, Minesweeper.minesNumber);
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
                    for (i = 0; i < Minesweeper.rows; i++) {
                        for (j = 0; j < Minesweeper.columns; j++) {
                            MinesweeperWindows.minesweeperButton[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
                            MinesweeperWindows.minesweeperButton[i][j].leftClickable = true;
                            MinesweeperWindows.minesweeperButton[i][j].rightClickable = true;
                            MinesweeperWindows.minesweeperButton[i][j].setCanFlag(true);
                            MinesweeperWindows.minesweeperButton[i][j].setBelongToFlag(false);
                            MinesweeperWindows.minesweeperButton[i][j].setMinesVisible(false);
                        }
                    }
                    MinesweeperStatusPanel.RemainingMinesPanel.setMinesNumber();
                    MinesweeperStatusPanel.remainingMinesPanel.setLabelIcon();
                    //计时器初始化
                    MinesweeperStatusPanel.MinesTimerPanel.minesTimer = new MinesweeperStatusPanel.MinesTimerPanel.MinesTimer();
                }

            }
            menuItem1_6.addActionListener(new Item1_6Listener());
            class Item1_7Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        BestTimes.readBestTime();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    JOptionPane.showMessageDialog(null, "\n初级：" + BestTimes.bestTime[0] + "秒                   " + BestTimes.bestName[0] + "\n中级：" + BestTimes.bestTime[1] + "秒                   " + BestTimes.bestName[1] + "\n高级：" + BestTimes.bestTime[2] + "秒                   " + BestTimes.bestName[2], "扫雷英雄榜", JOptionPane.PLAIN_MESSAGE);
                }

            }
            menuItem1_7.addActionListener(new Item1_7Listener());
        }
        static class BestTimes {
            private static final String[] difficult = {"easy", "medium", "hard"};
            private static final String[] bestName = new String[3];
            private static final Properties prop = new Properties();
            private static String name;
            private static int difficulty;
            private static final int[] bestTime = new int[3];

            public BestTimes() throws IOException {
                readBestTime();
            }

            public static void recordBestTime() {
                //记录玩家昵称和时间
                name = JOptionPane.showInputDialog(null, "原神60级玩家太有实力了\n旅行者你的名字是？", "胜利", JOptionPane.INFORMATION_MESSAGE);
            }

            //查看历史数据
            static void readBestTime() throws IOException {
                difficulty = Minesweeper.difficulty;
                //读取历史记录
                try {
                    prop.load(new FileInputStream("bestTime.properties"));
                    for (int i = 0; i < 3; i++) {
                        bestTime[i] = Integer.parseInt(prop.getProperty(difficult[i] + "Time"));
                        bestName[i] = prop.getProperty(difficult[i] + "Name");
                    }
                    //如果文件不存在，则创建一个默认文件
                } catch (FileNotFoundException ignored) {
                    prop.setProperty("easyName", "匿名");
                    prop.setProperty("mediumName", "匿名");
                    prop.setProperty("hardName", "匿名");
                    prop.setProperty("easyTime", "999");
                    prop.setProperty("mediumTime", "999");
                    prop.setProperty("hardTime", "999");
                    prop.store(new FileOutputStream("bestTime.properties"), null);
                    readBestTime();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            //写入新数据
            public static void writeBestTime() throws IOException {
                readBestTime(); // 确保英雄榜文件存在
                int time = MinesweeperStatusPanel.MinesTimerPanel.MinesTimer.seconds;
                //将time与历史记录比较，0为初级，1为中级，2为高级
                //如果小于历史记录，则更新
                if (time < Integer.parseInt(prop.getProperty(difficult[difficulty] + "Time"))) {
                    recordBestTime();
                    prop.setProperty(difficult[difficulty] + "Time", String.valueOf(time));
                    prop.setProperty(difficult[difficulty] + "Name", name);
                    prop.store(new FileOutputStream("bestTime.properties"), null);
                }
            }
        }
    }

    static class MinesweeperMenuHelp extends JMenu {
        public MinesweeperMenuHelp(String name) {
            super(name);

            JMenuItem menuItem2_1 = new JMenuItem("关于");

            add(menuItem2_1);

            //为菜单项添加事件监听器
            class Item2_1Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    new AboutDialog().setVisible(true);
                }
            }
            menuItem2_1.addActionListener(new Item2_1Listener());
        }

        static class AboutDialog extends JDialog {
            public AboutDialog() {
                super((Frame) null, "关于", true);
                JPanel aboutPanel = new JPanel();
                JButton github = new JButton("查看GitHub仓库");
                ImageIcon icon = new ImageIcon("./src/Themes/Organization icon.png");
                icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                JLabel iconLabel = new JLabel(icon);

                aboutPanel.setLayout(new FlowLayout());
                aboutPanel.add(iconLabel, FlowLayout.LEFT);
                aboutPanel.add(new JLabel("<html>芝士一个不成熟的扫雷软件，<br/>如果发现有bug，<br/>欢迎通过提交issue或者pull request</html>"), FlowLayout.CENTER);
                aboutPanel.add(github, FlowLayout.RIGHT);

                github.addActionListener(e1 -> {
                    if (java.awt.Desktop.isDesktopSupported()) {
                        try {
                            java.net.URI uri = java.net.URI.create("https://github.com/MinesweeperRUSHHE/Minesweeper");
                            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                                desktop.browse(uri);
                            }
                        } catch (Exception e2) {
                            System.out.println(e2.getMessage());
                        }
                    }
                });

                add(aboutPanel); // 将aboutPanel添加到对话框的内容面板中
                pack(); // 窗口自动大小
                setLocationRelativeTo(null); //设置居中
            }
        }
    }
}
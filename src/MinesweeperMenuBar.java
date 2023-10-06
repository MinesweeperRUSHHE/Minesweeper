import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MinesweeperMenuBar extends JMenuBar {
    MinesweeperMenuGame minesweeperMenuGame;
    MinesweeperMenuHelp minesweeperMenuHelp;
    BestTimes bestTimes;

    public MinesweeperMenuBar() {
        minesweeperMenuGame = new MinesweeperMenuGame();
        minesweeperMenuHelp = new MinesweeperMenuHelp();
        bestTimes = new BestTimes();

        add(minesweeperMenuGame);
        add(minesweeperMenuHelp);
    }

    public void recordBestTime() throws IOException {
        bestTimes.recordBestTime();
    }

    static class MinesweeperMenuHelp extends JMenu {
        public MinesweeperMenuHelp() {
            super("帮助");

            JMenuItem menuItem2_1 = new JMenuItem("关于");
            add(menuItem2_1);

            class Item2_1Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //TODO:完善"关于"
                    JOptionPane.showMessageDialog(null, "看到这条信息的人奖励2h原神", "关于", JOptionPane.PLAIN_MESSAGE);
                }
            }
            menuItem2_1.addActionListener(new Item2_1Listener());
        }
    }

    static class BestTimes {
        private final String[] difficult = {"easy", "medium", "hard"};
        private final String[] bestName;
        Properties prop = new Properties();
        private int time;
        private String name;
        private int difficulty;
        private final int[] bestTime;

        public BestTimes() {
            bestTime = new int[3];
            bestName = new String[3];
        }

        public void recordBestTime() throws IOException {
            //记录玩家昵称和时间
            name = JOptionPane.showInputDialog(null, "原神60级玩家太有实力了\n旅行者你的名字是？", "胜利", JOptionPane.INFORMATION_MESSAGE);
            time = Minesweeper.getMinesweeperWindows().getStatusPanel().getSeconds();
            writeBestTime();
        }

        //查看历史数据
        void readBestTime() throws IOException {
            difficulty = Minesweeper.getMinesweeperWindows().getDifficulty();
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
        public void writeBestTime() throws IOException {
            //将time与历史记录比较，0为初级，1为中级，2为高级
            //如果小于历史记录，则更新
            if (time < Integer.parseInt(prop.getProperty(difficult[difficulty] + "Time"))) {
                prop.setProperty(difficult[difficulty] + "Time", String.valueOf(time));
                prop.setProperty(difficult[difficulty] + "Name", name);
                prop.store(new FileOutputStream("bestTime.properties"), null);
            }
        }
    }

    class MinesweeperMenuGame extends JMenu {

        public MinesweeperMenuGame() {
            super("游戏");

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
                    MinesweeperWindows.minesweeper.dispose();
                    //打开新窗口
                    Minesweeper.getMinesweeperWindows().executeMinesweeper(new boolean[Minesweeper.getMinesweeperWindows().getHeight()][Minesweeper.getMinesweeperWindows().getWidth()], Minesweeper.getMinesweeperWindows().getMines());
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
                    MinesweeperWindows.minesweeper.dispose();
                    //修改数据
                    Minesweeper.getMinesweeperWindows().setHeight(9);
                    Minesweeper.getMinesweeperWindows().setWidth(9);
                    Minesweeper.getMinesweeperWindows().setMines(10);
                    //打开新窗口
                    Minesweeper.getMinesweeperWindows().executeMinesweeper(new boolean[Minesweeper.getMinesweeperWindows().getHeight()][Minesweeper.getMinesweeperWindows().getWidth()], Minesweeper.getMinesweeperWindows().getMines());
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
                    MinesweeperWindows.minesweeper.dispose();
                    //修改数据
                    Minesweeper.getMinesweeperWindows().setHeight(16);
                    Minesweeper.getMinesweeperWindows().setWidth(16);
                    Minesweeper.getMinesweeperWindows().setMines(40);
                    //打开新窗口
                    Minesweeper.getMinesweeperWindows().executeMinesweeper(new boolean[Minesweeper.getMinesweeperWindows().getHeight()][Minesweeper.getMinesweeperWindows().getWidth()], Minesweeper.getMinesweeperWindows().getMines());
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
                    MinesweeperWindows.minesweeper.dispose();
                    //修改数据
                    Minesweeper.getMinesweeperWindows().setHeight(16);
                    Minesweeper.getMinesweeperWindows().setWidth(30);
                    Minesweeper.getMinesweeperWindows().setMines(99);
                    //打开新窗口
                    Minesweeper.getMinesweeperWindows().executeMinesweeper(new boolean[Minesweeper.getMinesweeperWindows().getHeight()][Minesweeper.getMinesweeperWindows().getWidth()], Minesweeper.getMinesweeperWindows().getMines());
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
                    MinesweeperWindows.minesweeper.dispose();
                    //打开新窗口
                    MinesweeperWindows.CustomDifficulty customDifficulty = new MinesweeperWindows.CustomDifficulty();
                    Minesweeper.getMinesweeperWindows().executeMinesweeper(new boolean[customDifficulty.getHeight()][customDifficulty.getWidth()], customDifficulty.getMines());
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
                    for (i = 0; i < Minesweeper.getMinesweeperWindows().getHeight(); i++) {
                        for (j = 0; j < Minesweeper.getMinesweeperWindows().getWidth(); j++) {
                            Minesweeper.getMinesweeperWindows().getMinesweeperButton()[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
                            Minesweeper.getMinesweeperWindows().getMinesweeperButton()[i][j].setLeftClickable(true);
                            Minesweeper.getMinesweeperWindows().getMinesweeperButton()[i][j].setRightClickable(true);
                            Minesweeper.getMinesweeperWindows().getMinesweeperButton()[i][j].setCanFlag(true);
                            Minesweeper.getMinesweeperWindows().getMinesweeperButton()[i][j].setMinesVisible(false);
                        }
                    }//计时器初始化
                    MinesweeperStatusPanel.MinesTimerPanel.minesTimer = new MinesweeperStatusPanel.MinesTimerPanel.MinesTimer();
                    MinesweeperStatusPanel.remainingMinesPanel.resetRemainingMines();
                }
            }
            menuItem1_6.addActionListener(new Item1_6Listener());
            //扫雷英雄榜
            class Item1_7Listener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        bestTimes.readBestTime();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    JOptionPane.showMessageDialog(null, "\n初级：" + bestTimes.bestTime[0] + "秒                   " + bestTimes.bestName[0] + "\n中级：" + bestTimes.bestTime[1] + "秒                   " + bestTimes.bestName[1] + "\n高级：" + bestTimes.bestTime[2] + "秒                   " + bestTimes.bestName[2], "扫雷英雄榜", JOptionPane.PLAIN_MESSAGE);
                }

            }
            menuItem1_7.addActionListener(new Item1_7Listener());
        }
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class MinesweeperMenuBar extends JMenuBar {
    JMenu menuGames;
    JMenu menuHelps;
    public MinesweeperMenuBar() {

        menuGames = new JMenu("游戏");// 创建菜单对象
        menuHelps = new JMenu("帮助");

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

        add(menuGames);
        add(menuHelps);

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
                new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.getHeight()][MinesweeperWindows.getWidth()], MinesweeperWindows.getMines());
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
                MinesweeperWindows.height = 9;
                MinesweeperWindows.width = 9;
                MinesweeperWindows.mines = 10;
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
                MinesweeperWindows.minesweeper.dispose();
                //修改数据
                MinesweeperWindows.height = 16;
                MinesweeperWindows.width = 16;
                MinesweeperWindows.mines = 40;
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
                MinesweeperWindows.minesweeper.dispose();
                //修改数据
                MinesweeperWindows.height = 16;
                MinesweeperWindows.width = 30;
                MinesweeperWindows.mines = 99;
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
                MinesweeperWindows.minesweeper.dispose();
                //打开新窗口
                MinesweeperWindows.CustomDifficulty customDifficulty = new MinesweeperWindows.CustomDifficulty();
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
                        MinesweeperWindows.minesweeperButton[i][j].setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
                        MinesweeperWindows.minesweeperButton[i][j].leftClickable = true;
                        MinesweeperWindows.minesweeperButton[i][j].rightClickable = true;
                        MinesweeperWindows.minesweeperButton[i][j].setCanFlag(true);
                        MinesweeperWindows.minesweeperButton[i][j].setMinesVisible(false);
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
                    MinesweeperWindows.readBestTime();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(null, "\n初级：" + MinesweeperWindows.times[0] + "秒                   " + MinesweeperWindows.names[0] + "\n中级：" + MinesweeperWindows.times[1] + "秒                   " + MinesweeperWindows.names[1] + "\n高级：" + MinesweeperWindows.times[2] + "秒                   " + MinesweeperWindows.names[2], "扫雷英雄榜", JOptionPane.PLAIN_MESSAGE);
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
}

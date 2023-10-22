package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

            JMenuItem playGames = new JMenuItem("开局");// 创建子菜单的菜单项对象
            JMenuItem easyDifficulty = new JMenuItem("初级");
            JMenuItem mediumDifficulty = new JMenuItem("中级");
            JMenuItem hardDifficulty = new JMenuItem("高级");
            JMenuItem customDifficulty = new JMenuItem("自定义");
            JMenuItem restartGames = new JMenuItem("重新开始这一局");
            JMenuItem bestTime = new JMenuItem("扫雷英雄榜");

            add(playGames);
            addSeparator();
            add(easyDifficulty);
            add(mediumDifficulty);
            add(hardDifficulty);
            add(customDifficulty);
            addSeparator();
            add(restartGames);
            add(bestTime);

            //为菜单项添加事件监听器
            //开局
            playGames.addActionListener(arg0 -> {
                //关闭计时器
                if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                    MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                }
                //关闭原窗口
                Minesweeper.mw.dispose();
                //打开新窗口
                new MinesweeperWindows(Minesweeper.rows, Minesweeper.columns, Minesweeper.difficulty, Minesweeper.minesNumber);
            });

            //初级
            easyDifficulty.addActionListener(arg0 -> {
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
                Minesweeper.mw = new MinesweeperWindows(9, 9, 0, 10);
            });
            //中级
            mediumDifficulty.addActionListener(arg0 -> {
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
                Minesweeper.mw = new MinesweeperWindows(16, 16, 1, 40);
            });

            //高级
            hardDifficulty.addActionListener(arg0 -> {
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
                Minesweeper.mw = new MinesweeperWindows(16, 30, 2, 99);
            });

            //自定义
            customDifficulty.addActionListener(arg0 -> {
                if (MinesweeperStatusPanel.MinesTimerPanel.minesTimer != null) {
                    MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                }
                //关闭原窗口
                Minesweeper.mw.dispose();
                //打开新窗口
                new Minesweeper.DifficultyChoice.CustomDifficulty();
                Minesweeper.difficulty = 3;
                Minesweeper.mw = new MinesweeperWindows(Minesweeper.rows, Minesweeper.columns, Minesweeper.difficulty, Minesweeper.minesNumber);
            });

            //重新开始这一局
            restartGames.addActionListener(arg0 -> {
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
            });

            //英雄榜
            bestTime.addActionListener(arg0 -> {
                try {
                    new BestTimes().setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        static class BestTimes extends JDialog {
            private static final String[] difficult = {"easy", "medium", "hard"};
            private static final String[] bestName = new String[3];
            private static final Properties prop = new Properties();
            private static final int[] bestTime = new int[3];
            private static String name;
            private static int difficulty;

            public BestTimes() throws IOException {
                readBestTime();
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(3, 2, 10, 0));

                panel.add(new JLabel("初级：" + BestTimes.bestTime[0] + "秒"));
                panel.add(new JLabel(BestTimes.bestName[0]));
                panel.add(new JLabel("中级：" + BestTimes.bestTime[1] + "秒"));
                panel.add(new JLabel(BestTimes.bestName[1]));
                panel.add(new JLabel("高级：" + BestTimes.bestTime[2] + "秒"));
                panel.add(new JLabel(BestTimes.bestName[2]));

                // 放置重新记分和确定的panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(addResetButton());
                buttonPanel.add(addOkButton());

                getContentPane().add(panel, BorderLayout.EAST);
                getContentPane().add(buttonPanel, BorderLayout.SOUTH);

                setModal(true); // 设置模态
                pack(); // 自动大小
                setLocationRelativeTo(Minesweeper.mw); // 位置在主窗口处
            }

            public static void recordBestTime() {
                String[] difficulty = {"初级", "中级", "高级"};
                //记录玩家昵称和时间
                name = JOptionPane.showInputDialog(null, "已破" + difficulty[Minesweeper.difficulty] + "记录。\n请留尊姓大名。", null, JOptionPane.INFORMATION_MESSAGE);
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

            private JButton addResetButton() {
                JButton reset = new JButton("重新记分");
                reset.addActionListener(e -> {
                    // 关闭英雄榜
                    dispose();
                    try {
                        Files.delete(Paths.get("./bestTime.properties")); // 删除英雄榜
                        readBestTime(); // 读取（英雄榜文件不存在的时候会自动创建）
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(true); // 重新开启
                });
                return reset;
            }

            private JButton addOkButton() {
                JButton okButton = new JButton("确定");
                // 为确定按钮添加事件监听器
                okButton.addActionListener(e -> dispose()); // 关闭英雄榜
                return okButton;
            }
        }
    }

    static class MinesweeperMenuHelp extends JMenu {
        public MinesweeperMenuHelp(String name) {
            super(name);

            JMenuItem menuItem2_1 = new JMenuItem("关于");

            add(menuItem2_1);

            //为菜单项添加事件监听器
            menuItem2_1.addActionListener(arg0 -> new AboutDialog().setVisible(true));
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
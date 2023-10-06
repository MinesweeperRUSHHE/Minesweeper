import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class MinesweeperStatusPanel extends JPanel {
    static FaceButton faceButton;
    static RemainingMinesPanel remainingMinesPanel;
    MinesTimerPanel timerPanel;

    public MinesweeperStatusPanel() {
        remainingMinesPanel = new RemainingMinesPanel();
        faceButton = new FaceButton(); // 添加笑脸按钮
        timerPanel = new MinesTimerPanel();

        setLayout(new FlowLayout()); // 设置边框布局管理器

        add(remainingMinesPanel, FlowLayout.LEFT); //
        add(faceButton, FlowLayout.CENTER); // 笑脸添加到状态面板中
        add(timerPanel, FlowLayout.RIGHT); // 计时器面板添加到状态面板右侧
    }

    private static void initialLabel(JLabel label) {
        ImageIcon imageIcon = new ImageIcon("./src/Themes/Classic/Number_0.png");
        label.setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        label.setIcon(imageIcon);
    }

    static class MinesTimerPanel extends JPanel {

        public static MinesTimer minesTimer;
        private static JLabel timer_1;
        private static JLabel timer_2;
        private static JLabel timer_3;

        public MinesTimerPanel() {
            timer_1 = new JLabel();
            timer_2 = new JLabel();
            timer_3 = new JLabel();

            initialLabel(timer_1);
            initialLabel(timer_2);
            initialLabel(timer_3);

            setLayout(new GridLayout(1, 3, 0, 0));

            //计时器区域添加到计时器面板
            add(timer_1);
            add(timer_2);
            add(timer_3);

            //初始化计时器
            minesTimer = new MinesTimer();
        }

        static class MinesTimer {
            public static boolean firstClick; //创建一个标志，只有第一次点击时，才启动计时器
            public static int seconds; // 定义一个变量用于存储秒
            Timer timer = new Timer(); //创建一个计时器
            boolean started; // 定义一个变量用于标记计时器是否已经启动
            //创建执行的任务
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    seconds++;
                    //个位数变更
                    MinesweeperStatusPanel.MinesTimerPanel.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + seconds % 10 + ".png"));
                    //十位数变更
                    MinesweeperStatusPanel.MinesTimerPanel.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + seconds / 10 % 10 + ".png"));
                    //百位数变更
                    MinesweeperStatusPanel.MinesTimerPanel.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + seconds / 100 % 10 + ".png"));
                }
            };

            public MinesTimer() {
                // 每次打开计时器时重置计时器的状态数
                seconds = 0;
                firstClick = true;
                started = false; // 初始化为false
                MinesweeperStatusPanel.MinesTimerPanel.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Number_0.png"));
                MinesweeperStatusPanel.MinesTimerPanel.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Number_0.png"));
                MinesweeperStatusPanel.MinesTimerPanel.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Number_0.png"));
                // 不要在这里启动计时器
            }

            public void start() {
                // 只有当计时器没有启动时才启动计时器
                if (!started) {
                    //设置延时为1000毫秒
                    timer.scheduleAtFixedRate(timerTask, 1000, 1000);
                    started = true; // 将started设为true
                }
            }

            public void stop() {
                // 只有当计时器已经启动时才停止计时器
                if (started) {
                    timerTask.cancel();
                    started = false; // 将started设为false
                }
            }
        }
    }

    static class FaceButton extends JButton {
        public FaceButton() {
            ImageIcon imageIcon = new ImageIcon("./src/Themes/Classic/Face_smile.png");
            setPreferredSize(new Dimension(imageIcon.getIconHeight(), imageIcon.getIconWidth())); // 设置图标大小
            setIcon(imageIcon); // 初始为微笑
            addMouseListener(new FaceButton.FaceButtonListener()); // 为笑脸按钮添加鼠标监视器
        }

        //笑脸按钮鼠标监听器
        class FaceButtonListener implements MouseListener {
            //点击时重新开始游戏
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MinesTimerPanel.minesTimer != null) {
                    MinesTimerPanel.minesTimer.stop();
                }
                MinesweeperWindows.minesweeper.dispose();
                new MinesweeperWindows().executeMinesweeper(new boolean[MinesweeperWindows.getHeight()][MinesweeperWindows.getWidth()], MinesweeperWindows.getMines());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(new ImageIcon("./src/Themes/Classic/Face_smile_clicked.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("./src/Themes/Classic/Face_smile.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
    }

    static class RemainingMinesPanel extends JPanel {
        private static int minesNumber; // 显示地雷的数量
        private final JLabel remainingMines_1; // 个位数
        private final JLabel remainingMines_2; // 十位数
        private final JLabel remainingMines_3; // 百位数

        public RemainingMinesPanel() {
            minesNumber = MinesweeperWindows.getMines();

            remainingMines_1 = new JLabel();
            remainingMines_2 = new JLabel();
            remainingMines_3 = new JLabel();

            initialLabel(remainingMines_1);
            initialLabel(remainingMines_2);
            initialLabel(remainingMines_3);
            setLabelIcon();

            setLayout(new GridLayout(1, 3, 0, 0));
            add(remainingMines_3);
            add(remainingMines_2);
            add(remainingMines_1);
        }

        //增加显示地雷数量的方法
        public void addMine() {
            minesNumber++;
            setLabelIcon();
        }

        //减少显示地雷数量的方法
        public void removeMine() {
            minesNumber--;
            setLabelIcon();
        }

        private void setLabelIcon() {
            if (minesNumber >= 0) {
                int numbers = Math.min(minesNumber, 999); // 最大值不能超过999
                remainingMines_1.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + numbers % 10 + ".png"));
                remainingMines_2.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + numbers / 10 % 10 + ".png"));
                remainingMines_3.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + numbers / 100 % 10 + ".png"));
            } else {
                int numbers = (minesNumber > -99 ? -minesNumber : 99); // 最大值不能超过99，并且算法需要传入正数
                remainingMines_1.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + numbers % 10 + ".png"));
                remainingMines_2.setIcon(new ImageIcon("./src/Themes/Classic/Number_" + numbers / 10 % 10 + ".png"));
                remainingMines_3.setIcon(new ImageIcon("./src/Themes/Classic/Number_below zero.png")); // 负数的百位数不能被操作，默认为负号
            }
        }
    }
}
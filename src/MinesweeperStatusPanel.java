import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class MinesweeperStatusPanel extends JPanel {
    static FaceButton faceButton;
    MinesTimerPanel timerPanel;

    public MinesweeperStatusPanel() {
        timerPanel = new MinesTimerPanel();

        faceButton = new FaceButton(); // 添加笑脸按钮

        setLayout(new BorderLayout()); // 设置边框布局管理器

        add(timerPanel, BorderLayout.EAST); // 计时器面板添加到状态面板右侧
        add(faceButton); // 笑脸添加到状态面板中
    }

    static class MinesTimerPanel extends JPanel{

        public static JLabel timer_1;
        public static JLabel timer_2;
        public static JLabel timer_3;

        public MinesTimerPanel() {
            //创建三个计时器面板
            timer_1 = new JLabel();
            timer_2 = new JLabel();
            timer_3 = new JLabel();

            //计时器初始图片为0
            timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
            timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
            timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));

            //计时器区域添加到计时器面板
            add(timer_1);
            add(timer_2);
            add(timer_3);
        }
        static class MinesTimer {
            Timer timer = new Timer(); //创建一个计时器
            public static boolean firstClick; //创建一个标志，只有第一次点击时，才启动计时器
            int seconds; // 定义一个变量用于存储秒

            public MinesTimer() {
                // 每次打开计时器时重置计时器的状态数
                seconds = 0;
                firstClick  = true;
                MinesweeperStatusPanel.MinesTimerPanel.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                MinesweeperStatusPanel.MinesTimerPanel.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                MinesweeperStatusPanel.MinesTimerPanel.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                //设置延时为1000毫秒
                timer.schedule(timerTask,1000,1000);
            }
            public void stop(){
                    timerTask.cancel();
            }
            //创建执行的任务
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    seconds++;
                    //个位数变更
                    MinesweeperStatusPanel.MinesTimerPanel.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_" + seconds % 10 + ".png"));
                    //十位数变更
                    MinesweeperStatusPanel.MinesTimerPanel.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_" + seconds / 10 % 10 + ".png"));
                    //百位数变更
                    MinesweeperStatusPanel.MinesTimerPanel.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_" + seconds / 100 % 10 + ".png"));
                }
            };
        }
    }

    static class FaceButton extends JButton {
        public FaceButton() {
            setIcon(new ImageIcon("./src/Themes/Classic/Face_smile.png")); // 初始为微笑
            addMouseListener(new FaceButton.FaceButtonListener()); // 为笑脸按钮添加鼠标监视器
        }

        //笑脸按钮鼠标监听器
        class FaceButtonListener implements MouseListener {
            //点击时重新开始游戏
            @Override
            public void mouseClicked(MouseEvent e) {
                if(MinesweeperWindows.minesTimer != null) {
                    MinesweeperWindows.minesTimer.stop();
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

}

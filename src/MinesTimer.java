import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

class MinesTimer {
    Timer timer = new Timer(); //创建一个计时器
    public static boolean firstClick; //创建一个标志，只有第一次点击时，才启动计时器
    int seconds; // 定义一个变量用于存储秒

    public MinesTimer() {
        // 每次打开计时器时重置计时器的状态数
        seconds = 0;
        firstClick  = true;
        MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
        MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
        MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
        //设置延时为1000毫秒
        timer.schedule(timerTask,1000,1000);
    }
    public void stop(){
        if(MinesweeperWindows.minesTimer != null) {
            timerTask.cancel();
        }
    }
    //创建执行的任务
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            seconds++;
            //个位数变更
            switch (seconds % 10) {
                default -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                case 1 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_1.png"));
                case 2 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_2.png"));
                case 3 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_3.png"));
                case 4 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_4.png"));
                case 5 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_5.png"));
                case 6 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_6.png"));
                case 7 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_7.png"));
                case 8 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_8.png"));
                case 9 -> MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_9.png"));
            }//十位数变更
            switch (seconds / 10 % 10) {
                default -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                case 1 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_1.png"));
                case 2 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_2.png"));
                case 3 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_3.png"));
                case 4 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_4.png"));
                case 5 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_5.png"));
                case 6 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_6.png"));
                case 7 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_7.png"));
                case 8 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_8.png"));
                case 9 -> MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_9.png"));
            }//百位数变更
            switch (seconds / 100 % 10) {
                default -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_0.png"));
                case 1 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_1.png"));
                case 2 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_2.png"));
                case 3 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_3.png"));
                case 4 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_4.png"));
                case 5 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_5.png"));
                case 6 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_6.png"));
                case 7 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_7.png"));
                case 8 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_8.png"));
                case 9 -> MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_9.png"));
            }
        }
    };
}


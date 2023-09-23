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
            timerTask.cancel();
    }
    //创建执行的任务
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            seconds++;
            //个位数变更
            MinesweeperWindows.timer_3.setIcon(new ImageIcon("./src/Themes/Classic/Timer_" + seconds % 10 + ".png"));
            //十位数变更
            MinesweeperWindows.timer_2.setIcon(new ImageIcon("./src/Themes/Classic/Timer_" + seconds / 10 % 10 + ".png"));
            //百位数变更
            MinesweeperWindows.timer_1.setIcon(new ImageIcon("./src/Themes/Classic/Timer_" + seconds / 100 % 10 + ".png"));
        }
    };
}


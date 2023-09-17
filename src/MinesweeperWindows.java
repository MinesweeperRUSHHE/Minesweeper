import javax.swing.*;
import java.util.*;

public class MinesweeperWindows {

    MinesweeperButton[][] minesweeperButton;

    public boolean[][] getDifficultChoice() {
        //choice [0,1,2,3]分别对应"自定义", "高级", "中级", "低级"
        //TODO:用布尔数组表示布雷数量
        return switch (new DifficultyChoice().getDifficulty()) {
            case 3 -> new boolean[9][9];
            case 2 -> new boolean[16][16];
            case 1 -> new boolean[16][30];
            //TODO:新建一个窗口选择自定义大小，未实现
            case 0 -> new boolean[114][514];
            //TODO:确认这里直接结束程序是否合适
            default -> {
                System.exit(0);
                yield new boolean[0][0];
            }
        };
    }

    //TODO:写一个布雷的算法，就是把布尔数组的false变成true，numbers表示布雷的数量
    private void placeMines(boolean[][] mines, int numbers) {
        Random r = new Random();
        while (numbers>0){
            //随机产生雷所在行、所在列
            int rr = r.nextInt(mines.length);
            int cc = r.nextInt(mines[0].length);
            //判断当前雷是否有重复，没有就安雷
            if(mines[rr][cc] != false){
                mines[rr][cc] = false;
                numbers = numbers - 1;
            }
            for (int i = 0; i < mines.length; i++) {
                for (int j = 0; j < mines[i].length; j++) {
                    System.out.print(mines[i][j]+" ");
                }
                System.out.println();
            }
        }
    }
    static class DifficultyChoice {

        //存储用户的选择
        private int choice;
        //存储对话框的选项按钮
        private final Object[] options = {"自定义", "高级", "中级", "低级"};//按键是从右到左
        public int getDifficulty() {
            //调用showOptionDialog方法，创建一个选项对话框
            choice = JOptionPane.showOptionDialog(null, "请选择游戏难度", "选项对话框", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
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

    static class MainWindows {
        public MainWindows() {
        //TODO:扫雷主界面，需要传入一个布尔数组
        }
    }
}


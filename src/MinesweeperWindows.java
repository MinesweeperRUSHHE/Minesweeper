import javax.swing.*;
import java.awt.*;

public class MinesweeperWindows {

    MinesweeperButton[][] minesweeperButton;

    public void executeDifficultChoice() {
        //choice [0,1,2,3]分别对应"低级", "中级", "高级", "自定义"
        //TODO:用布尔数组表示布雷数量
        switch (new DifficultyChoice().getDifficulty()) {
            case 0 -> executeMinesweeper(new boolean[9][9]);
            case 1 -> executeMinesweeper(new boolean[16][16]);//雷数未知
            case 2 -> executeMinesweeper(new boolean[16][30]);//雷数未知
            //TODO:新建一个窗口选择自定义大小，未实现
            case 3 -> System.out.println("未完成");
            //TODO:确认这里直接结束程序是否合适
            default -> {
                System.exit(0);
            }
        }
    }

    public void executeMinesweeper(boolean[][] mines) {
        MainWindows mainWindows = new MainWindows(mines);

        mainWindows.executeMinesweeper();
    }

    private void placeMines(boolean[][] mines, int numbers) {
        //TODO:写一个布雷的算法，就是把布尔数组的false变成true，numbers表示布雷的数量
    }
    static class DifficultyChoice {

        //存储用户的选择
        private int choice;
        //存储对话框的选项按钮
        private final Object[] options = {"低级", "中级", "高级", "自定义"};
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
        private boolean[][] mines;
        private int rows;
        private int columns;
        public MainWindows(boolean[][] mines) {
        //TODO:扫雷主界面，需要传入一个布尔数组
            this.mines = mines;
        }

        public void executeMinesweeper() {
            setMatrix(mines);
            JFrame frame = new JFrame("扫雷");
            frame.setLayout(new BorderLayout()); // 设置边框布局管理器
            frame.add(new JButton("重新开始"), BorderLayout.NORTH); // 添加一个按钮到北区域
            JPanel panel = new JPanel(); // 创建一个面板
            panel.setLayout(new GridLayout(rows, columns)); // 设置网格布局管理器，3行3列
            for (int i = 1; i <= rows * columns; i++) { // 循环添加九个按钮到面板中
                panel.add(new JButton(String.valueOf(i)));
            }
            frame.add(panel, BorderLayout.CENTER); // 将面板添加到中心区域
            frame.pack(); // 设置自动窗口大小
            frame.setVisible(true); // 设置窗口可见
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
        }

        public void setMatrix(boolean[][] matrix) {
            rows = matrix.length;
            columns = matrix[0].length;
        }
    }
}


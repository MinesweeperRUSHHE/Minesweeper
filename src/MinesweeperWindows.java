import javax.swing.*;
import java.awt.*;

public class MinesweeperWindows {

    public void executeDifficultChoice() {
        /*
        choice [0,1,2,3]分别对应"低级", "中级", "高级", "自定义"
        初级为10个，中级为40个，高级为99个
        TODO:用布尔数组表示布雷数量
         */
        switch (new DifficultyChoice().getDifficulty()) {
            case 0 -> executeMinesweeper(new boolean[9][9], 10);
            case 1 -> executeMinesweeper(new boolean[16][16], 40);
            case 2 -> executeMinesweeper(new boolean[16][30], 90);
            //TODO:新建一个窗口选择自定义大小，未实现
            case 3 -> System.out.println("此功能未完成");
            //TODO:确认这里直接结束程序是否合适
            default -> System.exit(0);
        }
    }

    public void executeMinesweeper(boolean[][] mines, int numbers) {
        placeMines(mines, numbers);//在此处使用随机布雷方法
        MainWindows mainWindows = new MainWindows(mines);

        mainWindows.executeMinesweeper();
    }

    private void placeMines(boolean[][] mines, int numbers) {
        //TODO:写一个布雷的算法，就是把布尔数组的false变成true，numbers表示布雷的数量
    }

    static class DifficultyChoice {

        //存储对话框的选项按钮
        private final Object[] options = {"低级", "中级", "高级", "自定义"};
        //存储用户的选择
        private int choice;

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
        private final boolean[][] mines;
        private final MinesweeperButton[][] minesweeperButton;
        private int rows;
        private int columns;

        public MainWindows(boolean[][] mines) {
            //TODO:扫雷主界面，需要传入一个布尔数组
            this.mines = mines;
            setMatrix(mines);
            minesweeperButton = new MinesweeperButton[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    minesweeperButton[i][j] = new MinesweeperButton(mines[i][j]);
                }
            }
        }

        public void executeMinesweeper() {
            JFrame frame = new JFrame("扫雷");
            frame.setLayout(new BorderLayout()); // 设置边框布局管理器
            frame.add(new JButton("重新开始"), BorderLayout.NORTH); // 添加一个按钮到北区域
            JPanel panel = new JPanel(); // 创建一个面板
            panel.setLayout(new GridLayout(rows, columns)); // 设置网格布局管理器，3行3列
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    panel.add(minesweeperButton[i][j]);
                }
            }
            frame.add(panel, BorderLayout.CENTER); // 将面板添加到中心区域
            frame.pack(); // 设置自动窗口大小
            frame.setVisible(true); // 设置窗口可见
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
        }

        public void setMatrix(boolean[][] matrix) {
            //一个记录地雷矩阵长度的方法
            rows = matrix.length;
            columns = matrix[0].length;
        }
    }
}


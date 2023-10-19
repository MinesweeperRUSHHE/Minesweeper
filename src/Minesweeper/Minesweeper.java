package Minesweeper;

import javax.swing.*;
import java.awt.*;

public class Minesweeper {
    static MinesweeperWindows mw;
    static int rows;
    static int columns;
    static int minesNumber;
    static int difficulty;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DifficultyChoice();
            mw = new MinesweeperWindows(rows, columns, difficulty, minesNumber);
        });
    }


    static class DifficultyChoice {
        public DifficultyChoice() {
            /*
            choice [0,1,2,3]分别对应"低级", "中级", "高级", "自定义"
            初级为10个，中级为40个，高级为99个
            */
            switch (getDifficulty()) {
                case 0 -> {
                    difficulty = 0;
                    rows = 9;
                    columns = 9;
                    minesNumber = 10;
                }
                case 1 -> {
                    difficulty = 1;
                    rows = 16;
                    columns = 16;
                    minesNumber = 40;
                }
                case 2 -> {
                    difficulty = 2;
                    rows = 16;
                    columns = 30;
                    minesNumber = 99;
                }
                case 3 -> {
                    difficulty = 3;
                    new CustomDifficulty();
                }
                default -> System.exit(0);
            }
        }

        private int getDifficulty() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 指定其布局为BoxLayout，并设置其方向为垂直
            //创建一个ButtonGroup对象，用于将JRadioButtons分组
            ButtonGroup group = new ButtonGroup();
            String[] options = {"初级", "中级", "高级", "自定义"};
            JRadioButton[] buttons = new JRadioButton[options.length];
            //遍历数组，为每个选项创建一个JRadioButton，并添加到panel和group中
            for (int i = 0; i < options.length; i++) {
                buttons[i] = new JRadioButton(options[i]);
                panel.add(buttons[i]);
                group.add(buttons[i]);
            }
            buttons[0].setSelected(true);
            //存储用户的选择
            int choice = JOptionPane.showConfirmDialog(null, panel, "请选择游戏难度", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            //根据返回值判断用户的选择
            if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                //用户取消了对话框或关闭了对话框
                JOptionPane.showMessageDialog(null, "您没有选择难度，程序将自动关闭");
                System.exit(0);
            } else {
                //用户点击了确定按钮
                for (int i = 0; i < buttons.length; i++) {
                    if (buttons[i].isSelected()) {
                        choice = i;
                        break;
                    }
                }
                return choice;//对应的上面的选项
            }
            return -1;
        }

        static class CustomDifficulty {
            public CustomDifficulty() {
                //创建一个JPanel对象，用于放置三个JTextField
                JPanel panel = new JPanel(new GridLayout(3, 2));
                String[] labels = {"请输入行数", "请输入列数", "请输入雷数"};
                JTextField[] fields = new JTextField[labels.length];
                for (int i = 0; i < labels.length; i++) {
                    panel.add(new JLabel(labels[i]));
                    fields[i] = new JTextField(10);
                    panel.add(fields[i]);
                }
                //存储用户的选择
                int choice = JOptionPane.showConfirmDialog(null, panel, "自定义难度", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                //根据返回值判断用户的选择
                if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                    new DifficultyChoice();
                } else {
                    //用户点击了确定按钮
                    //获取用户输入的数据，并转换为整数
                    int rows = Integer.parseInt(fields[0].getText());
                    int columns = Integer.parseInt(fields[1].getText());
                    int minesNumber = Integer.parseInt(fields[2].getText());
                    //检查用户输入的数据是否符合要求
                    if (checkCustomNumbers(rows) && checkCustomNumbers(columns) && checkCustomNumbers(minesNumber)) {
                        //数据符合要求，设置相应的属性值
                        Minesweeper.rows = rows;
                        Minesweeper.columns = columns;
                        Minesweeper.minesNumber = minesNumber;
                        Minesweeper.difficulty = 3;
                        JOptionPane.showMessageDialog(null, "您选择了自定义难度");//暂时是给你一个窗口提示难度
                    } else {
                        //数据不符合要求，发出警告并要求重新输入
                        JOptionPane.showMessageDialog(null, "请输入9~30的整数", "WARNING", JOptionPane.WARNING_MESSAGE);
                        new CustomDifficulty(); // 重新输入
                    }
                }
            }

            private boolean checkCustomNumbers(int number) {
                //判断整数的大小是否符合要求
                return 8 < number && number < 31;
            }
        }
    }
}
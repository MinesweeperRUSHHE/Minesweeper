package Minesweeper;

import javax.swing.*;

public class Minesweeper {
    static MinesweeperWindows mw;
    static int rows;
    static int columns;
    static int minesNumber;
    static int difficulty;

    public static void main(String[] args) {
        new DifficultyChoice().executeDifficultChoice();
        mw = new MinesweeperWindows(rows, columns, difficulty, minesNumber);
    }

    static class DifficultyChoice {

        //存储对话框的选项按钮
        private final Object[] options = {"低级", "中级", "高级", "自定义"};

        public void executeDifficultChoice() {
        /*
        choice [0,1,2,3]分别对应"低级", "中级", "高级", "自定义"
        初级为10个，中级为40个，高级为99个
         */
            switch (new Minesweeper.DifficultyChoice().getDifficulty()) {
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

        public int getDifficulty() {
            //调用showOptionDialog方法，创建一个选项对话框
            //存储用户的选择
            int choice = JOptionPane.showOptionDialog(null, "请选择游戏难度", "选项对话框", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            //根据返回值判断用户的选择
            if (choice == -1) {
                //用户关闭了对话框
                JOptionPane.showMessageDialog(null, "您没有选择难度，程序将自动关闭");
                return -1;
            } else {
                //choice [0,1,2,3]分别对应"自定义", "高级", "中级", "低级"
                JOptionPane.showMessageDialog(null, "您选择了" + options[choice]);//暂时是给你一个窗口提示难度
                Minesweeper.difficulty = choice;
                return choice;//对应的上面的选项
            }
        }

        static class CustomDifficulty {
            public CustomDifficulty() {
                //调用showInputDialog方法获取用户输入的数据
                String rowString = JOptionPane.showInputDialog(null, "请输入行数");
                while (checkCustomNumbers(rowString)) {
                    rowString = reEnterNumbers();
                }//调用Integer方法把数据转换为整数
                rows = Integer.parseInt(rowString);

                String columnString = JOptionPane.showInputDialog(null, "请输入列数");
                while (checkCustomNumbers(columnString)) {
                    columnString = reEnterNumbers();
                }
                columns = Integer.parseInt(columnString);

                String mineString = JOptionPane.showInputDialog(null, "请输入雷数");
                while (checkCustomNumbers(mineString)) {
                    mineString = reEnterNumbers();
                }
                minesNumber = Integer.parseInt(mineString);
            }

            private boolean checkCustomNumbers(String number) {
                //检查用户操作和判断输入数据
                if (number == null) {
                    //用户关闭或取消了对话框
                    JOptionPane.showMessageDialog(null, "你取消了自定义");
                    //TODO:重新回到难度选择
                    System.exit(0);
                    return false;
                } else {
                    try {
                        //判断输入的是否为整数
                        Integer.parseInt(number);
                        //判断整数的大小是否符合要求
                        return 8 > Integer.parseInt(number) || Integer.parseInt(number) > 30;
                    } catch (Exception e) {
                        //不是整数
                        return true;
                    }
                }
            }

            private String reEnterNumbers() {
                //如果输入的不是符合要求的整数，发出警告并要求重新输入
                JOptionPane.showMessageDialog(null, "请输入9~30的整数", "WARNING", JOptionPane.WARNING_MESSAGE);
                return JOptionPane.showInputDialog(null, "请重新输入");
            }
        }
    }
}
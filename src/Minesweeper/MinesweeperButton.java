package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MinesweeperButton extends JButton {
    private final int status; // -1是雷，0-8为附近的雷数
    private final int xLocation; // 按钮的x坐标
    private final int yLocation; // 按钮的y坐标
    public boolean leftClickable = true; // 是否可被左键点击
    public boolean rightClickable = true; // 是否可被右键点击
    private boolean canFlag = true; // 是否可插旗
    private boolean belongToFlag = false; // 是否属于旗子
    private boolean belongToQuestion = false; // 是否属于问号
    private boolean bothPressed = false; // 按钮是否被左右键点击
    private boolean minesVisible = false;//false为不可见，true为可见
    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // 定义一个掩码，表示同时按下鼠标左键和右键
            int action = MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK;
            // 判断鼠标事件的修饰符是否与掩码相同
            if ((e.getModifiersEx() & action) == action) {
                if (minesVisible && status > 0) {
                    MinesweeperWindows.executeDoubleClick(xLocation, yLocation);
                }
                bothPressed = true; // 同时按下了左键和右键，不触发MouseReleased监听器
            }
            if (leftClickable || rightClickable) {
                MinesweeperStatusPanel.faceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_astonished.png")); //按下时为疑惑
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (MinesweeperStatusPanel.MinesTimerPanel.MinesTimer.firstClick) {
                MinesweeperStatusPanel.MinesTimerPanel.minesTimer.start(); //启动计时器
                MinesweeperStatusPanel.MinesTimerPanel.MinesTimer.firstClick = false;
            }
            //表示左右键同时按下，不触发操作
            if (bothPressed) {
                bothPressed = false;
                return;
            }
            //左键点击
            if (e.getButton() == MouseEvent.BUTTON1 && leftClickable) {
                clickButton();
            }
            //右键点击
            else if (e.getButton() == MouseEvent.BUTTON3 && rightClickable) {
                if (canFlag) { // 插旗子分支
                    setIcon(new ImageIcon("./src/Themes/Classic/Button_flag.png"));
                    canFlag = false;
                    belongToFlag = true;
                    leftClickable = false;
                    MinesweeperStatusPanel.remainingMinesPanel.removeMine();
                } else if (belongToFlag) { // 设置问号分支
                    setIcon(new ImageIcon("./src/Themes/Classic/Button_question.png"));
                    canFlag = false;
                    belongToFlag = false;
                    belongToQuestion = true;
                    leftClickable = true;
                    MinesweeperStatusPanel.remainingMinesPanel.addMine();
                } else if (belongToQuestion) { // 还原分支
                    setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
                    belongToQuestion = false;
                    canFlag = true;
                }
            }
            if (leftClickable || rightClickable) {
                //当鼠标抬起是变为笑脸
                MinesweeperStatusPanel.faceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile.png"));
            }
        }
    };

    public MinesweeperButton(int rows, int columns, int status) {
        //设置按钮的尺寸及图标
        ImageIcon imageIcon = new ImageIcon("./src/Themes/Classic/Button.png");
        setPreferredSize(new Dimension(imageIcon.getIconHeight(), imageIcon.getIconWidth()));
        setIcon(imageIcon);
        //传入按钮的xy坐标，记录按钮位置
        this.xLocation = columns;
        this.yLocation = rows;
        this.status = status;
        //添加按钮的点击事件，可以根据自己的逻辑来实现
        addMouseListener(mouseAdapter);
    }

    public boolean isBelongToFlag() {
        return belongToFlag;
    }

    public void setBelongToFlag(boolean belongToFlag) {
        this.belongToFlag = belongToFlag;
    }

    public boolean isMinesVisible() {
        return minesVisible;
    }

    public void setMinesVisible(boolean minesVisible) {
        this.minesVisible = minesVisible;
    }

    public void setCanFlag(boolean canFlag) {
        this.canFlag = canFlag;
    }

    public int getStatus() {
        return status;
    }

    public void setLeftClickable(boolean status) {
        this.leftClickable = status;
    }

    public void setRightClickable(boolean status) {
        this.rightClickable = status;
    }

    //设置地雷按钮图标，并添加一些规则
    public void setButtonIcon() {
        canFlag = false;
        if (status == -1) {
            setIcon(new ImageIcon("./src/Themes/Classic/Button_Mine.png"));
        } else {
            setIcon(new ImageIcon("./src/Themes/Classic/Button_" + status + ".png"));
        }
    }

    public void clickButton() {
        setMinesVisible(true);
        switch (status) {
            case -1 -> {
                //点击后设置为爆炸的雷的图标，并调用方法暂停计时器及引爆全部地雷
                canFlag = false;
                leftClickable = false;
                rightClickable = false;
                MinesweeperWindows.detonateAllMines();
                setIcon(new ImageIcon("./src/Themes/Classic/Button_Mine_exploded.png"));
            }
            //空且附近地雷为0
            case 0 -> {
                setButtonIcon();
                MinesweeperWindows.openAllCell(xLocation, yLocation);
            }
            default -> setButtonIcon();
        }
        try {
            MinesweeperWindows.successOrNot();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

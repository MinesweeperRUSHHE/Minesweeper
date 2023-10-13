import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MinesweeperButton extends JButton {
    private final int status; // -1是雷，0-8为附近的雷数
    private final int xLocation;
    private final int yLocation;
    public boolean leftClickable = true;
    public boolean rightClickable = true;
    private boolean canFlag = true;

    public void setBelongToFlag(boolean belongToFlag) {
        this.belongToFlag = belongToFlag;
    }

    private boolean belongToFlag = false;
    private boolean belongToQuestion = false;
    private boolean bothPressed = false;
    private boolean minesVisible = false;//false为不可见，true为可见
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // 定义一个掩码，表示同时按下鼠标左键和右键
            int action = MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK;
            // 判断鼠标事件的修饰符是否与掩码相同
            if ((e.getModifiersEx() & action) == action) {
                // 如果相同，就执行你想要的操作
                System.out.println("同时按下了左键和右键");
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

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };
    public MinesweeperButton(int xLocation, int yLocation, boolean[][] status) {
        //设置按钮的尺寸及图标
        ImageIcon imageIcon = new ImageIcon("./src/Themes/Classic/Button.png");
        setPreferredSize(new Dimension(imageIcon.getIconHeight(), imageIcon.getIconWidth()));
        setIcon(imageIcon);
        //传入按钮的xy坐标，记录按钮位置
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        if (status[yLocation][xLocation]) {
            this.status = -1;
        } else {
            this.status = calculateNearbyMines(status);
        }
        //添加按钮的点击事件，可以根据自己的逻辑来实现
        addMouseListener(mouseListener);
    }

    public boolean isBelongToFlag() {
        return belongToFlag;
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

    //计算周围雷的数量的方法
    private int calculateNearbyMines(boolean[][] mines) {
        //计数器
        int numbers = 0;
        //计算九宫格内的地雷数量
        for (int i = Math.max(0, yLocation - 1); i <= Math.min(mines.length - 1, yLocation + 1); i++) {
            for (int j = Math.max(0, xLocation - 1); j <= Math.min(mines[0].length - 1, xLocation + 1); j++) {
                if (mines[i][j]) {
                    numbers++;
                }
            }
        }
        return numbers;
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
//                        UIManager.put("OptionPane.buttonFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
//                        UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
//                        Component mainFrame = null;
//                        JOptionPane.showMessageDialog(mainFrame, "建议去玩玩原神放松一下");
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

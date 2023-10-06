import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperButton extends JButton {
    private final int status; // -1是雷，0-8为附近的雷数
    private final int xLocation;
    private final int yLocation;
    private boolean leftClickable = true;
    private boolean rightClickable = true;
    private boolean canFlag = true;
    private boolean belongToFlag = false;
    private boolean belongToQuestion = false;
    private boolean minesVisible = false;//false为不可见，true为可见
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
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
            //左键点击
            if (e.getButton() == MouseEvent.BUTTON1 && leftClickable) {
                switch (status) {
                    case -1 -> {
                        //点击后设置为爆炸的雷的图标，并调用方法暂停计时器及引爆全部地雷
                        canFlag = false;
                        leftClickable = false;
                        rightClickable = false;
                        setIcon(new ImageIcon("./src/Themes/Classic/Button_Mine_exploded.png"));
                        MinesweeperStatusPanel.MinesTimerPanel.minesTimer.stop();
                        Minesweeper.getMinesweeperWindows().detonateAllMines(xLocation, yLocation);
                        UIManager.put("OptionPane.buttonFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
                        UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new Font("宋体", Font.ITALIC, 13)));
                        JOptionPane.showMessageDialog(null, "建议去玩玩原神放松一下");
                    }
                    //空且附近地雷为0
                    case 0 -> {
                        setButtonIcon();
                        Minesweeper.getMinesweeperWindows().openAllCell(xLocation, yLocation);
                    }
                    default -> {
                        setButtonIcon();
                        belongToQuestion = false;
                    }
                }
                Minesweeper.getMinesweeperWindows().successOrNot();
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
        minesVisible = true;
        ImageIcon imageIcon = new ImageIcon("./src/Themes/Classic/Button_" + status + ".png");
        setIcon(imageIcon);
    }
}
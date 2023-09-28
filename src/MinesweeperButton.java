import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperButton extends JButton {
    private final int status; // -1是雷，0-8为附近的雷数
    private final int xLocation;
    private final int yLocation;
    public boolean leftClickable = true;
    public boolean rightClickable = true;
    private boolean canFlag = true;
    private boolean belongToFlag = false;
    private boolean belongToQuestion = false;
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
            if(MinesweeperStatusPanel.MinesTimerPanel.MinesTimer.firstClick){
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
                        MinesweeperWindows.detonateAllMines(xLocation, yLocation);
                    }
                    case 0 -> {
                        //空且附近地雷为0
                        canFlag = false;
                        setIcon(new ImageIcon("./src/Themes/Classic/Button_0.png"));
                        //TODO:需要一个翻开一片空格子的方法
                        MinesweeperWindows.openAllCell(xLocation, yLocation);
                    }
                    default -> {
                        canFlag = false;
                        setIcon(new ImageIcon("./src/Themes/Classic/Button_" + status + ".png"));
                    }
                }
                //TODO:在此调用检查输赢的方法
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
    private boolean minesVisible = false;//false为不可见，true为可见

    public MinesweeperButton(int xLocation, int yLocation, boolean[][] status) {
        //调用JButton类的无参构造方法，创建一个没有文本或图标的按钮
        super();
        //默认为未点击图标
        setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
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

    public void setMinesVisible(boolean minesVisible) {
        this.minesVisible = minesVisible;
    }

    public boolean isMinesVisible() {
        return minesVisible;
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
}

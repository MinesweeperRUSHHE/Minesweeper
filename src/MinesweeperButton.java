import javax.swing.*;
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

    /*TODO:
    添加一个方法，每次点击鼠标时调用该方法改变笑脸状态
    添加一个表示游戏状态的标志，初始值为0
    根据输赢改变Face的状态
    赢：flag = 1
    FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile_sunglasses.png"));
    正常：flag = 0
    FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile.png"));
    输：flag = -1
    FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile_cross-out eyes.png"));
    */
    /*TODO:
    添加一个胜利条件的方法
    当剩余雷数 = 0 且 所有格子点开时胜利
    当只剩一个格子且这个格子是雷时胜利
     */
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(MinesTimer.firstClick){
                MinesweeperWindows.minesTimer = new MinesTimer(); //启动计时器
                MinesTimer.firstClick = false;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (leftClickable || rightClickable) {
                MinesweeperWindows.FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_astonished.png")); //按下时为疑惑
            }
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            //左键点击
            if (e.getButton() == MouseEvent.BUTTON1 && leftClickable) {
                switch (status) {
                    case -1 -> {
                        //点击后设置为爆炸的雷的图标，并调用方法暂停计时器及引爆全部地雷
                        canFlag = false;
                        leftClickable = false;
                        rightClickable = false;
                        setIcon(new ImageIcon("./src/Themes/Classic/Button_Mine_exploded.png"));
                        MinesweeperWindows.minesTimer.stop();
                        MinesweeperWindows.detonateAllMines(xLocation, yLocation);
                    }
                    case 0 -> {
                        //空且附近地雷为0
                        canFlag = false;
                        setIcon(new ImageIcon("./src/Themes/Classic/Button_0.png"));
                        //TODO:需要一个翻开一片空格子的方法
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
                //旗子图标
                if (canFlag) {
                    setIcon(new ImageIcon("./src/Themes/Classic/Button_flag.png"));
                    canFlag = false;
                    belongToFlag = true;
                    leftClickable = false;
                } else if (belongToFlag) {
                    setIcon(new ImageIcon("./src/Themes/Classic/Button_question.png"));
                    canFlag = false;
                    belongToFlag = false;
                    belongToQuestion = true;
                    leftClickable = false;
                } else if (belongToQuestion) {
                    setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
                    belongToQuestion = false;
                    canFlag = true;
                    leftClickable = true;
                }
                /*TODO:
                添加一个表示有旗子的变量
                插旗子时剩余雷数 - 1
                再次右键点击旗子将取消插旗
                */
            }
            if (leftClickable || rightClickable) {
                //当鼠标抬起是变为笑脸
                MinesweeperWindows.FaceButton.setIcon(new ImageIcon("./src/Themes/Classic/Face_smile.png"));
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

    //计算周围雷的数量的方法
    private int calculateNearbyMines(boolean[][] mines) {
        //计数器
        int numbers = 0;
        //计算九宫格内的地雷数量
        for (int i = yLocation - 1; i <= yLocation + 1; i++) {
            for (int j = xLocation - 1; j <= xLocation + 1; j++) {
                if (!((i < 0 || i >= mines.length) || (j < 0 || j >= mines[0].length)) && mines[i][j]) {
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
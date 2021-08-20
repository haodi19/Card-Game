import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardTable extends JComponent {
    private static List<Card> cardList;
    private static JButton next;
    private static JButton quit;
    private static JButton restart;
    private static JLabel pointsShower1;
    private static JLabel pointsShower2;
    private static Player computer;
    private static Player player;
    private static Random random;
    private static final int GAME_GO_ON = 0;
    private static final int GAME_OVER = 1;
    private static final int GAME_SUCCESS = 2;
    private static final int GAME_DRAW = 3;
    private static int state = GAME_GO_ON;

    public CardTable() {
        initTable();
    }

    private void initTable() {
        setLayout(null);
        setSize(800, 800);
        initCardList();
        initPlayers();
        initButtons();
        initPointsLabels();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //更新状态
        if (state == GAME_GO_ON) {
            if (player.isExploded()) {
                state = GAME_OVER;
            } else if (computer.isExploded()) {
                state = GAME_SUCCESS;
            } else if (computer.calculateMaxPoints() >= 17 && !computer.isExploded()) {
                computer.setAddingCards(false);
            }
            if (!computer.isAddingCards() && !player.isAddingCards()) {
                if (computer.calculateMaxPoints() > player.calculateMaxPoints()) {
                    state = GAME_OVER;
                } else if (computer.calculateMaxPoints() < player.calculateMaxPoints()) {
                    state = GAME_SUCCESS;
                } else if (computer.getCardNums() > player.getCardNums()) {
                    state = GAME_OVER;
                } else if (computer.getCardNums() < player.getCardNums()) {
                    state = GAME_SUCCESS;
                } else {
                    state = GAME_DRAW;
                }
            }
        }
        //绘制棋局
        for (int i = 0; i < computer.getCards().size(); i++) {
            Card card = computer.getCards().get(i);
            if (i == 0) {
                card.setShown(state != GAME_GO_ON);
            }
            card.paintCard(g, 300 + i * 30, 100);
        }
        for (int i = 0; i < player.getCards().size(); i++) {
            Card card = player.getCards().get(i);
            card.paintCard(g, 300 + i * 30, 500);
        }

        //游戏结束
        if (state == GAME_SUCCESS) {
            next.setVisible(false);
            quit.setVisible(false);
            g.setColor(Color.RED);
            g.setFont(new Font("宋体", Font.BOLD, 60));
            g.drawString("获胜！！", 300, 380);
            pointsShower2.setText("最终分数:" + player.calculateMaxPoints());
            pointsShower1.setText("最终分数:" + computer.calculateMaxPoints());
            addRestartButton();
        } else if (state == GAME_OVER) {
            next.setVisible(false);
            quit.setVisible(false);
            g.setColor(Color.BLACK);
            g.setFont(new Font("宋体", Font.BOLD, 60));
            g.drawString("失败！！", 300, 380);
            pointsShower2.setText("最终分数:" + player.calculateMaxPoints());
            pointsShower1.setText("最终分数:" + computer.calculateMaxPoints());
            addRestartButton();
        } else if (state == GAME_DRAW) {
            next.setVisible(false);
            quit.setVisible(false);
            g.setColor(Color.orange);
            g.setFont(new Font("宋体", Font.BOLD, 60));
            g.drawString("平局！！", 300, 380);
            pointsShower2.setText("最终分数:" + player.calculateMaxPoints());
            pointsShower1.setText("最终分数:" + computer.calculateMaxPoints());
            addRestartButton();
        } else {
            pointsShower2.setText("<html>得分下限:" + player.getPoints() + "<br>得分上限:" + player.calculateMaxPoints() + "</html>");
        }

    }

    //随机抽一张牌
    private Card getACard() {
        if (cardList != null && cardList.size() > 0) {
            return cardList.remove(random.nextInt(cardList.size()));
        }
        return null;
    }

    private void addRestartButton() {
        restart = new JButton();
        restart.setSize(120, 60);
        restart.setLocation(310, 410);
        restart.setText("再来一局");
        restart.setFont(new Font("宋体", Font.BOLD, 20));
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = GAME_GO_ON;
                removeAll();
                initTable();
                repaint();
            }
        });
        add(restart);
    }


    //初始化套牌
    private void initCardList() {
        cardList = new ArrayList<>();
        for (int j = 1; j < 14; j++) {
            cardList.add(new Card(Math.min(j, 10), Pattern.HEART, new ImageIcon("src/img/heart" + j + ".jpg").getImage()));
        }
        for (int j = 1; j < 14; j++) {
            cardList.add(new Card(Math.min(j, 10), Pattern.SPADE, new ImageIcon("src/img/spade" + j + ".jpg").getImage()));
        }
        for (int j = 1; j < 14; j++) {
            cardList.add(new Card(Math.min(j, 10), Pattern.DIAMOND, new ImageIcon("src/img/diamond" + j + ".jpg").getImage()));
        }
        for (int j = 1; j < 14; j++) {
            cardList.add(new Card(Math.min(j, 10), Pattern.CLUB, new ImageIcon("src/img/club" + j + ".jpg").getImage()));
        }
        random = new Random();
    }

    //初始化玩家
    private void initPlayers() {
        computer = new Player(getACard(), getACard());
        player = new Player(getACard(), getACard());
    }

    //初始化分数面板
    private void initPointsLabels() {
        pointsShower1 = new JLabel();
        pointsShower1.setSize(250, 300);
        pointsShower1.setLocation(80, 20);
        pointsShower1.setFont(new Font("宋体", Font.BOLD, 26));
        pointsShower1.setForeground(Color.BLACK);
        add(pointsShower1);
        pointsShower2 = new JLabel("<html>得分下限:" + player.getPoints() + "<br>得分上限:" + player.calculateMaxPoints() + "</html>");
        pointsShower2.setSize(250, 300);
        pointsShower2.setLocation(80, 420);
        pointsShower2.setFont(new Font("宋体", Font.BOLD, 26));
        pointsShower2.setForeground(Color.MAGENTA);
        add(pointsShower2);
    }

    //初始化继续要牌和停止要牌按钮
    private void initButtons() {
        next = new JButton();
        next.setSize(120, 60);
        next.setLocation(380, 330);
        next.setText("继续要牌");
        next.setFont(new Font("宋体", Font.BOLD, 20));
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state == GAME_GO_ON) {
                    player.addCard(getACard());
                    repaint();
                }
                if (!player.isExploded()) {
                    if (computer.calculateMaxPoints() < 17) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(200);
                                computer.addCard(getACard());
                                repaint();
                            } catch (InterruptedException e1) { }
                        }).start();
                    }
                }
            }
        });
        add(next);
        quit = new JButton();
        quit.setSize(120, 60);
        quit.setLocation(220, 330);
        quit.setText("不要了");
        quit.setFont(new Font("宋体", Font.BOLD, 20));
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setAddingCards(false);
                if (computer.isAddingCards()) {
                    new Thread(() -> {
                        while (computer.calculateMaxPoints() < 17) {
                            try {
                                Thread.sleep(200);
                                computer.addCard(getACard());
                                repaint();
                            } catch (InterruptedException e1) { }
                        }
                    }).start();
                }else {
                    repaint();
                }
            }
        });
        add(quit);
    }

}

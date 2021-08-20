import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private int points;
    private List<Card> cards;
    private int cardNums;
    private boolean isAddingCards = true;


    public int getCardNums() {
        return cardNums;
    }

    public boolean isAddingCards() {
        return isAddingCards;
    }

    public void setAddingCards(boolean addingCards) {
        isAddingCards = addingCards;
    }

    public Player(Card card1, Card card2) {
        this.cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        points = card1.getFigure() + card2.getFigure();
        cardNums=2;
    }

    public int getPoints() {
        return points;
    }

    public List<Card> getCards() {
        return cards;
    }

    //牌的点数是否超过21
    public boolean isExploded() {
        return points > 21;
    }

    //继续要牌
    public void addCard(Card newCard) {
        cards.add(newCard);
        points += newCard.getFigure();
        cardNums++;
    }

    public int calculateMaxPoints() {
        int A_Nums = 0;
        int maxPoints = points;
        for (Card card : cards) {
            if (card.getFigure() == 1) {
                A_Nums++;
            }
        }
        for (int i = 0; i < A_Nums; i++) {
            if (maxPoints + 10 <= 21) {
                maxPoints += 10;
            } else {
                break;
            }
        }
        return maxPoints;
    }
}

import javax.swing.*;
import java.awt.*;

public class Card {
    private static final Image backCardImg = new ImageIcon("src/img/back.jpg").getImage();
    private int figure;
    private Pattern pattern;
    private Image image;
    private boolean isShown;

    public Card() {
    }

    public Card(int figure, Pattern pattern, Image image) {
        this.figure = figure;
        this.pattern = pattern;
        this.image = image;
        isShown = true;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Image getImage() {
        return image;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    //绘制扑克牌：当isShown为true时绘制牌的正面，否则绘制牌的反面
    public void paintCard(Graphics g, int x, int y) {
        if (isShown) {
            g.drawImage(image, x, y, null);
        } else {
            g.drawImage(backCardImg, x, y, null);
        }
    }
}

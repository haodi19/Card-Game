import javax.swing.*;

public class MainFrame extends JFrame {
    private static CardTable cardTable=new CardTable();
    public MainFrame(){
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        add(cardTable);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
        });
    }


}

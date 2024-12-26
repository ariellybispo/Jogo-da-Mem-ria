import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class jogodaMemoria {

    private JFrame frame;
    private JPanel gridPanel;
    private ArrayList<Color> cardColors;
    private JButton[] buttons;
    private Color firstColor = null;
    private JButton firstButton = null;
    private int revealedPairs = 0;

    public jogodaMemoria() {
        initializeGame();
    }

    private void initializeGame() {
        frame = new JFrame("Jogo da Memória");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        gridPanel = new JPanel(new GridLayout(4, 4)); 
        frame.add(gridPanel);

        cardColors = generateCardColors();
        buttons = new JButton[16];

        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(Color.GRAY);
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(new CardClickListener(i));
            gridPanel.add(buttons[i]);
        }

        frame.setVisible(true);
    }

    private ArrayList<Color> generateCardColors() {
        ArrayList<Color> colors = new ArrayList<>();
        Color[] baseColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA};
        for (Color color : baseColors) {
            colors.add(color);
            colors.add(color);
        }
        Collections.shuffle(colors);
        return colors;
    }

    private void checkMatch(int index) {
        Color currentColor = cardColors.get(index);
        JButton currentButton = buttons[index];

        currentButton.setBackground(currentColor);

        if (firstColor == null) {
            firstColor = currentColor;
            firstButton = currentButton;
            firstButton.setEnabled(false);
        } else {
            if (firstColor.equals(currentColor)) {
                revealedPairs++;
                firstColor = null;
                firstButton = null;

                if (revealedPairs == 8) {
                    JOptionPane.showMessageDialog(frame, "Parabéns! Você venceu o jogo!");
                }
            } else {
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        firstButton.setBackground(Color.GRAY);
                        firstButton.setEnabled(true);
                        currentButton.setBackground(Color.GRAY);
                        currentButton.setEnabled(true);
                        firstColor = null;
                        firstButton = null;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private class CardClickListener implements ActionListener {
        private int index;

        public CardClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            checkMatch(index);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(jogodaMemoria::new);
    }
}

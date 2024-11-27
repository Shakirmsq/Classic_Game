import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdGame extends JFrame implements KeyListener, ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int MARGIN = 50;  // Margin on all sides
    private static final int HEALTH_BAR_HEIGHT = 20; // Height of the health bar
    private static final int PLAYER_HEIGHT = 50; // Height of the player rectangle
    private static final int PLAYER_WIDTH = 50; // Width of the player rectangle

    private int player1X = 100, player1Y = 300, player2X = 600, player2Y = 300;
    private int player1Health = 100, player2Health = 100;
    private boolean player1MoveLeft, player1MoveRight, player1Punch;
    private boolean player2MoveLeft, player2MoveRight, player2Punch;
    private Timer timer;

    // GamePanel will handle all the drawing
    private GamePanel panel;

    public AdGame() {
        setTitle("AdGame");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize GamePanel and add to the JFrame
        panel = new GamePanel();
        add(panel);

        addKeyListener(this);
        setFocusable(true);

        // Set up the game timer (updates every 30 ms)
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Player 1 Controls
        if (key == KeyEvent.VK_A) player1MoveLeft = true;
        if (key == KeyEvent.VK_D) player1MoveRight = true;
        if (key == KeyEvent.VK_W) player1Punch = true;

        // Player 2 Controls
        if (key == KeyEvent.VK_LEFT) player2MoveLeft = true;
        if (key == KeyEvent.VK_RIGHT) player2MoveRight = true;
        if (key == KeyEvent.VK_UP) player2Punch = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Player 1 Controls
        if (key == KeyEvent.VK_A) player1MoveLeft = false;
        if (key == KeyEvent.VK_D) player1MoveRight = false;
        if (key == KeyEvent.VK_W) player1Punch = false;

        // Player 2 Controls
        if (key == KeyEvent.VK_LEFT) player2MoveLeft = false;
        if (key == KeyEvent.VK_RIGHT) player2MoveRight = false;
        if (key == KeyEvent.VK_UP) player2Punch = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (player1MoveLeft) player1X -= 5;
        if (player1MoveRight) player1X += 5;
        if (player2MoveLeft) player2X -= 5;
        if (player2MoveRight) player2X += 5;

        // Player 1 Punches
        if (player1Punch && Math.abs(player1X - player2X) < 50) {
            player2Health -= 2;
        }

        // Player 2 Punches
        if (player2Punch && Math.abs(player2X - player1X) < 50) {
            player1Health -= 2;
        }

        // Prevent players from going off the screen with margin
        if (player1X < MARGIN) player1X = MARGIN;
        if (player1X > WIDTH - PLAYER_WIDTH - MARGIN) player1X = WIDTH - PLAYER_WIDTH - MARGIN;

        if (player2X < MARGIN) player2X = MARGIN;
        if (player2X > WIDTH - PLAYER_WIDTH - MARGIN) player2X = WIDTH - PLAYER_WIDTH - MARGIN;

        // Check for game over
        if (player1Health <= 0 || player2Health <= 0) {
            timer.stop();
        }

        panel.repaint();  // Repaint the panel after each game update
    }

    // This is where the drawing will happen now
    private class GamePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // Ensure proper rendering of the component

            // Draw Players with margin (adjusted Y position for a closer layout)
            g.setColor(Color.RED);
            g.fillRect(player1X + MARGIN, player1Y + MARGIN, PLAYER_WIDTH, PLAYER_HEIGHT);  // Player 1
            g.setColor(Color.BLUE);
            g.fillRect(player2X + MARGIN, player2Y + MARGIN, PLAYER_WIDTH, PLAYER_HEIGHT);  // Player 2

            // Draw Health Bars with margin (adjusted Y position for a closer layout)
            g.setColor(Color.GREEN);
            g.fillRect(MARGIN, MARGIN + PLAYER_HEIGHT + 10, Math.max(player1Health * 2, 0), HEALTH_BAR_HEIGHT);  // Player 1 Health
          
            g.setColor(Color.RED);
            String redtitile = "RED FIGHTER";
           int xPos = (WIDTH /2) + 50;
           int yPos = (HEIGHT /2)+ 100; 
           g.drawString(redtitile, xPos, yPos);
          
           g.setColor(Color.GREEN);
            g.fillRect(MARGIN + 500, MARGIN + PLAYER_HEIGHT + 10, Math.max(player2Health * 2, 0), HEALTH_BAR_HEIGHT);  // Player 2 Health
          
            g.setColor(Color.BLUE);
            String bluetitile = "BLUE FIGHTER";
            int xPosi = (WIDTH /2) + 550;
            int yPosi = (HEIGHT /2)+ 100;
            g.drawString(bluetitile, xPosi, yPosi);
            
            // Game Over Message with margin (corrected vertical positioning)
            if (player1Health <= 0 || player2Health <= 0) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 30));

                String winner = (player1Health <= 0) ? "Player 2 blue Wins!" : "Player 1 red Wins!";
                int xPosition = (WIDTH / 2) + 10;
                int yPosition = (HEIGHT / 2) + 30;  // Position adjusted for margin

                g.drawString(winner, xPosition, yPosition);
            }
        }
    }

    public static void main(String[] args) {
        AdGame game = new AdGame();
        game.setVisible(true);
    }
}

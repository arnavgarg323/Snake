import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Snake implements ActionListener, KeyListener
{

    public static Snake snake;
    public int width = 1445;
    public int height = 835;
    public int gameStatus = 0; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
    public int points = 100;
    public boolean up = false;
    public boolean down = false;
    public boolean right = false;
    public boolean left = false;
    public boolean playerWon;
    public Renderer renderer;
    public Random random;
    public JFrame jframe;

    public Snake() {
        Timer timer = new Timer(20, this);
        random = new Random();

        jframe = new JFrame("Snake");

        renderer = new Renderer();

        jframe.setSize(width, height);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(renderer);
        jframe.addKeyListener(this);

        timer.start();
    }

    public void start() {
        gameStatus = 2;
    }

    public void update() {

    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (gameStatus == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));

            g.drawString("Snake", width / 2 - 75, 50);

            g.setFont(new Font("Arial", 1, 30));

            g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
        }


        if (gameStatus == 1) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
        }

        if (gameStatus == 3) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));

            g.drawString("Snake", width / 2 - 75, 50);

            if (playerWon == true) {

                g.drawString("You got " + points + "!", width / 2 - 150, 200);

            } else {

                g.drawString("You Lost", width / 2 - 100, 200);

            }

            g.setFont(new Font("Arial", 1, 30));

            g.drawString("Press Space to Play Again", width / 2 - 180, height / 2 - 25);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStatus == 2) {
            update();
        }

        renderer.repaint();
    }

    public static void main(String[] args) {
        snake = new Snake();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();

        if (id == KeyEvent.VK_UP) {

            up = true;

        } else if (id == KeyEvent.VK_DOWN) {

            down = true;

        } else if (id == KeyEvent.VK_RIGHT) {

            right = true;

        } else if (id == KeyEvent.VK_LEFT) {

            left = true;

        } else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3)) {

            gameStatus = 0;

        } else if (id == KeyEvent.VK_SPACE) {

            if (gameStatus == 0 || gameStatus == 3) {
                start();
            }
            else if (gameStatus == 1) {
                gameStatus = 2;
            }
            else if (gameStatus == 2) {
                gameStatus = 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*
        int id = e.getKeyCode();

        if (id == KeyEvent.VK_UP){

            up = false;

        } else if (id == KeyEvent.VK_DOWN){

            down = false;

        } else if (id == KeyEvent.VK_RIGHT){

            right = false;

        } else if (id == KeyEvent.VK_LEFT){

            left = false;

        }
         */
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
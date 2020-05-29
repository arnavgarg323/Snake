import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Snake implements ActionListener, KeyListener {

    private static Snake snake;
    private final int width = 1430;
    private final int height = 800;
    private int gameStatus = 0; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
    private int xDir;
    private int yDir;
    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private boolean playerWon;
    private final Renderer renderer;
    private Apple apple;
    private final ArrayList<SnakeBody> snakeBodies = new ArrayList<>();
    private SnakeBody snakeBody;

    public Snake() {

        Timer timer = new Timer(20, this);
        new Random();

        JFrame jframe = new JFrame("Snake");

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
        apple = new Apple(this);
        snakeBody = new SnakeBody((int) ((Math.random() * snake.width) - apple.getWidth()) + apple.getWidth(),
                (int) ((Math.random()*snake.height) - apple.getWidth()) + apple.getHeight());
        snakeBodies.add(snakeBody);
    }

    public void update() {

        if(snakeBodies.size() == 0){
            snakeBody = new SnakeBody((int) ((Math.random() * snake.width) - apple.getWidth()) + apple.getWidth(),
                    (int) ((Math.random()*snake.height) - apple.getWidth()) + apple.getHeight());
            snakeBodies.add(snakeBody);
        }

        if(apple.checkCollision(snakeBodies.get(0))){
            int counter = 0;
            while (counter < 5) {
                snakeBodies.add(new SnakeBody(snakeBodies.get(snakeBodies.size() - 1).getX(), snakeBodies.get(snakeBodies.size() - 1).getY()));
                counter++;
            }
        }

        move();
    }

    public void render(Graphics2D g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (gameStatus == 0) {

            up = false;
            down = false;
            left = false;
            right = false;

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));

            g.drawString("Snake", width / 2 - 75, 50);

            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
        }


        if (gameStatus == 1) {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
        }

        if (gameStatus == 1 || gameStatus == 2) {

            apple.render(g);

            for(SnakeBody body : snakeBodies){
                body.render(g);
            }

            if(apple.getScore() > 0){
                playerWon = true;
            }

            if(snakeBodies.get(0).getX() > width - snakeBodies.get(0).getWidth() || snakeBodies.get(0).getY() >
                    height - snakeBodies.get(0).getHeight() || snakeBodies.get(0).getX() < 0 || snakeBodies.get(0).getY() < 0) {
                gameStatus = 3;
            }

            /*
            if(snakeBodies.size() > 3) {
                for (SnakeBody body : snakeBodies) {
                    if (snakeBodies.get(0).getX() < body.getX() + body.getWidth() &&
                            snakeBodies.get(0).getX() + snakeBodies.get(0).getWidth() > body.getX() &&
                            snakeBodies.get(0).getY() < body.getY() + body.getHeight() &&
                            snakeBodies.get(0).getY() + snakeBodies.get(0).getHeight() > body.getY()) {

                        gameStatus = 3;
                        break;
                    }
                }
            }
             */

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Apples Eaten: " + apple.getScore(), width / 2 - 200, 50);
        }

        if (gameStatus == 3) {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));

            g.drawString("Snake", width / 2 - 75, 50);

            if (playerWon) {

                g.drawString("You ate " + apple.getScore() + " apples !", width / 2 - 200, 200);

            } else {

                g.drawString("You Lost", width / 2 - 100, 200);
            }
            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString("Press Space to Play Again", width / 2 - 180, height / 2 - 25);

            snakeBodies.clear();

            up = false;
            down = false;
            left = false;
            right = false;
            yDir = 0;
            yDir = 0;
        }
    }

    public void move(){
        SnakeBody temp = snakeBodies.get(0);
        SnakeBody last = snakeBodies.get(snakeBodies.size()-1);
        SnakeBody newStart = new SnakeBody(temp.getX() + xDir * 5, temp.getY() + yDir * 5);
        for(int i = snakeBodies.size()-1; i >= 1; i--){
            snakeBodies.set(i, snakeBodies.get(i-1));
        }
        snakeBodies.set(0, newStart);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public static Snake getSnake() {
        return snake;
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


        if(id == KeyEvent.VK_RIGHT && !left) {

            xDir = 1;
            yDir = 0;
            up = false;
            down = false;
            right = true;

        } else if(id == KeyEvent.VK_LEFT && !right) {

            xDir = -1;
            yDir = 0;
            up = false;
            down = false;
            left = true;

        } else if(id == KeyEvent.VK_UP && !down) {

            yDir = -1;
            xDir = 0;
            left = false;
            right = false;
            up = true;

        } else if(id == KeyEvent.VK_DOWN && !up) {

            yDir = 1;
            xDir = 0;
            left = false;
            right = false;
            down = true;

        } else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3)) {

            gameStatus = 0;

        } else if (id == KeyEvent.VK_SPACE) {

            if (gameStatus == 0 || gameStatus == 3) {

                start();

            } else if (gameStatus == 1) {

                gameStatus = 2;

            } else if (gameStatus == 2) {

                gameStatus = 1;
            }
        } else if(id == KeyEvent.VK_D) {
            yDir =0;
            xDir =0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Apple {

    private int x;
    private int y;
    private final int width = 20;
    private final int height = 20;
    private int score;
    private final Snake snake;

    public Apple(Snake snake) {

        this.snake = snake;

        new Random();

        spawn();
    }

    public void spawn() {

        this.x = (int) ((Math.random()*(snake.getWidth()) - this.width * 4)) + this.width * 2;
        this.y = (int) ((Math.random()*(snake.getHeight()) - this.height * 4)) + this.height * 2;
    }

    public void render(Graphics g) {

        g.setColor(Color.RED);
        g.fillOval(x, y, width, height);
    }

    public boolean checkCollision(SnakeBody snakeBody) {

        if (this.x < snakeBody.getX() + snakeBody.getWidth() && this.x + width > snakeBody.getX() &&
                this.y < snakeBody.getY() + snakeBody.getHeight() && this.y + height > snakeBody.getY()) {
            score++;
            spawn();
            return true;
        }else{
            return false;
        }
    }

    public int getScore() {
        return score;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
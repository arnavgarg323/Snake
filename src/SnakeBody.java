import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class SnakeBody {

    private final int x;
    private final int y;
    private final int width = 25;
    private final int height = 25;

    public SnakeBody(int x, int y) {

        new Random();

        this.x = x;
        this.y = y;
    }

    public void render(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillRect(x, y , width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
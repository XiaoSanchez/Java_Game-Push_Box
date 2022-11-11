import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class GamePeople {
    public static List behavior;
    public GameBox gameBox;
    public Rectangle rect;
    Image pup;
    Image pdown;
    Image pleft;
    Image pright;
    private static final String PEOPLE_UP = "img/back.png";
    private static final String PEOPLE_DOWN = "img/front.png";
    private static final String PEOPLE_LEFT = "img/left.png";
    private static final String PEOPLE_RIGHT = "img/right.png";
    int x, y, x1, y1;
    public boolean up = false, dowm = false, left = false, right = false;
    public GamePeople() {
        pup = GameUtil.loadBufferedImage(PEOPLE_UP);
        pdown = GameUtil.loadBufferedImage(PEOPLE_DOWN);
        pleft = GameUtil.loadBufferedImage(PEOPLE_LEFT);
        pright = GameUtil.loadBufferedImage(PEOPLE_RIGHT);
        rect = new Rectangle();
        behavior = new ArrayList();
        gameBox = new GameBox();
    }
    int b = 0;
    boolean aa = true;
    public void draw(Graphics g, int x, int y) {
        if (b == 0) {
            g.drawImage(pdown, x, y, null);
            rect(g);
        } else {
            direction(g);
            rect(g);
        }
        if (aa) {
            this.x = x;
            this.y = y;
            this.x1 = x;
            this.y1 = y;
            aa = false;
        }
    }
    public void walk(int num) {
        switch (num) {
            case 1:
                up = true;
                b = 1;
                y -= 50;
                dowm = left = right = false;
                break;
            case 2:
                dowm = true;
                y += 50;
                b = 1;
                up = left = right = false;
                break;
            case 3:
                left = true;
                x -= 50;
                b = 1;
                up = dowm = right = false;
                break;
            case 4:
                right = true;
                x += 50;
                b = 1;
                up = dowm = left = false;
                break;
        }
        behavior.add(num);
    }
    public void direction(Graphics g) {
        if (up) {
            g.drawImage(pup, x, y, null);
        } else if (dowm) {
            g.drawImage(pdown, x, y, null);
        } else if (left) {
            g.drawImage(pleft, x, y, null);
        } else if (right) {
            g.drawImage(pright, x, y, null);
        }
    }
    public void rect(Graphics g) {
        rect.x = this.x;
        rect.y = this.y;
        rect.width = 50;
        rect.height = 50;
    }
    public int stepBack() {
        x = x1;
        y = y1;
        return 0;
    }
    public void reset() {
        this.x = x1;
        this.y = y1;
    }
    public void newReset() {
        this.x = 250;
        this.y = 250;
    }
}

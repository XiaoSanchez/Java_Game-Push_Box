import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class GameBox extends GameCheckpoint {
    public Rectangle rect;
    public List<Rectangle> rects;
    private Image wall;
    private Image ground;
    private Image target;
    private Image crate;
    private List blks;
    public List behavior;
    public int checkpoint = 4;
    int x = 0, y = 0;
    private static final String WALL = "img/wall.png";
    private static final String GROUND = "img/ground.png";
    private static final String TARGET = "img/target.png";
    private static final String CRATE = "img/crate.png";
    int a = 0;
    int blk = 0;
    int blk1 = 0;
    boolean chenum = true;
    int[][] box;
    public GameBox() {
        wall = GameUtil.loadBufferedImage(WALL);
        ground = GameUtil.loadBufferedImage(GROUND);
        target = GameUtil.loadBufferedImage(TARGET);
        crate = GameUtil.loadBufferedImage(CRATE);
        rect = new Rectangle();
        rects = new ArrayList<>();
        blks = new ArrayList();
        behavior = new ArrayList();
    }
    boolean boo = false;
    public void draw(Graphics g, GamePeople gamePeople, int check) {
        if (chenum) {
            box = che(check);
            chenum = false;
        }
        if (boo) {
            box = crate();
            boo = false;
        }
        for (int j = 0; j < box.length; j++) {
            for (int i = 0; i < box[0].length; i++) {
                switch (box[j][i]) {
                    case 0:
                        x = (i + 1) * 50;
                        y = 50 + j * 50;
                        break;
                    case 1:
                        x = (i + 1) * 50;
                        y = 50 + j * 50;
                        g.drawImage(wall, x, y, null);
                        save(x, y, g);
                        break;
                    case 2:
                        x = (i + 1) * 50;
                        y = 50 + j * 50;
                        g.drawImage(ground, x, y, null);
                        break;
                    case 3:
                        x = (i + 1) * 50;
                        y = 50 + j * 50;
                        g.drawImage(target, x, y, null);
                        break;
                    case 4:
                        x = (i + 1) * 50;
                        y = 50 + j * 50;
                        if (gamePeople.x == x && gamePeople.y == y) {
                            if (gamePeople.right) {
                                if (box[j][i + 1] != 1 && box[j][i + 1] != 4) {
                                    if (box[j][i + 1] == 3 && !blks.contains((j + 1) * 10 + i)) {
                                        blk1++;
                                    }
                                    add(j, i, box[j][i], j, i + 1, box[j][i + 1]);
                                    if (blks.contains((j + 1) * 10 + i)) {
                                        box[j][i] = 3;
                                        box[j][i + 1] = 4;
                                    } else {
                                        box[j][i] = 2;
                                        box[j][i + 1] = 4;
                                    }
                                } else {
                                    gamePeople.x -= 50;
                                }
                            } else if (gamePeople.left) {
                                if (box[j][i - 1] != 1 && box[j][i - 1] != 4) {
                                    if (box[j][i - 1] == 3 && !blks.contains((j + 1) * 10 + i)) {
                                        blk1++;
                                    }
                                    add(j, i, box[j][i], j, i - 1, box[j][i - 1]);
                                    if (blks.contains((j + 1) * 10 + i)) {
                                        box[j][i] = 3;
                                        box[j][i - 1] = 4;
                                    } else {
                                        box[j][i] = 2;
                                        box[j][i - 1] = 4;
                                    }
                                } else {
                                    gamePeople.x += 50;
                                }
                            } else if (gamePeople.up) {
                                if (box[j - 1][i] != 1 && box[j - 1][i] != 4) {
                                    if (box[j - 1][i] == 3 && !blks.contains((j + 1) * 10 + i)) {
                                        blk1++;
                                    }
                                    add(j, i, box[j][i], j - 1, i, box[j - 1][i]);
                                    if (blks.contains((j + 1) * 10 + i)) {
                                        box[j][i] = 3;
                                        box[j - 1][i] = 4;
                                    } else {
                                        box[j][i] = 2;
                                        box[j - 1][i] = 4;
                                    }
                                } else {
                                    gamePeople.y += 50;
                                }
                            } else if (gamePeople.dowm) {
                                if (box[j + 1][i] != 1 && box[j + 1][i] != 4) {
                                    if (box[j + 1][i] == 3 && !blks.contains((j + 1) * 10 + i)) {
                                        blk1++;
                                    }
                                    add(j, i, box[j][i], j + 1, i, box[j + 1][i]);
                                    if (blks.contains((j + 1) * 10 + i)) {
                                        box[j][i] = 3;
                                        box[j + 1][i] = 4;
                                    } else {
                                        box[j][i] = 2;
                                        box[j + 1][i] = 4;
                                    }
                                } else {
                                    gamePeople.y -= 50;
                                }
                            }
                        }
                        g.drawImage(crate, x, y, null);
                        break;
                }
            }
        }
        if (blk == blk1) {
            checkpoint++;
            chenum = true;
        }
        coll(gamePeople);
        System.out.println(blk1);
        g.setColor(Color.WHITE);
        String str = "Level: " + checkpoint;
        g.setFont(new Font("Cursive", 1, 30));
        g.drawString(str, 350, 80);
    }
    public void save(int x, int y, Graphics g) {
        if (a != 0) {
            GameBox gamebox = new GameBox();
            gamebox.rect.x = x;
            gamebox.rect.y = y;
            gamebox.rect.width = 50;
            gamebox.rect.height = 50;
            rects.add(gamebox.rect);
            a--;
        }
    }
    public void coll(GamePeople gamePeople) {
        for (int i = 0; i < rects.size(); i++) {
            Rectangle rectangle = rects.get(i);
            if (gamePeople.rect.intersects(rectangle)) {
                System.out.println("Blocked!");
                if (gamePeople.up) {
                    gamePeople.y += 50;
                } else if (gamePeople.dowm) {
                    gamePeople.y -= 50;
                } else if (gamePeople.left) {
                    gamePeople.x += 50;
                } else if (gamePeople.right) {
                    gamePeople.x -= 50;
                }
                System.out.println(blk1);
            }
        }
    }
    public int[][] che(int check) {
        switch (check) {
            case 1:
                a = 0;
                blk = 0;
                blk1 = 0;
                rects.clear();
                blks.clear();
                for (int i = 0; i < box1.length; i++) {
                    for (int j = 0; j < box1.length; j++) {
                        if (box1[i][j] == 1) {
                            a++;
                        }
                        if (box1[i][j] == 3) {
                            blk++;
                            blks.add((i + 1) * 10 + j);
                        }
                    }
                }
                return box1;
            case 2:
                a = 0;
                blk = 0;
                blk1 = 0;
                rects.clear();
                blks.clear();
                for (int i = 0; i < box2.length; i++) {
                    for (int j = 0; j < box2.length; j++) {
                        if (box2[i][j] == 1) {
                            a++;
                        }
                        if (box2[i][j] == 3) {
                            blk++;
                            blks.add((i + 1) * 10 + j);
                        }
                    }
                }
                return box2;
            case 3:
                a = 0;
                blk = 0;
                blk1 = 0;
                rects.clear();
                blks.clear();
                for (int i = 0; i < box3.length; i++) {
                    for (int j = 0; j < box3.length; j++) {
                        if (box3[i][j] == 1) {
                            a++;
                        }
                        if (box3[i][j] == 3) {
                            blk++;
                            blks.add((i + 1) * 10 + j);
                        }
                    }
                }
                return box3;
        }
        return null;
    }
    public int stepBack() {
        if (behavior.size() == 0)
            return 0;
        int num = (int) behavior.get(behavior.size() - 1);
        behavior.remove(behavior.size() - 1);
        int i = num / 100000;
        int j = num / 10000 % 10;
        int n = num / 1000 % 10;
        int i1 = num / 100 % 10;
        int j1 = num / 10 % 10;
        int n1 = num % 10;
        box[i][j] = n;
        box[i1][j1] = n1;
        int num1 = (i1 + 1) * 10 + j1;
        for (int k = 0; k < blks.size(); k++) {
            if ((int) blks.get(k) == num1) {
                blk1--;
            }
        }
        return 0;
    }
    public void add(int j, int i, int num, int j1, int i1, int num1) {
        behavior.add(j * 100000 + i * 10000 + num * 1000 + j1 * 100 + i1 * 10 + num1);
    }
    public void reset() {
        chenum = true;
        blk1 = 0;
        blk = 0;
        switch (checkpoint) {
            case 1:
                for (int i = 0; i < box1Rest.length; i++) {
                    for (int j = 0; j < box[0].length; j++) {
                        box[i][j] = box1Rest[i][j];
                    }
                }
                break;
            case 2:
                for (int i = 0; i < box2Rest.length; i++) {
                    for (int j = 0; j < box[0].length; j++) {
                        box[i][j] = box2Rest[i][j];
                    }
                }
                break;
            case 3:
                for (int i = 0; i < box3Rest.length; i++) {
                    for (int j = 0; j < box[0].length; j++) {
                        box[i][j] = box3Rest[i][j];
                    }
                }
                break;
        }
    }
    public void resett() {
        this.checkpoint = 1;
        boo = true;
        behavior.clear();
    }
    public int[][] crate() {
        a = 0;
        blk = 0;
        blk1 = 0;
        rects.clear();
        blks.clear();
        for (int i = 0; i < box1Rest.length; i++) {
            for (int j = 0; j < box1Rest.length; j++) {
                if (box1Rest[i][j] == 1) {
                    a++;
                }
                if (box1Rest[i][j] == 3) {
                    blk++;
                    blks.add((i + 1) * 10 + j);
                }
            }
        }
        box = new int[][] {
                { 0, 0, 1, 1, 1, 0, 0, 0 },
                { 0, 0, 1, 3, 1, 0, 0, 0 },
                { 0, 0, 1, 2, 1, 1, 1, 1 },
                { 1, 1, 1, 4, 2, 4, 3, 1 },
                { 1, 3, 2, 4, 2, 1, 1, 1 },
                { 1, 1, 1, 1, 4, 1, 0, 0 },
                { 0, 0, 0, 1, 3, 1, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 0, 0 }
        };
        return box;
    }
}

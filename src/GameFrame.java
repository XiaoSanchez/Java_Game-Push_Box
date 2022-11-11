import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
public class GameFrame extends Frame {
    private GameBackground gameBackground;
    private GameBox gameBox;
    private GamePeople gamePeople;
    private BufferedImage buffimg = new BufferedImage(700, 700, BufferedImage.TYPE_4BYTE_ABGR);
    public GameFrame() {
        setSize(500, 500);
        setVisible(true);
        setTitle("Push Box");
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        new run().start();
        Instantiation();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                add(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                minu(e);
            }
        });
    }
    class run extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gameBox.checkpoint == 2) {
                    setSize(550, 550);
                } else if (gameBox.checkpoint == 3) {
                    setSize(600, 450);
                }
            }
        }
    }
    public void Instantiation() {
        gameBackground = new GameBackground();
        gameBox = new GameBox();
        gamePeople = new GamePeople();
    }
    boolean at = true;
    @Override
    public void update(Graphics g) {
        if (gameBox.checkpoint == 1) {
            Graphics graphics = buffimg.getGraphics();
            gameBackground.draw(graphics);
            gameBox.draw(graphics, gamePeople, gameBox.checkpoint);
            gamePeople.draw(graphics, 250, 250);
            g.drawImage(buffimg, 0, 0, null);
        } else if (gameBox.checkpoint == 2) {
            if (at) {
                gamePeople.aa = true;
                gameBox.behavior.clear();
                at = false;
            }
            Graphics graphics = buffimg.getGraphics();
            gameBackground.draw(graphics);
            gameBox.draw(graphics, gamePeople, gameBox.checkpoint);
            gamePeople.draw(graphics, 100, 100);
            g.drawImage(buffimg, 0, 0, null);
        } else if (gameBox.checkpoint == 3) {
            if (!at) {
                gamePeople.aa = true;
                gameBox.behavior.clear();
                at = true;
            }
            Graphics graphics = buffimg.getGraphics();
            gameBackground.draw(graphics);
            gameBox.draw(graphics, gamePeople, gameBox.checkpoint);
            gamePeople.draw(graphics, 100, 250);
            g.drawImage(buffimg, 0, 0, null);
        } else if (gameBox.checkpoint == 4) {
            if (at) {
                gamePeople.aa = true;
                at = false;
            }
            String over = "Success!";
            g.setColor(Color.BLUE);
            g.setFont(new Font("Cursive", 1, 60));
            g.drawString(over, 120, 250);
        }
    }
    public void add(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                gamePeople.walk(1);
                break;
            case KeyEvent.VK_DOWN:
                gamePeople.walk(2);
                break;
            case KeyEvent.VK_LEFT:
                gamePeople.walk(3);
                break;
            case KeyEvent.VK_RIGHT:
                gamePeople.walk(4);
                break;
            case KeyEvent.VK_L:
                gamePeople.stepBack();
                gameBox.stepBack();
                break;
            case KeyEvent.VK_SPACE:
                gamePeople.reset();
                gameBox.reset();
                break;
            case KeyEvent.VK_Q:
                gameBox.resett();
                gamePeople.aa = true;
                at = true;
                break;
        }
    }
    public void minu(KeyEvent e) {
    }
}

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class StartingClass extends Applet implements Runnable, KeyListener {

    private static Robot robot;
    private Heliboy hb, hb2;
    private Image image, currentSprite, character, character2, character3,
            characterDown, characterJumped, background, heliboy, heliboy2,
            heliboy3, heliboy4, heliboy5;

    static Image tiledirt, tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight;

    private Graphics second;
    private URL base;
    private static Background bg1, bg2;
    private Animation anim, hanim;

    private ArrayList<Tile> tilearray = new ArrayList<>();

    @Override
    public void init() {

        setSize(1360, 768);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Q-Bot Alpha");
        try {
            base = getDocumentBase();
        } catch (Exception e) {
            // TODO: handle exception
        }

        // Image Setups
        character = getImage(base, "/data/character.png");
        character2 = getImage(base, "/data/character2.png");
        character3 = getImage(base, "/data/character3.png");

        characterDown = getImage(base, "/data/down.png");
        characterJumped = getImage(base, "/data/jumped.png");

        heliboy = getImage(base, "/data/heliboy.png");
        heliboy2 = getImage(base, "/data/heliboy2.png");
        heliboy3 = getImage(base, "/data/heliboy3.png");
        heliboy4 = getImage(base, "/data/heliboy4.png");
        heliboy5 = getImage(base, "/data/heliboy5.png");

        background = getImage(base, "/data/background.png");

        tiledirt = getImage(base, "/data/tiledirt.png");
        tilegrassBot = getImage(base,"/data/tilegrassbot.png");
        tilegrassTop = getImage(base,"/data/tilegrasstop.png");
        tilegrassLeft = getImage(base,"/data/tilegrassleft.png");
        tilegrassRight = getImage(base,"/tilegrassright.png");

        anim = new Animation();
        anim.addFrame(character, 1250);
        anim.addFrame(character2, 50);
        anim.addFrame(character3, 50);
        anim.addFrame(character2, 50);

        hanim = new Animation();
        hanim.addFrame(heliboy, 100);
        hanim.addFrame(heliboy2, 100);
        hanim.addFrame(heliboy3, 100);
        hanim.addFrame(heliboy4, 100);
        hanim.addFrame(heliboy5, 100);
        hanim.addFrame(heliboy4, 100);
        hanim.addFrame(heliboy3, 100);
        hanim.addFrame(heliboy2, 100);

        currentSprite = anim.getImage();
    }

    @Override
    public void start() {
        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0);
        robot = new Robot();
        // Initialize Tiles
        try{
            loadmap("/data/map1.txt");
        } catch (IOException e){
            e.printStackTrace();
        }


        hb = new Heliboy(340, 360);
        hb2 = new Heliboy(700, 360);


        Thread thread = new Thread(this);
        thread.start();
    }

    private void loadmap(String filename) throws IOException{
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                reader.close();
                break;
            }
            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }
        height = lines.size();
        for(int j = 0; j<19;j++){
            String line = (String) lines.get(j);
            for(int i = 0; i<width;i++){
                System.out.println(i + "is i");

                if(i<line.length()){
                    char ch = line.charAt(i);
                    Tile t = new Tile(i, j, Character.getNumericValue(ch));
                    tilearray.add(t);
                }
            }
        }
    }


    @Override
    public void stop() {
        // TODO Auto-generated method stub
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void run() {
        while (true) {
            robot.update();
            if (robot.isJumped()) {
                currentSprite = characterJumped;
            } else if (!robot.isJumped() && !robot.isDucked()) {
                currentSprite = anim.getImage();
            }

            ArrayList projectiles = robot.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectiles p = (Projectiles) projectiles.get(i);
                if (p.isVisible()) {
                    p.update();
                } else {
                    projectiles.remove(i);
                }
            }

            updateTiles();
            hb.update();
            hb2.update();
            bg1.update();
            bg2.update();
            animate();
            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void animate() {
        anim.update(10);
        hanim.update(50);
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }

        second.setColor(getBackground());
        second.fillRect(0, 0, getWidth(), getHeight());
        second.setColor(getForeground());
        paint(second);

        g.drawImage(image, 0, 0, this);

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
        paintTiles(g);

        ArrayList projectiles = robot.getProjectiles();
        for (Object projectile : projectiles) {
            Projectiles p = (Projectiles) projectile;
            g.setColor(Color.YELLOW);
            g.fillRect(p.getX(), p.getY(), 10, 5);
        }

        g.drawImage(currentSprite, robot.getCenterX() - 61,
                robot.getCenterY() - 63, this);
        g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
                hb.getCenterY() - 48, this);
        g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
                hb2.getCenterY() - 48, this);
    }

    private void updateTiles() {

        for (Tile aTilearray : tilearray) {
            Tile t = (Tile) aTilearray;
            t.update();
        }

    }

    private void paintTiles(Graphics g) {
        for (Tile aTilearray : tilearray) {
            Tile t = (Tile) aTilearray;
            g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                break;

            case KeyEvent.VK_DOWN:
                currentSprite = characterDown;
                if (!robot.isJumped()) {
                    robot.ducked();
                    robot.setSpeedX(0);
                }
                break;

            case KeyEvent.VK_LEFT:
                robot.moveLeft();

                break;

            case KeyEvent.VK_RIGHT:
                robot.moveRight();
                break;

            case KeyEvent.VK_SPACE:
                robot.jump();
                break;

            case KeyEvent.VK_CONTROL:
                if (!robot.isDucked() && !robot.isJumped()) {
                    robot.shoot();
                    robot.setReadyToFire(false);
                }
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                break;

            case KeyEvent.VK_DOWN:
                currentSprite = anim.getImage();
                robot.setDucked(false);
                break;

            case KeyEvent.VK_LEFT:
                robot.stop();
                break;

            case KeyEvent.VK_RIGHT:
                robot.stop();
                break;

            case KeyEvent.VK_SPACE:
                break;

            case KeyEvent.VK_CONTROL:
                robot.setReadyToFire(true);
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    static Robot getRobot() {
        return robot;
    }

    public static void setRobot(Robot robot) {
        StartingClass.robot = robot;
    }

    static Background getBg1() {
        return bg1;
    }

    static Background getBg2() {
        return bg2;
    }


}

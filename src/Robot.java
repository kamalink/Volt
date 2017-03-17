import java.awt.*;
import java.util.ArrayList;

class Robot {
    private final int JUMPSPEED = -20;
    private final int MOVESPEED = 5;

    private int centerX = 100;
    private int centerY = 538;
    private boolean jumped = false;
    private boolean ducked = false;
    private boolean readyToFire = true;

    private static Background bg1 = StartingClass.getBg1();
    private static Background bg2 = StartingClass.getBg2();

    static Rectangle rect1 = new Rectangle(0,0,0,0);
    static Rectangle rect2 = new Rectangle(0,0,0,0);
    static Rectangle rect3 = new Rectangle(0,0,0,0);
    static Rectangle rect4 = new Rectangle(0,0,0,0);
    static Rectangle footleft = new Rectangle(0,0,0,0);
    static Rectangle footright = new Rectangle(0,0,0,0);
    static Rectangle yellowRED = new Rectangle(0,0,0,0);


    private int speedX = 0;
    private int speedY = 0;

    private ArrayList<Projectiles>projectiles = new ArrayList<>();

    void update() {

        // Moves Character or Scrolls Background accordingly.

        //Handle negative speed
        if (speedX < 0) {
            centerX += speedX;
        }

        //Stops Background if character speed <= 0
        if (speedX == 0 || speedX < 0) {
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);


        }
        //Handle positive speed up to border centerX = 200
        if (centerX <= 200 && speedX > 0) {
            centerX += speedX;
        }

        //Character stops moving on centerX = 200. Background start to moving
        if (speedX > 0 && centerX > 200){
            bg1.setSpeedX(-MOVESPEED/5);
            bg2.setSpeedX(-MOVESPEED/5);

        }

        // Updates Y Position
        centerY += speedY;

        //Handles Jumping
        speedY +=1;
        if(speedY>3){
            jumped = true;
        }

        // Force of gravity
        if (jumped) {
            speedY += 1;
        }

        // Prevents character going beyond 0 coordinate
        if (centerX + speedX <= 60) {
            centerX = 61;
        }

        rect1.setRect(centerX-34,centerY-63,68,63);
        rect2.setRect(rect1.getX(), rect1.getY()+63,68,64);
        rect3.setRect(rect3.getX()-26,rect3.getY()+32,26,20);
        rect4.setRect(rect4.getX()+68,rect4.getY()+32,26,20);
        footleft.setRect(centerX - 50, centerY + 20, 50, 15);
        footright.setRect(centerX, centerY + 20, 50, 15);

        yellowRED.setRect(centerX-110,centerY-110,180,180);
    }

    void moveRight() {
        speedX = MOVESPEED;
    }

    void moveLeft() {
        speedX = -MOVESPEED;
    }

    void jump() {
        if (!jumped) {
            speedY = JUMPSPEED;
            jumped = true;
        }
    }

    void shoot() {
        if (readyToFire) {
            Projectiles p = new Projectiles(centerX + 50, centerY - 25);
            projectiles.add(p);
        }
    }

    void ducked(){
        if (!ducked){
            ducked = true;
            speedX = 0;
        }
    }

    void stop() {
        speedX = 0;
    }


    int getCenterX() {
        return centerX;
    }

    int getCenterY() {
        return centerY;
    }

    void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public boolean isReadyToFire() {
        return readyToFire;
    }

    void setReadyToFire(boolean readyToFire) {
        this.readyToFire = readyToFire;
    }

    public int getJUMPSPEED() {
        return JUMPSPEED;
    }

    public int getMOVESPEED() {
        return MOVESPEED;
    }

    boolean isDucked() {
        return ducked;
    }

    void setDucked(boolean ducked) {
        this.ducked = ducked;
    }

    void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    boolean isJumped() {
        return jumped;
    }

    void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public static Background getBg1() {
        return bg1;
    }

    public static void setBg1(Background bg1) {
        Robot.bg1 = bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

    public static void setBg2(Background bg2) {
        Robot.bg2 = bg2;
    }

    public int getSpeedX() {
        return speedX;
    }

    void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    ArrayList<Projectiles> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectiles> projectiles) {
        this.projectiles = projectiles;
    }
}

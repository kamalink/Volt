import java.awt.*;


public class Tile {
    private Image tileImage;
    private int tileX, tileY, speedX, type;

    private Background bg = StartingClass.getBg1();
    private Robot robot = StartingClass.getRobot();
    private Rectangle r;

    Tile(int x, int y, int typeInt){
        tileX = x*40;
        tileY = y*40;
        type = typeInt;

        r = new Rectangle();

        if(type == 5){
            tileImage = StartingClass.tiledirt;
        } else if(type == 8){
            tileImage = StartingClass.tilegrassTop;
        } else  if(type == 4){
            tileImage = StartingClass.tilegrassLeft;
        } else if(type == 6){
            tileImage = StartingClass.tilegrassRight;
        } else if(type == 2){
            tileImage = StartingClass.tilegrassBot;
        } else {
            type = 0;
        }
    }

    void update(){
        speedX = bg.getSpeedX()*5;
        tileX += speedX;

        r.setBounds(tileX,tileY,40,40);

        if(r.intersects(Robot.yellowRED) && type !=0 ){
            checkVerticalCollision(Robot.rect1,Robot.rect2);
            checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft, Robot.footright);
        }
    }

    private void checkVerticalCollision(Rectangle rtop, Rectangle rbot){
        if(rtop.intersects(r)){

        }
        if(rbot.intersects(r) && type == 8){
            robot.setJumped(false);
            robot.setSpeedY(0);
            robot.setCenterY(tileY-63);
        }
    }

    private void checkSideCollision(Rectangle rleft, Rectangle rright, Rectangle leftfoot, Rectangle rightfoot) {
        if (type != 5 && type != 2 && type != 0) {
            if (rleft.intersects(r)) {
                robot.setCenterX(tileX + 102);

                robot.setSpeedX(0);

            } else if (leftfoot.intersects(r)) {
                robot.setCenterX(tileX + 85);
                robot.setSpeedX(0);
            }

            if (rright.intersects(r)) {
                robot.setCenterX(tileX - 62);

                robot.setSpeedX(0);
            } else if (rightfoot.intersects(r)) {
                robot.setCenterX(tileX - 45);
                robot.setSpeedX(0);
            }
        }
    }

    Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }

    int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Background getBg() {
        return bg;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }
}

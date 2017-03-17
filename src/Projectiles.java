
public class Projectiles {
    private int x,y,speedX;
    private boolean visible;
    Projectiles(int startX, int startY){
        x = startX;
        y = startY;
        speedX = 7;
        visible = true;
    }

    void update(){
        x+=speedX;
        if(x>800){
            visible = false;
        }
    }

    int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}

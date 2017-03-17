
class Background {
    private int bgX, bgY, speedX;

    Background(int x, int y){
        bgX = x;
        bgY = y;
        speedX = 0;
    }

    void update() {
        bgX += speedX;

        if (bgX <= -2160){
            bgX += 4320;
        }
    }

    int getBgX() {
        return bgX;
    }

    int getBgY() {
        return bgY;
    }

    void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    int getSpeedX() {
        return speedX;
    }
}

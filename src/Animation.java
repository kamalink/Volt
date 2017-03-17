import java.awt.*;
import java.util.ArrayList;


public class Animation {
    private ArrayList frames;
    private int currentFrame;
    private long animTime;
    private long totalDuration;


    Animation(){
        frames = new ArrayList();
        totalDuration = 0;

        synchronized (this){
            animTime = 0;
            currentFrame = 0;
        }
    }

    synchronized void addFrame(Image image, long duration){
        totalDuration+=duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    synchronized void update(long elapsedTime){
        if(frames.size()>1) {
            animTime += elapsedTime;

            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currentFrame = 0;
            }
        }
        while (animTime>getFrame(currentFrame).endTime){
            currentFrame++;
        }
    }

    synchronized Image getImage(){
        if(frames.size()==0){
            return null;
        }else {
            return getFrame(currentFrame).image;
        }
    }

    private AnimFrame getFrame(int i){
        return (AnimFrame) frames.get(i);
    }


    private class AnimFrame{
        Image image;
        long endTime;

        AnimFrame(Image image, long endTime){
            this.image = image;
            this.endTime = endTime;
        }
    }




    public ArrayList getFrames() {
        return frames;
    }

    public void setFrames(ArrayList frames) {
        this.frames = frames;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public long getAnimTime() {
        return animTime;
    }

    public void setAnimTime(long animTime) {
        this.animTime = animTime;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }
}

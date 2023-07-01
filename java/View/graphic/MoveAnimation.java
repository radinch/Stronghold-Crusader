package View.graphic;

import Controller.UnitMenuController;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class MoveAnimation extends Transition {

    private final double finalX;
    private final double finalY;
    private final double firstX;
    private final double firstY;
    private final HBox queues;
    private final ImageView imageView;


    public MoveAnimation(HBox queues, ImageView imageView) {
        this.queues = queues;
        this.imageView = imageView;
        this.setCycleDuration(Duration.millis(500));
        this.setCycleCount(-1);
        firstX = queues.getLayoutX();
        firstY = queues.getLayoutY();
        double deltaX = imageView.getBoundsInParent().getCenterX() -
                this.queues.getBoundsInParent().getCenterX();
        double deltaY =imageView.getBoundsInParent().getCenterY() -
                this.queues.getBoundsInParent().getCenterY();
        if(UnitMenuController.isFar) {
            System.out.println("salam");
            if(deltaX > 0)
                deltaX += 200;
            if(deltaY > 0)
                deltaY += 200;
            if(deltaX < 0)
                deltaX -= 100;
            if(deltaY < 0)
                deltaY -= 100;
        }
        finalX = queues.getLayoutX() + deltaX;
        finalY = queues.getLayoutY() + deltaY;
    }

    @Override
    protected void interpolate(double v) {
        double y = 0;
        double x = 0;
        if(firstY >= finalY)  {
            y = Math.max(queues.getLayoutY() - 2,finalY);
        }
        if(firstY < finalY) {
            y = Math.min(queues.getLayoutY() + 2,finalY);
        }
        if(firstX >= finalX)  {
            x = Math.max(queues.getLayoutX() - 2,finalX);
        }
        if(firstX < finalX) {
            x = Math.min(queues.getLayoutX() + 2,finalX);
        }
        if(areUnitsArrived(x,y)) {
            UnitMenuController.isAnimationFinished = true;
            if(UnitMenuController.isFar) {
                UnitMenuController.isFar = false;
            }
            this.stop();
        }
        queues.setLayoutX(x);
        queues.setLayoutY(y);

    }

    private boolean areUnitsArrived(double x, double y) {
        return (y<= finalY +0.1 && y>= finalY - 0.1) && (x<= finalX +0.1 && x>= finalX - 0.1);
    }
}

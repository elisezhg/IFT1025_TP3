// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.image.Image;

public class EtoileDeMer extends Poisson {

    private double timer = 0;

    /**
     * Constructeur de l'étoile de mer
     */
    public EtoileDeMer() {
        this.img = new Image("/res/star.png");
        ay = 0;
        vy = 200;
    }


    /**
     * Met à jour la position et la vitesse de l'étoile de mer
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    @Override
    public void update(double dt) {
        super.update(dt);
        timer += dt;

        if (timer >= 0.5) {
            vy *= -1;
            timer = 0;
        }
    }
}

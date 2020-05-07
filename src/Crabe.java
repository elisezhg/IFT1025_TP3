// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.image.Image;

public class Crabe extends Poisson {

    private boolean avance = true;
    private double timer = 0;

    /**
     * Constructeur du crabe
     */
    public Crabe() {
        this.img = new Image("/res/crabe.png");
        ay = 0;
        vx *= 1.3;
        vy = 0;
    }


    /**
     * Met à jour la position et la vitesse du crabe
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    @Override
    public void update(double dt) {
        super.update(dt);
        timer += dt;

        if (timer >= 0.5 && avance) {
            timer = 0;
            avance = false;
            vx *= -1;   // recule
        }

        if (timer >= 0.25 && !avance) {
            timer = 0;
            avance = true;
            vx *= -1;   // avance
        }
    }
}

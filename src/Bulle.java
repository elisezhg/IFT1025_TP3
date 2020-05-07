// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulle extends Entity {

    /**
     * Constructeur de la bulle
     * @param baseX position horizontale sur laquelle se base le groupe de bulles
     */
    public Bulle(double baseX) {
        this.color = Color.rgb(0, 0, 255, 0.4);
        this.largeur = Math.random() * 30 + 10;         // entre 10 et 40px
        this.hauteur = this.largeur;
        this.vy = -(Math.random() * 100 + 350);         // entre 350 et 450px/s vers le haut
        this.posX = baseX + Math.random() * 40 - 20;    // baseX +- 20
        this.posY = FishHunt.HEIGHT;     // positionné juste en bas de l'écran
    }


    /**
     * Met à jour la position de la bulle
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        super.update(dt);
    }


    /**
     * Dessine la bulle sur le canvas
     * @param context contexte graphique
     */
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillOval(posX, posY, largeur, hauteur);
    }
}

// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Balle extends Entity {

    /**
     * Constructeur de la balle
     * @param posX abscisse de la balle
     * @param posY ordonnée de la balle
     */
    public Balle(double posX, double posY) {
        this.hauteur = 100;     // rayon de 50px -> diamètre de 100px
        this.largeur = 100;
        this.vx = -600;         // rayon diminue de 300px/s -> diamètre diminue de 600px/s
        this.vy = -600;
        this.posX = posX;
        this.posY = posY;
    }


    /**
     * Met à jour la position et la vitesse de la balle
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    @Override
    public void update(double dt) {
        hauteur += dt * vx;
        largeur += dt * vy;
    }


    /**
     * Teste la collision entre la balle et le poisson
     * @param poisson le poisson à tester
     * @return true s'il y a collision, false sinon
     */
    public boolean collision(Poisson poisson) {

        // La balle est encore trop loin
        if (hauteur > 0) return false;

        // La balle n'est plus visible
        visible = false;

        boolean touchePoisson = posX >= poisson.getPosX() && posX <= poisson.getPosX() + poisson.getLargeur() &&
                                posY >= poisson.getPosY() && posY <= poisson.getPosY() + poisson.getLargeur();

        // Collision avec le poisson ?
        if (touchePoisson) {
            poisson.setVisible(false);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Dessine la balle sur le canvas
     * @param context contexte graphique
     */
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillOval(posX - largeur / 2, posY - hauteur / 2, hauteur, largeur);
    }
}
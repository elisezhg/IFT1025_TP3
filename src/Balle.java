import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Balle extends Entity {

    public Balle(double posX, double posY) {
        this.hauteur = 100;
        this.largeur = 100;
        this.vx = -600;
        this.vy = -600;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void update(double dt) {
        hauteur += dt * vx;
        largeur += dt * vy;
    }

    public void testCollision(Poisson poisson) {
        if (hauteur > 0) return;

        // Collision avec le poisson ?
        if (posX >= poisson.getPosX() && posX <= poisson.getPosX() + poisson.getLargeur() &&
            posY >= poisson.getPosY() && posY <= poisson.getPosY() + poisson.getLargeur()) {
            Jeu.score++;
            poisson.setVisible(false);
        }

        visible = false; // Balle n'est plus visible
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillOval(posX - largeur / 2, posY - hauteur / 2, hauteur, largeur);
    }

}

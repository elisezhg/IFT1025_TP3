import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {
    protected Color color;
    protected double ax = 0, ay = 0;
    protected double vx = 0, vy = 0;
    protected double posX, posY;
    protected double hauteur, largeur;
    protected boolean visible = true;

    // Met à jour l'accélération, la vitesse et la position de l'entité
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        posX += dt * vx;
        posY += dt * vy;

        // Force à rester dans les bornes de l'écran
//        if (posX + largeur > HighSeaTower.WIDTH || posX < 0) {
//            vx *= -1;
//        }
//
//        posX = Math.min(posX, HighSeaTower.WIDTH - largeur);
//        posX = Math.max(posX, 0);

    }

    // Dessine l'entité sur le canvas
    public abstract void draw(GraphicsContext context);

    // Getters et setters


    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

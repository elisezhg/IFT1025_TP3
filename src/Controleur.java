import javafx.scene.canvas.GraphicsContext;

public class Controleur {
    private Jeu jeu;

    public Controleur() {
        this.jeu = new Jeu();
    }

    public void incrNiveau() {
        jeu.incrNiveau();
    }

    public void incrScore() {
        jeu.incrScore();
    }

    public void incrVie() {
        jeu.incrVie();
    }

    public void perdre() {
        jeu.perdre();
    }

    public void lancerBalle(double balleX, double balleY) {
        jeu.lancerBalle(balleX, balleY);
    }

    public void updateCible(double cibleX, double cibleY) {
        jeu.updateCible(cibleX, cibleY);
    }

    public void update(double deltaTime) {
        jeu.update(deltaTime);
    }

    public void draw(GraphicsContext context) {
        jeu.draw(context);
    }

}

import java.awt.*;

public class Pipe {
    // Menyimpan koordinat posisi dan dimensi pipa.
    private int posX;
    private int posY;
    private int width;
    private int height;

    // Menyimpan gambar pipa, kecepatan horizontal, dan status apakah sudah dilewati.
    private Image image;
    private int velocityX;
    private boolean passed;

    // Membuat objek pipa berdasarkan posisi, ukuran, dan gambar yang diterima.
    public Pipe(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;

        // Pipa dimulai tanpa kecepatan dan belum pernah dilewati player.
        this.velocityX = 0;
        this.passed = false;
    }

    // Getter dan setter berikut digunakan untuk membaca atau mengubah atribut objek Pipe.
    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }

    public int getVelocityX() {
        return velocityX;
    }
    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    // Memeriksa dan mengubah status pipa ketika sudah dilewati player.
    public boolean isPassed() {
        return passed;
    }
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}

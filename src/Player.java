import java.awt.*;

public class Player {
    // Menyimpan posisi horizontal dan vertikal karakter.
    private int posX;
    private int posY;

    // Menyimpan ukuran karakter untuk proses penggambaran dan deteksi tabrakan.
    private int width;
    private int height;

    // Menyimpan gambar animasi dan kecepatan gerak vertikal karakter.
    private Image image;
    private int velocityY;

    // Menginisialisasi karakter berdasarkan posisi, ukuran, dan gambar awal.
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;

        // Kecepatan vertikal awal bernilai nol karena karakter belum bergerak.
        this.velocityY = 0;
    }

    // Getter dan setter digunakan untuk mengakses atau memperbarui atribut player.
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

    // Mengatur kecepatan vertikal yang digunakan dalam perhitungan gravitasi dan lompatan.
    public int getVelocityY() {
        return velocityY;
    }
    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}

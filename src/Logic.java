import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    // Ukuran area permainan yang menjadi acuan posisi objek dan batas layar.
    int frameWidth = 360;
    int frameHeight = 640;

    // Posisi awal player ditempatkan di sisi kiri dan bagian tengah layar.
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;

    // Ukuran karakter burung yang digunakan untuk menggambar dan mendeteksi tabrakan.
    int playerWidth = 34;
    int playerHeight = 24;

    // Pipa mulai muncul dari sisi kanan layar dengan ukuran yang telah ditentukan.
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // Timer gameLoop mengatur pembaruan permainan, sedangkan pipesCooldown mengatur kemunculan pipa.
    Timer gameLoop;
    Timer pipesCooldown;

    // Gravitasi mempercepat jatuhnya player, sedangkan nilai negatif menggerakkan pipa ke kiri.
    int gravity = 1;
    int pipeVelocityX = -2;

    // Referensi View digunakan untuk memperbarui skor dan tampilan permainan.
    View view;

    // Menyimpan gambar animasi burung, kedua jenis pipa, dan background.
    Image birdUpFlap;
    Image birdMidFlap;
    Image birdDownFlap;
    Image lowerPipeImage;
    Image upperPipeImage;
    Image backgroundImage;

    // Menyimpan objek player dan daftar seluruh pipa yang sedang berada di area permainan.
    Player player;
    ArrayList<Pipe> pipes;

    // Menandai apakah permainan telah berakhir dan menyimpan skor yang diperoleh.
    boolean isGameOver = false;
    double score = 0;

    public Logic() {
        // Memuat gambar burung untuk tiga kondisi animasi yang berbeda.
        birdUpFlap = loadImage("/assets/images/yellowbird-upflap.png");
        birdMidFlap = loadImage("/assets/images/yellowbird-midflap.png");
        birdDownFlap = loadImage("/assets/images/yellowbird-downflap.png");

        // Memuat gambar pipa atas, pipa bawah, dan latar belakang permainan.
        lowerPipeImage = loadImage("/assets/images/lowerPipe.png");
        upperPipeImage = loadImage("/assets/images/upperPipe.png");
        backgroundImage = loadImage("/assets/images/background.png");

        // Membuat player dengan posisi awal, ukuran, dan gambar burung saat diam.
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdMidFlap);

        // Menyiapkan ArrayList agar pasangan pipa dapat ditambahkan dan dihapus selama permainan.
        pipes = new ArrayList<>();

        // Timer ini memanggil placePipes() setiap 3000 ms untuk menghasilkan pasangan pipa baru.
        pipesCooldown = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                placePipes();
            }
        });

        // Mengatur pemrosesan event timer agar event yang tertunda tidak digabungkan.
        pipesCooldown.setCoalesce(false);
        pipesCooldown.start(); // Memulai timer kemunculan pipa.

        // Timer utama berjalan setiap 16 ms, setara dengan sekitar 60 pembaruan per detik.
        gameLoop = new Timer(16, this);
        gameLoop.setCoalesce(true); // Menggabungkan event yang tertunda agar tidak menumpuk.
        gameLoop.start();

        // Membuat pasangan pipa pertama langsung agar permainan tidak dimulai tanpa rintangan.
        placePipes();

        // Memainkan efek suara saat permainan dimulai.
        playSound("/assets/sfx/swoosh.wav");
    }

    // Mengambil gambar dari resources berdasarkan lokasi file yang diberikan.
    private Image loadImage(String path) {
        URL url = getClass().getResource(path);

        // Jika URL ditemukan, gambar dimuat menggunakan ImageIcon.
        if (url != null) {
            return new ImageIcon(url).getImage();
        } else {
            // Jika file tidak ditemukan, tampilkan pesan dan kembalikan null.
            System.err.println("Gambar tidak ditemukan: " + path);
            return null;
        }
    }

    // Menghubungkan objek View agar Logic dapat memperbarui tampilan permainan.
    public void setView(View view) {
        this.view = view;
    }

    // Getter untuk mengambil data player, daftar pipa, background, status, dan skor.
    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public Image getBackgroundImage() { return backgroundImage; }
    public boolean isGameOver() { return isGameOver; }
    public int getScore() { return (int) score; }

    // Membuat pasangan pipa dengan posisi vertikal yang diacak.
    public void placePipes() {
        // Menghentikan proses pembuatan pipa jika permainan sudah berakhir.
        if (isGameOver) return;

        // Mengacak posisi pipa atas agar setiap pasangan pipa memiliki ketinggian berbeda.
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));

        // Menentukan jarak vertikal antara pipa atas dan pipa bawah.
        int openingSpace = 180;

        // Membuat pipa atas menggunakan posisi acak dan gambar pipa atas.
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe); // Menambahkan pipa atas ke daftar pipa aktif.

        // Membuat pipa bawah dengan posisi yang disesuaikan berdasarkan celah pipa.
        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe); // Menambahkan pipa bawah sebagai pasangan pipa atas.
    }

    // Memperbarui posisi player dan pipa sekaligus memeriksa kondisi permainan.
    public void move() {
        // Tidak melakukan pembaruan jika permainan telah berakhir.
        if (isGameOver) return;

        // Gravitasi menambah kecepatan vertikal player pada setiap siklus permainan.
        player.setVelocityY(player.getVelocityY() + gravity);

        // Posisi vertikal diperbarui berdasarkan kecepatan yang telah dihitung.
        player.setPosY(player.getPosY() + player.getVelocityY());

        // Membatasi posisi player agar tidak bergerak melewati bagian atas layar.
        player.setPosY(Math.max(player.getPosY(), 0));

        // Menyesuaikan gambar burung berdasarkan arah dan kecepatan geraknya.
        updateBirdAnimation();

        // Memeriksa dan memperbarui posisi seluruh pipa yang masih aktif.
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);

            // Menggeser pipa ke kiri menggunakan kecepatan horizontal.
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // Memeriksa apakah area player bertabrakan dengan area pipa.
            if (checkCollision(player, pipe)) {
                playSound("/assets/sfx/hit.wav"); // Memainkan suara tabrakan.
                triggerGameOver(); // Menghentikan permainan akibat tabrakan.
                return; // Menghentikan proses move() agar tidak melanjutkan pemeriksaan.
            }

            // Memastikan setiap pipa hanya dihitung sekali setelah berhasil dilewati player.
            if (!pipe.isPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.setPassed(true); // Menandai pipa sebagai objek yang sudah dilewati.

                // Setiap pipa menambah setengah poin, sehingga satu pasangan menghasilkan satu poin.
                score += 0.5;

                // Memainkan suara ketika skor mencapai bilangan bulat.
                if (score % 1 == 0) {
                    playSound("/assets/sfx/point.wav");
                }

                // Memperbarui label skor pada View jika objek tampilan tersedia.
                if (view != null) {
                    view.updateScoreLabel((int) score);
                }
            }

            // Menghapus pipa yang seluruh bagiannya sudah melewati sisi kiri layar.
            if (pipe.getPosX() + pipe.getWidth() < 0) {
                pipes.remove(i); // Menghapus pipa dari ArrayList agar tidak terus diproses.
                i--; // Mengurangi indeks karena elemen setelahnya bergeser ke posisi sebelumnya.
            }
        }

        // Memeriksa apakah bagian bawah player sudah menyentuh batas bawah area permainan.
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            playSound("/assets/sfx/hit.wav");
            playSound("/assets/sfx/die.wav");
            triggerGameOver(); // Mengakhiri permainan ketika player jatuh.
        }
    }

    // Memilih gambar animasi burung berdasarkan nilai kecepatan vertikal.
    private void updateBirdAnimation() {
        int vy = player.getVelocityY();

        // Kecepatan negatif menunjukkan burung sedang bergerak naik.
        if (vy < -2) {
            player.setImage(birdUpFlap);
        }
        // Kecepatan mendekati nol menunjukkan burung sedang melayang.
        else if (vy <= 2) {
            player.setImage(birdMidFlap);
        }
        // Kecepatan positif yang lebih besar menunjukkan burung sedang jatuh.
        else {
            player.setImage(birdDownFlap);
        }
    }

    // Memeriksa tabrakan antara player dan pipa menggunakan Rectangle.
    private boolean checkCollision(Player p, Pipe pipe) {
        // Membuat area persegi panjang berdasarkan posisi dan ukuran player.
        Rectangle playerRect = new Rectangle(p.getPosX(), p.getPosY(), p.getWidth(), p.getHeight());

        // Membuat area persegi panjang berdasarkan posisi dan ukuran pipa.
        Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

        // Menghasilkan true jika kedua area saling berpotongan.
        return playerRect.intersects(pipeRect);
    }

    // Menghentikan permainan dan menampilkan tombol ketika terjadi Game Over
    private void triggerGameOver() {
        if (isGameOver) return; // Mencegah proses Game Over dijalankan berulang

        isGameOver = true;
        gameLoop.stop(); // Menghentikan pergerakan player dan pipa
        pipesCooldown.stop(); // Menghentikan kemunculan pipa baru

        // Menampilkan tombol Restart dan Exit pada layar Game Over
        if (view != null) {
            view.setGameOverButtonsVisible(true);
        }
    }

    // Mengembalikan player, pipa, skor, dan status permainan ke kondisi awal.
    public void restartGame() {
        playSound("/assets/sfx/swoosh.wav");

        // Mengembalikan posisi player ke titik awal permainan.
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);

        // Menghapus kecepatan sebelumnya agar player tidak langsung bergerak saat restart.
        player.setVelocityY(0);
        player.setImage(birdMidFlap); // Mengembalikan gambar burung ke posisi awal.

        pipes.clear(); // Menghapus semua pipa dari permainan sebelumnya.
        score = 0; // Mengatur ulang skor menjadi nol.
        isGameOver = false; // Mengaktifkan kembali status permainan.

        // Mengatur ulang skor dan menyembunyikan tombol Game Over
        if (view != null) {
            view.updateScoreLabel(0);
            view.setGameOverButtonsVisible(false);
        }

        // Membuat pipa pertama dan menjalankan kembali timer permainan.
        placePipes();
        gameLoop.start();
        pipesCooldown.restart();
    }

    // Memutar efek suara pada thread terpisah agar proses audio tidak menghambat permainan.
    public void playSound(String soundFilePath) {
        new Thread(() -> {
            try {
                // Mengambil file audio dari resources berdasarkan path yang diberikan.
                URL url = getClass().getResource(soundFilePath);

                // Melanjutkan pemrosesan hanya jika file audio berhasil ditemukan.
                if (url != null) {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);

                    // Membuat Clip untuk memuat dan memutar audio.
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);

                    // Menambahkan listener untuk mendeteksi saat audio selesai diputar.
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close(); // Menutup Clip agar resource audio dapat dibebaskan.
                        }
                    });

                    clip.start(); // Memulai pemutaran efek suara.
                }
            } catch (Exception e) {
                // Menampilkan pesan jika file audio gagal dimuat atau diputar.
                System.err.println("Gagal memutar SFX: " + soundFilePath);
            }
        }).start(); // Menjalankan proses audio secara asynchronous.
    }

    // Dipanggil oleh Timer gameLoop untuk menjalankan pembaruan permainan.
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Memperbarui posisi objek, skor, dan kondisi permainan.

        // Meminta View menggambar ulang tampilan setelah logika diperbarui.
        if (view != null) {
            view.repaint();
        }
    }

    // Tidak digunakan karena input permainan diproses melalui keyPressed().
    @Override
    public void keyTyped(KeyEvent e) {}

    // Menangani input keyboard untuk mengendalikan player dan mengulang permainan.
    @Override
    public void keyPressed(KeyEvent e) {
        // Tombol Space membuat player melompat selama permainan belum berakhir.
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isGameOver) {
            player.setVelocityY(-10); // Memberikan kecepatan negatif agar player bergerak ke atas.
            player.setImage(birdUpFlap); // Mengubah animasi burung saat melompat.
            playSound("/assets/sfx/wing.wav"); // Memainkan suara kepakan sayap.
        }

        // Tombol R hanya dapat digunakan untuk restart ketika Game Over.
        if (e.getKeyCode() == KeyEvent.VK_R && isGameOver) {
            restartGame();
        }
    }

    // Tidak digunakan karena permainan tidak memiliki aksi khusus saat tombol dilepas.
    @Override
    public void keyReleased(KeyEvent e) {}
}

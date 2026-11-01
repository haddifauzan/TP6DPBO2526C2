import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    // Menentukan ukuran area permainan yang akan digambar.
    int width = 360;
    int height = 640;

    // Menyimpan referensi Logic untuk mengambil kondisi permainan.
    private Logic logic;

    // Label untuk menampilkan skor pemain.
    private JLabel scoreLabel;

    // Tombol yang ditampilkan ketika permainan berakhir
    private JButton restartButton;
    private JButton exitButton;

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);
        setLayout(null);

        // Mengaktifkan double buffering untuk mengurangi flickering saat animasi berjalan.
        setDoubleBuffered(true);

        // Membuat label skor dan menempatkannya di bagian atas layar.
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(0, 20, width, 40);
        add(scoreLabel);

        // Mengaktifkan penerimaan input keyboard dan menghubungkan KeyListener dari Logic.
        setFocusable(true);
        addKeyListener(logic);

        // Tombol untuk mengulang permainan
        restartButton = new JButton("Restart (R)");
        restartButton.setBounds(80, 350, 200, 40);
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> logic.restartGame());
        add(restartButton);

        // Tombol untuk keluar dari permainan
        exitButton = new JButton("Exit");
        exitButton.setBounds(80, 400, 200, 40);
        exitButton.setVisible(false);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    // Mengubah teks skor ketika Logic memberikan nilai terbaru.
    public void updateScoreLabel(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }

    // Mengatur tampilan tombol Restart dan Exit sesuai kondisi permainan
    public void setGameOverButtonsVisible(boolean visible) {
        restartButton.setVisible(visible);
        exitButton.setVisible(visible);
    }

    // Dipanggil Swing ketika panel perlu digambar ulang.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Membersihkan gambar sebelumnya agar tidak terjadi penumpukan.
        draw(g); // Menggambar seluruh elemen berdasarkan kondisi terbaru permainan.

        // Menyinkronkan rendering grafis agar proses penggambaran lebih stabil.
        Toolkit.getDefaultToolkit().sync();
    }

    // Menggambar seluruh objek permainan berdasarkan data yang tersedia di Logic.
    public void draw(Graphics g) {
        // Menggambar background sebagai lapisan paling belakang.
        if (logic.getBackgroundImage() != null) {
            g.drawImage(logic.getBackgroundImage(), 0, 0, width, height, null);
        }

        // Mengambil data player dari Logic untuk menentukan posisi dan gambar karakter.
        Player player = logic.getPlayer();

        // Memastikan objek dan gambar player tersedia sebelum digambar.
        if (player != null && player.getImage() != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // Mengambil daftar pipa yang sedang aktif dalam permainan.
        ArrayList<Pipe> pipes = logic.getPipes();

        // Memeriksa seluruh pipa menggunakan perulangan.
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);

                // Menggambar pipa hanya jika gambar objek tersedia.
                if (pipe.getImage() != null) {
                    g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                            pipe.getWidth(), pipe.getHeight(), null);
                }
            }
        }

        // Menampilkan tampilan khusus ketika status permainan sudah Game Over.
        if (logic.isGameOver()) {
            // Membuat lapisan hitam transparan untuk meredupkan area permainan.
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, width, height);

            // Mengatur warna, ukuran font, dan posisi tulisan Game Over.
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));

            // Mengambil ukuran teks agar tulisan dapat diposisikan tepat di tengah.
            FontMetrics fm1 = g.getFontMetrics();
            String msg1 = "GAME OVER";

            // Menghitung posisi horizontal berdasarkan lebar teks.
            g.drawString(msg1, (width - fm1.stringWidth(msg1)) / 2, height / 2 - 20);

            // Mengatur tampilan petunjuk untuk mengulang permainan.
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));

            // Menghitung lebar teks petunjuk agar posisinya tetap berada di tengah.
            FontMetrics fm2 = g.getFontMetrics();
            String msg2 = "Press 'R' to Restart";

            g.drawString(msg2, (width - fm2.stringWidth(msg2)) / 2, height / 2 + 25);
        }
    }
}

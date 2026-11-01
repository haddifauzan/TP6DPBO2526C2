import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainMenu extends JPanel {
    // Menentukan dimensi panel menu utama.
    int width = 360;
    int height = 640;

    private Image backgroundImage;

    public MainMenu(JFrame frame) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);
        setLayout(null); // Menggunakan posisi manual untuk mengatur letak komponen.

        // Mengambil lokasi gambar background dari folder resources.
        URL bgUrl = getClass().getResource("/assets/images/background.png");

        // Memuat gambar hanya jika file berhasil ditemukan.
        if (bgUrl != null) {
            backgroundImage = new ImageIcon(bgUrl).getImage();
        }

        // Membuat label judul dengan posisi dan ukuran tertentu.
        JLabel titleLabel = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 150, width, 50);
        add(titleLabel);

        // Membuat tombol untuk berpindah ke halaman permainan.
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBounds(90, 280, 180, 45);
        startButton.setFocusPainted(false);

        // Menjalankan startGame() ketika tombol Start Game diklik.
        startButton.addActionListener(e -> App.startGame(frame));
        add(startButton);

        // Membuat tombol untuk menutup aplikasi.
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setBounds(90, 345, 180, 45);
        exitButton.setFocusPainted(false);

        // Menghentikan seluruh aplikasi ketika tombol Exit ditekan.
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    // Menggambar background pada panel menu utama.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Membersihkan area gambar sebelum menggambar ulang.

        // Menampilkan background jika gambar berhasil dimuat.
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, null);
        }
    }
}

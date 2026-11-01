import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Mengaktifkan akselerasi hardware Java2D untuk membantu proses rendering.
        System.setProperty("sun.java2d.opengl", "true");

        // Menjalankan GUI pada Event Dispatch Thread agar interaksi Swing berjalan aman.
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FlappyBird");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(360, 640);
            frame.setLocationRelativeTo(null); // Menempatkan jendela di tengah layar.
            frame.setResizable(false); // Menjaga ukuran area permainan tetap sesuai desain.

            // Membuat dan memasang menu utama sebagai tampilan pertama aplikasi.
            MainMenu menu = new MainMenu(frame);
            frame.add(menu);
            frame.pack(); // Menyesuaikan ukuran frame dengan preferred size menu.
            frame.setVisible(true);
        });
    }

    // Berpindah dari menu utama ke tampilan permainan.
    public static void startGame(JFrame frame) {
        frame.getContentPane().removeAll(); // Menghapus seluruh komponen menu sebelumnya.

        // Membuat objek Logic sebagai pengatur permainan dan View sebagai tampilannya.
        Logic logika = new Logic();
        View tampilan = new View(logika);
        logika.setView(tampilan); // Menghubungkan Logic dengan View agar keduanya dapat berkomunikasi.

        // Memasang tampilan permainan ke dalam frame.
        frame.add(tampilan);
        frame.revalidate(); // Memperbarui susunan komponen setelah pergantian tampilan.
        frame.repaint(); // Menggambar ulang frame agar tampilan terbaru muncul.

        // Memberikan fokus ke View agar tombol keyboard dapat langsung digunakan.
        tampilan.requestFocusInWindow();
    }
}

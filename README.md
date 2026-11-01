# Flappy Bird

Aplikasi permainan desktop berbasis Java yang mengadaptasi permainan Flappy Bird menggunakan Java Swing. Pemain mengendalikan burung untuk melewati rintangan berupa pipa dengan menghindari tabrakan dan menjaga agar burung tidak terjatuh. Program ini dilengkapi dengan sistem skor, kondisi Game Over, fitur restart, Main Menu, serta sound effect.

## 1. Desain dan Fitur Program

Flappy Bird memiliki antarmuka permainan dengan ukuran jendela 360 × 640 piksel. Permainan terdiri dari halaman Main Menu dan halaman Gameplay.

### Fitur Utama

- **Main Menu:** Menampilkan judul permainan serta tombol Start Game untuk memulai permainan dan Exit untuk keluar dari aplikasi.
- **Kontrol Player:** Pemain dapat mengendalikan burung menggunakan tombol Space pada keyboard untuk membuat burung terbang.
- **Animasi Player:** Tampilan burung berubah mengikuti kondisi pergerakannya, yaitu terbang ke atas, posisi normal, dan jatuh.
- **Rintangan Pipa:** Pipa atas dan bawah muncul secara berkala dengan posisi vertikal yang bervariasi sehingga pemain harus mengatur pergerakan burung untuk melewatinya.
- **Sistem Skor:** Skor bertambah satu poin setiap kali pemain berhasil melewati satu pasangan pipa. Skor ditampilkan melalui JLabel pada layar permainan.
- **Collision Detection:** Program mendeteksi tabrakan antara burung dan pipa menggunakan pemeriksaan area objek.
- **Game Over:** Permainan dihentikan ketika burung menabrak pipa atau jatuh hingga menyentuh batas bawah jendela permainan.
- **Restart Game:** Pemain dapat memulai ulang permainan dengan menekan tombol R atau menggunakan tombol Restart yang muncul ketika Game Over. Skor, posisi burung, dan rintangan akan diatur ulang.
- **Exit:** Pemain dapat keluar dari aplikasi melalui tombol Exit pada Main Menu maupun ketika permainan berakhir.
- **Sound Effect:** Permainan dilengkapi efek suara untuk mendukung interaksi, seperti suara kepakan sayap, tabrakan, mendapatkan skor, memulai permainan, dan Game Over.

## 2. Kontrol Permainan

| Tombol | Fungsi |
|---|---|
| Space | Membuat burung terbang ke atas |
| R | Mengulang permainan setelah Game Over |
| Start Game | Memulai permainan dari Main Menu |
| Restart | Memulai ulang permainan setelah Game Over |
| Exit | Keluar dari aplikasi |

## 3. Penjelasan Alur Program

Berikut merupakan alur penggunaan aplikasi Flappy Bird dari awal hingga permainan berakhir.

1. **Menjalankan Program**

   Ketika program dijalankan, aplikasi menampilkan halaman Main Menu yang berisi judul Flappy Bird serta dua tombol, yaitu Start Game dan Exit.

2. **Memulai Permainan**

   Pemain menekan tombol Start Game untuk masuk ke halaman Gameplay. Sistem menginisialisasi player, rintangan pipa, skor awal, dan timer permainan.

3. **Mengendalikan Player**

   Pemain menggunakan tombol Space untuk membuat burung terbang. Ketika tombol ditekan, burung bergerak ke atas, sedangkan gravitasi membuat burung kembali bergerak ke bawah.

4. **Melewati Rintangan dan Mendapatkan Skor**

   Pipa muncul secara berkala dengan celah yang harus dilewati pemain. Setiap berhasil melewati satu pasangan pipa, skor bertambah satu poin dan ditampilkan pada layar permainan.

5. **Mendeteksi Game Over**

   Sistem memeriksa tabrakan antara burung dengan pipa serta kondisi ketika burung menyentuh batas bawah jendela. Jika salah satu kondisi terpenuhi, permainan dihentikan dan tampilan Game Over ditampilkan.

6. **Mengulang Permainan**

   Setelah Game Over, pemain dapat menekan tombol R pada keyboard atau memilih tombol Restart. Sistem mengembalikan posisi burung, menghapus rintangan sebelumnya, mengatur skor menjadi nol, dan menjalankan kembali permainan.

7. **Keluar dari Permainan**

   Pemain dapat memilih tombol Exit pada Main Menu atau pada tampilan Game Over untuk menutup aplikasi.

## 4. Teknologi yang Digunakan

- **Bahasa Pemrograman:** Java
- **GUI:** Java Swing
- **Grafis:** Java AWT
- **Audio:** Java Sound API
- **IDE:** IntelliJ IDEA

## 5. Dokumentasi Program

Video berikut memperlihatkan demonstrasi aplikasi Flappy Bird, mulai dari Main Menu, gameplay, penggunaan kontrol, sistem skor, kondisi Game Over, fitur restart, hingga tombol Exit.

**Video Demonstrasi:**

[Flappy Bird - TP6 DPBO 2025/2026](https://youtube.com/shorts/9laqGYhESrA?feature=share)

## 6. Struktur Project

```text
FlappyBird/
├── Dokumentasi/
│   └── FlappyBird_Demo.mp4
├── src/
│   ├── assets/
│   │   ├── images/
│   │   │   ├── background.png
│   │   │   ├── lowerPipe.png
│   │   │   ├── upperPipe.png
│   │   │   ├── yellowbird-upflap.png
│   │   │   ├── yellowbird-midflap.png
│   │   │   └── yellowbird-downflap.png
│   │   └── sfx/
│   │       ├── die.wav
│   │       ├── hit.wav
│   │       ├── point.wav
│   │       ├── swoosh.wav
│   │       └── wing.wav
│   ├── App.java
│   ├── Logic.java
│   ├── MainMenu.java
│   ├── Pipe.java
│   ├── Player.java
│   └── View.java
└── README.md
```

## 7. Penjelasan Class

### App.java

Menjadi titik awal program yang menginisialisasi JFrame dan menampilkan Main Menu. Class ini juga mengatur perpindahan tampilan dari Main Menu menuju Gameplay.

### MainMenu.java

Mengatur tampilan halaman utama permainan, termasuk judul serta tombol Start Game dan Exit.

### Logic.java

Mengatur mekanisme utama permainan, seperti pergerakan player, gravitasi, kemunculan pipa, collision detection, sistem skor, kondisi Game Over, restart, dan pemutaran sound effect.

### View.java

Mengatur tampilan Gameplay, termasuk menggambar background, player, pipa, JLabel skor, serta tampilan Game Over dan tombol Restart maupun Exit.

### Player.java

Merepresentasikan objek burung yang dikendalikan pemain. Class ini menyimpan posisi, ukuran, gambar, dan kecepatan vertikal player.

### Pipe.java

Merepresentasikan objek rintangan pipa yang memiliki posisi, ukuran, gambar, serta status apakah pipa sudah dilewati oleh player.

---
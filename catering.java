import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class catering {
    static Scanner scan = new Scanner(System.in);
    static LocalDate date = LocalDate.now();

    // mendapatkan inputan dari pilihan menu program
    public static String getMenuProgram(String[] menu, String ornamen) {
        String pilihan;
        System.out.println();
        System.out.println(ornamen);

        System.out.println();

        for (int i = 0; i < menu.length; i++) {
            if (i == 0) {
                System.out.println("\tMenu");
            }
            System.out.printf("%d. %s\n", i + 1, menu[i]);
        }

        System.out.print("Masukkan pilihan anda : ");
        pilihan = scan.nextLine();

        return pilihan;
    }

    // fungsi mendapatkan informasi dari customer
    public static String getInfoCustomer(String[][] customer) {
        String nama = "";
        boolean notSame = false;
        for (int i = 0; i < customer.length; i++) {
            if (customer[i][0] == null) {
                do {
                    System.out.print("Nama Customer : ");
                    nama = scan.nextLine();
                    int check = findData(customer, nama);
                    if (check == customer.length) {
                        customer[i][0] = nama;
                        customer[i][1] = "Belum Lunas";
                        customer[i][2] = "-";
                        customer[i][3] = "-";
                        customer[i][4] = "-";
                        customer[i][5] = "-";
                        customer[i][6] = "-";
                        getTanggalPemesanan(customer, nama, 2);
                        notSame = true;
                    } else {
                        System.out.println("Nama sudah tersedia");
                    }
                } while (!notSame);
                break;
            }
        }
        return nama;
    }

    // fungsi menerima tanggal pemesanan
    public static void getTanggalPemesanan(String[][] customer, String nama, int pilihanTanggal) {
        String formatTanggal[] = { "bulan", "Tanggal" };
        int[] tanggal = { 0, 0, 0 };
        String tanggalfnl;
        int target = findData(customer, nama);
        System.out.println("Tanggal Hari Ini : " + date);

        for (int i = 0; i < customer.length; i++) {
            if (customer[i][2] == null) {
                for (int j = 0; j < tanggal.length; j++) {
                    if (j < formatTanggal.length) {
                        System.out.printf("Pesan di %s Keberapa : ", formatTanggal[j]);
                        tanggal[j] = scan.nextInt();
                        scan.nextLine();
                        if (j == 0) {
                            tanggal[j] %= 12;
                            if (tanggal[j] == 0) {
                                tanggal[j] = 12;
                            }
                        } else if (j == 1) {
                            if (tanggal[j] <= 7 && tanggal[j] % 2 == 0 && tanggal[j] != 2
                                    || tanggal[j] > 7 && tanggal[j] % 2 == 1) {
                                tanggal[j] %= 30;
                                if (tanggal[j] == 0) {
                                    tanggal[j] = 30;
                                }
                            } else if (tanggal[j] <= 7 && tanggal[j] % 2 == 1 && tanggal[j] != 2
                                    || tanggal[j] > 7 && tanggal[j] % 2 == 0) {
                                tanggal[j] %= 31;
                                if (tanggal[j] == 0) {
                                    tanggal[j] = 31;
                                }
                            }
                        }
                    } else {
                        if (date.getDayOfMonth() + 4 < tanggal[1] && date.getMonthValue() <= tanggal[0]) {
                            tanggal[j] = date.getYear();

                        } else {
                            tanggal[j] = date.getYear() + 1;
                        }
                    }

                    if (tanggal[0] == 2) {
                        if (tanggal[2] % 4 == 0) {
                            tanggal[1] %= 29;
                        } else {
                            tanggal[1] %= 28;
                        }
                    }
                }
                break;
            }
        }

        tanggalfnl = String.format("%02d-%02d-%04d", tanggal[1], tanggal[0], tanggal[2]);
        customer[target][pilihanTanggal] = tanggalfnl;

    }

    // program untuk menu dari catering
    public static void printMenuCatering(String[] menu, int[] harga) {
        int terpanjang = checkMaxLength1D(0, menu);
        String[] hargaString = new String[harga.length];
        for (int i = 0; i < harga.length; i++) {
            hargaString[i] = Integer.toString(harga[i]);
        }

        int hrgPnjg = checkMaxLength1D(0, hargaString);
        terpanjang += 4;
        hrgPnjg += 5;

        for (int i = 0; i < menu.length; i++) {
            if (i == 0) {
                System.out.print(" NO |");
                print(terpanjang, "Menu".length(), "Menu");
                print(hrgPnjg, "Harga".length(), "Harga");
                System.out.println();
            }
            System.out.printf(" %2d |", i + 1);

            print(terpanjang, menu[i].length(), menu[i]);

            print(hrgPnjg, Integer.toString(harga[i]).length(), Integer.toString(harga[i]));

            System.out.println();
        }

    }

    // program untuk mengambil masukan dari catering
    public static void getMenuCatering(int[][] pesananPelanggan,
            String[][] pelanggan, String target, int[] tagihanPelanggan, String[] text, int[] harga, String[] menu,
            int[] hargaOngkir, String[] jenisPengiriman) {

        int menuPesanan, pelangganKe, pengiriman;

        boolean isEnough, isRepeat;
        String alamat, tambah;
        pelangganKe = findData(pelanggan, target);
        do {
            isEnough = false;
            do {
                System.out.print("Anda ingin pesan menu yang keberapa :");
                menuPesanan = scan.nextInt();
                if (menuPesanan - 1 < pesananPelanggan[pelangganKe].length) {
                    isEnough = true;
                } else {
                    System.out.println("menu tidak tersedia");
                }
            } while (!isEnough);
            System.out.print("Berapa banyak jumlah porsi : ");
            pesananPelanggan[pelangganKe][menuPesanan - 1] += scan.nextInt();
            scan.nextLine();
            System.out.print("apakah anda ingin menambahkan menu lain : ");
            tambah = scan.nextLine();
        } while (tambah.charAt(0) == 'y' || tambah.charAt(0) == 'Y');

        isRepeat = false;
        do {
            System.out.println("pilih metode untuk pengiriman ");
            for (int i = 0; i < jenisPengiriman.length; i++) {
                System.out.printf("%s. %s\n", i + 1, jenisPengiriman[i]);
            }
            System.out.print("Masukkan pilihan anda : ");
            pengiriman = scan.nextInt();
            scan.nextLine();

            if (pengiriman <= jenisPengiriman.length && pengiriman != 0) {
                pelanggan[pelangganKe][4] = jenisPengiriman[pengiriman - 1];
                isRepeat = true;
                // } else if (pengiriman == 2) {
                // pelanggan[pelangganKe][4] = jenisPengiriman[pengiriman - 1];
                // isRepeat = true;
            } else {
                System.out.println("Diluar kriteria");
                isRepeat = false;
            }

        } while (!isRepeat);

        do {

            System.out.print("Masukkan Alamat pengiriman : ");
            alamat = scan.nextLine();
            if (alamat.length() > 7) {
                pelanggan[pelangganKe][6] = alamat;
                isRepeat = true;
            } else {
                System.out.println("Alamat yang anda masukkan kurang spesifik");
                isRepeat = false;
            }
        } while (!isRepeat);

        System.out.println("Pesanan anda :");
        tagihanPelanggan = hitungTotal(pelanggan, pesananPelanggan, harga, hargaOngkir, jenisPengiriman);
        printPesananPelanggan(pesananPelanggan, harga, menu, pelanggan, tagihanPelanggan, text,
                pelanggan[pelangganKe][0]);
        System.out.println("\n\n");

    }

    public static int[] hitungTotal(String[][] pelanggan, int[][] pesananPelanggan, int[] harga, int[] hargaOngkir,
            String[] jenisPengiriman) {
        int tagihanPelanggan[] = new int[pesananPelanggan.length];
        for (int i = 0; i < pesananPelanggan.length; i++) {
            if (pelanggan[i][0] != null) {
                for (int j = 0; j < pesananPelanggan[i].length; j++) {
                    tagihanPelanggan[i] += pesananPelanggan[i][j] * harga[j];
                }
                if (pelanggan[i][4].equalsIgnoreCase(jenisPengiriman[0])) {
                    tagihanPelanggan[i] += hargaOngkir[0];
                } else if (pelanggan[i][4].equalsIgnoreCase(jenisPengiriman[1])) {
                    tagihanPelanggan[i] += hargaOngkir[1];
                }
            }
        }
        return tagihanPelanggan;
    }

    public static int hitungTarget(int[] tagihanPelanggan, String[][] pelanggan, int[] hargaOngkir,
            String[] jenisPengiriman, int target) {
        int tempVar = 0;
        if (pelanggan[target][0] != null) {
            for (int j = 0; j < jenisPengiriman.length; j++) {
                for (int i = 0; i < pelanggan[target].length; i++) {
                    if (pelanggan[target][i].equalsIgnoreCase(jenisPengiriman[j])) {
                        tempVar = hargaOngkir[j];
                        tagihanPelanggan[target] += tempVar;
                    }
                }
            }
        }
        return tempVar;
    }

    public static void returnhitungAll(int[] tagihanPelanggan, String[][] pelanggan, int[] hargaOngkir,
            String[] jenisPengiriman, String target) {

        for (int z = 0; z < jenisPengiriman.length; z++) {
            for (int i = 0; i < pelanggan.length; i++) {
                for (int j = 0; j < pelanggan[i].length; j++) {
                    if (pelanggan[i][j] != null) {
                        if (pelanggan[i][j].equalsIgnoreCase(jenisPengiriman[z]) && target.equalsIgnoreCase("tambah")) {
                            tagihanPelanggan[i] += hargaOngkir[z];
                            break;
                        } else if (pelanggan[i][j].equalsIgnoreCase(jenisPengiriman[z])
                                && target.equalsIgnoreCase("kurang")) {
                            tagihanPelanggan[i] -= hargaOngkir[z];
                            break;
                        }
                    }

                }

            }

        }

    }

    public static void transaksi(int[] harga, int[] tagihanPelanggan, int[][] pesananPelanggan,
            String[][] pelanggan, int pelangganKe, int[] hargaOngkir, String[] menuTransaksi, String[] debit,
            int[] adminDebit, String[] jenisPengiriman) {

        int ongkir, pembayaran, mtdPembayaran, nominal, total1, dpMinimal;
        boolean isNgulang;

        ongkir = hitungTarget(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, pelangganKe);

        System.out.printf("Ongkir : %s \nTotal : %s \n", ongkir, tagihanPelanggan[pelangganKe]);

        for (int i = 0; i < menuTransaksi.length; i++) {
            System.out.printf("%s. %s\n", i + 1, menuTransaksi[i]);
        }

        do {
            System.out.print("Masukan pilihan anda : ");
            pembayaran = scan.nextInt();
            scan.nextLine();
            if (pembayaran > menuTransaksi.length || pembayaran <= 0) {
                System.out.println("Maaf menu yang anda pilih tidak ada");
            }
        } while (pembayaran > menuTransaksi.length || pembayaran <= 0);

        pelanggan[pelangganKe][5] = menuTransaksi[pembayaran - 1];
        if (pembayaran == 1) {
            isNgulang = false;
            do {
                System.out.printf("ingin menggunakan bank apa?\n");
                for (int i = 0; i < debit.length; i++) {
                    System.out.printf("%s. %s\n", i + 1, debit[i]);
                }
                System.out.print("Masukan pilihan anda : ");
                mtdPembayaran = scan.nextInt();
                scan.nextLine();

                switch (mtdPembayaran) {
                    case 1, 2, 3:
                        isNgulang = true;
                        tagihanPelanggan[pelangganKe] += adminDebit[mtdPembayaran - 1];
                        System.out.println("totalnya jadi " + tagihanPelanggan[pelangganKe]);
                        System.out.println("menggunakan pembayaran debit " + debit[mtdPembayaran - 1]);
                        pelanggan[pelangganKe][1] = "Lunas";
                        System.out.println("status : " + pelanggan[pelangganKe][1]);
                        pelanggan[pelangganKe][3] = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date);
                        break;

                    default:
                        isNgulang = false;
                        break;
                }
            } while (!isNgulang);

        } else if (pembayaran == 2) {
            do {
                System.out.print("Berapa nominal cash : ");
                nominal = scan.nextInt();
                scan.nextLine();
                total1 = 0;

                if (nominal >= 0) {
                    total1 = tagihanPelanggan[pelangganKe] - nominal;
                    if (total1 > 0) {
                        tagihanPelanggan[pelangganKe] = total1;
                        System.out.println("Masih kurang " + tagihanPelanggan[pelangganKe]);
                        System.out.println("status : Belum Lunas");
                    } else if (total1 < 0) {
                        nominal -= tagihanPelanggan[pelangganKe];
                        pelanggan[pelangganKe][1] = "Lunas";
                        System.out.println("status : lunas");
                        System.out.println("kembalian anda adalah " + nominal);
                        pelanggan[pelangganKe][3] = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date);
                        tagihanPelanggan = hitungTotal(pelanggan, pesananPelanggan, harga, hargaOngkir,
                                jenisPengiriman);
                    }
                } else {
                    System.out.println("Nominal yang anda masukkan tidak sesuai");
                }
            } while (!(nominal >= 0) || pelanggan[pelangganKe][1].equalsIgnoreCase("belum lunas"));
        } else if (pembayaran == 3) {
            dpMinimal = (int) (tagihanPelanggan[pelangganKe] * 0.30);
            System.out.printf("Dp minimal 33 dari tagihan\nTagihan : %s \nDP minimal : %s\n",
                    tagihanPelanggan[pelangganKe],
                    dpMinimal);
            tagihanPelanggan[pelangganKe] -= dpMinimal;
            System.out.println("Jadi total akhir : " + tagihanPelanggan[pelangganKe]);
        }

    }

    public static int checkMaxLength2D(int nilaiAwal, int checkTarget, String[][] Data) {
        int max = nilaiAwal;
        for (int i = 0; i < Data.length; i++) {
            if (Data[i][checkTarget] != null) {
                if (Data[i][checkTarget].length() > max) {
                    max = Data[i][checkTarget].length();
                }
            }
        }
        return max;
    }

    public static int checkMaxLength1D(int nilaiAwal, String[] Data) {
        int max = nilaiAwal;
        for (int i = 0; i < Data.length; i++) {
            if (Data[i].length() > max) {
                max = Data[i].length();
            }
        }
        return max;
    }

    public static void print(int max, int nilai, String name) {
        for (int i = 0; i < max - nilai; i++) {
            if (i == (max - nilai) / 2) {
                System.out.printf("%s", name);
            } else {
                System.out.print(" ");
            }
        }
        System.out.print("|");
    }

    public static void slicePrintDataFirst(String[] Data, int max1, int max2, int max3, int i) {
        if (i == 0) {
            System.out.print(" NO |");

            for (int j = 0; j < Data.length; j++) {
                if (j == 1) {
                    print(max1, Data[j].length(), Data[j]);
                } else if (j == 6) {
                    print(max2, Data[j].length(), Data[j]);
                } else {
                    print(max3, Data[j].length(), Data[j]);

                }
            }

            System.out.println();
        }
    }

    public static int slicePrintDataSecond(String[][] Data, int[] Data2, int max1, int max2, int max3, int counter,
            int i) {
        counter++;
        System.out.printf(" %2d |", counter);
        for (int j = 0; j < Data[i].length; j++) {
            if (j == 1) {
                print(max1, Data[i][j].length(), Data[i][j]);
            } else if (j == 6) {
                print(max2, Data[i][j].length(), Data[i][j]);
            } else {
                print(max3, Data[i][j].length(), Data[i][j]);
            }

        }
        print(max3, Integer.toString(Data2[i]).length(),
                Integer.toString(Data2[i]));

        System.out.println();
        return counter;
    }

    public static void printDataPelanggan(String[][] Data, String[] Data2, int[] tagihanPelanggan, String... target) {
        int max, maxAdress, max1D, maxStatus, counter;

        max = "nama Pelanggan".length();
        max = checkMaxLength2D(max, 0, Data);

        maxAdress = checkMaxLength2D(Data2[6].length(), 6, Data);

        max1D = checkMaxLength1D(0, Data2);

        maxStatus = checkMaxLength2D(Data2[1].length(), 1, Data);

        counter = 0;
        max += 3;
        max1D += 3;
        maxAdress += 3;
        maxStatus += 3;

        System.out.println("\nData pelanggan");

        if (target.length != 0) {
            for (int h = 0; h < target.length; h++) {
                for (int i = 0; i < Data.length; i++) {

                    slicePrintDataFirst(Data2, maxStatus, maxAdress, max1D, i);

                    for (int z = 0; z < Data[i].length; z++) {
                        if (Data[i][z] != null && Data[i][z].equalsIgnoreCase(target[h])) {

                            counter = slicePrintDataSecond(Data, tagihanPelanggan, maxStatus, maxAdress, max1D, counter,
                                    i);
                        }

                    }

                }
            }
        } else {
            for (int i = 0; i < Data.length; i++) {

                slicePrintDataFirst(Data2, maxStatus, maxAdress, max1D, i);

                if (Data[i][0] != null) {

                    counter = slicePrintDataSecond(Data, tagihanPelanggan, maxStatus, maxAdress, max1D, counter, i);
                }

            }
        }

    }

    public static void slicePrintPesananFirst(String[] Data, String[] Data2, int max, int maxNama, int i) {
        print(maxNama, Data2[i].length(), Data2[i]);

        for (int j = 0; j < Data.length; j++) {
            print(max, Data[j].length(), Data[j]);

        }
        System.out.print("\tTotal");

        System.out.println();
    }

    public static void slicePrintPesananSecond(String[][] Data, int[][] Data2, int[] Data3, int max, int maxNama,
            int i) {
        print(maxNama, Data[i][0].length(), Data[i][0]);

        for (int j = 0; j < Data2[i].length; j++) {
            print(max, Integer.toString(Data2[i][j]).length(),
                    Integer.toString(Data2[i][j]));

        }

        System.out.printf(" %d", Data3[i]);
        System.out.println();
    }

    public static void printPesananPelanggan(int[][] pesananPelanggan, int[] harga, String[] menu, String[][] pelanggan,
            int[] tagihanPelanggan, String[] menu1, String... target) {

        int max, maxNama, indexMenu;

        max = checkMaxLength1D(0, menu);
        maxNama = "nama Pelanggan".length();
        maxNama = checkMaxLength2D(maxNama, 0, pelanggan);

        max += 3;
        maxNama += 3;
        indexMenu = target.length;

        System.out.println("\nPesanan Pelanggan");

        if (target.length != 0) {
            for (int h = 0; h < target.length; h++) {
                for (int i = 0; i < menu.length; i++) {
                    if (menu[i].equalsIgnoreCase(target[h])) {
                        indexMenu = i;
                        break;
                    }
                }
            }
            for (int h = 0; h < target.length; h++) {
                for (int i = 0; i < pelanggan.length; i++) {
                    if (pelanggan[i][0] != null) {
                        if (i == 0) {
                            System.out.println("Status : " + target[h]);

                            slicePrintPesananFirst(menu, menu1, max, maxNama, i);

                        }

                        for (int u = 0; u < pelanggan[i].length; u++) {
                            if (pelanggan[i][u] != null && (pelanggan[i][u].equalsIgnoreCase(target[h]))) {

                                slicePrintPesananSecond(pelanggan, pesananPelanggan, tagihanPelanggan, max, maxNama, i);
                            }
                        }
                        if (indexMenu != target.length && pesananPelanggan[i][indexMenu] != 0) {

                            slicePrintPesananSecond(pelanggan, pesananPelanggan, tagihanPelanggan, max, maxNama, i);

                        }

                    }
                }
            }
        } else {
            for (int i = 0; i < pesananPelanggan.length; i++) {
                if (pelanggan[i][0] != null) {
                    if (i == 0) {

                        slicePrintPesananFirst(menu, menu1, max, maxNama, i);

                    }

                    slicePrintPesananSecond(pelanggan, pesananPelanggan, tagihanPelanggan, max, maxNama, i);
                }

            }
        }
    }

    public static String[] search(String[][] pelanggan) {
        String target[], tempVar, pastValue = null, more;
        boolean moreParameter = true;
        int a = 0;
        do {

            System.out.printf("Temukan datamu : ");
            tempVar = scan.nextLine();

            target = new String[a + 1];
            target[a] = tempVar;
            if (target.length > 1) {
                target[a - 1] = pastValue;
            }
            System.out.print("apakah ingin menambahkan parameter lagi : ");
            more = scan.nextLine();

            if (tempVar.equalsIgnoreCase("stop")) {
                return target;
            }

            if (more.charAt(0) == 'y' || more.charAt(0) == 'Y') {
                pastValue = target[a];
                moreParameter = false;
                a++;
            }
            if (a == 3) {
                System.out.println("Maaf parameter minimal 3");
                moreParameter = true;
            }

        } while (!moreParameter);

        return target;
    }

    public static int findData(String[][] Data, String target) {
        int index = Data.length;
        for (int i = 0; i < Data.length; i++) {
            for (int j = 0; j < Data[i].length; j++) {
                if (Data[i][j] != null) {
                    if (Data[i][j].equalsIgnoreCase(target)) {
                        index = i;
                        break;
                    }
                }
            }
        }
        return index;
    }

    public static void tambahPesanan(int[][] pesananPelanggan, String[] menu, int[] harga, int pelangganKe) {
        String tambah;
        int tempVar, menuPesanan;
        boolean isEnough;

        do {
            do {
                printMenuCatering(menu, harga);
                System.out.print("Anda ingin tambah menu yang keberapa :");
                menuPesanan = scan.nextInt();
                if (menuPesanan - 1 < pesananPelanggan[pelangganKe].length) {
                    isEnough = true;
                } else {
                    System.out.println("menu tidak tersedia");
                    isEnough = false;
                }
            } while (!isEnough);

            do {
                System.out.print("Berapa banyak yang ingin ditambah : ");
                tempVar = scan.nextInt();
                scan.nextLine();
                if (tempVar > 0) {
                    pesananPelanggan[pelangganKe][menuPesanan - 1] += tempVar;
                    isEnough = true;
                } else {
                    System.out.println("Maaf yang anda masukkan tidak sesuai");
                    isEnough = false;
                }
            } while (!isEnough);
            System.out.print("apakah anda ingin menambahkan di menu lain : ");
            tambah = scan.nextLine();
        } while (tambah.charAt(0) == 'y' || tambah.charAt(0) == 'Y');
    }

    public static void kurangPesanan(int[][] pesananPelanggan, String[] menu, int[] harga, int pelangganKe) {
        String tambah;
        int menuPesanan, bnykKurang;
        boolean isEnough;

        printMenuCatering(menu, harga);
        do {
            isEnough = false;

            do {
                System.out.print("Anda ingin mengurangi menu yang keberapa :");
                menuPesanan = scan.nextInt();
                scan.nextLine();
                if (menuPesanan - 1 < pesananPelanggan[pelangganKe].length && menuPesanan != 0) {
                    if (pesananPelanggan[pelangganKe][menuPesanan - 1] != 0) {
                        isEnough = true;
                    } else {
                        System.out.println("Anda masih belum memiliki pesanan menu " + menu[menuPesanan - 1]);
                    }
                } else {
                    System.out.println("menu tidak tersedia");
                }
            } while (!isEnough);

            isEnough = false;
            do {

                System.out.print("Berapa banyak yang ingin dikurangi : ");
                bnykKurang = scan.nextInt();
                scan.nextLine();
                if (pesananPelanggan[pelangganKe][menuPesanan - 1] - bnykKurang >= 0) {
                    pesananPelanggan[pelangganKe][menuPesanan - 1] -= bnykKurang;
                    isEnough = true;
                } else {
                    System.out.println("pesanan anda hanya " + pesananPelanggan[pelangganKe][menuPesanan - 1]);
                }
            } while (!isEnough);
            System.out.print("apakah anda ingin mengurangi di menu lain : ");
            tambah = scan.nextLine();
        } while (tambah.charAt(0) == 'y' || tambah.charAt(0) == 'Y');
    }

    public static void hapusDataPelanggan(String Data[][], int Data2[][], int[] Data3, String[] DataPendukung,
            int target) {
        int index;
        String nama;
        if (target == 1) {
            printDataPelanggan(Data, DataPendukung, Data3);
            System.out.print("Anda ingin menghapus data siapa: ");
            nama = scan.nextLine();
            index = findData(Data, nama);
            if (index != Data.length) {
                for (int i = 0; i < Data[index].length; i++) {
                    if (Data[index][i] != null) {
                        Data[index][i] = null;
                    }
                }
                for (int i = 0; i < Data2[index].length; i++) {
                    if (Data2[index][i] != 0) {
                        Data2[index][i] = 0;
                    }
                }
                System.out.println("Data sudah terhapus");
            } else {
                System.out.println("Data tidak ditemukan");
            }

        } else if (target == 2) {
            for (int i = 0; i < Data.length; i++) {
                for (int j = 0; j < Data[i].length; j++) {
                    if (Data[i][j] != null) {
                        Data[i][j] = null;
                    }
                }

                for (int j = 0; j < Data2[j].length; j++) {
                    if (Data2[i][j] != 0) {
                        Data2[i][j] = 0;
                    }
                }
            }
            System.out.println("Semua data sudah terhapus");
        }
    }

    public static void main(String[] args) {
        String[][] pelanggan = {
                { "Thoriq", "Belum Lunas", "07-09-2023", "-", "Luar Daerah", "-", "Jl.Brawijaya No.14" },
                { null, null, null, null, null, null, null },
                { "Rifki", "Belum Lunas", "24-12-2022", "-", "Dalam Daerah", "-", "MT Hariono no 14" },
                { null, null, null, null, null, null, null },
                { "Rofiq", "Belum Lunas", "04-02-2023", "-", "Luar Daerah", "-", "jln Soekarno" },
                { null, null, null, null, null, null, null }, { null, null, null, null, null, null, null } };
        String[] text = { "Nama Pelanggan", "Status", "Tanggal Pemesanan", "Tanggal Pembayaran", "Pengiriman",
                "Metode Pembayaran", "Alamat Pelanggan", "Nominal" };
        String[] menu = { "Nasi Goreng", "Mie Goreng", "Nasi Campur", "Es teh", "Boba" };
        String[] menuProgram = { "Pemesanan", "Tambah Pesanan", "Kurangi Pesanan", "Transaksi",
                "Pengecekan status pembayaran", "Pencarian data",
                "Pelaporan data", "Hapus Data Pelanggan", "Shutdown" };
        String[] menuPembayaran = { "Debit", "Cash", "DP" };
        String[] jenisPengiriman = { "Luar Daerah", "Dalam Daerah" };

        int[] harga = { 10_000, 15_000, 20_000, 5_000, 23_000 };
        int[] hargaOngkir = { 20_000, 10_000 };
        int[][] pesananPelanggan = { { 10, 7, 0, 0, 17 }, { 0, 0, 0, 0, 0 }, { 0, 0, 15, 10, 25 }, { 0, 0, 0, 0, 0 },
                { 0, 8, 5, 2, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
        String[] debit = { "BCA", "BRI", "Mandiri" };
        int[] adminDebit = { 5_000, 3_000, 2_000 };
        int[] tagihanPelanggan = hitungTotal(pelanggan, pesananPelanggan, harga, hargaOngkir, jenisPengiriman);
        String[] onPoint;
        String target, pilihanMenu;
        boolean isrepeat = false, isCheck = false;
        int targetCheck, totalAll, numb;

        String ornamen = "█░█░█  █▀▀  █░░  █▀▀  █▀█  █▀▄▀█  █▀▀     ▀█▀  █▀█     █▀▀  ▄▀█  ▀█▀  █▀▀  █▀█  █  █▄░█  █▀▀     █▀  █▄█  █▀  ▀█▀  █▀▀  █▀▄▀█\n▀▄▀▄▀  ██▄  █▄▄  █▄▄  █▄█  █░▀░█  ██▄     ░█░  █▄█     █▄▄  █▀█  ░█░  ██▄  █▀▄  █  █░▀█  █▄█     ▄█  ░█░  ▄█  ░█░  ██▄  █░▀░█";

        do {
            pilihanMenu = getMenuProgram(menuProgram, ornamen).toLowerCase();
            switch (pilihanMenu) {
                case "1", "pemesanan":
                    target = getInfoCustomer(pelanggan);
                    if (target != "") {
                        printMenuCatering(menu, harga);
                        getMenuCatering(pesananPelanggan, pelanggan, target, tagihanPelanggan, text, harga, menu,
                                hargaOngkir, jenisPengiriman);
                        tagihanPelanggan = hitungTotal(pelanggan, pesananPelanggan, harga, hargaOngkir,
                                jenisPengiriman);
                    } else {
                        System.out.println("penyimpanan sudah penuh");
                    }
                    break;

                case "2", "tambah":
                    returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "kurang");
                    printPesananPelanggan(pesananPelanggan, harga, menu, pelanggan, tagihanPelanggan, text);
                    returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "tambah");
                    isCheck = false;
                    do {
                        System.out.print("Pelanggan Mana yang ingin ditambah [stop untuk berhenti]: ");
                        target = scan.nextLine();
                        targetCheck = findData(pelanggan, target);

                        if (target.charAt(0) == 's' || target.charAt(0) == 'S') {
                            break;
                        }
                        if (targetCheck != pelanggan.length) {
                            tambahPesanan(pesananPelanggan, menu, harga, targetCheck);
                            isCheck = true;
                        } else {
                            System.out.println("Maaf data tidak ditemukan");
                        }
                    } while (!isCheck);

                    break;
                case "3", "kurangi":
                    returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "kurang");
                    printPesananPelanggan(pesananPelanggan, harga, menu, pelanggan, tagihanPelanggan, text);
                    returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "tambah");
                    isCheck = false;
                    do {
                        System.out.print("Pelanggan Mana yang ingin dikurangi [stop untuk berhenti]: ");
                        target = scan.nextLine();
                        targetCheck = findData(pelanggan, target);

                        if (target.charAt(0) == 's' || target.charAt(0) == 'S') {
                            break;
                        }
                        if (targetCheck != pelanggan.length) {
                            kurangPesanan(pesananPelanggan, menu, harga, targetCheck);
                            isCheck = true;
                        } else {
                            System.out.println("Maaf data tidak ditemukan");
                        }
                    } while (!isCheck);
                    break;
                case "4", "transaksi":
                    isCheck = false;
                    do {
                        returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "kurang");
                        printPesananPelanggan(pesananPelanggan, harga, menu, pelanggan, tagihanPelanggan, text,
                                "belum lunas");

                        System.out.print("pilih pelanggan siapa [stop untuk berhenti]: ");
                        target = scan.nextLine();
                        targetCheck = findData(pelanggan, target);

                        if (target.length() > 1 && (target.charAt(0) == 's' || target.charAt(0) == 'S')
                                && (target.charAt(1) == 't' || target.charAt(1) == 'T')) {
                            returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "tambah");
                            break;
                        }
                        if (targetCheck != pelanggan.length) {
                            if (pelanggan[targetCheck][1].equalsIgnoreCase("belum lunas")) {
                                transaksi(harga, tagihanPelanggan, pesananPelanggan, pelanggan, targetCheck,
                                        hargaOngkir,
                                        menuPembayaran, debit, adminDebit, jenisPengiriman);
                                isCheck = true;
                            } else {
                                System.out.println("Maaf pelanggan sudah lunas");
                            }
                        } else {
                            returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "tambah");
                            System.out.println("Maaf data tidak ditemukan");
                        }
                        System.out.println("\n\n");
                    } while (!isCheck);
                    break;
                case "5", "pengecekan":

                    printDataPelanggan(pelanggan, text, tagihanPelanggan);
                    printPesananPelanggan(pesananPelanggan, harga, menu, pelanggan, tagihanPelanggan,
                            text);
                    break;

                case "6", "pencarian":
                    System.out.println("Search your data");
                    onPoint = search(pelanggan);

                    for (String each : menu) {
                        for (int i = 0; i < onPoint.length; i++) {
                            if (onPoint[i].equalsIgnoreCase(each)) {
                                printPesananPelanggan(pesananPelanggan, harga, menu, pelanggan, tagihanPelanggan,
                                        text,
                                        onPoint[i]);
                                break;
                            }

                        }
                    }
                    printDataPelanggan(pelanggan, text, tagihanPelanggan, onPoint);
                    break;
                case "7", "pelaporan":
                    printDataPelanggan(pelanggan, text, tagihanPelanggan);
                    System.out.println();
                    System.out.println();
                    returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "kurang");
                    printPesananPelanggan(pesananPelanggan, harga, menu, pelanggan, tagihanPelanggan,
                            text);
                    returnhitungAll(tagihanPelanggan, pelanggan, hargaOngkir, jenisPengiriman, "tambah");
                    totalAll = 0;
                    for (int nominal : tagihanPelanggan) {
                        totalAll += nominal;
                    }
                    System.out.println("Jadi total semua pendapatan : " + totalAll);
                    break;

                case "8", "hapus":
                    printDataPelanggan(pelanggan, text, tagihanPelanggan);
                    System.out.println("pilih metode penghapusan:");
                    System.out.println("1. satu per satu\n2. hapus semua data");
                    numb = scan.nextInt();
                    scan.nextLine();
                    hapusDataPelanggan(pelanggan, pesananPelanggan, tagihanPelanggan, text, numb);
                    break;
                case "9", "shutdown":
                    isrepeat = true;
                    break;

                default:
                    isrepeat = false;
                    break;
            }

        } while (!isrepeat);

    }
}

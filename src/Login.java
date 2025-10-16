import java.io.*;

class Login {
    void login() {
        try {
            String datas = "";
            FileReader fr = new FileReader("data/data.dat");
            BufferedReader b = new BufferedReader(fr);
            while (true) {

            }
        } catch(FileNotFoundException e) {
            System.out.println("📁 data 폴더에 파일 없음");
        }
    }
}
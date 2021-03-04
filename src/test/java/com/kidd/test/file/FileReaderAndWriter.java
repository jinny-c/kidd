package com.kidd.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileReaderAndWriter {

    public void fileReader() throws Exception{

        File file = new File("D:\\workspace\\github\\kidd\\src\\main\\webapp\\D3680.exe");

        FileInputStream fin = new FileInputStream(file);
        byte b[] = new byte[(int)file.length()];
        fin.read(b);

        File nf = new File("D:\\workspace\\github\\kidd\\src\\main\\webapp\\D3680.mp4");
        FileOutputStream fw = new FileOutputStream(nf);
        fw.write(b);
        fw.flush();
        fw.close();

    }

    public static void main(String[] args) {
        try {
            new FileReaderAndWriter().fileReader();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

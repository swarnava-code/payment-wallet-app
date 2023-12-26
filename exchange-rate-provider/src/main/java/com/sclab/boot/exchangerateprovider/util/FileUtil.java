package com.sclab.boot.exchangerateprovider.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {

    public static String readFile(String pathName){
        String data = null;
        StringBuilder stringBuilder = new StringBuilder();
        try(Scanner scanner = new Scanner(new File(pathName))) {
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                stringBuilder.append(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
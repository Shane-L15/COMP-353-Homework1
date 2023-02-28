package util;

import database.DatabaseSupport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ImportDBFromFile implements DatabaseSupport {
    public static void importDB(String fileName) throws FileNotFoundException {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            String tableName = "";
            String insertStatement = "";
            while ((line = bufferedReader.readLine()) != null) {
                if(line.isEmpty())
                    continue;

                if (line.startsWith("@")){
                    tableName = String.valueOf(parseTableName(line));
                    continue;
                }else {
                    insertStatement = line;
                }
                DatabaseSupport.execute("INSERT INTO " + tableName + " VALUES (" + insertStatement + ")");
                //System.out.println("INSERT INTO " + tableName + " VALUES (" + insertStatement+")");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static StringBuilder parseTableName(String line){
        StringBuilder tableName = new StringBuilder();
        int i = 1;
        while (line.charAt(i) != '(') {
            tableName.append(line.charAt(i));
            i++;
        }
        return tableName;
    }
}

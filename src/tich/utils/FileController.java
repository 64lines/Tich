package tich.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FileController {
	
	public String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
	
    public void saveFile(String text){
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException ex) {
        	String msg = "Damn! Cannot save file, something happen";
        	String ttl = "Error while saving file";
        	int err = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog(null, msg, ttl, err);
        }
    }

    public String loadFile(){
        String loadedFile = "";
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String strLine;

            while ((strLine = bufferedReader.readLine()) != null)   {
                loadedFile += strLine + "\n";
            }
            bufferedReader.close();
            System.out.println("Loaded");
        } catch (IOException ex) {
        	String msg = "Damn! Cannot load file, something happen";
        	String ttl = "Error while loading file";
        	int err = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog(null, msg, ttl, err);
        }

        return loadedFile;
    }
}

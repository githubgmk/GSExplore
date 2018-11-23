package com.gmk.geisa.helper;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class SimpanLog {
    String idmachine;
    String foldername="FMS";
    public SimpanLog(String idmachine) {
        if(idmachine==null)idmachine="NEW";
        this.idmachine = idmachine;
    }
    public void simpan(String log) {
        String path = Environment.getExternalStorageDirectory().toString()
                + "/"+ foldername;
        File folder = new File(path);
        folder.mkdir();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat smp1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat smp2 = new SimpleDateFormat("yyyy-MM-dd");
        String bulan = smp2.format(cal.getTime());
        String tanggal = smp1.format(cal.getTime());
        File logSimpan = new File(folder, "LOG-"+ idmachine+"-" + bulan + ".log");
        if (logSimpan.exists()) {
            try {
                FileInputStream fIn = new FileInputStream(logSimpan);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                String isiLog;
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer += aDataRow + "\n";
                }
                isiLog = aBuffer + " \n" + tanggal + " :\n" + log + "\n";
                myReader.close();
                FileOutputStream fOut = new FileOutputStream(logSimpan);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(isiLog);
                myOutWriter.close();
                fOut.close();
            } catch (Exception e) {

            }
        } else {
            try {
                logSimpan.createNewFile();
                FileOutputStream fOut = new FileOutputStream(logSimpan);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append("\n" + tanggal + " : \n" + log + "\n");
                myOutWriter.close();
                fOut.close();
            } catch (Exception e) {

            }
        }
    }

    public void simpanTransaksi(String log) {
        String path = Environment.getExternalStorageDirectory().toString()
                + "/"+foldername;
        File folder = new File(path);

        folder.mkdir();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat smp1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat smp2 = new SimpleDateFormat("yyyy-MM");
        String bulan = smp2.format(cal.getTime());
        String tanggal = smp1.format(cal.getTime());
        File logSimpan = new File(folder, "Transaksi-"+ idmachine+"-" + bulan + ".log");
        if (logSimpan.exists()) {
            try {
                FileInputStream fIn = new FileInputStream(logSimpan);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                String isiLog;
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer += aDataRow + "\n";
                }
                isiLog = aBuffer + " \n" + tanggal + " :\n" + log + "\n";
                myReader.close();
                FileOutputStream fOut = new FileOutputStream(logSimpan);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(isiLog);
                myOutWriter.close();
                fOut.close();
            } catch (Exception e) {

            }
        } else {
            try {
                logSimpan.createNewFile();
                FileOutputStream fOut = new FileOutputStream(logSimpan);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append("\n" + tanggal + " :\n" + log + "\n");
                myOutWriter.close();
                fOut.close();
            } catch (Exception e) {

            }
        }
    }
    
    public  void DeleteOldFile(int period){
        period *=-1;
        String path = Environment.getExternalStorageDirectory().toString()
                + "/"+foldername;
        File folder = new File(path);
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DAY_OF_YEAR,period);
        ArrayList<File> files= getAllFilesInDir(folder);
        if(files!=null){
            for (File file:files) {
                //I store the required attributes here and delete them
                Date lastModified = new Date(file.lastModified());
                if(lastModified.before(time.getTime()))
                {
                    //file is older than a week
                    simpan("Delete File Before :"+time.getTime()+"\nFile: "+file.toString());
                    file.delete();
                }

            }
        }

    }

    public ArrayList<String> GetNewFile(int period){
        ArrayList<String> filehasil =new ArrayList<>();
        period *=-1;
        String path = Environment.getExternalStorageDirectory().toString()
                + "/"+foldername;
        File folder = new File(path);
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DAY_OF_YEAR,period);
        ArrayList<File> files= getAllFilesInDir(folder);
        for (File file:files) {
            //I store the required attributes here and delete them
            Date lastModified = new Date(file.lastModified());
            if(lastModified.after(time.getTime()))
            {
                //file is older than a week

                filehasil.add(file.toString());

            }

        }
        return  filehasil;
    }
    public static ArrayList<File> getAllFilesInDir(File dir) {
        if (dir == null)
            return null;

        ArrayList<File> files = new ArrayList<File>();

        Stack<File> dirlist = new Stack<File>();
        dirlist.clear();
        dirlist.push(dir);

        while (!dirlist.isEmpty()) {
            File dirCurrent = dirlist.pop();

            File[] fileList = dirCurrent.listFiles();
            if(fileList!=null) {
                for (File aFileList : fileList) {
                    if (aFileList.isDirectory())
                        dirlist.push(aFileList);
                    else
                        files.add(aFileList);
                }
            }
        }

        return files;
    }

}

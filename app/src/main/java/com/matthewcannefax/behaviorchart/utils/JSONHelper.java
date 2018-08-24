package com.matthewcannefax.behaviorchart.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.matthewcannefax.behaviorchart.model.Kid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JSONHelper {

    final static String FILENAME = "KidsJSON";

    public static void exportKidsToJSON(Context context, List<Kid> kids){

        Kids kidData = new Kids();

        kidData.setKidList(kids);

        Gson gson = new Gson();

        String jsonString = gson.toJson(kidData);

        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static  List<Kid>  importKidsFromJSON(Context context){
        FileReader reader = null;

        try{
            File file = new File(context.getFilesDir(), FILENAME);
            reader = new FileReader(file);
            Gson gson = new Gson();
            Kids kidData = gson.fromJson(reader, Kids.class);
            return kidData.getKidList();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }


    static class Kids{
        List<Kid> kidList;

        private List<Kid> getKidList(){return kidList;}
        private void setKidList(List<Kid> kids){this.kidList = kids;}
    }

}

package cn.cienet.zhihudaily.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class FileUtils {

	private static final String TAG="FileUtils";

	/**
	 * writeString2LocalFile
	 * @param filePath
	 * @param fileName
	 * @param content
	 * @throws IOException
	 */
	public static void writeString2LocalFile(String filePath, String fileName, String content)throws IOException{
    	File file=new File(filePath, fileName);
    	
    	if(!file.exists()){
    		if(file.getParentFile().mkdirs()){
    			Log.i(TAG,"Create "+ fileName+ " successed!");
    		}else{
    			Log.i(TAG, "Create "+ fileName+ " failed!");
    		}
    	}
    	
    	FileWriter  fo=new FileWriter (file);
		fo.write(content);
		fo.flush();
		fo.close();
    }
    
	/**
	 * readStringFromLocalFile
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
    public static String readStringFromLocalFile(String filePath, String fileName) throws IOException{
    	
    	File file=new File(filePath, fileName);
    	
    	if(!file.exists()){
    		return null;
    	}
    	
    	BufferedReader in=new BufferedReader(new FileReader(file));
    	String str=in.readLine();
    	in.close();
    	
    	return str;
    }
    
    public static String readStringFromLocalFile2(String path, String name){
    	
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path, name);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file); 
                if (instream != null) 
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }                
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e) 
            {
                Log.d("TestFile", "The File doesn't not exist.");
            } 
            catch (IOException e) 
            {
                 Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }
    
    /**
     * deleteFile
     * @param filePath
     * @param fileName
     * @param onDelFileListener
     */
    public static void deleteFile(String filePath, String fileName){
    	File file=new File(filePath, fileName);
    	if (file.exists()) {
    		boolean result = file.delete();
    		String str;
    		if (result) {
    		    str="Del "+fileName+" success!";
				Log.i(TAG, str);
				
			}else {
				str="Del "+fileName+ " fail!";
				Log.i(TAG, str);
			}
		}
    }
}

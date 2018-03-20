package cn.cienet.zhihudaily.model;

import java.io.IOException;

import android.content.Context;
import cn.cienet.zhihudaily.utils.FileUtils;

public class DataSource {
	
	private String dataPath;

	public DataSource(Context context) {
		// TODO Auto-generated constructor stub
		dataPath=context.getExternalCacheDir().getAbsolutePath();
	}
	
	public void writeNews2File(String tit, String content) {
		writeSource2File("news_title", tit);
		writeSource2File("news_content", content);
	}
	
	public String  readNewsTitle() {
		return FileUtils.readStringFromLocalFile2(dataPath, "news_title");
	}
	
	public String readNewsContent(){
		return FileUtils.readStringFromLocalFile2(dataPath, "news_content");
	}
	
	public String readSourceFromFile(String fileName){
		try {
			String str=FileUtils.readStringFromLocalFile(dataPath, fileName);
			if (str!=null) {
				return str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void writeSource2File(String fileName, String content){
		try {
			FileUtils.writeString2LocalFile(dataPath, fileName, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void delSourceFormFile(String fileName){
		FileUtils.deleteFile(dataPath, fileName);
	}
	
	
}

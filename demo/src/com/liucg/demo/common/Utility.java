package com.liucg.demo.common;

import java.io.File;
import java.util.ArrayList;

import android.util.Log;

public class Utility {
	
	public  ArrayList<String> items = new ArrayList<String>();
	public  ArrayList<String> scanFiles(String filePath) {
//		Log.i("filePath", filePath);
		File file = new File(filePath);
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			if(files != null){
				for(int i = 0; i < files.length; i++)
	            {
					scanFiles(files[i].getAbsolutePath());
	            }	
			}
		} else{
			items.add(file.getAbsolutePath());
		}
		return items;
	}

}

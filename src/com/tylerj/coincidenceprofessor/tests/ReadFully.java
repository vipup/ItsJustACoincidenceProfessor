package com.tylerj.coincidenceprofessor.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
 
 
public class ReadFully {
	private String base;

	ReadFully(){
		this.base = "/";
	}	
	
	ReadFully(String base){
		this.base = base;
	}
	
	String readFully(String theNameOfResource){
		ByteArrayOutputStream os = new ByteArrayOutputStream();	

    	try (InputStream is = ReadFully.class.getResourceAsStream(base+theNameOfResource)) { 
 
                byte[] buffer = new byte[1024];
                int bytesRead; 
                while ((bytesRead = is.read(buffer)) != -1) { 
                	os.write(buffer, 0, bytesRead);
                }
 
            os.close();
    	} catch (IOException e) {
			 
			System.out.println(e);
		}
		return new String(os.toByteArray());
	}
}

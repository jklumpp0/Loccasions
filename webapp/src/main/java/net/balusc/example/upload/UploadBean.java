package net.balusc.example.upload;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Media;

@ManagedBean
@RequestScoped
public class UploadBean {
    private String title;
    private File file;
    private Media mMedia;
    
    @EJB
    private MediaRemote mDB;
    
    public void submit() {
    	// Persist the new media object
    	if (file != null && title != null && title.length() > 0) {
    		String desc = "A temporary placeholder for the description.";
    		String mime = new MimetypesFileTypeMap().getContentType(file);
    		mMedia = new Media(title, desc, mime, file.getAbsolutePath());
    		mDB.createMedia(mMedia);
    	}
    }

    public String getTitle() {
        return title;
    }

    public File getFile() {
        return file;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFile(File file) {
        this.file = file;
    }    
    
    public long getSize() {
    	if (mMedia != null) {
    		return mMedia.getSize();
    	}
    	
    	return -1;
    }
    
    public String getFilePath() {
    	if (mMedia != null) {
    		return mMedia.getPath();
    	}
    	
    	return "UNKNOWN PATH";
    }
    
    public Media getMedia() {
    	return mMedia;
    }
}
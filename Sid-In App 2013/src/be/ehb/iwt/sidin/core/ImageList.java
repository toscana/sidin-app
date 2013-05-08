package be.ehb.iwt.sidin.core;

import java.io.Serializable;
import java.util.List;

public class ImageList implements Serializable{
	
	private static final long serialVersionUID = -468736561748815676L;
	private List<Image> images;

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public ImageList(List<Image> images) {
		super();
		this.images = images;
	}

	public ImageList() {
		super();
	}
	
	

}

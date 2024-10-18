package com.scm.services.implementation;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.AppConstant;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	private Cloudinary cloudinary;
	// tarika without using the autowired  annotation we can user this for cloudinary  companies  mai aise hee use hota hai 
	public ImageServiceImpl(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}



	public String uploadImage(MultipartFile contactImage , String filename) {
		
		//  yha par code likhenge jo  image ko server par upload karega 
		
		try {
			byte[]  data= new byte[contactImage.getInputStream().available()];
			
			 contactImage.getInputStream().read(data);
	            cloudinary.uploader().upload(data, ObjectUtils.asMap(
	                    "public_id", filename));
			
	            return this.getUrlFromPublicId(filename);
	            
	            
		} catch (IOException e) {		
			e.printStackTrace();
			
			return null;
		}
		
		// return url karega 
		
		
	}

	@Override
	public String getUrlFromPublicId(String publicId) {
	
		
		
		return cloudinary
			  .url()
			  .transformation( new Transformation<Transformation>()
			  .width(AppConstant.CONTACT_IMAGE_WIDTH)
			  .height(AppConstant.CONTACT_IMAGE_HEIGHT).crop("fill"))
			  .generate(publicId);
	}
	
	

}

package com.example.onestep.config.cloudinary;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
@AllArgsConstructor
@Component
public class CloudinaryUtils {

    private Cloudinary cloudinary;

    @Async
    public String uploadImage(byte[] file, String id) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file,
                    ObjectUtils.asMap("public_id", id ));
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

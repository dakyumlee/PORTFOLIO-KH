package com.kh.portfolio.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map<String, Object> upload(MultipartFile file, String folder) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
            ObjectUtils.asMap(
                "folder", "kh-portfolio/" + folder,
                "resource_type", "auto"
            ));
    }

    public Map<String, Object> uploadWithThumbnail(MultipartFile file, String folder) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
            ObjectUtils.asMap(
                "folder", "kh-portfolio/" + folder,
                "resource_type", "image",
                "eager", "c_thumb,w_400,h_400,g_face"
            ));
    }

    public void delete(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    public String getThumbnailUrl(String url, int width, int height) {
        if (url == null || url.isEmpty()) return url;
        return url.replace("/upload/", "/upload/c_fill,w_" + width + ",h_" + height + "/");
    }
}

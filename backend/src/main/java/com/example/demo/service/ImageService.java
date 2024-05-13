package com.example.demo.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageService implements IStorageService{

    public final Path storageFolder = Paths.get("uploads");

    public ImageService(){
        try {
            Files.createDirectories(storageFolder);
        }catch (Exception e){
        }
    }


    private boolean isImageFIle(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(
                new String[] {"png","jpg","jpeg","bmp"}
        ).contains(fileExtension.trim().toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file) {
       try{
           if (file.isEmpty()){
               throw new RuntimeException("file đã tồn tại");
           }
       }catch (Exception e){
           throw new RuntimeException("không thành công ", e);
       }
       if (!isImageFIle(file)){
           throw new RuntimeException("Vui lòng chọn ảnh");
       }
       float fileSize = file.getSize() / 1_000_000.0f;
       if (fileSize > 5.0f){
           throw new RuntimeException("Vui lòng chọn file dưới 5mb");
       }
       String fileExtension  = FilenameUtils.getExtension(file.getOriginalFilename());
       String generated = UUID.randomUUID().toString().replace("-","");
       generated += "." + fileExtension;
       Path designationFilePath = this.storageFolder.resolve(
               Paths.get(generated).normalize().toAbsolutePath()
       );
        // Kiểm tra nếu tệp không nằm trong thư mục uploads, di chuyển nó vào
        if (!designationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
            designationFilePath = this.storageFolder.resolve(generated).toAbsolutePath();
        }
       try (InputStream inputStream = file.getInputStream()){
           Files.copy(inputStream,designationFilePath, StandardCopyOption.REPLACE_EXISTING);
       }catch (Exception e){
       }
       return generated;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageFolder,1).filter(
                    path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        }catch (Exception e){
            throw new RuntimeException("không load được ảnh ",e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
           Resource resource = new UrlResource(file.toUri());
           if (resource.exists() || resource.isReadable()){
               byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
               return bytes;
           }
           throw new RuntimeException("Không thể đọc file :" + fileName);
        }catch (Exception e){
            throw new RuntimeException("Không thể đọc file :" + fileName,e);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}

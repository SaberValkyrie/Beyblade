package com.example.demo.controller;

        import com.example.demo.support.Util;
        import com.example.demo.dto.ResponseOpject;
        import com.example.demo.service.IStorageService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;
        import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

        import java.util.Collections;
        import java.util.List;
        import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/files")
public class FileUploadController {
    @Autowired
    private IStorageService storageService;

    @PostMapping("upload")
    public ResponseEntity<ResponseOpject> uploadFile(@RequestParam("image")MultipartFile file){
        try {
            String generated = storageService.storeFile(file);

            if (file.getSize() > 1048576) { // 1 MB = 1048576 bytes
                return Util.checkStatus(HttpStatus.NOT_IMPLEMENTED,"Yêu cầu kích thước dưới 1MB", generated);
            }
            return Util.checkStatus(HttpStatus.OK,"upload file thành công", generated);
        }catch (Exception e){
            return Util.checkStatus(HttpStatus.NOT_IMPLEMENTED,e.getMessage(), "");
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readFile(@PathVariable String fileName){
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);

        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/files/")
    public ResponseEntity<List<ResponseOpject>> getAllFilesUpload(){
        try {

            List<String> urls = storageService.loadAll().map(
                    path -> {
                       String urlPart = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readFile",path.getFileName().toString()).build().toUri().toString();
                   return urlPart;
                    }).collect(Collectors.toList());

            return ResponseEntity.ok(Collections.singletonList(new ResponseOpject("ok", "succcess", urls)));

        }catch (Exception e){
            return ResponseEntity.ok(Collections.singletonList(new ResponseOpject("failed", "failed", new String[] {})));
        }
    }
}

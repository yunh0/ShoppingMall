package com.yunho.shopping.service;

import com.yunho.shopping.domain.Product;
import com.yunho.shopping.domain.ProductImg;
import com.yunho.shopping.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImgService {

    private final ProductImgRepository productImgRepository;

    public void uploadImg(Product product, List<MultipartFile> images){
        try{
            String uploadDir = "src/main/resources/static/uploads/";

            for(MultipartFile image : images){
                String dbFilePath = saveImage(image, uploadDir);

                ProductImg productImg = ProductImg.of(dbFilePath, product);
                productImgRepository.save(productImg);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String saveImage(MultipartFile image, String uploadDir) throws IOException {
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        String filePath = uploadDir + fileName;
        String dbFilePath = "/uploads/" + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return dbFilePath;
    }

    public List<ProductImg> getProductImages(Long productId){
        return productImgRepository.findByProduct_ProductId(productId);
    }
}

package com.yunho.shopping.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.yunho.shopping.domain.Product;
import com.yunho.shopping.domain.ProductImg;
import com.yunho.shopping.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImgService {

    private final ProductImgRepository productImgRepository;
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public void uploadImg(Product product, List<MultipartFile> images){
        try{
            for(MultipartFile image : images){
                String dbFilePath = saveImage(image);

                ProductImg productImg = ProductImg.of(dbFilePath, product);
                productImgRepository.save(productImg);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = image.getContentType();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(ext)
                .build();

        storage.create(blobInfo, image.getBytes());

        return uuid;
    }

    public void deleteImagesByProduct(Long productId) {
        List<ProductImg> images = productImgRepository.findByProduct_ProductId(productId);
        List<String> imagesName = images.stream()
                .map(ProductImg::getPath)
                .toList();

        deleteObject(imagesName);
        productImgRepository.deleteByProduct_ProductId(productId);
    }

    private void deleteObject(List<String> imagesName) {
        for(String name : imagesName){
            Blob blob = storage.get(bucketName, name);
            if (blob == null) {
                System.out.println("The object " + name + " wasn't found in " + bucketName);
                return;
            }

            Storage.BlobSourceOption precondition =
                    Storage.BlobSourceOption.generationMatch(blob.getGeneration());

            storage.delete(bucketName, name, precondition);
        }
    }

    public List<ProductImg> getProductImages(Long productId){
        return productImgRepository.findByProduct_ProductId(productId);
    }

    public List<String> getProductImagesPath(List<ProductImg> images){
        if(images == null || images.isEmpty()) {
            return List.of("https://storage.googleapis.com/" + bucketName + "/no-image.jpg");
        }
        return images.stream()
                .map(productImg -> "https://storage.googleapis.com/" +
                        bucketName + "/" +
                        productImg.getPath()
                )
                .collect(Collectors.toList());
    }
}

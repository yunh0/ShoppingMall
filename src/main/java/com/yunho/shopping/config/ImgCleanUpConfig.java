package com.yunho.shopping.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class ImgCleanUpConfig {

    private static final String IMAGE_DIRECTORY = "src/main/resources/static/uploads/image/";

    @PostConstruct
    public void cleanUpImages() {
        try {
            Path directoryPath = Paths.get(IMAGE_DIRECTORY);
            if (Files.exists(directoryPath)) {
                File directory = directoryPath.toFile();
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            file.delete();
                        }
                    }
                }
                log.info("이미지 폴더 초기화 완료: {}", IMAGE_DIRECTORY);
            } else {
                log.warn("경로를 찾을 수 없습니다: {}", IMAGE_DIRECTORY);
            }
        } catch (Exception e) {
            log.error("🚨 이미지 폴더 초기화 중 오류 발생", e);
        }
    }
}

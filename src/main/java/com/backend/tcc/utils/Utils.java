package com.backend.tcc.utils;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

public class Utils {

    @Named("b2b64")
    public static String bytesToBase64(byte[] image) {
        return image != null ? "data:image/png;base64," + Base64.getEncoder().encodeToString(image) : null;
    }

    @Named("bList2b64List")
    public static List<String> bytesListToBase64List(List<byte[]> images) {
        if(images == null) return null;

        return images.stream().map(Utils::bytesToBase64).toList();
    }

    @Named("m2b")
    public static byte[] multipartToBytes(MultipartFile file) {
        try {
            return file != null ? file.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter MultipartFile para byte[]", e);
        }
    }

    @Named("mList2bList")
    public static List<byte[]> multipartListToBytesList(List<MultipartFile> files) {
        try {
            if (files == null) return null;
            return files.stream().map(Utils::multipartToBytes).toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter lista de MultipartFile para lista de byte[]", e);
        }
    }   

    @Named("b642b")
    public static byte[] base64ToBytes(String base64) {
        if (base64 == null || base64.isEmpty()) {
            return null;
        }
        String pureBase64 = base64.contains(",") ? base64.split(",")[1] : base64; // Remove prefixo "data:image/png;base64,"
        return Base64.getDecoder().decode(pureBase64);
    }

}

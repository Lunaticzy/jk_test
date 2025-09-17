package org.example.jk_test1.service.impl;

import jakarta.annotation.Resource;
import org.example.jk_test1.entity.Document;
import org.example.jk_test1.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocumentServiceImplTest {

    @Resource
    DocumentService documentService;


    @Test
    void search() {
        List<Document> res = documentService.search("手机");
        System.out.println(res);
    }
}
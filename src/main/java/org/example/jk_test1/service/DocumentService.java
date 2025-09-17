package org.example.jk_test1.service;

import org.example.jk_test1.entity.Document;

import java.util.List;

public interface DocumentService {
    List<Document> search(String keyword);
    List<Document> search(String keyword, int offset, int limit); // 添加分页搜索方法

    Document getById(Integer id);

    Integer getTotalCount();  // 获取总条数
    Integer getSearchCount(String keyword);  // 获取搜索结果条数
}

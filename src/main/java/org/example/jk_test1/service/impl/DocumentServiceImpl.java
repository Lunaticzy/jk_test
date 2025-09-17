package org.example.jk_test1.service.impl;

import org.example.jk_test1.entity.Document;
import org.example.jk_test1.service.DocumentService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final JdbcTemplate jdbcTemplate;

    public DocumentServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Document> search(String keyword) {

        // 使用tsvector进行全文搜索
        String sql = """
                select id, title, content
                from tsvector_documents
                where title_tsv @@ to_tsquery('jiebacfg', ?)
                or content_tsv @@ to_tsquery('jiebacfg', ?)
                limit 1000
                """;
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Document.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .build(),
                keyword, keyword
        );

    }

    // 在 DocumentServiceImpl.java 中添加分页搜索方法
    @Override
    public List<Document> search(String keyword, int offset, int limit) {
        // 使用tsvector进行全文搜索，添加分页支持
        String sql = """
            select id, title, content
            from tsvector_documents
            where title_tsv @@ to_tsquery('jiebacfg', ?)
            or content_tsv @@ to_tsquery('jiebacfg', ?)
            limit ? offset ?
            """;
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Document.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .build(),
                keyword, keyword, limit, offset
        );
    }

    @Override
    public Document getById(Integer id) {
        String sql = """
                select id, title, content
                from tsvector_documents
                where id = ?
                """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Document(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content")
                ), id);
    }

    // 在 DocumentServiceImpl.java 中添加方法实现
    @Override
    public Integer getTotalCount() {
        String sql = "SELECT COUNT(*) FROM tsvector_documents";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Integer getSearchCount(String keyword) {
        String sql = """
        SELECT COUNT(1)
        FROM tsvector_documents
        WHERE title_tsv @@ to_tsquery('jiebacfg', ?)
        OR content_tsv @@ to_tsquery('jiebacfg', ?)
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, keyword, keyword);
    }
}

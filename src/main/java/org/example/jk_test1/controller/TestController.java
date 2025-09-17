package org.example.jk_test1.controller;

import org.apache.commons.lang3.StringUtils;
import org.example.jk_test1.entity.Document;
import org.example.jk_test1.entity.User;
import org.example.jk_test1.service.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestController {

    private final DocumentService documentService;

    public TestController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        String returnMessage = "Hello World!";
        returnMessage += "1";
        returnMessage += "2";
        returnMessage += "3";
        returnMessage += "4";
        returnMessage += "5";
        returnMessage += "6";
        returnMessage += "7";
        returnMessage += "8";

        model.addAttribute("message", returnMessage);


        User test = User.builder().id(1).name("test").build();
        model.addAttribute("user", test);

        return "index";
    }

    @ResponseBody
    @GetMapping("/test")
    public User test() {
        return User.builder().id(1).name("test").build();
    }

    @GetMapping("/search")
    public String searchDocument() {
        return "search";
    }

    @PostMapping("/search")
    public String searchDocument(@RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        int pageSize = 50; // 每页显示10条记录
        long startTime = System.currentTimeMillis(); // 记录开始时间

        // 获取总条数并添加到模型
        int totalCount = documentService.getTotalCount();
        model.addAttribute("totalCount", totalCount);

        if (StringUtils.isBlank(keyword)) {
            model.addAttribute("message", "请输入关键字");
            return "search";
        }

        // 计算偏移量
        int offset = page * pageSize;

        List<Document> result = documentService.search(keyword, offset, pageSize); // 使用分页搜索
        int searchCount = documentService.getSearchCount(keyword);

        long endTime = System.currentTimeMillis(); // 记录结束时间
        long duration = endTime - startTime; // 计算耗时

        // 计算总页数
        int totalPages = (int) Math.ceil((double) searchCount / pageSize);

        model.addAttribute("searchCount", searchCount);
        model.addAttribute("result", result);
        model.addAttribute("keyword", keyword);
        model.addAttribute("duration", duration);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        if (result.isEmpty()) {
            model.addAttribute("message", "没有找到结果");
        }

        return "search";
    }
    @GetMapping("/document/{id}")
    public String document(@PathVariable Integer id, Model model) {
        Document document = documentService.getById(id);
        model.addAttribute("document", document);
        return "document";
    }


}

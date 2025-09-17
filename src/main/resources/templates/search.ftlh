<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search</title>
    <style>
        .pagination {
            margin: 20px 0;
        }
        .pagination a, .pagination span {
            padding: 8px 16px;
            margin: 0 4px;
            text-decoration: none;
            border: 1px solid #ddd;
            color: #007bff;
        }
        .pagination a:hover {
            background-color: #e9ecef;
        }
        .pagination .current {
            background-color: #007bff;
            color: white;
        }
        .pagination .disabled {
            color: #6c757d;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
<h1>Search</h1>

<!-- 显示总条数 -->
<div>
    总文档数: ${totalCount!0}
</div>

<#--message-->
<#if message??>
    <div>
        ${message}
    </div>
</#if>

<form action="/search" method="post">
    <input type="hidden" name="page" value="0">
    <label>
        <input type="text" name="keyword" value="${keyword!}">
    </label>
    <input type="submit" value="Search">
</form>

<#-- result -->
<#if result??>
    <div>
        <!-- 显示搜索结果条数和耗时 -->
        <#if keyword??>
            搜索 "${keyword}" 找到 ${searchCount!0} 条结果，耗时: ${duration!0} 毫秒:
        </#if>

        <#list result as item>
            <div>
                <ul>
                    <li><a href="/document/${item.id?c}">${item.title}</a></li>
                </ul>
            </div>
        </#list>

        <!-- 分页导航 -->
        <#if (totalPages > 1)>
            <div class="pagination">
                <#-- 上一页 -->
                <#if (currentPage > 0)>
                    <a href="javascript:void(0)" onclick="submitForm(${currentPage - 1})">&laquo; 上一页</a>
                <#else>
                    <span class="disabled">&laquo; 上一页</span>
                </#if>

                <#-- 页码显示逻辑 -->
                <#assign showPages = 5> <!-- 显示的页码数量 -->
                <#assign halfPages = showPages / 2?int>
                <#assign startPage = currentPage - halfPages>
                <#assign endPage = currentPage + halfPages>

                <#-- 调整起始和结束页码 -->
                <#if (startPage < 0)>
                    <#assign endPage = endPage - startPage>
                    <#assign startPage = 0>
                </#if>
                <#if (endPage >= totalPages)>
                    <#assign startPage = startPage - (endPage - totalPages + 1)>
                    <#if (startPage < 0)>
                        <#assign startPage = 0>
                    </#if>
                    <#assign endPage = totalPages - 1>
                </#if>

                <#-- 确保显示的页码数量正确 -->
                <#if ((endPage - startPage + 1) > showPages)>
                    <#if (currentPage - startPage > endPage - currentPage)>
                        <#assign startPage = startPage + 1>
                    <#else>
                        <#assign endPage = endPage - 1>
                    </#if>
                </#if>

                <#-- 显示第一页和省略号 -->
                <#if (startPage > 0)>
                    <a href="javascript:void(0)" onclick="submitForm(0)">1</a>
                    <#if (startPage > 1)>
                        <span>...</span>
                    </#if>
                </#if>

                <#-- 显示中间页码 -->
                <#list startPage..endPage as i>
                    <#if (i == currentPage)>
                        <span class="current">${i + 1}</span>
                    <#else>
                        <a href="javascript:void(0)" onclick="submitForm(${i})">${i + 1}</a>
                    </#if>
                </#list>

                <#-- 显示最后一页和省略号 -->
                <#if (endPage < totalPages - 1)>
                    <#if (endPage < totalPages - 2)>
                        <span>...</span>
                    </#if>
                    <a href="javascript:void(0)" onclick="submitForm(${totalPages - 1})">${totalPages}</a>
                </#if>

                <#-- 下一页 -->
                <#if (currentPage < totalPages - 1)>
                    <a href="javascript:void(0)" onclick="submitForm(${currentPage + 1})">下一页 &raquo;</a>
                <#else>
                    <span class="disabled">下一页 &raquo;</span>
                </#if>
            </div>
        </#if>
    </div>
</#if>

<script>
    function submitForm(page) {
        var form = document.querySelector('form');
        var pageInput = form.querySelector('input[name="page"]');
        if (!pageInput) {
            pageInput = document.createElement('input');
            pageInput.type = 'hidden';
            pageInput.name = 'page';
            form.appendChild(pageInput);
        }
        pageInput.value = page;
        form.submit();
    }
</script>

</body>
</html>

package com.ocp.server.search.controller;

import com.ocp.common.bean.PageInfo;
import com.ocp.common.bean.Result;
import com.ocp.server.search.entity.IndexDto;
import com.ocp.server.search.properties.IndexProperties;
import com.ocp.server.search.service.IIndexService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * 索引管理
 * @author kong
 * @date 2021/08/29 11:42
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@RestController
@Api(tags = "索引管理api")
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private IIndexService indexService;

    @Autowired
    private IndexProperties indexProperties;

    @PostMapping("/index")
    public Result createIndex(@RequestBody IndexDto indexDto) throws IOException {
        if (indexDto.getNumberOfShards() == null) {
            indexDto.setNumberOfShards(1);
        }
        if (indexDto.getNumberOfReplicas() == null) {
            indexDto.setNumberOfReplicas(0);
        }
        indexService.create(indexDto);
        return Result.success("操作成功");
    }

    /**
     * 索引列表
     */
    @GetMapping("/indices")
    public PageInfo<Map<String, String>> list(@RequestParam(required = false) String queryStr) throws IOException {
        return indexService.list(queryStr, indexProperties.getShow());
    }

    /**
     * 索引明细
     */
    @GetMapping("/index")
    public Result<Map<String, Object>> showIndex(String indexName) throws IOException {
        Map<String, Object> result = indexService.show(indexName);
        return Result.success("",result);
    }

    /**
     * 删除索引
     */
    @DeleteMapping("/index")
    public Result deleteIndex(String indexName) throws IOException {
        indexService.delete(indexName);
        return Result.success("操作成功");
    }
}

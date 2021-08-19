package com.ocp.auth.controller;

import com.ocp.auth.entity.Client;
import com.ocp.auth.entity.dto.ClientDto;
import com.ocp.auth.entity.dto.ClientQueryPageDto;
import com.ocp.auth.service.IClientService;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用相关接口
 * @author kong
 * @date 2021/08/12 21:45
 * blog: http://blog.kongyin.ltd
 */
@Api(tags = "应用")
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/list")
    @ApiOperation(value = "应用列表")
    public ResponseEntity<PageInfo<Client>> list(@RequestParam ClientQueryPageDto pageDto){
        return ResponseEntity.ok(clientService.listClient(pageDto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取应用")
    public ResponseEntity<Client> get(@PathVariable String id){
        return ResponseEntity.ok(clientService.getById(id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有应用")
    public ResponseEntity<List<Client>> allClient(){
        List<Client> list = clientService.list();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除应用")
    public ResponseEntity<Result<Object>> delete(@PathVariable Long id){
        clientService.delClient(id);
        return Result.success().response();
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存或者修改应用")
    public ResponseEntity<Result<Object>> saveOrUpdate(@RequestBody ClientDto clientDto) throws Exception {
        clientService.saveClient(clientDto);
        return Result.success().response();
    }
}

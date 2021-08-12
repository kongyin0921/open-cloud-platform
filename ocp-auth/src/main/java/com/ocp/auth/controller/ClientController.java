package com.ocp.auth.controller;

import com.ocp.auth.entity.Client;
import com.ocp.auth.entity.dto.ClientDto;
import com.ocp.auth.service.IClientService;
import com.ocp.common.bean.PageResult;
import com.ocp.common.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public PageResult<Client> list(@RequestParam Map<String,Object> params){
        return clientService.listClient(params,true);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取应用")
    public Client get(@PathVariable String id){
        return clientService.getById(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有应用")
    public Result<List<Client>> allClient(){
        List<Client> list = clientService.list();
        return Result.succeed(list);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除应用")
    public Result delete(@PathVariable Long id){
        clientService.removeById(id);
        return Result.succeed("删除成功");
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存或者修改应用")
    public Result saveOrUpdate(@RequestBody ClientDto clientDto) throws Exception {
        clientService.saveClient(clientDto);
        return Result.succeed("操作成功");
    }
}

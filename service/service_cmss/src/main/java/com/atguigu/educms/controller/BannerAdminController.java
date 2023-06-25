package com.atguigu.educms.controller;


import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author testjava
 * @since 2023-06-01
 */
@RestController
@CrossOrigin
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pagebanner = new Page<>(page, limit);
        crmBannerService.page(pagebanner,null);
        return R.ok().data("items",pagebanner.getRecords()).data("total",pagebanner.getTotal());
    }
    @ApiOperation(value = "新增Banner")
    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }
    //删除banner
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }

    //修改banner
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }
}


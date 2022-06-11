package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 后端控制器                 后端接口实现了 但是前端的管理员页面还没有整合 需要自己来一步步整合
 * </p>
 *
 * @author zzyt
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/educms/banner")
//@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    //进行分页的方法
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") long page, @PathVariable("limit") long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        bannerService.page(bannerPage, null);
        return R.ok().data("records", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    //进行添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    //进行查找banner
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable("id") String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    //进行对banner修改
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner) {
        bannerService.update(banner, null);
        return R.ok();
    }

    //对banner进行删除
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable("id") String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}


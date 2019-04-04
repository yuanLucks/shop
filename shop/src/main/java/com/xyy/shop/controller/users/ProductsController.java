package com.xyy.shop.controller.users;

import com.xyy.shop.annotates.UserLoginToken;
import com.xyy.shop.pojo.products.ProAssess;
import com.xyy.shop.pojo.products.Products;
import com.xyy.shop.pojo.products.Prolun;
import com.xyy.shop.pojo.products.Protj;
import com.xyy.shop.pojo.user.ResponseData;
import com.xyy.shop.pojo.user.ReturnCode;
import com.xyy.shop.service.users.IProductsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api("商品操作")
@RestController
public class ProductsController {

    @Autowired
    private IProductsService iProductsService;


    @GetMapping("product/query/tj")
    @ApiOperation(value = "显示推荐商品")
    public ResponseData showtj(){
        Map<String,Object> mapProducts = iProductsService.queryProtj();
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询商品成功！",mapProducts);
        return responseData;
    }


    @GetMapping("product/query/lun")
    @ApiOperation(value = "显示轮播图商品")
    public ResponseData showlun(){
        Map<String,Object> list = iProductsService.queryProlun();
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询商品成功！",list);
        return responseData;
    }

    @GetMapping("product/query")
    @ApiOperation(value = "根据关键字模糊查询商品")
    public ResponseData queryProductByProName(@ApiParam(name = "proname",value = "商品名称") @RequestParam String proname){
        System.out.println(proname+"阿萨德撒旦");
        Map<String,Object> mapProduct = iProductsService.queryProductsByProName(proname);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询商品成功！",mapProduct);
        return responseData;
    }

    @GetMapping("product/query/{proid}")
    @ApiOperation("根据Id查询商品详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "proid",value = "商品编号",dataType = "int",paramType = "path")})
    public ResponseData queryProductById(@PathVariable int proid){
        System.out.println(proid);
        Map<String , Object> mapProduct = iProductsService.queryProductsByProid(proid);

        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),mapProduct);
        return responseData;
    }

    @UserLoginToken
    @PostMapping("product")
    @ApiOperation("添加商品值收藏列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "proid",value = "商品编号",dataType = "int",paramType = "path")})
    public ResponseData addProduct(@RequestParam Integer proid, HttpServletRequest request){

        int uid =(int) request.getSession().getAttribute("uid");
        iProductsService.insertCollectByProid(proid,uid);

        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"添加收藏成功");
        return responseData;
    }

    @GetMapping("product/query/assess/{proid}")
    @ApiOperation("显示该商品最近两条评语")
    @ApiImplicitParams({@ApiImplicitParam(name = "proid",value = "商品编号",dataType = "int",paramType = "path")})
    public ResponseData showTwoAssess(@PathVariable int proid){
        List<ProAssess> listAssess = iProductsService.queryProAssessByProidTopTwo(proid);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询成功！",listAssess);
        return responseData;
    }

    @GetMapping("product/query/assess/all/{proid}")
    @ApiOperation(value = "显示该商品全部评语")
    @ApiImplicitParams({@ApiImplicitParam(name = "proid",value = "商品编号",dataType = "int",paramType = "path")})
    public ResponseData showAllAssess(@PathVariable int proid){
        List<ProAssess> listAssess = iProductsService.queryAllProAssessByproid(proid);
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询成功！",listAssess);
        System.out.println(ReturnCode.SUCCESS.getCode());
        System.out.println(responseData.toString());
        return responseData;
    }

    @GetMapping("product/query/collect")
    @UserLoginToken
    @ApiOperation(value = "显示全部收藏商品")
    public ResponseData showAllCollect(){
        Map<String,Object> mapCollect = iProductsService.queryAllCollect();
        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),mapCollect);
        return responseData;
    }
}

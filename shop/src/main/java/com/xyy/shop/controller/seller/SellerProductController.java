package com.xyy.shop.controller.seller;

import com.xyy.shop.pojo.products.Coupon;
import com.xyy.shop.pojo.products.ProType;
import com.xyy.shop.pojo.products.Products;
import com.xyy.shop.pojo.products.ProductsGroup;
import com.xyy.shop.pojo.user.ResponseData;
import com.xyy.shop.pojo.user.ReturnCode;
import com.xyy.shop.service.users.IOrdersService;
import com.xyy.shop.service.seller.ISellerOrderService;
import com.xyy.shop.service.seller.ISellerProductService;
import com.xyy.shop.utils.PictureJudgeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api("商家商品操作")
@RestController
public class SellerProductController {

    @Autowired
    private ISellerProductService iSellerProductService;

    @Autowired
    private ISellerOrderService iSellerOrderService;

    @Autowired
    private IOrdersService iOrdersService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/seller/summarization")
    @ApiOperation(value = "显示经营概括",notes = "amount是支付金额，sun是订单数量,dfhStatus待发货订单,thStatus退货订单")
    public ResponseData queryTodayOrderAmountAndCount(){
        //查询支付金额 和 订单数量
        Map<String,Object> map = iSellerProductService.queryTodayOrderAmountAndCount();

        //查询待发货订单
        Map<String,Object> mapStatus = iOrdersService.queryOrderByStatus("待发货");

        //查询退货订单
        Map<String,Object> mapStatus1 = iOrdersService.queryOrderByStatus("退货");

        map.put("dfhStatus",mapStatus);
        map.put("thStatus",mapStatus1);


        ResponseData responseData = new ResponseData(true, ReturnCode.SUCCESS.getCode(),"查询成功！",map);
        return responseData;
    }

    @GetMapping("/seller/product")
    @ApiOperation(value = "条件查询商品",notes = "proname是商品名称，groups是商品分组，typeid是商品类别，startOne是价格最小值，startTwo价格最大值")
    public ResponseData  queryProductByLike(@RequestParam String proname,@RequestParam int groups,@RequestParam String typeid,@RequestParam int startOne,@RequestParam int startTwo){
        System.out.println(proname+groups);
        Map<String,Object> map = iSellerProductService.queryProductByLike(proname,groups,typeid,startOne,startTwo);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"查询成功！",map);
        return responseData;
    }


    @DeleteMapping("product/{proid}")
    @ApiOperation(value = "根据商品编号删除 商品", notes = "删除商品 根据商品编号")
    public ResponseData deleteProductByProid(@PathVariable int proid){
        iSellerProductService.deleteProductByProid(proid);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"删除成功！");
        return responseData;
    }

    @PostMapping("protype")
    @ApiOperation(value = "添加商品类别",notes = "传一个类别名称就行")
    public ResponseData insertProtype(@RequestBody @ModelAttribute ProType proType){
        iSellerProductService.insertProtype(proType);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"添加成功！");
        return responseData;
    }

    @DeleteMapping("protype/{typeid}")
    @ApiOperation("根据商品编号  删除商品类别")
    public ResponseData deleteProtype(@PathVariable int typeid){
        iSellerProductService.deleteProtype(typeid);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"删除成功！");
        return responseData;
    }

    @PutMapping("protype")
    @ApiOperation("修改商品类别")
    public ResponseData updateProtype(@RequestBody @ModelAttribute ProType proType){
        iSellerProductService.updateProtype(proType);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"修改成功！");
        return responseData;
    }

    @PostMapping("group")
    @ApiOperation("添加商品分组")
    public ResponseData insertProductGroups(@RequestBody @ModelAttribute ProductsGroup productsGroup){
        iSellerProductService.insertProductGroups(productsGroup);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"添加成功！");
        return responseData;
    }

    @DeleteMapping("group/{groupid}")
    @ApiOperation("删除商品分组")
    public ResponseData deleteProductGroupsByGroupId(@PathVariable int groupid){
        System.out.println(groupid);
        iSellerProductService.deleteProductGroupsByGroupId(groupid);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"删除成功！");
        return responseData;
    }

    @PutMapping("group")
    @ApiOperation("修改商品分组")
    public ResponseData updateProductGroups(@RequestBody @ModelAttribute ProductsGroup productsGroup){
        System.out.println(productsGroup.toString());
        iSellerProductService.updateProductGroups(productsGroup);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"修改成功！");
        return responseData;
    }

    @PutMapping("product")
    @ApiOperation("修改商品信息")
    public ResponseData updateProducts(@RequestBody @ModelAttribute Products products){
        iSellerProductService.updateProducts(products);
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"修改成功！");
        return responseData;
    }

    @PostMapping("seller/product")
    @ApiOperation("发布商品")
    public ResponseData insertProduct(Products products, @RequestParam("files") MultipartFile files[],Coupon coupon){
        if (coupon != null){
            //添加优惠卷
            iSellerOrderService.insertCoupon(coupon);
            String id = String.valueOf(coupon.getId());
            //设置优惠卷失效时间
            redisTemplate.opsForValue().set("coupon" + coupon.getId() ,id ,coupon.getExpiretime().getTime() - coupon.getCreartetime().getTime(), TimeUnit.MILLISECONDS);

        }

        //设置优惠卷外键
        products.setCoupon(coupon.getId());

        iSellerProductService.insertProduct(products);


        //上传图片路径
        String uploadPath = "D:\\tupian";
        File filePath = new File(uploadPath);

        //如果路径不存在则创建路径
        if (!filePath.exists()){
            filePath.mkdir();
        }

        //判断files是否为空  且 长度大于 1
        if (files != null && files.length >= 1){
            BufferedOutputStream bw = null;
            try {
                for (MultipartFile file : files) {
                    //获取文件全名称
                    String fileName = file.getOriginalFilename();

                    //判断是否有文件  且 是图片
                    //equalsIgnoreCase: 将此 String 与另一个 String 比较，不考虑大小写。
                    if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && PictureJudgeUtil.isImageFile(fileName)) {
                        //创建输出文件对象
                        File outFile = new File(filePath + "/" + UUID.randomUUID().toString() + PictureJudgeUtil.getFileType(fileName));
                        //拷贝文件输出文件对象
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ResponseData responseData = new ResponseData(true,ReturnCode.SUCCESS.getCode(),"发布成功！");
        return responseData;
    }
}

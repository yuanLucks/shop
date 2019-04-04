package com.xyy.shop.controller;

import com.xyy.shop.mapper.OrdersMapper;
import com.xyy.shop.pojo.products.ProType;
import com.xyy.shop.pojo.user.Audience;
import com.xyy.shop.pojo.order.Orders;
import com.xyy.shop.pojo.user.User;
import com.xyy.shop.utils.JwtHelperUtil;
import com.xyy.shop.utils.PictureJudgeUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户测试Controller
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private Audience audience;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping(value = "/te", method = RequestMethod.POST)
    @ApiOperation("用户测试")
    public String test2() {
        BigDecimal discount1 = new BigDecimal(12.00);
        BigDecimal discount2 = new BigDecimal(9.00);
        BigDecimal divide = discount1.divide(discount2, 2, RoundingMode.HALF_UP);
        System.out.println(divide);
        return "success";
    }

    /**
     * 图片上传测试
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(@RequestParam("file") MultipartFile file) throws IOException {

        //允许文件的类型
        String fileType = "pdf,png,jpg";

        //获取文件名
        String org = file.getOriginalFilename();
        //获取文件后缀
        String extName = org.substring(org.indexOf(".") + 1).toLowerCase().trim();
        System.out.println("extName" + extName);

        String[] str = fileType.split(",");
        for (String s : str) {
            System.out.println(s);
            if (s.equals(extName)) {
                System.out.println("success!!!!!!");
            } else {

            }
        }


        //创建新的文件名，即使第二次上传的图片一样
        String filename = System.currentTimeMillis() + file.getOriginalFilename();

        File file1 = new File("D:\\tupian\\" + filename);

        file.transferTo(file1);
        return "ok";
    }

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public String a() {
        ProType proType = new ProType();
        proType.setTypename("asdasdasdasdasdas");
        //int i = ordersMapper.aaaa(proType);
        System.out.println(proType.toString());
        //System.out.println(i + "------------");
        return "index";
    }

    /**
     * jwt 测试
     *
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/logins", method = RequestMethod.POST)
    @ApiOperation(value = "jwt测试")
    public String logins(@RequestParam String name, @RequestParam String password) {
        System.out.println(name + password);
        String jwtToken;
        if (name.equals("luck") && password.equals("123")) {
            jwtToken = JwtHelperUtil.createJWT(name, "asddf");
            System.out.println(jwtToken);

            Claims claims = JwtHelperUtil.parseJWT(jwtToken, "asddf");
            System.out.println(claims.getAudience() + claims.getExpiration() + claims.getIssuer() + claims.getSubject() + claims.get("unique_name"));
            System.out.println(claims.getId());
            return jwtToken;
        }
        return "error";
    }

    @RequestMapping(value = "/ll", method = RequestMethod.POST)
    @ApiOperation(value = "aaaa")
    public String ll(@RequestParam String token) {
        Claims claims = JwtHelperUtil.parseJWT(token, "asddf");
        System.out.println(claims.toString());
        System.out.println(claims.getExpiration() + claims.getIssuer());
        return "secess";
    }

    @PostMapping("/aa")
    @ApiOperation(value = "测试")
    public String aa(@RequestBody String s, @RequestParam int[] a) {
        System.out.println(s + a.toString());
        Orders orders = new Orders();
        orders.setSTATUS(s);
        //ordersMapper.aaa(orders,a);
        return "success";
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadAllClassmate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查询出的数据
        //List<ProType> proTypes = ordersMapper.qqqq();

        // 获取服务路径
        String path = request.getServletContext().getRealPath("down");
        String filename = "demo.xlsx";

        // 存储File
        File tfile = new File(path + "\\" + filename);
        // 目录
        File mfile = new File(path);

        if (!tfile.exists()) {
            mfile.mkdir();
        }

        // 生成excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("学员信息表");
        int rownum = 0;

        /*for (ProType stu : proTypes) {
            XSSFRow row = sheet.createRow(rownum);
            row.createCell(0).setCellValue(stu.getTypeid());
            row.createCell(1).setCellValue(stu.getTypename());
            rownum++;
        }*/

        workbook.write(new FileOutputStream(tfile));

        // 创建请求头对象
        HttpHeaders headers = new HttpHeaders();
        // 下载显示的文件名，解决中文名称乱码问题
        String downloadFileName = new String(filename.getBytes("iso-8859-1"), "UTF-8");
        // 通知浏览器以attachment(下载方式)打开
        headers.setContentDispositionFormData("attachment", downloadFileName);
        // application/octet-stream:二进制流数据（最常见的文件下载）
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tfile),
                headers, HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(value = "exports", method = RequestMethod.GET)
    public void Export(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
       // List<ProType> proTypes = ordersMapper.qqqq();
        //System.out.println(proTypes.toString());
        // 设置要导出的文件的名字
        String fileName = "users" + new Date() + ".xls";

        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        // headers表示excel表中第一行的表头 在excel表中添加表头
        /*String[] headers = { "id", "uid"};
        HSSFRow row = sheet.createRow(0);

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }*/

        //在表中存放查询到的数据放入对应的列


        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @RequestMapping(value = "lalasss", method = RequestMethod.GET)
    @ApiOperation("图片上传测试")
    public String apap(@RequestBody @ModelAttribute User user, MultipartFile file) throws IOException {
        System.out.println("...." + file);
        //上传图片路径
        String uploadPath = "D:\\tupian";
        File filePath = new File(uploadPath);

        //如果路径不存在则创建路径
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        //判断files是否为空  且 长度大于 1
        if (file != null) {
            System.out.println(".........");
            BufferedOutputStream bw = null;
            //获取文件全名称
            String fileName = file.getOriginalFilename();

            //创建输出文件对象
            File outFile = new File(filePath + "/" + UUID.randomUUID().toString() + PictureJudgeUtil.getFileType(fileName));
            System.out.println(outFile.getName());
            //拷贝文件输出文件对象
            FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
            bw.close();
        }
        return "success";
    }

    @GetMapping("/authorize")
    @ApiOperation("authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        String url = "http://c36f363c911e5498.natapp.cc/wechat/userInfo";
        String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code,redirectURL={}", redirectURL);
        return "redirect:" + redirectURL;
    }

    @GetMapping("/userInfo")
    @ApiOperation("userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) throws Exception {
        log.info("【微信网页授权】code={}", code);
        log.info("【微信网页授权】state={}", returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();


        log.info("【微信网页授权】openId={}", openId);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }


    @GetMapping("/authsss")
    public void auth(@RequestParam("code") String code,@RequestParam("state") String state){
        log.info("auth开始了。。。。");
        log.info("code={}",code);
        log.info("state={}",state);
    }

}

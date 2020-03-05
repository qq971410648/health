package com.lihao.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckGroup;
import com.lihao.domain.Setmeal;
import com.lihao.service.SetmealService;
import com.lihao.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 分页查询套餐信息
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @RequestMapping("/getAllSetmeal")
    public ResultEntity getAllSetmeal(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name) {
        try {
            PageInfo pageInfo = setmealService.getAllSetmeal(page, size, name);
            return ResultEntity.successWithData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据id删除套餐信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/delSetmeal")
    public ResultEntity delSetmeal(int id) {
        try {
            setmealService.delSetmeal(id);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 查询所有检查组信息
     *
     * @return
     */
    @RequestMapping("/getAllCheckGroup")
    public ResultEntity getAllCheckGroup() {
        try {
            List<CheckGroup> list = setmealService.getAllCheckGroup();
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 添加套餐信息
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/addSetmeal")
    public ResultEntity addSetmeal(@RequestBody Setmeal setmeal, int[] checkgroupIds) {
        try {
            setmealService.addSetmeal(setmeal, checkgroupIds);
            jedisPool.getResource().sadd("imgUp_all",setmeal.getImg());
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/getSetmealById")
    public ResultEntity getSetmealById(int id) {
        try {
            Setmeal setmeal = setmealService.getSetmealById(id);
            return ResultEntity.successWithData(setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据套餐id查询检查组id集合
     *
     * @param id
     * @return
     */
    @RequestMapping("/getGroupIdsById")
    public ResultEntity getGroupIdsById(int id) {
        try {
            List<Integer> list = setmealService.getGroupIdsById(id);
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 编辑套餐信息
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/editSetmeal")
    public ResultEntity editSetmeal(@RequestBody Setmeal setmeal, int[] checkgroupIds) {
        try {
            setmealService.editSetmeal(setmeal, checkgroupIds);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 文件上传
     *
     * @return
     */
    @RequestMapping("/upload")
    public ResultEntity upload(@RequestParam("imgFile") MultipartFile imgFile) throws IOException {
        //1. 获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        System.out.println("原始文件名：" + originalFilename);

        //2. 获取文件后缀
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(lastIndexOf - 1);

        //3. 使用UUID随机产生文件名称
        String fileName = UUID.randomUUID().toString() + suffix;

        /**
         * 4. 执行上传
         */

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-chengdu.aliyuncs.com";

        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FbnwDjciQyNYWynd3G5";
        String accessKeySecret = "mQuRITQ2jQ0A3j2484ocKGGxwk9Taw";
        String bucketName = "chuanzhi-health";
        String objectName = "lihao/test/" + fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        InputStream inputStream = imgFile.getInputStream();
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //5. 将上传后的图片名称存入redis
        jedisPool.getResource().sadd("imgUp_part",objectName);

        return ResultEntity.successWithData(objectName);
    }
}

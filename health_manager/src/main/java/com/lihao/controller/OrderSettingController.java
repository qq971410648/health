package com.lihao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lihao.domain.OrderSetting;
import com.lihao.service.OrderSettingService;
import com.lihao.util.POIUtils;
import com.lihao.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 上传excel摸板文件实现预约设置
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public ResultEntity upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            //1. 读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list != null && list.size() > 0) {

                //用于封装数据
                List<OrderSetting> orderSettingList = new ArrayList<>();

                //2. 遍历所有行
                for (String[] strings : list) {
                    String string_date = strings[0];//数组的0索引就是  时间
                    String string_count = strings[1];//数组的1索引就是   预约数量
                    Integer count = Integer.parseInt(string_count);
                    Date date = new Date(string_date);

                    OrderSetting orderSetting = new OrderSetting(date, count);
                    orderSettingList.add(orderSetting);//将excel里面的每行数据封装成一个对象并存入orderSettingList中
                }

                //3. 将重新整理封装的excel数据保存到数据库
                orderSettingService.addOrderSetting(orderSettingList);

                return ResultEntity.successNoData();
            }
            return ResultEntity.failed("文件上传失败");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 查询当月相关预约信息
     *
     * @param date
     * @return
     */
    @RequestMapping("/getDays")
    public ResultEntity getMonthOrderSetting(String date) {
        try {
            List<Map> list = orderSettingService.getMonthOrderSetting(date);
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 修改当天可预约人数
     *
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByDate")
    public ResultEntity editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}

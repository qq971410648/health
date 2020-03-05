package com.lihao.service;

import com.lihao.util.ResultEntity;

import java.util.Map;

public interface OrderService {
    ResultEntity order(Map map) throws Exception;
}

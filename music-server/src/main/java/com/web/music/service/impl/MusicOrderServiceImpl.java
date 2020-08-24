package com.web.music.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.web.music.dao.MusicOrderMapper;
import com.web.music.domain.MusicOrder;
import com.web.music.service.MusicOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicOrderServiceImpl implements MusicOrderService {


    @Value("${APP_ID}")
    private String appId;

    @Value("${APP_PRIVATE_KEY}")
    private String appPrivateKey;

    @Value("${ALIPAY_PUBLIC_KEY}")
    private String aliPayPublicKey;

    @Value("${SERVERURL}")
    private String serverUrl;

    @Value("${APPFORMAT}")
    private String appFormat;

    @Value("${SIGNTYPE}")
    private String signType;

    @Value("${CHARSET}")
    private String charset;

    @Autowired
    private MusicOrderMapper musicOrderMapper;


    @Transactional
    @Override
    public String doPay(MusicOrder musicOrder) {
        String form = "";

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, appPrivateKey, appFormat, charset, aliPayPublicKey, signType);  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl("http://localhost:8888/success");
        alipayRequest.setNotifyUrl("http://localhost:8888/notify.action"); //在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("  {" +
                "    \"total_amount\":" + musicOrder.getTotalAmount() + "," +
                "    \"out_trade_no\":'" + musicOrder.getOrderNo() + "'," +
                "   \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "   \"subject\":\"下载音乐付费\"" +
                " }"); //填充业务参数

        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return form;
    }
}

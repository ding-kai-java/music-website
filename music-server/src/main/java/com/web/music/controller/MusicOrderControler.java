package com.web.music.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.web.music.dao.MusicOrderMapper;
import com.web.music.domain.MusicOrder;
import com.web.music.service.MusicOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MusicOrderControler {

    @Autowired
    private MusicOrderService musicOrderService;

    @Value("${ALIPAY_PUBLIC_KEY}")
    private String aliPayPublicKey;

    @Value("${CHARSET}")
    private String charset;

    @Value("${SIGNTYPE}")
    private String signType;

    @Autowired
    private MusicOrderMapper musicOrderMapper;

    @ResponseBody
    @RequestMapping(value = "/musicOrder/saveOrder", method = RequestMethod.GET)
    public String saveOrder(HttpServletRequest req){
        String songId = req.getParameter("songId").trim();
        String totalAmount = req.getParameter("totalAmount").trim();
        String musicName = req.getParameter("name").trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = simpleDateFormat.format(Calendar.getInstance().getTime());

        MusicOrder musicOrder = new MusicOrder();
        musicOrder.setOrderNo(orderNo);
        musicOrder.setSongId(Integer.parseInt(songId));
        musicOrder.setProductCode("FAST_INSTANT_TRADE_PAY");
        musicOrder.setSubject("下载"+ musicName);
        musicOrder.setTotalAmount(new BigDecimal(20));
        musicOrder.setCreateTime(new Date());
        musicOrder.setUpdateTime(new Date());
        musicOrder.setPayStatus(0);
        return musicOrderService.doPay(musicOrder);
    }

    @ResponseBody
    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    public String notify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接收到支付宝的异步通知请求——");
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        Map<String,String> map = new HashMap<>();
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(map, aliPayPublicKey, charset, signType);
            if(signVerified){
                MusicOrder musicOrder = new MusicOrder();
                musicOrder.setPayStatus(1);
                //TODO 暂且
                musicOrder.setOrderNo("1");
                musicOrderMapper.updateByPrimaryKeySelective(musicOrder);
                return "异步 success";
            } else{
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "支付成功";
    }
}

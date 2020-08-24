package com.web.music.controller;

import com.web.music.domain.MusicOrder;
import com.web.music.service.MusicOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

@RestController
public class MusicOrderControler {

    @Autowired
    private MusicOrderService downloadPayService;

    @ResponseBody
    @RequestMapping(value = "/musicOrder/saveOrder", method = RequestMethod.GET)
    public String saveOrder(HttpServletRequest req){
        /*String songId = req.getParameter("songId").trim();
        String totalAmount = req.getParameter("totalAmount").trim();*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = simpleDateFormat.format(Calendar.getInstance().getTime());

        MusicOrder musicOrder = new MusicOrder();
        musicOrder.setOrderNo(orderNo);
        musicOrder.setSongId(Integer.parseInt("1"));
        musicOrder.setProductCode("");
        musicOrder.setSubject("");
        musicOrder.setTotalAmount(new BigDecimal(20));
        return downloadPayService.doPay(musicOrder);
    }

    @ResponseBody
    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    public void notify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接收到支付宝的异步通知请求——");
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        //成功处理后返回success
        response.getWriter().write("success");
    }

    @ResponseBody
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "支付成功";
    }
}

package com.tg.rpc.example.service;


import com.tg.rpc.springsupport.annotation.RpcService;

/**
 * Created by twogoods on 17/2/17.
 */
@RpcService
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String s) {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return s;
    }
}

package com.ytzl.gotrip.rpc.service;

import com.ytzl.gotrip.utils.exception.GotripException;

import java.text.ParseException;

public interface TokenService {
    public void retoken(String token, String userAgent) throws GotripException, ParseException;



}

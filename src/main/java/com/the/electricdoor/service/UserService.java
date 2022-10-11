package com.the.electricdoor.service;


public interface UserService {
    public boolean login(String user_name, String passwd);

    public void addToken(String token, String user_name);
}

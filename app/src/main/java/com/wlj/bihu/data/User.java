package com.wlj.bihu.data;

public class User {
    private int id;
    private String username;
    private String password;
    private String avatarAdress;
    private String token;

    //私有构造
    private User() {}

    //饿汉式单例 静态对象在类加载时就（真）创建好 线程安全
    private static User user = new User();

    //得到user
    public static User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAvatarAdress(String avatarAdress) {
        this.avatarAdress = avatarAdress;

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarAdress() {
        return avatarAdress;
    }

    public String getToken() {
        return token;
    }
}

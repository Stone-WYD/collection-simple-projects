package org.wyd.back.db;

/**
 * @author xh
 * @date 2025-02-08
 * @Description:
 */
public class SecurityContextHolder {
    private static final ThreadLocal<String> usernameHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> passwordHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> urlHolder = new ThreadLocal<>();

    public static void setCredentials(String host, String port, String dbName,
                                      String username, String password) {
        // 根据传参生成数据库连接的 url
        String url = String.format("jdbc:mysql://%s:%s/%s?characterEncoding=utf8&usSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&allowMultiQueries=true", host, port, dbName);
        usernameHolder.set(username);
        passwordHolder.set(password);
        urlHolder.set(url);
    }

    public static String getUsername() {
        return usernameHolder.get();
    }

    public static String getPassword() {
        return passwordHolder.get();
    }

    public static String getUrl() {return urlHolder.get();}

    public static void clearCredentials() {
        usernameHolder.remove();
        passwordHolder.remove();
        urlHolder.remove();
    }

}

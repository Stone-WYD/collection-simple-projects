
package com.jgdsun.qms.model.ims4;

import java.util.List;
import javax.annotation.Generated;
import org.noear.snack.annotation.ONodeAttr;

@Generated("net.hexar.json2pojo")
public class LoginData {

    @ONodeAttr(name="csrf_token")
    private String csrfToken;

    @ONodeAttr(name="user_id")
    private Long userId;

    @ONodeAttr(name="user_name")
    private String userName;

    private List<View> views;

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

}

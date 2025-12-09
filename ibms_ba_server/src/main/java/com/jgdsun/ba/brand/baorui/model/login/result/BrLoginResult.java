
package com.jgdsun.ba.brand.baorui.model.login.result;

import java.util.List;
 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

  
@SuppressWarnings("unused")
public class BrLoginResult {

    @SerializedName("access_token")
    private String accessToken;
    @Expose
    private String account;
    @Expose
    private String avatar;
    @SerializedName("dept_id")
    private String deptId;
    @Expose
    private Detail detail;
    @SerializedName("expires_in")
    private Long expiresIn;
    @Expose
    private Boolean largeDataScreen;
    @SerializedName("nick_name")
    private String nickName;
    @SerializedName("oauth_id")
    private Object oauthId;
    @SerializedName("post_id")
    private String postId;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("role_id")
    private String roleId;
    @Expose
    private List<String> roleIds;
    @SerializedName("role_name")
    private String roleName;
    @SerializedName("tenant_id")
    private String tenantId;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_name")
    private String userName;
    @Expose
    private Version version;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Boolean getLargeDataScreen() {
        return largeDataScreen;
    }

    public void setLargeDataScreen(Boolean largeDataScreen) {
        this.largeDataScreen = largeDataScreen;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getOauthId() {
        return oauthId;
    }

    public void setOauthId(Object oauthId) {
        this.oauthId = oauthId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

}

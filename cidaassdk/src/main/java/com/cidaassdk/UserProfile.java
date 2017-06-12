package com.cidaassdk;

/**
 * Created by Suprada on 21-Mar-17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by suprada on 19/2/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile implements Serializable{
    private String id;
    private String provider;

    private String ssoId;
    private String username;
    private String email;
    private String mobile;
    private String firstname;
    private String lastname;
    private String displayName;
    private Date createTime;
    private boolean active;
    private boolean emailVerified;
    private boolean mobileNoVerified;
    private boolean smsNotificationEnabled;
    private boolean googleAuthenticatorEnabled;
    private Locale currentLocale;
    private UserStatus userStatus;
    private String identityJRString;
    @JsonProperty("customFields")
    private CustomFields customFields;
    private List<String> roles;
    private boolean twofactorenabled;
    private Date lastLoggedTime;
    private ObjectIdInfo lastUsedSocialIdentity;
    private String photoURL;
    private List<String> usedProviders;
    @JsonProperty("customFieldWithMetadata")
    private Map<String, CustomFieldDataEntity> customFieldWithMetadata;
    private Map<String,String> groups;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobileNoVerified() {
        return mobileNoVerified;
    }

    public void setMobileNoVerified(boolean mobileNoVerified) {
        this.mobileNoVerified = mobileNoVerified;
    }

    public boolean isSmsNotificationEnabled() {
        return smsNotificationEnabled;
    }

    public void setSmsNotificationEnabled(boolean smsNotificationEnabled) {
        this.smsNotificationEnabled = smsNotificationEnabled;
    }

    public boolean isGoogleAuthenticatorEnabled() {
        return googleAuthenticatorEnabled;
    }

    public void setGoogleAuthenticatorEnabled(boolean googleAuthenticatorEnabled) {
        this.googleAuthenticatorEnabled = googleAuthenticatorEnabled;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getIdentityJRString() {
        return identityJRString;
    }

    public void setIdentityJRString(String identityJRString) {
        this.identityJRString = identityJRString;
    }

    public CustomFields getCustomFields() {
        return customFields;
    }

    public void setCustomFields(CustomFields customFields) {
        this.customFields = customFields;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isTwofactorenabled() {
        return twofactorenabled;
    }

    public void setTwofactorenabled(boolean twofactorenabled) {
        this.twofactorenabled = twofactorenabled;
    }

    public Date getLastLoggedTime() {
        return lastLoggedTime;
    }

    public void setLastLoggedTime(Date lastLoggedTime) {
        this.lastLoggedTime = lastLoggedTime;
    }

    public ObjectIdInfo getLastUsedSocialIdentity() {
        return lastUsedSocialIdentity;
    }

    public void setLastUsedSocialIdentity(ObjectIdInfo lastUsedSocialIdentity) {
        this.lastUsedSocialIdentity = lastUsedSocialIdentity;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public List<String> getUsedProviders() {
        return usedProviders;
    }

    public void setUsedProviders(List<String> usedProviders) {
        this.usedProviders = usedProviders;
    }

    public Map<String, CustomFieldDataEntity> getCustomFieldWithMetadata() {
        return customFieldWithMetadata;
    }

    public void setCustomFieldWithMetadata(Map<String, CustomFieldDataEntity> customFieldWithMetadata) {
        this.customFieldWithMetadata = customFieldWithMetadata;
    }

    public Map<String, String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }
}

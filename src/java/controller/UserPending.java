package controller;

import java.util.Date;

/**
 *
 * @author Tom Ellman
 */
public class UserPending {

    public UserPending() {
    }

    public UserPending(String userName, String emailAddress,
            String password, String activationCode) {
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.activationCode = activationCode;
        this.timeStamp = new Date();
    }

    protected String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    protected String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    protected String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected String activationCode;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    protected Date timeStamp;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}

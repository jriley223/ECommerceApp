package controller;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Tom Ellman
 */
public class User {

    public User() {
    }

    public User(UserPending userPending) {
        this.userName = userPending.userName;
        this.emailAddress = userPending.emailAddress;
        this.password = userPending.password;
        this.roles = new HashSet<String>();
        this.roles.add("user");
    }

    protected String userName;
    public String getUserName() { return userName; }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    protected String emailAddress;
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    protected String password;
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    private Set<String> roles;
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    public void addRole(String role) {
        roles.add(role);
    }
    public void deleteRole(String role) {
        roles.remove(role);
    }
}

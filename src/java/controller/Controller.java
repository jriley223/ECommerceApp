package controller;

import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Tom Ellman
 */
public class Controller extends HttpServlet {

    public void init() throws ServletException {
        HibernateHelper.initSessionFactory(User.class, UserPending.class);
    }

    private CatalogItem item = new CatalogItem();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public HttpServletRequest request;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String emailAddress = request.getParameter("emailAddress");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String itemID = request.getParameter("itemID");
        HttpSession session = request.getSession();
        session.setAttribute("userName", userName);
        session.setAttribute("emailAddress", emailAddress);
        this.request = request;
        String address = null;
        if (request.getParameter("enrollButton") != null) {
            address = enrollMethod(userName, emailAddress, password1, password2);
        } else if (request.getParameter("retryButton") != null) {
            address = retryMethod();
        } else if (request.getParameter("activationCode") != null) {
            address = activationMethod(request, userName);
        } else if (request.getParameter("addCart") != null) {
            address = methodAddCart(itemID);
        } else if (request.getParameter("emptyCart") != null) {
            address = methodEmptyCart();
        } else if (request.getParameter("viewItem") != null) {
            address = methodViewItem(itemID);
        } else if (request.getParameter("viewCart") != null) {
            address = methodViewCart();
        } else if (request.getParameter("processCart") != null) {
            address = methodProcessCart();
        } else if (request.getParameter("billingInfo") != null) {
            address = methodBillingInfo();
        } else if (request.getParameter("purchaseHistory") != null) {
            address = methodpurchaseHistory();
        } else {
            address = methodDefault();
        }
        this.request.getRequestDispatcher(address).forward(this.request, response);
    }

    private String enrollMethod(String userName, String emailAddress,
            String password1, String password2) {
        String address = null;
        if (password1.equals(password2)) {
            String activationCode = generateActivationCode(userName, emailAddress);
            enrollUser(userName, emailAddress, password1, activationCode);
            sendEmail(userName, emailAddress, activationCode);
            address = "/emailSent.jsp";
        } else {
            address = "/passwordMismatch.jsp";
        }
        return address;
    }

    private void enrollUser(String userName, String emailAddress,
            String password, String code) {
        String digest = generateDigest(password);
        UserPending userPending = new UserPending(userName, emailAddress, digest, code);
        HibernateHelper.updateDB(userPending);
    }

    private String retryMethod() {
        return "/index.jsp";
    }

    private String activationMethod(HttpServletRequest request, String userName) {
        String address = null;
        String activationCode = request.getParameter("activationCode");
        UserPending userPending = (UserPending) HibernateHelper.getFirstMatch(UserPending.class, "userName", userName);
        if (validActivation(userPending, activationCode)) {
            activateUser(userPending);
            address = "/index.jsp";
        } else {
            address = "/invalidActivation.jsp";
        }
        return address;
    }

    private static final long MAX_DELAY = 86400000;

    private boolean validActivation(UserPending userPending, String activationCode) {
        if (userPending == null) {
            return false;
        }
        if (!userPending.getActivationCode().equals(activationCode)) {
            return false;
        }
        Date date = userPending.getTimeStamp();
        Date current = new Date();
        long delay = current.getTime() - date.getTime();
        if (delay > MAX_DELAY) {
            return false;
        }
        return true;
    }

    private void activateUser(UserPending userPending) {
        User user = new User(userPending);
        HibernateHelper.removeDB(userPending);
        HibernateHelper.updateDB(user);
    }

    private String generateDigest(String password) {
        byte[] defaultBytes = password.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xff & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException nsae) {
            return null;
        }
    }

    private String generateActivationCode(String userName, String emailAddress) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(160, random).toString(32);
    }

    private static String msgTemplate = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"
            + "<html>"
            + "<body>"
            + "<a href=\"https://localhost:8443/UserManagement/Controller"
            + "?"
            + "userName=USER_NAME"
            + "&"
            + "activationCode=ACTIVATION_CODE"
            + "\">Click here to activate your account!</a>"
            + "</body>"
            + "</html>";

    private void sendEmail(String userName, String emailAddress, String activationCode) {
        String subject = "Account Activation";
        String msgText = msgTemplate;
        msgText = msgText.replaceFirst("USER_NAME", userName);
        msgText = msgText.replaceFirst("ACTIVATION_CODE", activationCode);
        String to = emailAddress;
        String from = "kahodge@vassar.edu";
        String host = "smtp.vassar.edu";
//        String host = "mail.optimum.net";
        String port = "587";
//        String port = "25";
        boolean authenticate = true;
        boolean debug = true;
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        Authenticator auth = null;
        if (authenticate) {
            auth = new SMTPAuthenticator();
            props.put("mail.smtp.auth", "true");
        }
        Session session = Session.getInstance(props, auth);
        if (debug) {
            props.put("mail.debug", "true");
        }
        session.setDebug(debug);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            // setText(text);
            // setText(text, charset)
            // msg.setText(msgText,"UTF-8");
            msg.setText(msgText, "UTF-8", "html");

            Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("Exception in sending email message.");
            mex.printStackTrace();
            System.out.println();
            Exception ex = mex;
            do {
                if (ex instanceof SendFailedException) {
                    SendFailedException sfex = (SendFailedException) ex;
                    Address[] invalid = sfex.getInvalidAddresses();
                    if (invalid != null) {
                        System.out.println("    ** Invalid Addresses");
                        if (invalid != null) {
                            for (int i = 0; i < invalid.length; i++) {
                                System.out.println("         " + invalid[i]);
                            }
                        }
                    }
                    Address[] validUnsent = sfex.getValidUnsentAddresses();
                    if (validUnsent != null) {
                        System.out.println("    ** ValidUnsent Addresses");
                        if (validUnsent != null) {
                            for (int i = 0; i < validUnsent.length; i++) {
                                System.out.println("         " + validUnsent[i]);
                            }
                        }
                    }
                    Address[] validSent = sfex.getValidSentAddresses();
                    if (validSent != null) {
                        System.out.println("    ** ValidSent Addresses");
                        if (validSent != null) {
                            for (int i = 0; i < validSent.length; i++) {
                                System.out.println("         " + validSent[i]);
                            }
                        }
                    }
                }
                System.out.println();
                if (ex instanceof MessagingException) {
                    ex = ((MessagingException) ex).getNextException();
                } else {
                    ex = null;
                }
            } while (ex != null);
        }
    }

    public String jspLocation(String page) {
        return "" + page;
    }

    public String methodDefault() {
        List list = HibernateHelper.getListData(CatalogItem.class);
        request.setAttribute("allItems", list);
        return jspLocation("/secureUser/BrowseLoop.jsp");
    }

    public String methodAddCart(String itemID) {
        ShoppingCart shoppingCart = new ShoppingCart(request.getRemoteUser(), Integer.parseInt(itemID));
        Object dbObj = HibernateHelper.getFirstMatch(CatalogItem.class, "itemId", itemID);
        if (dbObj != null) {
            CatalogItem item = (CatalogItem) dbObj;

            HibernateHelper.updateDB(item);
            HibernateHelper.updateDB(shoppingCart);

        }
        return methodDefault();
    }

    public String methodEmptyCart() {
        List dbObj = HibernateHelper.getListData(ShoppingCart.class, "userName", request.getRemoteUser());
        if (dbObj != null) {
            for (Object b : dbObj) {
                ShoppingCart cartItem = (ShoppingCart) b;
                CatalogItem catalogItem = (CatalogItem) HibernateHelper.getFirstMatch(CatalogItem.class, "itemId",
                        String.valueOf(cartItem.getItemID()));
                HibernateHelper.updateDB(catalogItem);
                HibernateHelper.removeDB(b);
            }
        }
        return methodDefault();
    }

    public String methodViewItem(String itemID) {
        Object dbObj = HibernateHelper.getFirstMatch(CatalogItem.class, "itemId", itemID);
        if (dbObj != null) {
            item = (CatalogItem) dbObj;

        }
        request.setAttribute("CatalogItem", item);
        return methodDefault();
    }

    public String methodViewCart() {
        List dbObj = HibernateHelper.getListData(ShoppingCart.class, "userName", request.getRemoteUser());
        List shoppingcart = new LinkedList();
        if (dbObj != null) {
            for (Object b : dbObj) {
                ShoppingCart c = (ShoppingCart) b;
                Object item = HibernateHelper.getFirstMatch(CatalogItem.class, "itemId", String.valueOf(c.getItemID()));
                CatalogItem catItem = (CatalogItem) item;
                shoppingcart.add(catItem);
            }
        }
        request.setAttribute("cart", shoppingcart);
        return jspLocation("/secureUser/Cart.jsp");
    }

    public String methodProcessCart() {
        List dbObj = HibernateHelper.getListData(ShoppingCart.class, "userName", request.getRemoteUser());
        List cart = new LinkedList();
        double priceTotal = 0.00;
        if (dbObj != null) {
            for (Object x : dbObj) {
                ShoppingCart c = (ShoppingCart) x;
                Object item = HibernateHelper.getFirstMatch(CatalogItem.class, "itemId", String.valueOf(c.getItemID()));
                CatalogItem catItem = (CatalogItem) item;
                UserTransaction uT = new UserTransaction(request.getRemoteUser(), c.getItemID());
                uT.setTimeStamp();
                priceTotal += catItem.getPrice();
                cart.add(catItem);
                HibernateHelper.updateDB(uT);
                HibernateHelper.removeDB(x);
            }
        }
        request.setAttribute("cart", cart);
        request.setAttribute("priceTotal", priceTotal);
        return jspLocation("/secureUser/ProcessCart.jsp");
    }

    public String methodpurchaseHistory() {
        List dbObj = HibernateHelper.getListData(UserTransaction.class, "userName", request.getRemoteUser());
        List<UserTransaction> orders = new LinkedList();
        if (dbObj != null) {
            for (Object b : dbObj) {
                UserTransaction order = (UserTransaction) b;
                CatalogItem catItem = (CatalogItem) HibernateHelper.getFirstMatch(CatalogItem.class, "itemId", String.valueOf(order.getItemID()));
                order.setName(catItem.getName());
                order.setPrice(catItem.getPrice());
                order.setDescription(catItem.getDescription());
                order.getTimeStamp();
                orders.add(order);
            }
        }
        request.setAttribute("orders", orders);
        return "/secureUser/purchaseHistory.jsp";
    }

    public String methodBillingInfo() {
        return "/secureUser/billingInfo.jsp";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

class SMTPAuthenticator extends Authenticator {

    public PasswordAuthentication getPasswordAuthentication() {
        String username = "kahodge";
        String password = "1pkitty03!";
        return new PasswordAuthentication(username, password);
    }
}

package file_transfer_pc_testclient;

import java.util.Scanner;

import org.alljoyn.bus.AuthListener;
import org.alljoyn.bus.AuthListener.AuthRequest;
import org.alljoyn.bus.AuthListener.PasswordRequest;
import org.alljoyn.bus.AuthListener.UserNameRequest;

class SRPLogonAuthClient implements AuthListener {
    public boolean requested(String mechanism, String peerName, int count, String userName,
            AuthRequest[] requests) {
        System.out.println(String.format("AuthListener.requested(%s, %s, %d, %s, %s);", 
                mechanism ,
                peerName,
                count,
                userName,
                AuthRequestsToString(requests)));

        /* Collect the requests we're interested in to simplify processing below. */
        PasswordRequest passwordRequest = null;
        UserNameRequest userNameRequest = null;
        for (AuthRequest request : requests) {
            if (request instanceof PasswordRequest) {
                passwordRequest = (PasswordRequest) request;
            } else if (request instanceof UserNameRequest) {
                userNameRequest = (UserNameRequest) request;
            }
        }

        if (count <= 3) {

            System.out.print("Please enter user name [user1]: ");
            Scanner in = new Scanner(System.in);
            String user = in.nextLine();
            //check if the string is empty if so use a default user name
            if(user !=null && user.length() == 0) {
                user = "user1";
            }

            System.out.print("Please enter password [password1]: ");
            String password = in.nextLine();
            //check if the string is empty if so use a default password
            if(user != null && password.length() == 0) {
                password = "password1";
            }

            userNameRequest.setUserName(user);
            passwordRequest.setPassword(password.toCharArray());
            return true;
        }
        return false;
    }

    public void completed(String authMechanism, String authPeer, boolean authenticated) {
        if (!authenticated) {
            System.out.println("Authentication failed.");
        }
    }

    private String AuthRequestsToString(AuthListener.AuthRequest[] requests) {
        String str;
        str = "[";
        for (AuthListener.AuthRequest request : requests) {
            if (request instanceof AuthListener.CertificateRequest) {
                str += "CertificateRequest ";
            }
            if (request instanceof AuthListener.LogonEntryRequest) {
                str += "LogonEntryRequest ";
            }
            if (request instanceof AuthListener.PrivateKeyRequest) {
                str += "PrivateKeyRequest ";
            }
            if (request instanceof AuthListener.UserNameRequest) {
                str += "UserNameRequest ";
            }
            if (request instanceof AuthListener.PasswordRequest) {
                str += "PasswordRequest ";
            }
            if (request instanceof AuthListener.VerifyRequest) {
                str += "VerifyRequest ";
            }
        }
        str += "]";
        return str;
    }
}

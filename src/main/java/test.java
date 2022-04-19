import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        String username = "filippo";
        String pass = "1234";

        boolean login_success = AuthManager.userLogin(username, pass);
        if (login_success) {
            System.out.println("ok");
        }

    }
}

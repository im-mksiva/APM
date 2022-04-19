public class User {
    private String username;
    private int user_id;

    User(String username, int user_id) {
        this.username = username;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }
}

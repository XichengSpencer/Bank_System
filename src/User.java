import java.text.SimpleDateFormat;
import java.util.UUID;

public class User {
    private String name;
    private String password;
    private String id;

    private static volatile int Guid = 100;

    public User(String name, String password, String id) {
        this.name = name;
        this.password = password;
        this.id = id;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.id = getGuid();
    }

    public static String getGuid()
    {
        User.Guid += 1;

        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String time = dateFormat.format(now);
        String info = now + "";
        int ran = 0;
        if (User.Guid > 999)
        {
            User.Guid = 100;
        }
        ran = User.Guid;

        return time + info.substring(2, info.length()) + ran;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}

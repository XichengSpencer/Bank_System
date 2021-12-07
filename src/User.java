import java.util.UUID;

public class User {
    private String name;
    private String password;
    private String id;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
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

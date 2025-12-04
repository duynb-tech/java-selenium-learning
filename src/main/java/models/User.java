package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // 🔥 Mẹo: Bỏ qua các trường thừa không cần thiết (address, company...)
public class User {
    private String name;
    private String email;

    // Constructor rỗng (Bắt buộc)
    public User() {}

    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
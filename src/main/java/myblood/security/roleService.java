package myblood.security;

public interface roleService {
    SecRole addRole(String role);
    void addRoleToUser(String login, String role);

}

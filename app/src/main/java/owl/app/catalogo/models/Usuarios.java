package owl.app.catalogo.models;

public class Usuarios {

    private int id;
    private  String usuario;
    private  String password;
    private  String role;
    private  String mail;

    public Usuarios(int id, String usuario, String password, String role, String mail) {
        setId(id);
        setUsuario(usuario);
        setPassword(password);
        setRole(role);
        setMail(mail);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

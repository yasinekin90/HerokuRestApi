package POJOS.travelerpojo;


public class Travelerinformation {
    private String createdat;
    private String name;
    private int id;
    private String adderes;
    private String email;

    public Travelerinformation(int id, String name, String email, String adderes, String createdat) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.adderes = adderes;
        this.createdat = createdat;
    }
    public String getCreatedat() {
        return createdat;
    }
    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAdderes() {
        return adderes;
    }
    public void setAdderes(String adderes) {
        this.adderes = adderes;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}

package hu.humanskill.page.model;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;
@NamedQueries({
        @NamedQuery(name="getAllApplies",
        query = "SELECT c FROM Apply c"),
        @NamedQuery(name = "findById",
        query = "SELECT c FROM Apply c WHERE c.id = :id")
})
@Entity
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String job;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String filename;


    public Apply() {
    }

    public Apply(String name,String job, String email, String phone, String filename) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.filename = filename;
    }

    public Apply(String name, String email, String phone, String filename) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

package hu.humanskill.page.model;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name="getAllApplies",
        query = "SELECT c FROM Apply c ORDER BY created DESC"),
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Apply() {}

    public Apply(String name,String job, String email, String phone, String filename) {
        this.name = name;
        this.job = job;
        this.email = email;
        this.phone = phone;
        this.filename = filename;
    }

    public Apply(String name, String job, String email, String phone, String filename, Timestamp created) {
        this.name = name;
        this.job = job;
        this.email = email;
        this.phone = phone;
        this.filename = filename;
        this.created = created;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}

import org.sql2o.*;
import java.util.List;

public class Ranger extends Person {
  private int rangerId;
  private int badge;
  private String workcontact;

  public Ranger(String firstname, String lastname, String phone, String street, String city, String state, String zip, String email, int badge, String contact) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.phonenumber = phone;
    this.address = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.email = email;
    this.badge = badge;
    this.workcontact = contact;
    this.save();
  }

  public void save() {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "INSERT INTO people (firstname, lastname, phonenumber, address, city, state, zip, email, badge, workcontact, type) VALUES (:firstname, :lastname, :phonenumber, :address, :city, :state, :zip, :email, :badge, :workcontact, 1)";
      this.id = (int) cn.createQuery(sql, true)
        .addParameter("lastname", this.lastname)
        .addParameter("firstname", this.firstname)
        .addParameter("phonenumber", this.phonenumber)
        .addParameter("address", this.address)
        .addParameter("city", this.city)
        .addParameter("state", this.state)
        .addParameter("zip", this.zip)
        .addParameter("email", this.email)
        .addParameter("badge", this.badge)
        .addParameter("workcontact", this.workcontact)
        .throwOnMappingFailure(false)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Ranger> all() {
    String sql = "SELECT * FROM people WHERE type=1 ORDER BY lastname, firstname";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Ranger.class);
    }
  }

  public static Ranger find(int id) {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "SELECT * FROM people WHERE id=:id";
      Ranger ranger = cn.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Ranger.class);
      return ranger;
    }
  }

  public static Ranger findByBadge(int badge) {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "SELECT * FROM people WHERE badge=:badge";
      Ranger ranger = cn.createQuery(sql)
        .addParameter("badge", badge)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Ranger.class);
      return ranger;
    }
  }

  public int getBadge() {
    return badge;
  }

  public void setBadge(int badge) {
    this.badge=badge;
    try(Connection cn = DB.sql2o.open()) {
      String sql = "UPDATE people SET badge = :badge WHERE id = :id";
      cn.createQuery(sql)
        .addParameter("badge", badge)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public String getContact() {
    return workcontact;
  }

  public void setContact(String workcontact) {
    this.workcontact=workcontact;
    try(Connection cn = DB.sql2o.open()) {
      String sql = "UPDATE people SET workcontact = :workcontact WHERE id = :id";
      cn.createQuery(sql)
        .addParameter("workcontact", workcontact)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

}

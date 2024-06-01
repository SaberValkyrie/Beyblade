

package com.example.demo.entity;
        import javax.persistence.*;
        import lombok.Data;

        import java.sql.Timestamp;
        import java.util.Collection;
        import java.util.Objects;

@Entity
@Data
@Table(name = "giftcode")
public class GIFTCODE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "code")
    public String code;

    @Column(name = "type")
    public int type;


    @Column(name = "used")
    public boolean used;

}

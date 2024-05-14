
package com.example.demo.entity;
        import javax.persistence.*;
        import lombok.Data;

        import java.sql.Timestamp;
        import java.util.Collection;
        import java.util.Objects;

@Entity
@Data
@Table(name = "type_bey")
public class TypeBey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name = "images")
    public String images;

}

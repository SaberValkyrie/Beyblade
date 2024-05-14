
package com.example.demo.entity;
        import javax.persistence.*;
        import lombok.Data;

        import java.sql.Timestamp;
        import java.util.Collection;
        import java.util.Objects;

@Entity
@Data
@Table(name = "list_bey")
public class BeyBlade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name = "images")
    public String images;

    @Column(name = "character")
    public String character;

    @ManyToOne
    @JoinColumn(name = "type")
    public TypeBey type;

    @Column(name = "price")
    public int price;



    @Column(name = "isBoss")
    public boolean isBoss;

    @Column(name = "power")
    public int power;

    @Column(name = "hp")
    public long hp;

    @Column(name = "tiLeNeDon")
    public byte tiLeNeDon;

    

}

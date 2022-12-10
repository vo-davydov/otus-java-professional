package ru.otus.crm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "street")
    private String street;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(String street) {
        this.id = null;
        this.street = street;
    }
}

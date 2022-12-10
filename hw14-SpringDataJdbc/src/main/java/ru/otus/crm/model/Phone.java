package ru.otus.crm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(String number) {
        this.id = null;
        this.number = number;
    }

    public Phone(Long id, String number, Client client) {
        this.id = id;
        this.number = number;
        this.client = client;
    }
}

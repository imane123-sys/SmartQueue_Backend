package org.example.smartqueue.entity;

import jakarta.persistence.OneToMany;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {

    private String telephone;
    @OneToMany(mappedBy ="client")
    private List<Ticket> tickets = new ArrayList<>();
}

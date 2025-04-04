package org.example.restaurantmanagementapplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  public void setName(String name) {
    if (!name.startsWith("ROLE_")) {
      this.name = "ROLE_" + name;
    } else {
      this.name = name;
    }
  }
}

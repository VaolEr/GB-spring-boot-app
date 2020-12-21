package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractNamedEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity(name = "Supplier")
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
public class Supplier extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier")
    // этот момент можно будет обдумать, стоит ли реализовывать обработку "вложенных" изменений
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    private List<Item> items;

}

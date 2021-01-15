package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractNamedEntity;
import com.example.storehouse.util.StorehousesUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Storehouses")
@Table(name = "storehouses")
// если правильно помню - hibernate работает с контрактом java bean
// нужны акцессоры и конструктор без параметров
// https://stackoverflow.com/questions/3295496/what-is-a-javabean-exactly
@Getter
@Setter
@NoArgsConstructor
public class Storehouse extends AbstractNamedEntity {

    // Корректно, наверно вытаскивать items из itemsStorehouse?
    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("storehouse")
    private Set<ItemStorehouse> itemStorehouses = new HashSet<>();

    public Set<ItemStorehouse> getItemStorehouses() {
        return itemStorehouses;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemStorehouses")
//    // этот момент можно будет обдумать, стоит ли реализовывать обработку "вложенных" изменений
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @JsonBackReference
//    private List<Item> items;

}

package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractNamedEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Storehouses")
@Table(name = "storehouses")
// если правильно помню - hibernate работает с контрактом java bean
// нужны акцессоры и конструктор без параметров
// https://stackoverflow.com/questions/3295496/what-is-a-javabean-exactly
@Getter
@Setter
@NoArgsConstructor
public class Storehouse extends AbstractNamedEntity {

    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("storehouse")
    private Set<ItemStorehouse> itemStorehouses;

    public void addItemStorehouse(ItemStorehouse itemStorehouse) {
//        this.itemStorehouses = getItemStorehousesInstance();
        itemStorehouse.setStorehouse(this);
        itemStorehouses.add(itemStorehouse);
    }


    public void setItemStorehouses(Set<ItemStorehouse> itemsStorehouses) {
//        this.itemStorehouses = getItemStorehousesInstance();
        itemStorehouses.addAll(itemsStorehouses.stream()
                .peek(itemStorehouse -> itemStorehouse.setStorehouse(this))
                .collect(Collectors.toSet())
        );
    }

    private Set<ItemStorehouse> getItemStorehousesInstance(){
        return (itemStorehouses != null) ? itemStorehouses : new HashSet<>();
    }

}

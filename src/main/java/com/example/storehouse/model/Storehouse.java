package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractNamedEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL)
    private Set<ItemStorehouse> itemStorehouses;

}

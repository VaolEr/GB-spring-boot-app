package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "ItemStorehouse")
@Table(name = "items_storehouses")
public class ItemStorehouse extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties("itemStorehouses")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "storehouse_id")
    @JsonIgnoreProperties("itemStorehouses")
    private Storehouse storehouse;

    @Column(name = "qty")
    private int quantity;

    // Ошибался. При добавлении нового item для него ещё нет записи в items_storehouses,
    // след. - у ItemStorehouse нет id и для сравнения нужны поля.

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        if (!super.equals(o)) {
//            return false;
//        }
//        ItemStorehouse that = (ItemStorehouse) o;
//        return id.equals(that.id)
//            && quantity == that.quantity && item.equals(that.item) && storehouse
//            .equals(that.storehouse);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), id, item, storehouse, quantity);
//    }

}

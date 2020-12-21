package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractBaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ItemStorehouse")
@Table(name = "items_storehouses")
public class ItemStorehouse extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "storehouse_id")
    private Storehouse storehouse;

    @Column(name = "qty")
    private int quantity;

    // На мой взгляд - equals и hashCode нам здесь не нужны, т.к.:
    // 1. Мы наследуемся от AbstractBaseEntity, в которой есть Lombok-аннотация @Data,
    // а она включает в себя генерацию этих методов (можно поставить курсор на аннотацию @Data
    // и в идее нажать Refactor->Delombok->@Data, чтобы посмотреть).
    // 2. Мы ввели суррогатный ключ для данной сущности и таблицы (id) на уровне БД, и СУБД своими
    // средствами обеспечивает его уникальность на уровне таблицы БД. Т.е. под одним id у нас не
    // может быть в таблице записей с разными quantity, item, storehouse. И сравнивать по ним нет
    // смысла, достаточно сравнить по id (а это уже есть в AbstractBaseEntity).
    // Предлагаю обдумать и обсудить этот вопрос.

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

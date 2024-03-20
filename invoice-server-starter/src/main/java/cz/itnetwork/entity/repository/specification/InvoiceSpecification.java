package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {
    private final InvoiceFilter filter;

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getMinPrice() != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));

        if (filter.getMaxPrice() != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));

        if (filter.getBuyerID() != null){
            Join<PersonEntity, InvoiceEntity> personEntityJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(personEntityJoin.get(PersonEntity_.ID), filter.getBuyerID()));}

        if (filter.getSellerID() != null){
            Join<PersonEntity, InvoiceEntity> personEntityJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(personEntityJoin.get(PersonEntity_.ID), filter.getSellerID()));}

        if(filter.getProduct() != null)
            predicates.add(criteriaBuilder.like(root.get(InvoiceEntity_.PRODUCT),"%"+filter.getProduct()+"%"));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

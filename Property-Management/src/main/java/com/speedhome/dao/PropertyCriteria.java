package com.speedhome.dao;


import com.speedhome.entity.Property;
import com.speedhome.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyCriteria {
    private final String DEFAULT_SORT_BY = "id";

    @Autowired
    private EntityManager em;

    public List<Property> getPropertiesWithSearch(String sortBy, String sortOrder, int pageSize, String searchString,
                                                  int categoryId, int pageIndex) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Property> cq = cb.createQuery(Property.class);

        Root<Property> property = cq.from(Property.class);

        Path<Integer> category_Id = property.join("category").get("id");

        cq.select(property);

        if (categoryId == 0 && StringUtils.check(searchString, sortBy)) {

            cq.orderBy(cb.asc(property.get(DEFAULT_SORT_BY)));

        } else if (StringUtils.check(searchString, sortBy)) {

            cq.where(cb.equal(category_Id, categoryId));

            if (sortOrder.equals("ASC"))
                cq.orderBy(cb.asc(property.get(DEFAULT_SORT_BY)));
            else
                cq.orderBy(cb.desc(property.get(DEFAULT_SORT_BY)));


        } else {

            ArrayList<Predicate> conditions = new ArrayList<>();
            conditions.add(cb.like(property.get("address"), "%" + searchString + "%"));
            conditions.add(cb.like(property.get("city"), "%" + searchString + "%"));
            conditions.add(cb.like(property.get("pincode"), "%" + searchString + "%"));
            conditions.add(cb.like(property.get("state"), "%" + searchString + "%"));

            Predicate findByCatagoryId = cb.equal(category_Id, categoryId);

            cq.where(cb.or(conditions.toArray(new Predicate[conditions.size()])), cb.and(findByCatagoryId));

            if (sortOrder.equals("ASC"))
                cq.orderBy(cb.asc(property.get(sortBy)));
            else
                cq.orderBy(cb.desc(property.get(sortBy)));

        }

        TypedQuery<Property> query = em.createQuery(cq);

        return query.setFirstResult(pageSize * (pageIndex - 1)).setMaxResults(pageSize).getResultList();
    }
}



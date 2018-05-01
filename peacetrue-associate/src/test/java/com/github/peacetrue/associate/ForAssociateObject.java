package com.github.peacetrue.associate;

import com.github.peacetrue.associate.jpa.JpaObjectCollectionAssociatedSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class ForAssociateObject extends JpaObjectCollectionAssociatedSource<Long, Associated> {
    public ForAssociateObject() {
        setEntityClass(Associated.class);
    }
}

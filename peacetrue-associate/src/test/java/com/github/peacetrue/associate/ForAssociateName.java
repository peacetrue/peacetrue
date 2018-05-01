package com.github.peacetrue.associate;

import com.github.peacetrue.associate.jpa.JpaPropertyCollectionAssociatedSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class ForAssociateName extends JpaPropertyCollectionAssociatedSource<Long, String> {

    public ForAssociateName() {
        setEntityClass(Associated.class);
        setAssociatedProperty("name");
    }

}

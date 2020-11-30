package com.github.peacetrue.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * tests for {@link EntityManagerFactoryHolder}
 *
 * @author xiayx
 */
public class EntityManagerFactoryHolderTest extends JpaTest {

    public void setUp() throws Exception {
        super.setUp();
        EntityManagerFactoryHolder.setEntityManagerFactory(entityManagerFactory);
    }

    @Test
    public void getEntityManagerFactory() throws Exception {
        Assertions.assertEquals(entityManagerFactory, EntityManagerFactoryHolder.getEntityManagerFactory());
    }

}

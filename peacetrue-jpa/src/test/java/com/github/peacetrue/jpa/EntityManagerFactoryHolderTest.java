package com.github.peacetrue.jpa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * tests for {@link EntityManagerFactoryHolder}
 *
 * @author xiayx
 */
public class EntityManagerFactoryHolderTest extends JpaTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        EntityManagerFactoryHolder.setEntityManagerFactory(entityManagerFactory);
    }

    @Test
    public void getEntityManagerFactory() throws Exception {
        Assert.assertEquals(entityManagerFactory, EntityManagerFactoryHolder.getEntityManagerFactory());
    }

}
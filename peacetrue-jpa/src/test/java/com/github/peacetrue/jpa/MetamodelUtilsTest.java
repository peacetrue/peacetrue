/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package com.github.peacetrue.jpa;

import com.github.peacetrue.util.AssertUtils;
import org.junit.Assert;
import org.junit.Test;


/**
 * tests for {@link MetamodelUtils}
 *
 * @author xiayx
 */
public class MetamodelUtilsTest extends JpaTest {

    @Test
    public void getEntityClass() throws Exception {
        Class entityClass = MetamodelUtils.getEntityClass(entityManagerFactory.getMetamodel(), UnNamed.class.getSimpleName());
        Assert.assertEquals(entityClass, UnNamed.class);
        entityClass = MetamodelUtils.getEntityClass(entityManagerFactory.getMetamodel(), Named.TABLE_NAME);
        Assert.assertEquals(entityClass, Named.class);
        entityClass = MetamodelUtils.getEntityClass(entityManagerFactory.getMetamodel(), "not exists");
        Assert.assertNull(entityClass);
    }

    @Test
    public void getEntityName() throws Exception {
        String entityName = MetamodelUtils.getEntityName(entityManagerFactory.getMetamodel(), UnNamed.class);
        Assert.assertEquals(UnNamed.class.getSimpleName(), entityName);
        entityName = MetamodelUtils.getEntityName(entityManagerFactory.getMetamodel(), Named.class);
        Assert.assertEquals(Named.TABLE_NAME, entityName);
        entityName = MetamodelUtils.getEntityName(entityManagerFactory.getMetamodel(), MetamodelUtilsTest.class);
        Assert.assertNull(entityName);
    }

    @Test
    public void getRequiredEntityName() throws Exception {
        String entityName = MetamodelUtils.getEntityName(entityManagerFactory.getMetamodel(), UnNamed.class);
        Assert.assertEquals(UnNamed.class.getSimpleName(), entityName);
        entityName = MetamodelUtils.getEntityName(entityManagerFactory.getMetamodel(), Named.class);
        Assert.assertEquals(Named.TABLE_NAME, entityName);
        AssertUtils.assertException(() -> MetamodelUtils.getRequiredEntityName(entityManagerFactory.getMetamodel(), MetamodelUtilsTest.class));
    }

}

package com.github.peacetrue.associate;

import com.github.peacetrue.associate.support.AssociatedExpressionCollectionAssociatedSource;
import com.github.peacetrue.associate.support.AssociatedPropertyCollectionAssociatedSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * tests for {@link AssociateUtils}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan
public class AssociateUtilsTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ForAssociateObject forAssociateObject;
    @Autowired
    private ForAssociateName forAssociateName;
    private List<Associated> associateds = IntStream.range(0, 5)
            .mapToObj(value -> new Associated("name" + value))
            .collect(Collectors.toList());
    private List<Associate> mains;

    @Before
    public void initDatabase() {
        //init once
        if (associateds.get(0).getId() != null) return;
        associateds.forEach(entityManager::persist);
        mains = associateds.stream()
                .map(associated -> new Associate(associated.getId(), Collections.singletonList(associated.getId())))
                .collect(Collectors.toList());
        mains.forEach(entityManager::persist);
    }

    @Test
    @Transactional
    public void setSingleAssociateObjectForSingleMain() throws Exception {
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            AssociateUtils.setAssociate(main, "associated", forAssociateObject, "associatedId");
            Assert.assertEquals(associateds.get(i), main.getAssociated());
        }
    }

    @Test
    @Transactional
    public void setCollectionAssociateObjectForSingleMain() throws Exception {
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            AssociateUtils.setCollectionAssociate(main, "associateds", forAssociateObject, "associatedIds");
            Assert.assertEquals(Collections.singletonList(associateds.get(i)), main.getAssociateds());
        }

    }

    @Test
    @Transactional
    public void setSingleAssociateNameForSingleMain() throws Exception {
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            AssociateUtils.setAssociate(main, "associatedName", forAssociateName, "associatedId");
            Assert.assertEquals(associateds.get(i).getName(), main.getAssociatedName());
        }

    }

    @Test
    @Transactional
    public void setCollectionAssociateNameForSingleMain() throws Exception {
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            AssociateUtils.setCollectionAssociate(main, "associatedNames", forAssociateName, "associatedIds");
            Assert.assertEquals(Collections.singletonList(associateds.get(i).getName()), main.getAssociatedNames());
        }
    }


    @Test
    @Transactional
    public void setSingleAssociateObjectForCollectionMain() throws Exception {
        AssociateUtils.setAssociate(mains, "associated", forAssociateObject, "associatedId");
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            Assert.assertEquals(associateds.get(i), main.getAssociated());
        }
    }

    @Test
    @Transactional
    public void setCollectionAssociateObjectForCollectionMain() throws Exception {
        AssociateUtils.setCollectionAssociate(mains, "associateds", forAssociateObject, "associatedIds");
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            Assert.assertEquals(Collections.singletonList(associateds.get(i)), main.getAssociateds());
        }

    }

    @Test
    @Transactional
    public void setSingleAssociateNameForCollectionMain() throws Exception {
        AssociateUtils.setAssociate(mains, "associatedName", forAssociateName, "associatedId");
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            Assert.assertEquals(associateds.get(i).getName(), main.getAssociatedName());
        }
    }

    @Test
    @Transactional
    public void setCollectionAssociateNameForCollectionMain() throws Exception {
        AssociateUtils.setCollectionAssociate(mains, "associatedNames", forAssociateName, "associatedIds");
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            Assert.assertEquals(Collections.singletonList(associateds.get(i).getName()), main.getAssociatedNames());
        }
    }


    @Autowired
    private AssociatedExpressionCollectionAssociatedSource associatedExpressionCollectionAssociatedSource;

    @Test
    @Transactional
    public void setCollectionAssociateNameForCollectionMain1() throws Exception {
        AssociateUtils.setCollectionAssociate(mains, "associatedNames", associatedExpressionCollectionAssociatedSource, "associatedIds");
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            Assert.assertEquals(Collections.singletonList(associateds.get(i).getName()), main.getAssociatedNames());
        }
    }

    @Autowired
    private AssociatedPropertyCollectionAssociatedSource associatedPropertyCollectionAssociatedSource;

    @Test
    @Transactional
    public void setCollectionAssociateNameForCollectionMain2() throws Exception {
        AssociateUtils.setCollectionAssociate(mains, "associatedNames", associatedPropertyCollectionAssociatedSource, "associatedIds");
        for (int i = 0; i < mains.size(); i++) {
            Associate main = mains.get(i);
            Assert.assertEquals(Collections.singletonList(associateds.get(i).getName()), main.getAssociatedNames());
        }
    }


    @Configuration
    public static class Config {

        @Bean
        public ExpressionParser expressionParser() {
            return new SpelExpressionParser();
        }

        @Bean
        public AssociatedExpressionCollectionAssociatedSource associatedExpressionCollectionAssociatedSource() {
            return new AssociatedExpressionCollectionAssociatedSource<Long, Associated, String>("#{name}") {

                @PersistenceContext
                private EntityManager entityManager;

                @PostConstruct
                protected void init() {
                    super.init();
                }

                @Override
                public Collection<Associated> findCollectionAssociate(Collection<Long> ids) {
                    return entityManager.createQuery("from Associated where id in ?1").setParameter(1, ids).getResultList();
                }
            };
        }

        @Bean
        public AssociatedPropertyCollectionAssociatedSource associatedPropertyCollectionAssociatedSource() {
            return new AssociatedPropertyCollectionAssociatedSource<Long, Associated, String>("name") {
                @PersistenceContext
                private EntityManager entityManager;

                @Override
                public Collection<Associated> findCollectionAssociate(Collection<Long> ids) {
                    return entityManager.createQuery("from Associated where id in ?1").setParameter(1, ids).getResultList();
                }
            };
        }
    }


}
package com.github.peacetrue.associate;

import com.github.peacetrue.associate.jpa.JpaObjectCollectionAssociatedSource;
import com.github.peacetrue.associate.jpa.JpaPropertyCollectionAssociatedSource;
import com.github.peacetrue.associate.jpa.JpaTemplateCollectionAssociatedSource;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

/**
 * include by README.adoc
 *
 * @author xiayx
 */
@SuppressWarnings("unchecked")
public class ForREADME {

    private EntityManager entityManager;
    private Associate associate = null;
    private List<Associate> associates = null;

    //------------------for 使用说明
    public void setSingleAssociatedObjectForSingleAssociate() {
//tag::setSingleAssociatedObjectForSingleAssociate[]
        AssociatedSource<Long, Associated, Associated> associatedSource = new SameAssociatedSource<Long, Associated>() {
            public Associated findAssociate(Long id) {
                //根据id获取实体对象，需自行实现
                return entityManager.find(Associated.class, id);
            }
        };
        AssociateUtils.setAssociate(associate, "associated", associatedSource, "associatedId");
//end::setSingleAssociatedObjectForSingleAssociate[]
    }

    public void setCollectionAssociatedObjectForSingleAssociate() {
//tag::setCollectionAssociatedObjectForSingleAssociate[]
        CollectionAssociatedSource<Long, Associated, Associated> collectionAssociatedSource = new SameCollectionAssociatedSource<Long, Associated>() {
            public Collection<Associated> findCollectionAssociate(Collection<Long> ids) {
                //根据id集合获取实体对象集合，需自行实现
                return entityManager.createQuery("from Associated e where e.id in ?1").setParameter(1, ids).getResultList();
            }

            public Long resolveId(Associated data) {
                //获取上面查询结果中每一项的id，需自行实现
                return data.getId();
            }
        };
        AssociateUtils.setCollectionAssociate(associate, "associateds", collectionAssociatedSource, "associatedIds");
//end::setCollectionAssociatedObjectForSingleAssociate[]
    }

    public void setSingleAssociatedNameForSingleAssociate() {
//tag::setSingleAssociatedNameForSingleAssociate[]
        AssociatedSource<Long, String, String> associateSource = new SameAssociatedSource<Long, String>() {
            public String findAssociate(Long id) {
                //根据id获取name属性，需自行实现
                return (String) entityManager.createQuery("select e.name from Associated e where e.id = ?1").setParameter(1, id).getSingleResult();
            }
        };
        AssociateUtils.setAssociate(associate, "associatedName", associateSource, "associatedId");
//end::setSingleAssociatedNameForSingleAssociate[]
    }

    public void setCollectionAssociatedNameForSingleAssociate() {
//tag::setCollectionAssociatedNameForSingleAssociate[]
        CollectionAssociatedSource<Long, Object[], String> collectionAssociateSource = new CollectionAssociatedSource<Long, Object[], String>() {

            public Collection<Object[]> findCollectionAssociate(Collection<Long> ids) {
                //根据id集合获取实体对象(id和name)集合，需自行实现
                return entityManager.createQuery("select e.id,e.name from Associated e where e.id in ?1").setParameter(1, ids).getResultList();
            }

            public Long resolveId(Object[] data) {
                //获取上面查询结果中每一项的id，需自行实现
                return (Long) data[0];
            }

            public String format(Object[] data) {
                //获取上面查询结果中每一项的name，需自行实现
                return (String) data[1];
            }
        };
        AssociateUtils.setCollectionAssociate(associate, "associatedNames", collectionAssociateSource, "associatedIds");
//end::setCollectionAssociatedNameForSingleAssociate[]
    }

    public void setSingleAssociatedObjectForCollectionAssociate() {
//tag::setSingleAssociatedObjectForCollectionAssociate[]
        CollectionAssociatedSource<Long, Associated, Associated> collectionAssociatedSource = new SameCollectionAssociatedSource<Long, Associated>() {

            public Collection<Associated> findCollectionAssociate(Collection<Long> ids) {
                //根据id集合获取实体对象集合，需自行实现
                return entityManager.createQuery("from Associated e where e.id in ?1").setParameter(1, ids).getResultList();
            }

            public Long resolveId(Associated data) {
                //获取上面查询结果中每一项的id，需自行实现
                return data.getId();
            }
        };
        AssociateUtils.setAssociate(associates, "associated", collectionAssociatedSource, "associatedId");
//end::setSingleAssociatedObjectForCollectionAssociate[]
    }

    public void setCollectionAssociatedObjectForCollectionAssociate() {
//tag::setCollectionAssociatedObjectForCollectionAssociate[]
        CollectionAssociatedSource<Long, Associated, Associated> collectionAssociatedSource = new SameCollectionAssociatedSource<Long, Associated>() {

            public Collection<Associated> findCollectionAssociate(Collection<Long> ids) {
                //根据id集合获取实体对象集合，需自行实现
                return entityManager.createQuery("from Associated e where e.id in ?1").setParameter(1, ids).getResultList();
            }

            public Long resolveId(Associated data) {
                //获取上面查询结果中每一项的id，需自行实现
                return data.getId();
            }
        };
        AssociateUtils.setCollectionAssociate(associates, "associateds", collectionAssociatedSource, "associatedId");
//end::setCollectionAssociatedObjectForCollectionAssociate[]
    }

    public void setSingleAssociatedNameForCollectionAssociate() {
//tag::setSingleAssociatedNameForCollectionAssociate[]
        CollectionAssociatedSource<Long, Object[], String> associateSource = new CollectionAssociatedSource<Long, Object[], String>() {
            public Collection<Object[]> findCollectionAssociate(Collection<Long> ids) {
                //根据id集合获取实体对象(id和name)集合，需自行实现
                return entityManager.createQuery("select e.id,e.name from Associated e where e.id in ?1").setParameter(1, ids).getResultList();
            }

            public Long resolveId(Object[] data) {
                //获取上面查询结果中每一项的id，需自行实现
                return (Long) data[0];
            }

            public String format(Object[] data) {
                //获取上面查询结果中每一项的name，需自行实现
                return (String) data[1];
            }
        };
        AssociateUtils.setAssociate(associates, "associatedName", associateSource, "associatedId");
//end::setSingleAssociatedNameForCollectionAssociate[]
    }

    public void setCollectionAssociatedNameForCollectionAssociate() {
//tag::setCollectionAssociatedNameForCollectionAssociate[]
        CollectionAssociatedSource<Long, Object[], String> collectionAssociateSource = new CollectionAssociatedSource<Long, Object[], String>() {

            public Collection<Object[]> findCollectionAssociate(Collection<Long> ids) {
                //根据id集合获取实体对象(id和name)集合，需自行实现
                return entityManager.createQuery("select e.id,e.name from Associated e where e.id in ?1").setParameter(1, ids).getResultList();
            }

            public Long resolveId(Object[] data) {
                //获取上面查询结果中每一项的id，需自行实现
                return (Long) data[0];
            }

            public String format(Object[] data) {
                //获取上面查询结果中每一项的name，需自行实现
                return (String) data[1];
            }
        };
        AssociateUtils.setCollectionAssociate(associates, "associatedNames", collectionAssociateSource, "associatedIds");
//end::setCollectionAssociatedNameForCollectionAssociate[]
    }

    //------------------for 被关联数据源
    public void setAssociatedObject() {
//tag::setAssociatedObject[]
        JpaObjectCollectionAssociatedSource collectionAssociatedSource = new JpaObjectCollectionAssociatedSource(entityManager, Associated.class, "id");
        AssociateUtils.setAssociate(associate, "associated", collectionAssociatedSource, "associatedId");
        AssociateUtils.setAssociate(associate, "associateds", collectionAssociatedSource, "associatedIds");
        AssociateUtils.setAssociate(associates, "associated", collectionAssociatedSource, "associatedId");
        AssociateUtils.setAssociate(associates, "associateds", collectionAssociatedSource, "associatedIds");
//end::setAssociatedObject[]
    }

    public void setAssociatedName() {
//tag::setAssociatedName[]
        JpaPropertyCollectionAssociatedSource collectionAssociatedSource = new JpaPropertyCollectionAssociatedSource(entityManager, Associated.class, "id", "name");
        AssociateUtils.setAssociate(associate, "associatedName", collectionAssociatedSource, "associatedId");
        AssociateUtils.setAssociate(associate, "associatedNames", collectionAssociatedSource, "associatedIds");
        AssociateUtils.setAssociate(associates, "associatedName", collectionAssociatedSource, "associatedId");
        AssociateUtils.setAssociate(associates, "associatedNames", collectionAssociatedSource, "associatedIds");
//end::setAssociatedName[]
    }

    public void setAssociatedFormatName() {
//tag::setAssociatedFormatName[]
        //模板中的索引对应属性名列表中的属性，即1->code，2->name
        JpaTemplateCollectionAssociatedSource<Long> collectionAssociatedSource = new JpaTemplateCollectionAssociatedSource<>(entityManager, Associated.class, new String[]{"id", "code", "name"}, 0, "{2}({1})");
        AssociateUtils.setAssociate(associate, "associatedFormatName", collectionAssociatedSource, "associatedId");
        AssociateUtils.setAssociate(associate, "associatedFormatNames", collectionAssociatedSource, "associatedIds");
        AssociateUtils.setAssociate(associates, "associatedFormatName", collectionAssociatedSource, "associatedId");
        AssociateUtils.setAssociate(associates, "associatedFormatNames", collectionAssociatedSource, "associatedIds");
//end::setAssociatedFormatName[]
    }
}

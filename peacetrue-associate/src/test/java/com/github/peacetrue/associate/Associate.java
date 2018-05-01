package com.github.peacetrue.associate;

import javax.persistence.*;
import java.util.List;

/**
 * 关联实体类
 *
 * @author xiayx
 */
@Entity
//tag::class[]
public class Associate {
    //end::class[]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//tag::class[]
    private Long id;
//end::class[]
//tag::associatedId[]
//tag::associatedIdNote[]
    /** 被关联对象主键。作为关联属性 */
//end::associatedIdNote[]
//tag::associatedIdProperty[]
    private Long associatedId;
//end::associatedIdProperty[]
//end::associatedId[]
    @Transient
//tag::associated[]
//tag::associatedNote[]
    /** 被关联对象。作为关联扩展属性 */
//end::associatedNote[]
//tag::associatedProperty[]
    private Associated associated;
//end::associatedProperty[]
//end::associated[]
    @Transient
//tag::associatedName[]
    /** 被关联对象名称。作为关联扩展属性 */
    private String associatedName;
//end::associatedName[]
    @Transient
//tag::associatedFormatName[]
    /** 被关联对象格式化名称。作为关联扩展属性 */
    private String associatedFormatName;
//end::associatedFormatName[]

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
//tag::associatedIds[]
    /** 被关联对象主键集合。作为关联属性 */
    private List<Long> associatedIds;
//end::associatedIds[]
    @Transient
//tag::associateds[]
    /** 被关联对象集合。作为关联扩展属性 */
    private List<Associated> associateds;
//end::associateds[]
    @Transient
//tag::associatedNames[]
    /** 被关联对象名称集合。作为关联扩展属性 */
    private List<String> associatedNames;
//end::associatedNames[]
    @Transient
//tag::associatedFormatNames[]
    /** 被关联对象格式化名称集合。作为关联扩展属性 */
    private List<String> associatedFormatNames;
//end::associatedFormatNames[]


    public Associate() {
    }

    public Associate(Long associatedId) {
        this.associatedId = associatedId;
    }

    public Associate(Long associatedId, List<Long> associatedIds) {
        this.associatedId = associatedId;
        this.associatedIds = associatedIds;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(Long associatedId) {
        this.associatedId = associatedId;
    }

    public String getAssociatedName() {
        return associatedName;
    }

    public void setAssociatedName(String associatedName) {
        this.associatedName = associatedName;
    }

    public String getAssociatedFormatName() {
        return associatedFormatName;
    }

    public void setAssociatedFormatName(String associatedFormatName) {
        this.associatedFormatName = associatedFormatName;
    }

    public Associated getAssociated() {
        return associated;
    }

    public void setAssociated(Associated associated) {
        this.associated = associated;
    }

    public List<Long> getAssociatedIds() {
        return associatedIds;
    }

    public void setAssociatedIds(List<Long> associatedIds) {
        this.associatedIds = associatedIds;
    }

    public List<Associated> getAssociateds() {
        return associateds;
    }

    public void setAssociateds(List<Associated> associateds) {
        this.associateds = associateds;
    }

    public List<String> getAssociatedNames() {
        return associatedNames;
    }

    public void setAssociatedNames(List<String> associatedNames) {
        this.associatedNames = associatedNames;
    }

    public List<String> getAssociatedFormatNames() {
        return associatedFormatNames;
    }

    public void setAssociatedFormatNames(List<String> associatedFormatNames) {
        this.associatedFormatNames = associatedFormatNames;
    }
//tag::class[]
}
//end::class[]


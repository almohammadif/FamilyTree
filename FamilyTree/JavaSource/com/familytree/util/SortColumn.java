package com.familytree.util;

import java.io.Serializable;

public class SortColumn implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String jpaPropertyName;

    public static enum SortOrder
    {
        asc,
        desc,
        unsorted
    };

    private SortOrder orderDir;

    public SortColumn( String jpaPropertyName, SortOrder orderDir )
    {
        this.jpaPropertyName = jpaPropertyName;
        this.orderDir = orderDir;

    }

    public String getJpaPropertyName()
    {
        return jpaPropertyName;
    }

    public SortOrder getOrderDir()
    {
        return orderDir;
    }

}

package com.familytree.util;

import java.io.Serializable;

public class FilterColumn implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String jpaPropertyName;
    private Object filterValue;

    public FilterColumn( String jpaPropertyName, Object filterValue )
    {
        this.jpaPropertyName = jpaPropertyName;
        this.filterValue = filterValue;

    }

    public String getJpaPropertyName()
    {
        return jpaPropertyName;
    }

    public Object getFilterValue()
    {
        return filterValue;
    }

}

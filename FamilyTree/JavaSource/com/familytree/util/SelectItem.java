/**
 * Class representing a single entry in the dropdown box
 */

/*
 * ==================================================================================================
 * ======= Ver No:| Created/Updated Date | Created/Updated By | Comments
 * ============================
 * ============================================================================= 1 | 18/Nov/2010 |
 * Author | InItial Version
 * ==========================================================================
 * =================================
 */

package com.familytree.util;

/**
 * @author Ragesh
 */
public class SelectItem
{
    private Object value;
    private String name;

    /**
     * @param value
     * @param name
     */
    public SelectItem( Object value, String name )
    {
        this.value = value;
        this.name = name;
    }

    public SelectItem( long value, String name )
    {
        this.value = value;
        this.name = name;
    }

    public SelectItem( int value, String name )
    {
        this.value = value;
        this.name = name;
    }
    
    public SelectItem( Integer value, String name )
    {
        this.value = value;
        this.name = name;
    }
    /**
     * @return the value
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue( Object value )
    {
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }

}

package com.familytree.persistence;

/**
 * Entity implementation class for Entity: JPAAbstractRoot
 */
public abstract class JPAAbstractRoot
{

    public abstract Object getId();

    public abstract void setId( Object key );

    public abstract String getSequenceIdQuery();

    public abstract Object getNewID();

    public abstract String getFullActiveListNamedQ();

    public abstract String getShortActiveListNamedQ();

    public abstract String getShortListNamedQ();

    public abstract int getDBEntityID();

}

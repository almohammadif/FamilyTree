package com.familytree.ejb;

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.common.base.Strings;
import com.familytree.persistence.JPAAbstractRoot;
import com.familytree.util.FilterColumn;
import com.familytree.util.SelectItem;
import com.familytree.util.SortColumn;

/**
 * @author Othman
 */
public abstract class AbstractFacade< T extends JPAAbstractRoot >
{
    private final Class< T > entityClass;
    private static final String HIJRI_DATE_FORMAT = "yyyyMMdd";
    private static SimpleDateFormat hijriDateFormattter = new SimpleDateFormat( HIJRI_DATE_FORMAT );

    public AbstractFacade( Class< T > entityClass )
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void add( T entity )
    {
    	
        try
        {

            getEntityManager().persist( entity );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

    }

    public void edit( T entity )
    {
        getEntityManager().merge( entity );
    }

    /*
     * public void remove(T entity) { getEntityManager().remove(getEntityManager().merge(entity)); }
     */

    public void activate( T entity )
    {
        getEntityManager().merge( entity );
    }

    public void deActivate( T entity )
    {
        getEntityManager().merge( entity );
    }

    public T find( Object id )
    {
        return getEntityManager().find( entityClass, id );
    }

    public List< T > findAll()
    {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select( cq.from( entityClass ) );
        return getEntityManager().createQuery( cq ).getResultList();
    }

    public int findFilteredListCount( List< FilterColumn > filterFields )
    {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery< Long > criteriaQuery = criteriaBuilder.createQuery( Long.class );
        Root< T > root = criteriaQuery.from( entityClass );
        Expression< Boolean > filterExpression =
            createFilterCriteria( filterFields, criteriaBuilder, root );
        filterExpression=modifyExpression(filterExpression,criteriaBuilder, root);
        if ( filterExpression != null )
        {
            criteriaQuery.where( filterExpression );
        }
        Expression< Long > count = criteriaBuilder.count( root );
        criteriaQuery.select( count );

        return getEntityManager().createQuery( criteriaQuery ).getSingleResult().intValue();
    }

    public List< T > findSortedfilteredRange( int[] range, List< SortColumn > sortFields,
        List< FilterColumn > filterFields )
    {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery< T > criteriaQuery = criteriaBuilder.createQuery( entityClass );
        Root< T > root = criteriaQuery.from( entityClass );
        // System.out
        // .println(
        // "here is findSortedfilteredRange at AbstractFacade, the size of filterFields is:"
        // + filterFields.size() );
        System.out.println("Abstract Facade: range is : from " + range[0] + " fetch :" + range[1]);
        Expression< Boolean > filterExpression =
            createFilterCriteria( filterFields, criteriaBuilder, root );
        filterExpression=modifyExpression(filterExpression,criteriaBuilder, root);
        if ( filterExpression != null )
        {
            // System.out
            // .println( "filterExpression should not be null, this is " + filterExpression != null
            // );
            criteriaQuery.where( filterExpression );
        }
        
        // System.out
        // .println( "here is findSortedfilteredRange at AbstractFacade, the size of sortFields is:"
        // + sortFields.size() );
        List< Order > orderExpression = createOrdersList( sortFields, criteriaBuilder, root );
        if ( orderExpression != null )
        {
            // System.out
            // .println( "orderExpression should not be null, this is " + orderExpression != null );
            criteriaQuery.orderBy( orderExpression );
        }

        TypedQuery< T > query = getEntityManager().createQuery( criteriaQuery );

        query.setFirstResult( range[ 0 ] );
        query.setMaxResults( range[ 1 ] );
        return query.getResultList();
    }

    private List< Order > createOrdersList( List< SortColumn > sortFields,
        CriteriaBuilder criteriaBuilder, Root< T > root )
    {
        List< Order > ordersList = new ArrayList< Order >();
        System.out.println(
            "here is createOrdersList at AbstractFacade, the size of SortField is:"
                + sortFields.size() );
        if ( sortFields != null && sortFields.size() > 0 )
        {
            for ( SortColumn sc : sortFields )
            {

                String[] paths = sc.getJpaPropertyName().split( ":" );
                System.out.println( "paths size: " + paths.length );

                Path< Object > expression = root.get( paths[ 0 ] );

                if ( paths.length > 1 )
                {
                    for ( int i = 1; i < paths.length; i++ )
                    {
                        System.out.println( "inside paths loop" );
                        expression = expression.get( paths[ i ] );
                    }
                }
                Order order;

                if ( sc.getOrderDir() == SortColumn.SortOrder.asc )
                {
                    order = criteriaBuilder.asc( expression );
                }
                else if ( sc.getOrderDir() == SortColumn.SortOrder.desc )
                {
                    order = criteriaBuilder.desc( expression );
                }
                else
                {
                    throw new IllegalArgumentException( sc.toString() );
                }
                ordersList.add( order );
                System.out.println( "order toString is : " + order.toString()
                    + " Expression toString is : " + expression.toString() );
            }
        }
        // System.out
        // .println( "here is createOrdersList at AbstractFacade, the size of ordersList is:"
        // + ordersList.size() );
        return ordersList;
    }

    private Expression< Boolean > createFilterCriteria( List< FilterColumn > filterFields,
        CriteriaBuilder criteriaBuilder, Root< T > root )
    {
        Expression< Boolean > filterCriteria = null;
        // System.out.println( "Filter List Size : " + filterFields.size() );
        if ( filterFields != null && filterFields.size() > 0 )
        {
            for ( FilterColumn fc : filterFields )
            {
            	System.out.println("abstract facade "+filterFields.get(0));
                if ( Strings.isNullOrEmpty( fc.getFilterValue().toString() ) )
                {
                    continue;
                }

                String[] paths = fc.getJpaPropertyName().split( ":" );

                Path< Object > expression = root.get( paths[ 0 ] );

                if ( paths.length > 1 )
                {
                    for ( int i = 1; i < paths.length; i++ )
                    {
                        expression = expression.get( paths[ i ] );
                    }
                }

//                System.out.println( "Filter Name : Value =  " + fc.getJpaPropertyName() + " : "
//                    + fc.getFilterValue().toString()
//                    );
                //System.out.println( "Expression Value" + expression.toString() );
                /*
                 * Expression< Integer > locator = criteriaBuilder.locate( criteriaBuilder.lower(
                 * expression ), fc.getFilterValue().toString(), 1 ); Expression< Boolean >
                 * predicate = criteriaBuilder.gt( locator, 0 );
                 */
                Expression< Boolean > predicate =
                    criteriaBuilder.equal( expression, fc.getFilterValue() );
                if ( filterCriteria == null )
                {
                    //System.out.println( "we are inside filterCriteria null block VALUE = "+fc.getFilterValue() );
                    filterCriteria = predicate.as( Boolean.class );
                }
                else
                {
                    //System.out.println( "we are inside filterCriteria not null block" );
                    filterCriteria =
                        criteriaBuilder.and( filterCriteria, predicate.as( Boolean.class ) );
                }
            }
        }
        return filterCriteria;
    }

    public List< T > getFullActiveList( T entity )
    {
        TypedQuery< T > listQuery =
            getEntityManager().createNamedQuery( entity.getFullActiveListNamedQ(), entityClass );
        return listQuery.getResultList();
    }

    public List< SelectItem > getShortActiveList( T entity )
    {
        TypedQuery< SelectItem > listQuery =
            getEntityManager().createNamedQuery( entity.getShortActiveListNamedQ(),
                SelectItem.class );
        return listQuery.getResultList();
    }

    public List< SelectItem > getShortList( T entity )
    {
        TypedQuery< SelectItem > listQuery =
            getEntityManager().createNamedQuery( entity.getShortListNamedQ(), SelectItem.class );
        return listQuery.getResultList();
    }



    public BigDecimal toHijriDate( Date date )
    {
        return new BigDecimal( hijriDateFormattter.format( date ) );
    }
    
    public Expression< Boolean > modifyExpression( Expression< Boolean > FilterExpression,
            CriteriaBuilder criteriaBuilder, Root< T > root )
    {
    	return FilterExpression;
    }

}

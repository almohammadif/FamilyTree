/*
 * JBoss, Home of Professional Open Source Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a full listing of individual
 * contributors. This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This software is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details. You should have received a copy of the GNU Lesser General Public License along
 * with this software; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
 * Floor, Boston, MA 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.familytree.view;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.FilterField;
import org.richfaces.model.SortField;

import com.google.common.base.Strings;
import com.familytree.util.FilterColumn;
import com.familytree.util.SortColumn;

public abstract class AbstractRichfacesDataModel< T > extends ExtendedDataModel< T > implements
    Arrangeable
{

    private Object rowKey;
    private int rowIndex;
    private ArrangeableState arrangeableState;
    protected List< T > dataList;
    protected Hashtable< Object, T > dataMap;
    private final int rowCount = -1;

    public AbstractRichfacesDataModel()
    {
        super();
    }

    @Override
    public void arrange( FacesContext context, ArrangeableState state )
    {
        arrangeableState = state;
    }

    protected ArrangeableState getArrangeableState()
    {
        return arrangeableState;

    }

    @Override
    public void setRowKey( Object key )
    {
        rowKey = key;
    }

    @Override
    public Object getRowKey()
    {
        return rowKey;
    }

    @Override
    public T getRowData()
    {
        // System.out.println( "this is getRowData" );
        return dataMap.get( getRowKey() );
    }

    @Override
    public int getRowIndex()
    {
        // return rowIndex;
        return -1;
    }

    @Override
    public void setRowIndex( int rowIndex )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRowAvailable()
    {
        return rowKey != null;
    }

    @Override
    public int getRowCount()
    {
        // return the number total number of rows without paging
        List< FilterColumn > filterFields;
        filterFields = createFilterColumnsList();
        // System.out.println( "this is getRowcount" );

        return getFilteredCount( filterFields );

    }

    @Override
    public Object getWrappedData()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWrappedData( Object data )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void walk( FacesContext context, DataVisitor visitor, Range range, Object argument )
    {

        int[] rangeEntries =
        {
            0, 0
        };
        List< SortColumn > sortColumns = null;
        List< FilterColumn > filterColumns = null;

        // System.out.println( "This is walk" );

        SequenceRange sequenceRange = ( SequenceRange ) range;
        if ( sequenceRange.getFirstRow() >= 0 && sequenceRange.getRows() > 0 )
        {

            rangeEntries[ 0 ] = sequenceRange.getFirstRow();
            rangeEntries[ 1 ] = sequenceRange.getRows();
        }

        if ( arrangeableState != null )
        {
            sortColumns = createSortColumnsList();
            filterColumns = createFilterColumnsList();
        }

        dataList = getSortedFilteredList( rangeEntries, sortColumns, filterColumns );
        dataMap = null;
        dataMap = new Hashtable< Object, T >();
        for ( T t : dataList )
        {
            dataMap.put( getId( t ), t );
        }

        for ( T t : dataList )
        {
            visitor.process( context, getId( t ), argument );
        }

    }

    private List< SortColumn > createSortColumnsList()
    {
        List< SortColumn > sortColumns = new ArrayList< SortColumn >();
        SortColumn tempSortColumn = null;
        List< SortField > sortFields = arrangeableState.getSortFields();
        if ( sortFields != null && !sortFields.isEmpty() )
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            for ( SortField sortField : sortFields )
            {
                String propertyName =
                    ( String ) sortField.getSortBy().getValue( facesContext.getELContext() );
                SortOrder sortOrder = sortField.getSortOrder();
                if ( sortOrder == SortOrder.ascending )
                {
                    tempSortColumn = new SortColumn( propertyName, SortColumn.SortOrder.asc );
                }
                else if ( sortOrder == SortOrder.descending )
                {
                    tempSortColumn = new SortColumn( propertyName, SortColumn.SortOrder.desc );
                }
                else
                {
                    throw new IllegalArgumentException( sortOrder.toString() );
                }
                sortColumns.add( tempSortColumn );
            }
        }
        return sortColumns;
    }

    private List< FilterColumn > createFilterColumnsList()
    {
        List< FilterColumn > FilterColumns = new ArrayList< FilterColumn >();
        // System.out.println( "Arrageable state :" + arrangeableState );
        List< FilterField > filterFields = arrangeableState.getFilterFields();
        if ( filterFields != null && !filterFields.isEmpty() )
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            for ( FilterField filterField : filterFields )
            {
                String propertyName =
                    ( String ) filterField.getFilterExpression().getValue(
                        facesContext.getELContext() );
                Object filterValue = filterField.getFilterValue();
                String stringFilterValue = ( String ) filterValue;
                if ( Strings.isNullOrEmpty( stringFilterValue ) )
                {
                    continue;
                }
                //stringFilterValue = stringFilterValue.toLowerCase( arrangeableState.getLocale() );
                FilterColumns.add( new FilterColumn( propertyName, stringFilterValue ) );
            }
        }
        return FilterColumns;
    }

    protected abstract Object getId( T t );

    protected abstract List< T > getSortedFilteredList( int[] range, List< SortColumn > sortFields,
        List< FilterColumn > filterFields );

    protected abstract int getFilteredCount( List< FilterColumn > filterFields );

}

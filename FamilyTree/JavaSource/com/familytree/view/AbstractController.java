package com.familytree.view;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.model.DataModel;

import org.richfaces.component.SortOrder;

import com.google.common.collect.Maps;
import com.familytree.util.FilterColumn;
import com.familytree.util.SortColumn;

public abstract class AbstractController< T >
{

    protected Map< String, SortOrder > sortOrders = Maps.newHashMapWithExpectedSize( 1 );
    protected Map< String, String > filterValues = Maps.newHashMap();
    protected String sortProperty;
    protected String sortOrder;
    protected int totalCount; 
    
    protected DataModel items = null;
    protected T current = null;
    protected int rowCount=100;
    protected boolean filterField=false;

    public T getCurrent()
    {
        return current;
    }

    public void setCurrent( T current )
    {
        this.current = current;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder()
    {
        SortOrder sortOrder = sortOrders.get( sortProperty );
        if ( sortOrder == SortOrder.ascending )
        {
            return "asc";
        }
        else if ( sortOrder == SortOrder.descending )
        {
            return "desc";
        }
        return "unsort";
    }

    /**
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setSortOrder( String sortOrder )
    {
        this.sortOrder = sortOrder;
    }

    public Map< String, SortOrder > getSortOrders()
    {
        return sortOrders;
    }

    public Map< String, String > getFilterValues()
    {
        return filterValues;
    }

    public String getSortProperty()
    {
    	//System.out.println("get sortOrder value : "+sortProperty);
    	return sortProperty;
    }

    public void setSortProperty( String sortPropety )
    {
    	//System.out.println("set sortOrder value : "+sortProperty);
        this.sortProperty = sortPropety;
    }
    
    public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}    

    public void toggleSort()
    {
        for ( Entry< String, SortOrder > entry : sortOrders.entrySet() )
        {
            SortOrder newOrder;

            if ( entry.getKey().equals( sortProperty ) )
            {
                if ( entry.getValue() == SortOrder.ascending )
                {
                    newOrder = SortOrder.descending;
                }
                else
                {
                    newOrder = SortOrder.ascending;
                }
            }
            else
            {
                newOrder = SortOrder.unsorted;
            }

            entry.setValue( newOrder );
        }
    }

    protected abstract List< T > getList( int[] range, List< SortColumn > sortFields,
        List< FilterColumn > filterFields );

    protected abstract int getAllRowsCount( List< FilterColumn > filterFields );

    public abstract Object getItems();

    public abstract void setItems( DataModel items );

    public abstract String prepareAdd();

    public abstract String prepareEditDetails( T editRec );

    public abstract String update();

    public abstract String prepareViewDetails( T viewRec );

    public abstract String prepareList();

    public abstract String addNew();

    public abstract String activate();

    public abstract String deactivate();

    public abstract String prepareDeActivate();

    public abstract String prepareActivate();
    
    public String cancelBackToList(){
    	return "List";
    }
    
    public String cancelBackToView(){
    	return "View";
    }
    
    public String previousPage(){
    	return "";
    }
    
    public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public String concatString(String first, String second) {
		return first+second;
	}

	public boolean isFilterField() {
		return filterField;
	}

	public void showHideFilterField(boolean filterField) {
		this.filterField = filterField;
	}
	

}

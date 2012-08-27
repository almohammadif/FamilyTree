package com.familytree.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.component.SortOrder;


import com.familytree.ejb.FamilytreeSessionBean;
import com.familytree.persistence.Familytree;
import com.familytree.util.FilterColumn;
import com.familytree.util.SortColumn;
import com.sun.org.apache.regexp.internal.recompile;


@SessionScoped
@Named(value="familyTreeController")
public class FamilyTreeController extends AbstractController<Familytree> implements Serializable {
	
	private static final long serialVersionUID = -684149178488028089L;
	
	private static final class FamilyTreeDataModel extends AbstractRichfacesDataModel<Familytree>{

		
		private FamilyTreeController FamilyTreeBean;
		
		FamilyTreeDataModel(FamilyTreeController familyTreeController){
			this.FamilyTreeBean = familyTreeController;
		}
		
		@Override
		protected Object getId(Familytree t) {

			return t.getFtId();
		}

		@Override
		protected List<Familytree> getSortedFilteredList(int[] range,
				List<SortColumn> sortFields, List<FilterColumn> filterFields) {
			// TODO Auto-generated method stub
			return FamilyTreeBean.getList(range, sortFields, filterFields);
		}

		@Override
		protected int getFilteredCount(List<FilterColumn> filterFields) {
			// TODO Auto-generated method stub
			return FamilyTreeBean.getAllRowsCount(filterFields);
		}
		

	}

	
	@Inject
	private FamilytreeSessionBean FamilytreeFacade;
	private List<Familytree> familyList;
	private int familyListSize;
	private DataModel person = null;
	
	public FamilyTreeController(){

		
        sortOrders.put( "ftId", SortOrder.unsorted );
        sortOrders.put( "ftName", SortOrder.unsorted );
        sortOrders.put( "ftFatherId", SortOrder.unsorted );
        sortOrders.put( "ftMatherId", SortOrder.unsorted );
        sortOrders.put( "ftFullname", SortOrder.unsorted );
	
	}
		
	@Override
	protected List<Familytree> getList(int[] range,
			List<SortColumn> sortFields, List<FilterColumn> filterFields) {
		// TODO Auto-generated method stub
		return FamilytreeFacade.findSortedfilteredRange(range, sortFields, filterFields);
	}

	@Override
	protected int getAllRowsCount(List<FilterColumn> filterFields) {
		// TODO Auto-generated method stub
		return FamilytreeFacade.findFilteredListCount(filterFields);
	}
	

	

	@Override
	public Object getItems() {
		items = new FamilyTreeDataModel(this);
		return items;
	}

	@Override
	public void setItems(DataModel items) {
		this.items = items;
		
	}

	@Override
	public String prepareAdd() {

		current = new Familytree();
		return "Add";
	}
	
	public String prepareCahrt(Familytree chartTree) {
		current = chartTree;
		return "cahrt";
	}

	@Override
	public String prepareEditDetails(Familytree editRec) {
		current = editRec;
		return "Edit";
	}

	@Override
	public String update() {
		FamilytreeFacade.edit(current);
		return prepareList();
	}

	@Override
	public String prepareViewDetails(Familytree viewRec) {
        current = viewRec;
        return "View";
	}

	@Override
	public String prepareList() {
        items = null;
        return "List";
	}

	@Override
	public String addNew() {
//		current.setFtId(new Long(8).longValue());
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>: "+current.getFtId());
		FamilytreeFacade.add( current );
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>: "+current.getFtId());
        return prepareList();		
	}

	@Override
	public String activate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deactivate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String prepareDeActivate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String prepareActivate() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Familytree> getFamilyList(){
		return FamilytreeFacade.findAll();	
	}
	
	public void setFamilyList(List<Familytree> familyList) {
		
		this.familyList = familyList;
	}
	
	public int getFamilyListSize(){
		return getFamilyList().size();
	}

	public void setFamilyListSize(int familyListSize) {
		this.familyListSize = familyListSize;
	}

	public DataModel getPerson() {
		return person;
	}

	public void setPerson(DataModel person) {
		this.person = person;
	}
	
	
	

}

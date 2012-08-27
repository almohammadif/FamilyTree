/**
 * Class description
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

package com.familytree.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.familytree.ejb.FamilytreeSessionBean;
import com.familytree.persistence.Familytree.GenderEnum;
import com.familytree.util.SelectItem;





@Named( value = "dropdownDataLoader" )
@ApplicationScoped
public class DropdownDataLoader
{
	@Inject 
	private FamilytreeSessionBean familytreeFacade;

	public List< SelectItem > getFathersList() {
		return familytreeFacade.findAllFathersDropdown();
	}
	
	public List< SelectItem > getMothersList() {
		return familytreeFacade.findAllMothersDropdown();
	}
	

    public List< SelectItem > getGenderList()
    {
        List< SelectItem > genderList = new ArrayList< SelectItem >();
        for ( GenderEnum gender : GenderEnum.values() )
        {
        	genderList.add( new SelectItem( gender.getValue(), gender.getName() ) );
        }
        return genderList;
    }
    
	 
}

package com.familytree.ejb;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.familytree.persistence.Familytree;
import com.familytree.util.SelectItem;

/**
 * Session Bean implementation class FamilytreeSessionBean
 */
@Stateless
public class FamilytreeSessionBean extends AbstractFacade <Familytree> {

	@PersistenceContext( unitName = "FamilyTree" )

    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }
    
    public FamilytreeSessionBean() {
        super(Familytree.class);
    }
    
    @Override
    public void add( Familytree familytree )
    {
    	familytree.setFtFullname( familytree.getFtName());
        super.add( familytree );
    }    
    public List< SelectItem > findAllFathersDropdown()
    {
        TypedQuery< SelectItem > query =
            em.createQuery( Familytree.findAllFathersQuery(), SelectItem.class );
        return query.getResultList();
    }
    public List< SelectItem > findAllMothersDropdown()
    {
        TypedQuery< SelectItem > query =
            em.createQuery( Familytree.findAllMothersQuery(), SelectItem.class );
        return query.getResultList();
    }
    
 
    

}

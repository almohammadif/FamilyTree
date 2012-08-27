package com.familytree.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the familytree database table.
 * 
 */
@Entity
@Table( name = "familytree" )
public class Familytree extends JPAAbstractRoot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FT_ID")
	private long ftId;

	@Column(name="FT_FATHER_ID")
	private long ftFatherId;

	@Column(name="FT_FULLNAME")
	private String ftFullname;

	@Column(name="FT_GENDER")
	private long ftGender;

	@Column(name="FT_GENDER",insertable= false,updatable = false)
	private String gender;
	
	@Column(name="FT_MATHER_ID")
	private long ftMatherId;

	@Column(name="FT_NAME")
	private String ftName;

	@Column(name="FT_STATUS")
	private long ftStatus;

    public Familytree() {
    }

	public long getFtId() {
		return this.ftId;
	}

	public void setFtId(long ftId) {
		this.ftId = ftId;
	}

	public long getFtFatherId() {
		return this.ftFatherId;
	}

	public void setFtFatherId(long ftFatherId) {
		this.ftFatherId = ftFatherId;
	}

	public String getFtFullname() {
		return this.ftFullname;
	}

	public void setFtFullname(String ftFullname) {
		this.ftFullname = ftFullname;
	}

	public long getFtGender() {
		return this.ftGender;
	}

	public void setFtGender(long ftGender) {
		this.ftGender = ftGender;
	}

	public long getFtMatherId() {
		return this.ftMatherId;
	}

	public void setFtMatherId(long ftMatherId) {
		this.ftMatherId = ftMatherId;
	}

	public String getFtName() {
		return this.ftName;
	}

	public void setFtName(String ftName) {
		this.ftName = ftName;
	}

	public long getFtStatus() {
		return this.ftStatus;
	}

	public void setFtStatus(long ftStatus) {
		this.ftStatus = ftStatus;
	}

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return getFtId();
	}

	@Override
	public void setId(Object key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSequenceIdQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getNewID() {
		// TODO Auto-generated method stub
		return this.getFtId();
	}

	@Override
	public String getFullActiveListNamedQ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShortActiveListNamedQ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShortListNamedQ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDBEntityID() {
		// TODO Auto-generated method stub
		return 0;
	}

    public static String findAllFathersQuery()
    {
        return "SELECT NEW com.familyTree.util.SelectItem(c.ftId, c.ftFullname) FROM Familytree AS c where c.ftGender=1";
    }
    public static String findAllMothersQuery()
    {
        return "SELECT NEW com.familyTree.util.SelectItem(c.ftId, c.ftFullname) FROM Familytree AS c where c.ftGender=2";
    }
    


	public void setGender(String gender) {
		this.gender = gender;
	}

	public enum GenderEnum
    {
    	Male("Male",1),
    	Female("Female",2);


        private final String name;
        private final long value;

        GenderEnum( String name, long value )
        {
            this.name = name;
            this.value = value;
        }

        public String getName()
        {
            return this.name;
        }

        public long getValue()
        {
            return this.value;
        }
    }
    
    public enum Gender
    {
    	
    	Male("Male"),
    	Female("Female");

        private final String name;
        Gender( String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }

    }    

    public String getGender() {
		
    	System.out.println(">>>>>>>>>>>>>>>>>>>> Gender");
    	if (this.ftGender == 1){
    		return Gender.Male.getName();
    	}else{
    		return Gender.Female.getName();
    	}		
		
	}    

}
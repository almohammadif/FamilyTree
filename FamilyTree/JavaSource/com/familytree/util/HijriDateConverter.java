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

package com.familytree.util;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Ragesh
 */
@FacesConverter( value = "hijriDateConverter" )
public class HijriDateConverter implements Converter
{

    @Override
    public Object getAsObject( FacesContext arg0, UIComponent uiComponent, String value )
    {
        String[] dateDetails = value.split( "/" );
        StringBuilder tempValue = new StringBuilder( 10 );
        tempValue.append( dateDetails[ 2 ] )
            .append( dateDetails[ 1 ] )
            .append( dateDetails[ 0 ] );
        return new BigDecimal( tempValue.toString() );
    }

    @Override
    public String getAsString( FacesContext arg0, UIComponent arg1, Object value )
    {
        String tempVal = null;
        if ( value instanceof BigDecimal )
        {
            tempVal = ( ( BigDecimal ) value ).toString();
        }
        else if ( value instanceof String )
        {
            tempVal = ( String ) value;
        }

        return getDateString( tempVal );

    }

    private String getDateString( String value )
    {
        if ( value != null )
        {
            StringBuilder dateString = new StringBuilder( 12 );
            dateString.append( value.substring( 6, 8 ) )
                .append( "/" ).append( value.substring( 4, 6 ) ).append( "/" ).append(
                    value.substring( 0, 4 ) );
            return dateString.toString();
        }
        return "";
    }
}

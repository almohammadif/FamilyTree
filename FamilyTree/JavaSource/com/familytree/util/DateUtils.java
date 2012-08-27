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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ragesh
 */
public class DateUtils
{
    private static final String HIJRI_DATE_FORMAT = "yyyyMMdd";
    private static SimpleDateFormat hijriDateFormattter = new SimpleDateFormat( HIJRI_DATE_FORMAT );

    public static BigDecimal convertToHijriDate( Date date )
    {
        return new BigDecimal( hijriDateFormattter.format( date ) );
    }

    public static String getHijriDateStringFormat( Date date )
    {
        return hijriDateFormattter.format( date );
    }

}

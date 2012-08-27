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

/**
 * @author Ragesh
 */
public class NumberUtils
{

    public static BigDecimal flipBit( BigDecimal value, int bitPos )
    {
        return new BigDecimal( value.toBigInteger().flipBit( bitPos ) );
    }

    public static BigDecimal setBit( BigDecimal value, int bitPos )
    {
        return new BigDecimal( value.toBigInteger().setBit( bitPos ) );
    }
}

package com.intuit.cg.backendtechassessment.validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * validator for Bid
 * 
 * @author jayanthi
 *
 */
public final class BidValidator {

    /**
     * return true if this is a valid bid amount
     * 
     * @return
     */
    public static boolean validBidAmount(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.valueOf(-1)) <= 0)
            return false;
        else
            return true;
    }

    /**
     * return true if bid amount does not exceed the maximum
     * 
     * @return
     */
    public static boolean bidAmountNotExceeded(BigDecimal maxAmount,
            BigDecimal amount) {
        if (maxAmount.compareTo(amount) < 0)
            return false;
        return true;
    }

    /**
     * return true if bid deadline has passed
     * 
     * @param bid
     * @param bidDeadline
     * @return
     */
    public static boolean projectDeadlinePassed(LocalDateTime bidDeadline) {
        if (LocalDateTime.now().isAfter(bidDeadline))
            return true;
        return false;
    }

}

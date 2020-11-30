package com.urna.urnacare.enumeration;

public enum OrderStatus {
    CREATED,
    PAID,
    PROCESSED, // at this stage carrier is assigned and waiting pickup
    DISPATCHED, // at this stage carrier has picked item
    DELIVERED,
    RETURNED,
    RETURN_RECEIVED,
    LOST_IN_TRANSIT
}

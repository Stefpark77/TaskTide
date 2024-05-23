package com.tasktide.calendarServices.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecurringTime {
    ONCE(),
    DAILY(),
    WEEKLY(),
    BIWEEKLY(),
    MONTHLY(),
    ANNUAL(),
}

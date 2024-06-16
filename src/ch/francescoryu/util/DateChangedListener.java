package ch.francescoryu.util;

import java.time.LocalDateTime;

public interface DateChangedListener
{
    void dateChanged(LocalDateTime eventDate);
}

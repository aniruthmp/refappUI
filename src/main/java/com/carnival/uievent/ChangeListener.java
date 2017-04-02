package com.carnival.uievent;

import com.carnival.db.entity.Todo;

public interface ChangeListener {
    void todoChanged(Todo todo);
}

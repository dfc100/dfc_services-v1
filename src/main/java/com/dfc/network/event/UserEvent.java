package com.dfc.network.event;

import com.dfc.network.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserEvent extends ApplicationEvent {

    private User user;

    public UserEvent(
           Object source,  User user) {
        super(source);

        this.user = user;
    }

}

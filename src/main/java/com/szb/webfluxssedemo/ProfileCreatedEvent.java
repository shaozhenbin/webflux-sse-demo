package com.szb.webfluxssedemo;

public class ProfileCreatedEvent extends GenericEvent<Profile> {

    public ProfileCreatedEvent(Profile what) {
        super(what);
    }
}

package com.carnival.endpoint;

public interface Processor {

    public <T, E> E process(T t);

}

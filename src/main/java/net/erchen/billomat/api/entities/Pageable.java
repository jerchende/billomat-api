package net.erchen.billomat.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

abstract class Pageable<W, T> {

    @JsonProperty("@page")
    private int page;

    @JsonProperty("@per_page")
    private int perPage;

    @JsonProperty("@total")
    private int total;

    abstract List<T> extractItems();

}

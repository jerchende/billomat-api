package net.erchen.billomat.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract class Pageable {

    @JsonProperty("@page")
    private int page;

    @JsonProperty("@per_page")
    private int perPage;

    @JsonProperty("@total")
    private int total;

}

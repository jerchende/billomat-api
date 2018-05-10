package net.erchen.billomat.api;

import org.springframework.web.util.UriComponentsBuilder;

abstract class ApiBuilder<T> {

    protected BillomatService billomatService;

    public ApiBuilder(BillomatService billomatService) {
        this.billomatService = billomatService;
    }

    protected void addGlobalQueryParams(UriComponentsBuilder uriBuilder) {
        uriBuilder.queryParam("per_page", 999999);
        uriBuilder.queryParam("page", 1);
    }

    abstract T get();
}

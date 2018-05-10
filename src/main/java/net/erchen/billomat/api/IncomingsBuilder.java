package net.erchen.billomat.api;

import net.erchen.billomat.api.entities.Incoming;
import net.erchen.billomat.api.entities.Incomings;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.joining;

@SuppressWarnings("WeakerAccess")
public class IncomingsBuilder extends ApiBuilder<List<Incoming>> {

    private Integer supplierId;
    private String incomingNumber;
    private String status;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String note;
    private List<String> tags;

    IncomingsBuilder(BillomatService billomatService) {
        super(billomatService);
    }

    public IncomingsBuilder withSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public IncomingsBuilder withIncomingNumber(String incomingNumber) {
        this.incomingNumber = incomingNumber;
        return this;
    }

    public IncomingsBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public IncomingsBuilder withFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public IncomingsBuilder withToDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public IncomingsBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    public IncomingsBuilder withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    protected void addQueryParams(UriComponentsBuilder uriBuilder) {
        if (this.supplierId != null) {
            uriBuilder.queryParam("supplier_id", this.supplierId);
        }

        if (this.incomingNumber != null) {
            uriBuilder.queryParam("incoming_number", this.incomingNumber);
        }

        if (this.status != null) {
            uriBuilder.queryParam("status", this.status);
        }

        if (this.fromDate != null) {
            uriBuilder.queryParam("from", this.fromDate);
        }

        if (this.toDate != null) {
            uriBuilder.queryParam("to", this.toDate);
        }

        if (this.note != null) {
            uriBuilder.queryParam("note", this.note);
        }

        if (this.tags != null) {
            uriBuilder.queryParam("tags", this.tags.stream().collect(joining(",")));
        }
    }

    @Override
    public List<Incoming> get() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(billomatService.getBaseUrl() + "/incomings");
        addQueryParams(uriBuilder);
        addGlobalQueryParams(uriBuilder);
        return billomatService.getRestTemplate().getForObject(uriBuilder.toUriString(), Incomings.class).getIncomings();
    }


}

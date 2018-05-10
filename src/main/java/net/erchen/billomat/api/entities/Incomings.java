package net.erchen.billomat.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("incomings")
public class Incomings extends Pageable<Incomings, Incoming> {

    @JsonProperty("incoming")
    private List<Incoming> incomings;

    @Override
    List<Incoming> extractItems() {
        return getIncomings();
    }
}



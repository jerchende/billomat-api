package net.erchen.billomat.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Incoming {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("supplier_id")
    private Integer supplierId;

    @JsonProperty("created")
    private ZonedDateTime created;

    @JsonProperty("number")
    private String number;

    @JsonProperty("status")
    private String status;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("address")
    private String address;

    @JsonProperty("note")
    private String note;

    @JsonProperty("total_gross")
    private BigDecimal totalGross;

    @JsonProperty("total_net")
    private BigDecimal totalNet;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("open_amount")
    private BigDecimal openAmount;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("quote")
    private BigDecimal quote;

    @JsonProperty("expense_account_number")
    private Integer expenseAccountNumber;

    @JsonProperty("label")
    private String label;

}

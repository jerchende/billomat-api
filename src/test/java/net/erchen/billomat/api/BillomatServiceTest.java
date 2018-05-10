package net.erchen.billomat.api;

import net.erchen.billomat.api.entities.Incoming;
import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
public class BillomatServiceTest {

    @Value("classpath:billomatReplies/incomings.json")
    Resource mockedIncomings;
    private BillomatService billomatService;
    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void setUp() {
        billomatService = new BillomatService("billoId", "secretKey");
        mockRestServiceServer = MockRestServiceServer.bindTo(billomatService.getRestTemplate()).build();

    }

    @Test
    public void shouldGetIncomings() {
        mockRestServiceServer.expect(once(), requestTo("https://billoId.billomat.net/api/incomings?per_page=999999&page=1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(mockedIncomings, APPLICATION_JSON));

        List<Incoming> incomings = billomatService.getIncomings().get();

        assertThat(incomings).hasSize(5);
        assertThat(incomings.get(0).getId()).isEqualTo(1);
        assertThat(incomings.get(0).getCreated().withZoneSameLocal(UTC)).isEqualTo(ZonedDateTime.parse("2016-12-31T22:01:10+02:00").withZoneSameLocal(UTC));
        assertThat(incomings.get(0).getSupplierId()).isEqualTo(5);
        assertThat(incomings.get(0).getNumber()).isEqualTo("RE-1");
        assertThat(incomings.get(0).getDate()).isEqualTo(LocalDate.of(2016, 12, 31));
        assertThat(incomings.get(0).getDueDate()).isEqualTo(LocalDate.of(2017, 1, 1));
        assertThat(incomings.get(0).getAddress()).isEqualTo("Supplier Company, Street 1, 12345 City");
        assertThat(incomings.get(0).getStatus()).isEqualTo("PAID");
        assertThat(incomings.get(0).getNote()).isEqualTo("something helpfull");
        assertThat(incomings.get(0).getTotalGross()).isCloseTo(BigDecimal.valueOf(56.38), Offset.offset(BigDecimal.valueOf(100)));
        assertThat(incomings.get(0).getTotalNet()).isCloseTo(BigDecimal.valueOf(47.38), Offset.offset(BigDecimal.valueOf(100)));
        assertThat(incomings.get(0).getCurrencyCode()).isEqualTo("EUR");
        assertThat(incomings.get(0).getQuote()).isCloseTo(BigDecimal.valueOf(1.0000), Offset.offset(BigDecimal.valueOf(100)));
        assertThat(incomings.get(0).getPaidAmount()).isCloseTo(BigDecimal.valueOf(56.38), Offset.offset(BigDecimal.valueOf(100)));
        assertThat(incomings.get(0).getOpenAmount()).isCloseTo(BigDecimal.valueOf(0.00), Offset.offset(BigDecimal.valueOf(100)));
        assertThat(incomings.get(0).getExpenseAccountNumber()).isEqualTo(1234);
        assertThat(incomings.get(0).getLabel()).isEqualTo("label 1234");
    }

    @Test
    public void shouldBuildIncomingsUrlWithFilter() {
        mockRestServiceServer.expect(once(), requestTo("https://billoId.billomat.net/api/incomings?supplier_id=1234&incoming_number=RE1&status=OPEN&from=2010-05-19&to=2016-12-24&note=note&tags=tag1,tag2&per_page=999999&page=1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(mockedIncomings, APPLICATION_JSON));

        List<Incoming> incomings = billomatService.getIncomings()
                .withSupplierId(1234)
                .withIncomingNumber("RE1")
                .withStatus("OPEN")
                .withFromDate(LocalDate.of(2010, 5, 19))
                .withToDate(LocalDate.of(2016, 12, 24))
                .withNote("note")
                .withTags(Arrays.asList("tag1", "tag2"))
                .get();

        assertThat(incomings).hasSize(5);
    }

}

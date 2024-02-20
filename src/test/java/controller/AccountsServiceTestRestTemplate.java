package controller;
    import com.microcompany.accountsservice.AccountsServiceApplication;
    import com.microcompany.accountsservice.model.Account;
    import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.net.URISyntaxException;
    import java.util.Date;

    import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = AccountsServiceApplication.class)
@ActiveProfiles("dev")
public class AccountsServiceTestRestTemplate {

    // @Value(value = "${local.server.port}")
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void givenUrl_whenDepositMoney_thenAStringExists() throws Exception {
        int amount=100;
        String URIPut ="/accounts/deposit?accountId=1&amount=" + amount + "&ownerId=1";
         Account account =new Account(1L, "algo",new Date(),0,1L,null);
         RequestEntity<Account> request = RequestEntity.put(URIPut).contentType(MediaType.APPLICATION_JSON).body(null);
   // restTemplate.put(URIPut,account);
       ResponseEntity<Account> response = restTemplate.exchange(
               request, Account.class
       );
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBalance()).isGreaterThanOrEqualTo(amount);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }
}

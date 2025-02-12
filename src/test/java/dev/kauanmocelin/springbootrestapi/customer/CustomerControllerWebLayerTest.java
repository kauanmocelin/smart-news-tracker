package dev.kauanmocelin.springbootrestapi.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.kauanmocelin.springbootrestapi.customer.mapper.CustomerMapper;
import dev.kauanmocelin.springbootrestapi.customer.mapper.CustomerMapperImpl;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.security.JwtAuthenticationFilter;
import dev.kauanmocelin.springbootrestapi.security.JwtService;
import dev.kauanmocelin.springbootrestapi.util.CustomerPostRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(value = CustomerController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
@Import(CustomerMapperImpl.class)
class CustomerControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerMapper customerMapper;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Customer can be create")
    void shouldReturnNewCustomerWhenValidInputProvided() throws Exception {
        Customer customerMock = customerMapper.toCustomer(CustomerPostRequestBodyCreator.createPostRequestBodyCreator());
        customerMock.setId(1L);
        when(customerService.save(any(CustomerPostRequestBody.class))).thenReturn(customerMock);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CustomerPostRequestBodyCreator.createPostRequestBodyCreator()));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Customer createdCustomer = objectMapper.readValue(responseBodyAsString, Customer.class);

        assertThat(CustomerPostRequestBodyCreator.createPostRequestBodyCreator().email())
            .as("The returned e-mail is most likely incorrect")
            .isEqualTo(createdCustomer.getEmail());
        assertThat(createdCustomer.getId())
            .as("CustomerId should not be empty")
            .isNotNull()
            .isPositive();
    }

    @Test
    @DisplayName("Name is not empty")
    void shouldReturnExceptionBadRequestWhenNameIsNotProvided() throws Exception {
        Customer customerMock = customerMapper.toCustomer(CustomerPostRequestBodyCreator.createPostRequestBodyWithoutNameCreator());
        customerMock.setId(1L);
        when(customerService.save(any(CustomerPostRequestBody.class))).thenReturn(customerMock);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CustomerPostRequestBodyCreator.createPostRequestBodyWithoutNameCreator()));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(mvcResult.getResponse().getStatus())
            .as("Incorrect HTTP status code returned")
            .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Name cannont be shorter than 3 characters")
    void shouldReturnExceptionBadRequestWhenNameIsOnlyTwoCharacters() throws Exception {
        Customer customerMock = customerMapper.toCustomer(CustomerPostRequestBodyCreator.createPostRequestBodyWithInvalidNameCreator());
        customerMock.setId(1L);
        when(customerService.save(any(CustomerPostRequestBody.class))).thenReturn(customerMock);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CustomerPostRequestBodyCreator.createPostRequestBodyWithInvalidNameCreator()));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(mvcResult.getResponse().getStatus())
            .as("Incorrect HTTP status code returned")
            .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
package com.kars.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kars.test.dto.LoanDto;
import com.kars.test.dto.PersonDto;
import com.kars.test.model.Loan;
import com.kars.test.model.Person;
import com.kars.test.repositories.LoanRepository;
import com.kars.test.repositories.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Transactional
    void happyFlow() throws Exception {
        Loan loan = createLoan();
        Person john = createPerson("John", loan);
        personRepository.save(john);

        Person olga = createPerson("Olga", loan);
        personRepository.save(olga);

        entityManager.flush();
        entityManager.clear();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons/{id}", john.getId());
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        PersonDto resultPerson = objectMapper.readValue(json, PersonDto.class);

        assertThat(resultPerson.getLoans().size(), is(1));
        LoanDto resultLoan = resultPerson.getLoans().stream().findFirst().orElse(null);
        assertThat(resultLoan.getAscription(), is("John and/or Olga"));
    }

    private Person createPerson(String name, Loan loan) {
        return Person.builder()
                .name(name)
                .loan(loan)
                .build();
    }

    private Loan createLoan() {
        return Loan.builder().build();
    }
}

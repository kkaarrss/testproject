package com.kars.test.mapper;

import java.util.Comparator;
import java.util.StringJoiner;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.kars.test.dto.LoanDto;
import com.kars.test.dto.PersonDto;
import com.kars.test.model.Loan;
import com.kars.test.model.Person;

@Mapper(componentModel = "spring")
public interface PersonDtoMapper {
    PersonDto map(Person person);

    @Mapping(target = "ascription", source = "loan", qualifiedByName = "mapAscription")
    LoanDto mapLoan(Loan loan);

    @Named("mapAscription")
    default String mapAscription(Loan loan) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        loan.getPersons().stream()
                .sorted(Comparator.comparing(Person::getId))
                .forEach(applicant -> {
                    if (stringJoiner.length() != 0) {
                        stringJoiner.add("and/or");
                    }
                    stringJoiner.add(applicant.getName());
                });

        return stringJoiner.toString();
    }
}

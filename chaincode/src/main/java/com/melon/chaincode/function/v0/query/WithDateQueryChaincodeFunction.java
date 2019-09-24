package com.melon.chaincode.function.v0.query;

import com.melon.chaincode.model.query.Query;
import com.melon.chaincode.model.query.Query.QueryBuilder;
import com.melon.chaincode.model.query.PeriodCriteria;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
public class WithDateQueryChaincodeFunction extends DecoratingQueryChaincodeFunction {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public WithDateQueryChaincodeFunction(AbstractQueryChaincodeFunction chaincodeFunction) {
        super(chaincodeFunction);
    }

    @Override
    protected void validateParameters(List<String> parameters){
        String dateFormat = getDateFormatFrom(parameters);
        validateDateFormat(dateFormat);

        super.validateParameters(parameters.subList(0, getDateFormatIndex(parameters)));
    }

    private void validateDateFormat(String dateFormat){
        try{
            getMidNightOfDay(dateFormat);
        }catch(DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format : " + dateFormat);
        }
    }

    @Override
    public Query makeQuery(List<String> parameters){
        Query existingQuery = super.makeQuery(parameters);

        String dateFormat = getDateFormatFrom(parameters);
        log.error("zzzzzzzzzzzzzzzzzzz ::::::::  {}", dateFormat);
       LocalDateTime midNightOfDay = getMidNightOfDay(dateFormat);
        LocalDateTime midNightOfNextDay = getMidNightOfNextDay(midNightOfDay);

        return QueryBuilder
                .from(existingQuery)
                .selector("listenDateTime", new PeriodCriteria(midNightOfDay, midNightOfNextDay))
                .buildQuery();
    }

    private String getDateFormatFrom(List<String> parameters){
        return parameters.get(getDateFormatIndex(parameters));
    }

    /**
     *  Because of fabric chaincode api spec that receives parameters as String List,
     *  it is required to explicitly assign parameter's order(or index location)
     */
    private int getDateFormatIndex(List<String> parameters){
        return parameters.size() -1;
    }

    private LocalDateTime getMidNightOfDay(String dateFormat){
        return LocalDate.parse(dateFormat, dateFormatter).atStartOfDay();
    }

    private LocalDateTime getMidNightOfNextDay(LocalDateTime midNightOfDay){
        return midNightOfDay.plusDays(1);
    }

}

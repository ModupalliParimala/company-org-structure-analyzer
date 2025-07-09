package com.bigcompany.organalyzer.parser;

import com.bigcompany.organalyzer.model.Employee;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileParserTest {

    @Test
    void testParseValidCsv() throws Exception {
        String csv = """
                Id,firstName,lastName,salary,managerId
                1,John,Doe,50000,
                2,Jane,Smith,40000,1
                """;

        CsvFileParser parser = new CsvFileParser();
        List<Employee> employees = parser.parse(new StringReader(csv));

        assertEquals(2, employees.size());

        Employee ceo = employees.get(0);
        assertEquals("John", ceo.firstName());
        assertTrue(ceo.isCEO());
    }
}

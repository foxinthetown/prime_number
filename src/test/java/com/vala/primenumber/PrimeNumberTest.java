package com.vala.primenumber;

import com.vala.primenumber.helpers.PrimeFactorsDB;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.vala.primenumber.PrimeFactorSearch.isPrime;
import static com.vala.primenumber.helpers.FileHelper.getFilePath;
import static java.lang.Long.parseLong;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.testng.Assert.assertEquals;

public class PrimeNumberTest {

    private static final String RESULTS_FILE = "/results.txt";

    private PrimeFactorSearch primeFactorSearch;

    @BeforeClass
    public void initSearch() {
        String db = getFilePath(RESULTS_FILE);
        PrimeFactorsDB primeFactorsDB = new PrimeFactorsDB(db);
        Map<Long, Set<Long>> results = primeFactorsDB.getResults();
        primeFactorSearch = new PrimeFactorSearch(results);
    }
    @DataProvider(name = "prime numbers")
    public Object[][] dataForIsPrime() {
        return new Object[][]{
                {"13", true},
                {"0", false},
                {"1", false},
                {"97", true},
                {"96", false},
                {"10709", true}
        };
    }

    @Test(dataProvider = "prime numbers")
    public void testIsPrimeMethod(String input, boolean isPrime) {
        assertEquals(isPrime(parseLong(input)), isPrime);
    }

    @DataProvider(name = "prime factors")
    public Object[][] dataForFactors() {
        return new Object[][]{
                {new Result(0L, new HashSet<>())},
                {new Result(1L, new HashSet<>())},
                {new Result(-1L, new HashSet<>())},
                {new Result(-4L, new HashSet<>(singletonList(2L)))},
                {new Result(4L, new HashSet<>(singletonList(2L)))},
                {new Result(2L, new HashSet<>())},
                {new Result(15L, new HashSet<>(asList(3L, 5L)))},
                {new Result(-15L, new HashSet<>(asList(3L, 5L)))},
                {new Result(17L, new HashSet<>())},
                {new Result(145678678L,new HashSet<>(asList(2L, 17L,
                        4284667L)))},
        };
    }

    @Test(dataProvider = "prime factors")
    public void testPrimeFactors(Result expectedResult) {
        Result actualResult =
                primeFactorSearch.getPrimeFactors(expectedResult.getInput());
        assertEquals(actualResult.getPrimeFactors(),
                expectedResult.getPrimeFactors());
    }

    @Test(dataProvider = "prime factors")
    public void testPrimeFactorsForNewFile(Result expectedResult) {
        Result actualResult =
                primeFactorSearch.calculateNewPrimeFactors(expectedResult.getInput());
        assertEquals(actualResult.getPrimeFactors(),
                expectedResult.getPrimeFactors());
    }
}

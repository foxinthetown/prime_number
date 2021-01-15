package com.vala.primenumber.helpers;

import com.vala.primenumber.Result;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static com.vala.primenumber.helpers.FileHelper.writeToFile;
import static java.lang.Long.parseLong;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toSet;

@Slf4j
public final class PrimeFactorsDB {

    private static final String FACTORS_SEPARATOR = ",";

    private static final String INPUT_SEPARATOR = ":";

    private static final String CANNOT_READ_FILE_MSG =
            "Unable to read file with prime numbers";

    private final String resultFilePath;

    public PrimeFactorsDB(final String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }

    public Map<Long, Set<Long>> getResults() {
        Map<Long, Set<Long>> factors = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(resultFilePath);
             Scanner sc = new Scanner(inputStream, UTF_8)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Result result = parseResult(line);
                factors.put(result.getInput(), result.getPrimeFactors());
            }
        } catch (Exception e) {
            log.info(CANNOT_READ_FILE_MSG + resultFilePath);
        }
        return factors;
    }

    public void addResult(final Result result) throws IOException {
        String line = result.getInput() + INPUT_SEPARATOR
                + formatFactors(result.getPrimeFactors());
        writeToFile(this.resultFilePath, line);
    }

    public String formatFactors(final Collection<Long> factorNumbers) {
        Set<String> factorStrings = factorNumbers.stream().map(String::valueOf)
                .collect(toSet());
        return String.join(FACTORS_SEPARATOR, factorStrings);
    }

    private Result parseResult(final String result) {
        String inputStr = result.substring(0, result.indexOf(INPUT_SEPARATOR));
        Long input = parseLong(inputStr);
        Set<Long> factors = parseFactors(result);
        return new Result(input, factors);
    }

    private Set<Long> parseFactors(final String result) {
        int inputDelimiterIndex = result.indexOf(INPUT_SEPARATOR);
        String factorsStr = result.substring(inputDelimiterIndex + 1);
        String[] factors = factorsStr.split(FACTORS_SEPARATOR);
        Set<Long> factorsSet = new HashSet<>();
        if (factors.length != 0) {
            for (String factor : factors) {
                if (!factor.isEmpty()) {
                    factorsSet.add(parseLong(factor));
                }
            }
        }
        return factorsSet;
    }
}

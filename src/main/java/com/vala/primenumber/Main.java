package com.vala.primenumber;

import com.vala.primenumber.helpers.PrimeFactorsDB;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static com.vala.primenumber.helpers.FileHelper.getFilePath;
import static com.vala.primenumber.helpers.FileHelper.writeToFile;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.lang.System.exit;
import static java.lang.System.nanoTime;

@Slf4j
public class Main {

    public static final String INVALID_VALUE_ERROR =
            "Please enter a valid number ";

    private static final String PRIME_NUMBERS_MSG_TEMPLATE =
            "Prime Factors of number %d are %s";

    private static final String NO_PRIME_NUMBERS_FOUND_MSG_TEMPLATE =
            "No prime factors for number %d";

    private static final String DURATION_MSG_TEMPLATE =
            "\nIt took %d nanoseconds to find those \n";

    private static final String INPUT_REQUEST_MSG
            = "Give me the number:";

    private static final String INPUT_IS_NEGATIVE_MSG
            = "The number is negative, proceed with abs value? Yes/No";

    private static final String POSITIVE_ANSWER = "Yes";

    private static final String EMPTY_VALUE_ERROR =
            "You've entered an empty value";

    private static final String RESULTS_FILE =
            "/results.txt";

    private static final String OUTPUT_FILE_EXTENSION = ".txt";

    private static final PrimeFactorsDB PRIME_FACTORS_DB =
            new PrimeFactorsDB(getFilePath(RESULTS_FILE));

    public static void main(final String... args) throws IOException {
        Map<Long, Set<Long>> cache = PRIME_FACTORS_DB.getResults();
        PrimeFactorSearch primeFactorSearch = new PrimeFactorSearch(cache);

        Long input = getInput();

        long startTimeNanoSec = nanoTime();
        Result result = primeFactorSearch.getPrimeFactors(input);
        long endTimeNanoSec = nanoTime();

        long durationNanoSec = endTimeNanoSec - startTimeNanoSec;
        String resultMessage = getResultMessage(result, durationNanoSec);
        System.out.println(resultMessage);
        writeOutputToFile(input, resultMessage);

        if (!cache.containsKey(result.getInput())) {
            PRIME_FACTORS_DB.addResult(result);
        }
    }

    private static Long getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(INPUT_REQUEST_MSG);
        String input = scanner.nextLine();
        long number = 1L;
        if (input.isEmpty()) {
            log.error(EMPTY_VALUE_ERROR);
            exit(0);
        }
        try {
            number = parseLong(input);
        } catch (NumberFormatException e) {
            log.error(INVALID_VALUE_ERROR, e);
            exit(0);
        }
        if (number < 0) {
            System.out.println(INPUT_IS_NEGATIVE_MSG);
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase(POSITIVE_ANSWER)) {
                exit(0);
            }
        }
        return number;
    }

    public static String getResultMessage(final Result res,
                                          final long duration) {
        Long input = res.getInput();
        Set<Long> primeFactors = res.getPrimeFactors();
        String durationMsg = format(DURATION_MSG_TEMPLATE, duration);
        if (primeFactors.size() > 0) {
            return format(PRIME_NUMBERS_MSG_TEMPLATE, input,
                    PRIME_FACTORS_DB.formatFactors(primeFactors)) + durationMsg;
        } else {
            return format(NO_PRIME_NUMBERS_FOUND_MSG_TEMPLATE, input)
                    + durationMsg;
        }
    }

    private static void writeOutputToFile(final Long input,
                                          final String message)
            throws IOException {
        String fileName = input + "_" + nanoTime() + OUTPUT_FILE_EXTENSION;
        String outputPath = getFilePath(fileName);
        writeToFile(outputPath, message);
    }
}

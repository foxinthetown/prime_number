package com.vala.primenumber;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public final class PrimeFactorSearch {

    private final Map<Long, Set<Long>> cache;

    public PrimeFactorSearch(final Map<Long, Set<Long>> cache) {
        this.cache = cache;
    }

    private static final Set<Long> PRIMES = new HashSet<>();

    public Result getPrimeFactors(final long inputNum) {
        long number = abs(inputNum);
        if (number >= 0 && number < 4) {
            return new Result(number, new HashSet<>());
        }
        if (this.cache.containsKey(number)) {
            return new Result(number, this.cache.get(number));
        } else {
            return calculateNewPrimeFactors(number);
        }
    }

    public Result calculateNewPrimeFactors(final long number) {
        long n = abs(number);
        Set<Long> divisors = new HashSet<>();
        long divisor = 2;
        while (n > 1 && divisor != number) {
            if (n % divisor == 0) {
                if (isPrime(divisor)) {
                    divisors.add(divisor);
                    n = n / divisor;
                } else {
                    divisor++;
                }
            } else {
                divisor++;
            }
        }
        return new Result(number, divisors);
    }

    public static boolean isPrime(final long n) {
        if (n <= 1) {
            return false;
        }
        if (PRIMES.contains(n)) {
            return true;
        }

        for (long i = 2; i < n; i++) {
            if (i > sqrt(n)) {
                break;
            }
            if (n % i == 0) {
                return false;
            }
        }
        PRIMES.add(n);
        return true;
    }
}

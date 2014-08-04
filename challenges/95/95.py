#coding:utf-8
import random


def gcd(a, b):
    if b == 0:
        return a
    return gcd(b, a%b)

def pollar_rho(n):
    i = 1
    x = random.randint(1, n-1)
    y = x
    k = 2
    while True:
        i += 1
        x = (x * x - 1) % n
        d = gcd(y - x, n)
        if d != 1 and d != n:
            return d
        elif i == k:
            y = x
            k <<= 1

def is_probable_prime(n):
    max_try_num = 5
    assert n >= 2
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    # write n-1 as 2**s * d
    # repeatedly try to divide n-1 by 2
    s, d = 0, n - 1
    while True:
        quotient, remainder = divmod(d, 2)
        if remainder == 1:
            break
        s += 1
        d = quotient
    assert(2**s * d == n-1)
 
    # test the base a to see whether it is a witness for the composedness of n
    def try_composite(a):
        if pow(a, d, n) == 1:
            return False
        for i in range(s):
            if pow(a, 2**i * d, n) == n-1:
                return False
        return True # n is definitely composite
 
    for i in range(max_try_num):
        a = random.randrange(2, n)
        if try_composite(a):
            return False
 
    return True # no base tested showed n as composite

sum = 7393913335919140050521110339491123405991919445111971
while sum > 1:
    if is_probable_prime(sum):
        t = sum
    else:
        t = pollar_rho(sum)
    sum /= t
    print "one factor is %d, and rest is %d" % (t, sum)

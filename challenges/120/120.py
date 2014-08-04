#coding:utf-8
import math

f0 = 1
f1 = 1
f2 = 2

for i in range(2, 100):
	f2 = f0 + f1
	f0, f1 = f1, f2
f100 = f2
l = 150000000000000 - 100
t = 2.0 / (math.sqrt(5) - 1.0)
sum = l * math.log(t) + math.log(f100)
mod = 1000000
ans = str(int(sum / mod)) + str(sum - int(sum/mod)*mod)
print ans
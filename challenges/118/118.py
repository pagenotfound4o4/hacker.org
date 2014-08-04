#coding:utf-8
import math

f0 = 1
f1 = 1
f2 = 2

for i in range(2, 100):
	f2 = f0 + f1
	f0, f1 = f1, f2
f100 = f2
l = 150000000 - 100
t = 2.0 / (math.sqrt(5) - 1.0)
ans = l * math.log(t) + math.log(f100)
print ans
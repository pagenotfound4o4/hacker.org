#coding:utf-8

c = ['a', 'x', 'k', 'y', 'u', 'e']
a, b = 73, 391
s = ""
for i in range(6):
    s += c[((i * b + (i + 8) * a) % len(c))]
print s
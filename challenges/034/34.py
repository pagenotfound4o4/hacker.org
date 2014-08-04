#coding:utf-8

sum = 1
for i in range(0, 11*39):
    sum *= 17
sum = str(sum)
ans = ''
for i in range(0, len(sum), 33):
    ans += sum[i]
print ans


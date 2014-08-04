#-*- coding:utf-8 -*-

f = open('rfc3280.txt', 'r')
lines = f.readlines()
d = dict()
for line in lines:
    for item in line.split():
        if len(item) == 9:
            if item not in d:
                d[item] = 1
            else:
                d[item] += 1

maxkey = None
for key in d.keys():
    if maxkey == None:
        maxkey = key
    elif d[key] > d[maxkey]:
        maxkey = key
print(maxkey)

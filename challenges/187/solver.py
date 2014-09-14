#coding:utf-8
import urllib2

url = 'http://www.hacker.org/challenge/misc/toomany/infinite_number.txt'
req = urllib2.Request(url)
start = 1
for i in range(51):
    start *= 10
end = start + 99
req.headers['Range'] = 'bytes=%d-%d' % (start-1, end-1)
ans = urllib2.urlopen(req).read()
print ans
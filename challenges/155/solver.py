#coding:utf-8
import urllib2

headers = {'User-Agent': 'NCSA_Mosaic/2.0 (Windows 3.1)'}
url = "http://www.hacker.org/challenge/misc/past.php"

req = urllib2.Request(url=url, headers=headers)
result = urllib2.urlopen(req)
print result.read()
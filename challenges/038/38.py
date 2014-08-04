#coding:utf-8
import urllib, urllib2

headers = {'Referer': 'http://whitehouse.gov'}
data = {'name': 'YOUR_USERNAME', 'password': 'YOUR_PASSWORD', 'id': '38', 'go': 'Submit', 'answer': 'http://whitehouse.gov'}
url = "http://www.hacker.org/challenge/chal.php"

req = urllib2.Request(url=url, data=urllib.urlencode(data), headers=headers)
result = urllib2.urlopen(req)
fout = open('38.html', 'w')
fout.write(result.read())
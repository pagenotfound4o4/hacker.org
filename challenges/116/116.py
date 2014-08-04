#coding:utf-8

import urllib2, time

def main():
    while True:
        f = urllib2.urlopen('http://www.hacker.org/challenge/misc/minuteman.php')
        fout = open('116.out', 'w')
        ret = f.read()
        if ret.find('back later') == -1:
            fout.write(ret)
            print 'find answer'
            break
        time.sleep(0.5)

if __name__ == "__main__":
    main()
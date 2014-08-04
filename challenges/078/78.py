import os, sys, urllib, urllib2

class Hacker_Org:
    URL_Chal = "http://www.hacker.org/challenge/chal.php"
    URL_Login = "http://www.hacker.org/forum/login.php"

    usr_info = {'autologin': 'on', 'login': 'Log in', 'redirect': ''}
    submit_info = {'id': ''}
    opener = None
    fp = None
    fr = None

    def __init__(self, usrname, pwd):
        self.usr_info['username'] = usrname
        self.usr_info['password'] = pwd
        cookies = urllib2.HTTPCookieProcessor()
        self.opener = urllib2.build_opener(cookies)
        self.opener.open(self.URL_Login, urllib.urlencode(self.usr_info))
        self.fp = open('problem.html', 'w')
        self.fr = open('result.html', 'w')
        print('Init Class...')

    def __del__(self):
        self.fp.close()
        self.fr.close()
        self.opener.close()
        print("Delete Class...")

    def getproblem(self, id):
        self.submit_info['id'] = id
        fd = self.opener.open(self.URL_Chal, urllib.urlencode(self.submit_info))
        self.fp.write(fd.read())
        self.fp.flush()
        print('Problem is got')

    def submit(self, answer):
        if self.submit_info['id'] == '':
            print('No Problem ID')
            return
        self.submit_info['answer'] = answer
        self.submit_info['go'] = 'Submit'
        fd = self.opener.open(self.URL_Chal, urllib.urlencode(self.submit_info))
        self.fr.write(fd.read())
        self.fr.flush()
        print('Answer is submitted')

'''need to be implemented'''
def solve():
    fread = open('problem.html', 'r')
    data = fread.read()
    data = data.replace('<span style="font-size:8%">FOO</span>', '')
    pos1 = data.find('<b>')
    pos2 = data.find('</b>')

    return data[pos1+3:pos2]

user = ''    # Username
pwd = ''    # Password
id = '78'           # Problem ID

unlucky = Hacker_Org(user, pwd)
unlucky.getproblem(id)
ans = solve()
print(ans)
unlucky.submit(ans)

os.system("pause")
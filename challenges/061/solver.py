#coding:utf-8

def divide(s, base):
    a = 0
    quot = ''
    remainder = 0
    for i in range(0, len(s)):
        a = a * 10 + ord(s[i]) - ord('0')
        q = a / base
        remainder = a % base
        a = remainder
        quot += chr(q + ord('0'))
    while len(quot) > 1 and quot[0] == '0':
        quot = quot[1:]
    return quot, chr(remainder + ord('0'))

def main():
    s = '28679718602997181072337614380936720482949'
    ans = ''
    while s != '0':
        s, r = divide(s, 7)
        ans = r + ans
    print ans

if __name__ == "__main__":
    main()

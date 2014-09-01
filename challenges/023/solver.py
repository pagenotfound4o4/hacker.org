#coding:utf-8

s = "cqrb lryqna rb fjh, fjh qjamna cqjw axc cqracnnw. qnan, hxd wnena twxf qxf oja cx bqroc! xq kh cqn fjh, cqn jwbfna rb mnjmvjwblqnbc."
a = "abcdefghijklmnopqrstuvwxyz"
b = "rstuvwxyzabcdefghijklmnopq"

def main():
    d = dict()
    for c in s:
        if c not in d.keys():
            d[c] = 1
        else:
            d[c] += 1
    print d
    ans = ""
    for c in s:
        if c.isalpha() == True:
            ans += b[ord(c) - ord('a')]
        else:
            ans += c
    print ans

if __name__ == "__main__":
    main()
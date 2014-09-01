#coding:utf-8

NUM = [['\n'], [' '], ['a', 'b', 'c'], 
       ['d', 'e', 'f'], ['g', 'h', 'i'], ['j', 'k', 'l'], 
       ['m', 'n', 'o'], ['p', 'q', 'r', 's'], ['t', 'u', 'v'], 
       ['w', 'x', 'y', 'z']]

def main():
    s = '844331266777793377714447777144474466666330'
    l = []
    c, n = s[0], 1
    for i in range(1, len(s)):
        if s[i] == s[i-1]:
            if n == len(NUM[ord(c) - ord('0')]):
                l.append((c, n))
                n = 1
            else:
                n += 1
        else:
            l.append((c, n))
            c, n = s[i], 1
    l.append((c, n))
    ans = ''
    for t in l:
        pos = ord(t[0]) - ord('0')
        ans += NUM[pos][(t[1] - 1) % len(NUM[pos])]
    print ans

if __name__ == "__main__":
    main()

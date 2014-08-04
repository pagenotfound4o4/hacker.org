#coding:utf-8

s = 'ISS NVVK DIPXYWA PIT AVSUY QIAOP PWZEHVNWIEDZ. CDYT ZVM LOTK HDY AVSMHOVT HV HDOA HYFH, ZVM COSS QY IQSY HV NYH HDY ITACYW, CDOPD OA IKMGQWIHY.'

map = {'A': 's', 'B': '?', 'C': 'w', 'D': 'h', 'E': 'p', 'F': 'x', 
       'G': 'm', 'H': 't', 'I': 'a', 'J': '?', 'K': 'd', 'L': 'f', 
       'M': 'u', 'N': 'g', 'O': 'i', 'P': 'c', 'Q': 'b', 'R': '?', 
       'S': 'l', 'T': 'n', 'U': 'v', 'V': 'o', 'W': 'r', 'X': 'k', 
       'Y': 'e', 'Z': 'y'}

def main():
    d = dict()
    for c in s:
        if c not in d.keys():
            d[c] = 1
        else:
            d[c] += 1
    #print d
    ans = ''
    for c in s:
        if c in map.keys():
            ans += map[c]
        else:
            ans += c
    print ans

if __name__ == "__main__":
    main()

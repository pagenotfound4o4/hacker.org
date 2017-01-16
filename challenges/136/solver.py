#!/usr/bin/env python

ciphertext = 'abbbabaaabbabaaaabbaababaabaaaaaabbaaaababbabbbaabbbaabbabbbabbbabbaabababbbaabaaabaaaaaabbabaababbbaabbaabaaaaaabbaaaababbaabaaabbbabababbabbababbaaabaabbbaabaabbaaaababbbabaaabbaabab'

plaintext = ''
for i in range(0, len(ciphertext), 8):
    part = ''.join(['0' if c == 'a' else '1' for c in ciphertext[i:i+8]])
    plaintext += chr(int(part, 2))
print(plaintext)

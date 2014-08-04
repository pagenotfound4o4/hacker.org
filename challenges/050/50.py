#coding:utf-8
import gzip
import os


def is_gzip_file(src):
    with open(src, 'rb') as fin:
        b0 = fin.read(1)
        b1 = fin.read(1)
    if hex(ord(b0)) == '0x1f' and hex(ord(b1)) == '0x8b':
        return True
    return False

def extract(src, dst):
    g = gzip.GzipFile(mode="rb", fileobj=open(src, 'rb'))
    fout = open(dst, 'w')
    fout.write(g.read())
    fout.flush()
    g.close()
    fout.close()

if __name__ == "__main__":
    cnt = 1
    while is_gzip_file('doll.bin'):
        print cnt
        cnt += 1
        if os.path.exists('doll.bin.gz'):
            os.remove('doll.bin.gz')
        os.rename('doll.bin', 'doll.bin.gz')
        extract('doll.bin.gz', 'doll.bin')
        
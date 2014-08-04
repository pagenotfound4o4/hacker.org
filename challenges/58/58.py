#coding:utf-8
import re


def main():
    fin = open('lorem.txt', 'r')
    lines = fin.readlines()
    vis = dict()
    for line in lines:
        line_list = re.split(r' |,|\.', line.rstrip('\n'))
        while '' in line_list:
            line_list.remove('')
        for item in line_list:
            item = item.lower()
            if item not in vis.keys():
                vis[item] = 1
            else:
                vis[item] += 1
    for key in vis.keys():
        if vis[key] == 1:
            print key


if __name__ == "__main__":
    main()

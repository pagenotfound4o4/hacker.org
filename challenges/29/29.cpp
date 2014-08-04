#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
using namespace std;

char toChar(char* str);

char s[10];
int main()
{
	freopen("data.txt", "r", stdin);
	freopen("out.txt", "w", stdout);

	while (scanf("%s", s) != EOF)
	{
		printf("%c", toChar(s));
	}
	return 0;
}

char toChar(char* str)
{
	char c = 0;
	for (int i=0; i<8; i++)
	{
		c <<= 1;
		c = c | (str[i]-'0');
	}
	return c;
}
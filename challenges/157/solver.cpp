#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
using namespace std;

int ctoi(char c)
{
	if (c>='0' && c<='9')
		return c-'0';
	else
		return c-'a'+10;
}

int main()
{
	freopen("data.txt", "r", stdin);
	freopen("out.txt", "w", stdout);

	char s[101];
	memset(s, 0, sizeof(s));
	scanf("%s", s);
	int len = strlen(s);
	for (int i=0; i<len; i+=2)
	{
		int temp = ctoi(s[i])*16 + ctoi(s[i+1]);
		temp ^= 79;
		printf("%c", (char)temp);
	}
	return 0;
}
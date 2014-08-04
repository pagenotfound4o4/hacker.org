#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
using namespace std;

char s[105];
int main()
{
	freopen("data.txt", "r", stdin);

	gets(s);
	int len = strlen(s);
	for (int i=0; i<len; i++)
	{
		if (s[i]>='a' && s[i]<='z')
		{
			char c = s[i] - 'a';
			c = (c + 13) % 26;
			s[i] = c + 'a';
		}
		else if (s[i]>='A' && s[i]<='Z')
		{
			char c = s[i] - 'A';
			c = (c + 13) % 26;
			s[i] = c + 'A';
		}
		printf("%c", s[i]);
	}
	return 0;
}
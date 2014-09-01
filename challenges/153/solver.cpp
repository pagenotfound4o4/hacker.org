#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
#include <vector>
using namespace std;

int Add(int pos, int num);
int Minus(int pos, int num);

char s[100005];
bool vis[100005];
int main()
{
	freopen("data.txt", "r", stdin);
	//freopen("ans.txt", "w", stdout);

	memset(s, 0, sizeof(s));
	memset(vis, false, sizeof(vis));
	scanf("%s", s);
	int len = strlen(s);
	int sum = 0, pos = 0;
	while (pos < len)
	{
		vis[pos] = true;
		if (s[pos] == 'x')
			pos = Minus(pos, 3);
		else if (s[pos]>='0' && s[pos]<='9')
			sum += s[pos] - '0';
		pos = Add(pos, 1);
	}
	printf("%d\n", sum);

	return 0;
}

int Add(int pos, int num)
{
	while (num)
	{
		pos++;
		if (vis[pos] && s[pos]=='x') continue;
		num--;
	}
	return pos;
}

int Minus(int pos, int num)
{
	while (num)
	{
		pos--;
		if (vis[pos] && s[pos]=='x') continue;
		num--;
	}
	return pos;
}
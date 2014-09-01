#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
using namespace std;
const int MAXN = 10000005;

char s[MAXN];
int arr[MAXN];
int main()
{
	freopen("Pi_Hates_Nines.txt", "r", stdin);
	freopen("ans.txt", "w", stdout);

	memset(s, 0, sizeof(s));
	scanf("%s", s);

	int cnt = 0, len = strlen(s);
	for (int i=0; i<len; i++)
	{
		if (s[i]=='9')
			arr[cnt++] = i;
	}
	int maxpos = 0, maxval = 0;
	for (int i=0; i<cnt-1; i++)
	{
		int t = arr[i+1] - arr[i] - 1;
		if (t > maxval)
		{
			maxpos = i;
			maxval = t;
		}
	}
	printf("%d\n", maxval);
	for (int i=arr[maxpos]+1; i<arr[maxpos+1]; i++)
	{
		printf("%c", s[i]);
	}

	return 0;
}
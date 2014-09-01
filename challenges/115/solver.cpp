#include <cstdio>
#include <cstdlib>
#include <cmath>
#include <iostream>
using namespace std;

int main()
{
	int ans = 0;
	for (int i=1; i<=2118; i++)
	{
		ans += i;
		int t = (int)(sqrt(i*1.0) + 0.5);
		if (t*t == i) ans += i;
	}
	printf("%d\n", ans);
	return 0;
}
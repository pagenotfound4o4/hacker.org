/*
long long calc(long long depth)
{
	if (depth == 0) return 1;
	long long cc = calc(depth - 1);
	return cc + (depth % 7) + ((((cc ^ depth) % 4) == 0) ? 1 : 0);
}
*/
#include <cstdio>
#include <cstdlib>
#include <iostream>
using namespace std;

long long a[2] = {1, 0};
int main()
{
	for (long long i=1; i<=11589; i++)
	{
		a[1] = a[0] + (i % 7) + ((((a[0] ^ i) % 4) == 0) ? 1 : 0);
		a[0] = a[1];
	}
	printf("%lld\n", a[0]);
	return 0;
}
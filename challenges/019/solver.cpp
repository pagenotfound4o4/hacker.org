#include <cstdio>
#include <cstdlib>
#include <iostream>
using namespace std;

int a[20] = {0, 1, 1};
int main()
{
	int sum = 0;
	for (int i=3; i<20; i++)
	{
		a[i] = a[i-1] + a[i-2];
	}
	for (int i=10; i<=17; i++) sum += a[i];
	printf("%d\n", sum);
	return 0;
}
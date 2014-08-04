#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
using namespace std;
const long long TARGET = 1000000000000;

long long sum[5] = {1, 1, 2, 3, 0};
int main()
{
	int day = 4;
	long long total = 0;
	while (total < TARGET)
	{
		sum[4] = sum[3] + sum[2];
		total = sum[4] + sum[3] + sum[2] + sum[1];
		for (int i=0; i<4; i++)
			sum[i] = sum[i+1];
		day++;
	}
	printf("%d\n", day);

	return 0;
}
#include <stdio.h>

int C(int a, int n)
{
	int ret = 1, p = n, i;
	for (i=0; i<a; i++) {
		ret *= p--;
	}
	for (i=1; i<=a; i++) {
		ret /= i;
	}
	return ret;
}

int pow(int x, int y)
{
	int sum = 1;
	while (y-- > 0) {
		sum *= x;
	}
	return sum;
}

int calc(int num)
{
	int sum = 0, i;
	if (num < 10) {
		sum = pow(num, 5) * 21;
	} else if (num < 100) {
		for (i=0; i<=5; i++) {
			sum += C(i, 5) * pow(10, i) * pow((num-10), (5-i)) * (26 - i);
		}
	}
	return sum;
}

int main()
{
	printf("%d\n", calc(99));
	return 0;
}
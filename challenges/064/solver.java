import java.util.Random;

public class solver
{
    public static void main(String[] args) {
        final long mod = 1000000000L;
        final long q = 843997184L;
        Random random = new Random(739391L);
        long a = 1;
        for (int i=0; i<20000; i++) {
            a = (a << 1) % mod;
        }
        long x, b;
        do {
            while ((x=random.nextLong()) < 0L);
            b = (a * ((x + 1) % mod)) % mod;
        }while (b != q);
        System.out.println(x);
    }
}
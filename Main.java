import java.util.ArrayList;

public class Main {

    static byte prime_bits[];

    // ib: 0                    1                      2
    // i:  0 1 2 3  4  5  6  7  8  9 10 11 12 13 14 15 16 .... (i) 
    // v:  3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 .... (i*2+3) = t => i = ( t - 3 ) / 2
    
    static ArrayList<Integer> primes;

/*
 *     static void searchPrimes(int N) {

        prime_bits = new byte[N/2];
        
        primes = new ArrayList<Integer>();
        primes.add(2);

        for (int i = 0; i < prime_bits.length; i++)
            prime_bits[i] = 1; // at the beginning, all odd numbers are primes

        int nsqrt = (int) Math.sqrt(N);

        int ii=0;
        for (int i = 3; i <= nsqrt; i+=2,ii++) {

            if (prime_bits[ii] == 1) {

                for (int j = i * i; j < N; j += i) {
                    if (j%2!=0)  {
                        prime_bits[(j-3)/2] = 0;  
                    }
                }
            }
        }

        // for (int i = 3; i < prime_bits.length; i+=2)
        //     if (prime_bits[i]==1)
        //         primes.add(i);
        int size = N/2;
        for (int i = 0; i < size - 1; i++)
            if (prime_bits[i]==1)
                primes.add(i*2 + 3);

    }
 */

    static void searchPrimes(int N) {

        prime_bits = new byte[N/16];
        
        primes = new ArrayList<Integer>();
        primes.add(2);

        // at the beginning, all odd numbers are primes
        for (int i = 0; i < prime_bits.length; i++)
            prime_bits[i] = (byte)0b11111111; 

        int nsqrt = (int) Math.sqrt(N);

        int ii=0;
        for (int i = 3; i <= nsqrt; i+=2,ii++) {

            if (prime_bits[ii] == 1) {

                for (int j = i * i; j < N; j += i) {
                    if (j%2!=0)  {
                        int jj = (j-3)/2;
                        
                        int b = jj / 8;
                        int bi = jj % 8;

                        switch (bi)
                        {
                            case 0: prime_bits[b] &= 0b11111110; break;  
                            case 1: prime_bits[b] &= 0b11111101; break;
                            case 2: prime_bits[b] &= 0b11111011; break;
                            case 3: prime_bits[b] &= 0b11110111; break;
                            case 4: prime_bits[b] &= 0b11101111; break;
                            case 5: prime_bits[b] &= 0b11011111; break;
                            case 6: prime_bits[b] &= 0b10111111; break; 
                            case 7: prime_bits[b] &= 0b01111111; break;   
                        }
                    }
                }
            }
        }

        // for (int i = 3; i < prime_bits.length; i+=2)
        //     if (prime_bits[i]==1)
        //         primes.add(i);
        int size = N/2;
        for (int i = 0; i < size - 1; i++)
            if (prime_bits[i]==1)
                primes.add(i*2 + 3);

    }

/* 
100			    25
1000		    168	
10000		    1,229
100,000		    9,592
1,000,000	    78,498
10,000,000      664,579
100,000,000     5,761,455
1,000,000,000   50,847,534
2,000,000,000   98,222,287  (? not verify)
10,000,000,000  455,052,511
...
1b			50,847,534
 */



    public static void main(String args[]) {
        //System.out.println("Start crunching primes in the first " + N + " intergers");
        searchPrimes(2000000000);

        //System.out.println(primes);
        System.out.println(primes.size());
    }
}
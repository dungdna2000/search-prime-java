import java.util.ArrayList;

public class Main {

    static byte prime_bits[];

    // ib: 0                    1                      2
    // i:  0 1 2 3  4  5  6  7  8  9 10 11 12 13 14 15 16 .... (i) 
    // v:  3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 .... (i*2+3) = t => i = ( t - 3 ) / 2
    
    //static ArrayList<Integer> primes;
    static long prime_count = 1; 

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

    static void searchPrimes(long N) {

        prime_bits = new byte[(int)(N/16+1)];
        
        //primes = new ArrayList<Integer>();
        //primes.add(2);

        // at the beginning, all odd numbers are primes
        for (int i = 0; i < prime_bits.length; i++)
            prime_bits[i] = (byte)0b11111111; 

        int nsqrt = (int) Math.sqrt(N);

        // current bit and byte position 
        int pos_byte = 0;
        int pos_bit = 0;

        for (long i = 3; i <= nsqrt; i+=2) {
            
            byte byte_i = (byte)0b11111111;

            // extract bit at position corresponding to interger i 
            switch (pos_bit) {
                case 0: byte_i = (byte)(prime_bits[pos_byte] & 0b00000001); break;
                case 1: byte_i = (byte)(prime_bits[pos_byte] & 0b00000010); break;
                case 2: byte_i = (byte)(prime_bits[pos_byte] & 0b00000100); break;
                case 3: byte_i = (byte)(prime_bits[pos_byte] & 0b00001000); break;
                case 4: byte_i = (byte)(prime_bits[pos_byte] & 0b00010000); break;
                case 5: byte_i = (byte)(prime_bits[pos_byte] & 0b00100000); break;
                case 6: byte_i = (byte)(prime_bits[pos_byte] & 0b01000000); break;
                case 7: byte_i = (byte)(prime_bits[pos_byte] & 0b10000000); break;
            }

            if (byte_i != 0) {

                for (long j = i * i; j <= N; j += i) {
                    if (j%2!=0)  {
                        
                        long jj = (j-3)/2;
                        
                        int b = (int)(jj/8);
                        int bi = (int)(jj % 8);

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

            pos_bit++;
            if (pos_bit==8) {
                pos_bit = 0;
                pos_byte++;
            } 
        }

        // for (int i = 3; i < prime_bits.length; i+=2)
        //     if (prime_bits[i]==1)
        //         primes.add(i);
        // int size = N/16;
        // for (int i = 0; i < size - 1; i++)
        //     if (prime_bits[i]==1)
        //         primes.add(i*2 + 3);

        System.out.println("Done marking. Now counting primes!");

        pos_byte = 0;
        pos_bit = 0;
        byte byte_i = (byte)0b11111111;
        for (long i = 3; i <= N; i+=2) {
            // extract bit at position corresponding to interger i 
            switch (pos_bit) {
                case 0: byte_i = (byte)(prime_bits[pos_byte] & 0b00000001); break;
                case 1: byte_i = (byte)(prime_bits[pos_byte] & 0b00000010); break;
                case 2: byte_i = (byte)(prime_bits[pos_byte] & 0b00000100); break;
                case 3: byte_i = (byte)(prime_bits[pos_byte] & 0b00001000); break;
                case 4: byte_i = (byte)(prime_bits[pos_byte] & 0b00010000); break;
                case 5: byte_i = (byte)(prime_bits[pos_byte] & 0b00100000); break;
                case 6: byte_i = (byte)(prime_bits[pos_byte] & 0b01000000); break;
                case 7: byte_i = (byte)(prime_bits[pos_byte] & 0b10000000); break;
            }

            if (byte_i!=0)
                prime_count++; //primes.add(i);

            pos_bit++;
            if (pos_bit==8) {
                pos_bit = 0;
                pos_byte++;
            }
        }
    }

/* 

https://t5k.org/howmany.html

100			        25
1000		        168	
10000		        1,229
100,000		        9,592
1,000,000	        78,498
10,000,000          664,579
100,000,000         5,761,455
1,000,000,000       50,847,534          
10,000,000,000      455,052,511         (ok)
100,000,000,000     4,118,054,813       
1,000,000,000,000   37,607,912,018
10,000,000,000,000  346,065,536,839
...
2,000,000,000       98,222,287  
*/



    public static void main(String args[]) {
        //System.out.println("Start crunching primes in the first " + N + " intergers");
        searchPrimes(10000000000L);

        //System.out.println(primes);
        System.out.println(prime_count/*primes.size()*/);
    }
}
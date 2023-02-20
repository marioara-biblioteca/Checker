#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include <stdbool.h>
int printLevel;
int N;
int P;
int* v;
int *vQSort;
int *vNew;

void compareVectors(int * a, int * b) {
	// DO NOT MODIFY
	int i;
	for(i = 0; i < N; i++) {
		if(a[i]!=b[i]) {
			printf("Sorted incorrectly\n");
			return;
		}
	}
	printf("Sorted correctly\n");
}

void displayVector(int * v) {
	// DO NOT MODIFY
	int i;
	int max = 1;
	for(i = 0; i < N; i++)
		if(max<log10(v[i]))
			max = log10(v[i]);
	int displayWidth = 2 + max;
	for(i = 0; i < N; i++) {
		printf("%*i", displayWidth, v[i]);
		if(!((i+1) % 20))
			printf("\n");
	}
	printf("\n");
}

int cmp(const void *a, const void *b) {
	// DO NOT MODIFY
	int A = *(int*)a;
	int B = *(int*)b;
	return A-B;
}

void getArgs(int argc, char **argv)
{
	if(argc < 4) {
		printf("Not enough paramters: ./program N printLevel P\nprintLevel: 0=no, 1=some, 2=verbouse\n");
		exit(1);
	}
	N = atoi(argv[1]);
	printLevel = atoi(argv[2]);
	P = atoi(argv[3]);
}

void init()
{
	int i;
	v = malloc(sizeof(int) * N);
	vQSort = malloc(sizeof(int) * N);
	vNew = malloc(sizeof(int) * N);
	if(v == NULL) {
		printf("malloc failed!");
		exit(1);
	}

	// generate the vector v with random values
	// DO NOT MODIFY
	srand(42);
	for(i = 0; i < N; i++)
		v[i] = rand()%N;
}

void printPartial()
{
	int i;
	compareVectors(v, vQSort);
}

void printAll()
{
	displayVector(v);
	displayVector(vQSort);
	compareVectors(v, vQSort);
}

void print()
{
	if(printLevel == 0)
		return;
	else if(printLevel == 1)
		printPartial();
	else
		printAll();
}
void swap(int *xp, int *yp)
{
    int temp = *xp;
    *xp = *yp;
    *yp = temp;
}
 
pthread_barrier_t barrier;

void *threadFunction(void *var)
{
	int thread_id = *(int*)var;

	int i, phase;
	int localN, step, start, end;
	for(phase = 0; phase < N; phase++)
    {
        if(phase % 2 == 0) //even phase
        {
            int localSize = N;

            if(N % 2 != 0) localSize--;

            int step = localSize / P;
            
            if(step % 2 != 0) step += 1;
            

            int start = thread_id * step;
            int end = (thread_id + 1) * step;            

            if(thread_id == P - 1) end = localSize;
            for(i = start; i < end; i += 2)
            {
                if(v[i] > v[i+1])
                    swap(&v[i],&v[i+1]);
                
            }
        }
        else //odd phase
        {
            int localSize = N - 1;
            
            int step = localSize / P;
            
            if(step % 2 != 0) step += 1;

            int start = thread_id * step + 1;
            int end = (thread_id + 1) * step + 1;

            if(thread_id == P - 1) end = localSize;

            for(i = start; i < end; i += 2)
            {
                if(v[i] > v[i+1])
					swap(&v[i],&v[i+1]);
            }
        }
        pthread_barrier_wait(&barrier);
    }


}
int main(int argc, char *argv[])
{
	int i, j, aux;
	getArgs(argc, argv);
	init();
	
	// make copy to check it against qsort
	// DO NOT MODIFY
	for(i = 0; i < N; i++)
		vQSort[i] = v[i];
	qsort(vQSort, N, sizeof(int), cmp);
	
	pthread_barrier_init(&barrier, NULL, P);
	pthread_t tid[P];
	int thread_id[P];
	for(i = 0;i < P; i++)
		thread_id[i] = i;

	for(i = 0; i < P; i++) {
		pthread_create(&(tid[i]), NULL, threadFunction, &(thread_id[i]));
	}

	for(i = 0; i < P; i++) {
		pthread_join(tid[i], NULL);
	}
	

	print();
	pthread_barrier_destroy(&barrier);
	return 0;
}

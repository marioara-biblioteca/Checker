#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>

int printLevel;
long long N;
int P;
int* v;
int *vQSort;
int *vNew;
pthread_barrier_t levelBarrier1;
pthread_barrier_t levelBarrier2;
pthread_mutex_t mutex;
void compareVectors(int * a, int * b) {
	// DO NOT MODIFY
	long long i;
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
	if(log2(N)!=(int)log2(N)) {
		printf("N needs to be a power of 2\n");
		exit(1);
	}

	printLevel = atoi(argv[2]);
	P = atoi(argv[3]);
}

void init()
{
	long long i;
	v = malloc(sizeof(int) * N);
	vQSort = malloc(sizeof(int) * N);
	vNew = malloc(sizeof(int) * N);
	
	if(v == NULL || vQSort == NULL || vNew == NULL) {
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
	compareVectors(v, vQSort);
}

void printAll()
{
	compareVectors(v, vQSort);
	displayVector(v);
	displayVector(vQSort);
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
//dupa fiecare merge avem salvat in vNew

void merge(int source[], int start, int mid, int end, int destination[]) {
	// DO NOT MODIFY
	long long iA = start;
	long long iB = mid;
	long long i;
	
	for (i = start; i < end; i++)
	{
		if (end == iB || (iA < mid && source[iA] <= source[iB])) {
			destination[i] = source[iA];
			iA++;
		} else {
			destination[i] = source[iB];
			iB++;
		}
	}
}
int interval;
void *threadFunction(void *var)
{
	int thread_id = *(int*)var;
	long long width,i;
	int *aux;
	long long start,end;
	//for (int k=0;k<3000000;k++)
	for (width = 1; width < N; width = 2 * width) {
		start=thread_id*ceil(N/P);
		start=floor(start/(2*width))*2*width;
		
		end=fmin(N,(thread_id+1)*ceil(N/P));
		end=floor(end/(2*width))*2*width;
		
		for (i = start; i < end; i = i + 2 * width) {
			merge(v, i, i + width, i + 2 * width, vNew);
		}
		pthread_barrier_wait(&levelBarrier1);
		
		if(thread_id==P-1){
			aux = v;
			v = vNew;
			vNew = aux;
		}
		pthread_barrier_wait(&levelBarrier2);
	}

	
    return NULL;

}
int main(int argc, char *argv[])
{
	long long i, j;
	getArgs(argc, argv);
	init();
	pthread_barrier_init(&levelBarrier1,NULL,P);
	pthread_barrier_init(&levelBarrier2,NULL,P);
	pthread_mutex_init(&mutex,NULL);
	// make copy to check it against qsort
	// DO NOT MODIFY
	for(i = 0; i < N; i++)
		vQSort[i] = v[i];
	qsort(vQSort, N, sizeof(int), cmp);
	
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
	pthread_barrier_destroy(&levelBarrier1);
	pthread_barrier_destroy(&levelBarrier2);
	pthread_mutex_destroy(&mutex);
	return 0;
}

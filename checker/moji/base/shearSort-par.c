#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>

int printLevel;
long long N,n,numIter;
int **a;
int *row;
int *v;
int *vQSort;
int P;

pthread_barrier_t barrierRows;
pthread_barrier_t barrierCols;
pthread_barrier_t barrierCopy;

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
	long long i;
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

int cmpAscending(const void *a, const void *b) {
	// DO NOT MODIFY
	int A = *(int*)a;
	int B = *(int*)b;
	return A-B;
}
int cmpDescending(const void *a, const void *b) {
	// DO NOT MODIFY
	int A = *(int*)a;
	int B = *(int*)b;
	return B-A;
}

void getArgs(int argc, char **argv)
{
	if(argc < 4) {
		printf("Not enough paramters: ./program N printLevel\nprintLevel: 0=no, 1=some, 2=verbouse\n P");
		exit(1);
	}
	N = atoi(argv[1]);
	/*if(sqrt(N)!=(long long)sqrt(N)) {
		printf("N needs to be a perfect sqaure\n");//puteam sa nu, faceam padding cu 0
		exit(1);
	}*/
	printLevel = atoi(argv[2]);
    P=atoi(argv[3]);
    n=sqrt(N);
    numIter=(int)ceil(log2(N));
}

void init()
{
	int i,j;
	v = malloc(sizeof(int) * N);
	vQSort = malloc(sizeof(int) * N);
    //vectorul de sortat sub forma de matrice
    a=malloc(n*sizeof(int*));
    if(a==NULL){
        printf("malloc failed");
        exit(1);
    }
	srand(42);
    for(i=0;i<n;i++){
        a[i]=malloc(n*sizeof(int));
        if(a[i]==NULL){
            printf("malloc failed");
            exit(1);
        }
        for(j=0;j<n;j++){
            a[i][j] = rand()%n;
            v[i*n+j]=a[i][j];
        }
    }
	row=malloc(n*sizeof(int));
}
void sortRowEven(int r)
{
    qsort(a[r], n, sizeof(int), cmpDescending);
}

void sortRowOdd(int r)
{

	qsort(a[r],n,sizeof(int),cmpAscending);
}
void sortColum(int c)
{
    long long i;
    for(i=0;i<n;i++)
        row[i]=a[i][c];
    qsort(row,n,sizeof(int),cmpAscending);
    for(i=0;i<n;i++)
        a[i][c]=row[i];
}
void printPartial()
{
	compareVectors(v, vQSort);
}

void printAll()
{
	displayVector(vQSort);
	displayVector(v);
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

void *threadFunction(void *var)
{
    int thread_id = *(int*)var;
    int start,end;
    long long i,j;
    start=thread_id*ceil(n/P);
    if(thread_id == P-1)
        end = n;
    else
        end=fmin(n,(thread_id+1)*ceil(n/P));
   // for(i=0;i<numIter;i++){
        for(j=start;j<end;j++){
            if(j%2 == 1)
                sortRowEven(j);
            else 
                sortRowOdd(j);
        }
        pthread_barrier_wait(&barrierRows);
        for(j=start;j<end;j++) sortColum(j);
        pthread_barrier_wait(&barrierCols);
   // }
    //dupa ce termina de ordonat toate threadurile, fiecare thread copiaza o bucata din matrice in v
    pthread_barrier_wait(&barrierCopy);
    for(i=start;i<end;i++)
        if(i%2==0)
			for(j=0;j<n;j++)
				v[i*n+j]=a[i][j];
		else 
			for(j=n-1;j>=0;j--)
				v[i*n+(n-j-1)]=a[i][j];

}

int main(int argc, char *argv[])
{
	int i, j;
	getArgs(argc, argv);
	init();

	for(i = 0; i < N; i++)
		vQSort[i] = v[i];
	qsort(vQSort, N, sizeof(int), cmpAscending);

    pthread_barrier_init(&barrierRows,NULL,P);
    pthread_barrier_init(&barrierCols,NULL,P);
    pthread_barrier_init(&barrierCopy,NULL,P);

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

    pthread_barrier_destroy(&barrierRows);
    pthread_barrier_destroy(&barrierCols);
    pthread_barrier_destroy(&barrierCopy);
	return 0;
}
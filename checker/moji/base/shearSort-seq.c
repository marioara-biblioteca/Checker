#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>

int printLevel;
int N,n;
int **a;
int *row;
int *v;
int *vQSort;
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
	if(argc < 3) {
		printf("Not enough paramters: ./program N printLevel\nprintLevel: 0=no, 1=some, 2=verbouse\n");
		exit(1);
	}
	N = atoi(argv[1]);
	if(sqrt(N)!=(int)sqrt(N)) {
		printf("N needs to be a perfect sqaure\n");//puteam sa nu, faceam padding cu 0
		exit(1);
	}
	printLevel = atoi(argv[2]);
    n=sqrt(N);
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
    int i;
    
    for(i=0;i<n;i++)
        row[i]=a[r][i]; //copiem linia r in row si sortam
    qsort(row, n, sizeof(int), cmpDescending);
    for(i=0;i<n;i++)
        a[r][i]=row[i]; //copiem la loc in a
}

void sortRowOdd(int r)
{
    int i;
    
    for(i=0;i<n;i++)
        row[i]=a[r][i];
    qsort(row, n, sizeof(int), cmpAscending);
    for(i=0;i<n;i++)
        a[r][i]=row[i];
}
void sortColum(int c)
{
    int i;
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

int main(int argc, char *argv[])
{
	int i, j;
	getArgs(argc, argv);
	init();

	for(i = 0; i < N; i++)
		vQSort[i] = v[i];
	qsort(vQSort, N, sizeof(int), cmpAscending);

    int numIter=(int)ceil(log2(N));
    for(i=0;i<=numIter;i++){
        for(j=0;j<n;j++){ //nr de linii
            if(j%2 == 1)
                sortRowEven(j);
            else 
                sortRowOdd(j);
        }
        for(j=0;j<n;j++) sortColum(j);
    }
    for(i=0;i<n;i++)
        if(i%2==0)
			for(j=0;j<n;j++)
				v[i*n+j]=a[i][j];
		else 
			for(j=n-1;j>=0;j--)
				v[i*n+(n-j-1)]=a[i][j];
				
	
	print();

	return 0;
}
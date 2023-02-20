#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>

int NReps=1;
int printLevel;
int N;
int** a;
int** b;
int** c;

void getArgs(int argc, char **argv)
{
	if(argc < 3) {
		printf("Not enough paramters: ./program N printLevel\nprintLevel: 0=no, 1=some, 2=verbouse\n");
		exit(1);
	}
	N = atoi(argv[1]);
	printLevel = atoi(argv[2]);
}

void init()
{
	int i,j;
	a = malloc(sizeof(int *) * N);
	b = malloc(sizeof(int *) * N);
	c = malloc(sizeof(int *) * N);
	if(a == NULL || b == NULL || c == NULL) {
		printf("malloc failed!");
		exit(1);
	}
	for (i = 0; i < N;i++){
		a[i] = malloc(sizeof(int) * N);
        b[i] = malloc(sizeof(int) * N);
        c[i] = malloc(sizeof(int) * N);
	}

	for(i = 0; i < N; i++)
        for(j = 0; j < N; j++) {
		    a[i][j] = i%500;
		    b[i][j] = i%500;
		    c[i][j] = 0;
		}
}


void printAll()
{
	int i,j;
	for(i = 0; i < N; i++){
		for (j = 0; j < N;j++)
			printf("%i ", c[i][j]);
		printf("\n");
	}

}

void print()
{
	if(printLevel == 0)
		return;
	else
		printAll();
}

int main(int argc, char *argv[])
{
	int i, j,k;
	getArgs(argc, argv);
	init();

	for(k = 0; k < NReps; k++)
		for(i = 0; i < N; i++)
            for(j = 0; j < N; j++)
			    c[i][j] = a[i][j] + b[i][j];

	print();

	return 0;
}

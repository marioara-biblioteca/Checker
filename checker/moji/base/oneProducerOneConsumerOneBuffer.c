#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include <semaphore.h>

int N;
int P;
int printLevel;

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
pthread_mutex_t empty,full;
// THIS IS WHERE YOU HAVE TO IMPLEMENT YOUR SOLUTION
int * buffer;
int BUFFER_SIZE=1;
int get() {
	int val;
	pthread_mutex_lock(&full);  // ma blochez sa nu iau daca bufferul e empty; trec mai departe doar daca e full si am cum sa iau un element
	val = buffer[0];
	pthread_mutex_unlock(&empty);//bufferul e acum gol, pot sa scriu in el
	return val;
}

void put(int value) {
	pthread_mutex_lock(&empty);  // ma blochez sa nu pun daca bufferul e deja full; trec mai departe doar daca e empty
	buffer[0]=value;
	pthread_mutex_unlock(&full);//bufferul e full acum, pot sa iau din el
}
//END THIS IS WHERE YOU HAVE TO IMPLEMENT YOUR SOLUTION

void* consumerThread(void *var)
{
	int i;

	for (i = 0; i < N; i++) {
		
		int value = get();
		if (value != i) {
			printf("Wrong value. I was supposed to get %i but I received %i\n", i, value);
			exit(1);
		}
		
	}
	printf("I finished Correctly\n");

	return NULL;
}

void* producerThread(void *var)
{
	int i;

	for (i = 0; i < N; i++) {
		
		put(i);
		
	}

	return NULL;
}
int main(int argc, char **argv)
{
	getArgs(argc, argv);

	int i;
	int NPROD=1;
	int NCONS=1;
	pthread_t tid[NPROD+NCONS];
    buffer = malloc(BUFFER_SIZE * sizeof(int));
	buffer[0] = 0;
	pthread_mutex_init(&empty, NULL);
	pthread_mutex_init(&full, NULL);
	for (i = 0; i < NPROD; i++) {
		pthread_create(&(tid[i]), NULL, producerThread, NULL);
	}
	for (; i < NPROD + NCONS; i++) {
		pthread_create(&(tid[i]), NULL, consumerThread, NULL);
	}

	for(i = 0; i < NPROD+NCONS; i++) {
		pthread_join(tid[i], NULL);
	}
	pthread_mutex_destroy(&empty);
	pthread_mutex_destroy(&full);
	return 0;
}

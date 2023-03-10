#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>

int printLevel;
int N;
int P;


pthread_mutex_t mutex;
pthread_mutexattr_t attr;

void* threadFunction(void *var)
{
	//TODO preserve the correct order by using barriers
	int thread_id = *(int*)var;
	if(thread_id==0) {
		pthread_mutex_lock(&mutex);
		printf("There should be two messages displayed, I am one of them %i\n",thread_id);
	} else {
		pthread_mutex_lock(&mutex);
		printf("There should be two messages displayed, I am one of them %i\n",thread_id);
	}
}


int main(int argc, char *argv[])
{

	P = 2; // ATTENTION, WE OVERWRITE THE NUMBER OF THREADS. WE ONLY NEED 2
	int i;

	pthread_mutexattr_init(&attr);
	pthread_mutexattr_setrobust(&attr, PTHREAD_MUTEX_ROBUST);

	pthread_mutex_init(&mutex, &attr);

	pthread_t tid[P];
	int thread_id[P];
	for(i = 0;i < P; i++)
		thread_id[i] = i;
	//DO NOT EDIT
	for(i = 0; i < P; i++) {
		pthread_create(&(tid[i]), NULL, threadFunction, &(thread_id[i]));
	}
	//DO NOT EDIT
	for(i = 0; i < P; i++) {
		pthread_join(tid[i], NULL);
	}

	pthread_mutex_destroy(&mutex);
	return 0;
}

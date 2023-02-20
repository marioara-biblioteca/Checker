#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include <unistd.h>

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

int resource = 0;

int read_write_check = 0;
int write_write_check = 0;
int read_read_check = 0;
pthread_mutex_t rwmutex;

int get() {
	read_read_check += 2;
	pthread_mutex_lock(&rwmutex);
	read_write_check += 2;
	pthread_mutex_unlock(&rwmutex);
	return resource;
}

void put(int value) {
	write_write_check += 2;
	read_write_check += 2;
	resource = value;
}


//HERE IS WHERE YOU NEED TO IMPLEMENT YOUR SOLUTION
pthread_mutex_t rmutex, wmutex;
int readersCount;

int get_safe()
{
	int aux;
	printf("Reader is trying to enter into the Database for reading the data\n");
	pthread_mutex_lock(&rmutex); //countreaders mutex, protejeaza variabila countReaders
	readersCount++; //pot sa intre oricati
	if(readersCount == 1) //primul care citeste face lock pe wmutex
	//vreau sa las cititorii sa citeasca simultan, asa ca fac lock pe write pana cand nu mai e niciun cititior si pot sa scriu
		pthread_mutex_lock(&wmutex); //rmutex protejeaza si aici countReaders,
	pthread_mutex_unlock(&rmutex);//facem unlock inainte de instructiunea get pentru ca oricate threaduri cititor pot ajunge acolo
	//usleep(10);
	aux = get();//aici ajung oricati cititori
	printf("Reader is reading the database\n");
	pthread_mutex_lock(&rmutex); //proteject readersCount 
	readersCount--;
	if(readersCount == 0)//ultimul care citeste face unlock pe wmutex 
		pthread_mutex_unlock(&wmutex);
	pthread_mutex_unlock(&rmutex);
	printf("Reader is leaving the database\n");
	return aux;
}

void put_safe(int value) {
	printf("Writer  is trying to enter into database for modifying the data\n");
	pthread_mutex_lock(&wmutex);//asa eliminam write-write-ul (facem o zona critica), e clar ca trebuie pus in codul scriitorului, dar trebuie pus si in codul cititorului ca s ascap de read-write (adica cititorul nu trebuie sa citeasca in timp ce scriitorul scrie)
	//usleep(10);
	put(value);
	pthread_mutex_unlock(&wmutex);
	printf("Writer is leaving the database\n");
}
//END HERE IS WHERE YOU NEED TO IMPLEMENT YOUR SOLUTION

//http://boron.physics.metu.edu.tr/ozdogan/OperatingSystems/week7/node9.html
int value;
void* readerThread(void *var)
{
	int i;
	
	for (i = 0; i < N; i++) {
		value = get_safe();
	}

	return NULL;
}

void* writerThread(void *var)
{
	int i;
	
	for (i = 0; i < N; i++) {
		put_safe(i);
	}

	return NULL;
}

int main(int argc, char **argv)
{
	getArgs(argc, argv);

	int i;
	int NREAD=P;
	int NWRITE=P;
	pthread_t tid[NREAD+NWRITE];

	pthread_mutex_init(&rmutex,NULL);
	pthread_mutex_init(&wmutex,NULL);
	pthread_mutex_init(&rwmutex,NULL);	

	for (i = 0; i < NREAD; i++) {
		pthread_create(&(tid[i]), NULL, readerThread, NULL);
	}

	for(; i < NREAD+NWRITE; i++) {
		pthread_create(&(tid[i]), NULL, writerThread, NULL);
	}

	for(i = 0; i < NREAD+NWRITE; i++) {
		pthread_join(tid[i], NULL);
	}

	if(N * 2 * P == read_read_check && P > 1) {
		printf("Failed two simultaneous readers N*2*P = %d read_read_check = %d\n",N*P*2,read_read_check);
		return 1;
	}
	if(N * 2 * P * 2 != read_write_check) {
		printf("Failed read when write needed: %i got:  %i \n", N * 2 * P * 2, read_write_check);
		return 1;
	}
	if(N * 2 * P != write_write_check) {
		printf("Failed write when write needed: %d got: %d\n",N*2*P,write_write_check);
		return 1;
	}
	printf("Passed all\n");
	
	pthread_mutex_destroy(&rmutex);
	pthread_mutex_destroy(&wmutex);
	pthread_mutex_destroy(&rwmutex);
	return 0;
}
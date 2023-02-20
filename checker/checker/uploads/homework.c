#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include <semaphore.h>

double **initMatrix_2D;
double **auxMatrix_2D;
char **typeMatrix_2D;

double ***initMatrix_3D;
double ***auxMatrix_3D;
char ***typeMatrix_3D;


int linesNo, columnsNo, depthNo;
int dimensions;
int nofComputations;
pthread_barrier_t barrier;

char *inFile, *outFile;
int nofProcesses;

void getArgs(int argc, char **argv){
        if(argc < 4) {
                printf("Not enough paramters:  ./homework INPUT_FILE OUTPUT_FILE NUM_THREADS\n");
                exit(1);
        }

        inFile = argv[1];
        outFile = argv[2];
        nofProcesses = atoi(argv[3]);
}

void init(){
    FILE* fp;

    fp = fopen(inFile, "r");
    fscanf(fp, "%d %d %d", &dimensions, &linesNo, &columnsNo);

    //check if 3D; if so, read depth, alloc memory, read from file and write data in matrix.
    if (dimensions == 3){
        fscanf(fp, "%d", &depthNo);
        initMatrix_3D = malloc(sizeof(double**) * linesNo);
        auxMatrix_3D = malloc(sizeof(double**) * linesNo);
        typeMatrix_3D = malloc(sizeof(char**) * linesNo);

        for(int i = 0; i < linesNo; i++){
            initMatrix_3D[i] = malloc(sizeof(double*) * columnsNo);
            auxMatrix_3D[i] = malloc(sizeof(double*) * columnsNo);
            typeMatrix_3D[i] = malloc(sizeof(char*) * columnsNo);

            for(int j = 0; j < columnsNo; j++){
                initMatrix_3D[i][j] = malloc(sizeof(double) * depthNo);
                auxMatrix_3D[i][j] = malloc(sizeof(double) * depthNo);
                typeMatrix_3D[i][j] = malloc(sizeof(char) * depthNo);
            }
        }

        for(int z = 0; z < depthNo; z++)
            for (int j = 0; j < columnsNo; j++)
                for (int i = 0; i < linesNo; i++) {
                    fscanf(fp, "\n%c", &typeMatrix_3D[i][j][z]);
                    fscanf(fp, "%lf", &initMatrix_3D[i][j][z]);
                }
    }
    else if(dimensions == 2){
        initMatrix_2D = malloc(sizeof(double*) * linesNo);
        auxMatrix_2D = malloc(sizeof(double*) * linesNo);
        typeMatrix_2D = malloc(sizeof(char*) * linesNo);

        for(int i = 0; i < linesNo; i++){
            initMatrix_2D[i] = malloc(sizeof(double) * columnsNo);
            auxMatrix_2D[i] = malloc(sizeof(double) * columnsNo);
            typeMatrix_2D[i] = malloc(sizeof(char) * columnsNo);
        }

        for(int j = 0; j < columnsNo;j++)
            for (int i = 0; i < linesNo; i++) {
                fscanf(fp, "\n%c", &typeMatrix_2D[i][j]);
                fscanf(fp, "%lf", &initMatrix_2D[i][j]);
            }
    }
       
    fscanf(fp, "%d", &nofComputations);
    fclose(fp);
}

void writeToFile(){
    FILE *fp;

    fp = fopen(outFile,"w");
    fprintf(fp,"%d %d %d",dimensions,linesNo,columnsNo);
    if(dimensions == 3)
        fprintf(fp," %d",depthNo);

    fprintf(fp,"\n");

    if(dimensions == 2){
        for(int j=0;j<columnsNo;j++)
            for(int i=0;i<linesNo;i++)
                fprintf(fp,"%c %lf\n",typeMatrix_2D[i][j],initMatrix_2D[i][j]);
    }
    else if(dimensions == 3){
        for(int z = 0;z < depthNo;z++)
            for(int j = 0;j < columnsNo;j++)
                for(int i = 0;i < linesNo;i++)
                    fprintf(fp,"%c %lf\n",typeMatrix_3D[i][j][z],initMatrix_3D[i][j][z]);
    }

    fclose(fp);

}

void *threadFunction(void *args){
    int threadID = *(int*)args;
    int computations = nofComputations;

    int start = threadID * (linesNo/nofProcesses);
    int end = (threadID + 1) * (linesNo/nofProcesses);

    if(threadID == nofProcesses - 1)
        end += linesNo % nofProcesses;
    
    while(computations){
        if (dimensions == 2) {
            for (int i = start; i < end; i++)
                for (int j = 0; j < columnsNo; j++) {
                    double average = 0;
                    int nofValidNeighbours = 0;
                    if(typeMatrix_2D[i][j] == 'n'){
                        auxMatrix_2D[i][j] = initMatrix_2D[i][j];
                    }
                    else {
                        for (int relativeI = -1; relativeI <= 1; relativeI++)
                            for (int relativeJ = -1; relativeJ <= 1; relativeJ++){
                                if (i + relativeI >= 0 && 
                                    j + relativeJ >= 0 &&
                                    i + relativeI < linesNo && 
                                    j + relativeJ < columnsNo) {
                                        if(typeMatrix_2D[i + relativeI][j + relativeJ] != 'n'){
                                            nofValidNeighbours++;
                                            average += initMatrix_2D[i + relativeI][j + relativeJ];
                                        }
                                }
                            }
                        average /= nofValidNeighbours;
                        auxMatrix_2D[i][j] = average;    
                    }
                }
        }
        else if (dimensions == 3) {
            for (int i = start; i < end; i++)
                for (int j = 0; j < columnsNo; j++)
                    for(int z = 0; z < depthNo; z++){
                        double average = 0;
                        int nofValidNeighbours = 0;
                     
                        if(typeMatrix_3D[i][j][z] == 'n'){
                            auxMatrix_3D[i][j][z]= initMatrix_3D[i][j][z];
                        }
                        else {
                            for (int relativeI = -1; relativeI <= 1; relativeI++)
                                for (int relativeJ = -1; relativeJ <= 1; relativeJ++)
                                    for (int relativeZ = -1; relativeZ <= 1; relativeZ++){
                                        if (i + relativeI >= 0 && 
                                            j + relativeJ >= 0 &&
                                            z + relativeZ >= 0 &&
                                            i + relativeI < linesNo && 
                                            j + relativeJ < columnsNo &&
                                            z + relativeZ < depthNo
                                            ) {
                                                if(typeMatrix_3D[i + relativeI][j + relativeJ][z + relativeZ] != 'n'){
                                                    nofValidNeighbours++;
                                                    average += initMatrix_3D[i + relativeI][j + relativeJ][z + relativeZ];
                                                }
                                        }
                                    }
                            average /= nofValidNeighbours;
                            auxMatrix_3D[i][j][z] = average;    
                        }
                    }
        }
        pthread_barrier_wait(&barrier);
        
        if(threadID == 0){
            if (dimensions == 2) {
                double** aux = initMatrix_2D;
                initMatrix_2D = auxMatrix_2D;
                auxMatrix_2D = aux;
            }

            if (dimensions == 3) {
                double*** aux = initMatrix_3D;
                initMatrix_3D = auxMatrix_3D;
                auxMatrix_3D = aux;
            }
        }
        pthread_barrier_wait(&barrier);
        
        computations--;
    }
}


int main(int argc, char **argv){
    getArgs(argc, argv);
    pthread_barrier_init(&barrier, NULL, nofProcesses);
    init();
    
    pthread_t tid[nofProcesses];
    int thread_id[nofProcesses];
    for(int i = 0;i < nofProcesses; i++)
            thread_id[i] = i;
    for(int i = 0; i < nofProcesses; i++) {
            pthread_create(&(tid[i]), NULL, threadFunction, &(thread_id[i]));
    }
    for(int i = 0; i < nofProcesses; i++) {
            pthread_join(tid[i], NULL);
    }

    pthread_barrier_destroy(&barrier);

    writeToFile();

    if (dimensions == 2) {
        for (int i = 0; i < linesNo; i++) {
            free(initMatrix_2D[i]);
            free(auxMatrix_2D[i]);
            free(typeMatrix_2D[i]);
         }
             
        free(initMatrix_2D);
        free(auxMatrix_2D);
        free(typeMatrix_2D);
    }
    else if (dimensions == 3) {
         for (int i = 0; i < linesNo; i++) {
             for (int j = 0; j < columnsNo; j++) {
                free(initMatrix_3D[i][j]);
                free(auxMatrix_3D[i][j]);
                free(typeMatrix_3D[i][j]);
             }
                 
            free(initMatrix_3D[i]);
            free(auxMatrix_3D[i]);
            free(typeMatrix_3D[i]);
         }
        
        free(initMatrix_3D);
        free(auxMatrix_3D);
        free(typeMatrix_3D);
    }

    return 0;
}
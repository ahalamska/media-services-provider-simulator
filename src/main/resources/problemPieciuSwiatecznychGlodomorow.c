#include <stdio.h>
#include <sys/sem.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>

static struct sembuf widelce;


void podniesZeStolu(int idWidelcow, int numerWidelca){
	widelce.sem_num = numerWidelca;
	widelce.sem_op = -1;
	widelce.sem_flg = 0;
	printf("W trakcie otwierania %d  .... \n", numerWidelca);
	if(semop(idWidelcow, &widelce, 1 )== -1){
		perror("Nie moze uniesc widelca... Moze za ciezki ? ;p ");
		exit(1);
	}
}

void odlozNaStol(int idWidelcow, int numerWidelca){
        widelce.sem_num = numerWidelca;
        widelce.sem_op = 1;
        widelce.sem_flg = 0;
        printf("W trakcie zamykania  %d  .... \n", numerWidelca);
	if(semop(idWidelcow, &widelce, 1 )== -1){
               perror("Nie moze odłożyć ;p ");
                exit(1);
        }
}

void sprobujPodniescZeStolu(int idWidelcow, int nr){
	while(semctl(idWidelcow,(nr+4)%5 , GETVAL) == 0 || semctl(idWidelcow, nr , GETVAL) == 0 ){
	printf("CZEKAM : %d \n", nr); 
	sleep(1) ; //czekanie aż zwolni
	}	
	if((nr+4)%5 > nr){
		podniesZeStolu(idWidelcow, nr);
		printf("%d : podnosze 1 widelec : %d \n", nr, nr);
		podniesZeStolu(idWidelcow, (nr+4)%5);
		printf("%d : podnosze 2 widelec : %d \n",nr, (nr+4)%5 );

 	}
	else{
		podniesZeStolu(idWidelcow, (nr+4)%5);
		printf("%d : podnosze 1 widelec : %d \n",nr, (nr+4)%5);
		podniesZeStolu(idWidelcow, nr);
		printf("%d : podnosze 2 widelec : %d \n",nr,  nr);

	}
	
}

void odloz(int idWidelcow, int nr){
	if((nr+4)%5 <  nr){
               odlozNaStol(idWidelcow,nr);
               odlozNaStol(idWidelcow, (nr+4)%5);
        }
        else{
               odlozNaStol(idWidelcow, (nr+4)%5);
               odlozNaStol(idWidelcow, nr);
        }
}
int main(){
	printf("Zaczynamy Kolacje ! \n");

	int *zjedzone;
	int idWidelcow; 
	idWidelcow = semget(666, 5,IPC_CREAT|0600); //storzenie widelcow
	for(int i=0 ; i<5; i++){                   //rozkladanie na stole
		semctl(idWidelcow, i, SETVAL,(int) 1);

	}
	int shmid = shmget(45281, 5*sizeof(int), IPC_CREAT|0600);
        zjedzone = (int*)shmat(shmid, NULL, 0);

	if(fork() == 0){
		if(fork()==0){
			 if(fork()==0){
				if(fork() == 0){	
			
				}
			}
		}
	}
	
        int nr = getpid();   //numer głodomora na podstawie numeru wątku
	nr = nr%5;
	printf("Moj numer : %d \n", nr);
	while(1){
		//sprawdz, czy masz priorytet i sprobuj podnieść widelec
		if(zjedzone[nr] <=  zjedzone[(nr+4)%5] && zjedzone[nr] <= zjedzone[(nr+6)%5] ){
			printf("%d zjadzone :%d\n ",nr, zjedzone[nr]);
			printf("%d zjadzone :%d\n ",(nr+6)%5, zjedzone[(nr+6)%5]);
			printf("%d zjadzone :%d\n ",(nr+4)%5, zjedzone[(nr+4)%5]);
			sprobujPodniescZeStolu(idWidelcow, nr); 
			//zjedz
			zjedzone[nr]+=10;
			printf("JEEEEM:  %d \n ", nr);
			sleep(2);
			//odloż
			odloz(idWidelcow, nr);
			printf(" %d : Zjadlem\n", nr);
			sleep(7);
			printf(" %d : Gotów do jedzenia\n",nr );
		}	
	}
}

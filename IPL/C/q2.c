#include <stdio.h>
#include <stdlib.h>

/* run this program using the console pauser or add your own getch, system("pause") or input loop */

int main(int argc, char *argv[]) {
	int numero;
	freopen("e2.txt", "r", stdin);
	
	do {
	  scanf("%d", &numero);
	  
	  if (numero < 0)
	  	break;
		
	  if (numero % 2 == 0) {
	  	printf("par\n");
	  } else {
	  	printf("impar\n");
	  }
	} while (1);
	
	return 0;
}

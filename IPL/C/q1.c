#include <stdio.h>
#include <stdlib.h>

/* run this program using the console pauser or add your own getch, system("pause") or input loop */

int main(int argc, char *argv[]) {
	int contador, quantidade_de_operacoes, numero1, numero2, resultado;
	int operacao;
	freopen("e1.txt", "r", stdin);
	
	scanf("%d", &quantidade_de_operacoes);
	
	for (contador = 0; contador < quantidade_de_operacoes; contador++) {
		scanf("%d %c %d", &numero1, &operacao, &numero2);
		
		if (operacao == '+') {
			resultado = numero1 + numero2;
		} else {
			resultado = numero1 - numero2;
		}
		
		printf("%d %c %d = %d\n", numero1, operacao, numero2, resultado);
	}
	
	return 0;
}

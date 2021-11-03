#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<string.h>


int main(int argc, char *argv[])
{
	
	
	FILE *fp1;
	fp1=fopen(argv[1],"r"); 
	
	char c;
	while((c=fgetc(fp1))!=EOF){
		printf("%c",c);
	}
	


	
}

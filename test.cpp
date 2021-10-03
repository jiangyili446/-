#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<string.h>
int main(int argc, char *argv[])
{
	FILE *fp;
	fp=fopen(argv[1],"r");
	char c;
	int a=0;
	int b=0;
	while((c=fgetc(fp))!=' '){
		a=c-'0'+a*10;
	}
	while((c=fgetc(fp))!=EOF){
		b=c-'0'+b*10;
	}
	printf("%d\n",a+b); 
}

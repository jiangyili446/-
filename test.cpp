#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<string.h>
int main(int argc, char *argv[])
{
	FILE *fp,*fp2;
	fp=fopen(argv[1],"r");
	fp2=fopen(argv[2],"w");
	char c;
	int a=0;
	int b=0;
	printf("define  dso_local \n i32\n  @main\n(\n)\n{\n ret \n  i32 \n 100  \n}  \n");
	fprintf(fp2,"define  dso_local \n i32\n  @main \n ( \n ) \n { \n ret \n  i32 \n 100  \n}  \n");
	return 0;
}

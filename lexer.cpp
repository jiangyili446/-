#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<string.h>

int classification(char c){
	if( (c>='a'&&c<='z') || (c>='A'&&c<='Z') )
		return 1;
	else if(c>='0'&&c<='9')
		return 2;
	else if(c=='_')
		return 3;
	else if(c=='=' ||
			c==';' ||
			c=='(' ||
			c==')' ||
			c=='{' ||
			c=='}' ||
			c=='+' ||
			c=='*' ||
			c=='/' ||
			c=='<' ||
			c=='>' )
		return 4;
	else
		return 5;
}
int keys(char* s){

		if(strcmp(s,"if")==0) printf("If\n");
		else if(strcmp(s,"else")==0) printf("Else\n");
		else if(strcmp(s,"while")==0) printf("While\n");
		else if(strcmp(s,"break")==0) printf("Break\n");
		else if(strcmp(s,"continue")==0) printf("Continue\n");
		else if(strcmp(s,"return")==0) printf("Return\n");
		else printf("Ident(%s)\n",s);
	
}
int a(char c){
	switch(c){
		case '=':{
			printf("Assign\n");
			return 0;
			break;
		} 
		case ';':{
			printf("Semicolon\n");
			return 0;
			break;
		} 
		case '(':{
			printf("LPar\n");
			return 0;
			break;
		} 
		case ')':{
			printf("RPar\n");
			return 0;
			break;
		} 
		case '{':{
			printf("LBrace\n");
			return 0;
			break;
		} 
		case '}':{
			printf("RBrace\n");
			return 0;
			break;
		} 
		case '+':{
			printf("Plus\n");
			return 0;
			break;
		} 
		case '*':{
			printf("Multi\n");
			return 0;
			break;
		} 
		case '/':{
			printf("Div\n");
			return 0;
			break;
		} 
		case '<':{
			printf("Lt\n");
			return 0;
			break;
		} 
		case '>':{
			printf("Gt\n");
			return 0;
			break;
		} 
		default:{
			printf("Err\n");
			return -1;
			break;
		} 
	}
}
int main(int argc, char *argv[])
{
	FILE *fp1;
	char readChar;
	fp1=fopen(argv[1],"r"); 
	char token[20];
	int tokenIndex=0;
	token[tokenIndex]='\0';
	int ifRead=1;
	while((readChar=fgetc(fp1))!=EOF){
		
		printf("%d\n",readChar);	
		
		
	}
	return 0;
}

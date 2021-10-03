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
	while(1){
		if(ifRead==1){
			readChar=fgetc(fp1);
		}
		ifRead=1;

			if(classification(readChar)==1 || classification(readChar)==3 ){
				
				token[tokenIndex]=readChar;
				tokenIndex++;
				token[tokenIndex]='\0'; 
				while((readChar=fgetc(fp1))!=EOF){
					if(classification(readChar)==1 || classification(readChar)==2 || classification(readChar)==3 ){
						token[tokenIndex]=readChar;
						tokenIndex++;
						token[tokenIndex]='\0'; 
					}else if(readChar==' ' || readChar==10){
						keys(token);
						tokenIndex=0;
						token[tokenIndex]='\0';
						break;
					}else if(classification(readChar)==4){
						keys(token);
						tokenIndex=0;
						token[tokenIndex]='\0';
						ifRead=0;
						break;
					}else{
						keys(token);
						tokenIndex=0;
						token[tokenIndex]='\0';
						ifRead=0;
						return -1;
					}
				}
				if(readChar==EOF){
					keys(token);
					return 0;
				}
				
				
			}else if(classification(readChar)==2){
				
				token[tokenIndex]=readChar;
				tokenIndex++;
				token[tokenIndex]='\0'; 
				while((readChar=fgetc(fp1))!=EOF){
					if(classification(readChar)==2){
						token[tokenIndex]=readChar;
						tokenIndex++;
						token[tokenIndex]='\0'; 
					}else if(readChar==' ' || readChar==10){
						printf("Number(%s)\n",token);
						tokenIndex=0;
						token[tokenIndex]='\0';
						break;
					}else{
						printf("Number(%s)\n",token);
						tokenIndex=0;
						token[tokenIndex]='\0';
						ifRead=0; 
						break;
					}
				}
				if(readChar==EOF){
					printf("Number(%s)\n",token);
					return 0;
				}
				
			}else if(readChar==' '){
				
			}else if(readChar=='\n'){
				
			}else{
				
				char c=readChar;
				readChar=fgetc(fp1);
				if(readChar==EOF){
					a(c);
					return 0;
				}else if(c=='='&&readChar=='='){
					printf("Eq\n");
				}else{
					if(a(c)==-1)
						return 0;
					ifRead=0; 
					
				}
				
			}
			
		
		
	}
}

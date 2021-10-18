#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<string.h>
void clean_notes(char *input,char *output){
	FILE *fp,*fp2;
	fp=fopen(input,"r");
	fp2=fopen(output,"w");
	char c;
	int a=0;
	int b=0;
	while( (c=fgetc(fp))!=EOF ){
		if(c=='/'){
			char c2=fgetc(fp);
			if(c2=='/'){
				while( (c=fgetc(fp))!='\n' ){
					
				}
				fprintf(fp2,"\n");
			}
			else if(c2=='*'){
				while( (c=fgetc(fp))!=EOF ){
					if(c=='*'){
						c=fgetc(fp);
						if(c=='/'){
							break;
						}
					}
				}
			}
			else{
				fprintf(fp2,"/%c",c2);
			}
		}else{
			fprintf(fp2,"%c",c);
		}
	}
	fclose(fp);
	fclose(fp2);
}
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
int keys(char* s,FILE *fp2){

		if(strcmp(s,"if")==0) fprintf(fp2,"If\n");
		else if(strcmp(s,"else")==0) fprintf(fp2,"Else\n");
		else if(strcmp(s,"while")==0) fprintf(fp2,"While\n");
		else if(strcmp(s,"break")==0) fprintf(fp2,"Break\n");
		else if(strcmp(s,"continue")==0) fprintf(fp2,"Continue\n");
		else if(strcmp(s,"return")==0) fprintf(fp2,"Return\n");
		else fprintf(fp2,"Ident(%s)\n",s);
	
}
int a(char c,FILE *fp2){
	switch(c){
		case '=':{
			fprintf(fp2,"Assign\n");
			return 0;
			break;
		} 
		case ';':{
			fprintf(fp2,"Semicolon\n");
			return 0;
			break;
		} 
		case '(':{
			fprintf(fp2,"LPar\n");
			return 0;
			break;
		} 
		case ')':{
			fprintf(fp2,"RPar\n");
			return 0;
			break;
		} 
		case '{':{
			fprintf(fp2,"LBrace\n");
			return 0;
			break;
		} 
		case '}':{
			fprintf(fp2,"RBrace\n");
			return 0;
			break;
		} 
		case '+':{
			fprintf(fp2,"Plus\n");
			return 0;
			break;
		} 
		case '*':{
			fprintf(fp2,"Mult\n");
			return 0;
			break;
		} 
		case '/':{
			fprintf(fp2,"Div\n");
			return 0;
			break;
		} 
		case '<':{
			fprintf(fp2,"Lt\n");
			return 0;
			break;
		} 
		case '>':{
			fprintf(fp2,"Gt\n");
			return 0;
			break;
		} 
		default:{
			fprintf(fp2,"Err\n");
			return -1;
			break;
		} 
	}
}

int lexer(FILE *fp1,FILE *fp2){
	char readChar;
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
						keys(token,fp2);
						tokenIndex=0;
						token[tokenIndex]='\0';
						break;
					}else if(classification(readChar)==4){
						keys(token,fp2);
						tokenIndex=0;
						token[tokenIndex]='\0';
						ifRead=0;
						break;
					}else{
						keys(token,fp2);
						tokenIndex=0;
						token[tokenIndex]='\0';
						ifRead=0;
						break;
					}
				}
				if(readChar==EOF){
					keys(token,fp2);
					return 0;
				}
				
				
			}
			else if(readChar=='0'){
				token[tokenIndex]=readChar;
				tokenIndex++;
				token[tokenIndex]='\0'; 
				
				readChar=fgetc(fp1);
				if(readChar=='x' || readChar=='X'){
					
					tokenIndex=0;
					token[tokenIndex]='\0';
					int hex=0;
					int times=0;
					while((readChar=fgetc(fp1))!=EOF){
						if( (readChar>='0'&&readChar<='9') ){
							hex=hex*16+readChar-'0';
						}else if( (readChar>='a'&&readChar<='f')  ){
							hex=hex*16+10+readChar-'a';
						}else if( (readChar>='A'&&readChar<='F')){
							hex=hex*16+10+readChar-'A';
						}else if(readChar==' ' || readChar==10){
							if(times==0){
								return 9;
							}else{
								fprintf(fp2,"Number(%d)\n",hex);
								tokenIndex=0;
								token[tokenIndex]='\0';
								break;
							}
						}else{
							if(times==0){
								return 9;
							}else{
								fprintf(fp2,"Number(%d)\n",hex);
								tokenIndex=0;
								token[tokenIndex]='\0';
								ifRead=0; 
								break;
							}
							
						}
						times++;
					}
					if(readChar==EOF){
						if(times==0){
							return 9;
						}else{
							fprintf(fp2,"Number(%d)\n",hex);
							return 0;
						}
						
					}
					
				}
				else if(readChar>='0' && readChar<='7'){
					
					tokenIndex=0;
					token[tokenIndex]='\0';
					int octal=0;
					octal=octal*8+readChar-'0';
					int times=0;
					while((readChar=fgetc(fp1))!=EOF){
						if( (readChar>='0'&&readChar<='7') ){
							octal=octal*8+readChar-'0';
						}else if(readChar==' ' || readChar==10){
							if(times==0){
								return 9;
							}else{
								fprintf(fp2,"Number(%d)\n",octal);
								tokenIndex=0;
								token[tokenIndex]='\0';
								break;
							}
						}else{
							if(times==0){
								return 9;
							}else{
								fprintf(fp2,"Number(%d)\n",octal);
								tokenIndex=0;
								token[tokenIndex]='\0';
								ifRead=0; 
								break;
							}
							
						}
						times++;
					}
					if(readChar==EOF){
						if(times==0){
							return 9;
						}
						else{
							fprintf(fp2,"Number(%d)\n",octal);
							return 0;
						}
						
					}
					
				}
				else if(readChar==' ' || readChar==10){
					fprintf(fp2,"Number(%s)\n",token);
					tokenIndex=0;
					token[tokenIndex]='\0';
				}
				else{
					fprintf(fp2,"Number(%s)\n",token);
					tokenIndex=0;
					token[tokenIndex]='\0';
					ifRead=0; 
				}
				
			}
			else if(classification(readChar)==2){
				
				token[tokenIndex]=readChar;
				tokenIndex++;
				token[tokenIndex]='\0'; 
				while((readChar=fgetc(fp1))!=EOF){
					if(classification(readChar)==2){
						token[tokenIndex]=readChar;
						tokenIndex++;
						token[tokenIndex]='\0'; 
					}
					else if(readChar==' ' || readChar==10){
						fprintf(fp2,"Number(%s)\n",token);
						tokenIndex=0;
						token[tokenIndex]='\0';
						break;
					}else{
						fprintf(fp2,"Number(%s)\n",token);
						tokenIndex=0;
						token[tokenIndex]='\0';
						ifRead=0; 
						break;
					}
				}
				if(readChar==EOF){
					fprintf(fp2,"Number(%s)\n",token);
					return 0;
				}
				
			}
			else if(readChar==' '){
				
			}
			else if(readChar=='\n'){
				
			}
			else if(readChar==9){
				
			}
			else if(readChar==EOF){
				return 0;
			}
			else{
				
				char c=readChar;
				readChar=fgetc(fp1);
				if(readChar==EOF){
					a(c,fp2);
					return 0;
				}else if(c=='='&&readChar=='='){
					fprintf(fp2,"Eq\n");
				}else{
					if(a(c,fp2)==-1)
						return 0;
					ifRead=0; 
					
				}
				
			}
			
		
		
	}
}
int yufa(FILE *fp1,FILE *fp2){
	char buf[100];
	fprintf(fp2,"define dso_local ");
	while(fgets(buf,100,fp1)!=NULL){
		
		if(strcmp(buf,"Ident(int)\n")==0){
			fprintf(fp2,"i32 ");
		}
		else if(strcmp(buf,"Ident(main)\n")==0){
			fprintf(fp2,"@main");
		}
		else if(strcmp(buf,"LPar\n")==0){
			fprintf(fp2,"(");
		}
		else if(strcmp(buf,"RPar\n")==0){
			fprintf(fp2,") ");
		}
		else if(strcmp(buf,"LBrace\n")==0){
			fprintf(fp2,"{\n");
		}
		else if(strcmp(buf,"RBrace\n")==0){
			fprintf(fp2,"}\n");
		}
		else if(strcmp(buf,"Return\n")==0){
			fprintf(fp2,"ret ");
		}
		else if(buf[0]=='N'){
			int num=0;
			for(int i=7;buf[i]!=')';i++){
				num=num*10+buf[i]-'0';
			}
			fprintf(fp2,"i32 %d\n",num);
		}
		else{
			
		}
	}
}
int main(int argc, char *argv[])
{
	
	char* input=argv[1];
	char no_notes[]="no_notes.txt";
	clean_notes(input,no_notes);
	
	FILE *fp1;
	fp1=fopen("no_notes.txt","r"); 
	
	FILE *fp2;
	fp2=fopen("lexer.txt","w"); 
	
	if (lexer(fp1,fp2)==0){
		fclose(fp1);
		fclose(fp2);
	}else{
		fclose(fp1);
		fclose(fp2);
		return 9;
	}
	
	FILE *fp3;
	fp3=fopen("lexer.txt","r"); 
	
	FILE *fp4;
	fp4=fopen(argv[2],"w"); 
	
	yufa(fp3,fp4);
	
	fclose(fp3);
	fclose(fp4);
	
	FILE *fp5;
	fp5=fopen(argv[2],"r"); 
	char k;
	while( (k=fgetc(fp5))!=EOF ){
		printf("%c",k);
	}
	
	
	
}

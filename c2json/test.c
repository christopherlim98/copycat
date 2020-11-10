void Strcat(char *s, char *t){
	while(*s!='\0')s++;
	for(;*t!='\0';s++,t++)*s=*t;
	*s='\0';
 
}
#include <iostream>
#include <stdio.h>
using namespace std;

int main(int argc, char *argv[])
{
	FILE *fp;
	for(int i = 0;i<argc;i++){
		cout<<argv[i]<<endl ;
	}
		if( (fp=fopen("C:\\Gnss\\Data\\demo1.txt","a")) == NULL ){
   	 	printf("Error on open demo1.txt file!");
		}else{
			cout<<"参数1："<<argv[1]<<endl;
			//fputs(argv[1],fp);
			fputc('B',fp);
			//fclose(fp);
		}
		if( (fp=fopen("C:\\Gnss\\Data\\demo2.txt","a")) == NULL ){
   	 	printf("Error on open demo2.txt file!");
		}else{
			cout<<"参数2："<<argv[2]<<endl;
			fputs(argv[2],fp);
			//fclose(fp);
		}
		if( (fp=fopen("C:\\Gnss\\Data\\demo3.txt","a")) == NULL ){
   	 	printf("Error on open demo3.txt file!");
		}else{
			cout<<"参数3："<<argv[3]<<endl;
			fputs(argv[3],fp);
		}
		fclose(fp);
	cout<<"the end ~~~~"<<endl;
	return 0;

}

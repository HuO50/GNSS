/*
some useless lines
// #include "stdafx.h"
//  #pragma comment(lib,"ws2_32.lib")
*/
#include <windows.h>
#include <winsock.h>
#include <stdio.h>
#include <math.h>
#include <iostream>
#include <fstream>
#include <string>

using namespace std;
const std::string endString	= "---  ***  ---";
const char delimiter		= '\t';		// Delimiter for output to file
const double startTime		= 1;		// seconds into run to start sampling data
const double endTime		= 15;		// Seconds into run to finish
const double timeBetween	= 0.5;		// seconds between each sample

const char* hostname		= "localhost";	// Name of machine to connect to
const int portNumber		= 15650;		// Port to connect to SimGEN, must be byte swapped using htons() fn (equiv to 8765 unswapped)

const char* logfile			= "F:\\Done\\ConSIM\\data.txt";	// Filename of the logfile


const std::string commands[] = { "SIG_TXID,v1_a1,0,0,1,0,0",
								 "SIG_MPI,v1_a1,0,0,1,0,0",
								 "SIG_LEVEL,v1_a1,0,0,1,0,0",
								 "SIG_PRANGE,v1_a1,0,0,1,0,0",
								 "SIG_TXID,v1_a1,0,1,1,0,0",
								 "SIG_MPI,v1_a1,0,1,1,0,0",
								 "SIG_LEVEL,v1_a1,0,1,1,0,0",
								 "SIG_PRANGE,v1_a1,0,1,1,0,0",
								 "SIG_TXID,v1_a1,0,2,1,0,0",
								 "SIG_MPI,v1_a1,0,2,1,0,0",
								 "SIG_LEVEL,v1_a1,0,2,1,0,0",
								 "SIG_PRANGE,v1_a1,0,2,1,0,0",
								 "SIG_TXID,v1_a1,0,3,1,0,0",
								 "SIG_MPI,v1_a1,0,3,1,0,0",
								 "SIG_LEVEL,v1_a1,0,3,1,0,0",
								 "SIG_PRANGE,v1_a1,0,3,1,0,0",
								 "SIG_TXID,v1_a1,0,4,1,0,0",
								 "SIG_MPI,v1_a1,0,4,1,0,0",
								 "SIG_LEVEL,v1_a1,0,4,1,0,0",
								 "SIG_PRANGE,v1_a1,0,4,1,0,0",
								 "SIG_TXID,v1_a1,0,5,1,0,0",
								 "SIG_MPI,v1_a1,0,5,1,0,0",
								 "SIG_LEVEL,v1_a1,0,5,1,0,0",
								 "SIG_PRANGE,v1_a1,0,5,1,0,0",
								 "SIG_TXID,v1_a1,0,6,1,0,0",
								 "SIG_MPI,v1_a1,0,6,1,0,0",
								 "SIG_LEVEL,v1_a1,0,6,1,0,0",
								 "SIG_PRANGE,v1_a1,0,6,1,0,0",
								 "SIG_TXID,v1_a1,0,7,1,0,0",
								 "SIG_MPI,v1_a1,0,7,1,0,0",
								 "SIG_LEVEL,v1_a1,0,7,1,0,0",
								 "SIG_PRANGE,v1_a1,0,7,1,0,0",
								 "SIG_TXID,v1_a1,0,8,1,0,0",
								 "SIG_MPI,v1_a1,0,8,1,0,0",
								 "SIG_LEVEL,v1_a1,0,8,1,0,0",
								 "SIG_PRANGE,v1_a1,0,8,1,0,0",
								 "SIG_TXID,v1_a1,0,9,1,0,0",
								 "SIG_MPI,v1_a1,0,9,1,0,0",
								 "SIG_LEVEL,v1_a1,0,9,1,0,0",
								 "SIG_PRANGE,v1_a1,0,9,1,0,0",
								 "SIG_TXID,v1_a1,0,10,1,0,0",
								 "SIG_MPI,v1_a1,0,10,1,0,0",
								 "SIG_LEVEL,v1_a1,0,10,1,0,0",
								 "SIG_PRANGE,v1_a1,0,10,1,0,0",
								 "SIG_TXID,v1_a1,0,11,1,0,0",
								 "SIG_MPI,v1_a1,0,11,1,0,0",
								 "SIG_LEVEL,v1_a1,0,11,1,0,0",
								 "SIG_PRANGE,v1_a1,0,11,1,0,0",
								 endString };

/*  Function prototypes  *****************************************************/

void selectWinSock1_1();	// Use WinSock v1.1 instead of v2.0
void createSocket();		// Creates a socket connection
bool connectToServer();		// Connects the socket to SimGEN

inline std::string readData( void );							// Reads a string from SimGEN
inline void sendData( const std::string& message );				// Sends a string to SimGEN
inline std::string extractData( const std::string& message );	// Extracts the data part of the messgae
inline std::string calculateTimeString( const double time );	// Calculate time string

bool initialise( void );	// Sets up the socket and sets this thread's priority
void tidyup();				// Clears up the things set by initialise()

/*  Global variables  ********************************************************/

SOCKET			theSocket;				// Socket to SimGEN
std::ofstream	of( logfile );			// Log file for collected data



int main(int argc, char* argv[])
{
	register int	index = 0;					// Index into message array
	double			currentTime = startTime;	// Time into scenario to get data from
	double			progress = startTime;		// For a simple on-screen progress indicator

	std::string		buffer = "";				// Output buffer for each line of data
	string comand = "";
	string ip = "";
	string port = "";
	string storePath = "";
	int i;
	for(i=0;i<argc;i++){
        cout<<"输出传入的参数，测试用，从0开始计------"<<endl;
        cout<<"第"<<i<<"个参数："<<argv[i]<<endl;
	}
	if(argc>1){
            cout<<"使用自定义参数！"<<endl;
            ip = argv[1];//第一个参数是连接到的模拟器的IP
            port = argv[2];//第二个参数是连接到的模拟器的端口号
            comand = argv[3];//第三个参数是发送的命令
            storePath = argv[4];//第四个参数是接收来自模拟器反馈的数据，并存储在这个路径的文本中
	}else{
	cout<<endl<<"使用默认参数"<<endl<<endl;
	}
	cout << "使用WinSock连接SimGEN-----模拟器\n" << flush;


    cout<<"传输命令内容如下："<<endl;
	for( index = 0; commands[ index ] != endString; index++ ){
        cout<<commands[index]<<endl<<endl;
	}

	if( !initialise() )
	{
		cout << "初始化socket连接失败." << endl;
		Sleep( 2000 );
		exit( 1 );
	}

	/* Show the commands that are requesting the data in the top line of the log
	 */
	for( index = 0; commands[ index ] != endString; index++ ){
        cout<<"传输命令内容如下："<<endl;
        cout<<commands[index]<<endl;
		of << "'" << commands[ index ] << "'" << delimiter;
	}
	of << "'TIME'"<< endl << endl;

	/* Tell the user to start the scenario
	 */
	cout << endl << "启动一个scenario，并将反馈数据存储在您刚才设置的路径中，如果没有设置，将采用默认路径，同文件夹下的data.txt中"<< endl;

	/* Start collecting data
	 */
	for( currentTime = startTime; currentTime <= endTime; currentTime += timeBetween )
	{
		/* Empty the output buffer
		 */
		buffer = "";

		/* Log any pieces of data with the delimiter between them
		 */
		for( index = 0; commands[ index ] != endString; index++ )
		{
			sendData( calculateTimeString( currentTime ) + ',' + commands[ index ] );
			buffer += extractData( readData() ) + delimiter;
		} /*  end for  */

		/* Add a timestamp to the end of the line.  If the time is not the same
		 * as the timestamp sent to request data, some of the data could be from
		 * the wrong time in the simulation
		 */
		sendData( "TIME" );
		buffer += extractData( readData() );

		/* Output the received data to the log-file
		 */
		of << buffer << endl << flush;

		/* Update the progress indicator.  Indicator outputs once a second
		 */
		if( floor( progress ) < floor( currentTime ) )
		{
			progress = currentTime;
			cout << '.';
		} /*  end if  */
	} /*  end for  */

	cout << endl;
	of << endl;

	tidyup();
	return 0;
}


/* 思博伦推荐winsock1.1
 */
void selectWinSock1_1()
{
	int error = 0;
    WORD version = MAKEWORD(1, 1);
    WSADATA wsaData;

    error = WSAStartup(version, &wsaData);
    if (error != 0 )
    {
        cout << "选取WinSock出现错误---selectWinSock1_1 " << endl;
        exit(1);
    }

    if (LOBYTE(wsaData.wVersion) != 1 || HIBYTE(wsaData.wVersion) != 1 )
    {
        cout << "WinSock 1.1 不可用" << endl;
        WSACleanup();
        exit(2);
    }
}


/* Creates a socket connection
 */
void createSocket()
{
    theSocket = socket( AF_INET, SOCK_STREAM, 0 );

    if( theSocket == INVALID_SOCKET )
	{
		cout << "创建Socket失败" << endl;
		Sleep( 2000 );
		exit( 1 );
	}
}

/* Connects the socket to SimGEN
 */
bool connectToServer()
{
	SOCKADDR_IN theServerAddress;
	bool		connected = true;	// Assume a connection has been made

	/* NOTE: We use a hard-wired port number, this is not strictly proper but
	 *       should be ok for all practical purposes.
	 */
    theServerAddress.sin_family = AF_INET;
    theServerAddress.sin_port = htons(portNumber);  // Swap bytes to net ordering (equiv to port # 8765 unswapped)

    /* Find IP address of SimGEN application. If this is on the same PC as this
	 * program then set the hostname to "localhost".  If on a different PC then
	 * set the network name of the other PC,
	 * e.g. the Spirent lab HP vectra PC is "paulc-310"
	 */
    HOSTENT* pHost = gethostbyname( hostname );
    in_addr* pHexAddress = (in_addr*)pHost->h_addr_list[ 0 ];
    char* DottedAddress = inet_ntoa( *pHexAddress );
    theServerAddress.sin_addr.s_addr = inet_addr( DottedAddress );

	/* Make the connection to SimGEN
	 */
	if( connect( theSocket,
		(const SOCKADDR*) &theServerAddress,
		sizeof( SOCKADDR_IN )) == SOCKET_ERROR )
	{
		connected = false;
	} /*  end if  */

	return connected;
}


/* Closes the socket connection and does any other tidying up needed
 */
void tidyup()
{
	cout << "关闭socket----tudyup" << flush;
	closesocket( theSocket );
	cout << "完成" << endl;
}


/* All the initialisation is done here to keep the main() function a little bit
 * neater and make the code more readable
 */
bool initialise( void )
{
	bool	isInitialised = true;

	/* Set process priority to above normal
	 */
	cout << "初始化开始，设置本进程的而优先级" << flush;
	if ( !SetPriorityClass( GetCurrentProcess(), HIGH_PRIORITY_CLASS ) )
	{
		cout << "失败" << endl;
		cout << "Warning: Data may not arrive promptly.  Timing errors may occur" << endl;
	}
	else
	{
		cout << "完毕~~~" << endl;
	}

	/* Select Sockets Version 1.1 as it is more generic than Sockets 2.0
	 */
	cout << "使用winsock 1.1...进行连接" << flush;
	selectWinSock1_1();
	cout << "完成" << endl;

	/* Create the socket to communicate with SimGEN
	 */
	cout << "创建socket..." << flush;
	createSocket();
	cout << "完成" << endl;

	/* Connect to SimGEN
	 */
	cout << "开始连接到 SimGEN..." << flush;
	if( connectToServer() )
	{
		cout << "完成" << endl;
	}
	else
	{
		cout << "失败" << endl;
		isInitialised = false;
	} /*  end if  */

	return isInitialised;
}


/* Sends the message to SimGEN
 */
void sendData( const std::string& message )
{
    if( send( theSocket, message.c_str(), message.length(), 0 ) == SOCKET_ERROR )
	{
		cout << "发送命令或者用户需要发送的数据" << endl;
		Sleep( 2000 );
		exit( 1 );
	}
}


/* Reads the data from the socket
 */
std::string readData ( void )
{
	static char		buffer[ 32001 ];
	register int	num_chars_read = recv( theSocket, buffer, 32000, 0 );

	if( num_chars_read == SOCKET_ERROR )
	{
		cout << "Connection::ReadData()错误捕获代码触发----- " << WSAGetLastError() << endl;
		Sleep( 2000 );
		exit( 1 );
	} /*  end if  */

	buffer[ num_chars_read ] = '\0';	// Make sure the message is NULL-terminated
	return buffer;
}


/* Extracts the data part of the messgae
 */
std::string extractData( const std::string& message )
{
	register int startOfData = message.find( "<data>" ) + 6;
	register int endOfData = message.find( "</data>" );

	return message.substr( startOfData, endOfData - startOfData );
	cout<<"extractData完成"<<endl;
}


/* Calculate time string to add to the front of the command
 */
std::string calculateTimeString( const double t )
{
	static double	fraction, seconds;
	static long		totalSeconds, totalMinutes, totalHours, totalDays;
	static int		hours, minutes;

	static char		buffer[ 64 ];

	fraction  = t - floor( t );			// Separate the fractions of a second
	totalSeconds = floor( t );			// Time into scenario in seconds
	totalMinutes = totalSeconds / 60;	// Complete minutes into scenario
	totalHours = totalMinutes / 60;		// Whole hours into scenario
	totalDays = totalHours / 24;		// Number of days into scenario

	/* Calculate the time into days, hours, minutes and seconds human-readable
	 * form
	 */
	hours = ( totalHours % 24 );
	minutes = ( totalMinutes % 60 );
	seconds = fraction + ( totalSeconds % 60 );

	sprintf( buffer, "%i %02i:%02i:%02.6f", totalDays, hours, minutes, seconds );

	return buffer;
}

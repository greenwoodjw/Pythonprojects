
What is it?

*  I've built a crude secure mail system.  Like a normal email system, it allows users to send
and receive email.  it will automatically encrypt all messages with and RSA assymetric key pair

Installation

* Drop war file into you web-apps folder
* change webserver address and port in web.xml's context parameter "webserver"
* build table space in mysql with "source crypto.sql"
* using "AssymetricKeyMaker", you will generate and load RSA public and private keys into the user database.  
This is a one time use per user tool, so I was lazy and hardcoded user names into the method.  You will need
to edit the uploadPubKey and uploadPrivKey preparedstatements, recompile, and execute for every user who 
sends or receives messages.


Launching the application

* open http://localhost:port/final/HomePage.jsp to start application
* you should send yourself an email message first, otherwise reading your inbox will be very boring and
not really show you anything interesting the first time you open it


Features and Notes

* When I commit messages to the database, I commit a cleartext and ciphertext version of the message.  
In the real world, this would kind of defeat the purpose of a secure mail system, but it helps demonstrate
that the system really works by giving us before and after data.
* The keys and ciphertext are all byte[] blocks, which created interesting problems when it came time to 
upload info into the database.  Using standard sql statements, you wound up turning the bytes into ascii, 
rendering it useless.  Byte data instead had to be uploaded w/ prepared statements to preserve the byte arrays

